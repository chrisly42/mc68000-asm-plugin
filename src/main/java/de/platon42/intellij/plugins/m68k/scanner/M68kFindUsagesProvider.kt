package de.platon42.intellij.plugins.m68k.scanner

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import de.platon42.intellij.plugins.m68k.lexer.M68kLexer
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs
import de.platon42.intellij.plugins.m68k.psi.*
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls

class M68kFindUsagesProvider : FindUsagesProvider {

    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            M68kLexer(M68kLexerPrefs()),  // FIXME Oh no! More Prefs!
            TokenSet.create(M68kTypes.SYMBOLDEF, M68kTypes.GLOBAL_LABEL_DEF, M68kTypes.LOCAL_LABEL_DEF, M68kTypes.SYMBOL),
            TokenSet.create(M68kTypes.COMMENT),
            TokenSet.create(M68kTypes.STRINGLIT, M68kTypes.DECIMAL, M68kTypes.HEXADECIMAL, M68kTypes.OCTAL, M68kTypes.BINARY),
            TokenSet.EMPTY
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }

    override fun getHelpId(psiElement: PsiElement): @NonNls String? {
        return null
    }

    override fun getType(element: PsiElement): @Nls String {
        return when (element) {
            is M68kGlobalLabel -> "global label"
            is M68kLocalLabel -> "local label"
            is M68kSymbolDefinition -> "symbol definition"
            is M68kSymbolReference -> "symbol reference"
            else -> ""
        }
    }

    override fun getDescriptiveName(element: PsiElement): @Nls String {
        return when (element) {
            is M68kGlobalLabel -> element.name!!
            is M68kLocalLabel -> element.name!!
            is M68kSymbolDefinition -> element.parent.text
            is M68kSymbolReference -> element.symbolName
            else -> ""
        }
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): @Nls String {
        return getDescriptiveName(element)
    }
}