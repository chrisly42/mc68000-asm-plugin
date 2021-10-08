package de.platon42.intellij.plugins.m68k.psi

interface M68kWithOuterDisplacement : M68kAddressingMode {

    val outerDisplacement: M68kExpr?

    val outerDataWidth: M68kDataWidth?
}