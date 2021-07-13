package de.platon42.intellij.plugins.m68k

import com.intellij.lang.Language

class M68kLanguage private constructor() : Language("M68k") {
    companion object {
        val INSTANCE = M68kLanguage()
    }
}