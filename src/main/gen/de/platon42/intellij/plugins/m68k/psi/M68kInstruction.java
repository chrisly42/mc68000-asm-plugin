// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface M68kInstruction extends PsiElement {

    @Nullable
    M68kAsmInstruction getAsmInstruction();

    @Nullable
    M68kMacroCall getMacroCall();

}
