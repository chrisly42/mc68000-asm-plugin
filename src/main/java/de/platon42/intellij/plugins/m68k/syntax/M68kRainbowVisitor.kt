package de.platon42.intellij.plugins.m68k.syntax

import com.intellij.codeInsight.daemon.RainbowVisitor
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import de.platon42.intellij.plugins.m68k.psi.M68kAddressRegister
import de.platon42.intellij.plugins.m68k.psi.M68kDataRegister
import de.platon42.intellij.plugins.m68k.psi.M68kFile
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel

class M68kRainbowVisitor : RainbowVisitor() {

    override fun suitableForFile(file: PsiFile): Boolean = file is M68kFile

    override fun visit(element: PsiElement) {
        when (element) {
            is M68kAddressRegister -> addInfo(getInfo(element.containingFile, element, element.text, M68kSyntaxHighlighter.AREG))
            is M68kDataRegister -> addInfo(getInfo(element.containingFile, element, element.text, M68kSyntaxHighlighter.DREG))
//            is M68kGlobalLabel -> addInfo(getInfo(element.containingFile, element, element.text, M68kSyntaxHighlighter.GLOBAL_LABEL))
            is M68kLocalLabel -> addInfo(getInfo(element.containingFile, element, element.text, M68kSyntaxHighlighter.LOCAL_LABEL))
//            is M68kMacroCall -> addInfo(getInfo(element.containingFile, element.firstChild, element.macroName, M68kSyntaxHighlighter.MACRO_CALL))
        }
    }

    override fun clone(): HighlightVisitor = M68kRainbowVisitor()

}