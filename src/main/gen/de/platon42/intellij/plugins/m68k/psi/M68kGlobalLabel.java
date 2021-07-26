// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.platon42.intellij.plugins.m68k.stubs.M68kGlobalLabelStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kGlobalLabel extends M68kNamedElement, StubBasedPsiElement<M68kGlobalLabelStub> {

    @Nullable
    String getName();

    @NotNull
    PsiElement setName(@NotNull String name);

    @NotNull
    PsiElement getNameIdentifier();

}
