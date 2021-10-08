package de.platon42.intellij.plugins.m68k.asm

import java.util.*

enum class Machine {
    MC68000,
    MC68010,
    MC68020,
    MC68030,
    MC68040,
    MC68060
}

private val MC68000_ONLY = EnumSet.of(Machine.MC68000)
private val MC68020_PLUS = EnumSet.range(Machine.MC68020, Machine.MC68060)
private val ALL_MACHINES = EnumSet.allOf(Machine::class.java)

enum class Register(val regname: String, val num: Int) {
    D0("d0", 0),
    D1("d1", 1),
    D2("d2", 2),
    D3("d3", 3),
    D4("d4", 4),
    D5("d5", 5),
    D6("d6", 6),
    D7("d7", 7),
    A0("a0", 0),
    A1("a1", 1),
    A2("a2", 2),
    A3("a3", 3),
    A4("a4", 4),
    A5("a5", 5),
    A6("a6", 6),
    A7("a7", 7);

    companion object {
        private val NAME_TO_REG_MAP = values().associateBy { it.regname }.plus("sp" to A7)

        fun getRegFromName(regname: String) = NAME_TO_REG_MAP[regname.lowercase()]!!
    }
}

enum class AddressMode(val description: String, val syntax: String) {
    DATA_REGISTER_DIRECT("data register direct", "Dn"),
    ADDRESS_REGISTER_DIRECT("address register direct", "An"),
    ADDRESS_REGISTER_INDIRECT("address register indirect", "(An)"),
    ADDRESS_REGISTER_INDIRECT_POST_INC("address register indirect with postincrement", "(An)+"),
    ADDRESS_REGISTER_INDIRECT_PRE_DEC("address register indirect with predecrement", "-(An)"),
    ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT("address register indirect with displacement", "(d16,An)"),
    PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT("program counter indirect with displacement", "(d16,PC)"),
    ADDRESS_REGISTER_INDIRECT_WITH_INDEX("address register indirect with index", "(d8,An,Xn)"),
    ADDRESS_REGISTER_INDIRECT_WITH_SCALED_INDEX("address register indirect with scaled index", "(d8,An,Xn*s)"),
    ADDRESS_REGISTER_INDIRECT_WITH_BASE_DISPLACEMENT("address register indirect with index (base displacement)", "(bd,An,Xn*s)"),
    PROGRAM_COUNTER_INDIRECT_WITH_INDEX("program counter indirect with index", "(d8,PC,Xn)"),
    PROGRAM_COUNTER_INDIRECT_WITH_SCALED_INDEX("program counter indirect with scaled index", "(d8,PC,Xn*s)"),
    PROGRAM_COUNTER_INDIRECT_WITH_BASE_DISPLACEMENT("program counter indirect with index (base displacement)", "(bd,PC,Xn*s)"),
    MEMORY_INDIRECT("memory indirect", "([bd,An],od)"),
    MEMORY_INDIRECT_POSTINDEXED("memory indirect postindexed", "([bd,An],Xn*s,od)"),
    MEMORY_INDIRECT_PREINDEXED("memory indirect preindexed", "([bd,An,Xn*s],od)"),
    PROGRAM_COUNTER_MEMORY_INDIRECT("program counter memory indirect", "([bd,PC],od)"),
    PROGRAM_COUNTER_MEMORY_INDIRECT_POSTINDEXED("program counter memory indirect postindexed", "([bd,PC],Xn*s,od)"),
    PROGRAM_COUNTER_MEMORY_INDIRECT_PREINDEXED("program counter memory indirect preindexed", "([bd,PC,Xn*s],od)"),
    SPECIAL_REGISTER_DIRECT("special register", "sr|ccr|usp|vbr"),
    REGISTER_LIST("register list", "list"),
    IMMEDIATE_DATA("immediate", "#<xxx>"),
    ABSOLUTE_ADDRESS("absolute short/long", "(xxx).w|l")
}

const val OP_UNSIZED = 0
const val OP_SIZE_B = 1
const val OP_SIZE_W = 2
const val OP_SIZE_L = 4
const val OP_SIZE_S = 8

const val OP_SIZE_BWL = (OP_SIZE_B or OP_SIZE_W or OP_SIZE_L)
const val OP_SIZE_WL = (OP_SIZE_W or OP_SIZE_L)
const val OP_SIZE_SBW = (OP_SIZE_B or OP_SIZE_S or OP_SIZE_W)

const val RWM_OP_MASK = 0xfff
const val RWM_SIZE_MASK = 0x00e

const val RWM_SET_OPSIZE = 0x008
const val RWM_SET_B = 0x009
const val RWM_SET_W = 0x00b
const val RWM_SET_L = 0x00f

const val RWM_READ_OPSIZE = 0x800
const val RWM_READ_B = 0x900
const val RWM_READ_W = 0xb00
const val RWM_READ_L = 0xf00
const val RWM_READ_SHIFT = 8

const val RWM_MODIFY_OPSIZE = 0x080
const val RWM_MODIFY_B = 0x090
const val RWM_MODIFY_W = 0x0b0
const val RWM_MODIFY_L = 0x0f0
const val RWM_MODIFY_SHIFT = 4

const val RWM_OP1_SHIFT = 0
const val RWM_OP2_SHIFT = 12

const val RWM_READ_OP1_OPSIZE = RWM_READ_OPSIZE shl RWM_OP1_SHIFT
const val RWM_READ_OP1_B = RWM_READ_B shl RWM_OP1_SHIFT
const val RWM_READ_OP1_W = RWM_READ_W shl RWM_OP1_SHIFT
const val RWM_READ_OP1_L = RWM_READ_L shl RWM_OP1_SHIFT

const val RWM_MODIFY_OP1_OPSIZE = RWM_MODIFY_OPSIZE shl RWM_OP1_SHIFT
const val RWM_MODIFY_OP1_B = RWM_MODIFY_B shl RWM_OP1_SHIFT
const val RWM_MODIFY_OP1_W = RWM_MODIFY_W shl RWM_OP1_SHIFT
const val RWM_MODIFY_OP1_L = RWM_MODIFY_L shl RWM_OP1_SHIFT

const val RWM_SET_OP1_OPSIZE = RWM_SET_OPSIZE shl RWM_OP1_SHIFT
const val RWM_SET_OP1_B = RWM_SET_B shl RWM_OP1_SHIFT
const val RWM_SET_OP1_W = RWM_SET_W shl RWM_OP1_SHIFT
const val RWM_SET_OP1_L = RWM_SET_L shl RWM_OP1_SHIFT

const val RWM_READ_OP2_OPSIZE = RWM_READ_OPSIZE shl RWM_OP2_SHIFT
const val RWM_READ_OP2_B = RWM_READ_B shl RWM_OP2_SHIFT
const val RWM_READ_OP2_W = RWM_READ_W shl RWM_OP2_SHIFT
const val RWM_READ_OP2_L = RWM_READ_L shl RWM_OP2_SHIFT

const val RWM_MODIFY_OP2_OPSIZE = RWM_MODIFY_OPSIZE shl RWM_OP2_SHIFT
const val RWM_MODIFY_OP2_B = RWM_MODIFY_B shl RWM_OP2_SHIFT
const val RWM_MODIFY_OP2_W = RWM_MODIFY_W shl RWM_OP2_SHIFT
const val RWM_MODIFY_OP2_L = RWM_MODIFY_L shl RWM_OP2_SHIFT

const val RWM_SET_OP2_OPSIZE = RWM_SET_OPSIZE shl RWM_OP2_SHIFT
const val RWM_SET_OP2_B = RWM_SET_B shl RWM_OP2_SHIFT
const val RWM_SET_OP2_W = RWM_SET_W shl RWM_OP2_SHIFT
const val RWM_SET_OP2_L = RWM_SET_L shl RWM_OP2_SHIFT

const val RWM_MODIFY_STACK = 0x1000000

data class AllowedAdrMode(
    val op1: Set<AddressMode>? = null,
    val op2: Set<AddressMode>? = null,
    val size: Int = OP_SIZE_BWL,
    val specialReg: String? = null,
    val modInfo: Int = 0,
    val affectedCc: Int = 0,
    val testedCc: Int = 0
)

data class IsaData(
    val mnemonic: String,
    val description: String,
    val machine: Set<Machine> = MC68000_ONLY,
    val altMnemonics: List<String> = emptyList(),
    val conditionCodes: List<String> = emptyList(),
    val id: String = mnemonic,
    val isPrivileged: Boolean = false,
    val hasOps: Boolean = true,
    val modes: List<AllowedAdrMode> = listOf(AllowedAdrMode()),
    val changesControlFlow: Boolean = false
)

object M68kIsa {

    private val NO_OPS_UNSIZED = listOf(AllowedAdrMode(size = OP_UNSIZED))

    private val ALL_68000_MODES = setOf(
        AddressMode.DATA_REGISTER_DIRECT,
        AddressMode.ADDRESS_REGISTER_DIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
        AddressMode.ABSOLUTE_ADDRESS,
        AddressMode.IMMEDIATE_DATA,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX,
    )

    private val ALL_68020_MODES = ALL_68000_MODES.plus(
        setOf(
            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_SCALED_INDEX,
            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_BASE_DISPLACEMENT,
            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_SCALED_INDEX,
            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_BASE_DISPLACEMENT,
            AddressMode.MEMORY_INDIRECT,
            AddressMode.MEMORY_INDIRECT_POSTINDEXED,
            AddressMode.MEMORY_INDIRECT_PREINDEXED,
            AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT,
            AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT_POSTINDEXED,
            AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT_PREINDEXED
        )
    )

    private val ALL_EXCEPT_IMMEDIATE_AND_PC_REL = setOf(
        AddressMode.DATA_REGISTER_DIRECT,
        AddressMode.ADDRESS_REGISTER_DIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
        AddressMode.ABSOLUTE_ADDRESS
    )

    private val ALL_68020_MODES_EXCEPT_IMMEDIATE_AND_PC_REL = ALL_EXCEPT_IMMEDIATE_AND_PC_REL.plus(
        setOf(
            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_SCALED_INDEX,
            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_BASE_DISPLACEMENT,
            AddressMode.MEMORY_INDIRECT,
            AddressMode.MEMORY_INDIRECT_POSTINDEXED,
            AddressMode.MEMORY_INDIRECT_PREINDEXED,
        )
    )

    private val ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL = setOf(
        AddressMode.DATA_REGISTER_DIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
        AddressMode.ABSOLUTE_ADDRESS
    )

    private val ALL_EXCEPT_AREG_AND_IMMEDIATE = setOf(
        AddressMode.DATA_REGISTER_DIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
        AddressMode.ABSOLUTE_ADDRESS,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX,
    )

    private val ALL_EXCEPT_AREG = setOf(
        AddressMode.DATA_REGISTER_DIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
        AddressMode.ABSOLUTE_ADDRESS,
        AddressMode.IMMEDIATE_DATA,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX,
    )

    private val ALL_EXCEPT_AREG_AND_DREG = setOf(
        AddressMode.ADDRESS_REGISTER_INDIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
        AddressMode.ABSOLUTE_ADDRESS,
        AddressMode.IMMEDIATE_DATA,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX,
    )

    private val INDIRECT_MODES = setOf(
        AddressMode.ADDRESS_REGISTER_INDIRECT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
        AddressMode.ABSOLUTE_ADDRESS
    )

    private val AREG_ONLY = setOf(AddressMode.ADDRESS_REGISTER_DIRECT)
    private val DREG_ONLY = setOf(AddressMode.DATA_REGISTER_DIRECT)

    private val ADD_SUB_MODES = listOf(
        AllowedAdrMode(
            ALL_EXCEPT_AREG,
            setOf(AddressMode.DATA_REGISTER_DIRECT),
            modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
            affectedCc = cc("C****")
        ),
        AllowedAdrMode(
            setOf(AddressMode.ADDRESS_REGISTER_DIRECT),
            setOf(AddressMode.DATA_REGISTER_DIRECT),
            OP_SIZE_WL,
            modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
            affectedCc = cc("C****")
        ),
        AllowedAdrMode(
            setOf(AddressMode.DATA_REGISTER_DIRECT),
            INDIRECT_MODES,
            modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
            affectedCc = cc("C****")
        ),
    )

    private val ADDQ_SUBQ_MODES = listOf(
        AllowedAdrMode(
            setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, modInfo = RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("C****")
        ),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), AREG_ONLY, size = OP_SIZE_WL, modInfo = RWM_MODIFY_OP2_L, affectedCc = cc("C****"))
    )

    private val ADDX_SUBX_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, DREG_ONLY, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("C*A**"), testedCc = cc("?-?--")),
        AllowedAdrMode(
            setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC),
            setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC),
            modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
            affectedCc = cc("C*A**"), testedCc = cc("?-?--")
        )
    )

    private val ASD_LSD_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, DREG_ONLY, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("*****")),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, modInfo = RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("*****")),
        AllowedAdrMode(INDIRECT_MODES, null, size = OP_SIZE_W, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("*****")),
        // not an official address mode, but supported by assembler (implicit #1)
        AllowedAdrMode(DREG_ONLY, null, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("*****"))
    )

    private val ROD_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, DREG_ONLY, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("-**0*")),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, modInfo = RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("-**0*")),
        AllowedAdrMode(INDIRECT_MODES, null, size = OP_SIZE_W, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("-**0*")),
        // not an official address mode, but supported by assembler (implicit #1)
        AllowedAdrMode(DREG_ONLY, null, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("-**0*")),
    )

    private val ROXD_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, DREG_ONLY, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("***0*"), testedCc = cc("?----")),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, modInfo = RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("***0*"), testedCc = cc("?----")),
        AllowedAdrMode(INDIRECT_MODES, null, size = OP_SIZE_W, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("***0*"), testedCc = cc("?----")),
        // not an official address mode, but supported by assembler (implicit #1)
        AllowedAdrMode(DREG_ONLY, null, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("***0*"), testedCc = cc("?----")),
    )

    private val BCHG_BCLR_BSET_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, DREG_ONLY, OP_SIZE_L, modInfo = RWM_READ_OP1_B or RWM_MODIFY_OP2_L, affectedCc = cc("--*--")),
        AllowedAdrMode(DREG_ONLY, INDIRECT_MODES, OP_SIZE_B, modInfo = RWM_READ_OP1_B or RWM_MODIFY_OP2_B, affectedCc = cc("--*--")),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, OP_SIZE_L, modInfo = RWM_MODIFY_OP2_L, affectedCc = cc("--*--")),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_AND_DREG, OP_SIZE_B, modInfo = RWM_MODIFY_OP2_B, affectedCc = cc("--*--")),
    )

    private val BTST_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, DREG_ONLY, OP_SIZE_L, modInfo = RWM_READ_OP1_B or RWM_READ_OP2_L, affectedCc = cc("--*--")),
        AllowedAdrMode(DREG_ONLY, ALL_EXCEPT_AREG_AND_DREG, OP_SIZE_B, modInfo = RWM_READ_OP1_B or RWM_READ_OP2_B, affectedCc = cc("--*--")),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, OP_SIZE_L, modInfo = RWM_READ_OP2_L, affectedCc = cc("--*--")),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_AND_DREG, OP_SIZE_B, modInfo = RWM_READ_OP2_B, affectedCc = cc("--*--")),
    )

    private val conditionCodes =
        listOf("cc", "hs", "ls", "cs", "lo", "lt", "eq", "mi", "f", "ne", "ge", "pl", "gt", "t", "hi", "vc", "le", "vs")

    private val conditionCodesBcc = conditionCodes.filterNot { it == "f" || it == "t" }

    private val isaData = listOf(

        // Data Movement Instructions
        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData(
                "move", "Move",
                modes = listOf(
                    AllowedAdrMode(
                        ALL_68000_MODES, ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, modInfo = RWM_READ_OP1_OPSIZE or RWM_SET_OP2_OPSIZE,
                        affectedCc = cc("-**00")
                    )
                )
            ),

            IsaData(
                "movea", "Move Address", altMnemonics = listOf("move"),
                modes = listOf(AllowedAdrMode(ALL_68000_MODES, AREG_ONLY, OP_SIZE_WL, modInfo = RWM_READ_OP1_OPSIZE or RWM_SET_OP2_L))
            )
        ),

        IsaData(
            "movem", "Move Multiple Registers",
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.REGISTER_LIST, AddressMode.ADDRESS_REGISTER_DIRECT, AddressMode.DATA_REGISTER_DIRECT),
                    setOf(
                        AddressMode.ADDRESS_REGISTER_INDIRECT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                        AddressMode.ABSOLUTE_ADDRESS
                    ),
                    OP_SIZE_WL,
                    modInfo = RWM_READ_OP1_OPSIZE or RWM_SET_OP2_OPSIZE
                ),
                AllowedAdrMode(
                    setOf(
                        AddressMode.ADDRESS_REGISTER_INDIRECT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                        AddressMode.ABSOLUTE_ADDRESS,
                        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
                    ),
                    setOf(AddressMode.REGISTER_LIST, AddressMode.ADDRESS_REGISTER_DIRECT, AddressMode.DATA_REGISTER_DIRECT),
                    OP_SIZE_WL,
                    modInfo = RWM_READ_OP1_OPSIZE or RWM_SET_OP2_L
                ),
                // according to Yann, specifying the registers as bitmask is also valid
                AllowedAdrMode(
                    setOf(AddressMode.IMMEDIATE_DATA),
                    setOf(
                        AddressMode.ADDRESS_REGISTER_INDIRECT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                        AddressMode.ABSOLUTE_ADDRESS
                    ),
                    OP_SIZE_WL,
                    modInfo = RWM_SET_OP2_OPSIZE // Can't map immediate data to registers
                ),
                AllowedAdrMode(
                    setOf(
                        AddressMode.ADDRESS_REGISTER_INDIRECT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                        AddressMode.ABSOLUTE_ADDRESS,
                        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
                    ),
                    setOf(AddressMode.IMMEDIATE_DATA),
                    OP_SIZE_WL,
                    modInfo = RWM_READ_OP1_OPSIZE // Can't map immediate data to registers
                ),
            )
        ),

        IsaData(
            "movep", "Move Peripheral",
            machine = EnumSet.range(Machine.MC68000, Machine.MC68040),
            modes = listOf(
                AllowedAdrMode(
                    DREG_ONLY,
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT, AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT),
                    OP_SIZE_WL,
                    modInfo = RWM_READ_OP1_OPSIZE
                ),
                AllowedAdrMode(
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT, AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT),
                    DREG_ONLY,
                    OP_SIZE_WL,
                    modInfo = RWM_SET_OP2_OPSIZE
                )
            )
        ),

        IsaData(
            "moveq", "Move Quick",
            machine = ALL_MACHINES,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, OP_SIZE_L, modInfo = RWM_SET_OP2_L, affectedCc = cc("-**00")))
        ),

        IsaData(
            "exg", "Exchange Registers",
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.DATA_REGISTER_DIRECT, AddressMode.ADDRESS_REGISTER_DIRECT),
                    setOf(AddressMode.DATA_REGISTER_DIRECT, AddressMode.ADDRESS_REGISTER_DIRECT),
                    OP_SIZE_L,
                    modInfo = RWM_MODIFY_OP1_L or RWM_MODIFY_OP2_L // exchanging registers does not set value to a defined state
                )
            )
        ),

        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData(
                "lea", "Load Effective Address",
                modes = listOf(
                    AllowedAdrMode(
                        setOf(
                            AddressMode.ADDRESS_REGISTER_INDIRECT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                            AddressMode.ABSOLUTE_ADDRESS,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX,
                        ),
                        AREG_ONLY,
                        OP_SIZE_L,
                        modInfo = RWM_SET_OP2_L
                    )
                )
            ),

            IsaData(
                "pea", "Push Effective Address",
                modes = listOf(
                    AllowedAdrMode(
                        setOf(
                            AddressMode.ADDRESS_REGISTER_INDIRECT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                            AddressMode.ABSOLUTE_ADDRESS,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX,
                        ),
                        null,
                        OP_SIZE_L,
                        modInfo = RWM_MODIFY_STACK
                    )
                )
            )
        ),

        IsaData(
            "link", "Link and Allocate",
            machine = ALL_MACHINES,
            modes = listOf(AllowedAdrMode(AREG_ONLY, setOf(AddressMode.IMMEDIATE_DATA), OP_SIZE_W, modInfo = RWM_SET_OP1_L or RWM_MODIFY_STACK))
        ),

        IsaData(
            "unlk", "Unlink",
            machine = ALL_MACHINES,
            modes = listOf(AllowedAdrMode(AREG_ONLY, null, OP_UNSIZED, modInfo = RWM_SET_OP1_L or RWM_MODIFY_STACK))
        ),

        // Integer Arithmetic Instructions
        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData("add", "Add", modes = ADD_SUB_MODES),

            IsaData(
                "adda", "Add Address", altMnemonics = listOf("add"),
                modes = listOf(AllowedAdrMode(ALL_68000_MODES, AREG_ONLY, OP_SIZE_WL, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_L))
            ),

            IsaData(
                "addi", "Add Immediate", altMnemonics = listOf("add"),
                modes = listOf(
                    AllowedAdrMode(
                        setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, modInfo = RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("C****")
                    )
                )
            ),

            IsaData("addq", "Add Quick", modes = ADDQ_SUBQ_MODES)
        ),

        IsaData("addx", "Add with Extend", machine = ALL_MACHINES, modes = ADDX_SUBX_MODES),

        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData("sub", "Subtract", modes = ADD_SUB_MODES),

            IsaData(
                "suba", "Subtract Address", altMnemonics = listOf("sub"),
                modes = listOf(AllowedAdrMode(ALL_68000_MODES, AREG_ONLY, OP_SIZE_WL, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_L))
            ),

            IsaData(
                "subi", "Subtract Immediate", altMnemonics = listOf("sub"),
                modes = listOf(
                    AllowedAdrMode(
                        setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, modInfo = RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("C****")
                    )
                )
            ),

            IsaData("subq", "Subtract Quick", modes = ADDQ_SUBQ_MODES)
        ),

        IsaData("subx", "Subtract with Extend", machine = ALL_MACHINES, modes = ADDX_SUBX_MODES),

        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData(
                "neg", "Negate", modes = listOf(
                    AllowedAdrMode(
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, modInfo = RWM_MODIFY_OP1_OPSIZE,
                        affectedCc = cc("C****")
                    )
                )
            ),

            IsaData(
                "negx", "Negate with Extend", modes = listOf(
                    AllowedAdrMode(
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, modInfo = RWM_MODIFY_OP1_OPSIZE,
                        affectedCc = cc("C****"), testedCc = cc("?----")
                    )
                )
            ),

            IsaData(
                "clr",
                "Clear",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, modInfo = RWM_SET_OP1_OPSIZE, affectedCc = cc("-0100")))
            ),

            IsaData(
                "cmp", "Compare", modes = listOf(
                    AllowedAdrMode(
                        ALL_EXCEPT_AREG,
                        setOf(AddressMode.DATA_REGISTER_DIRECT),
                        modInfo = RWM_READ_OP1_OPSIZE or RWM_READ_OP2_OPSIZE,
                        affectedCc = cc("-****")
                    ),
                    AllowedAdrMode(
                        setOf(AddressMode.ADDRESS_REGISTER_DIRECT),
                        setOf(AddressMode.DATA_REGISTER_DIRECT),
                        OP_SIZE_WL,
                        modInfo = RWM_READ_OP1_OPSIZE or RWM_READ_OP2_OPSIZE,
                        affectedCc = cc("-****")
                    ),
                )
            ),

            IsaData(
                "cmpa", "Compare Address", altMnemonics = listOf("cmp"),
                modes = listOf(
                    AllowedAdrMode(
                        ALL_68000_MODES,
                        AREG_ONLY,
                        OP_SIZE_WL,
                        modInfo = RWM_READ_OP1_OPSIZE or RWM_READ_OP2_L,
                        affectedCc = cc("-****")
                    )
                )
            ),

            IsaData(
                "cmpi", "Compare Immediate", altMnemonics = listOf("cmp"),
                modes = listOf(
                    AllowedAdrMode(
                        setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_AND_IMMEDIATE, modInfo = RWM_READ_OP2_OPSIZE,
                        affectedCc = cc("-****")
                    )
                )
            )
        ),

        IsaData(
            "cmpm", "Compare Memory to Memory", altMnemonics = listOf("cmp"),
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC),
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC),
                    modInfo = RWM_READ_OP1_OPSIZE or RWM_READ_OP2_OPSIZE,
                    affectedCc = cc("-****")
                )
            )
        ),

        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData(
                "muls", "Signed Multiply",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W, modInfo = RWM_READ_OP1_W or RWM_MODIFY_OP2_L, affectedCc = cc("-**00")))
            ),

            IsaData(
                "mulu", "Unsigned Multiply",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W, modInfo = RWM_READ_OP1_W or RWM_MODIFY_OP2_L, affectedCc = cc("-**00")))
            ),

            IsaData(
                "divs", "Signed Divide",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W, modInfo = RWM_READ_OP1_L or RWM_MODIFY_OP2_L, affectedCc = cc("-***0")))
            ),

            IsaData(
                "divu", "Unsigned Divide",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W, modInfo = RWM_READ_OP1_L or RWM_MODIFY_OP2_L, affectedCc = cc("-***0")))
            ),
        ),

        IsaData(
            "ext",
            "Sign Extend",
            machine = ALL_MACHINES,
            modes = listOf(AllowedAdrMode(DREG_ONLY, null, OP_SIZE_WL, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("-**00")))
        ),

        // Logical Instructions

        *autoExpandForOtherCpus(
            MC68020_PLUS,
            IsaData(
                "and", "Logical AND",
                modes = listOf(
                    AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("-**00")),
                    AllowedAdrMode(DREG_ONLY, INDIRECT_MODES, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("-**00"))
                )
            ),

            IsaData(
                "andi", "Logical AND Immediate",
                altMnemonics = listOf("and"),
                modes = listOf(
                    AllowedAdrMode(
                        setOf(AddressMode.IMMEDIATE_DATA),
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL,
                        modInfo = RWM_MODIFY_OP2_OPSIZE,
                        affectedCc = cc("-**00")
                    )
                )
            ),

            IsaData(
                "eor", "Logical Exclusive-OR",
                modes = listOf(
                    AllowedAdrMode(
                        DREG_ONLY,
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL,
                        modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
                        affectedCc = cc("-**00")
                    )
                )
            ),

            IsaData(
                "eori", "Logical Exclusive-OR Immediate", altMnemonics = listOf("eor"),
                modes = listOf(
                    AllowedAdrMode(
                        setOf(AddressMode.IMMEDIATE_DATA),
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL,
                        modInfo = RWM_MODIFY_OP2_OPSIZE,
                        affectedCc = cc("-**00")
                    )
                )
            ),

            IsaData(
                "or", "Logical Inclusive-OR",
                modes = listOf(
                    AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("-**00")),
                    AllowedAdrMode(DREG_ONLY, INDIRECT_MODES, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE, affectedCc = cc("-**00"))
                )
            ),

            IsaData(
                "ori", "Logical Inclusive-OR Immediate", altMnemonics = listOf("or"),
                modes = listOf(
                    AllowedAdrMode(
                        setOf(AddressMode.IMMEDIATE_DATA),
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL,
                        modInfo = RWM_MODIFY_OP2_OPSIZE,
                        affectedCc = cc("-**00")
                    )
                )
            ),

            IsaData(
                "not", "Logical Complement",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, modInfo = RWM_MODIFY_OP1_OPSIZE, affectedCc = cc("-**00")))
            ),

            // Shift and Rotate Instructions
            IsaData("asl", "Arithmetic Shift Left", modes = ASD_LSD_MODES),
            IsaData("asr", "Arithmetic Shift Right", modes = ASD_LSD_MODES),
            IsaData("lsl", "Logical Shift Left", modes = ASD_LSD_MODES),
            IsaData("lsr", "Logical Shift Right", modes = ASD_LSD_MODES),
            IsaData("rol", "Rotate Left", modes = ROD_MODES),
            IsaData("ror", "Rotate Right", modes = ROD_MODES),
            IsaData("roxl", "Rotate with Extend Left", modes = ROXD_MODES),
            IsaData("roxr", "Rotate with Extend Right", modes = ROXD_MODES),
        ),

        IsaData(
            "swap",
            "Swap Register Words",
            modes = listOf(AllowedAdrMode(DREG_ONLY, null, OP_SIZE_W, modInfo = RWM_MODIFY_OP1_L, affectedCc = cc("-**00")))
        ),

        // Bit Manipulation Instructions

        *autoExpandForOtherCpus(
            MC68020_PLUS,
            IsaData("bchg", "Test Bit and Change", modes = BCHG_BCLR_BSET_MODES),
            IsaData("bclr", "Test Bit and Clear", modes = BCHG_BCLR_BSET_MODES),
            IsaData("bset", "Test Bit and Set", modes = BCHG_BCLR_BSET_MODES),
            IsaData("btst", "Test Bit", modes = BTST_MODES),
        ),

        // Binary-Coded Decimal Instructions
        IsaData(
            "abcd", "Add Decimal with Extend",
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    DREG_ONLY, DREG_ONLY, OP_SIZE_B, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
                    affectedCc = cc("CUAU*"), testedCc = cc("?-?--")
                ),
                AllowedAdrMode(
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC),
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC),
                    OP_SIZE_B,
                    modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
                    affectedCc = cc("CUAU*"), testedCc = cc("?-?--")
                )
            )
        ),
        IsaData(
            "sbcd", "Subtract Decimal with Extend",
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    DREG_ONLY, DREG_ONLY, OP_SIZE_B, modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
                    affectedCc = cc("CUAU*"), testedCc = cc("?-?--")
                ),
                AllowedAdrMode(
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC),
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC),
                    OP_SIZE_B,
                    modInfo = RWM_READ_OP1_OPSIZE or RWM_MODIFY_OP2_OPSIZE,
                    affectedCc = cc("CUAU*"), testedCc = cc("?-?--")
                )
            )
        ),

        *autoExpandForOtherCpus(
            MC68020_PLUS,
            IsaData(
                "nbcd", "Negate Decimal with Extend",
                modes = listOf(
                    AllowedAdrMode(
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, OP_SIZE_B, modInfo = RWM_MODIFY_OP1_OPSIZE,
                        affectedCc = cc("CUAU*"), testedCc = cc("?-?--")
                    )
                )
            )
        ),

        // Program Control Instructions
        IsaData(
            "bCC", "Branch Conditionally", conditionCodes = conditionCodesBcc,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.ABSOLUTE_ADDRESS), null, OP_SIZE_SBW, testedCc = cc("-????"))),
            changesControlFlow = true
        ),
        IsaData(
            "bra", "Branch",
            modes = listOf(AllowedAdrMode(setOf(AddressMode.ABSOLUTE_ADDRESS), null, OP_SIZE_SBW)),
            changesControlFlow = true
        ),
        IsaData(
            "bsr",
            "Branch to Subroutine",
            modes = listOf(AllowedAdrMode(setOf(AddressMode.ABSOLUTE_ADDRESS), null, OP_SIZE_SBW, modInfo = RWM_MODIFY_STACK)),
            changesControlFlow = true
        ),

        IsaData(
            "dbCC",
            "Test Condition, Decrement, and Branch",
            altMnemonics = listOf("dbra"),
            conditionCodes = conditionCodes,
            modes = listOf(AllowedAdrMode(DREG_ONLY, setOf(AddressMode.ABSOLUTE_ADDRESS), OP_SIZE_W, modInfo = RWM_MODIFY_OP1_W, testedCc = cc("-????"))),
            changesControlFlow = true
        ),


        *autoExpandForOtherCpus(
            MC68020_PLUS,
            IsaData(
                "sCC", "Set Conditionally", conditionCodes = conditionCodes,
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, OP_SIZE_B, modInfo = RWM_SET_OP2_B, testedCc = cc("-????")))
            ),

            IsaData(
                "jmp", "Jump",
                modes = listOf(
                    AllowedAdrMode(
                        setOf(
                            AddressMode.ADDRESS_REGISTER_INDIRECT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                            AddressMode.ABSOLUTE_ADDRESS,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
                        ), null, OP_UNSIZED
                    )
                ),
                changesControlFlow = true
            ),
            IsaData(
                "jsr", "Jump to Subroutine",
                modes = listOf(
                    AllowedAdrMode(
                        setOf(
                            AddressMode.ADDRESS_REGISTER_INDIRECT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                            AddressMode.ABSOLUTE_ADDRESS,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
                            AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX
                        ), null, OP_UNSIZED, modInfo = RWM_MODIFY_STACK
                    )
                ),
                changesControlFlow = true
            )
        ),

        IsaData("nop", "No Operation", machine = ALL_MACHINES, hasOps = false, modes = NO_OPS_UNSIZED),

        IsaData(
            "rtr",
            "Return and Restore",
            machine = ALL_MACHINES,
            hasOps = false,
            modes = listOf(AllowedAdrMode(size = OP_UNSIZED, modInfo = RWM_MODIFY_STACK, affectedCc = cc("*****"))),
            changesControlFlow = true
        ),
        IsaData(
            "rts", "Return from Subroutine", hasOps = false,
            machine = ALL_MACHINES,
            modes = listOf(AllowedAdrMode(size = OP_UNSIZED, modInfo = RWM_MODIFY_STACK)),
            changesControlFlow = true
        ),

        IsaData(
            "tst", "Test Operand",
            machine = MC68000_ONLY,
            modes = listOf(
                AllowedAdrMode(
                    ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, modInfo = RWM_READ_OP1_OPSIZE,
                    affectedCc = cc("-**00")
                )
            )
        ),

        IsaData(
            "tst", "Test Operand",
            machine = MC68020_PLUS,
            modes = listOf(
                AllowedAdrMode(
                    ALL_68020_MODES.minus(AddressMode.ADDRESS_REGISTER_DIRECT), null, modInfo = RWM_READ_OP1_OPSIZE,
                    affectedCc = cc("-**00")
                ),
                AllowedAdrMode(
                    setOf(AddressMode.ADDRESS_REGISTER_DIRECT), null, OP_SIZE_WL, modInfo = RWM_READ_OP1_OPSIZE,
                    affectedCc = cc("-**00")
                ),
            )
        ),

        // System Control Instructions
        IsaData(
            "andi", "AND Immediate to Status Register", id = "andi to SR", altMnemonics = listOf("and"), isPrivileged = true,
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr",
                    affectedCc = cc("AAAAA"), testedCc = cc("?????")
                )
            )
        ),
        IsaData(
            "eori", "Exclusive-OR Immediate to Status Register", id = "eori to SR", altMnemonics = listOf("eor"), isPrivileged = true,
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr",
                    affectedCc = cc("*****"), testedCc = cc("?????")
                )
            )
        ),
        IsaData(
            "ori", "Inclusive-OR Immediate to Status Register", id = "ori to SR", altMnemonics = listOf("or"), isPrivileged = true,
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr",
                    affectedCc = cc("OOOOO"), testedCc = cc("?????")
                )
            )
        ),

        IsaData(
            "move", "Move from Status Register", id = "move from SR",
            machine = MC68000_ONLY,
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.SPECIAL_REGISTER_DIRECT),
                    ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL,
                    OP_SIZE_W,
                    "sr",
                    modInfo = RWM_SET_OP2_W,
                    testedCc = cc("?????")
                )
            )
        ),

        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData(
                "move", "Move to Status Register", id = "move to SR", isPrivileged = true,
                modes = listOf(
                    AllowedAdrMode(
                        ALL_EXCEPT_AREG, setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr",
                        affectedCc = cc("*****")
                    )
                )
            )
        ),

        IsaData(
            "move", "Move User Stack Pointer", id = "move USP", isPrivileged = true,
            machine = ALL_MACHINES,
            modes = listOf(
                AllowedAdrMode(setOf(AddressMode.SPECIAL_REGISTER_DIRECT), AREG_ONLY, OP_SIZE_L, "usp", modInfo = RWM_SET_OP2_L),
                AllowedAdrMode(AREG_ONLY, setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_L, "usp", modInfo = RWM_READ_OP1_L),
            )
        ),

        IsaData("reset", "Reset External Devices", machine = ALL_MACHINES, isPrivileged = true, hasOps = false, modes = NO_OPS_UNSIZED),
        IsaData(
            "rte", "Return from Exception",
            machine = ALL_MACHINES, isPrivileged = true, hasOps = false,
            modes = listOf(AllowedAdrMode(size = OP_UNSIZED, modInfo = RWM_MODIFY_STACK)),
            changesControlFlow = true
        ),
        IsaData(
            "stop", "Stop",
            machine = ALL_MACHINES, isPrivileged = true,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), null, OP_UNSIZED))
        ),

        *autoExpandForOtherCpus(
            MC68020_PLUS,
            IsaData(
                "chk", "Check Register Against Bound",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W, modInfo = RWM_READ_OP1_W or RWM_READ_OP2_W, affectedCc = cc("-*UUU")))
            )
        ),

        IsaData("illegal", "Take Illegal Instruction Trap", machine = ALL_MACHINES, hasOps = false, modes = NO_OPS_UNSIZED, changesControlFlow = true),
        IsaData(
            "trap",
            "Trap",
            machine = ALL_MACHINES,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), null, OP_UNSIZED)),
            changesControlFlow = true
        ),
        IsaData(
            "trapv", "Trap on Overflow",
            machine = ALL_MACHINES, hasOps = false,
            modes = listOf(AllowedAdrMode(size = OP_UNSIZED, testedCc = cc("---?-"))),
            changesControlFlow = true
        ),

        IsaData(
            "andi", "AND Immediate to Condition Code Register", id = "andi to CCR", altMnemonics = listOf("and"),
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_B, "ccr",
                    affectedCc = cc("AAAAA"), testedCc = cc("?????")
                )
            )
        ),
        IsaData(
            "eori", "Exclusive-OR Immediate to Condition Code Register", id = "eori to CCR", altMnemonics = listOf("eor"),
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_B, "ccr",
                    affectedCc = cc("*****"), testedCc = cc("?????")
                )
            )
        ),
        IsaData(
            "ori", "Inclusive-OR Immediate to Condition Code Register", id = "ori to CCR", altMnemonics = listOf("or"),
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_B, "ccr",
                    affectedCc = cc("OOOOO"), testedCc = cc("?????")
                )
            )
        ),

        *autoExpandForOtherCpus(
            MC68020_PLUS,

            IsaData(
                "move", "Move from Condition Code Register", id = "move from CCR",
                machine = EnumSet.of(Machine.MC68010),
                modes = listOf(
                    AllowedAdrMode(
                        setOf(AddressMode.SPECIAL_REGISTER_DIRECT),
                        ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL,
                        OP_SIZE_W,
                        "ccr",
                        modInfo = RWM_SET_OP2_W,
                        testedCc = cc("?????")
                    )
                )
            ),

            IsaData(
                "move", "Move to Condition Code Register", id = "move to CCR",
                modes = listOf(
                    AllowedAdrMode(ALL_EXCEPT_AREG, setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "ccr", affectedCc = cc("*****")),
                )
            )
        ),

        // Multiprocessor Instructions
        *autoExpandForOtherCpus(
            MC68020_PLUS,
            IsaData(
                "tas", "Test Operand and Set",
                modes = listOf(AllowedAdrMode(ALL_EXCEPT_IMMEDIATE_AND_PC_REL, null, OP_SIZE_B, modInfo = RWM_MODIFY_OP1_B, affectedCc = cc("-**00")))
            )
        )
    )

    private fun autoExpandForOtherCpus(machines: Set<Machine>, vararg isaDatas: IsaData): Array<out IsaData> {
        val allIsaData = ArrayList<IsaData>()
        for (isaData in isaDatas) {
            val newModes = ArrayList<AllowedAdrMode>()
            for (mode in isaData.modes) {
                newModes.add(mode.copy(op1 = add68020Modes(mode.op1), op2 = add68020Modes(mode.op2)))
            }
            allIsaData.add(isaData.copy(machine = machines, modes = newModes))
        }
        return allIsaData.toTypedArray()
    }

    private fun add68020Modes(oldModes: Set<AddressMode>?) = if (oldModes != null) {
        val newModes = oldModes.toMutableSet()
        if (oldModes.contains(AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX)) {
            newModes.add(AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_SCALED_INDEX)
            newModes.add(AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_BASE_DISPLACEMENT)
            newModes.add(AddressMode.MEMORY_INDIRECT)
            newModes.add(AddressMode.MEMORY_INDIRECT_POSTINDEXED)
            newModes.add(AddressMode.MEMORY_INDIRECT_PREINDEXED)
        }
        if (oldModes.contains(AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_INDEX)) {
            newModes.add(AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_SCALED_INDEX)
            newModes.add(AddressMode.PROGRAM_COUNTER_INDIRECT_WITH_BASE_DISPLACEMENT)
            newModes.add(AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT)
            newModes.add(AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT_POSTINDEXED)
            newModes.add(AddressMode.PROGRAM_COUNTER_MEMORY_INDIRECT_PREINDEXED)
        }
        newModes
    } else {
        null
    }

    private
    val mnemonicLookupMap = isaData.asSequence()
        .flatMap {
            (if (it.conditionCodes.isEmpty()) it.altMnemonics.plus(it.mnemonic) else it.altMnemonics.plus(it.conditionCodes
                .map { cc ->
                    it.mnemonic.replace("CC", cc)
                })).map { mnemonic -> mnemonic to it }
        }
        .groupBy({ it.first }) { it.second }

    val mnemonics = mnemonicLookupMap.keys

    fun findMatchingInstructions(mnemonic: String): List<IsaData> {
        return mnemonicLookupMap.getOrDefault(mnemonic.lowercase(), emptyList())
    }

    fun findMatchingOpMode(candidates: List<IsaData>, op1: AddressMode?, op2: AddressMode?, opSize: Int?, specialReg: String?): List<IsaData> {
        return candidates.filter {
            it.modes.any { am ->
                isAddressModeMatching(am, op1, op2, specialReg)
                        && ((opSize == null) || ((opSize and am.size) == opSize))
            }
        }
    }

    fun findMatchingAddressMode(modes: List<AllowedAdrMode>, op1: AddressMode?, op2: AddressMode?, opSize: Int?, specialReg: String?): List<AllowedAdrMode> {
        return modes.filter { am ->
            isAddressModeMatching(am, op1, op2, specialReg)
                    && ((opSize == null) || ((opSize and am.size) == opSize))
        }
    }

    fun findMatchingOpModeIgnoringSize(candidates: List<IsaData>, op1: AddressMode?, op2: AddressMode?, specialReg: String?): List<IsaData> {
        return candidates.filter {
            it.modes.any { am -> isAddressModeMatching(am, op1, op2, specialReg) }
        }
    }

    fun findSupportedOpSizes(candidates: List<IsaData>, op1: AddressMode?, op2: AddressMode?, specialReg: String?): Int {
        return candidates.fold(OP_UNSIZED) { acc, isaData ->
            acc or isaData.modes.fold(OP_UNSIZED) { am_acc, am ->
                if (isAddressModeMatching(am, op1, op2, specialReg)) am_acc or am.size else am_acc
            }
        }
    }

    private fun isAddressModeMatching(am: AllowedAdrMode, op1: AddressMode?, op2: AddressMode?, specialReg: String?) =
        ((((op1 == null) && (am.op1 == null)) || am.op1?.contains(op1) ?: false)
                && (((op2 == null) && (am.op2 == null)) || am.op2?.contains(op2) ?: false)
                && ((specialReg == null) || (specialReg.equals(am.specialReg, true))))
}