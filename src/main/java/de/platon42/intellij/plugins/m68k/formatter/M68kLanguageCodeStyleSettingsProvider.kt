package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import de.platon42.intellij.plugins.m68k.MC68000Language

class M68kLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage() = MC68000Language.INSTANCE

    override fun getCodeSample(settingsType: SettingsType) = """; This is an example assembly language program
PIC_HEIGHT = 256

        include "../includes/hardware/custom.i"

BLTHOGON MACRO                  ; macro definition
        move.w	#DMAF_SETCLR|DMAF_BLITHOG,dmacon(a5) ; hog!
        ENDM

demo_init                       ; global label
        tst.w   d1
        beq.s   .skip
        PUSHM   d0-d7/a0-a6     ; this is a macro call
        lea     hello(pc),a1
        lea     pd_ModViewTable(a4,d1.w),a0
        moveq.l #0,d0
        move.w  #PIC_HEIGHT-1,d7
.loop   move.l  d0,(a0)+        ; local label
        dbra    d7,.loop
        POPM
.skip   rts

irq:    move.l  a0,-(sp)
        move    usp,a0
        move.l  a0,${'$'}400.w
        move.l  (sp)+,a0
        rte

hello:  dc.b   'Hello World!',10,0
        even
        dc.w    *-hello         ; length of string
"""
}