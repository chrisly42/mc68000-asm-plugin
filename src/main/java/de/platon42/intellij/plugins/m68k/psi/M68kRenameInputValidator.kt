package de.platon42.intellij.plugins.m68k.psi

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.StandardPatterns
import com.intellij.psi.PsiElement
import com.intellij.refactoring.rename.RenameInputValidator
import com.intellij.util.ProcessingContext
import java.util.regex.Pattern

class M68kRenameInputValidator : RenameInputValidator {

    override fun getPattern(): ElementPattern<out PsiElement?> = StandardPatterns.or(
        PlatformPatterns.psiElement(M68kGlobalLabel::class.java),
        PlatformPatterns.psiElement(M68kLocalLabel::class.java),
        PlatformPatterns.psiElement(M68kSymbolDefinition::class.java)
    )

    override fun isInputValid(newName: String, element: PsiElement, context: ProcessingContext): Boolean {
        return when (element) {
            is M68kGlobalLabel, is M68kSymbolDefinition -> SYMBOL_PATTERN.matcher(newName).matches()
            is M68kLocalLabel -> LOCAL_LABEL_PATTERN.matcher(newName).matches()
            else -> false
        }
    }

    companion object {
        private val SYMBOL_PATTERN = Pattern.compile("(\\p{Alpha}|_)(\\p{Alnum}|_)*")
        private val LOCAL_LABEL_PATTERN = Pattern.compile("(\\.(\\p{Alpha}|_)(\\p{Alnum}|_)*)|((\\p{Alpha}|_)(\\p{Alnum}|_)*\\$)")
    }
}