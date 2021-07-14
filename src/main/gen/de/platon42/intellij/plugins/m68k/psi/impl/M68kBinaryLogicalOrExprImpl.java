// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kBinaryLogicalOrExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class M68kBinaryLogicalOrExprImpl extends M68kExprImpl implements M68kBinaryLogicalOrExpr {

    public M68kBinaryLogicalOrExprImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitBinaryLogicalOrExpr(this);
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

}
