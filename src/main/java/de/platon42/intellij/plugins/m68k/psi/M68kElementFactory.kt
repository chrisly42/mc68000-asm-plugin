package de.platon42.intellij.plugins.m68k.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.M68kFileType.Companion.INSTANCE

object M68kElementFactory {

    fun createGlobalLabel(project: Project, label: String): M68kGlobalLabel {
        val file = createFile(project, label)
        return PsiTreeUtil.findChildOfType(file, M68kGlobalLabel::class.java)!!
    }

    fun createLocalLabel(project: Project, label: String): M68kLocalLabel {
        val file = createFile(project, label)
        return PsiTreeUtil.findChildOfType(file, M68kLocalLabel::class.java)!!
    }

    fun createFile(project: Project, content: String): M68kFile {
        return PsiFileFactory.getInstance(project).createFileFromText("dummy.m68k", INSTANCE, content) as M68kFile
    }
}