package de.platon42.intellij.plugins.m68k.psi

interface M68kWithDisplacement : M68kAddressingMode {

    val displacement: M68kExpr?
}