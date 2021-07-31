// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kAsmOp extends M68kPsiElement {

    @Nullable
    M68kOperandSize getOperandSize();

    @NotNull
    String getMnemonic();

    int getOpSize();

}
