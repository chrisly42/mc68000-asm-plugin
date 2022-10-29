package de.platon42.intellij.plugins.m68k.asm

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kDirectiveCompletionContributorTest : AbstractM68kTest() {

    @Test
    internal fun completion_shows_plain_directive(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
 inc<caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder("include", "incbin", "incdir")
    }

    @Test
    internal fun completion_shows_other_directive(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
 IF<caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder(
            "IF",
            "IFB",
            "IFC",
            "IFD",
            "IFEQ",
            "IFGE",
            "IFGT",
            "IFLE",
            "IFLT",
            "IFMACROD",
            "IFMACROND",
            "IFNB",
            "IFNC",
            "IFND",
            "IFNE",
            "ENDIF"
        )
    }

    @Test
    internal fun completion_shows_data_directives(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
 dc<caret>
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder("dc", "dc.b", "dc.w", "dc.l", "dcb", "dcb.b", "dcb.w", "dcb.l", "data_c")
    }
}