package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kStatement
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference

class M68kLocalLabelReference(element: M68kSymbolReference) : PsiPolyVariantReferenceBase<M68kSymbolReference>(element, TextRange(0, element.textLength)) {

    companion object {
        val INSTANCE = Resolver()

        fun findLocalLabels(element: M68kSymbolReference, predicate: (M68kLocalLabel) -> Boolean): List<M68kLocalLabel> {
            val statement = PsiTreeUtil.getStubOrPsiParentOfType(element, M68kStatement::class.java)!!
            val results: MutableList<M68kLocalLabel> = SmartList()
            // go backward
            var currentStatement = PsiTreeUtil.getPrevSiblingOfType(statement, M68kStatement::class.java)
            while (currentStatement != null) {
                val child = currentStatement.firstChild
                if (child is M68kGlobalLabel) break
                if (child is M68kLocalLabel && predicate.invoke(child)) results.add(child)
                currentStatement = PsiTreeUtil.getPrevSiblingOfType(currentStatement, M68kStatement::class.java)
            }
            // go forward
            currentStatement = statement
            while (currentStatement != null) {
                val child = currentStatement.firstChild
                if (child is M68kGlobalLabel) break
                if (child is M68kLocalLabel && predicate.invoke(child)) results.add(child)
                currentStatement = PsiTreeUtil.getNextSiblingOfType(currentStatement, M68kStatement::class.java)
            }
            return results
        }
    }

    class Resolver : ResolveCache.PolyVariantResolver<M68kLocalLabelReference> {
        override fun resolve(ref: M68kLocalLabelReference, incompleteCode: Boolean): Array<ResolveResult> {
            val refName = ref.element.symbolName

            return findLocalLabels(ref.myElement) { it.name == refName }
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
        return findLocalLabels(element) { true }.asSequence()
            .distinct()
            .map { LookupElementBuilder.createWithIcon(it) }
            .toList()
            .toTypedArray()
    }
}