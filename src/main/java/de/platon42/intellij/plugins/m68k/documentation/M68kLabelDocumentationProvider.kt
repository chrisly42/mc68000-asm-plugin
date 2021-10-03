package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kNamedElement
import de.platon42.intellij.plugins.m68k.psi.M68kStatement

class M68kLabelDocumentationProvider : AbstractM68kDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement, originalElement: PsiElement?): String? {
        return generateDoc(element, originalElement)
    }

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return if (element is M68kGlobalLabel || element is M68kLocalLabel) {
            // TODO find out how we can generate inner links for more symbol references inside the expression (DocumentationManagerUtil)
            val statement = element.parent as M68kStatement
            var preprocessorDirective = statement.preprocessorDirective
            if ((preprocessorDirective == null) && (statement.asmInstruction == null) && (statement.macroCall == null)) {
                val nextLineStatement = PsiTreeUtil.skipWhitespacesAndCommentsForward(PsiTreeUtil.skipWhitespacesAndCommentsForward(statement))
                        as? M68kStatement
                preprocessorDirective = nextLineStatement?.preprocessorDirective
            }
            val builder = HtmlBuilder()
            builder.append(getComments(statement))
            builder.append(getDefinition(element as M68kNamedElement))
            if (preprocessorDirective != null) builder.append(getContent(preprocessorDirective))
            builder.toString()
        } else null
    }
}