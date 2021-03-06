// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel;
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabelMixin;
import de.platon42.intellij.plugins.m68k.psi.M68kPsiImplUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kLocalLabelImpl extends M68kLocalLabelMixin implements M68kLocalLabel {

    public M68kLocalLabelImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitLocalLabel(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public String getName() {
        return M68kPsiImplUtil.getName(this);
    }

    @Override
    @NotNull
    public PsiElement setName(@NotNull String name) {
        return M68kPsiImplUtil.setName(this, name);
    }

    @Override
    @NotNull
    public PsiElement getNameIdentifier() {
        return M68kPsiImplUtil.getNameIdentifier(this);
    }

}
