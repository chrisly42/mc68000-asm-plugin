package de.platon42.intellij.plugins.m68k

import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.DefaultStubBuilder
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.tree.IStubFileElementType
import de.platon42.intellij.plugins.m68k.psi.M68kFile
import de.platon42.intellij.plugins.m68k.stubs.M68kFileStub

class M68kFileElementType private constructor() : IStubFileElementType<M68kFileStub>("MC68000_FILE", MC68000Language.INSTANCE) {

    companion object {
        @JvmField
        val INSTANCE = M68kFileElementType()

        const val STUB_VERSION = 2
        const val STUB_EXTERNAL_ID_PREFIX = "MC68000."
    }

    override fun getStubVersion() = STUB_VERSION

    override fun getBuilder() =
        object : DefaultStubBuilder() {
            override fun createStubForFile(file: PsiFile): StubElement<*> {
                return if (file is M68kFile) M68kFileStub(file) else super.createStubForFile(file)
            }
        }
}