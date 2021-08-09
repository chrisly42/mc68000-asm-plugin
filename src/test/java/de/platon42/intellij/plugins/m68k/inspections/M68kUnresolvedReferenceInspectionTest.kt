package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.MyFixture
import org.junit.jupiter.api.Test

internal class M68kUnresolvedReferenceInspectionTest : AbstractInspectionTest() {

    @Test
    internal fun shows_warning_on_undefined_symbol_label(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnresolvedReferenceInspection::class.java)
        myFixture.configureByText("unresolvedref.asm", " bra foobar")
        assertHighlightings(myFixture, 1, "Cannot resolve symbol 'foobar'")
    }

    @Test
    internal fun shows_warning_on_undefined_local_label(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnresolvedReferenceInspection::class.java)
        myFixture.configureByText("unresolvedref.asm", " bra .foobar")
        assertHighlightings(myFixture, 1, "Cannot resolve local label '.foobar'")
    }

    @Test
    internal fun shows_warning_on_undefined_macro(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.enableInspections(M68kUnresolvedReferenceInspection::class.java)
        myFixture.configureByText("unresolvedref.asm", " foobar")
        assertHighlightings(myFixture, 1, "Cannot resolve macro 'foobar'")
    }
}