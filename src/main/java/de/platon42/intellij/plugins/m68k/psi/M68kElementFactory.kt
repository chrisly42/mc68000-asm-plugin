package de.platon42.intellij.plugins.m68k.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.M68kFileType.Companion.INSTANCE

object M68kElementFactory {

    fun createGlobalLabel(project: Project, label: String): M68kGlobalLabel {
        val file = createFile(project, "$label\n")
        return PsiTreeUtil.findChildOfType(file, M68kGlobalLabel::class.java)!!
    }

    fun createLocalLabel(project: Project, label: String): M68kLocalLabel {
        val file = createFile(project, "$label\n")
        return PsiTreeUtil.findChildOfType(file, M68kLocalLabel::class.java)!!
    }

    fun createSymbolDefinition(project: Project, label: String): M68kSymbolDefinition {
        val file = createFile(project, "$label=0\n")
        return PsiTreeUtil.findChildOfType(file, M68kSymbolDefinition::class.java)!!
    }

    fun createSymbolReference(project: Project, symbol: String): M68kSymbolReference {
        val file = createFile(project, " bra $symbol\n")
        return PsiTreeUtil.findChildOfType(file, M68kSymbolReference::class.java)!!
    }

    fun createFile(project: Project, content: String): M68kFile {
        return PsiFileFactory.getInstance(project).createFileFromText("dummy.m68k", INSTANCE, content) as M68kFile
    }
}