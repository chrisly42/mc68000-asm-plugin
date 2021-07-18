// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kProgramCounterReference;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kProgramCounterReferenceImpl extends ASTWrapperPsiElement implements M68kProgramCounterReference {

    public M68kProgramCounterReferenceImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitProgramCounterReference(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

}
