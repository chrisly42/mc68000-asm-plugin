package de.platon42.intellij.plugins.m68k.psi

interface M68kWithOptionalAddressRegisterIndirect : M68kAddressingMode {

    val addressRegister: M68kAddressRegister?
}