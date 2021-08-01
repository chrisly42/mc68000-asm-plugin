package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kRegisterFlowDocumentationProviderTest : AbstractDocumentationProviderTest() {

    @Test
    internal fun check_documentation_for_a_symbol_definition(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
label
    moveq.l #0,d0
    add.w   #1,d0
    move.w  d0,d1
    move.b  d2,d0
    addq.b  #1,d0
    move.w  d<caret>0,d1
    move.l  d0,d2
    move.w  d1,d2
    rts
        """
        )
        assertThat(generateDocumentation(myFixture)).isEqualTo("d0")
    }
}