// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.Nullable;

public interface M68kAddressRegisterIndirectWithIndexBaseDisplacementAddressingMode extends M68kAddressingMode, M68kWithOptionalAddressRegisterIndirect, M68kWithBaseDisplacement, M68kWithOptionalIndexRegister {

    @Nullable
    M68kAddressRegister getAddressRegister();

    @Nullable
    M68kIndexRegister getIndexRegister();

    @Nullable
    M68kBaseDisplacement getBaseDisplacement();

}
