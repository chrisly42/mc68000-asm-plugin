package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import com.intellij.util.containers.ContainerUtil
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.jupiter.TestDataPath
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@TestDataPath("src/test/resources/formatting")
@ExtendWith(LightCodeInsightExtension::class)
internal class M68kAsmFormatterTest {

    @Test
    @Disabled
    internal fun check_if_formatting_works_as_expected(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("basic_before.asm")
        val settings = CodeStyle.getLanguageSettings(myFixture.file)
        settings.indentOptions?.INDENT_SIZE = 6
        settings.indentOptions?.CONTINUATION_INDENT_SIZE = 9
        WriteCommandAction.runWriteCommandAction(myFixture.project)
        {
            CodeStyleManager.getInstance(myFixture.project).reformatText(
                myFixture.file,
                ContainerUtil.newArrayList(myFixture.file.textRange)
            )
        }
        myFixture.checkResultByFile("basic_after.asm")
    }

    @Test
    internal fun check_formatting_for_mnemonics(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("mnemonics_before.asm")
        val settings = CodeStyle.getLanguageSettings(myFixture.file)
        settings.indentOptions?.INDENT_SIZE = 8
        WriteCommandAction.runWriteCommandAction(myFixture.project)
        {
            CodeStyleManager.getInstance(myFixture.project).reformatText(
                myFixture.file,
                ContainerUtil.newArrayList(myFixture.file.textRange)
            )
        }
        myFixture.checkResultByFile("mnemonics_after.asm")
    }
}
