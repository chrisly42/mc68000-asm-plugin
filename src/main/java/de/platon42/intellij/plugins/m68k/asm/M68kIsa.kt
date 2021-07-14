package de.platon42.intellij.plugins.m68k.asm

enum class Machine {
    MC68000
}

data class IsaData(
        val mnemonic: String,
        val description: String,
        val machine: Machine = Machine.MC68000,
        val altMnemonics: List<String> = emptyList(),
        val conditionCodes: List<String> = emptyList()
)

object M68kIsa {
    val conditionCodes =
            listOf("cc", "ls", "cs", "lt", "eq", "mi", "f", "ne", "ge", "pl", "gt", "t", "hi", "vc", "le", "vs")
    val conditionCodesBcc = conditionCodes.filterNot { it == "f" || it == "t" }

    val isaData = listOf(
            IsaData("abcd", "Add Decimal with Extend"),
            IsaData("add", "Add"),
            IsaData("adda", "Add Address"),
            IsaData("addi", "Add Immediate"),
            IsaData("addq", "Add Quick"),
            IsaData("addx", "Add with Extend"),
            IsaData("and", "Logical AND"),
            IsaData("andi", "Logical AND Immediate"),
            IsaData("andi", "to CCR AND Immediate to Condition Code Register"),
            IsaData("andi", "to SR AND Immediate to Status Register"),
            IsaData("asl", "Arithmetic Shift Left"),
            IsaData("asr", "Arithmetic Shift Right"),
            IsaData("bCC", "Branch Conditionally", conditionCodes = conditionCodesBcc),
            IsaData("bchg", "Test Bit and Change"),
            IsaData("bclr", "Test Bit and Clear"),
            IsaData("bra", "Branch"),
            IsaData("bset", "Test Bit and Set"),
            IsaData("bsr", "Branch to Subroutine"),
            IsaData("btst", "Test Bit"),
            IsaData("chk", "Check Register Against Bound"),
            IsaData("clr", "Clear"),
            IsaData("cmp", "Compare"),
            IsaData("cmpa", "Compare Address"),
            IsaData("cmpi", "Compare Immediate"),
            IsaData("cmpm", "Compare Memory to Memory"),
            IsaData(
                    "dbCC",
                    "Test Condition, Decrement, and Branch",
                    altMnemonics = listOf("dbra"),
                    conditionCodes = conditionCodes
            ),
            IsaData("divs", "Signed Divide"),
            IsaData("divu", "Unsigned Divide"),
            IsaData("eor", "Logical Exclusive-OR"),
            IsaData("eori", "Logical Exclusive-OR Immediate"),
            IsaData("eori", "to CCR Exclusive-OR Immediate to Condition Code Register"),
            IsaData("eori", "to SR Exclusive-OR Immediate to Status Register"),
            IsaData("exg", "Exchange Registers"),
            IsaData("ext", "Sign Extend"),
            IsaData("illegal", "Take Illegal Instruction Trap"),
            IsaData("jmp", "Jump"),
            IsaData("jsr", "Jump to Subroutine"),
            IsaData("lea", "Load Effective Address"),
            IsaData("link", "Link and Allocate"),
            IsaData("lsl", "Logical Shift Left"),
            IsaData("lsr", "Logical Shift Right"),
            IsaData("move", "Move"),
            IsaData("movea", "Move Address"),
            IsaData("move", "to CCR Move to Condition Code Register"),
            IsaData("move", "from SR Move from Status Register"),
            IsaData("move", "to SR Move to Status Register"),
            IsaData("move", "USP Move User Stack Pointer"),
            IsaData("movem", "Move Multiple Registers"),
            IsaData("movep", "Move Peripheral"),
            IsaData("moveq", "Move Quick"),
            IsaData("muls", "Signed Multiply"),
            IsaData("mulu", "Unsigned Multiply"),
            IsaData("nbcd", "Negate Decimal with Extend"),
            IsaData("neg", "Negate"),
            IsaData("negx", "Negate with Extend"),
            IsaData("nop", "No Operation"),
            IsaData("not", "Logical Complement"),
            IsaData("or", "Logical Inclusive-OR"),
            IsaData("ori", "Logical Inclusive-OR Immediate"),
            IsaData("ori", "to CCR Inclusive-OR Immediate to Condition Code Register"),
            IsaData("ori", "to SR Inclusive-OR Immediate to Status Register"),
            IsaData("pea", "Push Effective Address"),
            IsaData("reset", "Reset External Devices"),
            IsaData("rol", "Rotate Left"),
            IsaData("ror", "Rotate Right"),
            IsaData("roxl", "Rotate with Extend Left"),
            IsaData("roxr", "Rotate with Extend Right"),
            IsaData("rte", "Return from Exception"),
            IsaData("rtr", "Return and Restore"),
            IsaData("rts", "Return from Subroutine"),
            IsaData("sbcd", "Subtract Decimal with Extend"),
            IsaData("sCC", "Set Conditionally", conditionCodes = conditionCodes),
            IsaData("stop", "Stop"),
            IsaData("sub", "Subtract"),
            IsaData("suba", "Subtract Address"),
            IsaData("subi", "Subtract Immediate"),
            IsaData("subq", "Subtract Quick"),
            IsaData("subx", "Subtract with Extend"),
            IsaData("swap", "Swap Register Words"),
            IsaData("tas", "Test Operand and Set"),
            IsaData("trap", "Trap"),
            IsaData("trapv", "Trap on Overflow"),
            IsaData("tst", "Test Operand"),
            IsaData("unlk", "Unlink "),
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