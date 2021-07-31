package de.platon42.intellij.plugins.m68k.psi

import de.platon42.intellij.plugins.m68k.asm.AddressMode

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
}