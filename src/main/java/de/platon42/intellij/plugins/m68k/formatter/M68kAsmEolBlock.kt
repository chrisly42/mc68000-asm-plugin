package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.formatter.common.AbstractBlock

class M68kAsmEolBlock(node: ASTNode) : AbstractBlock(
    node, Wrap.createWrap(WrapType.NONE, false),
    Alignment.createAlignment()
) {

    override fun getIndent() = Indent.getNoneIndent()

    override fun getSpacing(child1: Block?, child2: Block) = null

    override fun isLeaf() = true

    override fun buildChildren() = emptyList<Block>()
}