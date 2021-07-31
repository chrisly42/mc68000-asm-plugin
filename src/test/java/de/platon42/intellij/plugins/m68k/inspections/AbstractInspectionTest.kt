package de.platon42.intellij.plugins.m68k.inspections

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
}