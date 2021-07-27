package de.platon42.intellij.plugins.m68k.refs

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.util.IncorrectOperationException
import de.platon42.intellij.plugins.m68k.psi.M68kElementFactory
import de.platon42.intellij.plugins.m68k.psi.M68kMacroCall

class M68kMacroCallElementManipulator : AbstractElementManipulator<M68kMacroCall>() {

    @Throws(IncorrectOperationException::class)
    override fun handleContentChange(element: M68kMacroCall, range: TextRange, newContent: String): M68kMacroCall {
        val newMacroCall = M68kElementFactory.createMacroCall(element.project, newContent)
        element.firstChild.replace(newMacroCall.firstChild)
        return element
    }
}