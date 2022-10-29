package de.platon42.intellij.plugins.m68k.asm

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kMnemonicCompletionContributorTest : AbstractM68kTest() {

    @Test
    internal fun completion_shows_all_move_mnemonics_after_first_letters(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
 mo<caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder("move", "moveq", "movea", "movem", "movep", "movec", "moves")
    }

    @Test
    internal fun completion_shows_all_mnemonics_after_label(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
label: <caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsAnyElementsOf(M68kIsa.mnemonics)
    }

    @Test
    internal fun completion_shows_all_mnemonics_after_space(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
 <caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsAnyElementsOf(M68kIsa.mnemonics)
    }
}