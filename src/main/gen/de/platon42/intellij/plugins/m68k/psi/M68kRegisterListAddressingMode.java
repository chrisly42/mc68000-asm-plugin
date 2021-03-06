// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import de.platon42.intellij.plugins.m68k.asm.Register;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface M68kRegisterListAddressingMode extends M68kAddressingMode {

    @NotNull
    List<M68kRegister> getRegisterList();

    @NotNull
    List<M68kRegisterRange> getRegisterRangeList();

    @NotNull
    Set<Register> getRegisters();

}
