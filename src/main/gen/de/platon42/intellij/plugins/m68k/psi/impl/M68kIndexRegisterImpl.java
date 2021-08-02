// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kIndexRegisterImpl extends ASTWrapperPsiElement implements M68kIndexRegister {

    public M68kIndexRegisterImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitIndexRegister(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public M68kDataWidth getDataWidth() {
        return PsiTreeUtil.getChildOfType(this, M68kDataWidth.class);
    }

    @Override
    @NotNull
    public M68kRegister getRegister() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kRegister.class));
    }

    @Override
    public boolean isLongWidth() {
        return M68kPsiImplUtil.isLongWidth(this);
    }

}
