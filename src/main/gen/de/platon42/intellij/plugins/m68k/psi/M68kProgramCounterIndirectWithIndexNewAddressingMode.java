// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kProgramCounterIndirectWithIndexNewAddressingMode extends M68kAddressingMode, M68kWithDisplacement, M68kWithIndexRegister {

    @NotNull
    M68kIndexRegister getIndexRegister();

    @Nullable
    M68kExpr getDisplacement();

}
