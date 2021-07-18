package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class M68kSymbolDefinitionMixin(node: ASTNode) : ASTWrapperPsiElement(node), M68kSymbolDefinition {

}