// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition;
import de.platon42.intellij.plugins.m68k.psi.M68kMacroNameDefinition;
import de.platon42.intellij.plugins.m68k.psi.M68kMacroPlainLine;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class M68kMacroDefinitionImpl extends ASTWrapperPsiElement implements M68kMacroDefinition {

    public M68kMacroDefinitionImpl(@NotNull ASTNode node) {
        super(node);
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

}
