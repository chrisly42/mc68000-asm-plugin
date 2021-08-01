// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kRegister;
import de.platon42.intellij.plugins.m68k.psi.M68kRegisterRange;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class M68kRegisterRangeImpl extends ASTWrapperPsiElement implements M68kRegisterRange {

    public M68kRegisterRangeImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitRegisterRange(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<M68kRegister> getRegisterList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kRegister.class);
    }

    @Override
    @NotNull
    public M68kRegister getStartRegister() {
        List<M68kRegister> p1 = getRegisterList();
        return p1.get(0);
    }

    @Override
    @Nullable
    public M68kRegister getEndRegister() {
        List<M68kRegister> p1 = getRegisterList();
        return p1.size() < 2 ? null : p1.get(1);
    }

}
