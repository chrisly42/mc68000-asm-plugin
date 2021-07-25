package de.platon42.intellij.plugins.m68k.stubs

import com.intellij.lang.LighterAST
import com.intellij.lang.LighterASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LightTreeUtil
import com.intellij.psi.stubs.*
import com.intellij.psi.tree.ILightStubFileElementType
import de.platon42.intellij.plugins.m68k.MC68000Language.Companion.INSTANCE
import de.platon42.intellij.plugins.m68k.psi.M68kGlobalLabel
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolDefinition
import de.platon42.intellij.plugins.m68k.psi.M68kTypes
import de.platon42.intellij.plugins.m68k.psi.impl.M68kGlobalLabelImpl
import de.platon42.intellij.plugins.m68k.psi.impl.M68kSymbolDefinitionImpl
import de.platon42.intellij.plugins.m68k.stubs.impl.M68kGlobalLabelStubImpl
import de.platon42.intellij.plugins.m68k.stubs.impl.M68kSymbolDefinitionStubImpl
import java.io.IOException

interface M68kElementTypes {
    companion object {
        @JvmField
        val FILE = ILightStubFileElementType<M68kFileStub>(INSTANCE)

        @JvmField
        val GLOBAL_LABEL: IStubElementType<M68kGlobalLabelStub, M68kGlobalLabel> =
            object : M68kStubElementType<M68kGlobalLabelStub, M68kGlobalLabel>("GLOBAL_LABEL") {
                override fun createPsi(stub: M68kGlobalLabelStub): M68kGlobalLabel = M68kGlobalLabelImpl(stub, this)

                override fun createStub(psi: M68kGlobalLabel, parentStub: StubElement<out PsiElement>): M68kGlobalLabelStub =
                    M68kGlobalLabelStubImpl(parentStub, this, psi.name!!)

                override fun createStub(tree: LighterAST, node: LighterASTNode, parentStub: StubElement<*>): M68kGlobalLabelStub {
                    val idNode = LightTreeUtil.requiredChildOfType(tree, node, M68kTypes.GLOBAL_LABEL_DEF)
                    return M68kGlobalLabelStubImpl(parentStub, this, LightTreeUtil.toFilteredString(tree, idNode, null))
                }

                @Throws(IOException::class)
                override fun serialize(stub: M68kGlobalLabelStub, dataStream: StubOutputStream) = dataStream.writeName(stub.name)

                @Throws(IOException::class)
                override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): M68kGlobalLabelStub =
                    M68kGlobalLabelStubImpl(parentStub, this, dataStream.readName()!!)

                override fun indexStub(stub: M68kGlobalLabelStub, sink: IndexSink) = sink.occurrence(M68kGlobalLabelStubIndex.KEY, stub.name!!)
            }

        @JvmField
        val SYMBOL_DEFINITION: IStubElementType<M68kSymbolDefinitionStub, M68kSymbolDefinition> =
            object : M68kStubElementType<M68kSymbolDefinitionStub, M68kSymbolDefinition>("SYMBOL_DEFINITION") {
                override fun createPsi(stub: M68kSymbolDefinitionStub): M68kSymbolDefinition = M68kSymbolDefinitionImpl(stub, this)

                override fun createStub(psi: M68kSymbolDefinition, parentStub: StubElement<out PsiElement>): M68kSymbolDefinitionStub =
                    M68kSymbolDefinitionStubImpl(parentStub, this, psi.name!!)

                override fun createStub(tree: LighterAST, node: LighterASTNode, parentStub: StubElement<*>): M68kSymbolDefinitionStub {
                    val idNode = LightTreeUtil.requiredChildOfType(tree, node, M68kTypes.SYMBOLDEF)
                    return M68kSymbolDefinitionStubImpl(parentStub, this, LightTreeUtil.toFilteredString(tree, idNode, null))
                }

                @Throws(IOException::class)
                override fun serialize(stub: M68kSymbolDefinitionStub, dataStream: StubOutputStream) = dataStream.writeName(stub.name)

                @Throws(IOException::class)
                override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): M68kSymbolDefinitionStub =
                    M68kSymbolDefinitionStubImpl(parentStub, this, dataStream.readName()!!)

                override fun indexStub(stub: M68kSymbolDefinitionStub, sink: IndexSink) = sink.occurrence(M68kSymbolDefinitionStubIndex.KEY, stub.name!!)
            }

    }
}