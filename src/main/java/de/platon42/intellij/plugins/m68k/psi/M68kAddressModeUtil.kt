package de.platon42.intellij.plugins.m68k.psi

import de.platon42.intellij.plugins.m68k.asm.*

object M68kAddressModeUtil {
    fun getAddressModeForType(addressingMode: M68kAddressingMode?): AddressMode? {
        if (addressingMode == null) return null
        return when (addressingMode) {
            is M68kImmediateData -> AddressMode.IMMEDIATE_DATA
            is M68kAddressRegisterIndirectAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT
            is M68kAddressRegisterIndirectPostIncAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC
            is M68kAddressRegisterIndirectPreDecAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC
            is M68kAddressRegisterIndirectWithDisplacementNewAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT
            is M68kAddressRegisterIndirectWithDisplacementOldAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT
            is M68kAddressRegisterIndirectWithIndexNewAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX
            is M68kAddressRegisterIndirectWithIndexOldAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX
            is M68kProgramCounterIndirectWithDisplacementNewAddressingMode -> AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT
            is M68kProgramCounterIndirectWithDisplacementOldAddressingMode -> AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT
            is M68kProgramCounterIndirectWithIndexNewAddressingMode -> AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
            is M68kProgramCounterIndirectWithIndexOldAddressingMode -> AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
            is M68kSpecialRegisterDirectAddressingMode -> AddressMode.SPECIAL_REGISTER_DIRECT
            is M68kDataRegisterDirectAddressingMode -> AddressMode.DATA_REGISTER_DIRECT
            is M68kAddressRegisterDirectAddressingMode -> AddressMode.ADDRESS_REGISTER_DIRECT
            is M68kRegisterListAddressingMode -> AddressMode.REGISTER_LIST
            is M68kAbsoluteAddressAddressingMode -> AddressMode.ABSOLUTE_ADDRESS
            else -> throw IllegalArgumentException("Unknown addressing mode $addressingMode")
        }
    }

    fun getReadWriteModifyRegisters(addressingMode: M68kAddressingMode?, rwm: Int): Set<Pair<Register, Int>> {
        if (addressingMode == null) return emptySet()
        return when (addressingMode) {
            is M68kImmediateData -> emptySet()
            is M68kAddressRegisterIndirectPostIncAddressingMode -> setOf(Register.getRegFromName(addressingMode.addressRegister.text) to RWM_MODIFY_OP1_L)
            is M68kAddressRegisterIndirectPreDecAddressingMode -> setOf(Register.getRegFromName(addressingMode.addressRegister.text) to RWM_MODIFY_OP1_L)
            is M68kWithAddressRegisterIndirect -> {
                if (addressingMode is M68kWithIndexRegister) {
                    setOf(
                        Register.getRegFromName(addressingMode.addressRegister.text) to RWM_READ_OP1_L,
                        Register.getRegFromName(addressingMode.indexRegister.text) to if (addressingMode.hasLongWidth()) RWM_READ_OP1_L else RWM_READ_OP1_W
                    )
                } else {
                    setOf(Register.getRegFromName(addressingMode.addressRegister.text) to RWM_READ_OP1_L)
                }
            }
            is M68kWithIndexRegister -> setOf(Register.getRegFromName(addressingMode.indexRegister.text) to if (addressingMode.hasLongWidth()) RWM_READ_OP1_L else RWM_READ_OP1_W)
            is M68kProgramCounterIndirectWithDisplacementNewAddressingMode -> emptySet()
            is M68kProgramCounterIndirectWithDisplacementOldAddressingMode -> emptySet()
            is M68kSpecialRegisterDirectAddressingMode -> emptySet()
            is M68kDataRegisterDirectAddressingMode -> setOf(Register.getRegFromName(addressingMode.dataRegister.text) to rwm)
            is M68kAddressRegisterDirectAddressingMode -> setOf(Register.getRegFromName(addressingMode.addressRegister.text) to rwm)
            is M68kRegisterListAddressingMode -> addressingMode.registers.map { it to rwm }.toSet()
            is M68kAbsoluteAddressAddressingMode -> emptySet()
            else -> throw IllegalArgumentException("Unknown addressing mode $addressingMode")
        }
    }
}