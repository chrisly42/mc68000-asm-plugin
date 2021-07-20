package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.search.GlobalSearchScope
import de.platon42.intellij.plugins.m68k.psi.M68kLookupUtil
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference

class M68kSymbolDefinitionReference(element: M68kSymbolReference) :
    PsiPolyVariantReferenceBase<M68kSymbolReference>(element, TextRange(0, element.textLength)) {

    companion object {
        val INSTANCE = Resolver()

        fun findSymbolDefinitions(element: M68kSymbolReference, predicate: (M68kSymbolDefinition) -> Boolean): List<M68kSymbolDefinition> {
            return M68kLookupUtil.findAllSymbolDefinitions(element.project).filter(predicate)
        }

        private fun getCurrentFileSearchScope(element: PsiElement): GlobalSearchScope {
            return GlobalSearchScope.fileScope(element.containingFile.originalFile)
        }
    }

    class Resolver : ResolveCache.PolyVariantResolver<M68kSymbolDefinitionReference> {
        override fun resolve(ref: M68kSymbolDefinitionReference, incompleteCode: Boolean): Array<ResolveResult> {
            val refName = ref.element.symbolName

            return findSymbolDefinitions(ref.myElement) { it.name == refName }
                .map { PsiElementResolveResult(it) }
                .toTypedArray()
        }
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return ResolveCache.getInstance(element.project)
            .resolveWithCaching(this, INSTANCE, false, incompleteCode)
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return resolveResults.singleOrNull()?.element
    }

    override fun getVariants(): Array<Any> {
        return findSymbolDefinitions(element) { true }
            .map { LookupElementBuilder.createWithIcon(it) }
            .toTypedArray()
    }
}