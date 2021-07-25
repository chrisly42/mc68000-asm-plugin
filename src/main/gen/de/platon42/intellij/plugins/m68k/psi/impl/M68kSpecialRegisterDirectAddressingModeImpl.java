// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kSpecialRegister;
import de.platon42.intellij.plugins.m68k.psi.M68kSpecialRegisterDirectAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kSpecialRegisterDirectAddressingModeImpl extends M68kAddressingModeImpl implements M68kSpecialRegisterDirectAddressingMode {

    public M68kSpecialRegisterDirectAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitSpecialRegisterDirectAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kSpecialRegister getSpecialRegister() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kSpecialRegister.class));
    }

}
