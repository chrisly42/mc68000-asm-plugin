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
import java.text.NumberFormat
import java.text.ParseException
import javax.swing.JComponent
import javax.swing.JFormattedTextField
import javax.swing.JLabel
import javax.swing.text.DefaultFormatterFactory
import javax.swing.text.NumberFormatter

class M68kProjectSettingsConfigurable(project: Project?) : Configurable {

    private val settings = project?.getService(M68kProjectSettings::class.java)

    private val spacesOptionField = JBCheckBox("Space introduces comment (-spaces)", settings?.settings?.spaceIntroducesComment ?: false)

    private val maxLinesPerMacroField = createNumberField(settings?.settings?.maxLinesPerMacro)
    private val maxShortDocumentationLinesField = createNumberField(settings?.settings?.maxShortDocumentationLines)
    private val maxLongDocumentationLinesField = createNumberField(settings?.settings?.maxLongDocumentationLines)

    override fun getDisplayName(): @ConfigurableName String = "M68k"

    override fun createComponent(): JComponent? {
        return FormBuilder.createFormBuilder()
            .setAlignLabelOnRight(false)
            .setHorizontalGap(UIUtil.DEFAULT_HGAP)
            .setVerticalGap(UIUtil.DEFAULT_VGAP)
            .addComponent(spacesOptionField)
            .addLabeledComponent(JLabel("Maximum lines parsed inside macro"), maxLinesPerMacroField)
            .addSeparator()
            .addLabeledComponent(JLabel("Lines of code in hovered documentation"), maxShortDocumentationLinesField)
            .addLabeledComponent(JLabel("Lines of code in full documentation"), maxLongDocumentationLinesField)
            .addComponentFillVertically(Spacer(), 0)
            .panel
    }

    private fun createNumberField(value: Any?): JFormattedTextField {
        val valueField = JFormattedTextField()
        valueField.columns = 4
        val formatter = NumberFormat.getIntegerInstance()
        formatter.isParseIntegerOnly = true
        valueField.formatterFactory = DefaultFormatterFactory(NumberFormatter(formatter))
        valueField.value = value
        return valueField
    }

    override fun isModified(): Boolean {
        val prefs = settings?.settings ?: return false

        return prefs.spaceIntroducesComment != spacesOptionField.isSelected
                || fieldDiffers(maxLinesPerMacroField, prefs.maxLinesPerMacro)
                || fieldDiffers(maxShortDocumentationLinesField, prefs.maxShortDocumentationLines)
                || fieldDiffers(maxLongDocumentationLinesField, prefs.maxLongDocumentationLines)
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val prefs = settings?.settings ?: return
        prefs.spaceIntroducesComment = spacesOptionField.isSelected
        getFieldValue(maxLinesPerMacroField).let { if (it != null) prefs.maxLinesPerMacro = it }
        getFieldValue(maxShortDocumentationLinesField).let { if (it != null) prefs.maxShortDocumentationLines = it }
        getFieldValue(maxLongDocumentationLinesField).let { if (it != null) prefs.maxLongDocumentationLines = it }
    }

    private fun fieldDiffers(field: JFormattedTextField, value: Int?): Boolean {
        val fieldValue = getFieldValue(field)
        return (fieldValue != null) && (value != null) && (fieldValue != value)
    }

    private fun getFieldValue(field: JFormattedTextField): Int? {
        return try {
            val value = field.value
            (value as Number).toInt()
        } catch (ex: ParseException) {
            null
        }
    }

    override fun reset() {
        settings?.settings = M68kLexerPrefs()
    }
}