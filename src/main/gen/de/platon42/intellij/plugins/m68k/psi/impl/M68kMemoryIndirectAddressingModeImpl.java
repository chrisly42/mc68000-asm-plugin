// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kMemoryIndirectAddressingModeImpl extends M68kAddressingModeImpl implements M68kMemoryIndirectAddressingMode {

    public M68kMemoryIndirectAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitMemoryIndirectAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kAddressRegister getAddressRegister() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kAddressRegister.class));
    }

    @Override
    @Nullable
    public M68kBaseDisplacement getBaseDisplacement() {
        return PsiTreeUtil.getChildOfType(this, M68kBaseDisplacement.class);
    }

    @Override
    @Nullable
    public M68kOuterDisplacement getOuterDisplacement() {
        return PsiTreeUtil.getChildOfType(this, M68kOuterDisplacement.class);
    }

}
