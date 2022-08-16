package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
@TestDataPath("src/test/resources/documentation")
abstract class AbstractDocumentationProviderTest : AbstractM68kTest() {

    fun generateDocumentation(myFixture: CodeInsightTestFixture): String? {
        // FIXME migrate to DocumentationTarget
        val docElement = DocumentationManager.getInstance(myFixture.project).findTargetElement(myFixture.editor, myFixture.file)
        val provider = DocumentationManager.getProviderFromElement(docElement)

        return provider.generateDoc(docElement, myFixture.file.findElementAt(myFixture.caretOffset))
    }
}