// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.SYMBOL;
import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.WHITE_SPACE;

public class M68kGlobalLabelImpl extends ASTWrapperPsiElement implements M68kGlobalLabel {

    public M68kGlobalLabelImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitGlobalLabel(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public PsiElement getSymbol() {
        return findNotNullChildByType(SYMBOL);
    }

    @Override
    @Nullable
    public PsiElement getWhiteSpace() {
        return findChildByType(WHITE_SPACE);
    }

}
