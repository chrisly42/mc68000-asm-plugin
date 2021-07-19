package de.platon42.intellij.plugins.m68k.refs

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.util.IncorrectOperationException
import de.platon42.intellij.plugins.m68k.psi.M68kElementFactory
import de.platon42.intellij.plugins.m68k.psi.M68kSymbolReference

class M68kSymbolReferenceElementManipulator : AbstractElementManipulator<M68kSymbolReference>() {

    @Throws(IncorrectOperationException::class)
    override fun handleContentChange(element: M68kSymbolReference, range: TextRange, newContent: String): M68kSymbolReference {
        val newSymbolReference = M68kElementFactory.createSymbolReference(element.project, newContent)
        element.firstChild.replace(newSymbolReference.firstChild)
        return element
    }
}