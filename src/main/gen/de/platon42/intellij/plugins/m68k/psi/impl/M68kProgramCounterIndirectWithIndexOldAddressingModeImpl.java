// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kIndexRegister;
import de.platon42.intellij.plugins.m68k.psi.M68kProgramCounterIndirectWithIndexOldAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kProgramCounterIndirectWithIndexOldAddressingModeImpl extends M68kAddressingModeImpl implements M68kProgramCounterIndirectWithIndexOldAddressingMode {

    public M68kProgramCounterIndirectWithIndexOldAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitProgramCounterIndirectWithIndexOldAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kIndexRegister getIndexRegister() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kIndexRegister.class));
    }

    @Override
    @Nullable
    public M68kExpr getDisplacement() {
        return PsiTreeUtil.getChildOfType(this, M68kExpr.class);
    }

}
