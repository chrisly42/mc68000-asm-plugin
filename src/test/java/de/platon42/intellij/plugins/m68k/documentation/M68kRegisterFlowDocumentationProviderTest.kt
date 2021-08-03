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
    internal fun check_documentation_for_a_read_register_in_code_flow(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
label
    moveq.l #0,d0
    add.w   #1,d0
    move.l  d1,-(sp)
    move.w  d0,d1
    move.b  d2,d0
    addq.b  #1,d0
    move.w  d<caret>0,d1
    move.l  d0,d2
    move.w  d1,d2
    clr.b   d0
    moveq.l #0,d0
    move.l  (sp)+,d1
    rts
nextlabel
        """
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualToIgnoringWhitespace(
                """
<div class="definition">move instruction uses d0.w</div>
<table class="sections" style="padding-left: 8pt; padding-right: 8pt">
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>moveq.l #0,<font color="#ffc800">d0</font></code></td>
        <td valign="top"> ; <font color="#00ff00">sets d0.l</font></td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>add.w #1,<font color="#ffc800">d0</font></code></td>
        <td valign="top"> ; changes d0.w</td>
    </tr>
    <tr>
        <td valign="top">&nbsp;</td>
        <td valign="top">
            <div class="grayed">[...]</div>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>move.w <font color="#ffc800">d0</font>,d1</code></td>
        <td valign="top"> ; uses d0.w</td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>move.b d2,<font color="#ffc800">d0</font></code></td>
        <td valign="top"> ; <font color="#00ff00">sets d0.b</font></td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>addq.b #1,<font color="#ffc800">d0</font></code></td>
        <td valign="top"> ; changes d0.b</td>
    </tr>
    <tr>
        <td valign="top">--&gt;</td>
        <td valign="top"><b><code>move.w <font color="#ffc800">d0</font>,d1</code></b></td>
        <td valign="top"> ; &lt;--</td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>move.l <font color="#ffc800">d0</font>,d2</code></td>
        <td valign="top"> ; uses d0.l</td>
    </tr>
    <tr>
        <td valign="top">&nbsp;</td>
        <td valign="top">
            <div class="grayed">[...]</div>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>clr.b <font color="#ffc800">d0</font></code></td>
        <td valign="top"> ; <font color="#00ff00">sets d0.b</font></td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>moveq.l #0,<font color="#ffc800">d0</font></code></td>
        <td valign="top"> ; <font color="#00ff00">sets d0.l</font></td>
    </tr>
</table>
"""
            )
    }


    @Test
    internal fun check_documentation_for_a_written_register_in_code_flow(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "documentme.asm", """
label
    moveq.l #0,d0
    add.w   #1,d0
    move.l  d1,-(sp)
    move.w  d0,d1
    move.b  d2,d0
    addq.b  #1,d0
    move.w  d0,d<caret>1
    move.l  d0,d2
    move.w  d1,d2
    clr.b   d0
    moveq.l #0,d0
    move.l  (sp)+,d1
    rts
nextlabel
"""
        )
        assertThat(generateDocumentation(myFixture))
            .isEqualToIgnoringWhitespace(
                """
<div class="definition">move instruction sets d1.w</div>
<table class="sections" style="padding-left: 8pt; padding-right: 8pt">
    <tr>
        <td colspan="3" valign="top"><b>label</b></td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>move.l <font color="#ffc800">d1</font>,-(sp)</code></td>
        <td valign="top"> ; uses d1.l</td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>move.w d0,<font color="#ffc800">d1</font></code></td>
        <td valign="top"> ; sets d1.w</td>
    </tr>
    <tr>
        <td valign="top">&nbsp;</td>
        <td valign="top">
            <div class="grayed">[...]</div>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
    <tr>
        <td valign="top">--&gt;</td>
        <td valign="top"><b><code>move.w d0,<font color="#ffc800">d1</font></code></b></td>
        <td valign="top"> ; &lt;--</td>
    </tr>
    <tr>
        <td valign="top">&nbsp;</td>
        <td valign="top">
            <div class="grayed">[...]</div>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>move.w <font color="#ffc800">d1</font>,d2</code></td>
        <td valign="top"> ; uses d1.w</td>
    </tr>
    <tr>
        <td valign="top">&nbsp;</td>
        <td valign="top">
            <div class="grayed">[...]</div>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
    <tr>
        <td valign="top">        </td>
        <td valign="top"><code>move.l (sp)+,<font color="#ffc800">d1</font></code></td>
        <td valign="top"> ; <font color="#00ff00">sets d1.l</font></td>
    </tr>
</table>
"""
            )
    }
}