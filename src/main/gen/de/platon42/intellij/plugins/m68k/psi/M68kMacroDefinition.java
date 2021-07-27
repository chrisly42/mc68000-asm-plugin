// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.platon42.intellij.plugins.m68k.stubs.M68kMacroDefinitionStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface M68kMacroDefinition extends M68kNamedElement, StubBasedPsiElement<M68kMacroDefinitionStub> {

    @NotNull
    M68kMacroNameDefinition getMacroNameDefinition();

    @NotNull
    List<M68kMacroPlainLine> getMacroPlainLineList();

    @Nullable
    String getName();

    @NotNull
    PsiElement setName(@NotNull String name);

    @NotNull
    PsiElement getNameIdentifier();

}
