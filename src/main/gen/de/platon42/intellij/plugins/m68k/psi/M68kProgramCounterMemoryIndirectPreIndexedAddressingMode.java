// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kProgramCounterMemoryIndirectPreIndexedAddressingMode extends M68kAddressingMode, M68kWithBaseDisplacement, M68kWithIndexRegister, M68kWithOuterDisplacement {

    @NotNull
    M68kIndexRegister getIndexRegister();

    @Nullable
    M68kBaseDisplacement getBaseDisplacement();

    @Nullable
    M68kOuterDisplacement getOuterDisplacement();

}
