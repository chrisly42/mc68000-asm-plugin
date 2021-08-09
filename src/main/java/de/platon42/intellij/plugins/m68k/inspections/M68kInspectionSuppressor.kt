package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.psi.M68kPsiElement
import de.platon42.intellij.plugins.m68k.psi.M68kStatement

class M68kInspectionSuppressor : InspectionSuppressor {

    companion object {
        const val MARKER = "suppress "
    }

    override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
        if (element !is M68kPsiElement) return false
        val statement = PsiTreeUtil.getParentOfType(element, M68kStatement::class.java) ?: return false
        val nextToken = PsiTreeUtil.skipWhitespacesForward(statement)
        return isSuppressedWithComment(nextToken, toolId) || isSuppressedWithComment(PsiTreeUtil.skipWhitespacesBackward(statement), toolId)
    }

    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
        return arrayOf(LineSuppressQuickFix(toolId))
    }

    private fun isSuppressedWithComment(nextToken: PsiElement?, toolId: String): Boolean {
        return if (nextToken is PsiComment) {
            val comment = nextToken.text
            comment.contains(MARKER) && comment.contains(toolId)
        } else {
            false
        }
    }

    class LineSuppressQuickFix(private val toolId: String) : SuppressQuickFix {

        override fun getFamilyName() = "Suppress for statement"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val statement = PsiTreeUtil.getParentOfType(descriptor.startElement, M68kStatement::class.java) ?: return

            val document = PsiDocumentManager.getInstance(project).getDocument(statement.containingFile) ?: return
            val lineNumber = document.getLineNumber(statement.textOffset)
            var usePrevLine = false
            val nextToken = PsiTreeUtil.skipWhitespacesForward(statement)
            if (nextToken is PsiComment) {
                val comment = nextToken.text
                if (comment.contains(MARKER)) {
                    if (!comment.contains(toolId)) {
                        document.insertString(document.getLineEndOffset(lineNumber), " $toolId")
                    }
                    return
                } else {
                    usePrevLine = true
                }
            }
            val prevToken = PsiTreeUtil.skipWhitespacesBackward(statement)
            if (prevToken is PsiComment) {
                val comment = prevToken.text
                if (comment.contains(MARKER)) {
                    if (!comment.contains(toolId)) {
                        document.insertString(document.getLineEndOffset(document.getLineNumber(prevToken.textOffset)), " $toolId")
                    }
                    return
                }
            }
            if (usePrevLine) {
                document.insertString(document.getLineStartOffset(lineNumber), "; $MARKER$toolId\n")
            } else {
                document.insertString(document.getLineEndOffset(lineNumber), "\t; $MARKER$toolId")
            }
        }

        override fun isAvailable(project: Project, context: PsiElement) = true

        override fun isSuppressAll() = false
    }
}