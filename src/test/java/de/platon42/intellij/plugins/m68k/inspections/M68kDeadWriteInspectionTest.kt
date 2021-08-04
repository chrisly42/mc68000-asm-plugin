package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class M68kDeadWriteInspectionTest : AbstractInspectionTest() {

    @Test
    internal fun find_direct_dead_write_to_register(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.l  #123,d1
    add.l   d0,d2
    moveq.l #123,d1
        """
        )
        assertHighlightings(myFixture, 1, "Register d1 is overwritten later without being used")
    }

    @Test
    internal fun find_direct_dead_write_to_register_with_modification(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.l  #123,d1
    add.l   d0,d1
    moveq.l #123,d1
        """
        )
        assertHighlightings(myFixture, 1, "Register d1 is overwritten later without being used")
    }

    @Test
    internal fun no_dead_write_to_register_due_to_part_modification(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.l  #123,d1
    add.b   d1,d0
    moveq.l #123,d1
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun use_of_condition_code_causes_weak_warning(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.l  #123,d1
    scc     d0
    moveq.l #123,d1
        """
        )
        assertHighlightings(myFixture, 1, "Register d1 is overwritten later (only CC evaluated?)")
    }

    @Test
    internal fun use_of_conditional_instruction_for_non_conditional_causes_full_warning(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.l  #123,d1
    st      d0
    moveq.l #123,d1
        """
        )
        assertHighlightings(myFixture, 1, "Register d1 is overwritten later without being used")
    }

    @Test
    internal fun use_of_condition_code_after_modification_causes_no_warning(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.l  #123,d1
    add.b   d0,d1
    seq     .foo
    moveq.l #123,d1
.foo
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun use_of_control_flow_causes_no_warning(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.l  #123,d1
    add.b   d0,d1
    bra.s   .foo
    moveq.l #123,d1
.foo
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun movem_can_cause_multiple_warnings(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    movem.l (a0),d0-d7
    add.l   d0,d1
    moveq.l #123,d3
    clr.l   #123,d6
    movem.l (a0),d5-d7
        """
        )
        assertHighlightings(myFixture, 1, "Register d5 is overwritten later without being used")
        assertHighlightings(myFixture, 1, "Register d6 is overwritten later without being used")
        assertHighlightings(myFixture, 1, "Register d7 is overwritten later without being used")
    }

    @Test
    internal fun overwrite_in_same_instruction_with_read_causes_no_warning(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.w  #123,d3
    move.w  (a0,d3.w),d3
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun partial_overwrite_in_causes_no_warning(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.w  #123,d3
    move.b  d2,d3
        """
        )
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun oversized_overwrite_in_causes_warning(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kDeadWriteInspection::class.java)
        myFixture.configureByText(
            "deadwrite.asm", """
    move.w  #123,d3
    move.l  d2,d3
        """
        )
        assertHighlightings(myFixture, 1, "Register d3 is overwritten later without being used")
    }
}