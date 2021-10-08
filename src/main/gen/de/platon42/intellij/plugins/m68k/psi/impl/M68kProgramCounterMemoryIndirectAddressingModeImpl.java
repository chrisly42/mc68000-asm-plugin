// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kProgramCounterMemoryIndirectAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class M68kProgramCounterMemoryIndirectAddressingModeImpl extends M68kAddressingModeImpl implements M68kProgramCounterMemoryIndirectAddressingMode {

    public M68kProgramCounterMemoryIndirectAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitProgramCounterMemoryIndirectAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
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
