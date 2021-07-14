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

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.*;

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
    public PsiElement getCnopTag() {
        return findChildByType(CNOP_TAG);
    }

    @Override
    @Nullable
    public PsiElement getElseTag() {
        return findChildByType(ELSE_TAG);
    }

    @Override
    @Nullable
    public PsiElement getEndcTag() {
        return findChildByType(ENDC_TAG);
    }

    @Override
    @Nullable
    public PsiElement getEndTag() {
        return findChildByType(END_TAG);
    }

    @Override
    @Nullable
    public PsiElement getEvenTag() {
        return findChildByType(EVEN_TAG);
    }

    @Override
    @Nullable
    public PsiElement getFailTag() {
        return findChildByType(FAIL_TAG);
    }

    @Override
    @Nullable
    public PsiElement getIfTag() {
        return findChildByType(IF_TAG);
    }

    @Override
    @Nullable
    public PsiElement getIncbinTag() {
        return findChildByType(INCBIN_TAG);
    }

    @Override
    @Nullable
    public PsiElement getIncludeTag() {
        return findChildByType(INCLUDE_TAG);
    }

    @Override
    @Nullable
    public PsiElement getMacroEndTag() {
        return findChildByType(MACRO_END_TAG);
    }

    @Override
    @Nullable
    public PsiElement getMacroTag() {
        return findChildByType(MACRO_TAG);
    }

    @Override
    @Nullable
    public PsiElement getReptEndTag() {
        return findChildByType(REPT_END_TAG);
    }

    @Override
    @Nullable
    public PsiElement getReptTag() {
        return findChildByType(REPT_TAG);
    }

    @Override
    @Nullable
    public PsiElement getSectionTag() {
        return findChildByType(SECTION_TAG);
    }

    @Override
    @Nullable
    public PsiElement getSymbol() {
        return findChildByType(SYMBOL);
    }

    @Override
    @Nullable
    public PsiElement getWhiteSpace() {
        return findChildByType(WHITE_SPACE);
    }

}
