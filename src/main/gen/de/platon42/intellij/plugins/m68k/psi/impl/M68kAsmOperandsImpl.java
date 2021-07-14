// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kAsmOperands;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class M68kAsmOperandsImpl extends ASTWrapperPsiElement implements M68kAsmOperands {

    public M68kAsmOperandsImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitAsmOperands(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<M68kAddressingMode> getAddressingModeList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAddressingMode.class);
    }

}
