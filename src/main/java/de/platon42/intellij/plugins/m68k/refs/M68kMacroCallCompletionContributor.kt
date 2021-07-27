package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import de.platon42.intellij.plugins.m68k.psi.M68kLookupUtil
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kMacroCallCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(M68kTypes.MACRO_INVOCATION), object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                resultSet.addAllElements(M68kLookupUtil.findAllMacroDefinitions(parameters.originalFile.project).map(LookupElementBuilder::createWithIcon))
            }
        })
    }
}