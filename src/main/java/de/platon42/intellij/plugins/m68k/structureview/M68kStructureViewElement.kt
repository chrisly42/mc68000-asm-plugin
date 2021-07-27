package de.platon42.intellij.plugins.m68k.structureview

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.refactoring.suggested.startOffset
import de.platon42.intellij.plugins.m68k.psi.M68kFile
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kLookupUtil
import de.platon42.intellij.plugins.m68k.psi.M68kLookupUtil.findAllLocalLabels

class M68kStructureViewElement(private val myElement: NavigatablePsiElement) : StructureViewTreeElement {
    override fun getValue(): Any = myElement

    override fun getPresentation(): ItemPresentation = myElement.presentation!!

    override fun getChildren(): Array<TreeElement> {
        return when (myElement) {
            is M68kFile -> {
                listOf(
                    M68kLookupUtil.findAllSymbolDefinitions(myElement).sortedBy { it.startOffset },
                    M68kLookupUtil.findAllMacroDefinitions(myElement).sortedBy { it.startOffset },
                    M68kLookupUtil.findAllGlobalLabels(myElement).sortedBy { it.startOffset },
//                    M68kLookupUtil.findAllSymbolDefinitions(myElement).sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name!! }),
//                    M68kLookupUtil.findAllGlobalLabels(myElement).sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name!! })
                )
                    .flatten()
                    .map(::M68kStructureViewElement)
                    .toTypedArray()
            }
            is M68kGlobalLabel -> {
                findAllLocalLabels(myElement).map(::M68kStructureViewElement).toTypedArray()
            }
            else -> emptyArray()
        }
    }

    override fun navigate(requestFocus: Boolean) = myElement.navigate(requestFocus)

    override fun canNavigate() = myElement.canNavigate()

    override fun canNavigateToSource() = myElement.canNavigateToSource()
}