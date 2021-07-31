package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.MyFixture
import org.junit.jupiter.api.Test

internal class M68kSyntaxInspectionTest : AbstractInspectionTest() {

    @Test
    internal fun shows_error_on_instruction_with_unexpected_operands(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " rts d0")
        assertHighlightings(myFixture, 1, "No operands expected for 'rts'")
    }

    @Test
    internal fun shows_error_on_instruction_with_unexpected_second_operand(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " jmp label,d0")
        assertHighlightings(myFixture, 1, "Second operand 'd0' unexpected")
    }

    @Test
    internal fun shows_unsupported_addressing_mode_in_first_operand(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " moveq.l 123,d0")
        assertHighlightings(myFixture, 1, "Unsupported addressing mode '123' for first operand")
    }

    @Test
    internal fun shows_unsupported_addressing_mode_in_second_operand(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " moveq.l #123,a0")
        assertHighlightings(myFixture, 1, "Unsupported addressing mode 'a0' for second operand")
    }

    @Test
    internal fun shows_unsupported_swapped_addressing_mode(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " cmp.l d0,(a0)")
        assertHighlightings(myFixture, 1, "Unsupported addressing modes for operands in this order for 'cmp.l'")
    }

    @Test
    internal fun shows_error_on_unsized_instruction_with_size(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " rts.l")
        assertHighlightings(myFixture, 1, "Instruction 'rts' is unsized")
    }

    @Test
    internal fun shows_error_on_unsupported_op_size_for_b(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " btst.l #5,(a0)")
        assertHighlightings(myFixture, 1, "Operation size '.l' unsupported (should be .b)")
    }

    @Test
    internal fun shows_error_on_unsupported_op_size_for_w(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " divu.b #5,d0")
        assertHighlightings(myFixture, 1, "Operation size '.b' unsupported (should be .w)")
    }

    @Test
    internal fun shows_error_on_unsupported_op_size_for_l(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " moveq.w #5,d0")
        assertHighlightings(myFixture, 1, "Operation size '.w' unsupported (should be .l)")
    }

    @Test
    internal fun shows_error_on_unsupported_op_size_for_w_or_l(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " addq.b #5,a0")
        assertHighlightings(myFixture, 1, "Operation size '.b' unsupported (should be .w or .l)")
    }

    @Test
    internal fun shows_error_on_unsupported_op_size_for_other(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " bsr.l label")
        assertHighlightings(myFixture, 1, "Operation size '.l' unsupported")
    }

    @Test
    internal fun shows_error_wrong_mode_on_instruction_with_special_reg(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " move.l usp,d0")
        assertHighlightings(myFixture, 1, "Unsupported addressing mode for 'move.l'")
    }

    @Test
    internal fun shows_error_wrong_register_on_instruction_with_special_reg(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " move.l vbr,a0")
        assertHighlightings(myFixture, 1, "Unsupported addressing mode for 'move.l'")
    }
}