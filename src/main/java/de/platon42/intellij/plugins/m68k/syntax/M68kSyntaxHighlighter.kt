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
            M68kTypes.LOCAL_LABEL_DEF, M68kTypes.LOCAL_LABEL -> arrayOf(LOCAL_LABEL)
            M68kTypes.SYMBOLDEF -> arrayOf(SYMBOLDEF)
            M68kTypes.SYMBOL -> arrayOf(SYMBOL)
            M68kTypes.MNEMONIC -> arrayOf(MNEMONIC)
            M68kTypes.MACRO_INVOKATION -> arrayOf(MACRO_CALL)
            M68kTypes.DATA_DIRECTIVE -> arrayOf(DATA_PREPROCESSOR)
            M68kTypes.OTHER_DIRECTIVE, M68kTypes.EQU -> arrayOf(OTHER_PREPROCESSOR)
            M68kTypes.OPSIZE_BS -> arrayOf(DATA_WIDTH_BS)
            M68kTypes.OPSIZE_W -> arrayOf(DATA_WIDTH_W)
            M68kTypes.OPSIZE_L -> arrayOf(DATA_WIDTH_L)
            M68kTypes.ADDRESS_REGISTER, M68kTypes.AREG -> arrayOf(AREG)
            M68kTypes.DATA_REGISTER, M68kTypes.DREG -> arrayOf(DREG)
            M68kTypes.SPECIAL_REGISTER, M68kTypes.REG_USP, M68kTypes.REG_SR, M68kTypes.REG_CCR -> arrayOf(SPECIAL_REG)
            M68kTypes.COMMENT -> arrayOf(COMMENT)
            M68kTypes.DECIMAL, M68kTypes.HEXADECIMAL, M68kTypes.OCTAL -> arrayOf(NUMBER)
            M68kTypes.STRINGLIT -> arrayOf(STRING)
            TokenType.BAD_CHARACTER -> arrayOf(BAD_CHARACTER)
            else -> emptyArray()
        }
    }

    companion object {
        val GLOBAL_LABEL = TextAttributesKey.createTextAttributesKey("M68K_LOCAL_LABEL", DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
        val LOCAL_LABEL = TextAttributesKey.createTextAttributesKey("M68K_GLOBAL_LABEL", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE)
        val SEPARATOR = TextAttributesKey.createTextAttributesKey("M68K_SEPARATOR", DefaultLanguageHighlighterColors.COMMA)
        val SYMBOLDEF = TextAttributesKey.createTextAttributesKey("M68K_SYMBOLDEF", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val SYMBOL = TextAttributesKey.createTextAttributesKey("M68K_SYMBOL", DefaultLanguageHighlighterColors.IDENTIFIER)
        val MNEMONIC = TextAttributesKey.createTextAttributesKey("M68K_MNEMONIC", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val MACRO_CALL = TextAttributesKey.createTextAttributesKey("M68K_MACRO_CALL", DefaultLanguageHighlighterColors.STATIC_METHOD)
        val DATA_WIDTH_BS = TextAttributesKey.createTextAttributesKey("M68K_DATA_WIDTH_BS", DefaultLanguageHighlighterColors.DOT)
        val DATA_WIDTH_L = TextAttributesKey.createTextAttributesKey("M68K_DATA_WIDTH_W", DefaultLanguageHighlighterColors.DOT)
        val DATA_WIDTH_W = TextAttributesKey.createTextAttributesKey("M68K_DATA_WIDTH_L", DefaultLanguageHighlighterColors.DOT)
        val DATA_PREPROCESSOR = TextAttributesKey.createTextAttributesKey("M68K_DATA_PREPROCESSOR", DefaultLanguageHighlighterColors.CONSTANT)
        val OTHER_PREPROCESSOR = TextAttributesKey.createTextAttributesKey("M68K_OTHER_PREPROCESSOR", DefaultLanguageHighlighterColors.KEYWORD)
        val STRING = TextAttributesKey.createTextAttributesKey("M68K_STRING", DefaultLanguageHighlighterColors.STRING)
        val NUMBER = TextAttributesKey.createTextAttributesKey("M68K_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val AREG = TextAttributesKey.createTextAttributesKey("M68K_AREG", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL)
        val DREG = TextAttributesKey.createTextAttributesKey("M68K_DREG", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL)
        val SPECIAL_REG = TextAttributesKey.createTextAttributesKey("M68K_SPECIALREG", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL)
        val COMMENT = TextAttributesKey.createTextAttributesKey("M68K_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("M68K_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
    }
}