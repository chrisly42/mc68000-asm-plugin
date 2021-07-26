package de.platon42.intellij.plugins.m68k.refs

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
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference

class M68kGlobalLabelSymbolReference(element: M68kSymbolReference) :
    PsiPolyVariantReferenceBase<M68kSymbolReference>(element, TextRange(0, element.textLength)) {

    companion object {
        val INSTANCE = Resolver()
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
            return targets
                .map(::PsiElementResolveResult)
                .toTypedArray()
        }
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> =
        ResolveCache.getInstance(element.project)
            .resolveWithCaching(this, INSTANCE, false, incompleteCode)

    override fun resolve(): PsiElement? = multiResolve(false).singleOrNull()?.element

    override fun getVariants(): Array<Any> = emptyArray()
}