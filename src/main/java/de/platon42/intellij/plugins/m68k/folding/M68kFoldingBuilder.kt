package de.platon42.intellij.plugins.m68k.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import de.platon42.intellij.plugins.m68k.psi.M68kFile
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kStatement
import de.platon42.intellij.plugins.m68k.psi.utils.M68kPsiWalkUtil.getBeginningOfRelatedComment
import de.platon42.intellij.plugins.m68k.psi.utils.M68kPsiWalkUtil.isSignificantLine

class M68kFoldingBuilder : FoldingBuilderEx(), DumbAware {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        if (root !is M68kFile) return FoldingDescriptor.EMPTY

        val descriptors = ArrayList<FoldingDescriptor>()
        var lineElement = root.firstChild
        var foldingOpen = false
        var foldingStart: PsiElement? = null
        var foldingName: String? = null
        var lastStatement = lineElement as? M68kStatement
        while (lineElement != null) {
            if (lineElement is M68kMacroDefinition) {
                val fd = FoldingDescriptor(lineElement, lineElement.startOffset, lineElement.endOffset, null, "[[[ MACRO " + lineElement.name!! + " ]]]")
                descriptors.add(fd)
            }
            if (lineElement is M68kStatement) {
                val label = lineElement.globalLabel
                if (label != null) {
                    foldingOpen = true
                    foldingStart = getBeginningOfRelatedComment(lineElement)
                    foldingName = "[[[ ${label.name} ]]]"
                }
                lastStatement = lineElement
            }
            lineElement = PsiTreeUtil.skipWhitespacesAndCommentsForward(lineElement)
            if (foldingOpen) {
                val stopIt = (lineElement == null) || isSignificantLine(lineElement)
                if (stopIt) {
                    val fd = FoldingDescriptor(
                        foldingStart!!.parent!!,
                        foldingStart.startOffsetInParent, lastStatement!!.textRangeInParent.endOffset,
                        null, foldingName!!
                    )
                    descriptors.add(fd)
                    foldingOpen = false
                }
            }
        }
        return descriptors.toArray(FoldingDescriptor.EMPTY)
    }

    override fun getPlaceholderText(node: ASTNode): String? {
        return "..."
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }
}