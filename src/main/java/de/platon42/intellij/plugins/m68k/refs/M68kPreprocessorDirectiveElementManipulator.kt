package de.platon42.intellij.plugins.m68k.refs

import com.intellij.history.core.Paths
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.IncorrectOperationException
import de.platon42.intellij.plugins.m68k.lexer.LexerUtil
import de.platon42.intellij.plugins.m68k.psi.M68kElementFactory
import de.platon42.intellij.plugins.m68k.psi.M68kLiteralExpr
import de.platon42.intellij.plugins.m68k.psi.M68kPreprocessorDirective

class M68kPreprocessorDirectiveElementManipulator : AbstractElementManipulator<M68kPreprocessorDirective>() {

    @Throws(IncorrectOperationException::class)
    override fun handleContentChange(element: M68kPreprocessorDirective, range: TextRange, newContent: String): M68kPreprocessorDirective {
        val oldPath = LexerUtil.unquoteString(element.exprList.first().text)
            .replace('\\', '/')
            .substringBeforeLast('/', missingDelimiterValue = "")
        val newIncludeStatement = M68kElementFactory.createIncludeStatement(element.project, Paths.appended(oldPath, newContent))
        PsiTreeUtil.findChildOfType(element, M68kLiteralExpr::class.java)!!.replace(newIncludeStatement.exprList.first())
        return element
    }

    override fun getRangeInElement(element: M68kPreprocessorDirective): TextRange {
        val pathExpr = element.exprList.first()
        val unquotedPath = LexerUtil.unquoteString(pathExpr.text)
        return if (unquotedPath.length < pathExpr.textLength) {
            TextRange.from(pathExpr.startOffsetInParent + 1, unquotedPath.length)
        } else {
            pathExpr.textRangeInParent
        }
    }
}