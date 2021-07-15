package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyParser
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ParsingTestExtension::class)
@TestDataPath("src/test/resources/parser")
@MyParser(value = M68kParserDefinition::class, extension = "asm")
abstract class AbstractParsingTest : AbstractM68kTest() {

    fun testGoodSyntax(testCase: ParsingTestExtension.IParsingTestCase, code: String) {
        testCase.doCodeTest(code)
        testCase.ensureCorrectReparse()
        testCase.ensureNoErrorElements()
    }

    fun testBadSyntax(testCase: ParsingTestExtension.IParsingTestCase, code: String) {
        testCase.doCodeTest(code)
        testCase.ensureCorrectReparse()
    }
}