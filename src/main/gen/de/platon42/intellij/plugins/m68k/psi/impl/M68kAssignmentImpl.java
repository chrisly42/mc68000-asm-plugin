// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kAssignment;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kAssignmentImpl extends ASTWrapperPsiElement implements M68kAssignment {

    public M68kAssignmentImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitAssignment(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kSymbolDefinition getSymbolDefinition() {
        return findNotNullChildByClass(M68kSymbolDefinition.class);
    }

    @Override
    @NotNull
    public M68kExpr getExpr() {
        return findNotNullChildByClass(M68kExpr.class);
    }

}
