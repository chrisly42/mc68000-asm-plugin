// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface M68kAsmInstruction extends PsiElement {

    @NotNull
    List<M68kAddressingMode> getAddressingModeList();

    @NotNull
    M68kAsmOp getAsmOp();

}
