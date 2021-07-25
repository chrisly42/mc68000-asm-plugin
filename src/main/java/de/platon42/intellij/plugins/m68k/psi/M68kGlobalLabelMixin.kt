package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.stubs.IStubElementType
import de.platon42.intellij.plugins.m68k.M68kIcons
import de.platon42.intellij.plugins.m68k.stubs.M68kGlobalLabelStub
import javax.swing.Icon

abstract class M68kGlobalLabelMixin : StubBasedPsiElementBase<M68kGlobalLabelStub>, M68kGlobalLabel {

    constructor(node: ASTNode) : super(node)

    constructor(stub: M68kGlobalLabelStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getPresentation(): ItemPresentation? {
        return PresentationData(name, containingFile.name, getIcon(0), null)
    }

    override fun getIcon(flags: Int): Icon? {
        return M68kIcons.GLOBAL_LABEL
    }

    override fun toString(): String {
        return javaClass.simpleName + "(GLOBAL_LABEL)"
    }
}