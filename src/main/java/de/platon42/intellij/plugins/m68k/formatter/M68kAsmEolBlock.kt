package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.Block
import com.intellij.lang.ASTNode
import com.intellij.psi.formatter.common.AbstractBlock

class M68kAsmEolBlock(node: ASTNode) : AbstractBlock(node, null, null) {

    override fun getSpacing(child1: Block?, child2: Block) = null

    override fun isLeaf() = true

    override fun buildChildren() = emptyList<Block>()
}