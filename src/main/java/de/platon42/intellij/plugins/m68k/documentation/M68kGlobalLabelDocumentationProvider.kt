package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kStatement

class M68kGlobalLabelDocumentationProvider : AbstractDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement, originalElement: PsiElement?): String? {
        return generateDoc(element, originalElement)
    }

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return if (element is M68kGlobalLabel) {
            // TODO find out how we can generate inner links for more symbol references inside the expression (DocumentationManagerUtil)
            val statement = element.parent as M68kStatement
            var preprocessorDirective = statement.preprocessorDirective
            if ((preprocessorDirective == null) && (statement.asmInstruction == null) && (statement.macroCall == null)) {
                val nextLineStatement = PsiTreeUtil.skipWhitespacesAndCommentsForward(PsiTreeUtil.skipWhitespacesAndCommentsForward(statement))
                        as? M68kStatement
                preprocessorDirective = nextLineStatement?.preprocessorDirective
            }
            val content = if (preprocessorDirective != null)
                DocumentationMarkup.CONTENT_START + StringUtil.escapeXmlEntities(preprocessorDirective.text) + DocumentationMarkup.CONTENT_END
            else ""
            val comments = SmartList<String>()
            var prevToken: PsiElement? = statement
            do {
                prevToken = PsiTreeUtil.skipWhitespacesBackward(prevToken)
                if (prevToken !is PsiComment) break
                comments.add(prevToken.text)
            } while (true)
            val commentpart =
                if (comments.isNotEmpty()) comments.asReversed().joinToString("<br>", DocumentationMarkup.GRAYED_START, DocumentationMarkup.GRAYED_END) else ""

            commentpart +
                    DocumentationMarkup.DEFINITION_START + StringUtil.escapeXmlEntities(element.name!!) + DocumentationMarkup.DEFINITION_END +
                    content
        } else null
    }
}