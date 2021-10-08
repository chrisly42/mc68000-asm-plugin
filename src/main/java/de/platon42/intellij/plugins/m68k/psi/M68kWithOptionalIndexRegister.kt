package de.platon42.intellij.plugins.m68k.psi

interface M68kWithOptionalIndexRegister : M68kAddressingMode {

    val indexRegister: M68kIndexRegister?
}