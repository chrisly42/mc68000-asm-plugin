// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kMemoryIndirectAddressingMode extends M68kAddressingMode, M68kWithAddressRegisterIndirect, M68kWithBaseDisplacement, M68kWithOuterDisplacement {

    @NotNull
    M68kAddressRegister getAddressRegister();

    @Nullable
    M68kBaseDisplacement getBaseDisplacement();

    @Nullable
    M68kOuterDisplacement getOuterDisplacement();

}
