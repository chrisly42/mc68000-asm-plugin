package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import de.platon42.intellij.plugins.m68k.psi.M68kAssignment
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition

class M68kSymbolDefinitionDocumentationProvider : AbstractDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement, originalElement: PsiElement?): String? {
        return generateDoc(element, originalElement)
    }

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return if (element is M68kSymbolDefinition) {
            // TODO find out how we can generate inner links for more symbol references inside the expression (DocumentationManagerUtil)
            val value = (element.getParent() as M68kAssignment).expr.text
            DocumentationMarkup.DEFINITION_START + StringUtil.escapeXmlEntities(element.name!!) + DocumentationMarkup.DEFINITION_END +
                    DocumentationMarkup.CONTENT_START + StringUtil.escapeXmlEntities(value) + DocumentationMarkup.CONTENT_END
        } else null
    }
}