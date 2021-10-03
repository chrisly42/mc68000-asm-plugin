package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kSymbolDefinitionDocumentationProviderTest : AbstractDocumentationProviderTest() {

    @Test
    internal fun check_documentation_for_a_symbol_definition(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
PIC_WIDTH = 320 ; width of picture
 move.w #PIC_WIDT<caret>H,d0
        """
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualTo("<span class=\"grayed\">; width of picture</span><div class=\"definition\"><code>PIC_WIDTH</code></div><div class=\"content\"><code>320</code></div>")
    }
}