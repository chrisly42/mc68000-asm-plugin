package de.platon42.intellij.plugins.m68k.psi.utils

import de.platon42.intellij.plugins.m68k.psi.M68kMacroCall
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition

object M68kMacroExpansionUtil {

    fun expandMacro(macroDefinition: M68kMacroDefinition, macroCall: M68kMacroCall?): List<String> {
        val params = macroCall?.exprList?.map { it.text }?.toTypedArray() ?: emptyArray()

        if (params.isEmpty()) return macroDefinition.macroPlainLineList.map { it.text }

        val originalContent = macroDefinition.macroPlainLineList.joinToString("\n", transform = { it.text })
        var modifiedContent = originalContent
        for (param in params.withIndex()) {
            val paramRef = when (param.index) {
                in 0..8 -> "\\" + (param.index + 1)
                in 9..34 -> "\\" + ('a' + (param.index - 9))
                else -> "seriously?"
            }
            modifiedContent = modifiedContent.replace(paramRef, param.value)
        }
        return modifiedContent.split("\n")
    }
}