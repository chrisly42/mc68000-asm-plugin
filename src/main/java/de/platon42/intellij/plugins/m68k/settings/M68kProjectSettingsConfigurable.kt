package de.platon42.intellij.plugins.m68k.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import com.intellij.ui.components.JBCheckBox
import com.intellij.uiDesigner.core.Spacer
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UIUtil
import de.platon42.intellij.plugins.m68k.lexer.M68kLexerPrefs
import javax.swing.JComponent

class M68kProjectSettingsConfigurable(project: Project?) : Configurable {

    private val settings = project?.getService(M68kProjectSettings::class.java)

    private val spacesOptionField = JBCheckBox("Space introduces comment (-spaces)", settings?.settings?.spaceIntroducesComment ?: false)

    override fun getDisplayName(): @ConfigurableName String = "M68k"

    override fun createComponent(): JComponent? {
        return FormBuilder.createFormBuilder()
            .setAlignLabelOnRight(false)
            .setHorizontalGap(UIUtil.DEFAULT_HGAP)
            .setVerticalGap(UIUtil.DEFAULT_VGAP)
            .addComponent(spacesOptionField)
            .addComponentFillVertically(Spacer(), 0)
            .panel
    }

    override fun isModified(): Boolean {
        return settings?.settings?.spaceIntroducesComment != spacesOptionField.isSelected
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        settings?.settings?.spaceIntroducesComment = spacesOptionField.isSelected
    }

    override fun reset() {
        val default = M68kLexerPrefs()
        settings?.settings?.spaceIntroducesComment = default.spaceIntroducesComment
    }
}