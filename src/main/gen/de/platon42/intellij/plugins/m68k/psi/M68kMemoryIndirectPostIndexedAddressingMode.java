// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kMemoryIndirectPostIndexedAddressingMode extends M68kAddressingMode, M68kWithAddressRegisterIndirect, M68kWithBaseDisplacement, M68kWithIndexRegister, M68kWithOuterDisplacement {

    @Nullable
    M68kAddressRegister getAddressRegister();

    @NotNull
    M68kIndexRegister getIndexRegister();

    @Nullable
    M68kBaseDisplacement getBaseDisplacement();

    @Nullable
    M68kOuterDisplacement getOuterDisplacement();

}
