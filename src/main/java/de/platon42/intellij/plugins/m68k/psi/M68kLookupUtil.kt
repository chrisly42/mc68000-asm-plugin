package de.platon42.intellij.plugins.m68k.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.M68kFileType

object M68kLookupUtil {

    fun findAllGlobalLabels(project: Project): List<M68kGlobalLabel> {
        return getFiles(project)
            .flatMap(::findAllGlobalLabels)
            .toList()
    }

    fun findAllGlobalLabels(file: M68kFile): List<M68kGlobalLabel> {
        val results: MutableList<M68kGlobalLabel> = ArrayList()
        var currentStatement = file.firstChild
        while (currentStatement != null) {
            val child = currentStatement.firstChild
            if (child is M68kGlobalLabel) results.add(child)
            currentStatement = PsiTreeUtil.getNextSiblingOfType(currentStatement, M68kStatement::class.java)
        }
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
        return getFiles(project)
            .flatMap(::findAllSymbolDefinitions)
            .toList()
    }

    fun findAllSymbolDefinitions(file: M68kFile): List<M68kSymbolDefinition> {
        val results: MutableList<M68kSymbolDefinition> = ArrayList()
        var currentStatement = file.firstChild
        while (currentStatement != null) {
            val child = currentStatement.firstChild
            if (child is M68kAssignment) results.add(child.firstChild as M68kSymbolDefinition)
            currentStatement = PsiTreeUtil.getNextSiblingOfType(currentStatement, M68kStatement::class.java)
        }
        return results
    }

    private fun getFiles(project: Project) =
        FileTypeIndex.getFiles(M68kFileType.INSTANCE, GlobalSearchScope.allScope(project)).asSequence()
            .map { PsiManager.getInstance(project).findFile(it) as M68kFile }
}