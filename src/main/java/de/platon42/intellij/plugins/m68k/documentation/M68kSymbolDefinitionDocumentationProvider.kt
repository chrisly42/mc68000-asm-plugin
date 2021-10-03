package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.psi.PsiElement
import de.platon42.intellij.plugins.m68k.psi.M68kAssignment
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition

class M68kSymbolDefinitionDocumentationProvider : AbstractM68kDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement, originalElement: PsiElement?): String? {
        return generateDoc(element, originalElement)
    }

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return if (element is M68kSymbolDefinition) {
            // TODO find out how we can generate inner links for more symbol references inside the expression (DocumentationManagerUtil)
            val assignment = element.parent as M68kAssignment
            HtmlBuilder()
                .append(getComments(assignment.parent))
                .append(getDefinition(element))
                .append(getContent(assignment.expr))
                .toString()
        } else null
    }
}