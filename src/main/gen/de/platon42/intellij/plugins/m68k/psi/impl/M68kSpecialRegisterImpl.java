// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kSpecialRegister;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.*;

public class M68kSpecialRegisterImpl extends M68kRegisterImpl implements M68kSpecialRegister {

    public M68kSpecialRegisterImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitSpecialRegister(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getRegCcr() {
        return findChildByType(REG_CCR);
    }

    @Override
    @Nullable
    public PsiElement getRegSr() {
        return findChildByType(REG_SR);
    }

    @Override
    @Nullable
    public PsiElement getRegUsp() {
        return findChildByType(REG_USP);
    }

    @Override
    @Nullable
    public PsiElement getRegVbr() {
        return findChildByType(REG_VBR);
    }

}
