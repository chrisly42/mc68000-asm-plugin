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
        testGoodSyntax(testCase, " MOVE.L d0,a7\n")
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
    internal fun register_indirect_with_scaled_index_and_offset_old_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l -4+foo(sp,d0.w*8),(10*20+4)(a1,a3*4)\n")
    }

    @Test
    internal fun register_indirect_with_scaled_index_and_offset_new_syntax(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (-4+foo,sp,a0*1),((10*20+4),a1,d5.l*2)\n")
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
    internal fun memory_indirect_with_all_params(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.w,a1],124),([-12.w,a0],-120)\n")
    }

    @Test
    internal fun memory_indirect_without_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([a1],124.l),([a0],-120.w)\n")
    }

    @Test
    internal fun memory_indirect_without_outer_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234,a1]),([-12,a0])\n")
    }

    @Test
    internal fun memory_indirect_minimal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([a1]),([a0])\n")
    }

    @Test
    internal fun memory_indirect_post_indexed_with_all_params(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.w,a1],a0.w*4,124),([-12,a0],d0.l*8,-120.w)\n")
    }

    @Test
    internal fun memory_indirect_post_indexed_without_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([a1],a0,124.l),([a0],d0.l*8,-120)\n")
    }

    @Test
    internal fun memory_indirect_post_indexed_without_outer_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.l,a1],a0),([-12,a0],d0.l*8)\n")
    }

    @Test
    internal fun memory_indirect_post_indexed_without_address_register(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.l],a0),([-12],d0.l*8)\n")
    }

    @Test
    internal fun memory_indirect_post_indexed_minimal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([a1],a0),([a0],d0)\n")
    }

    @Test
    internal fun memory_indirect_pre_indexed_with_all_params(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234,a1,a0.w*4],124),([-12,a0,d0.l*8],-120)\n")
    }

    @Test
    internal fun memory_indirect_pre_indexed_without_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([a1,a0],124),([a0,d0.l*8],-120.w)\n")
    }

    @Test
    internal fun memory_indirect_pre_indexed_without_outer_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.l,a1,a0]),([-12,a0,d0.l*8])\n")
    }

    @Test
    internal fun memory_indirect_pre_indexed_without_address_register(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.l,a0*4]),([-12,d0.l*8])\n")
    }

    @Test
    internal fun memory_indirect_pre_indexed_only_with_index(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([a0*4]),([d0.l*8])\n")
    }

    @Test
    internal fun memory_indirect_pre_indexed_minimal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([a1,a0]),([a0,d0])\n")
    }

    @Test
    internal fun pc_memory_indirect_with_all_params(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.w,pc],124),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_without_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([pc],124.l),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_without_outer_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234,pc]),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_minimal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([pc]),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_post_indexed_with_all_params(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.l,pc],a0.w*4,124.w),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_post_indexed_without_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([pc],a0.l*4,124),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_post_indexed_without_outer_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234,pc],a0),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_post_indexed_minimal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([pc],a0*4),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_pre_indexed_with_all_params(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234.w,pc,a0.w*4],124),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_pre_indexed_without_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([pc,a0*4],124.l),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_pre_indexed_without_outer_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([1234,pc,a0]),d0\n")
    }

    @Test
    internal fun pc_memory_indirect_pre_indexed_minimal(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l ([pc,a0.l*4]),d0\n")
    }

    @Test
    internal fun register_indirect_with_index_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (100.w,a0,a0.l*4),d0\n")
    }

    @Test
    internal fun register_indirect_with_index_base_displacement_without_areg(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (100.w,a0.l*4),d0\n")
    }

    @Test
    internal fun register_indirect_with_index_base_displacement_without_index(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (100.l,a0),d0\n")
    }

    @Test
    internal fun register_indirect_with_index_base_displacement_with_dreg_index(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (d0),(d0.w*4)\n")
    }

    @Test
    internal fun register_indirect_with_index_base_displacement_only(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (100.l),d0\n")
    }

    @Test
    internal fun register_indirect_with_index_base_displacement_zero(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (),d0\n")
    }

    @Test
    internal fun pc_indirect_with_index_base_displacement(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (100.w,pc,a0.l*4),d0\n")
    }

    @Test
    internal fun pc_indirect_with_index_base_displacement_without_index(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l (100.l,pc),d0\n")
    }

    @Test
    internal fun absolute_address(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " move.l 4.w,a6\n")
    }

    @Test
    internal fun btst_quirk(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " btst #5,#42\n btst d0,#42")
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
    internal fun movem_register_list_with_data_address_wrapping(@MyTestCase testCase: ParsingTestExtension.IParsingTestCase) {
        testGoodSyntax(testCase, " movem.l d0-a6,-(sp)\n")
        val element = testCase.file.findElementAt(9)
        val regList = PsiTreeUtil.getParentOfType(element, M68kRegisterListAddressingMode::class.java)!!
        assertThat(regList.registers).containsExactlyInAnyOrder(
            Register.D0, Register.D1, Register.D2, Register.D3, Register.D4, Register.D5, Register.D6, Register.D7,
            Register.A0, Register.A1, Register.A2, Register.A3, Register.A4, Register.A5, Register.A6,
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
                    + " move.l a5,USP\n"
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