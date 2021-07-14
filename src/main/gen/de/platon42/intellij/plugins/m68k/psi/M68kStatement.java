// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface M68kStatement extends PsiElement {

    @Nullable
    M68kAssignment getAssignment();

    @Nullable
    M68kLabel getLabel();

    @Nullable
    M68kPreprocessorDirective getPreprocessorDirective();

    @Nullable
    PsiElement getWhiteSpace();

}
