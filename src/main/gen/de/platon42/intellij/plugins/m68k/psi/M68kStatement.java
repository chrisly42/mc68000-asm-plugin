// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.Nullable;

public interface M68kStatement extends M68kPsiElement {

    @Nullable
    M68kAsmInstruction getAsmInstruction();

    @Nullable
    M68kAssignment getAssignment();

    @Nullable
    M68kLabel getLabel();

    @Nullable
    M68kMacroCall getMacroCall();

    @Nullable
    M68kPreprocessorDirective getPreprocessorDirective();

}
