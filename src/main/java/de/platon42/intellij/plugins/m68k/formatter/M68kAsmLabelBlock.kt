package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock

class M68kAsmLabelBlock(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment) {

    override fun getIndent(): Indent? {
        return Indent.getAbsoluteNoneIndent()
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return Spacing.getReadOnlySpacing()
    }

    override fun isLeaf() = true

    override fun buildChildren() = emptyList<Block>()
}