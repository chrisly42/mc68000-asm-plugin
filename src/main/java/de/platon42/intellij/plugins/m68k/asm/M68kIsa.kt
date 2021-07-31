package de.platon42.intellij.plugins.m68k.asm

enum class Machine {
    MC68000,
    MC68010,
    MC68020,
    MC68030,
    MC68040,
    MC68060
}

enum class AddressMode {
    IMMEDIATE_DATA,
    ADDRESS_REGISTER_INDIRECT_PRE_DEC,
    ADDRESS_REGISTER_INDIRECT_POST_INC,
    ADDRESS_REGISTER_INDIRECT,
    ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
    PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT,
    ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
    PROGRAM_COUNTER_INDIRECT_WITH_INDEX,
    SPECIAL_REGISTER_DIRECT,
    DATA_REGISTER_DIRECT,
    ADDRESS_REGISTER_DIRECT,
    REGISTER_LIST,
    ABSOLUTE_ADDRESS
}

const val OP_UNSIZED = 0
const val OP_SIZE_B = 1
const val OP_SIZE_W = 2
const val OP_SIZE_L = 4
const val OP_SIZE_S = 8

const val OP_SIZE_BWL = (OP_SIZE_B or OP_SIZE_W or OP_SIZE_L)
const val OP_SIZE_WL = (OP_SIZE_W or OP_SIZE_L)
const val OP_SIZE_SBW = (OP_SIZE_B or OP_SIZE_S or OP_SIZE_W)

data class AllowedAdrMode(
    val op1: Set<AddressMode>? = null,
    val op2: Set<AddressMode>? = null,
    val size: Int = OP_SIZE_BWL,
    val specialReg: String? = null
)

data class IsaData(
    val mnemonic: String,
    val description: String,
    val machine: Set<Machine> = setOf(Machine.MC68000),
    val altMnemonics: List<String> = emptyList(),
    val conditionCodes: List<String> = emptyList(),
    val id: String = mnemonic,
    val isPrivileged: Boolean = false,
    val hasOps: Boolean = true,
    val modes: List<AllowedAdrMode> = listOf(AllowedAdrMode())
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
        AllowedAdrMode(ALL_EXCEPT_AREG, setOf(AddressMode.DATA_REGISTER_DIRECT)),
        AllowedAdrMode(setOf(AddressMode.ADDRESS_REGISTER_DIRECT), setOf(AddressMode.DATA_REGISTER_DIRECT), OP_SIZE_WL),
        AllowedAdrMode(setOf(AddressMode.DATA_REGISTER_DIRECT), INDIRECT_MODES),
    )

    private val ASD_LSD_ROD_ROXD_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, DREG_ONLY),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY),
        AllowedAdrMode(INDIRECT_MODES, null),
    )

    private val BTST_BCHG_BCLR_BSET_MODES = listOf(
        AllowedAdrMode(DREG_ONLY, ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, OP_SIZE_L),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, OP_SIZE_L),
        AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), INDIRECT_MODES, OP_SIZE_B),
    )

    private val conditionCodes =
        listOf("cc", "ls", "cs", "lt", "eq", "mi", "f", "ne", "ge", "pl", "gt", "t", "hi", "vc", "le", "vs")

    private val conditionCodesBcc = conditionCodes.filterNot { it == "f" || it == "t" }

    private val isaData = listOf(

        // Data Movement Instructions
        IsaData(
            "move", "Move",
            modes = listOf(AllowedAdrMode(ALL_68000_MODES, ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL))
        ),
        IsaData(
            "movea", "Move Address", altMnemonics = listOf("move"),
            modes = listOf(AllowedAdrMode(ALL_68000_MODES, AREG_ONLY, OP_SIZE_WL))
        ),
        IsaData(
            "movem", "Move Multiple Registers",
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.REGISTER_LIST),
                    setOf(
                        AddressMode.ADDRESS_REGISTER_INDIRECT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                        AddressMode.ABSOLUTE_ADDRESS
                    ),
                    OP_SIZE_WL
                ),
                AllowedAdrMode(
                    setOf(
                        AddressMode.ADDRESS_REGISTER_INDIRECT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                        AddressMode.ABSOLUTE_ADDRESS
                    ),
                    setOf(AddressMode.REGISTER_LIST),
                    OP_SIZE_WL
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
                    OP_SIZE_WL
                ),
                AllowedAdrMode(
                    setOf(
                        AddressMode.ADDRESS_REGISTER_INDIRECT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT,
                        AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_INDEX,
                        AddressMode.ABSOLUTE_ADDRESS
                    ),
                    setOf(AddressMode.IMMEDIATE_DATA),
                    OP_SIZE_WL
                ),
            )
        ),
        IsaData(
            "movep", "Move Peripheral",
            modes = listOf(
                AllowedAdrMode(
                    DREG_ONLY,
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT, AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT),
                    OP_SIZE_WL
                ),
                AllowedAdrMode(
                    setOf(AddressMode.ADDRESS_REGISTER_INDIRECT, AddressMode.ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT),
                    DREG_ONLY,
                    OP_SIZE_WL
                )
            )
        ),

        IsaData(
            "moveq", "Move Quick",
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), DREG_ONLY, OP_SIZE_L))
        ),

        IsaData(
            "exg", "Exchange Registers",
            modes = listOf(
                AllowedAdrMode(
                    setOf(AddressMode.DATA_REGISTER_DIRECT, AddressMode.ADDRESS_REGISTER_DIRECT),
                    setOf(AddressMode.DATA_REGISTER_DIRECT, AddressMode.ADDRESS_REGISTER_DIRECT),
                    OP_SIZE_L
                )
            )
        ),
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
                    OP_SIZE_L
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
                    OP_SIZE_L
                )
            )
        ),
        IsaData(
            "link", "Link and Allocate",
            modes = listOf(AllowedAdrMode(AREG_ONLY, setOf(AddressMode.IMMEDIATE_DATA), OP_SIZE_W))
        ),
        IsaData(
            "unlk", "Unlink",
            modes = listOf(AllowedAdrMode(AREG_ONLY, null, OP_UNSIZED))
        ),

        // Integer Arithmetic Instructions
        IsaData("add", "Add", modes = ADD_SUB_MODES),
        IsaData("adda", "Add Address", altMnemonics = listOf("add"), modes = listOf(AllowedAdrMode(ALL_68000_MODES, AREG_ONLY, OP_SIZE_WL))),
        IsaData(
            "addi", "Add Immediate", altMnemonics = listOf("add"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL))
        ),
        IsaData(
            "addq", "Add Quick", modes = listOf(
                AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL),
                AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), AREG_ONLY, size = OP_SIZE_WL)
            )
        ),
        IsaData(
            "addx", "Add with Extend",
            modes = listOf(
                AllowedAdrMode(DREG_ONLY, DREG_ONLY),
                AllowedAdrMode(setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC), setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC))
            )
        ),

        IsaData("sub", "Subtract", modes = ADD_SUB_MODES),
        IsaData("suba", "Subtract Address", altMnemonics = listOf("sub"), modes = listOf(AllowedAdrMode(ALL_68000_MODES, AREG_ONLY, OP_SIZE_WL))),
        IsaData(
            "subi", "Subtract Immediate", altMnemonics = listOf("sub"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL))
        ),
        IsaData(
            "subq", "Subtract Quick", modes = listOf(
                AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL),
                AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), AREG_ONLY, size = OP_SIZE_WL)
            )
        ),
        IsaData(
            "subx", "Subtract with Extend",
            modes = listOf(
                AllowedAdrMode(DREG_ONLY, DREG_ONLY),
                AllowedAdrMode(setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC), setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC))
            )
        ),

        IsaData("neg", "Negate", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null))),
        IsaData("negx", "Negate with Extend", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null))),

        IsaData("clr", "Clear", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null))),

        IsaData(
            "cmp", "Compare", modes = listOf(
                AllowedAdrMode(ALL_EXCEPT_AREG, setOf(AddressMode.DATA_REGISTER_DIRECT)),
                AllowedAdrMode(setOf(AddressMode.ADDRESS_REGISTER_DIRECT), setOf(AddressMode.DATA_REGISTER_DIRECT), OP_SIZE_WL),
            )
        ),
        IsaData("cmpa", "Compare Address", altMnemonics = listOf("cmp"), modes = listOf(AllowedAdrMode(ALL_68000_MODES, AREG_ONLY, OP_SIZE_WL))),
        IsaData(
            "cmpi", "Compare Immediate", altMnemonics = listOf("cmp"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_AND_IMMEDIATE))
        ),
        IsaData(
            "cmpm", "Compare Memory to Memory", altMnemonics = listOf("cmp"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC), setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_POST_INC)))
        ),

        IsaData("muls", "Signed Multiply", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W))),
        IsaData("mulu", "Unsigned Multiply", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W))),
        IsaData("divs", "Signed Divide", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W))),
        IsaData("divu", "Unsigned Divide", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W))),

        IsaData("ext", "Sign Extend", modes = listOf(AllowedAdrMode(DREG_ONLY, null, OP_SIZE_WL))),

        // Logical Instructions
        IsaData(
            "and", "Logical AND",
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY), AllowedAdrMode(DREG_ONLY, INDIRECT_MODES))
        ),
        IsaData(
            "andi", "Logical AND Immediate", altMnemonics = listOf("and"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL))
        ),
        IsaData(
            "eor", "Logical Exclusive-OR",
            modes = listOf(AllowedAdrMode(DREG_ONLY, ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL))
        ),
        IsaData(
            "eori", "Logical Exclusive-OR Immediate", altMnemonics = listOf("eor"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL))
        ),
        IsaData(
            "or", "Logical Inclusive-OR",
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY), AllowedAdrMode(DREG_ONLY, INDIRECT_MODES))
        ),
        IsaData(
            "ori", "Logical Inclusive-OR Immediate", altMnemonics = listOf("or"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL))
        ),
        IsaData(
            "not", "Logical Complement",
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null))
        ),

        // Shift and Rotate Instructions
        IsaData("asl", "Arithmetic Shift Left", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("asr", "Arithmetic Shift Right", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("lsl", "Logical Shift Left", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("lsr", "Logical Shift Right", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("rol", "Rotate Left", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("ror", "Rotate Right", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("roxl", "Rotate with Extend Left", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("roxr", "Rotate with Extend Right", modes = ASD_LSD_ROD_ROXD_MODES),
        IsaData("swap", "Swap Register Words", modes = listOf(AllowedAdrMode(DREG_ONLY, null, OP_SIZE_W))),

        // Bit Manipulation Instructions
        IsaData("bchg", "Test Bit and Change", modes = BTST_BCHG_BCLR_BSET_MODES),
        IsaData("bclr", "Test Bit and Clear", modes = BTST_BCHG_BCLR_BSET_MODES),
        IsaData("bset", "Test Bit and Set", modes = BTST_BCHG_BCLR_BSET_MODES),
        IsaData("btst", "Test Bit", modes = BTST_BCHG_BCLR_BSET_MODES),

        // Binary-Coded Decimal Instructions
        IsaData(
            "abcd", "Add Decimal with Extend",
            modes = listOf(
                AllowedAdrMode(DREG_ONLY, DREG_ONLY, OP_SIZE_B),
                AllowedAdrMode(setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC), setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC), OP_SIZE_B)
            )
        ),
        IsaData(
            "sbcd", "Subtract Decimal with Extend",
            modes = listOf(
                AllowedAdrMode(DREG_ONLY, DREG_ONLY, OP_SIZE_B),
                AllowedAdrMode(setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC), setOf(AddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DEC), OP_SIZE_B)
            )
        ),
        IsaData(
            "nbcd", "Negate Decimal with Extend",
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, OP_SIZE_B))
        ),

        // Program Control Instructions
        IsaData(
            "bCC", "Branch Conditionally", conditionCodes = conditionCodesBcc,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.ABSOLUTE_ADDRESS), null, OP_SIZE_SBW))
        ),
        IsaData("bra", "Branch", modes = listOf(AllowedAdrMode(setOf(AddressMode.ABSOLUTE_ADDRESS), null, OP_SIZE_SBW))),
        IsaData("bsr", "Branch to Subroutine", modes = listOf(AllowedAdrMode(setOf(AddressMode.ABSOLUTE_ADDRESS), null, OP_SIZE_SBW))),

        IsaData(
            "dbCC",
            "Test Condition, Decrement, and Branch",
            altMnemonics = listOf("dbra"),
            conditionCodes = conditionCodes,
            modes = listOf(AllowedAdrMode(DREG_ONLY, setOf(AddressMode.ABSOLUTE_ADDRESS), OP_SIZE_W))
        ),
        IsaData(
            "sCC", "Set Conditionally", conditionCodes = conditionCodes,
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null, OP_SIZE_B))
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
            )
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
                    ), null, OP_UNSIZED
                )
            )
        ),
        IsaData("nop", "No Operation", hasOps = false, modes = NO_OPS_UNSIZED),

        IsaData("rtr", "Return and Restore", hasOps = false, modes = NO_OPS_UNSIZED),
        IsaData("rts", "Return from Subroutine", hasOps = false, modes = NO_OPS_UNSIZED),

        IsaData("tst", "Test Operand", modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, null))),

        // System Control Instructions
        IsaData(
            "andi", "AND Immediate to Status Register", id = "andi to SR", altMnemonics = listOf("and"), isPrivileged = true,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr"))
        ),
        IsaData(
            "eori", "Exclusive-OR Immediate to Status Register", id = "eori to SR", altMnemonics = listOf("eor"), isPrivileged = true,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr"))
        ),
        IsaData(
            "ori", "Inclusive-OR Immediate to Status Register", id = "ori to SR", altMnemonics = listOf("or"), isPrivileged = true,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr"))
        ),
        IsaData(
            "move", "Move from Status Register", id = "move from SR",
            modes = listOf(AllowedAdrMode(setOf(AddressMode.SPECIAL_REGISTER_DIRECT), ALL_EXCEPT_AREG_IMMEDIATE_AND_PC_REL, OP_SIZE_W, "sr"))
        ),
        IsaData(
            "move", "Move to Status Register", id = "move to SR", isPrivileged = true,
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "sr"))
        ),
        IsaData(
            "move", "Move User Stack Pointer", id = "move USP", isPrivileged = true,
            modes = listOf(
                AllowedAdrMode(setOf(AddressMode.SPECIAL_REGISTER_DIRECT), AREG_ONLY, OP_SIZE_L, "usp"),
                AllowedAdrMode(AREG_ONLY, setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_L, "usp"),
            )
        ),

        IsaData("reset", "Reset External Devices", isPrivileged = true, hasOps = false, modes = NO_OPS_UNSIZED),
        IsaData("rte", "Return from Exception", isPrivileged = true, hasOps = false, modes = NO_OPS_UNSIZED),
        IsaData(
            "stop", "Stop", isPrivileged = true,
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), null, OP_UNSIZED))
        ),

        IsaData(
            "chk", "Check Register Against Bound",
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, DREG_ONLY, OP_SIZE_W))
        ),
        IsaData("illegal", "Take Illegal Instruction Trap", hasOps = false, modes = NO_OPS_UNSIZED),
        IsaData("trap", "Trap", modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), null, OP_UNSIZED))),
        IsaData("trapv", "Trap on Overflow", hasOps = false, modes = NO_OPS_UNSIZED),

        IsaData(
            "andi", "AND Immediate to Condition Code Register", id = "andi to CCR", altMnemonics = listOf("and"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "ccr"))
        ),
        IsaData(
            "eori", "Exclusive-OR Immediate to Condition Code Register", id = "eori to CCR", altMnemonics = listOf("eor"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "ccr"))
        ),
        IsaData(
            "ori", "Inclusive-OR Immediate to Condition Code Register", id = "ori to CCR", altMnemonics = listOf("or"),
            modes = listOf(AllowedAdrMode(setOf(AddressMode.IMMEDIATE_DATA), setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "ccr"))
        ),
        IsaData(
            "move", "Move to Condition Code Register", id = "move to CCR",
            modes = listOf(AllowedAdrMode(ALL_EXCEPT_AREG, setOf(AddressMode.SPECIAL_REGISTER_DIRECT), OP_SIZE_W, "ccr"))
        ),

        // Multiprocessor Instructions
        IsaData("tas", "Test Operand and Set", modes = listOf(AllowedAdrMode(ALL_EXCEPT_IMMEDIATE_AND_PC_REL, null, OP_SIZE_B)))
    )

    val mnemonics =
        isaData.asSequence()
            .flatMap {
                if (it.conditionCodes.isEmpty()) it.altMnemonics.plus(it.mnemonic) else it.altMnemonics.plus(it.conditionCodes
                    .map { cc ->
                        it.mnemonic.replace("CC", cc)
                    })
            }
            .toSet()

    fun findMatchingInstruction(mnemonic: String): List<IsaData> {
        val lowerMnemonic = mnemonic.lowercase()
        return isaData
            .filter {
                if (it.conditionCodes.isEmpty()) {
                    (it.mnemonic == lowerMnemonic) || it.altMnemonics.any { altMnemonic -> altMnemonic == lowerMnemonic }
                } else {
                    it.altMnemonics.any { altMnemonic -> altMnemonic == lowerMnemonic } ||
                            it.conditionCodes.any { cc ->
                                it.mnemonic.replace("CC", cc) == lowerMnemonic
                            }
                }
            }
    }

    fun findMatchingOpMode(candidates: List<IsaData>, op1: AddressMode?, op2: AddressMode?, opSize: Int?, specialReg: String?): List<IsaData> {
        return candidates.filter {
            it.modes.any { am ->
                isAddressModeMatching(am, op1, op2, specialReg)
                        && ((opSize == null) || ((opSize and am.size) == opSize))
            }
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
                && ((specialReg == null) || (specialReg == am.specialReg)))
}