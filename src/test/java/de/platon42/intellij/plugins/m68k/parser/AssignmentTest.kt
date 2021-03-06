package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("assignment")
internal class AssignmentTest : AbstractParsingTest() {

    @Test
    internal fun assignment_via_equals_with_spaces(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = 123+10\n")
    }

    @Test
    internal fun assignment_via_equals_without_spaces(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO=123+10\n")
    }

    @Test
    internal fun assignment_with_local_label(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO= .local-.bar\n")
    }

    @Test
    internal fun assignment_with_colon(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO: = 123\n")
    }

    @Test
    internal fun assignment_with_colon_and_set(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO: set 123\n")
    }

    @Test
    internal fun assignment_via_equ(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO equ BAR\n")
    }

    @Test
    internal fun assignment_via_set(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO set BAR\n")
    }
}