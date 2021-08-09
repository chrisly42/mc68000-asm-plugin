package de.platon42.intellij.plugins.m68k.inspections

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiPolyVariantReference
import de.platon42.intellij.plugins.m68k.psi.M68kMacroCall
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference
import de.platon42.intellij.plugins.m68k.psi.M68kVisitor
import de.platon42.intellij.plugins.m68k.refs.M68kGlobalLabelSymbolReference

class M68kUnresolvedReferenceInspection : AbstractBaseM68kLocalInspectionTool() {

    companion object {
        private const val DISPLAY_NAME = "Unresolved label/symbol/macro reference"
    }

    override fun getDisplayName() = DISPLAY_NAME

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {
        return object : M68kVisitor() {
            override fun visitMacroCall(macroCall: M68kMacroCall) {
                val reference = macroCall.reference as? PsiPolyVariantReference ?: return
                val resolve = reference.multiResolve(false)
                if (resolve.isEmpty()) {
                    holder.registerProblem(reference)
                }
            }

            override fun visitSymbolReference(symbolReference: M68kSymbolReference) {
                val references = symbolReference.references ?: return
                if (references.isEmpty()) return
                val resolve = references.mapNotNull { it as? PsiPolyVariantReference }
                    .firstNotNullOfOrNull { it.multiResolve(false).ifEmpty { null } }
                if (resolve == null) {
                    // TODO currently, because macro invocations are not evaluated, mark missing symbols only as weak warning
                    val makeWeak = references.any { it is M68kGlobalLabelSymbolReference }
                    if (makeWeak) {
                        holder.registerProblem(references.first(), ProblemHighlightType.WEAK_WARNING)
                    } else {
                        holder.registerProblem(references.first())
                    }
                }
            }
        }
    }
}