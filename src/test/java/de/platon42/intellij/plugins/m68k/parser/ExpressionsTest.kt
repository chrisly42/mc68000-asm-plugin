package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("expressions")
internal class ExpressionsTest : AbstractParsingTest() {

    @Test
    internal fun pure_decimal_number_literal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = 10\n")
    }

    @Test
    internal fun pure_hexadecimal_number_literal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = $1234\n")
    }

    @Test
    internal fun pure_octal_number_literal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = @777\n")
    }

    @Test
    internal fun pure_binary_number_literal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = %11001100\n")
    }

    @Test
    internal fun pure_string_literal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = 'string'\n" + "FOO=\"bar\"\n")
    }

    @Test
    internal fun math_expression(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = -(~(!!(+(1//~WIDTH^@123+%100101*4/2+(NARF%10|32!21))<<2)>>1)&$1f)\n")
    }

    @Test
    internal fun comparing_expressions(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "FOO = ((WIDTH<20)&&(HEIGHT<=10))||((WIDTH>=40)&&(HEIGHT>128))&&(WIDTH!=0)&&(HEIGHT<>0)=1\n")
    }

    @Test
    internal fun current_pc_symbol_relative_expression(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " dc.w *-.label\n")
    }

    @Test
    internal fun uppercase_hexadecimal_string(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l #\$DEADBEEF,d0\n")
    }

    @Test
    internal fun if_with_single_equals_comparison(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " IF DEBUG=1\n")
    }
}