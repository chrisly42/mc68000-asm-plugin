package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.Block
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiComment
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kStatement
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kAsmRootBlock(node: ASTNode, private val codeStyleSettings: CodeStyleSettings) : AbstractBlock(node, null, null) {

    override fun getIndent(): Indent? {
        return Indent.getAbsoluteNoneIndent()
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        if (child2 is M68kAsmEolBlock) {
            return Spacing.createSpacing(0, 0, 0, false, 0)
            //return null
        }
        if (child2 is M68kAsmMacroDefBlock) {
            return child2.getSpacing(child1, child2)
        }
        if (child2 is M68kAsmCommentBlock) {
            if (child1 is M68kAsmStatementBlock) {
                val indentSize = codeStyleSettings.indentOptions.INDENT_SIZE
                val oddIdent = indentSize - (child1.node.textLength % indentSize)
                return Spacing.createSpacing(oddIdent, Integer.MAX_VALUE, 0, false, 0)
            } else {
                return Spacing.getReadOnlySpacing()
            }
        }
        if (child2.subBlocks.isNotEmpty()) {
            return child2.getSpacing(child1, child2.subBlocks.first())
        }
        return null
    }

    override fun isLeaf(): Boolean {
        return false
    }

    override fun buildChildren(): List<Block> {
        val subBlocks = ArrayList<Block>()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType == M68kTypes.EOL) {
                subBlocks.add(M68kAsmEolBlock(child))
            } else if (child.elementType != TokenType.WHITE_SPACE) {
                if (child.psi is M68kStatement) {
                    subBlocks.add(M68kAsmStatementBlock(child, codeStyleSettings))
                } else if (child.psi is M68kMacroDefinition) {
                    subBlocks.add(M68kAsmMacroDefBlock(child, codeStyleSettings))
                } else if (child.psi is PsiComment) {
                    subBlocks.add(M68kAsmCommentBlock(child, codeStyleSettings))
                } else {
                    subBlocks.add(M68kAsmBlock(child, null, null, 0, codeStyleSettings))
                }
            }

            child = child.treeNext
        }
        return subBlocks
    }
}