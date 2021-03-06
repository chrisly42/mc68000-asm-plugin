package de.platon42.intellij.plugins.m68k.syntax

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.project.Project
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import de.platon42.intellij.plugins.m68k.lexer.M68kLexer
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs
import de.platon42.intellij.plugins.m68k.psi.M68kTypes
import de.platon42.intellij.plugins.m68k.settings.M68kProjectSettings

class M68kSyntaxHighlighter(val project: Project?) : SyntaxHighlighterBase() {
    private val settings = project?.getService(M68kProjectSettings::class.java)

    override fun getHighlightingLexer(): Lexer {
        return M68kLexer(settings?.settings ?: M68kLexerPrefs()) // Use some defaults
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            M68kTypes.SEPARATOR -> arrayOf(SEPARATOR)
            M68kTypes.COLON -> arrayOf(COLON)
            M68kTypes.GLOBAL_LABEL_DEF -> arrayOf(GLOBAL_LABEL)
            M68kTypes.LOCAL_LABEL_DEF -> arrayOf(LOCAL_LABEL)
            M68kTypes.SYMBOLDEF -> arrayOf(SYMBOL_DEF)
            M68kTypes.SYMBOL -> arrayOf(SYMBOL_REF)
            M68kTypes.CURRENT_PC_SYMBOL, M68kTypes.PC -> arrayOf(PROGRAM_COUNTER)
            M68kTypes.MNEMONIC -> arrayOf(MNEMONIC)
            M68kTypes.MACRO_TAG, M68kTypes.MACRO_END_TAG -> arrayOf(MACRO_PREPROCESSOR)
            M68kTypes.MACRO_LINE -> arrayOf(MACRO_LINE)
            M68kTypes.MACRO_INVOCATION -> arrayOf(MACRO_CALL)
            M68kTypes.MACRO_NAME -> arrayOf(MACRO_NAME_DEF)
            M68kTypes.DATA_DIRECTIVE -> arrayOf(DATA_PREPROCESSOR)
            M68kTypes.OTHER_DIRECTIVE, M68kTypes.EQU -> arrayOf(OTHER_PREPROCESSOR)
            M68kTypes.OPSIZE_BS -> arrayOf(DATA_WIDTH_BS)
            M68kTypes.OPSIZE_W -> arrayOf(DATA_WIDTH_W)
            M68kTypes.OPSIZE_L -> arrayOf(DATA_WIDTH_L)
            M68kTypes.AREG -> arrayOf(AREG)
            M68kTypes.REG_SP -> arrayOf(STACK_POINTER)
            M68kTypes.DREG -> arrayOf(DREG)
            M68kTypes.REG_USP, M68kTypes.REG_SR, M68kTypes.REG_CCR, M68kTypes.REG_VBR, M68kTypes.REG_SFC, M68kTypes.REG_DFC -> arrayOf(SPECIAL_REG)
            M68kTypes.COMMENT -> arrayOf(COMMENT)
            M68kTypes.DECIMAL, M68kTypes.HEXADECIMAL, M68kTypes.OCTAL -> arrayOf(NUMBER)
            M68kTypes.STRINGLIT -> arrayOf(STRING)
            TokenType.BAD_CHARACTER -> arrayOf(BAD_CHARACTER)
            else -> emptyArray()
        }
    }

    companion object {
        val GLOBAL_LABEL = TextAttributesKey.createTextAttributesKey("MC68000_LOCAL_LABEL", DefaultLanguageHighlighterColors.LABEL)
        val LOCAL_LABEL = TextAttributesKey.createTextAttributesKey("MC68000_GLOBAL_LABEL", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
        val SEPARATOR = TextAttributesKey.createTextAttributesKey("MC68000_SEPARATOR", DefaultLanguageHighlighterColors.COMMA)
        val COLON = TextAttributesKey.createTextAttributesKey("MC68000_COLON", DefaultLanguageHighlighterColors.DOT)
        val SYMBOL_DEF = TextAttributesKey.createTextAttributesKey("MC68000_SYMBOLDEF", DefaultLanguageHighlighterColors.CONSTANT)
        val SYMBOL_REF = TextAttributesKey.createTextAttributesKey("MC68000_SYMBOLREF", DefaultLanguageHighlighterColors.IDENTIFIER)
        val MNEMONIC = TextAttributesKey.createTextAttributesKey("MC68000_MNEMONIC", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val MACRO_PREPROCESSOR = TextAttributesKey.createTextAttributesKey("MC68000_MACRO_PREPROCESSOR", DefaultLanguageHighlighterColors.INSTANCE_METHOD)
        val MACRO_NAME_DEF = TextAttributesKey.createTextAttributesKey("MC68000_MACRO_NAME_DEF", DefaultLanguageHighlighterColors.METADATA)
        val MACRO_LINE = TextAttributesKey.createTextAttributesKey("MC68000_MACRO_LINE", DefaultLanguageHighlighterColors.METADATA)
        val MACRO_CALL = TextAttributesKey.createTextAttributesKey("MC68000_MACRO_CALL", DefaultLanguageHighlighterColors.STATIC_METHOD)
        val DATA_WIDTH_BS = TextAttributesKey.createTextAttributesKey("MC68000_DATA_WIDTH_BS", DefaultLanguageHighlighterColors.DOT)
        val DATA_WIDTH_L = TextAttributesKey.createTextAttributesKey("MC68000_DATA_WIDTH_W", DefaultLanguageHighlighterColors.DOT)
        val DATA_WIDTH_W = TextAttributesKey.createTextAttributesKey("MC68000_DATA_WIDTH_L", DefaultLanguageHighlighterColors.DOT)
        val DATA_PREPROCESSOR = TextAttributesKey.createTextAttributesKey("MC68000_DATA_PREPROCESSOR", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val OTHER_PREPROCESSOR = TextAttributesKey.createTextAttributesKey("MC68000_OTHER_PREPROCESSOR", DefaultLanguageHighlighterColors.KEYWORD)
        val STRING = TextAttributesKey.createTextAttributesKey("MC68000_STRING", DefaultLanguageHighlighterColors.STRING)
        val NUMBER = TextAttributesKey.createTextAttributesKey("MC68000_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val AREG = TextAttributesKey.createTextAttributesKey("MC68000_AREG", DefaultLanguageHighlighterColors.PARAMETER)
        val STACK_POINTER = TextAttributesKey.createTextAttributesKey("MC68000_SP", DefaultLanguageHighlighterColors.PARAMETER)
        val DREG = TextAttributesKey.createTextAttributesKey("MC68000_DREG", DefaultLanguageHighlighterColors.PARAMETER)
        val SPECIAL_REG = TextAttributesKey.createTextAttributesKey("MC68000_SPECIALREG", DefaultLanguageHighlighterColors.PARAMETER)
        val PROGRAM_COUNTER = TextAttributesKey.createTextAttributesKey("MC68000_PROGRAM_COUNTER", DefaultLanguageHighlighterColors.PARAMETER)
        val COMMENT = TextAttributesKey.createTextAttributesKey("MC68000_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("MC68000_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
    }
}