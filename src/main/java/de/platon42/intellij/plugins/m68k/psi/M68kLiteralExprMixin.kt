package de.platon42.intellij.plugins.m68k.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class M68kLiteralExprMixin(node: ASTNode) : ASTWrapperPsiElement(node), M68kLiteralExpr {
    override fun getValue(): Any? {
        val childNode = firstChild.node
        when (childNode.elementType) {
            M68kTypes.STRINGLIT -> {
                return text.run {
                    when {
                        startsWith('"') -> removeSurrounding("\"")
                        startsWith('\'') -> removeSurrounding("'")
                        startsWith('<') -> removeSurrounding(">")
                        else -> this
                    }
                }
            }
            M68kTypes.DECIMAL -> {
                try {
                    return childNode.text.toInt()
                } catch (ex: NumberFormatException) {
                    // ignore and fall through
                }
            }
            M68kTypes.HEXADECIMAL -> {
                try {
                    return childNode.text.substring(1).toInt(16)
                } catch (ex: NumberFormatException) {
                    // ignore and fall through
                }
            }
            M68kTypes.BINARY -> {
                try {
                    return childNode.text.substring(1).toInt(2)
                } catch (ex: NumberFormatException) {
                    // ignore and fall through
                }
            }
            M68kTypes.OCTAL -> {
                try {
                    return childNode.text.substring(1).toInt(8)
                } catch (ex: NumberFormatException) {
                    // ignore and fall through
                }
            }
        }
        return text
    }
}