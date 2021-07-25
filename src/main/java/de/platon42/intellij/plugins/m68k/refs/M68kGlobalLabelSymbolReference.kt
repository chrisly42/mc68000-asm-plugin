package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiSearchHelper
import com.intellij.psi.search.UsageSearchContext
import com.intellij.util.SmartList
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kLookupUtil
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference

class M68kGlobalLabelSymbolReference(element: M68kSymbolReference) :
    PsiPolyVariantReferenceBase<M68kSymbolReference>(element, TextRange(0, element.textLength)) {

    companion object {
        val INSTANCE = Resolver()

        fun findGlobalLabels(element: M68kSymbolReference, predicate: (M68kGlobalLabel) -> Boolean): List<M68kGlobalLabel> =
            M68kLookupUtil.findAllGlobalLabels(element.project).filter(predicate)

        fun findSymbolDefinitions(element: M68kSymbolReference, predicate: (M68kSymbolDefinition) -> Boolean): List<M68kSymbolDefinition> =
            M68kLookupUtil.findAllSymbolDefinitions(element.project).filter(predicate)
    }

    class Resolver : ResolveCache.PolyVariantResolver<M68kGlobalLabelSymbolReference> {
        override fun resolve(ref: M68kGlobalLabelSymbolReference, incompleteCode: Boolean): Array<ResolveResult> {
            val refName = ref.element.symbolName

            val project = ref.element.project
            val targets: MutableList<PsiElement> = SmartList()
            PsiSearchHelper.getInstance(project).processElementsWithWord(
                { elem, _ ->
                    when (elem) {
                        is M68kGlobalLabel, is M68kSymbolDefinition -> targets.add(elem)
                    }
                    true
                }, GlobalSearchScope.allScope(project),
                refName, UsageSearchContext.IN_CODE, true
            )

            val globalLabelMatches: Array<ResolveResult> = findGlobalLabels(ref.myElement) { it.name == refName }
                .map { PsiElementResolveResult(it) }
                .toTypedArray()
            if (globalLabelMatches.isNotEmpty()) return globalLabelMatches
            return targets
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
        return listOf(findGlobalLabels(element) { true }, findSymbolDefinitions(element) { true }).asSequence()
            .flatten()
            .map { LookupElementBuilder.createWithIcon(it) }
            .toList()
            .toTypedArray()
    }
}