package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("directives")
internal class OtherDirectivesTest : AbstractParsingTest() {

    @Test
    internal fun include_file(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " include \"exec/execbase.i\"\n")
    }

    @Test
    internal fun include_file_unquoted(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "howto:incbin Convertedassets\\scroller_howto.raw.BPL\n")
    }

    @Test
    internal fun if_defined_block(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "\tIFD DEBUG ; cause a crash\n illegal\n ENDC\n")
    }

    @Test
    internal fun kalms_include_file_special_quote(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " include\t<Engine/A500/System/Copper.i>")
    }

    @Test
    internal fun opt_directive(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " opt o+,w-")
    }
}