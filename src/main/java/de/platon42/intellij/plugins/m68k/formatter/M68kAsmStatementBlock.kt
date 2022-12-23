package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import de.platon42.intellij.plugins.m68k.psi.*

class M68kAsmStatementBlock(node: ASTNode, private val codeStyleSettings: CodeStyleSettings) :
    AbstractBlock(node, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment()) {

    override fun getIndent(): Indent {
        val statement = myNode.psi as M68kStatement
        if (((statement.asmInstruction != null) || (statement.preprocessorDirective != null)
                    || (statement.macroCall != null))
            && ((statement.localLabel == null) && (statement.globalLabel == null))
        ) {
            return IndentImpl(Indent.Type.SPACES, true, codeStyleSettings.indentOptions.INDENT_SIZE, false, false)
        } else {
            return Indent.getAbsoluteNoneIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        if (child2 is M68kAsmBlock) {
            val indentSize = codeStyleSettings.indentOptions.INDENT_SIZE
            if (child1 is M68kAsmEolBlock) {
                return Spacing.createSpacing(1, indentSize, 0, true, 0)
            }
            if (child1 is M68kAsmLabelBlock) {
                val spacesLeft = indentSize - child1.node.textLength
                if (spacesLeft <= 0) {
                    return Spacing.createSpacing(1, indentSize, 1, false, 0)
                } else {
                    return Spacing.createSpacing(1, spacesLeft, 0, false, 0)
                }
            }
            return Spacing.createSpacing(1, indentSize, 0, false, 0)
        }
        if (child2 is M68kAsmLabelBlock) {
            return Spacing.createSpacing(0, 0, 0, false, 0)
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
                    is M68kAssignment ->
                        subBlocks.add(
                            M68kAsmBlock(
                                child,
                                Wrap.createWrap(WrapType.NORMAL, false),
                                Alignment.createAlignment(),
                                0,
                                codeStyleSettings
                            )
                        )

                    is M68kAsmInstruction, is M68kPreprocessorDirective, is M68kMacroCall ->
                        subBlocks.add(
                            M68kAsmBlock(
                                child,
                                Wrap.createWrap(WrapType.NORMAL, false),
                                Alignment.createAlignment(),
                                1,
                                codeStyleSettings
                            )
                        )

                    is M68kGlobalLabel, is M68kLocalLabel ->
                        subBlocks.add(
                            M68kAsmLabelBlock(
                                child,
                                Wrap.createWrap(WrapType.NONE, false),
                                Alignment.createAlignment(),
                                codeStyleSettings
                            )
                        )


                    else ->
                        subBlocks.add(
                            M68kAsmBlock(
                                child,
                                Wrap.createWrap(WrapType.NONE, false),
                                Alignment.createAlignment(),
                                1,
                                codeStyleSettings
                            )
                        )
                }
            }

            child = child.treeNext
        }
        return subBlocks
    }
}