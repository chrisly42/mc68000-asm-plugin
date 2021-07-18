package de.platon42.intellij.plugins.m68k.syntax

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import de.platon42.intellij.plugins.m68k.psi.M68kTypes

class M68kPairedBraceMatcher : PairedBraceMatcher {

    companion object {
        val bracePairs = arrayOf(
            BracePair(M68kTypes.ROUND_L, M68kTypes.ROUND_R, false)
        )
    }

    override fun getPairs(): Array<BracePair> = bracePairs

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?) = true

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int) = openingBraceOffset
}