// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kProgramCounterIndirectWithDisplacementNewAddressingMode;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.PC;

public class M68kProgramCounterIndirectWithDisplacementNewAddressingModeImpl extends M68kAddressingModeImpl implements M68kProgramCounterIndirectWithDisplacementNewAddressingMode {

    public M68kProgramCounterIndirectWithDisplacementNewAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitProgramCounterIndirectWithDisplacementNewAddressingMode(this);
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

    @Override
    @NotNull
    public PsiElement getPc() {
        return findNotNullChildByType(PC);
    }

}
