package de.platon42.intellij.plugins.m68k.lexer

import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import de.platon42.intellij.plugins.m68k.asm.AssemblerDirectives
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.mnemonics
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

object LexerUtil {

    private val ASSIGNMENT_SEPARATORS = charArrayOf(' ', '\t', '=', ':')
    private val TOKEN_SEPARATORS = charArrayOf(' ', '\t', ':')

    @JvmStatic
    fun isAsmMnemonic(text: CharSequence) = mnemonics.contains(text.toString().lowercase())

    @JvmStatic
    fun isAsmMnemonicWithSize(text: CharSequence) = text.contains('.')
            && text.dropLast(1).endsWith('.')
            && mnemonics.contains(text.dropLast(2).toString().lowercase())

    @JvmStatic
    fun isEndDirective(text: CharSequence) = text.contentEquals("end", ignoreCase = true)

    @JvmStatic
    fun isDataDirective(text: CharSequence) = AssemblerDirectives.dataDirectives.contains(text.toString().lowercase())

    @JvmStatic
    fun isPlainDirective(text: CharSequence) = AssemblerDirectives.plainDirectives.contains(text.toString().lowercase())

    @JvmStatic
    fun isOtherDirective(text: CharSequence) = AssemblerDirectives.otherDirectives.contains(text.toString().lowercase())

    @JvmStatic
    fun pushbackAssignment(text: CharSequence): Int {
        return text.length - text.indexOfAny(ASSIGNMENT_SEPARATORS)
    }

    @JvmStatic
    fun pushbackAfterFirstToken(text: CharSequence): Int {
        return text.length - text.indexOfAny(TOKEN_SEPARATORS)
    }

    @JvmStatic
    fun pushbackLabelColons(text: CharSequence): Int {
        return text.count { it == ':' }
    }

    @JvmStatic
    fun handleEolCommentWhitespace(lexer: _M68kLexer): IElementType {
        if (!lexer.eatOneWhitespace && lexer.lexerPrefs.spaceIntroducesComment) {
            lexer.yybegin(_M68kLexer.WAITEOL)
        }
        lexer.eatOneWhitespace = false
        return TokenType.WHITE_SPACE
    }

    @JvmStatic
    fun handleMacroMode(lexer: _M68kLexer): IElementType {
        if (lexer.lexerPrefs.macroParametersUnparsed) {
            lexer.yybegin(_M68kLexer.PLAINPARAMS)
        } else {
            lexer.yybegin(_M68kLexer.ASMOPS)
        }
        lexer.eatOneWhitespace = true
        return M68kTypes.MACRO_INVOCATION
    }

    @JvmStatic
    fun handleMacroLineEol(lexer: _M68kLexer): IElementType {
        if (++lexer.macroLines > lexer.lexerPrefs.maxLinesPerMacro) {
            lexer.yybegin(_M68kLexer.YYINITIAL)
            return TokenType.BAD_CHARACTER
        }
        lexer.yybegin(_M68kLexer.MACROLINE)
        return TokenType.WHITE_SPACE
    }

    fun unquoteString(string: String) = string.run {
        when {
            startsWith('"') -> removeSurrounding("\"")
            startsWith('\'') -> removeSurrounding("'")
            startsWith('<') -> removeSurrounding("<", ">")
            else -> this
        }
    }
}