// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface M68kPreprocessorDirective extends M68kPsiElement {

    @Nullable
    M68kGlobalLabel getGlobalLabel();

    @Nullable
    M68kLocalLabel getLocalLabel();

    @NotNull
    M68kPreprocessorKeyword getPreprocessorKeyword();

    @NotNull
    List<M68kExpr> getExprList();

}
