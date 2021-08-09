package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class M68kInspectionSuppressorTest : AbstractInspectionTest() {

    companion object {
        private const val SUPPRESS_FOR_STATEMENT = "Suppress for statement"
    }

    @Test
    internal fun suppresses_inspection_with_end_of_line_comment(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " rts d0 ; suppress M68kSyntax")
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun suppresses_inspection_with_full_line_comment_on_line_before(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", "; suppress M68kSyntax\n rts d0")
        assertThat(myFixture.doHighlighting()).isEmpty()
    }

    @Test
    internal fun suppression_works_without_prior_comment(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " rts<caret> d0")
        executeSuppressAction(myFixture, SUPPRESS_FOR_STATEMENT)
        myFixture.checkResult(" rts d0\t; suppress M68kSyntax")
    }

    @Test
    internal fun suppression_works_with_prior_suppression_comment(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " rts<caret> d0; suppress foobar")
        executeSuppressAction(myFixture, SUPPRESS_FOR_STATEMENT)
        myFixture.checkResult(" rts d0; suppress foobar M68kSyntax")
    }

    @Test
    internal fun suppression_works_with_prior_suppression_comment_in_line_above(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", "; suppress foobar\n rts<caret> d0")
        executeSuppressAction(myFixture, SUPPRESS_FOR_STATEMENT)
        myFixture.checkResult("; suppress foobar M68kSyntax\n rts d0")
    }

    @Test
    internal fun suppression_works_by_adding_suppression_comment_in_line_above(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kSyntaxInspection::class.java)
        myFixture.configureByText("syntax.asm", " rts<caret> d0 ; unrelated comment")
        executeSuppressAction(myFixture, SUPPRESS_FOR_STATEMENT)
        myFixture.checkResult("; suppress M68kSyntax\n rts d0 ; unrelated comment")
    }

    private fun executeSuppressAction(myFixture: CodeInsightTestFixture, suppressAction: String) {
        val highlightInfos = myFixture.doHighlighting()
        assertThat(highlightInfos).hasSize(1)
        val quickFixPair = highlightInfos[0].quickFixActionRanges[0]
        val intentionActionDescriptor = quickFixPair.first
        val element = myFixture.file.findElementAt(quickFixPair.second.startOffset)!!
        val suppressQuickFix = intentionActionDescriptor.getOptions(element, myFixture.editor)!!
            .first { it.text == suppressAction }
        myFixture.launchAction(suppressQuickFix)
    }
}