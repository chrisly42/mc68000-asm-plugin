package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.Spacing
import com.intellij.formatting.Wrap
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kAsmBlock(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    val column: Int,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment) {

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        if (child1 is M68kAsmBlock && child2 is M68kAsmBlock) {
            val columnDiff = child2.column - child1.column
            if (columnDiff > 0) {
                var minSpaces = columnDiff * codeStyleSettings.indentOptions.INDENT_SIZE - child1.node.textLength
                while (minSpaces < 1) {
                    minSpaces += codeStyleSettings.indentOptions.INDENT_SIZE
                }
                return Spacing.createSpacing(minSpaces, minSpaces, 0, false, 0)
            }
        }
        return null
    }

    override fun isLeaf() = (node.firstChildNode == null)

    override fun buildChildren(): List<Block> {
        val subBlocks = ArrayList<Block>()
        if (myNode.elementType == M68kTypes.ASM_OP) {
            return subBlocks
        }

        var child = myNode.firstChildNode
        var newColumn = column
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                subBlocks.add(
                    M68kAsmBlock(
                        child,
                        null,
                        null,
                        newColumn,
                        codeStyleSettings
                    )
                )
                if (child.elementType == M68kTypes.ASM_OP) {
                    newColumn += 2
                }
            }
            child = child.treeNext
        }
        return subBlocks
    }
}