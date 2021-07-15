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
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.COMMENT
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.DREG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.GLOBAL_LABEL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.KEYWORD
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.LOCAL_LABEL
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.MNEMONIC
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.NUMBER
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.PREPROCESSOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SEPARATOR
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SPECIAL_REG
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.STRING
import de.platon42.intellij.plugins.m68k.syntax.M68kSyntaxHighlighter.Companion.SYMBOL
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class M68kColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon {
        return FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return M68kSyntaxHighlighter()
    }

    @NonNls
    override fun getDemoText(): String {
        return """; This is an example assembly language program
PIC_HEIGHT = 256

        include "../includes/hardware/custom.i"

BLTHOGON MACRO
        move.w	#DMAF_SETCLR|DMAF_BLITHOG,dmacon(a5)
        ENDM

demo_init                       ; global label
        PUSHM   d0-d7/a0-a6     ; this is a macro
        lea     pd_ModViewTable(a4),a0
        moveq.l #0,d0
        move.w  #PIC_HEIGHT-1,d7
.loop   move.l  d0,(a0)+        ; local label
        dbra    d7,.loop
        POPM
        rts
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
                AttributesDescriptor("SEPARATOR", SEPARATOR),
                AttributesDescriptor("GLOBAL_LABEL", GLOBAL_LABEL),
                AttributesDescriptor("LOCAL_LABEL", LOCAL_LABEL),
                AttributesDescriptor("SYMBOL", SYMBOL),
                AttributesDescriptor("MNEMONIC", MNEMONIC),
                AttributesDescriptor("KEYWORD", KEYWORD),
                AttributesDescriptor("PREPROCESSOR", PREPROCESSOR),
                AttributesDescriptor("STRING", STRING),
                AttributesDescriptor("NUMBER", NUMBER),
                AttributesDescriptor("AREG", AREG),
                AttributesDescriptor("DREG", DREG),
                AttributesDescriptor("SPECIAL_REG", SPECIAL_REG),
                AttributesDescriptor("COMMENT", COMMENT),
                AttributesDescriptor("BAD_CHARACTER", BAD_CHARACTER))
    }
}