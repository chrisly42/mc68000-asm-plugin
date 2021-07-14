// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kPreprocessorDirective;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.IF_TAG;
import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.SYMBOL;

public class M68kPreprocessorDirectiveImpl extends ASTWrapperPsiElement implements M68kPreprocessorDirective {

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
    @NotNull
    public List<M68kExpr> getExprList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kExpr.class);
    }

    @Override
    @Nullable
    public PsiElement getIfTag() {
        return findChildByType(IF_TAG);
    }

    @Override
    @Nullable
    public PsiElement getSymbol() {
        return findChildByType(SYMBOL);
    }

}
