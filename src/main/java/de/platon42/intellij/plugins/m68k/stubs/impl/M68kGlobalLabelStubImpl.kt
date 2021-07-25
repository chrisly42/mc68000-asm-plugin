package de.platon42.intellij.plugins.m68k.stubs.impl

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.stubs.M68kGlobalLabelStub

class M68kGlobalLabelStubImpl : NamedStubBase<M68kGlobalLabel>, M68kGlobalLabelStub {
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: StringRef) : super(parent, elementType, name)
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: String) : super(parent, elementType, name)
}