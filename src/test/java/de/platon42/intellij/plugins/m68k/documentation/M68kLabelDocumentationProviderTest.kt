package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kLabelDocumentationProviderTest : AbstractDocumentationProviderTest() {

    @Test
    internal fun check_documentation_for_a_label_definition(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
 rts
; inputs: d0 = number

; output: d0 = square
squareme: include "fancysquarecode.asm" ; code -> cool!
 rts
 bsr square<caret>me
        """
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualTo("<span class=\"grayed\">; inputs: d0 = number<br/>; output: d0 = square<br/>; code -&gt; cool!</span><div class=\"definition\"><code>squareme</code></div><div class=\"content\"><code>include &quot;fancysquarecode.asm&quot;</code></div>")
    }

    @Test
    internal fun check_documentation_for_a_label_definition_next_line(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
; output: d0 = square
.square<caret>me:
; oh man
 include "fancysquarecode.asm"
        """
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualTo("<span class=\"grayed\">; output: d0 = square</span><div class=\"definition\"><code>.squareme</code></div><div class=\"content\"><code>include &quot;fancysquarecode.asm&quot;</code></div>")
    }
}