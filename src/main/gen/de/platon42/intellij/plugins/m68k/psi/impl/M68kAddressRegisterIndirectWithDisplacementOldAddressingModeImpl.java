// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kAddressRegister;
import de.platon42.intellij.plugins.m68k.psi.M68kAddressRegisterIndirectWithDisplacementOldAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kAddressRegisterIndirectWithDisplacementOldAddressingModeImpl extends M68kAddressingModeImpl implements M68kAddressRegisterIndirectWithDisplacementOldAddressingMode {

    public M68kAddressRegisterIndirectWithDisplacementOldAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitAddressRegisterIndirectWithDisplacementOldAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kAddressRegister getAddressRegister() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kAddressRegister.class));
    }

    @Override
    @NotNull
    public M68kExpr getDisplacement() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kExpr.class));
    }

}
