package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kAsmBlock(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    private val column: Int,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment) {

    override fun getIndent() = Indent.getNoneIndent()

    private fun normalIndent(): Indent {
        return IndentImpl(Indent.Type.SPACES, true, codeStyleSettings.indentOptions.INDENT_SIZE, false, false)
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return null
    }

    override fun isLeaf() = (node.firstChildNode == null)

    override fun buildChildren(): List<Block> {
        val subBlocks = ArrayList<Block>()
        var child = myNode.firstChildNode
        var newColumn = column
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                subBlocks.add(
                    M68kAsmBlock(
                        child,
                        Wrap.createWrap(WrapType.NONE, false),
                        Alignment.createAlignment(),
                        newColumn,
                        codeStyleSettings
                    )
                )
                if (child.elementType == M68kTypes.ASM_OP) {
                    newColumn++
                }
            }
            child = child.treeNext
        }
        return subBlocks
    }
}