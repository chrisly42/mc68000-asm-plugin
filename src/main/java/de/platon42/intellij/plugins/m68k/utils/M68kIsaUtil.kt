package de.platon42.intellij.plugins.m68k.utils

import de.platon42.intellij.plugins.m68k.asm.*
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

    fun findOpSizeDescriptions(opSize: Int): Set<String> {
        return when (opSize) {
            OP_UNSIZED -> setOf("")
            OP_SIZE_B -> setOf(".b")
            OP_SIZE_W -> setOf(".w")
            OP_SIZE_L -> setOf(".l")
            OP_SIZE_S -> setOf(".s")
            OP_SIZE_BWL -> setOf(".b", ".w", ".l")
            OP_SIZE_WL -> setOf(".w", ".l")
            OP_SIZE_SBW -> setOf(".s", ".b", ".w")
            else -> setOf("?")
        }
    }
}