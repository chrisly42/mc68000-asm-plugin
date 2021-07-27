package de.platon42.intellij.plugins.m68k.refs

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.jupiter.TestDataSubPath
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@TestDataPath("src/test/resources/references")
@TestDataSubPath("completion")
@ExtendWith(LightCodeInsightExtension::class)
internal class M68kMacroCallCompletionContributorTest : AbstractM68kTest() {

    @Test
    internal fun completion_shows_mnemonics_and_macros(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """

noppy   MACRO
        ENDM

 no<caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder("nop", "not", "noppy")
    }
}