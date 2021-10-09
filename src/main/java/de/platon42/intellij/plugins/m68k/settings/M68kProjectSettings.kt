package de.platon42.intellij.plugins.m68k.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs

@State(name = "M68k.Settings", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class M68kProjectSettings : PersistentStateComponent<M68kLexerPrefs> {

    var settings: M68kLexerPrefs = M68kLexerPrefs()

    override fun getState() = settings

    override fun loadState(state: M68kLexerPrefs) {
        settings = state
    }
}