package de.platon42.intellij.plugins.m68k.asm

object AssemblerDirectives {

    val dataDirectives: Set<String> = setOf(
            "section", "pushsection", "popsection",
            "bss", "bss_c", "bss_f",
            "data", "data_c", "data_f",
            "text", "cseg", "code", "code_c", "code_f",
            "offset",

            "abs",
            "db", "dw", "dl",
            "dr.b", "dr.w", "dr.l",
            "dc", "dc.b", "dc.w", "dc.l", "dcb", "dcb.b", "dcb.w", "dcb.l",
            "blk.b", "blk.w", "blk.l",
            "ds", "ds.b", "ds.w", "ds.l",

            "align", "even", "odd", "cnop", "long", "dphrase", "phrase", "qphrase",

            "cargs", "comm", "comment",
            "rsset", "clrfo", "clrso", "setfo", "setso"
    )

    val otherDirective: Set<String> = setOf(
            "if",
            "ifeq", "ifne", "ifgt", "ifge", "iflt", "ifle", "ifb", "ifnb", "ifc", "ifnc",
            "ifd", "ifnd", "ifmacrod", "ifmacrond",
            //"iif" // not supported
            "else", "endif", "endc",

            "macro", "exitm", "mexit",

            "extern", "nref", "xdef", "xref", "globl", "public", "weak",

            "incdir", "include", "incbin", "output",

            "list", "nlist", "nolist", "llen", "nopage", "page", "spc",
            "org",

            "assert", "fail", "print", "printt", "printv", "echo",

            "inline", "einline",
            "rem", "erem"
    )
}