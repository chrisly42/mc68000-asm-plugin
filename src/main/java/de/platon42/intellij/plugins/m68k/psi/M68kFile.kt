package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import de.platon42.intellij.plugins.m68k.M68kFileType
import de.platon42.intellij.plugins.m68k.MC68000Language

class M68kFile(private val fileViewProvider: FileViewProvider) : PsiFileBase(fileViewProvider, MC68000Language.INSTANCE) {
    override fun toString(): String {
        val virtualFile = if (fileViewProvider.isEventSystemEnabled) fileViewProvider.virtualFile else null
        return "Assembly File: " + (virtualFile?.name ?: "<unknown>")
    }

    override fun getFileType() = M68kFileType.INSTANCE
}