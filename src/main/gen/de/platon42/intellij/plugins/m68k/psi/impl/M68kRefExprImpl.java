// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import de.platon42.intellij.plugins.m68k.psi.M68kProgramCounterReference;
import de.platon42.intellij.plugins.m68k.psi.M68kRefExpr;
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference;
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kRefExprImpl extends M68kExprImpl implements M68kRefExpr {

    public M68kRefExprImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitRefExpr(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public M68kProgramCounterReference getProgramCounterReference() {
        return findChildByClass(M68kProgramCounterReference.class);
    }

    @Override
    @Nullable
    public M68kSymbolReference getSymbolReference() {
        return findChildByClass(M68kSymbolReference.class);
    }

}
