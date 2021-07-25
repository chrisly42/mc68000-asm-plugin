package de.platon42.intellij.plugins.m68k.stubs.impl

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition
import de.platon42.intellij.plugins.m68k.stubs.M68kSymbolDefinitionStub

class M68kSymbolDefinitionStubImpl : NamedStubBase<M68kSymbolDefinition>, M68kSymbolDefinitionStub {
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: StringRef) : super(parent, elementType, name)
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: String) : super(parent, elementType, name)
}