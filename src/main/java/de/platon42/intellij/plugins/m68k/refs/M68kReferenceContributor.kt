package de.platon42.intellij.plugins.m68k.refs

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import de.platon42.intellij.plugins.m68k.psi.M68kMacroCall
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference

class M68kReferenceContributor : PsiReferenceContributor() {

    companion object {
        val localLabelReferenceProvider = LocalLabelReferenceProvider()
        val globalLabelReferenceProvider = GlobalLabelSymbolReferenceProvider()
        val macroReferenceProvider = MacroReferenceProvider()
    }

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(M68kSymbolReference::class.java), localLabelReferenceProvider)
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(M68kSymbolReference::class.java), globalLabelReferenceProvider)
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(M68kMacroCall::class.java), macroReferenceProvider)
    }

    class LocalLabelReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            val symbolReference = element as M68kSymbolReference
            if (!symbolReference.isLocalLabelRef) return emptyArray()
            return arrayOf(M68kLocalLabelReference(symbolReference))
        }
    }

    class GlobalLabelSymbolReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            val symbolReference = element as M68kSymbolReference
            if (symbolReference.isLocalLabelRef) return emptyArray()
            return arrayOf(M68kGlobalLabelSymbolReference(symbolReference))
        }
    }

    class MacroReferenceProvider : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> =
            arrayOf(M68kMacroReference(element as M68kMacroCall))
    }
}
