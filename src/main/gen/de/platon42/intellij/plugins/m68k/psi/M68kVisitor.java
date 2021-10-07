// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

public class M68kVisitor extends PsiElementVisitor {

    public void visitAbsoluteAddressAddressingMode(@NotNull M68kAbsoluteAddressAddressingMode o) {
        visitAddressingMode(o);
    }

    public void visitAddressRegister(@NotNull M68kAddressRegister o) {
        visitRegister(o);
    }

    public void visitAddressRegisterDirectAddressingMode(@NotNull M68kAddressRegisterDirectAddressingMode o) {
        visitAddressingMode(o);
    }

    public void visitAddressRegisterIndirectAddressingMode(@NotNull M68kAddressRegisterIndirectAddressingMode o) {
        visitAddressingMode(o);
        // visitWithAddressRegisterIndirect(o);
    }

    public void visitAddressRegisterIndirectPostIncAddressingMode(@NotNull M68kAddressRegisterIndirectPostIncAddressingMode o) {
        visitAddressingMode(o);
        // visitWithAddressRegisterIndirect(o);
    }

    public void visitAddressRegisterIndirectPreDecAddressingMode(@NotNull M68kAddressRegisterIndirectPreDecAddressingMode o) {
        visitAddressingMode(o);
        // visitWithAddressRegisterIndirect(o);
    }

    public void visitAddressRegisterIndirectWithDisplacementNewAddressingMode(@NotNull M68kAddressRegisterIndirectWithDisplacementNewAddressingMode o) {
        visitAddressingMode(o);
        // visitWithAddressRegisterIndirect(o);
        // visitWithDisplacement(o);
    }

    public void visitAddressRegisterIndirectWithDisplacementOldAddressingMode(@NotNull M68kAddressRegisterIndirectWithDisplacementOldAddressingMode o) {
        visitAddressingMode(o);
        // visitWithAddressRegisterIndirect(o);
        // visitWithDisplacement(o);
    }

    public void visitAddressRegisterIndirectWithIndexNewAddressingMode(@NotNull M68kAddressRegisterIndirectWithIndexNewAddressingMode o) {
        visitAddressingMode(o);
        // visitWithAddressRegisterIndirect(o);
        // visitWithDisplacement(o);
        // visitWithIndexRegister(o);
    }

    public void visitAddressRegisterIndirectWithIndexOldAddressingMode(@NotNull M68kAddressRegisterIndirectWithIndexOldAddressingMode o) {
        visitAddressingMode(o);
        // visitWithAddressRegisterIndirect(o);
        // visitWithDisplacement(o);
        // visitWithIndexRegister(o);
    }

    public void visitAddressSize(@NotNull M68kAddressSize o) {
        visitPsiElement(o);
    }

    public void visitAddressingMode(@NotNull M68kAddressingMode o) {
        visitPsiElement(o);
    }

    public void visitAsmInstruction(@NotNull M68kAsmInstruction o) {
        visitPsiElement(o);
    }

    public void visitAsmOp(@NotNull M68kAsmOp o) {
        visitPsiElement(o);
    }

    public void visitAssignment(@NotNull M68kAssignment o) {
        visitPsiElement(o);
    }

    public void visitDataRegister(@NotNull M68kDataRegister o) {
        visitRegister(o);
    }

    public void visitDataRegisterDirectAddressingMode(@NotNull M68kDataRegisterDirectAddressingMode o) {
        visitAddressingMode(o);
    }

    public void visitDataWidth(@NotNull M68kDataWidth o) {
        visitPsiElement(o);
    }

    public void visitGlobalLabel(@NotNull M68kGlobalLabel o) {
        visitNamedElement(o);
    }

    public void visitImmediateData(@NotNull M68kImmediateData o) {
        visitAddressingMode(o);
    }

    public void visitIndexRegister(@NotNull M68kIndexRegister o) {
        visitPsiElement(o);
    }

    public void visitIndexScale(@NotNull M68kIndexScale o) {
        visitPsiElement(o);
    }

    public void visitLocalLabel(@NotNull M68kLocalLabel o) {
        visitNamedElement(o);
    }

    public void visitMacroCall(@NotNull M68kMacroCall o) {
        visitPsiElement(o);
    }

    public void visitMacroDefinition(@NotNull M68kMacroDefinition o) {
        visitNamedElement(o);
    }

    public void visitMacroNameDefinition(@NotNull M68kMacroNameDefinition o) {
        visitPsiElement(o);
    }

    public void visitMacroPlainLine(@NotNull M68kMacroPlainLine o) {
        visitPsiElement(o);
    }

    public void visitOperandSize(@NotNull M68kOperandSize o) {
        visitPsiElement(o);
    }

    public void visitPreprocessorDirective(@NotNull M68kPreprocessorDirective o) {
        visitPsiElement(o);
    }

    public void visitPreprocessorKeyword(@NotNull M68kPreprocessorKeyword o) {
        visitPsiElement(o);
    }

    public void visitProgramCounterIndirectWithDisplacementNewAddressingMode(@NotNull M68kProgramCounterIndirectWithDisplacementNewAddressingMode o) {
        visitAddressingMode(o);
        // visitWithDisplacement(o);
    }

    public void visitProgramCounterIndirectWithDisplacementOldAddressingMode(@NotNull M68kProgramCounterIndirectWithDisplacementOldAddressingMode o) {
        visitAddressingMode(o);
        // visitWithDisplacement(o);
    }

    public void visitProgramCounterIndirectWithIndexNewAddressingMode(@NotNull M68kProgramCounterIndirectWithIndexNewAddressingMode o) {
        visitAddressingMode(o);
        // visitWithDisplacement(o);
        // visitWithIndexRegister(o);
    }

    public void visitProgramCounterIndirectWithIndexOldAddressingMode(@NotNull M68kProgramCounterIndirectWithIndexOldAddressingMode o) {
        visitAddressingMode(o);
        // visitWithDisplacement(o);
        // visitWithIndexRegister(o);
    }

    public void visitProgramCounterReference(@NotNull M68kProgramCounterReference o) {
        visitPsiElement(o);
    }

    public void visitRegister(@NotNull M68kRegister o) {
        visitPsiElement(o);
    }

    public void visitRegisterListAddressingMode(@NotNull M68kRegisterListAddressingMode o) {
        visitAddressingMode(o);
    }

    public void visitRegisterRange(@NotNull M68kRegisterRange o) {
        visitPsiElement(o);
    }

    public void visitSpecialRegister(@NotNull M68kSpecialRegister o) {
        visitRegister(o);
    }

    public void visitSpecialRegisterDirectAddressingMode(@NotNull M68kSpecialRegisterDirectAddressingMode o) {
        visitAddressingMode(o);
    }

    public void visitSymbolDefinition(@NotNull M68kSymbolDefinition o) {
        visitNamedElement(o);
    }

    public void visitSymbolReference(@NotNull M68kSymbolReference o) {
        visitPsiElement(o);
    }

    public void visitBinaryAddExpr(@NotNull M68kBinaryAddExpr o) {
        visitExpr(o);
    }

    public void visitBinaryBitwiseAndExpr(@NotNull M68kBinaryBitwiseAndExpr o) {
        visitExpr(o);
    }

    public void visitBinaryBitwiseOrExpr(@NotNull M68kBinaryBitwiseOrExpr o) {
        visitExpr(o);
    }

    public void visitBinaryBitwiseXorExpr(@NotNull M68kBinaryBitwiseXorExpr o) {
        visitExpr(o);
    }

    public void visitBinaryCmpEqExpr(@NotNull M68kBinaryCmpEqExpr o) {
        visitExpr(o);
    }

    public void visitBinaryCmpGeExpr(@NotNull M68kBinaryCmpGeExpr o) {
        visitExpr(o);
    }

    public void visitBinaryCmpGtExpr(@NotNull M68kBinaryCmpGtExpr o) {
        visitExpr(o);
    }

    public void visitBinaryCmpLeExpr(@NotNull M68kBinaryCmpLeExpr o) {
        visitExpr(o);
    }

    public void visitBinaryCmpLtExpr(@NotNull M68kBinaryCmpLtExpr o) {
        visitExpr(o);
    }

    public void visitBinaryCmpNeExpr(@NotNull M68kBinaryCmpNeExpr o) {
        visitExpr(o);
    }

    public void visitBinaryDivExpr(@NotNull M68kBinaryDivExpr o) {
        visitExpr(o);
    }

    public void visitBinaryLogicalAndExpr(@NotNull M68kBinaryLogicalAndExpr o) {
        visitExpr(o);
    }

    public void visitBinaryLogicalOrExpr(@NotNull M68kBinaryLogicalOrExpr o) {
        visitExpr(o);
    }

    public void visitBinaryModExpr(@NotNull M68kBinaryModExpr o) {
        visitExpr(o);
    }

    public void visitBinaryMulExpr(@NotNull M68kBinaryMulExpr o) {
        visitExpr(o);
    }

    public void visitBinaryShiftLExpr(@NotNull M68kBinaryShiftLExpr o) {
        visitExpr(o);
    }

    public void visitBinaryShiftRExpr(@NotNull M68kBinaryShiftRExpr o) {
        visitExpr(o);
    }

    public void visitBinarySubExpr(@NotNull M68kBinarySubExpr o) {
        visitExpr(o);
    }

    public void visitExpr(@NotNull M68kExpr o) {
        visitPsiElement(o);
    }

    public void visitLiteralExpr(@NotNull M68kLiteralExpr o) {
        visitExpr(o);
        // visitPsiLiteralValue(o);
    }

    public void visitParenExpr(@NotNull M68kParenExpr o) {
        visitExpr(o);
    }

    public void visitRefExpr(@NotNull M68kRefExpr o) {
        visitExpr(o);
    }

    public void visitStatement(@NotNull M68kStatement o) {
        visitPsiElement(o);
    }

    public void visitUnaryComplExpr(@NotNull M68kUnaryComplExpr o) {
        visitExpr(o);
    }

    public void visitUnaryMinusExpr(@NotNull M68kUnaryMinusExpr o) {
        visitExpr(o);
    }

    public void visitUnaryNotExpr(@NotNull M68kUnaryNotExpr o) {
        visitExpr(o);
    }

    public void visitUnaryPlusExpr(@NotNull M68kUnaryPlusExpr o) {
        visitExpr(o);
    }

    public void visitNamedElement(@NotNull M68kNamedElement o) {
        visitPsiElement(o);
    }

    public void visitPsiElement(@NotNull M68kPsiElement o) {
        visitElement(o);
    }

}
