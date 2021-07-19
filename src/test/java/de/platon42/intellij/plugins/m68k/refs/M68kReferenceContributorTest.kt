package de.platon42.intellij.plugins.m68k.refs

import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.jupiter.TestDataSubPath
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@TestDataPath("src/test/resources/references")
@TestDataSubPath("labels")
@ExtendWith(LightCodeInsightExtension::class)
internal class M68kReferenceContributorTest : AbstractM68kTest() {

    @Test
    internal fun reference_to_dot_local_label(@MyFixture myFixture: CodeInsightTestFixture) {
//        val reference = myFixture.getReferenceAtCaretPositionWithAssertion("dot_local_label.asm")
//        assertThat(reference.element).isInstanceOf(M68kSym)
        myFixture.configureByFile("dot_local_label.asm")
        assertThat(myFixture.elementAtCaret).isInstanceOf(M68kLocalLabel::class.java)
            .extracting(PsiElement::getText).isEqualTo(".loop")
    }

    @Test
    internal fun reference_to_multiple_conditional_local_label_dollar(@MyFixture myFixture: CodeInsightTestFixture) {
        val reference = myFixture.getReferenceAtCaretPositionWithAssertion("multiple_conditional_local_label_dollar.asm")
        assertThat(reference.element).isInstanceOf(M68kLocalLabel::class.java)
        myFixture.configureByFile("multiple_conditional_local_label_dollar.asm")
        assertThat(myFixture.elementAtCaret).isInstanceOf(M68kLocalLabel::class.java)
            .extracting(PsiElement::getText).isEqualTo("loop$")
    }
}