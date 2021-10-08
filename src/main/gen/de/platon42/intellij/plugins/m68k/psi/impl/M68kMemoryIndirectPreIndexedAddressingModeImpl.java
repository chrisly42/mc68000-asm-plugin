// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class M68kMemoryIndirectPreIndexedAddressingModeImpl extends M68kAddressingModeImpl implements M68kMemoryIndirectPreIndexedAddressingMode {

    public M68kMemoryIndirectPreIndexedAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitMemoryIndirectPreIndexedAddressingMode(this);
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
    @NotNull
    public M68kIndexRegister getIndexRegister() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kIndexRegister.class));
    }

    @Override
    @NotNull
    public List<M68kExpr> getExprList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kExpr.class);
    }

    @Override
    @Nullable
    public M68kExpr getBaseDisplacement() {
        List<M68kExpr> p1 = getExprList();
        return p1.size() < 1 ? null : p1.get(0);
    }

    @Override
    @Nullable
    public M68kExpr getOuterDisplacement() {
        List<M68kExpr> p1 = getExprList();
        return p1.size() < 2 ? null : p1.get(1);
    }

}
