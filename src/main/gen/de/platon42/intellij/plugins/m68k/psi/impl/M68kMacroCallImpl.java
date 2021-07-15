// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kMacroCall;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kMacroCallImpl extends ASTWrapperPsiElement implements M68kMacroCall {

    public M68kMacroCallImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitMacroCall(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

}
