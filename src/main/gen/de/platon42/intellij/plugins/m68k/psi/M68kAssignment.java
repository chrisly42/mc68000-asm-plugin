// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kAssignment extends PsiElement {

  @NotNull
  M68kExpr getExpr();

  @Nullable
  PsiElement getEqu();

  @NotNull
  PsiElement getSymbol();

}
