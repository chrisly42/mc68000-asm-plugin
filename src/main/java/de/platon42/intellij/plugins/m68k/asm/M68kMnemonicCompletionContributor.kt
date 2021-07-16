package de.platon42.intellij.plugins.m68k.asm

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kMnemonicCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(M68kTypes.MACRO_INVOKATION), object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                resultSet.addAllElements(M68kIsa.mnemonics.map(LookupElementBuilder::create))
            }
        })
    }
}