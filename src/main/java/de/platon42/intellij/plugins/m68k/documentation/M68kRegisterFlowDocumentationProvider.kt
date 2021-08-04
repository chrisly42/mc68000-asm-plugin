package de.platon42.intellij.plugins.m68k.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor
import de.platon42.intellij.plugins.m68k.asm.*
import de.platon42.intellij.plugins.m68k.asm.Register.Companion.getRegFromName
import de.platon42.intellij.plugins.m68k.psi.*
import de.platon42.intellij.plugins.m68k.psi.M68kAddressModeUtil.getOtherReadWriteModifyRegisters
import de.platon42.intellij.plugins.m68k.psi.M68kAddressModeUtil.getReadWriteModifyRegisters
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.findExactIsaDataAndAllowedAdrModeForInstruction
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.getOpSizeOrDefault
import de.platon42.intellij.plugins.m68k.utils.M68kIsaUtil.modifyRwmWithOpsize

class M68kRegisterFlowDocumentationProvider : AbstractDocumentationProvider() {

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        if (element is M68kDataRegister || element is M68kAddressRegister) {
            return createDoc(element as M68kRegister, 100) // TODO make this configurable
        }
        return null
    }

    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?): String? {
        if (element is M68kDataRegister || element is M68kAddressRegister) {
            return createDoc(element as M68kRegister, 4) // TODO make this configurable
        }
        return null
    }

    private fun createDoc(element: M68kRegister, linesLimit: Int): String? {
        val register = getRegFromName(element.text)
        val addressingMode = PsiTreeUtil.getParentOfType(element, M68kAddressingMode::class.java) ?: return null
        val asmInstruction = PsiTreeUtil.getParentOfType(addressingMode, M68kAsmInstruction::class.java) ?: return null

        val (isaData, adrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(asmInstruction) ?: return "unknown instruction"

        val cursorInstRwmRegs = evaluateRegisterUse(asmInstruction, adrMode, register)
        val opSize = getOpSizeOrDefault(asmInstruction.asmOp.opSize, adrMode)

        val rn = register.regname
        val thisInfo = cursorInstRwmRegs
            .joinToString(separator = ", ", prefix = "${isaData.mnemonic} instruction ") { rwmToDisplayText(it, rn) }
        val totalRwm = cursorInstRwmRegs.reduce(Int::or)

        val firstOp = asmInstruction.addressingModeList[0] == addressingMode
        val cursorRwm = modifyRwmWithOpsize((adrMode.modInfo ushr if (firstOp) RWM_OP1_SHIFT else RWM_OP2_SHIFT) and RWM_OP_MASK, opSize)
        val backtrace = ArrayList<HtmlChunk>()
        val missingBits = if (cursorRwm and RWM_SET_L != 0) {
            if (totalRwm and RWM_SET_L == RWM_SET_L) {
                backtrace.add(
                    HtmlChunk.tag("tr")
                        .children(
                            DocumentationMarkup.SECTION_CONTENT_CELL.attr("colspan", "3")
                                .child(HtmlChunk.text("Register result is fully defined by this instruction."))
                        )
                )
                0
            } else {
                (RWM_SET_L and RWM_SIZE_MASK) and totalRwm.inv()
            }
        } else {
            (RWM_SET_L and RWM_SIZE_MASK) and ((cursorRwm and RWM_MODIFY_L) ushr 8)
        }
        val initialStatement: M68kStatement = asmInstruction.parent as M68kStatement
        val localLabelName = PsiTreeUtil.findChildOfType(initialStatement, M68kLocalLabel::class.java)?.name ?: "-->"
        backtrace.add(
            HtmlChunk.tag("tr")
                .children(DocumentationMarkup.SECTION_CONTENT_CELL.child(HtmlChunk.text(localLabelName)))
                .children(highlightRegister(asmInstruction, register).bold().wrapWith(DocumentationMarkup.SECTION_CONTENT_CELL))
                .children(DocumentationMarkup.SECTION_CONTENT_CELL.child(HtmlChunk.text(" ; <--")))
        )

        backtrace.addAll(analyseFlow(register, missingBits, true, initialStatement, linesLimit) {
            PsiTreeUtil.getPrevSiblingOfType(
                it,
                M68kStatement::class.java
            )
        })
        backtrace.reverse()
        val traceBits = (cursorRwm or (cursorRwm ushr 8)) and RWM_SIZE_MASK
        backtrace.addAll(analyseFlow(register, traceBits, false, initialStatement, linesLimit) {
            PsiTreeUtil.getNextSiblingOfType(
                it,
                M68kStatement::class.java
            )
        })

        val statementRows = HtmlBuilder()
        backtrace.forEach(statementRows::append)
        val builder = HtmlBuilder()
        builder.append(HtmlChunk.text(thisInfo).wrapWith(DocumentationMarkup.DEFINITION_ELEMENT))
        builder.append(statementRows.wrapWith(DocumentationMarkup.SECTIONS_TABLE.style("padding-left: 8pt; padding-right: 8pt")))
        return builder.toString()
    }

    private fun analyseFlow(
        register: Register,
        rwmBits: Int,
        globalLabelBreaksOnInitialStatement: Boolean,
        startingStatement: M68kStatement,
        linesLimit: Int,
        direction: (statement: M68kStatement) -> M68kStatement?
    ): List<HtmlChunk> {
        var missingBits = rwmBits
        var currStatement = startingStatement
        val statementLines = ArrayList<HtmlChunk>()
        val rn = register.regname
        var addAbrevDots = false
        var lines = 0
        while (missingBits > 0) {
            val globalLabel = PsiTreeUtil.findChildOfType(currStatement, M68kGlobalLabel::class.java)
            if ((globalLabel != null) && (globalLabelBreaksOnInitialStatement || (currStatement !== startingStatement))) {
                statementLines.add(
                    HtmlChunk.tag("tr")
                        .children(DocumentationMarkup.SECTION_CONTENT_CELL.attr("colspan", "3").child(HtmlChunk.text(globalLabel.name!!).bold()))
                )
                break
            }
            currStatement = direction.invoke(currStatement) ?: break
            val currAsmInstruction = PsiTreeUtil.getChildOfType(currStatement, M68kAsmInstruction::class.java) ?: continue
            if (checkIfInstructionUsesRegister(currAsmInstruction, register)) {
                if (addAbrevDots) {
                    ++lines
                    statementLines.add(createAbbreviationDots())
                }
                if (++lines > linesLimit) {
                    if (!addAbrevDots) {
                        statementLines.add(createAbbreviationDots())
                    }
                    break
                }
                addAbrevDots = false
                val (_, currAdrMode) = findExactIsaDataAndAllowedAdrModeForInstruction(currAsmInstruction) ?: continue

                val localLabelName = PsiTreeUtil.findChildOfType(currStatement, M68kLocalLabel::class.java)?.name ?: "        "
                val currRwms = evaluateRegisterUse(currAsmInstruction, currAdrMode, register)
                val currTotalRwm = currRwms.reduce(Int::or)
                val lineInfo = currRwms
                    .map {
                        val text = HtmlChunk.text(rwmToDisplayText(it, rn))
                        if ((missingBits and it) > 0) text.wrapWith(HtmlChunk.font("#" + ColorUtil.toHex(JBColor.GREEN))) else text
                    }
                if ((currTotalRwm and RWM_SET_L) > 0) {
                    missingBits = missingBits and currTotalRwm.inv()
                }
                val lineBuilder = HtmlBuilder()
                lineBuilder.append(" ; ")
                lineBuilder.appendWithSeparators(HtmlChunk.text(", "), lineInfo)
                    .wrapWith(HtmlChunk.div())

                statementLines.add(
                    HtmlChunk.tag("tr")
                        .children(DocumentationMarkup.SECTION_CONTENT_CELL.child(HtmlChunk.text(localLabelName)))
                        .children(highlightRegister(currAsmInstruction, register).wrapWith(DocumentationMarkup.SECTION_CONTENT_CELL))
                        .children(lineBuilder.wrapWith(DocumentationMarkup.SECTION_CONTENT_CELL))
                )
            } else {
                addAbrevDots = true
            }
        }
        return statementLines
    }

    private fun highlightRegister(currAsmInstruction: M68kAsmInstruction, register: Register): HtmlChunk {
        val builder = HtmlBuilder()
        val plainText = currAsmInstruction.text
        var startPos = 0
        val rn = if (register == Register.A7) listOf(register.regname, "sp") else listOf(register.regname)
        do {
            val indexPos = plainText.indexOfAny(rn, startPos, ignoreCase = true)
            if (indexPos >= 0) {
                builder.append(HtmlChunk.text(plainText.substring(startPos until indexPos)))
                startPos = indexPos + register.regname.length
                builder.append(HtmlChunk.text(plainText.substring(indexPos until startPos)).wrapWith(HtmlChunk.font("#" + ColorUtil.toHex(JBColor.ORANGE))))
            } else {
                builder.append(HtmlChunk.text(plainText.substring(startPos)))
            }
        } while (indexPos >= 0)
        return builder.toFragment().code()
    }

    private fun rwmToDisplayText(rwm: Int, rn: String) =
        when (rwm) {
            RWM_READ_B -> "uses $rn.b"
            RWM_READ_W -> "uses $rn.w"
            RWM_READ_L -> "uses $rn.l"
            RWM_MODIFY_B -> "changes $rn.b"
            RWM_MODIFY_W -> "changes $rn.w"
            RWM_MODIFY_L -> "changes $rn.l"
            RWM_SET_B -> "sets $rn.b"
            RWM_SET_W -> "sets $rn.w"
            RWM_SET_L -> "sets $rn.l"
            else -> "uhm?"
        }

    private fun createAbbreviationDots() = HtmlChunk.tag("tr")
        .children(DocumentationMarkup.SECTION_CONTENT_CELL.child(HtmlChunk.nbsp()))
        .children(
            DocumentationMarkup.SECTION_CONTENT_CELL.child(
                HtmlChunk.text("[...]").wrapWith(HtmlChunk.div().attr("class", "grayed"))
            )
        )
        .children(DocumentationMarkup.SECTION_CONTENT_CELL.child(HtmlChunk.nbsp()))

    private fun evaluateRegisterUse(
        asmInstruction: M68kAsmInstruction,
        adrMode: AllowedAdrMode,
        register: Register
    ): List<Int> {
        val opSize = getOpSizeOrDefault(asmInstruction.asmOp.opSize, adrMode)

        val rwm1 = modifyRwmWithOpsize((adrMode.modInfo ushr RWM_OP1_SHIFT) and RWM_OP_MASK, opSize)
        val rwm2 = if (asmInstruction.addressingModeList.size > 1) modifyRwmWithOpsize((adrMode.modInfo ushr RWM_OP2_SHIFT) and RWM_OP_MASK, opSize) else 0
        return getReadWriteModifyRegisters(asmInstruction.addressingModeList[0], rwm1).asSequence()
            .plus(getReadWriteModifyRegisters(asmInstruction.addressingModeList.getOrNull(1), rwm2))
            .plus(getOtherReadWriteModifyRegisters(adrMode.modInfo))
            .filter { it.first == register }
            .map { it.second }
            .toList()
    }

    private fun checkIfInstructionUsesRegister(instruction: M68kAsmInstruction, register: Register): Boolean {
        if (instruction.addressingModeList.isEmpty()) {
            return false
        }
        return instruction.addressingModeList.any { aml -> getReadWriteModifyRegisters(aml, 0).any { it.first == register } }
    }

    override fun getCustomDocumentationElement(editor: Editor, file: PsiFile, contextElement: PsiElement?, targetOffset: Int): PsiElement? {
        if (contextElement == null) return null
        if (contextElement is M68kDataRegister || contextElement is M68kAddressRegister) return contextElement
        val parent = contextElement.parent ?: return null
        if (parent is M68kDataRegister || parent is M68kAddressRegister) return parent
        return null
    }
}
