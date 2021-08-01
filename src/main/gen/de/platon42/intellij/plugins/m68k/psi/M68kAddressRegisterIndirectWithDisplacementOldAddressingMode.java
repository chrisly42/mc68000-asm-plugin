// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;

public interface M68kAddressRegisterIndirectWithDisplacementOldAddressingMode extends M68kAddressingMode, M68kWithAddressRegisterIndirect, M68kWithDisplacement {

    @NotNull
    M68kAddressRegister getAddressRegister();

    @NotNull
    M68kExpr getDisplacement();

}
