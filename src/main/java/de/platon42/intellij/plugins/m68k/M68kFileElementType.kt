package de.platon42.intellij.plugins.m68k

import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.ILightStubFileElementType
import de.platon42.intellij.plugins.m68k.psi.M68kFile

class M68kFileElementType private constructor() : ILightStubFileElementType<PsiFileStub<M68kFile>>("MC68000_FILE", MC68000Language.INSTANCE) {

    companion object {
        @JvmField
        val INSTANCE = M68kFileElementType()

        const val STUB_VERSION = 6
        const val STUB_EXTERNAL_ID_PREFIX = "MC68000."
        const val EXTERNAL_ID = STUB_EXTERNAL_ID_PREFIX + "FILE"
    }

    override fun getStubVersion() = STUB_VERSION

    override fun getExternalId() = EXTERNAL_ID
}