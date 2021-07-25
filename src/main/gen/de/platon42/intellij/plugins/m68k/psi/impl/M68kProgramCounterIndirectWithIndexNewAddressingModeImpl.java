// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kProgramCounterIndirectWithIndexNewAddressingModeImpl extends M68kAddressingModeImpl implements M68kProgramCounterIndirectWithIndexNewAddressingMode {

    public M68kProgramCounterIndirectWithIndexNewAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitProgramCounterIndirectWithIndexNewAddressingMode(this);
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
    @Nullable
    public M68kExpr getExpr() {
        return PsiTreeUtil.getChildOfType(this, M68kExpr.class);
    }

}
