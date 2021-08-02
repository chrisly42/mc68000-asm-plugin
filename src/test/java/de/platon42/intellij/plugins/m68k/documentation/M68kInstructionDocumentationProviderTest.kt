package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kInstructionDocumentationProviderTest : AbstractDocumentationProviderTest() {

    @Test
    internal fun check_documentation_inside_mnemonic(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
 mo<caret>veq.l #40,d0
        """
        )
        assertThat(generateDocumentation(myFixture)).isEqualToIgnoringWhitespace(
            """
<div class="definition"><pre><b>Move Quick</b></pre></div><div class="content"><table class="sections"><tr><td class="section" valign="top">Mnemonic</td><td class="section" valign="top">Op1</td><td class="section" valign="top">Op2</td></tr><tr><td valign="top"><div>moveq.l</div></td><td valign="top"><div>#&lt;xxx&gt;</div></td><td valign="top"><div>Dn</div></td></tr></table></div>
"""
        )
    }

    @Test
    internal fun check_documentation_inside_op_size(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
 bra.<caret>s label
        """
        )
        assertThat(generateDocumentation(myFixture)).isEqualToIgnoringWhitespace(
            """
<div class="definition">
    <pre><b>Branch</b></pre>
</div>
<div class="content">
    <table class="sections">
        <tr>
            <td class="section" valign="top">Mnemonic</td>
            <td class="section" valign="top">Op1</td>
            <td class="section" valign="top">Op2</td>
        </tr>
        <tr>
            <td valign="top">
                <div>bra.s</div>
                <div>bra.b</div>
                <div>bra.w</div>
            </td>
            <td valign="top">
                <div>(xxx).w|l</div>
            </td>
            <td valign="top"></td>
        </tr>
    </table>
</div>
            """
        )
    }

    @Test
    internal fun check_documentation_for_privileged_instruction(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
 <caret>reset
        """
        )
        assertThat(generateDocumentation(myFixture)).contains("Reset External Devices", "(privileged)")
    }

    @Test
    internal fun check_documentation_if_there_is_no_concrete_match(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
 <caret>cmp
        """
        )
        assertThat(generateDocumentation(myFixture))
            .contains("<b>Compare</b>", "<b>Compare Address</b>", "<b>Compare Immediate</b>", "<b>Compare Memory to Memory</b>")
    }
}