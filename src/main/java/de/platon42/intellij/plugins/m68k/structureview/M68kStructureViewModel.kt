package de.platon42.intellij.plugins.m68k.structureview

import com.intellij.ide.structureView.StructureViewModel.ElementInfoProvider
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.*
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import de.platon42.intellij.plugins.m68k.M68kIcons
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kLocalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition

class M68kStructureViewModel(psiFile: PsiFile, editor: Editor?) :
    StructureViewModelBase(psiFile, editor, M68kStructureViewElement(psiFile)), ElementInfoProvider {

    companion object {
        val FILTERS = arrayOf(SymbolsFilter(), MacrosFilter(), GlobalLabelFilter(), LocalLabelFilter())

        const val SYMBOLS_FILTER_ID = "SHOW_SYMBOLS"
        const val MACROS_FILTER_ID = "SHOW_MACROS"
        const val GLOBAL_LABEL_FILTER_ID = "SHOW_GLOBAL_LABELS"
        const val LOCAL_LABEL_FILTER_ID = "SHOW_LOCAL_LABELS"
    }

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
        return when (element.value) {
            is M68kLocalLabel, is M68kMacroDefinition, is M68kSymbolDefinition -> true
            else -> false
        }
    }

    override fun getSorters(): Array<Sorter> = arrayOf(Sorter.ALPHA_SORTER)

    override fun getFilters(): Array<Filter> = FILTERS

    class SymbolsFilter : Filter {
        override fun getPresentation(): ActionPresentation = ActionPresentationData("Show Symbols", null, M68kIcons.SYMBOL_DEF)

        override fun getName(): String = SYMBOLS_FILTER_ID

        override fun isVisible(treeNode: TreeElement?) = (treeNode as? StructureViewTreeElement)?.value !is M68kSymbolDefinition

        override fun isReverted() = true
    }

    class MacrosFilter : Filter {
        override fun getPresentation(): ActionPresentation = ActionPresentationData("Show Macros", null, M68kIcons.MACRO_DEF)

        override fun getName(): String = MACROS_FILTER_ID

        override fun isVisible(treeNode: TreeElement?) = (treeNode as? StructureViewTreeElement)?.value !is M68kMacroDefinition

        override fun isReverted() = true
    }

    class GlobalLabelFilter : Filter {
        override fun getPresentation(): ActionPresentation = ActionPresentationData("Show Global Labels", null, M68kIcons.GLOBAL_LABEL)

        override fun getName(): String = GLOBAL_LABEL_FILTER_ID

        override fun isVisible(treeNode: TreeElement?) = (treeNode as? StructureViewTreeElement)?.value !is M68kGlobalLabel

        override fun isReverted() = true
    }

    class LocalLabelFilter : Filter {
        override fun getPresentation(): ActionPresentation = ActionPresentationData("Show Local Labels", null, M68kIcons.LOCAL_LABEL)

        override fun getName(): String = LOCAL_LABEL_FILTER_ID

        override fun isVisible(treeNode: TreeElement?) = (treeNode as? StructureViewTreeElement)?.value !is M68kLocalLabel

        override fun isReverted() = true
    }
}