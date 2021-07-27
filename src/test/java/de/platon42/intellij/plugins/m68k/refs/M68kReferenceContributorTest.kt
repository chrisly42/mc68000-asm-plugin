package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.jupiter.TestDataSubPath
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import de.platon42.intellij.plugins.m68k.psi.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@TestDataPath("src/test/resources/references")
@ExtendWith(LightCodeInsightExtension::class)
internal class M68kReferenceContributorTest : AbstractM68kTest() {

    @Test
    @TestDataSubPath("labels")
    internal fun reference_to_dot_local_label_can_be_renamed(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("dot_local_label.asm")
        assertThat(myFixture.elementAtCaret).isInstanceOf(M68kLocalLabel::class.java)
            .extracting(PsiElement::getText).isEqualTo(".loop")
        myFixture.renameElementAtCaret(".narf")
        myFixture.checkResultByFile("dot_local_label_after_rename.asm")
    }

    @Test
    @TestDataSubPath("labels")
    internal fun reference_to_multiple_conditional_local_label_dollar_and_variants(@MyFixture myFixture: CodeInsightTestFixture) {
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion("multiple_conditional_local_label_dollar.asm")
        assertThat(reference.element).isInstanceOf(M68kSymbolReference::class.java)
        assertThat(reference.variants).hasOnlyElementsOfType(LookupElementBuilder::class.java)
            .extracting<String> { (it as LookupElementBuilder).lookupString }
            .containsExactlyInAnyOrderElementsOf(listOf("loop$", "loop$", ".skip"))
    }

    @Test
    @TestDataSubPath("labels")
    internal fun reference_to_global_label_can_be_renamed(@MyFixture myFixture: CodeInsightTestFixture) {
        val file = myFixture.configureByFile("global_labels.asm")
        assertThat(myFixture.elementAtCaret).isInstanceOf(M68kGlobalLabel::class.java)
            .extracting(PsiElement::getText).isEqualTo("main")

        val reference = file.findReferenceAt(myFixture.editor.caretModel.offset - 1)!!
        assertThat(reference).isInstanceOf(M68kGlobalLabelSymbolReference::class.java)
        assertThat(reference.variants).isEmpty()

        myFixture.renameElementAtCaret("intro_main")

        myFixture.checkResultByFile("global_labels_after_rename.asm")
    }

    @Test
    @TestDataSubPath("symbols")
    internal fun reference_to_symbol_can_be_renamed(@MyFixture myFixture: CodeInsightTestFixture) {
        val file = myFixture.configureByFile("symbol_assignment.asm")
        assertThat(myFixture.elementAtCaret).isInstanceOf(M68kSymbolDefinition::class.java)
            .extracting(PsiElement::getText).isEqualTo("PIC_HEIGTH")

        myFixture.renameElementAtCaret("PIC_HEIGHT")

        val reference = file.findReferenceAt(myFixture.editor.caretModel.offset)!!
        assertThat(reference).isInstanceOf(M68kGlobalLabelSymbolReference::class.java)
        assertThat(reference.variants).isEmpty()

        myFixture.checkResultByFile("symbol_assignment_after_rename.asm")
    }

    @Test
    @TestDataSubPath("macros")
    internal fun reference_to_macro_can_be_renamed(@MyFixture myFixture: CodeInsightTestFixture) {
        val file = myFixture.configureByFile("macros.asm")
        val elementAtCaret = myFixture.elementAtCaret
        assertThat(elementAtCaret).isInstanceOf(M68kMacroDefinition::class.java)
        val macroDef = elementAtCaret as M68kMacroDefinition
        assertThat(macroDef.macroNameDefinition.text).isEqualTo("PUTMSG")

        myFixture.renameElementAtCaret("PRINTF")

        val reference = file.findReferenceAt(myFixture.editor.caretModel.offset)!!
        assertThat(reference).isInstanceOf(M68kMacroReference::class.java)
        assertThat(reference.variants).isEmpty()

        myFixture.checkResultByFile("macros_after_rename.asm")
    }
}