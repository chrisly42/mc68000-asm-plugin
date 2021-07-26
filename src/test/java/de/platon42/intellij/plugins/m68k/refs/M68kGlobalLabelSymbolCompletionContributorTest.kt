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
internal class M68kGlobalLabelSymbolCompletionContributorTest : AbstractM68kTest() {

    @Test
    internal fun completion_shows_fitting_symbols_and_labels_after_first_letter(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
PIC_WIDTH = 100
PIC_HEIGHT = 100
platon = 42
HURZ = 220
NP equ 10

Irrelevant_Label:
Problem_solver:
 move.l P<caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder("PIC_HEIGHT", "PIC_WIDTH", "Problem_solver", "NP")
    }

    @Test
    internal fun completion_shows_all_symbols_and_labels_inside_expr(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
PIC_WIDTH = 100
HURZ = 220
NP equ 10

Problem_solver:
 move.l 145+<caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder(
            "a0", "a1", "a2", "a3", "a4", "a5", "a6", "sp",
            "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7",
            "pc",
            "HURZ", "NP", "PIC_WIDTH", "Problem_solver"
        )
    }

    @Test
    internal fun completion_puts_register_on_top(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
PIC_WIDTH = 320
PIC_HEIGHT equ 256
DEBUG_LEVEL set 10
double_buffer_1 = 1
auto_complete_2 = 1

entry:
        bsr     init
        bsr     main
        bsr     exit
        move.l  d1<caret>
        rts
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactly("d1", "double_buffer_1")
    }

    @Test
    internal fun complete_several_basic_symbols_or_labels(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("basic_completion.asm")
        myFixture.completeBasicAllCarets(null)
        myFixture.checkResultByFile("basic_completion_after_op.asm")
    }
}