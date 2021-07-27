package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.stubs.IStubElementType
import de.platon42.intellij.plugins.m68k.M68kIcons
import de.platon42.intellij.plugins.m68k.stubs.M68kMacroDefinitionStub
import javax.swing.Icon

abstract class M68kMacroDefinitionMixin : StubBasedPsiElementBase<M68kMacroDefinitionStub>, M68kMacroDefinition {

    constructor(node: ASTNode) : super(node)

    constructor(stub: M68kMacroDefinitionStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getPresentation(): ItemPresentation? {
        return PresentationData(name, containingFile.name, getIcon(0), null)
    }

    override fun getIcon(flags: Int): Icon? {
        return M68kIcons.MACRO_DEF
    }

    override fun toString(): String {
        return javaClass.simpleName + "(MACRO_DEFINITION)"
    }
}