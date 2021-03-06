package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import de.platon42.intellij.plugins.m68k.asm.*
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.findMatchingInstructions
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.findMatchingOpMode
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.findMatchingOpModeIgnoringSize
import de.platon42.intellij.plugins.m68k.asm.M68kIsa.findSupportedOpSizes
import de.platon42.intellij.plugins.m68k.psi.M68kAddressModeUtil.getAddressModeForType
import de.platon42.intellij.plugins.m68k.psi.M68kAsmInstruction
import de.platon42.intellij.plugins.m68k.psi.M68kSpecialRegisterDirectAddressingMode
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil

class M68kSyntaxInspection : AbstractBaseM68kLocalInspectionTool() {

    companion object {
        private const val DISPLAY_NAME = "Assembly instruction validity"

        private const val INSTRUCTION_NOT_FOUND_MSG = "Instruction '#ref' not supported on selected cpu"
        private const val OPERANDS_UNEXPECTED_MSG_TEMPLATE = "No operands expected for '%s'"
        private const val SECOND_OP_UNEXPECTED_MSG_TEMPLATE = "Second operand '#ref' unexpected for '%s'"
        private const val UNSUPPORTED_ADDRESSING_MODE_MSG_TEMPLATE = "Unsupported addressing mode for '%s'"
        private const val UNSUPPORTED_ADDRESSING_MODE_OP1_MSG_TEMPLATE = "Unsupported addressing mode '#ref' for first operand of '%s'"
        private const val UNSUPPORTED_ADDRESSING_MODE_OP2_MSG_TEMPLATE = "Unsupported addressing mode '#ref' for second operand of '%s'"
        private const val UNSUPPORTED_ADDRESSING_MODE_FLIP_MSG_TEMPLATE = "Unsupported addressing modes for operands in this order for '%s'"
        private const val UNSUPPORTED_SIZE_UNSIZED_MSG_TEMPLATE = "Instruction '%s' is unsized"
        private const val UNSUPPORTED_SIZE_MSG_TEMPLATE = "Operation size '#ref' unsupported for '%s"
        private const val UNSUPPORTED_SIZE_HINT_MSG_TEMPLATE = "Operation size '#ref' unsupported (should be %s)"
    }

    override fun getDisplayName() = DISPLAY_NAME

    override fun checkAsmInstruction(asmInstruction: M68kAsmInstruction, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val asmOp = asmInstruction.asmOp
        val mnemonicWithSize = asmOp.text
        val isaData = findMatchingInstructions(asmOp.mnemonic)
        if (isaData.isEmpty()) {
            return arrayOf(
                manager.createProblemDescriptor(
                    asmOp,
                    INSTRUCTION_NOT_FOUND_MSG,
                    null as LocalQuickFix?,
                    ProblemHighlightType.ERROR,
                    isOnTheFly
                )
            )
        }
        if (asmInstruction.addressingModeList.isNotEmpty() && isaData.none { it.hasOps }) {
            return arrayOf(
                manager.createProblemDescriptor(
                    asmInstruction.addressingModeList.first(),
                    asmInstruction.addressingModeList.last(),
                    OPERANDS_UNEXPECTED_MSG_TEMPLATE.format(mnemonicWithSize),
                    ProblemHighlightType.ERROR,
                    isOnTheFly
                )
            )
        }
        val amOp1 = asmInstruction.addressingModeList.getOrNull(0)
        val amOp2 = asmInstruction.addressingModeList.getOrNull(1)
        val op1 = getAddressModeForType(amOp1)
        val op2 = getAddressModeForType(amOp2)
        val specialReg1 = (amOp1 as? M68kSpecialRegisterDirectAddressingMode)?.specialRegister?.text
        val specialReg2 = (amOp2 as? M68kSpecialRegisterDirectAddressingMode)?.specialRegister?.text
        val specialReg = specialReg1 ?: specialReg2
        val opSize = asmOp.opSize
        val matchingModeIsaData = findMatchingOpMode(isaData, op1, op2, opSize, specialReg)
        if (matchingModeIsaData.isEmpty()) {
            val matchingModeIsaDataIgnoringSize = findMatchingOpModeIgnoringSize(isaData, op1, op2, specialReg)
            if (matchingModeIsaDataIgnoringSize.isEmpty()) {
                val matchingModeIsaDataSwapped = findMatchingOpModeIgnoringSize(isaData, op2, op1, specialReg)
                val supportedModesOp1 = isaData.flatMap { it.modes.flatMap { am -> am.op1 ?: emptySet() } }.toSet()
                val supportedModesOp2 = isaData.flatMap { it.modes.flatMap { am -> am.op2 ?: emptySet() } }.toSet()

                if (matchingModeIsaDataSwapped.isNotEmpty()) {
                    return arrayOf(
                        manager.createProblemDescriptor(
                            asmInstruction,
                            UNSUPPORTED_ADDRESSING_MODE_FLIP_MSG_TEMPLATE.format(mnemonicWithSize),
                            null as LocalQuickFix?, // TODO add flipping quickfix
                            ProblemHighlightType.ERROR,
                            isOnTheFly
                        )
                    )
                }

                if ((op2 != null)) {
                    if (supportedModesOp2.isEmpty()) {
                        return arrayOf(
                            manager.createProblemDescriptor(
                                asmInstruction.addressingModeList[1],
                                SECOND_OP_UNEXPECTED_MSG_TEMPLATE.format(mnemonicWithSize),
                                null as LocalQuickFix?,
                                ProblemHighlightType.ERROR,
                                isOnTheFly
                            )
                        )
                    }
                    if (supportedModesOp1.contains(op1) && !supportedModesOp2.contains(op2)) {
                        return arrayOf(
                            manager.createProblemDescriptor(
                                asmInstruction.addressingModeList[1],
                                UNSUPPORTED_ADDRESSING_MODE_OP2_MSG_TEMPLATE.format(mnemonicWithSize),
                                null as LocalQuickFix?,
                                ProblemHighlightType.ERROR,
                                isOnTheFly
                            )
                        )
                    }
                    if (supportedModesOp2.contains(op2) && !supportedModesOp1.contains(op1)) {
                        return arrayOf(
                            manager.createProblemDescriptor(
                                asmInstruction.addressingModeList[0],
                                UNSUPPORTED_ADDRESSING_MODE_OP1_MSG_TEMPLATE.format(mnemonicWithSize),
                                null as LocalQuickFix?,
                                ProblemHighlightType.ERROR,
                                isOnTheFly
                            )
                        )
                    }
                }
                return arrayOf(
                    manager.createProblemDescriptor(
                        asmInstruction.addressingModeList.firstOrNull() ?: asmInstruction,
                        asmInstruction.addressingModeList.lastOrNull() ?: asmInstruction,
                        UNSUPPORTED_ADDRESSING_MODE_MSG_TEMPLATE.format(mnemonicWithSize),
                        ProblemHighlightType.ERROR,
                        isOnTheFly
                    )
                )
            }
            
            val supportedOpSizes = findSupportedOpSizes(matchingModeIsaDataIgnoringSize, op1, op2, specialReg)
            return arrayOf(
                when (supportedOpSizes) {
                    OP_UNSIZED ->
                        manager.createProblemDescriptor(
                            asmOp.operandSize ?: asmOp,
                            UNSUPPORTED_SIZE_UNSIZED_MSG_TEMPLATE.format(asmOp.mnemonic),
                            null as LocalQuickFix?, // TODO remove size quickfix?
                            ProblemHighlightType.ERROR,
                            isOnTheFly
                        )
                    OP_SIZE_B, OP_SIZE_W, OP_SIZE_L, OP_SIZE_WL, OP_SIZE_SBW ->
                        manager.createProblemDescriptor(
                            asmOp.operandSize ?: asmOp,
                            UNSUPPORTED_SIZE_HINT_MSG_TEMPLATE.format(M68kIsaUtil.findOpSizeDescription(supportedOpSizes)),
                            null as LocalQuickFix?, // TODO change size to .b?
                            ProblemHighlightType.ERROR,
                            isOnTheFly
                        )
                    else ->
                        manager.createProblemDescriptor(
                            asmOp.operandSize ?: asmOp,
                            UNSUPPORTED_SIZE_MSG_TEMPLATE.format(asmOp.mnemonic),
                            null as LocalQuickFix?, // TODO remove size quickfix?
                            ProblemHighlightType.ERROR,
                            isOnTheFly
                        )
                }
            )
        }
        return emptyArray()
    }
}