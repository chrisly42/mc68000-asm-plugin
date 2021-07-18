package de.platon42.intellij.plugins.m68k.refs

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kRefExpr

class M68kReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(M68kRefExpr::class.java), object : PsiReferenceProvider() {
            override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
                return emptyArray()
            }
        })
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(M68kGlobalLabel::class.java), object : PsiReferenceProvider() {
            override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
                return emptyArray()
            }
        })
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(M68kLocalLabel::class.java), object : PsiReferenceProvider() {
            override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
                return emptyArray()
            }
        })
    }
}