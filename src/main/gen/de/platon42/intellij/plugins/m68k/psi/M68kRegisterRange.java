// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface M68kRegisterRange extends M68kPsiElement {

    @NotNull
    List<M68kRegister> getRegisterList();

    @NotNull
    M68kRegister getStartRegister();

    @Nullable
    M68kRegister getEndRegister();

}
