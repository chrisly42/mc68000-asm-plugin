package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import de.platon42.intellij.plugins.m68k.M68kIcons
import javax.swing.Icon

abstract class M68kLocalLabelMixin(node: ASTNode) : ASTWrapperPsiElement(node), M68kLocalLabel {

    override fun getIcon(flags: Int): Icon? {
        return M68kIcons.LOCAL_LABEL
    }
}