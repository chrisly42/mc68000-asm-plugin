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
    internal fun check_documentation_for_a_register_in_code_flow(@MyFixture myFixture: CodeInsightTestFixture) {
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
<div class="definition">move instruction reads d0.w</div>
<table class="sections" style="padding-left: 8pt; padding-right: 8pt">
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>moveq.l #0,<font color="red">d0</font></code></td>
        <td valign="top"> ; <i>sets d0.l</i></td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>add.w #1,<font color="red">d0</font></code></td>
        <td valign="top"> ; <i>modifies d0.w</i></td>
    </tr>
    <tr>
        <td valign="top">&nbsp;</td>
        <td valign="top">
            <div class="grayed">[...]</div>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>move.w <font color="red">d0</font>,d1</code></td>
        <td valign="top"> ; <i>reads d0.w</i></td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>move.b d2,<font color="red">d0</font></code></td>
        <td valign="top"> ; <i>sets d0.b</i></td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>addq.b #1,<font color="red">d0</font></code></td>
        <td valign="top"> ; <i>modifies d0.b</i></td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><b><code>move.w <font color="red">d0</font>,d1</code></b></td>
        <td valign="top"> ; &lt;--</td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>move.l <font color="red">d0</font>,d2</code></td>
        <td valign="top"> ; <i>reads d0.l</i></td>
    </tr>
    <tr>
        <td valign="top">&nbsp;</td>
        <td valign="top">
            <div class="grayed">[...]</div>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>clr.b <font color="red">d0</font></code></td>
        <td valign="top"> ; <i>sets d0.b</i></td>
    </tr>
    <tr>
        <td valign="top"></td>
        <td valign="top"><code>moveq.l #0,<font color="red">d0</font></code></td>
        <td valign="top"> ; <i>sets d0.l</i></td>
    </tr>
</table>
<div class="content"/>
            """.trimIndent()
            )
    }
}