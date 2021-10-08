package de.platon42.intellij.plugins.m68k.psi

interface M68kWithBaseDisplacement : M68kAddressingMode {

    val baseDisplacement: M68kExpr?

    val baseDataWidth: M68kDataWidth?
}