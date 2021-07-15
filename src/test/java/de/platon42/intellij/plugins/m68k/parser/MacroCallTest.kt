package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("macros")
internal class MacroCallTest : AbstractParsingTest() {

    @Test
    internal fun standard_macrocall_without_parameters(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " BLT_HOG_ON\n")
    }

    @Test
    internal fun call_with_complex_parameters(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " COPIMOVE (3<<0)|(3<<3)|(1<<6),bplcon2\n")
    }

    @Test
    internal fun pushm_call_with_register_list(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " PUSHM d0/d3/d5-d7/a0-a2/a4-a6 ; save a couple of registers\n")
    }

    @Test
    internal fun putmsg_call_with_specially_bracketed_parameters(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        // FIXME this doesn't work as intended...
        testGoodSyntax(testCase, "\tPUTMSG 10,<\"%s has left the building at %d:%d\",10,'Yup!'>,a0,$42(a1,d0.w),#42\n")
    }
}