package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class M68kUnexpectedConditionalInstructionInspectionTest : AbstractInspectionTest() {

    @Test
    internal fun movea_causes_warning_when_used_for_conditional_branching(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnexpectedConditionalInstructionInspection::class.java)
        myFixture.configureByText(
            "unexpectedcc.asm", """
    move.l  d0,a1
    bne.s   .cont
    rts
.cont
        """
        )
        assertHighlightings(myFixture, 1, "Condition codes unaffected by instruction (movea - Move Address)")
    }

    @Test
    internal fun no_warning_on_consecutive_conditional_branches(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnexpectedConditionalInstructionInspection::class.java)
        myFixture.configureByText(
            "unexpectedcc.asm", """
	move.b  P61_arplist(pc,d0),d0
	beq.b   .arp0
	bmi.b   .arp1
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun no_warning_on_macro_call_inbetween(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnexpectedConditionalInstructionInspection::class.java)
        myFixture.configureByText(
            "unexpectedcc.asm", """
    move.l  pd_PalCurShamPalPtr(a4),a1
    PALSTEPDOWN
    bne.s   .loopline
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun no_warning_flow_control_instruction(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnexpectedConditionalInstructionInspection::class.java)
        myFixture.configureByText(
            "unexpectedcc.asm", """
    bsr     foo
    bne.s   .loopline
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun warning_on_conditional_set_series_with_suba(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnexpectedConditionalInstructionInspection::class.java)
        myFixture.configureByText(
            "unexpectedcc.asm", """
    sub.l   a0,a0
    seq     d0
    sne     d1
        """
        )
        assertHighlightings(myFixture, 1, "Condition codes unaffected by instruction (suba - Subtract Address)")
    }
}