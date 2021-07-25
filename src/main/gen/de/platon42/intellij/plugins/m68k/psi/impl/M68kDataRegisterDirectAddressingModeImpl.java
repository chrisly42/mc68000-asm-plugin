// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kDataRegister;
import de.platon42.intellij.plugins.m68k.psi.M68kDataRegisterDirectAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kDataRegisterDirectAddressingModeImpl extends M68kAddressingModeImpl implements M68kDataRegisterDirectAddressingMode {

    public M68kDataRegisterDirectAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitDataRegisterDirectAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kDataRegister getDataRegister() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kDataRegister.class));
    }

}
