package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.asm.RWM_OP1_SHIFT
import de.platon42.intellij.plugins.m68k.asm.RWM_OP2_SHIFT
import de.platon42.intellij.plugins.m68k.asm.Register.Companion.getRegFromName
import de.platon42.intellij.plugins.m68k.psi.M68kAddressRegister
import de.platon42.intellij.plugins.m68k.psi.M68kAddressingMode
import de.platon42.intellij.plugins.m68k.psi.M68kAsmInstruction
import de.platon42.intellij.plugins.m68k.psi.M68kDataRegister
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.findExactIsaDataAndAllowedAdrModeForInstruction

class M68kRegisterFlowDocumentationProvider : AbstractDocumentationProvider() {
    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        if (element is M68kDataRegister || element is M68kAddressRegister) {
            val register = getRegFromName(element.text)
            val addressingMode = PsiTreeUtil.getParentOfType(element, M68kAddressingMode::class.java) ?: return null
            val asmInstruction = PsiTreeUtil.getParentOfType(addressingMode, M68kAsmInstruction::class.java) ?: return null
            val (isaData, adrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(asmInstruction) ?: return "unknown instruction"

            val opShift = if (asmInstruction.addressingModeList[0] == addressingMode) RWM_OP1_SHIFT else RWM_OP2_SHIFT

            return register.regname
        }
        return null
    }

    override fun getCustomDocumentationElement(editor: Editor, file: PsiFile, contextElement: PsiElement?, targetOffset: Int): PsiElement? {
        if (contextElement == null) return null
        if (contextElement is M68kDataRegister || contextElement is M68kAddressRegister) return contextElement
        val parent = contextElement.parent ?: return null
        if (parent is M68kDataRegister || parent is M68kAddressRegister) return parent
        return null
    }
}