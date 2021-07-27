// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import de.platon42.intellij.plugins.m68k.stubs.M68kMacroDefinitionStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class M68kMacroDefinitionImpl extends M68kMacroDefinitionMixin implements M68kMacroDefinition {

    public M68kMacroDefinitionImpl(@NotNull ASTNode node) {
        super(node);
    }

    public M68kMacroDefinitionImpl(@NotNull M68kMacroDefinitionStub stub, @NotNull IStubElementType<?, ?> nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitMacroDefinition(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public M68kMacroNameDefinition getMacroNameDefinition() {
        return notNullChild(PsiTreeUtil.getChildOfType(this, M68kMacroNameDefinition.class));
    }

    @Override
    @NotNull
    public List<M68kMacroPlainLine> getMacroPlainLineList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kMacroPlainLine.class);
    }

    @Override
    @Nullable
    public String getName() {
        return M68kPsiImplUtil.getName(this);
    }

    @Override
    @NotNull
    public PsiElement setName(@NotNull String name) {
        return M68kPsiImplUtil.setName(this, name);
    }

    @Override
    @NotNull
    public PsiElement getNameIdentifier() {
        return M68kPsiImplUtil.getNameIdentifier(this);
    }

}
