package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider

class M68kAsmFormattingModelBuilder : FormattingModelBuilder {

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings

        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            M68kAsmRootBlock(formattingContext.node, codeStyleSettings),
            codeStyleSettings
        )
    }
}