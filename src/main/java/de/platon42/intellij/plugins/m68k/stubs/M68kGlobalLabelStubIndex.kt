package de.platon42.intellij.plugins.m68k.stubs

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import de.platon42.intellij.plugins.m68k.M68kFileElementType
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel

class M68kGlobalLabelStubIndex : StringStubIndexExtension<M68kGlobalLabel>() {
    override fun getKey(): StubIndexKey<String, M68kGlobalLabel> = KEY

    override fun getVersion(): Int = M68kFileElementType.STUB_VERSION

    companion object {
        val KEY = StubIndexKey.createIndexKey<String, M68kGlobalLabel>("mc68000.globallabel.index")
    }
}