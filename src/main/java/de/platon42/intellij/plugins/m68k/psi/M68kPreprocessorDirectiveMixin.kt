package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry

abstract class M68kPreprocessorDirectiveMixin(node: ASTNode) : ASTWrapperPsiElement(node), M68kPreprocessorDirective {

    override fun getReferences(): Array<PsiReference> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }
}