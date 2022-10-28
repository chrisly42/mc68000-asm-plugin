package de.platon42.intellij.plugins.m68k.folding

import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.jupiter.TestDataPath
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@TestDataPath("src/test/resources/folding")
@ExtendWith(LightCodeInsightExtension::class)
internal class M68kFoldingBuilderTest {

    @Test
    internal fun check_documentation_for_a_symbol_definition(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.testFolding(myFixture.testDataPath + "/folding.asm")
    }
}