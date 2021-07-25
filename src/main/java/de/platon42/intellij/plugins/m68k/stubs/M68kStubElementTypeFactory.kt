package de.platon42.intellij.plugins.m68k.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import de.platon42.intellij.plugins.m68k.psi.M68kPsiElement

object M68kStubElementTypeFactory {
    @JvmStatic
    fun stubFactory(name: String): IStubElementType<out StubElement<out M68kPsiElement>, out M68kPsiElement> {
        when (name) {
            "GLOBAL_LABEL" -> return M68kElementTypes.GLOBAL_LABEL
            else -> throw RuntimeException("Unknown element type '$name'")
        }
    }
}