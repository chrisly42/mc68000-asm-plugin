package de.platon42.intellij.plugins.m68k.asm

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import de.platon42.intellij.plugins.m68k.M68kIcons
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kMnemonicCompletionContributor : CompletionContributor() {

    companion object {
        val MNEMONICS = M68kIsa.mnemonics
            .map { PrioritizedLookupElement.withPriority(LookupElementBuilder.create(it).withIcon(M68kIcons.MNEMONIC), 10.0) }
    }

    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(M68kTypes.MACRO_INVOCATION), object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                resultSet.addAllElements(MNEMONICS)
            }
        })
    }
}