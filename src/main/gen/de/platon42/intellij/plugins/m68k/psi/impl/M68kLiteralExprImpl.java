// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kLiteralExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.*;

public class M68kLiteralExprImpl extends M68kExprImpl implements M68kLiteralExpr {

    public M68kLiteralExprImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitLiteralExpr(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getBinary() {
        return findChildByType(BINARY);
    }

    @Override
    @Nullable
    public PsiElement getDecimal() {
        return findChildByType(DECIMAL);
    }

    @Override
    @Nullable
    public PsiElement getHexadecimal() {
        return findChildByType(HEXADECIMAL);
    }

    @Override
    @Nullable
    public PsiElement getOctal() {
        return findChildByType(OCTAL);
    }

    @Override
    @Nullable
    public PsiElement getStringlit() {
        return findChildByType(STRINGLIT);
    }

}
