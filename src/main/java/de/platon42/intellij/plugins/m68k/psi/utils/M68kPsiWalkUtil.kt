package de.platon42.intellij.plugins.m68k.psi.utils

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import com.intellij.util.containers.addIfNotNull
import de.platon42.intellij.plugins.m68k.psi.M68kFile
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kStatement

object M68kPsiWalkUtil {

    fun collectRelatedComments(element: PsiElement): List<PsiComment> {
        val comments = SmartList<PsiComment>()
        val eolComment = if (element is M68kMacroDefinition) {
            // macro definition needs special handling of EOL comments
            PsiTreeUtil.findChildOfType(element, PsiComment::class.java)
        } else {
            PsiTreeUtil.skipWhitespacesForward(element) as? PsiComment
        }

        comments.addIfNotNull(eolComment)
        var prevToken: PsiElement? = element
        do {
            prevToken = PsiTreeUtil.skipWhitespacesBackward(prevToken)
            if (prevToken !is PsiComment) break
            comments.add(prevToken)
        } while (true)

        return comments.reversed()
    }

    fun getBeginningOfRelatedComment(lineElement: PsiElement): PsiElement {
        // go back to catch comments that are not more than one empty line away
        var relStart = lineElement
        var prevLine = relStart.prevSibling ?: return lineElement
        var eolCount = 2
        do {
            if (prevLine is PsiComment) {
                relStart = prevLine
                eolCount = 1
            } else if (prevLine is PsiWhiteSpace) {
                if (--eolCount < 0) break
            } else {
                break
            }
            prevLine = prevLine.prevSibling ?: break
        } while (true)
        return relStart
    }

    fun isSignificantLine(element: PsiElement) = when (element) {
        is M68kStatement -> (element.assignment != null) || (element.globalLabel != null)
        is M68kMacroDefinition -> true
        else -> false
    }

    fun findStatementForElement(psiElement: PsiElement): M68kStatement? {
        if (psiElement is M68kStatement) return psiElement
        if (psiElement.parent is M68kFile) {
            return PsiTreeUtil.getPrevSiblingOfType(psiElement, M68kStatement::class.java)
        }
        return PsiTreeUtil.getParentOfType(psiElement, M68kStatement::class.java);
    }
}