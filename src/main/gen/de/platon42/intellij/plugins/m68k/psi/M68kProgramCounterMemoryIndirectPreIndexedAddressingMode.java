// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface M68kProgramCounterMemoryIndirectPreIndexedAddressingMode extends M68kAddressingMode, M68kWithBaseDisplacement, M68kWithIndexRegister, M68kWithOuterDisplacement {

    @NotNull
    M68kIndexRegister getIndexRegister();

    @NotNull
    List<M68kExpr> getExprList();

    @Nullable
    M68kExpr getBaseDisplacement();

    @Nullable
    M68kExpr getOuterDisplacement();

}
