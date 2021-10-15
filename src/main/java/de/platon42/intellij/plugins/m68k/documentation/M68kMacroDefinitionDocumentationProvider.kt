package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.psi.M68kMacroCall
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition
import de.platon42.intellij.plugins.m68k.psi.utils.M68kMacroExpansionUtil

class M68kMacroDefinitionDocumentationProvider : AbstractM68kDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement, originalElement: PsiElement?): String? {
        return generateDoc(element, originalElement)
    }

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return if (element is M68kMacroDefinition) createDoc(element, originalElement, getSettings(element).maxLongDocumentationLines) else null
    }

    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return if (element is M68kMacroDefinition) createDoc(element, originalElement, getSettings(element).maxShortDocumentationLines) else null
    }

    private fun createDoc(macrodef: M68kMacroDefinition, originalElement: PsiElement?, linesLimit: Int): String {
        val macroCall = PsiTreeUtil.getParentOfType(originalElement, M68kMacroCall::class.java)
        val expandedMacro = M68kMacroExpansionUtil.expandMacro(macrodef, macroCall)
        val builder = HtmlBuilder()
        return builder
            .append(getComments(macrodef))
            .append(getDefinition(HtmlChunk.text(macrodef.name!!).code()))
            .append(
                getContent(
                    HtmlBuilder().appendWithSeparators(
                        HtmlChunk.br(),
                        expandedMacro.take(linesLimit).map { HtmlChunk.text(it).code() }.toList()
                    ).toFragment()
                )
            )
            .toString()
    }
}