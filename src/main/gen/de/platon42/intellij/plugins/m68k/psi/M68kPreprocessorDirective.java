// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface M68kPreprocessorDirective extends PsiElement {

    @NotNull
    List<M68kExpr> getExprList();

    @Nullable
    PsiElement getIfTag();

    @Nullable
    PsiElement getSymbol();

}
