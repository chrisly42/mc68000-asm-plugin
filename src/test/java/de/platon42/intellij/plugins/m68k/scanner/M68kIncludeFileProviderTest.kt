package de.platon42.intellij.plugins.m68k.scanner

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.impl.include.FileIncludeManager
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LightCodeInsightExtension::class)
internal class M68kIncludeFileProviderTest : AbstractM68kTest() {

    @Test
    internal fun accepts_a_couple_of_include_statements(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.addFileToProject("macros.i", "PUSHM MACRO\n ENDM")
        myFixture.addFileToProject("exec/execbase.i", "; Commodore include")
        myFixture.addFileToProject("exec/exec.i", " include execbase.i")
        myFixture.addFileToProject("includes/hardware/custom.i", "; Another Commodore include")
        myFixture.addFileToProject("data/ease_x.bin", "binary file")
        myFixture.addFileToProject("fx/fx_angle_only.S", "; Nosfe!!")
        myFixture.configureByText(
            "completeme.asm", """
 include macros.i
 include "exec/exec.i"
 include <../src/includes/hardware/custom.i>
 incbin "data/ease_x.bin"
 include fx\fx_angle_only.S
        """
        )
        val includedFiles = FileIncludeManager.getManager(myFixture.project).getIncludedFiles(myFixture.file.virtualFile, true, true)
        assertThat(includedFiles).extracting<String>(VirtualFile::getName)
            .containsExactlyInAnyOrder("completeme.asm", "macros.i", "fx_angle_only.S", "custom.i", "exec.i", "execbase.i")
    }
}