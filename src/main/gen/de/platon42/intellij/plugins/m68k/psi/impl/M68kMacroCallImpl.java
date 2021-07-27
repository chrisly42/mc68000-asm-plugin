// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class M68kMacroCallImpl extends M68kMacroCallMixin implements M68kMacroCall {

    public M68kMacroCallImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitMacroCall(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<M68kAddressingMode> getAddressingModeList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAddressingMode.class);
    }

    @Override
    @NotNull
    public List<M68kExpr> getExprList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kExpr.class);
    }

    @Override
    @NotNull
    public String getMacroName() {
        return M68kPsiImplUtil.getMacroName(this);
    }

}
