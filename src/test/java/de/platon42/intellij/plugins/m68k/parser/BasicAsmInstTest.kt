package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParserResultFile
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("basic")
internal class BasicAsmInstTest : AbstractParsingTest() {

    @Test
    @ParserResultFile("basic_block_of_code")
    internal fun parser_can_parse_basic_block_of_code(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase, "\tadd.l d0,d0\n"
                    + "\tmoveq #10,d1\n"
                    + " rts\n"
                    + "\n"
                    + "; comment\n"
                    + "    jmp\tresetcode ; unreachable\n"
                    + "  \n"
                    + "eoflabel"
        )
    }
}