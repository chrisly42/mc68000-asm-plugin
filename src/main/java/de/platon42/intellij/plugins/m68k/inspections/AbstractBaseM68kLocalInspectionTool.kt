package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import de.platon42.intellij.plugins.m68k.psi.*

abstract class AbstractBaseM68kLocalInspectionTool : LocalInspectionTool() {

    protected open fun checkStatement(statement: M68kStatement, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? = null

    protected open fun checkAsmInstruction(asmInstruction: M68kAsmInstruction, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? =
        null

    protected open fun checkMacroCall(macroCall: M68kMacroCall, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? = null

    protected open fun checkDirective(directive: M68kPreprocessorDirective, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? = null

    protected open fun checkRegister(register: M68kRegister, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? = null

    override fun getGroupDisplayName() = "M68k"

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : M68kVisitor() {
            override fun visitStatement(statement: M68kStatement) = addDescriptors(checkStatement(statement, holder.manager, isOnTheFly))

            override fun visitAsmInstruction(asmInstruction: M68kAsmInstruction) =
                addDescriptors(checkAsmInstruction(asmInstruction, holder.manager, isOnTheFly))

            override fun visitMacroCall(macroCall: M68kMacroCall) = addDescriptors(checkMacroCall(macroCall, holder.manager, isOnTheFly))

            override fun visitPreprocessorDirective(directive: M68kPreprocessorDirective) =
                addDescriptors(checkDirective(directive, holder.manager, isOnTheFly))

            override fun visitRegister(register: M68kRegister) = addDescriptors(checkRegister(register, holder.manager, isOnTheFly))

            private fun addDescriptors(descriptors: Array<ProblemDescriptor>?) {
                descriptors?.forEach(holder::registerProblem)
            }
        }
    }
}