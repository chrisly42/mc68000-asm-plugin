package de.platon42.intellij.plugins.m68k.utils

import de.platon42.intellij.plugins.m68k.asm.*
import de.platon42.intellij.plugins.m68k.asm.ConditionCode.Companion.getCcFromMnemonic
import de.platon42.intellij.plugins.m68k.psi.M68kAddressModeUtil
import de.platon42.intellij.plugins.m68k.psi.M68kAsmInstruction
import de.platon42.intellij.plugins.m68k.psi.M68kSpecialRegisterDirectAddressingMode

object M68kIsaUtil {

    // TODO if more than one result, do a check and consolidate
    fun findExactIsaDataForInstruction(asmInstruction: M68kAsmInstruction): IsaData? =
        findIsaDataForInstruction(asmInstruction).firstOrNull()?.first

    // TODO if more than one result, do a check and consolidate
    fun findExactIsaDataAndAllowedAdrModeForInstruction(asmInstruction: M68kAsmInstruction): Pair<IsaData, AllowedAdrMode>? {
        val (isaData, adrModeList) = findIsaDataForInstruction(asmInstruction).firstOrNull() ?: return null
        return isaData to adrModeList.first()
    }

    fun findIsaDataForInstruction(asmInstruction: M68kAsmInstruction): List<Pair<IsaData, List<AllowedAdrMode>>> {
        val isaDataCandidates = M68kIsa.findMatchingInstructions(asmInstruction.asmOp.mnemonic)
        if (isaDataCandidates.isEmpty()) return emptyList()

        val amOp1 = asmInstruction.addressingModeList.getOrNull(0)
        val amOp2 = asmInstruction.addressingModeList.getOrNull(1)
        val op1 = M68kAddressModeUtil.getAddressModeForType(amOp1)
        val op2 = M68kAddressModeUtil.getAddressModeForType(amOp2)
        val specialReg1 = (amOp1 as? M68kSpecialRegisterDirectAddressingMode)?.specialRegister?.text
        val specialReg2 = (amOp2 as? M68kSpecialRegisterDirectAddressingMode)?.specialRegister?.text
        val specialReg = specialReg1 ?: specialReg2
        val opSize = asmInstruction.asmOp.opSize
        val matchedIsaData =
            M68kIsa.findMatchingOpMode(isaDataCandidates, op1, op2, opSize, specialReg)
        return matchedIsaData.map { it to M68kIsa.findMatchingAddressMode(it.modes, op1, op2, opSize, specialReg) }
    }

    fun checkIfInstructionUsesRegister(instruction: M68kAsmInstruction, register: Register): Boolean {
        if (instruction.addressingModeList.isEmpty()) {
            return false
        }
        return instruction.addressingModeList.any { aml -> M68kAddressModeUtil.getReadWriteModifyRegisters(aml, 0).any { it.first == register } }
    }

    fun evaluateRegisterUse(asmInstruction: M68kAsmInstruction, adrMode: AllowedAdrMode, register: Register): List<Int> {
        val opSize = getOpSizeOrDefault(asmInstruction.asmOp.opSize, adrMode)

        val rwm1 = modifyRwmWithOpsize((adrMode.modInfo ushr RWM_OP1_SHIFT) and RWM_OP_MASK, opSize)
        val rwm2 = if (asmInstruction.addressingModeList.size > 1) modifyRwmWithOpsize((adrMode.modInfo ushr RWM_OP2_SHIFT) and RWM_OP_MASK, opSize) else 0
        return M68kAddressModeUtil.getReadWriteModifyRegisters(asmInstruction.addressingModeList[0], rwm1).asSequence()
            .plus(M68kAddressModeUtil.getReadWriteModifyRegisters(asmInstruction.addressingModeList.getOrNull(1), rwm2))
            .plus(M68kAddressModeUtil.getOtherReadWriteModifyRegisters(adrMode.modInfo))
            .filter { it.first == register }
            .map { it.second }
            .distinct()
            .toList()
    }

    fun getConcreteTestedCcFromMnemonic(mnemonic: String, isaData: IsaData, adrMode: AllowedAdrMode) =
        if (isaData.conditionCodes.isNotEmpty()) getCcFromMnemonic(isaData.mnemonic, mnemonic).testedCc else adrMode.testedCc

    fun getOpSizeOrDefault(opSize: Int, adrMode: AllowedAdrMode): Int {
        if (opSize == OP_UNSIZED && (adrMode.size != OP_UNSIZED)) {
            return if ((adrMode.size and OP_SIZE_W) == OP_SIZE_W) {
                OP_SIZE_W
            } else {
                adrMode.size
            }
        }
        return opSize
    }

    fun modifyRwmWithOpsize(rwm: Int, opSize: Int): Int {
        if (opSize == OP_UNSIZED) return rwm
        return when (rwm) {
            RWM_READ_OPSIZE -> when (opSize) {
                OP_SIZE_B -> RWM_READ_B
                OP_SIZE_W -> RWM_READ_W
                OP_SIZE_L -> RWM_READ_L
                else -> rwm
            }
            RWM_MODIFY_OPSIZE -> when (opSize) {
                OP_SIZE_B -> RWM_MODIFY_B
                OP_SIZE_W -> RWM_MODIFY_W
                OP_SIZE_L -> RWM_MODIFY_L
                else -> rwm
            }
            RWM_SET_OPSIZE -> when (opSize) {
                OP_SIZE_B -> RWM_SET_B
                OP_SIZE_W -> RWM_SET_W
                OP_SIZE_L -> RWM_SET_L
                else -> rwm
            }
            else -> rwm
        }
    }

    fun findOpSizeDescription(opSize: Int): String {
        return when (opSize) {
            OP_UNSIZED -> ""
            OP_SIZE_B -> ".b"
            OP_SIZE_W -> ".w"
            OP_SIZE_L -> ".l"
            OP_SIZE_S -> ".s"
            OP_SIZE_BWL -> ".b|.w|.l"
            OP_SIZE_WL -> ".w|.l"
            OP_SIZE_SBW -> ".s|.b|.w"
            else -> "?"
        }
    }

    fun findOpSizeDescriptions(opSize: Int): List<String> {
        return when (opSize) {
            OP_UNSIZED -> listOf("")
            OP_SIZE_B -> listOf(".b")
            OP_SIZE_W -> listOf(".w")
            OP_SIZE_L -> listOf(".l")
            OP_SIZE_S -> listOf(".s")
            OP_SIZE_BWL -> listOf(".b", ".w", ".l")
            OP_SIZE_WL -> listOf(".w", ".l")
            OP_SIZE_SBW -> listOf(".s", ".b", ".w")
            else -> listOf("?")
        }
    }
}