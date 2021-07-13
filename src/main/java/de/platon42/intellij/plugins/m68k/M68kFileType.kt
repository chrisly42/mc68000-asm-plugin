package de.platon42.intellij.plugins.m68k

import com.intellij.openapi.fileTypes.LanguageFileType
import de.platon42.intellij.plugins.m68k.M68kIcons.FILE
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

@Suppress("unused")
class M68kFileType private constructor() : LanguageFileType(M68kLanguage.INSTANCE, true) {
    @NonNls
    override fun getName(): String {
        return "M68k ASM File"
    }

    override fun getDescription(): String {
        return "M68k assembly language file"
    }

    @NonNls
    override fun getDefaultExtension(): String {
        return "asm"
    }

    override fun getIcon(): Icon {
        return FILE
    }

    companion object {
        val INSTANCE = M68kFileType()
    }
}