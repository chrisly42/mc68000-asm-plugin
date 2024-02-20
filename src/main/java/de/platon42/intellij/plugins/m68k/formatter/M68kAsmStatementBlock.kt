package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.Block
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import de.platon42.intellij.plugins.m68k.psi.*

class M68kAsmStatementBlock(node: ASTNode, private val codeStyleSettings: CodeStyleSettings) :
    AbstractBlock(node, null, null) {

    override fun getIndent(): Indent? {
        val statement = myNode.psi as M68kStatement
        if (((statement.asmInstruction != null) || (statement.asmInstruction != null) || (statement.preprocessorDirective != null)
                    || (statement.macroCall != null))
            && ((statement.localLabel == null) && (statement.globalLabel == null))
        ) {
            return null
        } else {
            return Indent.getAbsoluteNoneIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        if (child2 is M68kAsmBlock) {
            val indentSize = codeStyleSettings.indentOptions.INDENT_SIZE * child2.column
            if (child1 is M68kAsmEolBlock || child1 is M68kAsmCommentBlock) {
                return Spacing.createSpacing(indentSize, indentSize, 0, true, 0)
            }
            if (child1 is M68kAsmLabelBlock) {
                val spacesLeft = indentSize - child1.node.textLength
                if (spacesLeft <= 0) {
                    return Spacing.createSpacing(0, 0, 1, true, 0)
                } else {
                    return Spacing.createSpacing(spacesLeft, spacesLeft, 0, true, 0)
                }
            }
            return Spacing.createSpacing(indentSize, indentSize, 0, true, 0)
        }
        if (child2 is M68kAsmLabelBlock || child2 is M68kAsmAssignmentBlock) {
            return Spacing.createSpacing(0, 0, 0, true, 0)
        }
        return null
    }

    override fun isLeaf() = false

    override fun buildChildren(): List<Block> {
        val subBlocks = ArrayList<Block>()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                val element = child.psi
                when (element) {
                    is M68kAssignment -> subBlocks.add(M68kAsmAssignmentBlock(child, null, null, codeStyleSettings))

                    is M68kAsmInstruction, is M68kPreprocessorDirective, is M68kMacroCall ->
                        subBlocks.add(M68kAsmBlock(child, null, null, 2, codeStyleSettings))

                    is M68kGlobalLabel, is M68kLocalLabel ->
                        subBlocks.add(M68kAsmLabelBlock(child, null, null, codeStyleSettings))

                    else -> subBlocks.add(M68kAsmBlock(child, null, null, 1, codeStyleSettings))
                }
            }

            child = child.treeNext
        }
        return subBlocks
    }
}