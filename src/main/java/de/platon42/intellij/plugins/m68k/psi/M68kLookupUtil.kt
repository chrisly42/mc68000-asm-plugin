package de.platon42.intellij.plugins.m68k.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.stubs.M68kGlobalLabelStubIndex
import de.platon42.intellij.plugins.m68k.stubs.M68kSymbolDefinitionStubIndex

object M68kLookupUtil {

    fun findAllGlobalLabels(project: Project): List<M68kGlobalLabel> {
        val results: MutableList<M68kGlobalLabel> = ArrayList()
        StubIndex.getInstance().processAllKeys(M68kGlobalLabelStubIndex.KEY, project)
        {
            results.addAll(StubIndex.getElements(M68kGlobalLabelStubIndex.KEY, it, project, GlobalSearchScope.allScope(project), M68kGlobalLabel::class.java))
            true
        }
        return results
    }

    fun findAllGlobalLabels(file: M68kFile): List<M68kGlobalLabel> {
        val results: MutableList<M68kGlobalLabel> = ArrayList()
        StubIndex.getInstance().processAllKeys(
            M68kGlobalLabelStubIndex.KEY,
            {
                results.addAll(
                    StubIndex.getElements(
                        M68kGlobalLabelStubIndex.KEY,
                        it,
                        file.project,
                        GlobalSearchScope.fileScope(file),
                        M68kGlobalLabel::class.java
                    )
                )
                true
            }, GlobalSearchScope.fileScope(file), null
        )
        return results
    }

    fun findAllLocalLabels(globalLabel: M68kGlobalLabel): List<M68kLocalLabel> {
        val statement = PsiTreeUtil.getStubOrPsiParentOfType(globalLabel, M68kStatement::class.java)!!
        val results: MutableList<M68kLocalLabel> = ArrayList()
        var currentStatement = PsiTreeUtil.getNextSiblingOfType(statement, M68kStatement::class.java)
        while (currentStatement != null) {
            val child = currentStatement.firstChild
            if (child is M68kGlobalLabel) break
            if (child is M68kLocalLabel) results.add(child)
            currentStatement = PsiTreeUtil.getNextSiblingOfType(currentStatement, M68kStatement::class.java)
        }
        return results
    }

    fun findAllSymbolDefinitions(project: Project): List<M68kSymbolDefinition> {
        val results: MutableList<M68kSymbolDefinition> = ArrayList()
        StubIndex.getInstance().processAllKeys(M68kSymbolDefinitionStubIndex.KEY, project)
        {
            results.addAll(
                StubIndex.getElements(
                    M68kSymbolDefinitionStubIndex.KEY,
                    it,
                    project,
                    GlobalSearchScope.allScope(project),
                    M68kSymbolDefinition::class.java
                )
            )
            true
        }
        return results
    }

    fun findAllSymbolDefinitions(file: M68kFile): List<M68kSymbolDefinition> {
        val results: MutableList<M68kSymbolDefinition> = ArrayList()
        StubIndex.getInstance().processAllKeys(
            M68kSymbolDefinitionStubIndex.KEY,
            {
                results.addAll(
                    StubIndex.getElements(
                        M68kSymbolDefinitionStubIndex.KEY,
                        it,
                        file.project,
                        GlobalSearchScope.fileScope(file),
                        M68kSymbolDefinition::class.java
                    )
                )
                true
            }, GlobalSearchScope.fileScope(file), null
        )
        return results
    }
}