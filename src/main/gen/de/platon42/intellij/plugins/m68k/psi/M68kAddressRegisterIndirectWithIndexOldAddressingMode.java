// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kAddressRegisterIndirectWithIndexOldAddressingMode extends M68kAddressingMode {

    @NotNull
    M68kAddressRegister getAddressRegister();

    @Nullable
    M68kDataWidth getDataWidth();

    @NotNull
    M68kRegister getRegister();

    @Nullable
    M68kExpr getExpr();

}
