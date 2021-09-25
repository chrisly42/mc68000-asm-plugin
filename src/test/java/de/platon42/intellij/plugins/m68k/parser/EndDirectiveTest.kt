package de.platon42.intellij.plugins.m68k.parser

import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@TestDataSubPath("directives")
internal class EndDirectiveTest : AbstractParsingTest() {

    @Test
    @Disabled("END directive detection breaks stuff, need to find a different solution")
    internal fun end_directive(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase, """
 moveq.l #0,d0
 rts
 END narf
 äöö#öä235r2352ou485742q3535p3ü
"""
        )
    }
}