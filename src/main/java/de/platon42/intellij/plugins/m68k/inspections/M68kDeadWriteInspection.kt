package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import de.platon42.intellij.plugins.m68k.asm.*
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.findMatchingInstructions
import de.platon42.intellij.plugins.m68k.psi.M68kAddressModeUtil
import de.platon42.intellij.plugins.m68k.psi.M68kAsmInstruction
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kStatement
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.checkIfInstructionUsesRegister
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.evaluateRegisterUse
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.findExactIsaDataAndAllowedAdrModeForInstruction
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.getConcreteTestedCcFromMnemonic

class M68kDeadWriteInspection : AbstractBaseM68kLocalInspectionTool() {

    companion object {
        private const val DISPLAY_NAME = "Dead writes to registers"

        private const val DEAD_WRITE_MSG = "Register %s is overwritten later without being used"
        private const val POSSIBLY_DEAD_WRITE_MSG = "Register %s is overwritten later (only CC evaluated?)"
    }

    override fun getDisplayName() = DISPLAY_NAME

    override fun checkAsmInstruction(asmInstruction: M68kAsmInstruction, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val asmOp = asmInstruction.asmOp
        if (asmInstruction.addressingModeList.isEmpty()) return emptyArray()

        val isaDataCandidates = findMatchingInstructions(asmOp.mnemonic)
        if (isaDataCandidates.isEmpty()) return emptyArray()
        val (_, adrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(asmInstruction) ?: return emptyArray()

        val opSize = M68kIsaUtil.getOpSizeOrDefault(asmInstruction.asmOp.opSize, adrMode)
        val rwm1 = M68kIsaUtil.modifyRwmWithOpsize((adrMode.modInfo ushr RWM_OP1_SHIFT) and RWM_OP_MASK, opSize)
        val rwm2 = if (asmInstruction.addressingModeList.size > 1) M68kIsaUtil.modifyRwmWithOpsize(
            (adrMode.modInfo ushr RWM_OP2_SHIFT) and RWM_OP_MASK, opSize
        ) else 0
        val regsWritten = M68kAddressModeUtil.getReadWriteModifyRegisters(asmInstruction.addressingModeList[0], rwm1).asSequence()
            .plus(M68kAddressModeUtil.getReadWriteModifyRegisters(asmInstruction.addressingModeList.getOrNull(1), rwm2))
            .plus(M68kAddressModeUtil.getOtherReadWriteModifyRegisters(adrMode.modInfo))
            .filter { (it.second and RWM_SET_L) > 0 }
            .distinct()
            .toList()

        val hints = SmartList<ProblemDescriptor>()
        for (regPair in regsWritten) {
            val register = regPair.first
            val rwm = regPair.second
            var currStatement = asmInstruction.parent as M68kStatement

            var ccModification = adrMode.affectedCc
            var ccOverwritten = false
            var ccTested = false
            var hasModification = false

            while (true) {
                currStatement = PsiTreeUtil.getNextSiblingOfType(currStatement, M68kStatement::class.java) ?: break
                val globalLabel = PsiTreeUtil.findChildOfType(currStatement, M68kGlobalLabel::class.java)
                if (globalLabel != null) break

                val currAsmInstruction = PsiTreeUtil.getChildOfType(currStatement, M68kAsmInstruction::class.java) ?: continue
                val (isaData, currAdrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(currAsmInstruction) ?: continue
                if (isaData.changesControlFlow) break
                val testedCc = getConcreteTestedCcFromMnemonic(currAsmInstruction.asmOp.mnemonic, isaData, adrMode)
                if (((testedCc and ccModification) > 0) && !ccOverwritten) ccTested = true
                if (currAdrMode.affectedCc != 0) ccOverwritten = true
                if (checkIfInstructionUsesRegister(currAsmInstruction, register)) {
                    val totalRwms = evaluateRegisterUse(currAsmInstruction, currAdrMode, register).reduce(Int::or)
                    if (totalRwms and RWM_READ_L > 0) break
                    if (totalRwms and RWM_MODIFY_L > 0) {
                        hasModification = true
                        ccOverwritten = false
                        ccModification = ccModification or currAdrMode.affectedCc
                    }
                    if (totalRwms and RWM_SET_L >= rwm) {
                        if (ccTested && hasModification) {
                            break
                        }
                        hints.add(
                            manager.createProblemDescriptor(
                                asmInstruction,
                                asmInstruction,
                                (if (ccTested) POSSIBLY_DEAD_WRITE_MSG else DEAD_WRITE_MSG).format(register.regname),
                                if (ccTested) ProblemHighlightType.WEAK_WARNING else ProblemHighlightType.WARNING,
                                isOnTheFly
                            )
                        )
                        break
                    }
                }
            }

        }
        return hints.toTypedArray()
    }
}