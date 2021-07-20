package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import de.platon42.intellij.plugins.m68k.M68kIcons
import javax.swing.Icon

abstract class M68kSymbolDefinitionMixin(node: ASTNode) : ASTWrapperPsiElement(node), M68kSymbolDefinition {

    override fun getPresentation(): ItemPresentation? {
        return PresentationData(name, containingFile?.name, getIcon(0), null)
    }

    override fun getIcon(flags: Int): Icon? {
        return M68kIcons.SYMBOL_DEF
    }
}