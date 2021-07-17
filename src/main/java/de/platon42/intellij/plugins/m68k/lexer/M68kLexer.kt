package de.platon42.intellij.plugins.m68k.lexer

import com.intellij.lexer.FlexAdapter

class M68kLexer(lexerPrefs: M68kLexerPrefs) : FlexAdapter(_M68kLexer(lexerPrefs))