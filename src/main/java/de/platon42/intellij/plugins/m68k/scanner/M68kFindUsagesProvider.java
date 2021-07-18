package de.platon42.intellij.plugins.m68k.scanner;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import de.platon42.intellij.plugins.m68k.lexer.M68kLexer;
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs;
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel;
import de.platon42.intellij.plugins.m68k.psi.M68kTypes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kFindUsagesProvider implements FindUsagesProvider {

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new M68kLexer(new M68kLexerPrefs()), // FIXME Oh no! More Prefs!
                TokenSet.create(M68kTypes.SYMBOLDEF, M68kTypes.GLOBAL_LABEL_DEF, M68kTypes.LOCAL_LABEL_DEF, M68kTypes.SYMBOL),
                TokenSet.create(M68kTypes.COMMENT),
                TokenSet.create(M68kTypes.STRINGLIT),
                TokenSet.EMPTY);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Override
    public @Nullable @NonNls String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @Override
    public @Nls @NotNull String getType(@NotNull PsiElement element) {
        if (element instanceof M68kGlobalLabel) {
            return "global label";
        }
        return "";
    }

    @Override
    public @Nls @NotNull String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof M68kGlobalLabel) {
            return ((M68kGlobalLabel) element).getName();
        }
        return "";
    }

    @Override
    public @Nls @NotNull String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        return getDescriptiveName(element);
    }
}
