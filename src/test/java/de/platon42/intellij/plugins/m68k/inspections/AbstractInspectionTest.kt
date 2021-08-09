package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
@TestDataPath("src/test/resources/inspections")
abstract class AbstractInspectionTest : AbstractM68kTest() {

    protected fun assertHighlightings(myFixture: CodeInsightTestFixture, count: Int, snippet: String) {
        assertThat(myFixture.doHighlighting())
            .areExactly(count, Condition({ it.description?.contains(snippet) ?: false }, "containing"))
    }

    protected fun executeQuickFixes(myFixture: CodeInsightTestFixture, regex: Regex, expectedFixes: Int) {
        val quickfixes = getQuickFixes(myFixture, regex, expectedFixes)
        assertThat(quickfixes.groupBy { it.familyName }).hasSize(1)
        quickfixes.forEach(myFixture::launchAction)
    }

    protected fun getQuickFixes(myFixture: CodeInsightTestFixture, regex: Regex, expectedFixes: Int): List<IntentionAction> {
        val quickfixes = myFixture.getAllQuickFixes().filter { it.text.matches(regex) }
        assertThat(quickfixes).`as`("Fixes matched by $regex: ${myFixture.getAllQuickFixes().map { it.text }}").hasSize(expectedFixes)
        return quickfixes
    }
}