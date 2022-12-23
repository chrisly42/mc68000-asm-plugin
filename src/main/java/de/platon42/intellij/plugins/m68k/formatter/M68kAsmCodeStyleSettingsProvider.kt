package de.platon42.intellij.plugins.m68k.formatter

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import de.platon42.intellij.plugins.m68k.MC68000Language


class M68kAsmCodeStyleSettingsProvider : CodeStyleSettingsProvider() {

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings {
        return M68kAsmCodeStyleSettings(settings)
    }

    override fun createConfigurable(settings: CodeStyleSettings, modelSettings: CodeStyleSettings): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(settings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
                return M68kAsmCodeStyleMainPanel(currentSettings, settings)
            }
        }
    }

    override fun getConfigurableDisplayName() = "M68k"

    private class M68kAsmCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
        TabbedLanguageCodeStylePanel(MC68000Language.INSTANCE, currentSettings, settings)
}