package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kMacroDefinitionDocumentationProviderTest : AbstractDocumentationProviderTest() {

    @Test
    internal fun check_expanded_documentation_for_a_macro_definition(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
MOLV<caret>ANIA MACRO ; the bestest macro
 move.l \1,d0
 mulu   d0,d0
 ENDM
        """
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualTo("<span class=\"grayed\">; the bestest macro</span><div class=\"definition\"><code>MOLVANIA</code></div><div class=\"content\"><code> move.l \\1,d0</code><br/><code> mulu   d0,d0</code></div>")
    }

    @Test
    internal fun check_expanded_documentation_for_a_macro_invocation(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
MOLVANIA MACRO ; the bestest macro
 move.l \1,d0
 mulu   d0,d0
 ENDM

 MOLV<caret>ANIA d1
        """
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualTo("<span class=\"grayed\">; the bestest macro</span><div class=\"definition\"><code>MOLVANIA</code></div><div class=\"content\"><code> move.l d1,d0</code><br/><code> mulu   d0,d0</code></div>")
    }

    @Test
    internal fun check_expanded_documentation_for_a_macro_invocation_with_lots_of_params(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
; compress nibbles
DTW MACRO
 dc.b ((\1)<<4)|(\2),((\3)<<4)|(\4),((\5)<<4)|(\6),((\7)<<4)|(\8),((\9)<<4)|(\a),((\b)<<4)|(\c),((\d)<<4)|(\e),((\f)<<4)|(\g)
 ENDM

 DTW<caret> 15,14,14,10,1,2,3,4,10,11,10,12,5,3,9,10
        """
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualTo("<span class=\"grayed\">; compress nibbles</span><div class=\"definition\"><code>DTW</code></div><div class=\"content\"><code> dc.b ((15)&lt;&lt;4)|(14),((14)&lt;&lt;4)|(10),((1)&lt;&lt;4)|(2),((3)&lt;&lt;4)|(4),((10)&lt;&lt;4)|(11),((10)&lt;&lt;4)|(12),((5)&lt;&lt;4)|(3),((9)&lt;&lt;4)|(10)</code></div>")
    }
}