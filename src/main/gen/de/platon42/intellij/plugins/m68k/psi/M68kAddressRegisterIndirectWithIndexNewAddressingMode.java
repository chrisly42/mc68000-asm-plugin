// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kAddressRegisterIndirectWithIndexNewAddressingMode extends M68kAddressingMode, M68kWithAddressRegisterIndirect, M68kWithDisplacement, M68kWithIndexRegister {

    @NotNull
    M68kAddressRegister getAddressRegister();

    @Nullable
    M68kExpr getDisplacement();

    @NotNull
    M68kRegister getIndexRegister();

    @Nullable
    M68kDataWidth getIndexWidth();

}
