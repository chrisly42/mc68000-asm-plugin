// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kIndexRegister extends M68kPsiElement {

    @Nullable
    M68kDataWidth getDataWidth();

    @Nullable
    M68kIndexScale getIndexScale();

    @NotNull
    M68kRegister getRegister();

    boolean isLongWidth();

}
