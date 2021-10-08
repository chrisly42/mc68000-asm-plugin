// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface M68kMemoryIndirectAddressingMode extends M68kAddressingMode, M68kWithAddressRegisterIndirect, M68kWithBaseDisplacement, M68kWithOuterDisplacement {

    @NotNull
    M68kAddressRegister getAddressRegister();

    @NotNull
    List<M68kDataWidth> getDataWidthList();

    @NotNull
    List<M68kExpr> getExprList();

    @Nullable
    M68kExpr getBaseDisplacement();

    @Nullable
    M68kExpr getOuterDisplacement();

    @Nullable
    M68kDataWidth getBaseDataWidth();

    @Nullable
    M68kDataWidth getOuterDataWidth();

}
