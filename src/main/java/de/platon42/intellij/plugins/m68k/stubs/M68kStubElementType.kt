package de.platon42.intellij.plugins.m68k.stubs

import com.intellij.psi.stubs.ILightStubElementType
import com.intellij.psi.stubs.StubElement
import de.platon42.intellij.plugins.m68k.M68kFileElementType
import de.platon42.intellij.plugins.m68k.MC68000Language.Companion.INSTANCE
import de.platon42.intellij.plugins.m68k.psi.M68kPsiElement

abstract class M68kStubElementType<STUB : StubElement<out M68kPsiElement>, PSI_TYPE : M68kPsiElement>(debugName: String) :

    ILightStubElementType<STUB, PSI_TYPE>(debugName, INSTANCE) {
    override fun getExternalId(): String = M68kFileElementType.STUB_EXTERNAL_ID_PREFIX + this
}