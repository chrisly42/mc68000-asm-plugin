package de.platon42.intellij.plugins.m68k

import com.intellij.lang.Language

class MC68000Language private constructor() : Language("MC68000") {
    companion object {
        val INSTANCE = MC68000Language()
    }
}