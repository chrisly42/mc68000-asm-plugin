package de.platon42.intellij.plugins.m68k.refs

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.SmartList
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kNamedElement
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference
import de.platon42.intellij.plugins.m68k.stubs.M68kGlobalLabelStubIndex
import de.platon42.intellij.plugins.m68k.stubs.M68kSymbolDefinitionStubIndex

class M68kGlobalLabelSymbolReference(element: M68kSymbolReference) :
    PsiPolyVariantReferenceBase<M68kSymbolReference>(element, TextRange(0, element.textLength)) {

    companion object {
        val INSTANCE = Resolver()
    }

    class Resolver : ResolveCache.PolyVariantResolver<M68kGlobalLabelSymbolReference> {
        override fun resolve(ref: M68kGlobalLabelSymbolReference, incompleteCode: Boolean): Array<ResolveResult> {
            val refName = ref.element.symbolName
            val project = ref.element.project

            val targets: MutableList<M68kNamedElement> = SmartList()
            StubIndex.getInstance()
                .processElements(M68kGlobalLabelStubIndex.KEY, refName, project, GlobalSearchScope.allScope(project), M68kGlobalLabel::class.java)
                {
                    targets.add(it)
                    true
                }

            StubIndex.getInstance()
                .processElements(M68kSymbolDefinitionStubIndex.KEY, refName, project, GlobalSearchScope.allScope(project), M68kSymbolDefinition::class.java)
                {
                    targets.add(it)
                    true
                }

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