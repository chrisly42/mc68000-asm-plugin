// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.WHITE_SPACE;

public class M68kStatementImpl extends ASTWrapperPsiElement implements M68kStatement {

    public M68kStatementImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitStatement(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public M68kAssignment getAssignment() {
        return findChildByClass(M68kAssignment.class);
    }

    @Override
    @Nullable
    public M68kLabel getLabel() {
        return findChildByClass(M68kLabel.class);
    }

    @Override
    @Nullable
    public M68kPreprocessorDirective getPreprocessorDirective() {
        return findChildByClass(M68kPreprocessorDirective.class);
    }

    @Override
    @Nullable
    public PsiElement getWhiteSpace() {
        return findChildByType(WHITE_SPACE);
    }

}
