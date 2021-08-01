package de.platon42.intellij.plugins.m68k.psi

interface M68kWithIndexRegister {

    val indexRegister: M68kRegister

    val indexWidth: M68kDataWidth?

    fun hasLongWidth(): Boolean {
        return indexWidth?.text.equals(".l", ignoreCase = true)
    }
}