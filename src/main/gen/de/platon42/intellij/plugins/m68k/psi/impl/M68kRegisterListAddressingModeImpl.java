// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kRegister;
import de.platon42.intellij.plugins.m68k.psi.M68kRegisterListAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class M68kRegisterListAddressingModeImpl extends M68kAddressingModeImpl implements M68kRegisterListAddressingMode {

    public M68kRegisterListAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitRegisterListAddressingMode(this);
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

}
