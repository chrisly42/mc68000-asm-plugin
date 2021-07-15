package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("directives")
internal class DataDirectivesTest : AbstractParsingTest() {

    @Test
    internal fun dc_w_with_some_numbers(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " dc.w 0,1,2,3,4,5\n")
    }

    @Test
    internal fun dc_b_with_strings_and_global_label(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "foo: dc.b 'It could be good!',10,\"Don't ya think?\",10,0\n")
    }

    @Test
    internal fun even(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "\teven ; align to even address\n")
    }
}