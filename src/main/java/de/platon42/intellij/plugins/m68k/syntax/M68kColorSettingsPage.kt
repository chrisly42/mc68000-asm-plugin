package de.platon42.intellij.plugins.m68k.syntax

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import de.platon42.intellij.plugins.m68k.M68kIcons.FILE
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.AREG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.BAD_CHARACTER
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.COLON
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.COMMENT
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_PREPROCESSOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_WIDTH_BS
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_WIDTH_L
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DATA_WIDTH_W
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DREG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.GLOBAL_LABEL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.LOCAL_LABEL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MACRO_CALL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MNEMONIC
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.NUMBER
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.OTHER_PREPROCESSOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SEPARATOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SPECIAL_REG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.STRING
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SYMBOL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SYMBOLDEF
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class M68kColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon {
        return FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return M68kSyntaxHighlighter(null)
    }

    @NonNls
    override fun getDemoText(): String {
        return """; This is an example assembly language program
PIC_HEIGHT = 256

        include "../includes/hardware/custom.i"

BLTHOGON MACRO                  ; macro definition
        move.w	#DMAF_SETCLR|DMAF_BLITHOG,dmacon(a5)
        ENDM

demo_init                       ; global label
        tst.w   d1
        beq.s   .skip
        PUSHM   d0-d7/a0-a6     ; this is a macro call
        lea     pd_ModViewTable(a4,d1.w),a0
        moveq.l #0,d0
        move.w  #PIC_HEIGHT-1,d7
.loop   move.l  d0,(a0)+        ; local label
        dbra    d7,.loop
        POPM
.skip   rts

hello:  dc.b   'Hello World!',10,0
        even
"""
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): @ConfigurableName String {
        return "M68k Assembly"
    }

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Global labels", GLOBAL_LABEL),
            AttributesDescriptor("Local labels", LOCAL_LABEL),
            AttributesDescriptor("Comma (separator)", SEPARATOR),
            AttributesDescriptor("Colon", COLON),
            AttributesDescriptor("Symbol definition", SYMBOLDEF),
            AttributesDescriptor("Symbol reference", SYMBOL),
            AttributesDescriptor("Assembly mnemonic", MNEMONIC),
            AttributesDescriptor("Macro invocation", MACRO_CALL),
            AttributesDescriptor("Byte/short data width", DATA_WIDTH_BS),
            AttributesDescriptor("Word data width", DATA_WIDTH_W),
            AttributesDescriptor("Long data width", DATA_WIDTH_L),
            AttributesDescriptor("Data preprocessor directives", DATA_PREPROCESSOR),
            AttributesDescriptor("Other preprocessor directives", OTHER_PREPROCESSOR),
            AttributesDescriptor("Strings", STRING),
            AttributesDescriptor("Numbers", NUMBER),
            AttributesDescriptor("Address registers", AREG),
            AttributesDescriptor("Data registers", DREG),
            AttributesDescriptor("Special registers", SPECIAL_REG),
            AttributesDescriptor("Comments", COMMENT),
            AttributesDescriptor("Bad characters", BAD_CHARACTER)
        )
    }
}