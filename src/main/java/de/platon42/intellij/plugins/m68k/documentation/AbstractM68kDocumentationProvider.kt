package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs
import de.platon42.intellij.plugins.m68k.psi.M68kNamedElement
import de.platon42.intellij.plugins.m68k.psi.utils.M68kPsiWalkUtil
import de.platon42.intellij.plugins.m68k.settings.M68kProjectSettings

abstract class AbstractM68kDocumentationProvider : AbstractDocumentationProvider() {

    fun getSettings(element: PsiElement): M68kLexerPrefs = element.project.getService(M68kProjectSettings::class.java).settings

    fun getComments(element: PsiElement): HtmlChunk {
        val builder = HtmlBuilder()
        val comments = M68kPsiWalkUtil.collectRelatedComments(element).map { HtmlChunk.text(it.text) }
        builder.appendWithSeparators(HtmlChunk.br(), comments)
        return if (comments.isNotEmpty()) builder.wrapWith(HtmlChunk.span().attr("class", "grayed")) else HtmlChunk.empty()
    }

    fun getDefinition(element: M68kNamedElement) = getDefinition(HtmlChunk.text(element.name!!).code())

    fun getDefinition(value: String) = getDefinition(HtmlChunk.text(value))

    fun getDefinition(chunk: HtmlChunk) =
        HtmlBuilder().append(chunk).wrapWith(DocumentationMarkup.DEFINITION_ELEMENT)

    fun getContent(element: PsiElement) =
        HtmlBuilder().append(HtmlChunk.text(element.text).code()).wrapWith(DocumentationMarkup.CONTENT_ELEMENT)

    fun getContent(chunk: HtmlChunk) =
        HtmlBuilder().append(chunk).wrapWith(DocumentationMarkup.CONTENT_ELEMENT)

    fun getContent(value: String) =
        HtmlBuilder().append(value).wrapWith(DocumentationMarkup.CONTENT_ELEMENT)
}