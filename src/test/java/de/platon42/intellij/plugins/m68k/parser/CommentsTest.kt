package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Test

@TestDataSubPath("comments")
internal class CommentsTest : AbstractParsingTest() {

    @Test
    internal fun asterisk_comment_at_sol(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "*********** comment\n")
    }

    @Test
    internal fun asterisk_comment_after_space(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "    *********** comment\n")
    }

    @Test
    internal fun hash_comment_at_sol(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "#comment   \n")
    }

    @Test
    internal fun hash_comment_after_space(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "    #comment  \n")
    }

    @Test
    internal fun semicolon_comment_at_sol(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "; comment  \n")
    }

    @Test
    internal fun semicolon_comment_after_space(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "    ; comment  \n")
    }

    @Test
    internal fun empty_and_blank_lines(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "; hi\n\n\n  * foo\n\n\n    \n\n # bar\n\n")
    }

    @Test
    internal fun end_of_line_comments(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, "foo = 1234; hi\n\n\nlabel; foo\n\n\n add.w d0,d0;narf   \n\n")
    }
}