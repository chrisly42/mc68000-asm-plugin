package de.platon42.intellij.plugins.m68k.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.M68kFileType

object M68kLookupUtil {

    fun findAllGlobalLabels(project: Project): List<M68kGlobalLabel> {
        return FileTypeIndex.getFiles(M68kFileType.INSTANCE, GlobalSearchScope.allScope(project)).asSequence()
            .map { PsiManager.getInstance(project).findFile(it) as M68kFile }
            .flatMap {
                val results: MutableList<M68kGlobalLabel> = ArrayList()
                var currentStatement = it.firstChild
                while (currentStatement != null) {
                    val child = currentStatement.firstChild
                    if (child is M68kGlobalLabel) results.add(child)
                    currentStatement = PsiTreeUtil.getNextSiblingOfType(currentStatement, M68kStatement::class.java)
                }
                results
            }
            .toList()
    }

    fun findAllSymbolDefinitions(project: Project): List<M68kSymbolDefinition> {
        return FileTypeIndex.getFiles(M68kFileType.INSTANCE, GlobalSearchScope.allScope(project)).asSequence()
            .map { PsiManager.getInstance(project).findFile(it) as M68kFile }
            .flatMap {
                val results: MutableList<M68kSymbolDefinition> = ArrayList()
                var currentStatement = it.firstChild
                while (currentStatement != null) {
                    val child = currentStatement.firstChild
                    if (child is M68kAssignment) results.add(child.firstChild as M68kSymbolDefinition)
                    currentStatement = PsiTreeUtil.getNextSiblingOfType(currentStatement, M68kStatement::class.java)
                }
                results
            }
            .toList()
    }
}