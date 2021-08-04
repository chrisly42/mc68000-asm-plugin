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
<div class="definition">
    <pre><b>Move Quick</b></pre>
</div>
<div class="content">
    <table class="sections">
        <tr>
            <td class="section" valign="top">Mnemonic / CCs</td>
            <td class="section" valign="top">Operand 1</td>
            <td class="section" valign="top">Operand 2</td>
        </tr>
        <tr>
            <td valign="top">moveq.l
                <hr/>
                Condition Codes: <br/>
                <table class="sections">
                    <tr>
                        <td class="section" valign="top">X</td>
                        <td class="section" valign="top">N</td>
                        <td class="section" valign="top">Z</td>
                        <td class="section" valign="top">V</td>
                        <td class="section" valign="top">C</td>
                    </tr>
                    <tr>
                        <td valign="top">-</td>
                        <td valign="top">*</td>
                        <td valign="top">*</td>
                        <td valign="top">0</td>
                        <td valign="top">0</td>
                    </tr>
                </table>
                X - Not affected<br/>N - From result (usually if negative)<br/>Z - From result (usually if zero)<br/>V - Always cleared<br/>C - Always cleared
            </td>
            <td valign="top">
                <div>#&lt;xxx&gt;</div>
            </td>
            <td valign="top">
                <div>Dn</div>
            </td>
        </tr>
    </table>
</div>
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
            <td class="section" valign="top">Mnemonic / CCs</td>
            <td class="section" valign="top">Operand</td>
        </tr>
        <tr>
            <td valign="top">bra.s<br/>bra.b<br/>bra.w
                <hr/>
                Condition Codes: <br/>Not affected.
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