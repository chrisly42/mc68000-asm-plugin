package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("labels")
internal class LabelsTest : AbstractParsingTest() {

    @Test
    internal fun plain_global_label(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "Global_Label\n")
    }

    @Test
    internal fun global_label_with_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "Global_Label:\n")
    }

    @Test
    internal fun external_label_with_double_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "External_Label::\n")
    }

    @Test
    internal fun indented_global_label_with_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "   Global_Label:\n")
    }

    @Test
    internal fun indented_external_label_with_double_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "   External_Label::\n")
    }

    @Test
    internal fun plain_dot_local_label(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, ".local_label\n")
    }

    @Test
    internal fun dot_local_label_with_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, ".local_label:\n")
    }

    @Test
    internal fun indented_dot_local_label_with_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "\t.local_label:\n")
    }

    @Test
    internal fun plain_local_label_dollar(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "local_label$\n")
    }

    @Test
    internal fun local_label_dollar_with_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "local_label$:\n")
    }

    @Test
    internal fun indented_local_label_dollar_with_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "   local_label$:\n")
    }

    @Test
    internal fun bad_indented_dot_local_label_without_label(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testBadSyntax(testCase, "   .local_label\n")
    }

    @Test
    internal fun bad_indented_local_label_dollar_without_label(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testBadSyntax(testCase, "   local_label$\n")
    }

    @Test
    internal fun complex_label_test(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
                testCase, "start_demo: ; here we will do some cool stuff\n"
                + ".outerloop moveq.l #17-1,d7 ; this is the outer loop\n"
                + "innerloop$ move.l (a0)+,(a1)+ ; copy stuff\n"
                + "   dbne d7,innerloop$ ; copy more\n"
                + " .skip: bne.s .outerloop ; end of chunk?\n"
                + "  GlobalExit:: rts\n"
        )
    }
}