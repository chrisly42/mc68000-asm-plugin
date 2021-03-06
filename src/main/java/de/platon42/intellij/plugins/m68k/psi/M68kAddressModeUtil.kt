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
            is M68kAddressRegisterIndirectWithIndexNewAddressingMode -> if (hasScale(addressingMode)) AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_SCALED_INDEX else AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX
            is M68kAddressRegisterIndirectWithIndexOldAddressingMode -> if (hasScale(addressingMode)) AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_SCALED_INDEX else AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX
            is M68kAddressRegisterIndirectWithIndexBaseDisplacementAddressingMode -> AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_BASE_DISPLACEMENT
            is M68kProgramCounterIndirectWithDisplacementNewAddressingMode -> AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT
            is M68kProgramCounterIndirectWithDisplacementOldAddressingMode -> AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT
            is M68kProgramCounterIndirectWithIndexNewAddressingMode -> if (hasScale(addressingMode)) AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_SCALED_INDEX else AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
            is M68kProgramCounterIndirectWithIndexOldAddressingMode -> if (hasScale(addressingMode)) AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_SCALED_INDEX else AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
            is M68kProgramCounterIndirectWithIndexBaseDisplacementAddressingMode -> AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_BASE_DISPLACEMENT
            is M68kMemoryIndirectAddressingMode -> AddressMode.MEMORY_INDIRECT
            is M68kMemoryIndirectPreIndexedAddressingMode -> AddressMode.MEMORY_INDIRECT_PREINDEXED
            is M68kMemoryIndirectPostIndexedAddressingMode -> AddressMode.MEMORY_INDIRECT_POSTINDEXED
            is M68kProgramCounterMemoryIndirectAddressingMode -> AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT
            is M68kProgramCounterMemoryIndirectPreIndexedAddressingMode -> AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT_PREINDEXED
            is M68kProgramCounterMemoryIndirectPostIndexedAddressingMode -> AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT_POSTINDEXED
            is M68kSpecialRegisterDirectAddressingMode -> AddressMode.SPECIAL_REGISTER_DIRECT
            is M68kDataRegisterDirectAddressingMode -> AddressMode.DATA_REGISTER_DIRECT
            is M68kAddressRegisterDirectAddressingMode -> AddressMode.ADDRESS_REGISTER_DIRECT
            is M68kRegisterListAddressingMode -> AddressMode.REGISTER_LIST
            is M68kAbsoluteAddressAddressingMode -> AddressMode.ABSOLUTE_ADDRESS
            else -> throw IllegalArgumentException("Unknown addressing mode $addressingMode")
        }
    }

    // TODO add evaluation of constant expressions and allow scale == 1 as false
    private fun hasScale(addressingMode: M68kWithIndexRegister) = addressingMode.indexRegister.indexScale != null

    fun getOtherReadWriteModifyRegisters(rwm: Int): List<Pair<Register, Int>> {
        if (rwm and RWM_MODIFY_STACK > 0) {
            return listOf(Register.A7 to RWM_MODIFY_L)
        }
        return emptyList()
    }

    fun getReadWriteModifyRegisters(addressingMode: M68kAddressingMode?, rwm: Int): List<Pair<Register, Int>> {
        if (addressingMode == null) return emptyList()
        return when (addressingMode) {
            is M68kAddressRegisterIndirectPostIncAddressingMode -> listOf(Register.getRegFromName(addressingMode.addressRegister.text) to (RWM_READ_L or RWM_MODIFY_L))
            is M68kAddressRegisterIndirectPreDecAddressingMode -> listOf(Register.getRegFromName(addressingMode.addressRegister.text) to (RWM_READ_L or RWM_MODIFY_L))
            is M68kWithAddressRegisterIndirect -> listOf(Register.getRegFromName(addressingMode.addressRegister.text) to RWM_READ_L)
            is M68kWithOptionalAddressRegisterIndirect -> if (addressingMode.addressRegister != null) listOf(Register.getRegFromName(addressingMode.addressRegister!!.text) to RWM_READ_L) else emptyList()
            is M68kDataRegisterDirectAddressingMode -> listOf(Register.getRegFromName(addressingMode.dataRegister.text) to rwm)
            is M68kAddressRegisterDirectAddressingMode -> listOf(Register.getRegFromName(addressingMode.addressRegister.text) to rwm)
            is M68kRegisterListAddressingMode -> addressingMode.registers.map { it to rwm }
            else -> emptyList()
        }.plus(
            when (addressingMode) {
                is M68kWithIndexRegister -> listOf(Register.getRegFromName(addressingMode.indexRegister.register.text) to if (addressingMode.indexRegister.isLongWidth) RWM_READ_L else RWM_READ_W)
                is M68kWithOptionalIndexRegister -> if (addressingMode.indexRegister != null) listOf(Register.getRegFromName(addressingMode.indexRegister!!.register.text) to if (addressingMode.indexRegister!!.isLongWidth) RWM_READ_L else RWM_READ_W) else emptyList()
                else -> emptyList()
            }
        )
    }
}