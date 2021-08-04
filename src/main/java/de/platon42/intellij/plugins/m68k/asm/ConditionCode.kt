package de.platon42.intellij.plugins.m68k.asm

const val CC_X_CLEAR = 0x10000
const val CC_X_SET = 0x20000
const val CC_X_UNDEF = 0x30000
const val CC_X_RES = 0x40000
const val CC_X_AND = 0x50000
const val CC_X_OR = 0x60000
const val CC_X_CARRY = 0x70000
const val CC_X_TST = 0xf0000

const val CC_N_CLEAR = 0x01000
const val CC_N_SET = 0x02000
const val CC_N_UNDEF = 0x03000
const val CC_N_RES = 0x04000
const val CC_N_AND = 0x05000
const val CC_N_OR = 0x06000
const val CC_N_TST = 0x0f000

const val CC_Z_CLEAR = 0x00100
const val CC_Z_SET = 0x00200
const val CC_Z_UNDEF = 0x00300
const val CC_Z_RES = 0x00400
const val CC_Z_AND = 0x00500
const val CC_Z_OR = 0x00600
const val CC_Z_TST = 0x00f00

const val CC_V_CLEAR = 0x00010
const val CC_V_SET = 0x00020
const val CC_V_UNDEF = 0x00030
const val CC_V_RES = 0x00040
const val CC_V_AND = 0x00050
const val CC_V_OR = 0x00060
const val CC_V_TST = 0x000f0

const val CC_C_CLEAR = 0x00001
const val CC_C_SET = 0x00002
const val CC_C_UNDEF = 0x00003
const val CC_C_RES = 0x00004
const val CC_C_AND = 0x00005
const val CC_C_OR = 0x00006
const val CC_C_TST = 0x0000f

private const val CC_NOT_AFFECTED_STR = "Not affected"
private const val CC_ALWAYS_CLEAR_STR = "Always cleared"
private const val CC_ALWAYS_SET_STR = "Always set"
private const val CC_UNDEFINED_STR = "Undefined"
private const val CC_RES_STR = "From result"
private const val CC_AND_STR = "And'ed: Only cleared for zero bit"
private const val CC_OR_STR = "Or'ed: Only set for one bit"

fun getCcInfo(cc: Int): Map<String, Pair<String, String>> {
    val xnzvcMap = LinkedHashMap<String, Pair<String, String>>(5)
    xnzvcMap["X"] = when (cc and CC_X_TST) {
        0 -> "-" to CC_NOT_AFFECTED_STR
        CC_X_SET -> "1" to CC_ALWAYS_SET_STR
        CC_X_CLEAR -> "0" to CC_ALWAYS_CLEAR_STR
        CC_X_UNDEF -> "U" to CC_UNDEFINED_STR
        CC_X_AND -> "*" to CC_AND_STR
        CC_X_OR -> "*" to CC_OR_STR
        CC_X_CARRY -> "*" to "Set the same as the carry bit"
        else -> "*" to "$CC_RES_STR (usually the bit shifted out)"
    }
    xnzvcMap["N"] = when (cc and CC_N_TST) {
        0 -> "-" to CC_NOT_AFFECTED_STR
        CC_N_SET -> "1" to CC_ALWAYS_SET_STR
        CC_N_CLEAR -> "0" to CC_ALWAYS_CLEAR_STR
        CC_N_UNDEF -> "U" to CC_UNDEFINED_STR
        CC_N_AND -> "*" to CC_AND_STR
        CC_N_OR -> "*" to CC_OR_STR
        else -> "*" to "$CC_RES_STR (usually if negative)"
    }
    xnzvcMap["Z"] = when (cc and CC_Z_TST) {
        0 -> "-" to CC_NOT_AFFECTED_STR
        CC_Z_SET -> "1" to CC_ALWAYS_SET_STR
        CC_Z_CLEAR -> "0" to CC_ALWAYS_CLEAR_STR
        CC_Z_UNDEF -> "U" to CC_UNDEFINED_STR
        CC_Z_AND -> "*" to CC_AND_STR
        CC_Z_OR -> "*" to CC_OR_STR
        else -> "*" to "$CC_RES_STR (usually if zero)"
    }
    xnzvcMap["V"] = when (cc and CC_V_TST) {
        0 -> "-" to CC_NOT_AFFECTED_STR
        CC_V_SET -> "1" to CC_ALWAYS_SET_STR
        CC_V_CLEAR -> "0" to CC_ALWAYS_CLEAR_STR
        CC_V_UNDEF -> "U" to CC_UNDEFINED_STR
        CC_V_AND -> "*" to CC_AND_STR
        CC_V_OR -> "*" to CC_OR_STR
        else -> "*" to "$CC_RES_STR (usually for overflows)"
    }
    xnzvcMap["C"] = when (cc and CC_V_TST) {
        0 -> "-" to CC_NOT_AFFECTED_STR
        CC_V_SET -> "1" to CC_ALWAYS_SET_STR
        CC_V_CLEAR -> "0" to CC_ALWAYS_CLEAR_STR
        CC_V_UNDEF -> "U" to CC_UNDEFINED_STR
        CC_V_AND -> "*" to CC_AND_STR
        CC_V_OR -> "*" to CC_OR_STR
        else -> "*" to "$CC_RES_STR (usually carry/borrow)"
    }

    return xnzvcMap
}

fun cc(xnzvc: String): Int {
    var result = 0
    result += when (xnzvc[0]) {
        '-' -> 0
        '0' -> CC_X_CLEAR
        '1' -> CC_X_SET
        'U' -> CC_X_UNDEF
        '*' -> CC_X_RES
        'A' -> CC_X_AND
        'O' -> CC_X_OR
        'C' -> CC_X_CARRY
        '?' -> CC_X_TST
        else -> throw IllegalArgumentException("Syntax Error")
    }
    result += when (xnzvc[1]) {
        '-' -> 0
        '0' -> CC_N_CLEAR
        '1' -> CC_N_SET
        'U' -> CC_N_UNDEF
        '*' -> CC_N_RES
        'A' -> CC_N_AND
        'O' -> CC_N_OR
        '?' -> CC_N_TST
        else -> throw IllegalArgumentException("Syntax Error")
    }
    result += when (xnzvc[2]) {
        '-' -> 0
        '0' -> CC_Z_CLEAR
        '1' -> CC_Z_SET
        'U' -> CC_Z_UNDEF
        '*' -> CC_Z_RES
        'A' -> CC_Z_AND
        'O' -> CC_Z_OR
        '?' -> CC_Z_TST
        else -> throw IllegalArgumentException("Syntax Error")
    }
    result += when (xnzvc[3]) {
        '-' -> 0
        '0' -> CC_V_CLEAR
        '1' -> CC_V_SET
        'U' -> CC_V_UNDEF
        '*' -> CC_V_RES
        'A' -> CC_V_AND
        'O' -> CC_V_OR
        '?' -> CC_V_TST
        else -> throw IllegalArgumentException("Syntax Error")
    }
    result += when (xnzvc[4]) {
        '-' -> 0
        '0' -> CC_C_CLEAR
        '1' -> CC_C_SET
        'U' -> CC_C_UNDEF
        '*' -> CC_C_RES
        'A' -> CC_C_AND
        'O' -> CC_C_OR
        '?' -> CC_C_TST
        else -> throw IllegalArgumentException("Syntax Error")
    }
    return result
}

enum class ConditionCode(val cc: String, val testedCc: Int) {
    TRUE("t", cc("-----")),
    FALSE("f", cc("-----")),
    HI("hi", cc("--?-?")),
    LS("ls", cc("--?-?")),
    CC("cc", cc("----?")),
    HS("hs", cc("----?")), // same as CC
    CS("cs", cc("----?")),
    LO("lo", cc("----?")), // same as CS
    NE("ne", cc("--?--")),
    EQ("eq", cc("--?--")),
    VC("vc", cc("---?-")),
    VS("vs", cc("---?-")),
    PL("pl", cc("-?---")),
    MI("mi", cc("-?---")),
    GE("ge", cc("-?-?-")),
    LT("lt", cc("-?-?-")),
    GT("gt", cc("-???-")),
    LE("le", cc("-???-"));

    companion object {
        private val NAME_TO_CC_MAP = values().associateBy { it.cc }

        fun getCcFromName(cc: String) = NAME_TO_CC_MAP[cc.lowercase()]!!

        fun getCcFromMnemonic(originalMnemonic: String, mnemonic: String) =
            // handle special case for dbra
            if (mnemonic.equals("dbra", ignoreCase = true)) {
                FALSE
            } else {
                NAME_TO_CC_MAP[mnemonic.removePrefix(originalMnemonic.removeSuffix("CC")).lowercase()]!!
            }
    }
}
