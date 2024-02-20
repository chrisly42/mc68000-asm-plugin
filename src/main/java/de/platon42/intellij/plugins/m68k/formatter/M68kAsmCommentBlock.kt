package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.Block
import com.intellij.formatting.Spacing
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock

class M68kAsmCommentBlock(
    node: ASTNode,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, null, null) {

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return Spacing.getReadOnlySpacing()
    }

    override fun isLeaf() = true

    override fun buildChildren() = emptyList<Block>()
}