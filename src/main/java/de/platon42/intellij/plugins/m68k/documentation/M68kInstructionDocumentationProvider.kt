package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import de.platon42.intellij.plugins.m68k.asm.AddressMode
import de.platon42.intellij.plugins.m68k.asm.IsaData
import de.platon42.intellij.plugins.m68k.asm.M68kIsa
import de.platon42.intellij.plugins.m68k.psi.M68kAsmInstruction
import de.platon42.intellij.plugins.m68k.psi.M68kAsmOp
import de.platon42.intellij.plugins.m68k.psi.M68kOperandSize
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.findExactIsaDataForInstruction
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.findOpSizeDescriptions

class M68kInstructionDocumentationProvider : AbstractDocumentationProvider() {

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        if (element is M68kAsmInstruction) {
            val builder = HtmlBuilder()
            val isaData = findExactIsaDataForInstruction(element)
            if (isaData == null) {
                M68kIsa.findMatchingInstructions(element.asmOp.mnemonic).forEach { buildDocForIsaData(it, builder) }
            } else {
                buildDocForIsaData(isaData, builder)
            }
            return builder.toString()
        }
        return null
    }

    private fun buildDocForIsaData(isaData: IsaData, builder: HtmlBuilder) {
        val defBuilder = createDefinition(isaData)
        builder.append(defBuilder.wrapWith(DocumentationMarkup.DEFINITION_ELEMENT))

        val bindingRows = HtmlBuilder()
        val headerCells = listOf(
            HtmlChunk.text("Mnemonic").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL),
            HtmlChunk.text("Op1").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL),
            HtmlChunk.text("Op2").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL)
        )
        bindingRows.append(HtmlChunk.tag("tr").children(headerCells))
        isaData.modes.forEach { allowedAdrMode ->
            val mnemonics = findOpSizeDescriptions(allowedAdrMode.size)
                .map { HtmlChunk.text(isaData.mnemonic + it).wrapWith(HtmlChunk.p()) }
            bindingRows.append(
                HtmlChunk.tag("tr").children(
                    DocumentationMarkup.SECTION_CONTENT_CELL.children(mnemonics),
                    DocumentationMarkup.SECTION_CONTENT_CELL.child(collectAddressModes(allowedAdrMode.op1)),
                    DocumentationMarkup.SECTION_CONTENT_CELL.child(collectAddressModes(allowedAdrMode.op2))
                )
            )
        }

        val contentBuilder = HtmlBuilder()
        contentBuilder.append(bindingRows.br().wrapWith(DocumentationMarkup.SECTIONS_TABLE))

        builder.append(contentBuilder.wrapWith(DocumentationMarkup.CONTENT_ELEMENT))
    }

    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?): String? {
        if (element is M68kAsmInstruction) {
            val isaData = findExactIsaDataForInstruction(element) ?: return null
            val defBuilder = createDefinition(isaData)

            val builder = HtmlBuilder()
            builder.append(defBuilder.wrapWith(DocumentationMarkup.DEFINITION_ELEMENT))
            return builder.toString()
        }
        return null
    }

    private fun createDefinition(isaData: IsaData): HtmlBuilder {
        val defBuilder = HtmlBuilder()
        defBuilder.append(HtmlChunk.text(isaData.description).bold().wrapWith("pre"))
        if (isaData.isPrivileged) {
            defBuilder.append(HtmlChunk.font("red").addText("(privileged)").wrapWith("p"))
        }
        return defBuilder
    }

    private fun collectAddressModes(addressModes: Set<AddressMode>?): HtmlChunk {
        if (addressModes == null) return HtmlChunk.text("")
        val modes = HtmlBuilder()
        addressModes.sortedBy(AddressMode::ordinal)
            .forEach { modes.append(HtmlChunk.text(it.syntax).wrapWith(HtmlChunk.p())) }
        return modes.toFragment()
    }

    override fun getCustomDocumentationElement(editor: Editor, file: PsiFile, contextElement: PsiElement?, targetOffset: Int): PsiElement? {
        if (contextElement == null) return null
        if (contextElement is M68kAsmInstruction) return contextElement
        val parent = contextElement.parent ?: return null
        if (parent is M68kAsmInstruction) return parent
        if (parent is M68kAsmOp) return parent.parent
        if (parent is M68kOperandSize) return parent.parent.parent
        return null
    }
}