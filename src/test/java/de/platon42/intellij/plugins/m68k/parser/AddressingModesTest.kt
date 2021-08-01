package de.platon42.intellij.plugins.m68k.parser

import com.intellij.psi.util.PsiTreeUtil
import de.platon42.intellij.jupiter.MyTestCase
import de.platon42.intellij.jupiter.ParsingTestExtension
import de.platon42.intellij.jupiter.TestDataSubPath
import de.platon42.intellij.plugins.m68k.asm.Register
import de.platon42.intellij.plugins.m68k.psi.M68kRegisterListAddressingMode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@TestDataSubPath("addressingmodes")
internal class AddressingModesTest : AbstractParsingTest() {

    @Test
    internal fun register_direct(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l d0,a7\n")
    }

    @Test
    internal fun register_indirect(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (sp),(a0)\n")
    }

    @Test
    internal fun register_indirect_predecrement_postincrement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (a1)+,-(sp)\n")
    }

    @Test
    internal fun register_indirect_with_offset_old_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l -4+foo(sp),(10*20+4)(a1)\n")
    }

    @Test
    internal fun register_indirect_with_offset_new_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (-4+foo,sp),((10*20+4),a1)\n")
    }

    @Test
    internal fun register_indirect_with_index_and_offset_old_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l -4+foo(sp,d0.w),(10*20+4)(a1,a3)\n")
    }

    @Test
    internal fun register_indirect_with_index_and_offset_new_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (-4+foo,sp,a0),((10*20+4),a1,d5.l)\n")
    }

    @Test
    internal fun register_indirect_with_index_special_case_without_offset(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (sp,d0.w),(a1,a3)\n")
    }

    @Test
    internal fun pc_indirect_with_offset_old_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l -4*4(pc),+4(pc)\n")
    }

    @Test
    internal fun pc_indirect_with_offset_new_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ((-4+foo),pc),((10*20+4),pc)\n")
    }

    @Test
    internal fun pc_indirect_special_case_without_offset(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (pc),d6\n")
    }

    @Test
    internal fun pc_indirect_with_index_and_offset_old_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l -4+foo(pc,d0.w),(10*20+4)(pc,a3)\n")
    }

    @Test
    internal fun pc_indirect_with_index_and_offset_new_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (-4+foo,pc,a0),((10*20+4),pc,d5.l)\n")
    }

    @Test
    internal fun pc_indirect_with_index_special_case_without_offset(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (pc,d0.w),(pc,a3)\n")
    }

    @Test
    internal fun absolute_address(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l 4.w,a6\n")
    }

    @Test
    internal fun movem_register_list(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " movem.l d0-d2/d4/a0/a2-a3/a5,-(sp)\n")
        val element = testCase.file.findElementAt(9)
        val regList = PsiTreeUtil.getParentOfType(element, M68kRegisterListAddressingMode::class.java)!!
        assertThat(regList.registers).containsExactlyInAnyOrder(
            Register.D0, Register.D1, Register.D2, Register.D4,
            Register.A0, Register.A2, Register.A3, Register.A5,
        )
    }

    @Test
    internal fun immediate_data(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " moveq.l #FOO_BAR,d0\n")
    }

    @Test
    internal fun special_register_move(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase, " move.l usp,a0\n"
                    + " move.l a5,usp\n"
        )
    }

    @Test
    internal fun complex_math_expression_in_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(
            testCase,
            " move.l -(~(!!(+(1//~WIDTH^@123+3*4/2+(NARF%10|32!21))<<2)>>1)&$1f)(a0),(-(~(!!(+(1//~WIDTH^@123+3*4/2+(NARF%10|32!21))<<2)>>1)&\$1f),a0,d0.l)\n"
        )
    }
}