package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import de.platon42.intellij.plugins.m68k.psi.*

class M68kLocalLabelDefCompletionContributor : CompletionContributor() {

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.or(PlatformPatterns.psiElement(M68kTypes.LOCAL_LABEL_DEF), PlatformPatterns.psiElement(M68kTypes.GLOBAL_LABEL_DEF)),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                    var topLevelElement = parameters.originalFile.findElementAt(parameters.offset)
                    while (topLevelElement?.parent !is M68kFile) {
                        topLevelElement = topLevelElement?.parent ?: return
                    }
                    // TODO find out if we can cache this somehow
                    val affectedStatements = ArrayList<M68kStatement>()
                    val definedLocalLabels = HashSet<String>()
                    val referencedLocalLabels = HashSet<String>()
                    findUndefinedLocalLabels(topLevelElement, affectedStatements, definedLocalLabels, referencedLocalLabels) {
                        PsiTreeUtil.getNextSiblingOfType(it, M68kStatement::class.java)
                    }
                    findUndefinedLocalLabels(topLevelElement, affectedStatements, definedLocalLabels, referencedLocalLabels) {
                        PsiTreeUtil.getPrevSiblingOfType(it, M68kStatement::class.java)
                    }
                    referencedLocalLabels.removeAll(definedLocalLabels)
                    resultSet.addAllElements(
                        if (parameters.originalPosition?.text == ".") {
                            referencedLocalLabels.map { LookupElementBuilder.create(it.removePrefix(".")) }
                        } else {
                            referencedLocalLabels.map(LookupElementBuilder::create)
                        }
                    )
                }

                private fun findUndefinedLocalLabels(
                    topLevelElement: PsiElement,
                    affectedStatements: MutableList<M68kStatement>,
                    definedLocalLabels: MutableSet<String>,
                    referencedLocalLabels: MutableSet<String>,
                    direction: (topLevelElement: PsiElement) -> M68kStatement?
                ) {
                    var currStatement = topLevelElement
                    while (true) {
                        currStatement = direction.invoke(currStatement) ?: break
                        val globalLabel = PsiTreeUtil.findChildOfType(currStatement, M68kGlobalLabel::class.java)
                        if (globalLabel != null) break
                        affectedStatements.add(currStatement)
                        val localLabel = PsiTreeUtil.findChildOfType(currStatement, M68kLocalLabel::class.java)
                        if (localLabel != null) definedLocalLabels.add(localLabel.name!!)
                        val symbolReferences = PsiTreeUtil.findChildrenOfAnyType(currStatement, M68kSymbolReference::class.java)
                        if (symbolReferences.isNotEmpty()) {
                            referencedLocalLabels.addAll(
                                symbolReferences.filter(M68kSymbolReference::isLocalLabelRef).map(M68kSymbolReference::getSymbolName)
                            )
                        }
                    }
                }
            })
    }
}