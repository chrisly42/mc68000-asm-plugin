package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class M68kLocalLabelMixin(node: ASTNode) : ASTWrapperPsiElement(node), M68kLocalLabel {

}