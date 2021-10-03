package de.platon42.intellij.plugins.m68k.psi.utils

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import com.intellij.util.containers.addIfNotNull
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition

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
}