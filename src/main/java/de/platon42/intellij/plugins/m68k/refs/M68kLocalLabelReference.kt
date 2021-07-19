package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kStatement
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference

class M68kLocalLabelReference(element: M68kSymbolReference) : PsiPolyVariantReferenceBase<M68kSymbolReference>(element, TextRange(0, element.textLength)) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val refName = myElement.symbolName

        return findLocalLabels { it.name == refName }
            .map { PsiElementResolveResult(it) }
            .toTypedArray()
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return resolveResults.singleOrNull()?.element
    }

    override fun getVariants(): Array<Any> {
        return findLocalLabels { true }
            .map { LookupElementBuilder.createWithIcon(it) }
            .toTypedArray()
    }

    private fun findLocalLabels(predicate: (M68kLocalLabel) -> Boolean): List<M68kLocalLabel> {
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