package de.platon42.intellij.plugins.m68k.lexer

import de.platon42.intellij.plugins.m68k.asm.AssemblerDirectives
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.mnemonics

object LexerUtil {

    private val ASSIGNMENT_SEPARATORS = charArrayOf(' ', '\t')

    @JvmStatic
    fun isAsmMnemonic(text: CharSequence) = mnemonics.contains(text.toString().lowercase())

    @JvmStatic
    fun isAsmMnemonicWithSize(text: CharSequence) = text.contains('.')
            && text.dropLast(1).endsWith('.')
            && mnemonics.contains(text.dropLast(2).toString().lowercase())

    @JvmStatic
    fun isDataDirective(text: CharSequence) = AssemblerDirectives.dataDirectives.contains(text.toString().lowercase())

    @JvmStatic
    fun isOtherDirective(text: CharSequence) = AssemblerDirectives.otherDirective.contains(text.toString().lowercase())

    @JvmStatic
    fun pushbackAssignment(text: CharSequence): Int {
        val spacePos = text.indexOfAny(ASSIGNMENT_SEPARATORS)
        if (spacePos > -1) {
            return text.length - spacePos
        }
        return text.length - text.indexOf('=')
    }
}