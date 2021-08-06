// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class M68kPreprocessorDirectiveImpl extends M68kPreprocessorDirectiveMixin implements M68kPreprocessorDirective {

    public M68kPreprocessorDirectiveImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitPreprocessorDirective(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public M68kGlobalLabel getGlobalLabel() {
        return PsiTreeUtil.getChildOfType(this, M68kGlobalLabel.class);
    }

    @Override
    @Nullable
    public M68kLocalLabel getLocalLabel() {
        return PsiTreeUtil.getChildOfType(this, M68kLocalLabel.class);
    }

    @Override
    @NotNull
    public M68kPreprocessorKeyword getPreprocessorKeyword() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kPreprocessorKeyword.class));
    }

    @Override
    @NotNull
    public List<M68kExpr> getExprList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kExpr.class);
    }

}
