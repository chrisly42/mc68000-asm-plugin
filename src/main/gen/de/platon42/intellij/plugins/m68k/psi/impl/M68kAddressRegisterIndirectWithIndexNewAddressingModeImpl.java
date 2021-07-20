// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kAddressRegisterIndirectWithIndexNewAddressingModeImpl extends M68kAddressingModeImpl implements M68kAddressRegisterIndirectWithIndexNewAddressingMode {

    public M68kAddressRegisterIndirectWithIndexNewAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitAddressRegisterIndirectWithIndexNewAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kAddressRegister getAddressRegister() {
        return findNotNullChildByClass(M68kAddressRegister.class);
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
    @Nullable
    public M68kExpr getExpr() {
        return findChildByClass(M68kExpr.class);
    }

}