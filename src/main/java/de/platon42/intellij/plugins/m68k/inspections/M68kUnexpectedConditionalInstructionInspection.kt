package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.findMatchingInstructions
import de.platon42.intellij.plugins.m68k.psi.*
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.findExactIsaDataAndAllowedAdrModeForInstruction

class M68kUnexpectedConditionalInstructionInspection : AbstractBaseM68kLocalInspectionTool() {

    companion object {
        private const val DISPLAY_NAME = "Unaffected condition codes before conditional instruction"

        private const val UNAFFECTED_CONDITION_CODES_MSG_TEMPLATE = "Condition codes unaffected by instruction (%s - %s)"
    }

    override fun getDisplayName() = DISPLAY_NAME

    override fun checkAsmInstruction(asmInstruction: M68kAsmInstruction, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val asmOp = asmInstruction.asmOp
        if (asmInstruction.addressingModeList.isEmpty()) return emptyArray()

        val isaDataCandidates = findMatchingInstructions(asmOp.mnemonic)
        if (isaDataCandidates.isEmpty()) return emptyArray()
        val (isaData, adrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(asmInstruction) ?: return emptyArray()
        if ((adrMode.affectedCc > 0) || (adrMode.testedCc > 0) || isaData.changesControlFlow) return emptyArray()

        var currStatement = asmInstruction.parent as M68kStatement
        while (true) {
            currStatement = PsiTreeUtil.getNextSiblingOfType(currStatement, M68kStatement::class.java) ?: break
            val globalLabel = PsiTreeUtil.findChildOfType(currStatement, M68kGlobalLabel::class.java)
            if (globalLabel != null) break
            if (PsiTreeUtil.findChildOfAnyType(currStatement, M68kMacroCall::class.java, M68kPreprocessorDirective::class.java) != null) break
            val currAsmInstruction = PsiTreeUtil.getChildOfType(currStatement, M68kAsmInstruction::class.java) ?: continue
            val (currIsaData, currAdrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(currAsmInstruction) ?: break
            val testedCc = M68kIsaUtil.getConcreteTestedCcFromMnemonic(currAsmInstruction.asmOp.mnemonic, currIsaData, currAdrMode)
            if (testedCc == 0) break

            return arrayOf(
                manager.createProblemDescriptor(
                    asmInstruction,
                    asmInstruction,
                    UNAFFECTED_CONDITION_CODES_MSG_TEMPLATE.format(isaData.mnemonic, isaData.description),
                    ProblemHighlightType.WARNING,
                    isOnTheFly
                )
            )
        }
        return emptyArray()
    }
}