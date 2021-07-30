package de.platon42.intellij.plugins.m68k.structureview

import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import de.platon42.intellij.jupiter.LightCodeInsightExtension
import de.platon42.intellij.jupiter.MyFixture
import de.platon42.intellij.jupiter.TestDataPath
import de.platon42.intellij.plugins.m68k.AbstractM68kTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@TestDataPath("src/test/resources/structureview")
@ExtendWith(LightCodeInsightExtension::class)
internal class M68kStructureViewTest : AbstractM68kTest() {

    @Test
    internal fun do_basic_verification_of_resulting_structure_view(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("basic_example.asm")
        myFixture.testStructureView {
            val tree = it.tree
            PlatformTestUtil.waitWhileBusy(tree)
            PlatformTestUtil.assertTreeEqual(
                tree, """-basic_example.asm
 PIC_WIDTH
 PIC_HEIGHT
 DEBUG_LEVEL
 BLTHOGON
 BLTHOGOFF
 entry
 -init
  .looph
  .loopw
 main
 exit
"""
            )
        }
    }

    @Test
    internal fun macros_filter_works(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("basic_example.asm")
        myFixture.testStructureView {
            val tree = it.tree
            it.setActionActive(M68kStructureViewModel.MACROS_FILTER_ID, true)
            PlatformTestUtil.waitWhileBusy(tree)
            PlatformTestUtil.assertTreeEqual(
                tree, """-basic_example.asm
 PIC_WIDTH
 PIC_HEIGHT
 DEBUG_LEVEL
 entry
 -init
  .looph
  .loopw
 main
 exit
"""
            )
        }
    }

    @Test
    internal fun symbols_filter_works(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("basic_example.asm")
        myFixture.testStructureView {
            val tree = it.tree
            it.setActionActive(M68kStructureViewModel.SYMBOLS_FILTER_ID, true)
            PlatformTestUtil.waitWhileBusy(tree)
            PlatformTestUtil.assertTreeEqual(
                tree, """-basic_example.asm
 BLTHOGON
 BLTHOGOFF
 entry
 -init
  .looph
  .loopw
 main
 exit
"""
            )
        }
    }

    @Test
    internal fun global_labels_filter_works(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("basic_example.asm")
        myFixture.testStructureView {
            val tree = it.tree
            it.setActionActive(M68kStructureViewModel.GLOBAL_LABEL_FILTER_ID, true)
            PlatformTestUtil.waitWhileBusy(tree)
            PlatformTestUtil.assertTreeEqual(
                tree, """-basic_example.asm
 PIC_WIDTH
 PIC_HEIGHT
 DEBUG_LEVEL
 BLTHOGON
 BLTHOGOFF
"""
            )
        }
    }

    @Test
    internal fun local_labels_filter_works(@MyFixture myFixture: CodeInsightTestFixture) {
        myFixture.configureByFile("basic_example.asm")
        myFixture.testStructureView {
            val tree = it.tree
            it.setActionActive(M68kStructureViewModel.LOCAL_LABEL_FILTER_ID, true)
            PlatformTestUtil.waitWhileBusy(tree)
            PlatformTestUtil.assertTreeEqual(
                tree, """-basic_example.asm
 PIC_WIDTH
 PIC_HEIGHT
 DEBUG_LEVEL
 BLTHOGON
 BLTHOGOFF
 entry
 init
 main
 exit
"""
            )
        }
    }
}