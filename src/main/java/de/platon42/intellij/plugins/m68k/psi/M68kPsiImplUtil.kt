package de.platon42.intellij.plugins.m68k.psi

import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import de.platon42.intellij.plugins.m68k.psi.M68kElementFactory.createGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kElementFactory.createLocalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kElementFactory.createMacroDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kElementFactory.createSymbolDefinition

object M68kPsiImplUtil {

    // Global Label
    @JvmStatic
    fun getName(element: M68kGlobalLabel): String? = element.firstChild.text

    @JvmStatic
    fun setName(element: M68kGlobalLabel, name: String): PsiElement {
        val nameNode = element.node.findChildByType(M68kTypes.GLOBAL_LABEL_DEF)
        if (nameNode != null) {
            val newGlobalLabel = createGlobalLabel(element.project, name)
            element.node.replaceChild(nameNode, newGlobalLabel.firstChild.node)
        }
        return element
    }

    @JvmStatic
    fun getNameIdentifier(element: M68kGlobalLabel): PsiElement = element.firstChild

    // Local Label
    @JvmStatic
    fun getName(element: M68kLocalLabel): String? = element.firstChild.text

    @JvmStatic
    fun setName(element: M68kLocalLabel, name: String): PsiElement {
        if (!(name.startsWith(".") || name.endsWith("$"))) throw IncorrectOperationException("local label must start with '.' or end with '$'")
        val nameNode = element.node.findChildByType(M68kTypes.LOCAL_LABEL_DEF)
        if (nameNode != null) {
            val newLocalLabel = createLocalLabel(element.project, name)
            element.node.replaceChild(nameNode, newLocalLabel.firstChild.node)
        }
        return element
    }

    @JvmStatic
    fun getNameIdentifier(element: M68kLocalLabel): PsiElement = element.firstChild


    // Symbol Definition
    @JvmStatic
    fun getName(element: M68kSymbolDefinition): String? = element.firstChild.text

    @JvmStatic
    fun setName(element: M68kSymbolDefinition, name: String): PsiElement {
        val nameNode = element.node.findChildByType(M68kTypes.SYMBOLDEF)
        if (nameNode != null) {
            val newSymbolDefinition = createSymbolDefinition(element.project, name)
            element.node.replaceChild(nameNode, newSymbolDefinition.firstChild.node)
        }
        return element
    }

    @JvmStatic
    fun getNameIdentifier(element: M68kSymbolDefinition): PsiElement = element.firstChild


    // Symbol Reference
    @JvmStatic
    fun getSymbolName(element: M68kSymbolReference): String = element.firstChild.text

    @JvmStatic
    fun isLocalLabelRef(element: M68kSymbolReference): Boolean {
        val text = element.firstChild.text
        return text.startsWith('.') || text.endsWith('$')
    }


    // Macro Definition
    @JvmStatic
    fun getName(element: M68kMacroDefinition): String? = element.macroNameDefinition.firstChild.text

    @JvmStatic
    fun setName(element: M68kMacroDefinition, name: String): PsiElement {
        val nameNode = element.macroNameDefinition
        val newMacroDefinition = createMacroDefinition(element.project, name)
        nameNode.replace(newMacroDefinition.firstChild)
        return element
    }

    @JvmStatic
    fun getNameIdentifier(element: M68kMacroDefinition): PsiElement = element.firstChild


    // Macro Call
    @JvmStatic
    fun getMacroName(element: M68kMacroCall): String = element.firstChild.text

}