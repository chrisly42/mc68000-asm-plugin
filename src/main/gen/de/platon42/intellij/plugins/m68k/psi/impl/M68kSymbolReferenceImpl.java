// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kPsiImplUtil;
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference;
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReferenceMixin;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kSymbolReferenceImpl extends M68kSymbolReferenceMixin implements M68kSymbolReference {

    public M68kSymbolReferenceImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitSymbolReference(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public String getSymbolName() {
        return M68kPsiImplUtil.getSymbolName(this);
    }

    @Override
    public boolean isLocalLabelRef() {
        return M68kPsiImplUtil.isLocalLabelRef(this);
    }

}
