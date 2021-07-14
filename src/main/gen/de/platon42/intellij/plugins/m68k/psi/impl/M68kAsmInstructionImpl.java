// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kAsmInstruction;
import de.platon42.intellij.plugins.m68k.psi.M68kAsmOp;
import de.platon42.intellij.plugins.m68k.psi.M68kAsmOperands;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kAsmInstructionImpl extends ASTWrapperPsiElement implements M68kAsmInstruction {

    public M68kAsmInstructionImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitAsmInstruction(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kAsmOp getAsmOp() {
        return findNotNullChildByClass(M68kAsmOp.class);
    }

    @Override
    @Nullable
    public M68kAsmOperands getAsmOperands() {
        return findChildByClass(M68kAsmOperands.class);
    }

}
