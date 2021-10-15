package de.platon42.intellij.plugins.m68k.lexer

data class M68kLexerPrefs(
    var spaceIntroducesComment: Boolean = false,
    var maxLinesPerMacro: Int = 250,
    var macroSectionUnparsed: Boolean = false,
    var macroParametersUnparsed: Boolean = true,

    var maxShortDocumentationLines: Int = 5,
    var maxLongDocumentationLines: Int = 100,
)