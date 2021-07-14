// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel;
import de.platon42.intellij.plugins.m68k.psi.M68kLabel;
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kLabelImpl extends ASTWrapperPsiElement implements M68kLabel {

    public M68kLabelImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitLabel(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public M68kGlobalLabel getGlobalLabel() {
        return findChildByClass(M68kGlobalLabel.class);
    }

    @Override
    @Nullable
    public M68kLocalLabel getLocalLabel() {
        return findChildByClass(M68kLocalLabel.class);
    }

}
