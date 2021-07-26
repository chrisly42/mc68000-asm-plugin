// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public M68kAsmInstruction getAsmInstruction() {
        return PsiTreeUtil.getChildOfType(this, M68kAsmInstruction.class);
    }

    @Override
    @Nullable
    public M68kAssignment getAssignment() {
        return PsiTreeUtil.getChildOfType(this, M68kAssignment.class);
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
    @Nullable
    public M68kMacroCall getMacroCall() {
        return PsiTreeUtil.getChildOfType(this, M68kMacroCall.class);
    }

    @Override
    @Nullable
    public M68kPreprocessorDirective getPreprocessorDirective() {
        return PsiTreeUtil.getChildOfType(this, M68kPreprocessorDirective.class);
    }

}
