package de.platon42.intellij.plugins.m68k.navigation

import com.intellij.ide.navigationToolbar.AbstractNavBarModelExtension
import com.intellij.ide.ui.UISettings
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.IndexNotReadyException
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.plugins.m68k.M68kIcons
import de.platon42.intellij.plugins.m68k.MC68000Language
import de.platon42.intellij.plugins.m68k.psi.*
import de.platon42.intellij.plugins.m68k.psi.utils.M68kPsiWalkUtil.findStatementForElement
import org.jetbrains.annotations.Nullable
import javax.swing.Icon

class M68kStructureAwareNavbar : AbstractNavBarModelExtension() {

    override fun getLeafElement(dataContext: DataContext): PsiElement? {
        if (UISettings.getInstance().showMembersInNavigationBar) {
            val psiFile = CommonDataKeys.PSI_FILE.getData(dataContext)
            val editor = CommonDataKeys.EDITOR.getData(dataContext)
            if (psiFile == null || !psiFile.isValid || editor == null) return null
            val psiElement = psiFile.findElementAt(editor.caretModel.offset)
            if (isAcceptableLanguage(psiElement)) {
                try {
                    var statement = findStatementForElement(psiElement!!) ?: return null
                    do {
                        if (statement.localLabel != null) return statement.localLabel
                        if (statement.globalLabel != null) return statement.globalLabel
                        statement = PsiTreeUtil.getPrevSiblingOfType(statement, M68kStatement::class.java) ?: return null
                    } while (true)
                } catch (ignored: IndexNotReadyException) {
                }
            }
        }
        return null
    }

    private fun isAcceptableLanguage(psiElement: @Nullable PsiElement?) = psiElement?.language == MC68000Language.INSTANCE

    override fun getPresentableText(obj: Any?): String? {
        return when (obj) {
            is M68kFile -> obj.name
            is M68kGlobalLabel -> obj.name
            is M68kLocalLabel -> obj.name
            is M68kSymbolDefinition -> obj.name
            is M68kMacroDefinition -> obj.macroNameDefinition.text
            else -> null
        }
    }

    override fun getParent(psiElement: PsiElement): PsiElement? {
        if (!isAcceptableLanguage(psiElement)) return null
        var statement = findStatementForElement(psiElement) ?: return null
        if (statement.localLabel != null) {
            do {
                if (statement.globalLabel != null) return statement.globalLabel
                statement = PsiTreeUtil.getPrevSiblingOfType(statement, M68kStatement::class.java) ?: return null
            } while (true)
        }
        return psiElement.containingFile
    }

    override fun getIcon(obj: Any?): Icon? {
        return when (obj) {
            is M68kFile -> M68kIcons.FILE
            is M68kGlobalLabel -> M68kIcons.GLOBAL_LABEL
            is M68kLocalLabel -> M68kIcons.LOCAL_LABEL
            is M68kSymbolDefinition -> M68kIcons.SYMBOL_DEF
            is M68kMacroDefinition -> M68kIcons.MACRO_DEF
            else -> null
        }
    }
}