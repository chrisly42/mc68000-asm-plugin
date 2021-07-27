package de.platon42.intellij.plugins.m68k.stubs.impl

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import de.platon42.intellij.plugins.m68k.psi.M68kMacroDefinition
import de.platon42.intellij.plugins.m68k.stubs.M68kMacroDefinitionStub

class M68kMacroDefinitionStubImpl : NamedStubBase<M68kMacroDefinition>, M68kMacroDefinitionStub {
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: StringRef) : super(parent, elementType, name)
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: String) : super(parent, elementType, name)
}