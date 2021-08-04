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
import de.platon42.intellij.plugins.m68k.psi.M68kMacroCall
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition
import de.platon42.intellij.plugins.m68k.stubs.M68kMacroDefinitionStubIndex

class M68kMacroReference(element: M68kMacroCall) :
    PsiPolyVariantReferenceBase<M68kMacroCall>(element, TextRange(0, element.macroName.length)) {

    companion object {
        val INSTANCE = Resolver()
    }

    class Resolver : ResolveCache.PolyVariantResolver<M68kMacroReference> {
        override fun resolve(ref: M68kMacroReference, incompleteCode: Boolean): Array<ResolveResult> {
            val macroName = ref.element.macroName
            val project = ref.element.project

            val targets = SmartList<M68kMacroDefinition>()
            StubIndex.getInstance()
                .processElements(M68kMacroDefinitionStubIndex.KEY, macroName, project, GlobalSearchScope.allScope(project), M68kMacroDefinition::class.java)
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