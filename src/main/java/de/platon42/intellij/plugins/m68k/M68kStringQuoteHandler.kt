package de.platon42.intellij.plugins.m68k

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kStringQuoteHandler : SimpleTokenSetQuoteHandler(M68kTypes.STRINGLIT)