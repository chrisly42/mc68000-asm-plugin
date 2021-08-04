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
import de.platon42.intellij.plugins.m68k.asm.getCcInfo
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

        val hasSameCcsForEverything = isaData.modes.map { it.affectedCc }.distinct().count() == 1
        var alreadyShownCcsOnce = false
        val mnemonicInfoRows = HtmlBuilder()
        mnemonicInfoRows.appendWithSeparators(HtmlChunk.tag("tr").child(HtmlChunk.hr().wrapWith(DocumentationMarkup.SECTION_CONTENT_CELL).attr("colspan", "3")),
            isaData.modes.map { allowedAdrMode ->
                val addressModeInfoRows = HtmlBuilder()
                val headerCells = if (allowedAdrMode.op2 != null) {
                    listOf(
                        HtmlChunk.text("Mnemonic / CCs").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL),
                        HtmlChunk.text("Operand 1").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL),
                        HtmlChunk.text("Operand 2").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL)
                    )
                } else if (allowedAdrMode.op1 != null) {
                    listOf(
                        HtmlChunk.text("Mnemonic / CCs").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL),
                        HtmlChunk.text("Operand").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL)
                    )
                } else {
                    listOf(HtmlChunk.text("Mnemonic / CCs").wrapWith(DocumentationMarkup.SECTION_HEADER_CELL))
                }
                addressModeInfoRows.append(HtmlChunk.tag("tr").children(headerCells))

                val contentBuilder = HtmlBuilder()
                val mnemonics = findOpSizeDescriptions(allowedAdrMode.size)
                    .map { HtmlChunk.text(isaData.mnemonic + it) }
                contentBuilder.appendWithSeparators(HtmlChunk.br(), mnemonics)
                contentBuilder.append(HtmlChunk.hr())
                if (alreadyShownCcsOnce && hasSameCcsForEverything) {
                    contentBuilder.append(HtmlChunk.text("Condition Codes: Same as above"))
                } else {
                    alreadyShownCcsOnce = true
                    contentBuilder.append(HtmlChunk.text("Condition Codes: "))
                    contentBuilder.append(HtmlChunk.br())
                    if (allowedAdrMode.affectedCc == 0) {
                        contentBuilder.append(HtmlChunk.text("Not affected."))
                    } else {
                        val ccMap = getCcInfo(allowedAdrMode.affectedCc)
                        val ccShortTableRows = HtmlBuilder()
                        ccShortTableRows.append(
                            HtmlChunk.tag("tr").children(ccMap.keys.map { HtmlChunk.text(it).wrapWith(DocumentationMarkup.SECTION_HEADER_CELL) })
                        )
                        ccShortTableRows.append(
                            HtmlChunk.tag("tr").children(ccMap.values.map {
                                HtmlChunk.text(it.first).wrapWith(DocumentationMarkup.SECTION_CONTENT_CELL)
                            })
                        )
                        contentBuilder.append(ccShortTableRows.wrapWith(DocumentationMarkup.SECTIONS_TABLE))
                        contentBuilder.appendWithSeparators(HtmlChunk.br(), ccMap.map {
                            HtmlChunk.text(it.key + " - " + it.value.second)
                        })
                    }
                }
                val cellsPerRow = ArrayList<HtmlChunk>(3)
                cellsPerRow.add(contentBuilder.toFragment())

                if (allowedAdrMode.op1 != null) cellsPerRow.add(collectAddressModes(allowedAdrMode.op1))
                if (allowedAdrMode.op1 != null) cellsPerRow.add(collectAddressModes(allowedAdrMode.op2))

                addressModeInfoRows.append(HtmlChunk.tag("tr").children(cellsPerRow.map { it.wrapWith(DocumentationMarkup.SECTION_CONTENT_CELL) }))
                addressModeInfoRows.toFragment()
            })

        val contentBuilder = HtmlBuilder()
        contentBuilder.append(mnemonicInfoRows.wrapWith(DocumentationMarkup.SECTIONS_TABLE))

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
            defBuilder.append(HtmlChunk.font("red").addText("(privileged)").wrapWith(HtmlChunk.p()))
        }
        return defBuilder
    }

    private fun collectAddressModes(addressModes: Set<AddressMode>?): HtmlChunk {
        if (addressModes == null) return HtmlChunk.text("")
        val modes = HtmlBuilder()
        addressModes.sortedBy(AddressMode::ordinal)
            .forEach { modes.append(HtmlChunk.text(it.syntax).wrapWith(HtmlChunk.div())) }
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