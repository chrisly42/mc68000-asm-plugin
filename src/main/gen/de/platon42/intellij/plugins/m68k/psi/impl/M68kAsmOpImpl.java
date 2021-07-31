// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kAsmOp;
import de.platon42.intellij.plugins.m68k.psi.M68kOperandSize;
import de.platon42.intellij.plugins.m68k.psi.M68kPsiImplUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kAsmOpImpl extends ASTWrapperPsiElement implements M68kAsmOp {

    public M68kAsmOpImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitAsmOp(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public M68kOperandSize getOperandSize() {
        return PsiTreeUtil.getChildOfType(this, M68kOperandSize.class);
    }

    @Override
    @NotNull
    public String getMnemonic() {
        return M68kPsiImplUtil.getMnemonic(this);
    }

    @Override
    public int getOpSize() {
        return M68kPsiImplUtil.getOpSize(this);
    }

}
