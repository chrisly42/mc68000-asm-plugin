// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kProgramCounterIndirectWithIndexOldAddressingModeImpl extends M68kAddressingModeImpl implements M68kProgramCounterIndirectWithIndexOldAddressingMode {

    public M68kProgramCounterIndirectWithIndexOldAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitProgramCounterIndirectWithIndexOldAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public M68kDataWidth getDataWidth() {
        return findChildByClass(M68kDataWidth.class);
    }

    @Override
    @NotNull
    public M68kRegister getRegister() {
        return findNotNullChildByClass(M68kRegister.class);
    }

    @Override
    @NotNull
    public M68kExpr getExpr() {
        return findNotNullChildByClass(M68kExpr.class);
    }

}
