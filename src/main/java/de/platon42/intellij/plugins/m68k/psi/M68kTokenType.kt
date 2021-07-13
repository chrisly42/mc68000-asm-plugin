package de.platon42.intellij.plugins.m68k.psi

import com.intellij.psi.tree.IElementType
import de.platon42.intellij.plugins.m68k.M68kLanguage.Companion.INSTANCE
import org.jetbrains.annotations.NonNls

class M68kTokenType(@NonNls debugName: String) : IElementType(debugName, INSTANCE) {
    override fun toString(): String {
        return "M68kTokenType." + super.toString()
    }
}