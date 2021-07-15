package de.platon42.intellij.plugins.m68k.syntax

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import de.platon42.intellij.plugins.m68k.lexer.M68kLexer
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return M68kLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            M68kTypes.SEPARATOR -> arrayOf(SEPARATOR)
            M68kTypes.GLOBAL_LABEL_DEF, M68kTypes.GLOBAL_LABEL -> arrayOf(GLOBAL_LABEL)
            M68kTypes.LOCAL_LABEL_DEF, M68kTypes.LOCAL_LABEL_DEF -> arrayOf(LOCAL_LABEL)
            M68kTypes.SYMBOLDEF, M68kTypes.SYMBOL -> arrayOf(SYMBOL)
            M68kTypes.MNEMONIC, M68kTypes.ASM_OP -> arrayOf(MNEMONIC)
            M68kTypes.OPERAND_SIZE, M68kTypes.DATA_WIDTH, M68kTypes.ADDRESS_SIZE -> arrayOf(MNEMONIC)
            M68kTypes.ADDRESS_REGISTER, M68kTypes.AREG -> arrayOf(AREG)
            M68kTypes.DATA_REGISTER, M68kTypes.DREG -> arrayOf(DREG)
            M68kTypes.SPECIAL_REGISTER, M68kTypes.REG_USP, M68kTypes.REG_SR, M68kTypes.REG_CCR -> arrayOf(SPECIAL_REG)
            M68kTypes.PREPROCESSOR_DIRECTIVE -> arrayOf(PREPROCESSOR)
            M68kTypes.COMMENT -> arrayOf(COMMENT)
            M68kTypes.DECIMAL, M68kTypes.HEXADECIMAL, M68kTypes.OCTAL -> arrayOf(NUMBER)
            M68kTypes.STRINGLIT -> arrayOf(STRING)
            TokenType.BAD_CHARACTER -> arrayOf(BAD_CHARACTER)
            else -> emptyArray()
        }
    }

    companion object {
        val SEPARATOR = TextAttributesKey.createTextAttributesKey("M68K_SEPARATOR", DefaultLanguageHighlighterColors.COMMA)
        val GLOBAL_LABEL = TextAttributesKey.createTextAttributesKey("M68K_LOCAL_LABEL", DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
        val LOCAL_LABEL = TextAttributesKey.createTextAttributesKey("M68K_GLOBAL_LABEL", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE)
        val SYMBOL = TextAttributesKey.createTextAttributesKey("M68K_SYMBOL", DefaultLanguageHighlighterColors.IDENTIFIER)
        val MNEMONIC = TextAttributesKey.createTextAttributesKey("M68K_MNEMONIC", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val KEYWORD = TextAttributesKey.createTextAttributesKey("M68K_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val PREPROCESSOR = TextAttributesKey.createTextAttributesKey("M68K_PREPROCESSOR", DefaultLanguageHighlighterColors.KEYWORD)
        val STRING = TextAttributesKey.createTextAttributesKey("M68K_STRING", DefaultLanguageHighlighterColors.STRING)
        val NUMBER = TextAttributesKey.createTextAttributesKey("M68K_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val AREG = TextAttributesKey.createTextAttributesKey("M68K_AREG", DefaultLanguageHighlighterColors.CONSTANT)
        val DREG = TextAttributesKey.createTextAttributesKey("M68K_DREG", DefaultLanguageHighlighterColors.CONSTANT)
        val SPECIAL_REG = TextAttributesKey.createTextAttributesKey("M68K_SPECIALREG", DefaultLanguageHighlighterColors.CONSTANT)
        val COMMENT = TextAttributesKey.createTextAttributesKey("M68K_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("M68K_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
    }
}