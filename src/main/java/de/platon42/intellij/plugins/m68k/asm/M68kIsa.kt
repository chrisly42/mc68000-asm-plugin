package de.platon42.intellij.plugins.m68k.asm

enum class Machine {
    MC68000,
    MC68010,
    MC68020,
    MC68030,
    MC68040,
    MC68060
}

data class IsaData(
    val mnemonic: String,
    val description: String,
    val machine: Set<Machine> = setOf(Machine.MC68000),
    val altMnemonics: List<String> = emptyList(),
    val conditionCodes: List<String> = emptyList(),
    val id: String = mnemonic,
    val opSize: String = "bWl",
    val isPrivileged: Boolean = false,
    val hasOps: Boolean = true
)

object M68kIsa {
    val conditionCodes =
        listOf("cc", "ls", "cs", "lt", "eq", "mi", "f", "ne", "ge", "pl", "gt", "t", "hi", "vc", "le", "vs")
    val conditionCodesBcc = conditionCodes.filterNot { it == "f" || it == "t" }

    val isaData = listOf(

        // Data Movement Instructions
        IsaData("move", "Move"),
        IsaData("movea", "Move Address", altMnemonics = listOf("move"), opSize = "L"),
        IsaData("movem", "Move Multiple Registers", opSize = "Wl"),
        IsaData("movep", "Move Peripheral", opSize = ""),
        IsaData("moveq", "Move Quick", opSize = "L"),

        IsaData("exg", "Exchange Registers", opSize = "L"),
        IsaData("lea", "Load Effective Address", opSize = ""),
        IsaData("pea", "Push Effective Address", opSize = ""),
        IsaData("link", "Link and Allocate", opSize = ""),
        IsaData("unlk", "Unlink", opSize = ""),

        // Integer Arithmetic Instructions
        IsaData("add", "Add"),
        IsaData("adda", "Add Address", altMnemonics = listOf("add"), opSize = "Wl"),
        IsaData("addi", "Add Immediate", altMnemonics = listOf("add")),
        IsaData("addq", "Add Quick"),
        IsaData("addx", "Add with Extend"),

        IsaData("sub", "Subtract"),
        IsaData("suba", "Subtract Address", altMnemonics = listOf("sub")),
        IsaData("subi", "Subtract Immediate", altMnemonics = listOf("sub")),
        IsaData("subq", "Subtract Quick"),
        IsaData("subx", "Subtract with Extend"),

        IsaData("neg", "Negate"),
        IsaData("negx", "Negate with Extend"),

        IsaData("clr", "Clear"),

        IsaData("cmp", "Compare"),
        IsaData("cmpa", "Compare Address", altMnemonics = listOf("cmp"), opSize = "Wl"),
        IsaData("cmpi", "Compare Immediate", altMnemonics = listOf("cmp")),
        IsaData("cmpm", "Compare Memory to Memory", altMnemonics = listOf("cmp")),

        IsaData("muls", "Signed Multiply", opSize = "W"),
        IsaData("mulu", "Unsigned Multiply", opSize = "W"),
        IsaData("divs", "Signed Divide", opSize = "W"),
        IsaData("divu", "Unsigned Divide", opSize = "W"),

        IsaData("ext", "Sign Extend", opSize = "Wl"),

        // Logical Instructions
        IsaData("and", "Logical AND"),
        IsaData("andi", "Logical AND Immediate", altMnemonics = listOf("and")),
        IsaData("eor", "Logical Exclusive-OR"),
        IsaData("eori", "Logical Exclusive-OR Immediate", altMnemonics = listOf("eor")),
        IsaData("not", "Logical Complement"),
        IsaData("or", "Logical Inclusive-OR"),
        IsaData("ori", "Logical Inclusive-OR Immediate", altMnemonics = listOf("or")),

        // Shift and Rotate Instructions
        IsaData("asl", "Arithmetic Shift Left"),
        IsaData("asr", "Arithmetic Shift Right"),
        IsaData("lsl", "Logical Shift Left"),
        IsaData("lsr", "Logical Shift Right"),
        IsaData("rol", "Rotate Left"),
        IsaData("ror", "Rotate Right"),
        IsaData("roxl", "Rotate with Extend Left"),
        IsaData("roxr", "Rotate with Extend Right"),
        IsaData("swap", "Swap Register Words", opSize = ""),

        // Bit Manipulation Instructions
        IsaData("bchg", "Test Bit and Change", opSize = "Bl"),
        IsaData("bclr", "Test Bit and Clear", opSize = "Bl"),
        IsaData("bset", "Test Bit and Set", opSize = "Bl"),
        IsaData("btst", "Test Bit", opSize = "Bl"),

        // Binary-Coded Decimal Instructions
        IsaData("abcd", "Add Decimal with Extend", opSize = ""),
        IsaData("sbcd", "Subtract Decimal with Extend", opSize = ""),
        IsaData("nbcd", "Negate Decimal with Extend", opSize = ""),

        // Program Control Instructions
        IsaData("bCC", "Branch Conditionally", conditionCodes = conditionCodesBcc, opSize = "bsW"),
        IsaData("bra", "Branch", opSize = "bsW"),
        IsaData("bsr", "Branch to Subroutine", opSize = "bsW"),

        IsaData(
            "dbCC",
            "Test Condition, Decrement, and Branch",
            altMnemonics = listOf("dbra"),
            conditionCodes = conditionCodes, opSize = "W"
        ),
        IsaData("sCC", "Set Conditionally", conditionCodes = conditionCodes, opSize = ""),

        IsaData("jmp", "Jump", opSize = ""),
        IsaData("jsr", "Jump to Subroutine", opSize = ""),
        IsaData("nop", "No Operation", opSize = "", hasOps = false),

        IsaData("rtr", "Return and Restore", hasOps = false),
        IsaData("rts", "Return from Subroutine", hasOps = false),

        IsaData("tst", "Test Operand"),

        // System Control Instructions
        IsaData("andi", "AND Immediate to Status Register", id = "andi to SR", altMnemonics = listOf("and"), isPrivileged = true),
        IsaData("eori", "Exclusive-OR Immediate to Status Register", id = "eori to SR", altMnemonics = listOf("eor"), isPrivileged = true),
        IsaData("ori", "Inclusive-OR Immediate to Status Register", id = "ori to SR", altMnemonics = listOf("or"), isPrivileged = true),
        IsaData("move", "Move from Status Register", id = "move from SR"),
        IsaData("move", "Move to Status Register", id = "move to SR", isPrivileged = true),
        IsaData("move", "Move User Stack Pointer", id = "move USP", isPrivileged = true),

        IsaData("reset", "Reset External Devices", opSize = "", isPrivileged = true, hasOps = false),
        IsaData("rte", "Return from Exception", isPrivileged = true, hasOps = false),
        IsaData("stop", "Stop", opSize = "", isPrivileged = true),

        IsaData("chk", "Check Register Against Bound"),
        IsaData("illegal", "Take Illegal Instruction Trap", opSize = "", hasOps = false),
        IsaData("trap", "Trap", opSize = ""),
        IsaData("trapv", "Trap on Overflow", opSize = ""),

        IsaData("andi", "AND Immediate to Condition Code Register", id = "andi to CCR", altMnemonics = listOf("and")),
        IsaData("eori", "Exclusive-OR Immediate to Condition Code Register", id = "eori to CCR", altMnemonics = listOf("eor")),
        IsaData("ori", "Inclusive-OR Immediate to Condition Code Register", id = "ori to CCR", altMnemonics = listOf("or")),
        IsaData("move", "Move to Condition Code Register", id = "move to CCR"),


        // Multiprocessor Instructions
        IsaData("tas", "Test Operand and Set", opSize = "B"),

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
}