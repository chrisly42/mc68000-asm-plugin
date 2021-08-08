package de.platon42.intellij.plugins.m68k.refs

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.jupiter.TestDataSubPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@TestDataPath("src/test/resources/references")
@TestDataSubPath("completion")
@ExtendWith(LightCodeInsightExtension::class)
internal class M68kLocalLabelDefCompletionContributorTest {

    @Test
    internal fun completion_shows_undefined_local_labels_without_dot_prefix(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
main
.loop
        dbra    d0,.loop
        dbra    d1,.loop2
.<caret>
        bra.s   .foo
        rts

coolsubroutine
        bra.s   .narf
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder("loop2", "foo")
    }

    @Test
    internal fun completion_shows_undefined_local_labels_even_without_dot(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByText(
            "completeme.asm", """
main
.loop
        dbra    d0,.loop
        dbra    d1,.loop2
<caret>
        bra.s   .foo
        bra.s   bar$
        rts

coolsubroutine
        bra.s   .narf
        """
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings).containsExactlyInAnyOrder(".loop2", ".foo", "bar$")
    }
}