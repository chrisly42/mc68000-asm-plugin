package de.platon42.intellij.plugins.m68k.psi

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import com.intellij.util.containers.addIfNotNull

object M68kPsiWalkUtil {

    fun collectRelatedComments(element: PsiElement): List<PsiComment> {
        val comments = SmartList<PsiComment>()
        val eolComment = PsiTreeUtil.skipWhitespacesForward(element) as? PsiComment
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