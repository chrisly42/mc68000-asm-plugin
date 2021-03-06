package de.platon42.intellij.plugins.m68k.psi.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.psi.*
import de.platon42.intellij.plugins.m68k.stubs.M68kGlobalLabelStubIndex
import de.platon42.intellij.plugins.m68k.stubs.M68kMacroDefinitionStubIndex
import de.platon42.intellij.plugins.m68k.stubs.M68kSymbolDefinitionStubIndex

object M68kLookupUtil {

    fun findAllGlobalLabels(project: Project): List<M68kGlobalLabel> {
        val results = ArrayList<M68kGlobalLabel>()
        StubIndex.getInstance().processAllKeys(M68kGlobalLabelStubIndex.KEY, project)
        {
            results.addAll(StubIndex.getElements(M68kGlobalLabelStubIndex.KEY, it, project, GlobalSearchScope.allScope(project), M68kGlobalLabel::class.java))
            true
        }
        return results
    }

    fun findAllGlobalLabels(file: M68kFile): List<M68kGlobalLabel> {
        val results = ArrayList<M68kGlobalLabel>()
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

    fun findAllGlobalLabelNames(project: Project): Collection<String> = StubIndex.getInstance().getAllKeys(M68kGlobalLabelStubIndex.KEY, project).toList()

    fun findAllLocalLabels(globalLabel: M68kGlobalLabel): List<M68kLocalLabel> {
        val statement = PsiTreeUtil.getStubOrPsiParentOfType(globalLabel, M68kStatement::class.java)!!
        val results = ArrayList<M68kLocalLabel>()
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
        val results = ArrayList<M68kSymbolDefinition>()
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
        val results = ArrayList<M68kSymbolDefinition>()
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

    fun findAllSymbolDefinitionNames(project: Project): Collection<String> = StubIndex.getInstance().getAllKeys(M68kSymbolDefinitionStubIndex.KEY, project)


    fun findAllMacroDefinitions(project: Project): List<M68kMacroDefinition> {
        val results = ArrayList<M68kMacroDefinition>()
        StubIndex.getInstance().processAllKeys(M68kMacroDefinitionStubIndex.KEY, project)
        {
            results.addAll(
                StubIndex.getElements(
                    M68kMacroDefinitionStubIndex.KEY,
                    it,
                    project,
                    GlobalSearchScope.allScope(project),
                    M68kMacroDefinition::class.java
                )
            )
            true
        }
        return results
    }

    fun findAllMacroDefinitions(file: M68kFile): List<M68kMacroDefinition> {
        val results = ArrayList<M68kMacroDefinition>()
        StubIndex.getInstance().processAllKeys(
            M68kMacroDefinitionStubIndex.KEY,
            {
                results.addAll(
                    StubIndex.getElements(
                        M68kMacroDefinitionStubIndex.KEY,
                        it,
                        file.project,
                        GlobalSearchScope.fileScope(file),
                        M68kMacroDefinition::class.java
                    )
                )
                true
            }, GlobalSearchScope.fileScope(file), null
        )
        return results
    }

    fun findAllMacroDefinitionNames(project: Project): Collection<String> = StubIndex.getInstance().getAllKeys(M68kMacroDefinitionStubIndex.KEY, project)
}