package de.platon42.intellij.plugins.m68k.lexer

import de.platon42.intellij.plugins.m68k.asm.M68kIsa.mnemonics

object LexerUtil {

    private val ASSIGNMENT_SEPARATORS = charArrayOf(' ', '\t')

    @JvmStatic
    fun isAsmMnemonic(text: CharSequence) = mnemonics.contains(text.toString().lowercase())

    @JvmStatic
    fun pushbackAssignment(text: CharSequence): Int {
        val spacePos = text.indexOfAny(ASSIGNMENT_SEPARATORS)
        if (spacePos > -1) {
            return text.length - spacePos
        }
        return text.length - 1 - text.indexOf('=')
    }
}