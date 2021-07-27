package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("macros")
internal class MacroDefinitionTest : AbstractParsingTest() {

    @Test
    internal fun macro_with_name_left(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase, """
FOOBAR MACRO ; start of macro
.loop\@ move.l \1,(\2)+  ; just copy all the stuff
        dbra \3,.loop\@
        ENDM ; end of macro

        move.w  d0,d1
"""
        )
    }

    @Test
    internal fun macro_with_name_right(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase, """
        MACRO FOOBAR ; start of macro
.loop\@ move.l \1,(\2)+  ; just copy all the stuff
        dbra \3,.loop\@
        ENDM ; end of macro
label:  move.w  d0,d1
"""
        )
    }

    @Test
    internal fun empty_macro(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase, """
        MACRO FOOBAR
        ENDM
NARF = 100
"""
        )
    }

    @Test
    internal fun two_macro_definitions_in_succession(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase, """
        MACRO FOOBAR1
.loop\@ move.l \1,(\2)+
        dbra \3,.loop\@
        ENDM
        MACRO FOOBAR2
.loop\@ move.w \1,(\2)+
        dbra \3,.loop\@
        ENDM
label:  move.w  d0,d1
"""
        )
    }

}