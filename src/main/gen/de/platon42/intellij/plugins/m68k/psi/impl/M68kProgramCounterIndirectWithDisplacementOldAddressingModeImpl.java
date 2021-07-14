// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kProgramCounterIndirectWithDisplacementOldAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kProgramCounterIndirectWithDisplacementOldAddressingModeImpl extends M68kAddressingModeImpl implements M68kProgramCounterIndirectWithDisplacementOldAddressingMode {

    public M68kProgramCounterIndirectWithDisplacementOldAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitProgramCounterIndirectWithDisplacementOldAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kExpr getExpr() {
        return findNotNullChildByClass(M68kExpr.class);
    }

}
