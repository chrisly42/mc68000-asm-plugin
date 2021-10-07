// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static de.platon42.intellij.plugins.m68k.parser.M68kParserUtilBase.*;
import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class M68kParser implements PsiParser, LightPsiParser {

    public ASTNode parse(IElementType t, PsiBuilder b) {
        parseLight(t, b);
        return b.getTreeBuilt();
    }

    public void parseLight(IElementType t, PsiBuilder b) {
        boolean r;
        b = adapt_builder_(t, b, this, EXTENDS_SETS_);
        Marker m = enter_section_(b, 0, _COLLAPSE_, null);
        r = parse_root_(t, b);
        exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
    }

    protected boolean parse_root_(IElementType t, PsiBuilder b) {
        return parse_root_(t, b, 0);
    }

    static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
        return M68kFile(b, l + 1);
    }

    public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[]{
            create_token_set_(ADDRESS_REGISTER, DATA_REGISTER, REGISTER, SPECIAL_REGISTER),
            create_token_set_(ABSOLUTE_ADDRESS_ADDRESSING_MODE, ADDRESSING_MODE, ADDRESS_REGISTER_DIRECT_ADDRESSING_MODE, ADDRESS_REGISTER_INDIRECT_ADDRESSING_MODE,
                    ADDRESS_REGISTER_INDIRECT_POST_INC_ADDRESSING_MODE, ADDRESS_REGISTER_INDIRECT_PRE_DEC_ADDRESSING_MODE, ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE, ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE,
                    ADDRESS_REGISTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE, ADDRESS_REGISTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE, DATA_REGISTER_DIRECT_ADDRESSING_MODE, IMMEDIATE_DATA,
                    PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE, PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE, PROGRAM_COUNTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE, PROGRAM_COUNTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE,
                    REGISTER_LIST_ADDRESSING_MODE, SPECIAL_REGISTER_DIRECT_ADDRESSING_MODE),
            create_token_set_(BINARY_ADD_EXPR, BINARY_BITWISE_AND_EXPR, BINARY_BITWISE_OR_EXPR, BINARY_BITWISE_XOR_EXPR,
                    BINARY_CMP_EQ_EXPR, BINARY_CMP_GE_EXPR, BINARY_CMP_GT_EXPR, BINARY_CMP_LE_EXPR,
                    BINARY_CMP_LT_EXPR, BINARY_CMP_NE_EXPR, BINARY_DIV_EXPR, BINARY_LOGICAL_AND_EXPR,
                    BINARY_LOGICAL_OR_EXPR, BINARY_MOD_EXPR, BINARY_MUL_EXPR, BINARY_SHIFT_L_EXPR,
                    BINARY_SHIFT_R_EXPR, BINARY_SUB_EXPR, EXPR, LITERAL_EXPR,
                    PAREN_EXPR, REF_EXPR, UNARY_COMPL_EXPR, UNARY_MINUS_EXPR,
                    UNARY_NOT_EXPR, UNARY_PLUS_EXPR),
    };

    /* ********************************************************** */
    // expr AddressSize? !ROUND_L
    public static boolean AbsoluteAddressAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AbsoluteAddressAddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ABSOLUTE_ADDRESS_ADDRESSING_MODE, "<AddressingMode>");
        r = expr(b, l + 1, -1);
        r = r && AbsoluteAddressAddressingMode_1(b, l + 1);
        r = r && AbsoluteAddressAddressingMode_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // AddressSize?
    private static boolean AbsoluteAddressAddressingMode_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AbsoluteAddressAddressingMode_1")) return false;
        AddressSize(b, l + 1);
        return true;
    }

    // !ROUND_L
    private static boolean AbsoluteAddressAddressingMode_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AbsoluteAddressAddressingMode_2")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NOT_);
        r = !consumeTokenFast(b, ROUND_L);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AREG | REG_SP
    public static boolean AddressRegister(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegister")) return false;
        if (!nextTokenIs(b, "<address register>", AREG, REG_SP)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER, "<address register>");
        r = consumeToken(b, AREG);
        if (!r) r = consumeToken(b, REG_SP);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AddressRegister !(OP_MINUS|OP_AR_DIV)
    public static boolean AddressRegisterDirectAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterDirectAddressingMode")) return false;
        if (!nextTokenIsFast(b, AREG, REG_SP)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_DIRECT_ADDRESSING_MODE, "<AddressingMode>");
        r = AddressRegister(b, l + 1);
        r = r && AddressRegisterDirectAddressingMode_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // !(OP_MINUS|OP_AR_DIV)
    private static boolean AddressRegisterDirectAddressingMode_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterDirectAddressingMode_1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NOT_);
        r = !AddressRegisterDirectAddressingMode_1_0(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // OP_MINUS|OP_AR_DIV
    private static boolean AddressRegisterDirectAddressingMode_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterDirectAddressingMode_1_0")) return false;
        boolean r;
        r = consumeTokenFast(b, OP_MINUS);
        if (!r) r = consumeTokenFast(b, OP_AR_DIV);
        return r;
    }

    /* ********************************************************** */
    // ROUND_L AddressRegister ROUND_R !OP_PLUS
    public static boolean AddressRegisterIndirectAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectAddressingMode")) return false;
        if (!nextTokenIsFast(b, ROUND_L)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_INDIRECT_ADDRESSING_MODE, "<AddressingMode>");
        r = consumeTokenFast(b, ROUND_L);
        r = r && AddressRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        r = r && AddressRegisterIndirectAddressingMode_3(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // !OP_PLUS
    private static boolean AddressRegisterIndirectAddressingMode_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectAddressingMode_3")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NOT_);
        r = !consumeTokenFast(b, OP_PLUS);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ROUND_L AddressRegister ROUND_R OP_PLUS
    public static boolean AddressRegisterIndirectPostIncAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectPostIncAddressingMode")) return false;
        if (!nextTokenIsFast(b, ROUND_L)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_INDIRECT_POST_INC_ADDRESSING_MODE, "<AddressingMode>");
        r = consumeTokenFast(b, ROUND_L);
        r = r && AddressRegister(b, l + 1);
        r = r && consumeTokens(b, 0, ROUND_R, OP_PLUS);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // OP_MINUS ROUND_L AddressRegister ROUND_R
    public static boolean AddressRegisterIndirectPreDecAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectPreDecAddressingMode")) return false;
        if (!nextTokenIsFast(b, OP_MINUS)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_INDIRECT_PRE_DEC_ADDRESSING_MODE, "<AddressingMode>");
        r = consumeTokens(b, 0, OP_MINUS, ROUND_L);
        r = r && AddressRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ROUND_L expr SEPARATOR AddressRegister ROUND_R
    public static boolean AddressRegisterIndirectWithDisplacementNewAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectWithDisplacementNewAddressingMode")) return false;
        if (!nextTokenIsFast(b, ROUND_L)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE, "<AddressingMode>");
        r = consumeTokenFast(b, ROUND_L);
        r = r && expr(b, l + 1, -1);
        r = r && consumeToken(b, SEPARATOR);
        r = r && AddressRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // expr ROUND_L AddressRegister ROUND_R
    public static boolean AddressRegisterIndirectWithDisplacementOldAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectWithDisplacementOldAddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE, "<AddressingMode>");
        r = expr(b, l + 1, -1);
        r = r && consumeToken(b, ROUND_L);
        r = r && AddressRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ROUND_L (expr SEPARATOR)? AddressRegister SEPARATOR IndexRegister ROUND_R
    public static boolean AddressRegisterIndirectWithIndexNewAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectWithIndexNewAddressingMode")) return false;
        if (!nextTokenIsFast(b, ROUND_L)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE, "<AddressingMode>");
        r = consumeTokenFast(b, ROUND_L);
        r = r && AddressRegisterIndirectWithIndexNewAddressingMode_1(b, l + 1);
        r = r && AddressRegister(b, l + 1);
        r = r && consumeToken(b, SEPARATOR);
        r = r && IndexRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (expr SEPARATOR)?
    private static boolean AddressRegisterIndirectWithIndexNewAddressingMode_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectWithIndexNewAddressingMode_1")) return false;
        AddressRegisterIndirectWithIndexNewAddressingMode_1_0(b, l + 1);
        return true;
    }

    // expr SEPARATOR
    private static boolean AddressRegisterIndirectWithIndexNewAddressingMode_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectWithIndexNewAddressingMode_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = expr(b, l + 1, -1);
        r = r && consumeToken(b, SEPARATOR);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // expr? ROUND_L AddressRegister SEPARATOR IndexRegister ROUND_R
    public static boolean AddressRegisterIndirectWithIndexOldAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectWithIndexOldAddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_REGISTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE, "<AddressingMode>");
        r = AddressRegisterIndirectWithIndexOldAddressingMode_0(b, l + 1);
        r = r && consumeToken(b, ROUND_L);
        r = r && AddressRegister(b, l + 1);
        r = r && consumeToken(b, SEPARATOR);
        r = r && IndexRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // expr?
    private static boolean AddressRegisterIndirectWithIndexOldAddressingMode_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressRegisterIndirectWithIndexOldAddressingMode_0")) return false;
        expr(b, l + 1, -1);
        return true;
    }

    /* ********************************************************** */
    // OPSIZE_W|OPSIZE_L
    public static boolean AddressSize(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressSize")) return false;
        if (!nextTokenIs(b, "<.w|.l>", OPSIZE_L, OPSIZE_W)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ADDRESS_SIZE, "<.w|.l>");
        r = consumeToken(b, OPSIZE_W);
        if (!r) r = consumeToken(b, OPSIZE_L);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ImmediateData
    //                 | AddressRegisterIndirectPreDecAddressingMode
    //                 | AddressRegisterIndirectPostIncAddressingMode
    //                 | AddressRegisterIndirectAddressingMode
    //                 | AddressRegisterIndirectWithDisplacementNewAddressingMode
    //                 | ProgramCounterIndirectWithDisplacementNewAddressingMode
    //                 | AddressRegisterIndirectWithIndexNewAddressingMode
    //                 | ProgramCounterIndirectWithIndexNewAddressingMode
    //                 | AddressRegisterIndirectWithDisplacementOldAddressingMode
    //                 | ProgramCounterIndirectWithDisplacementOldAddressingMode
    //                 | AddressRegisterIndirectWithIndexOldAddressingMode
    //                 | ProgramCounterIndirectWithIndexOldAddressingMode
    //                 | SpecialRegisterDirectAddressingMode
    //                 | DataRegisterDirectAddressingMode
    //                 | AddressRegisterDirectAddressingMode
    //                 | RegisterListAddressingMode
    //                 | AbsoluteAddressAddressingMode
    public static boolean AddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _COLLAPSE_, ADDRESSING_MODE, "<AddressingMode>");
        r = ImmediateData(b, l + 1);
        if (!r) r = AddressRegisterIndirectPreDecAddressingMode(b, l + 1);
        if (!r) r = AddressRegisterIndirectPostIncAddressingMode(b, l + 1);
        if (!r) r = AddressRegisterIndirectAddressingMode(b, l + 1);
        if (!r) r = AddressRegisterIndirectWithDisplacementNewAddressingMode(b, l + 1);
        if (!r) r = ProgramCounterIndirectWithDisplacementNewAddressingMode(b, l + 1);
        if (!r) r = AddressRegisterIndirectWithIndexNewAddressingMode(b, l + 1);
        if (!r) r = ProgramCounterIndirectWithIndexNewAddressingMode(b, l + 1);
        if (!r) r = AddressRegisterIndirectWithDisplacementOldAddressingMode(b, l + 1);
        if (!r) r = ProgramCounterIndirectWithDisplacementOldAddressingMode(b, l + 1);
        if (!r) r = AddressRegisterIndirectWithIndexOldAddressingMode(b, l + 1);
        if (!r) r = ProgramCounterIndirectWithIndexOldAddressingMode(b, l + 1);
        if (!r) r = SpecialRegisterDirectAddressingMode(b, l + 1);
        if (!r) r = DataRegisterDirectAddressingMode(b, l + 1);
        if (!r) r = AddressRegisterDirectAddressingMode(b, l + 1);
        if (!r) r = RegisterListAddressingMode(b, l + 1);
        if (!r) r = AbsoluteAddressAddressingMode(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AsmOp AsmOperands?
    public static boolean AsmInstruction(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction")) return false;
        if (!nextTokenIs(b, MNEMONIC)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = AsmOp(b, l + 1);
        r = r && AsmInstruction_1(b, l + 1);
        exit_section_(b, m, ASM_INSTRUCTION, r);
        return r;
    }

    // AsmOperands?
    private static boolean AsmInstruction_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmInstruction_1")) return false;
        AsmOperands(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // MNEMONIC OperandSize?
    public static boolean AsmOp(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOp")) return false;
        if (!nextTokenIs(b, "<mnemonic>", MNEMONIC)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ASM_OP, "<mnemonic>");
        r = consumeToken(b, MNEMONIC);
        r = r && AsmOp_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // OperandSize?
    private static boolean AsmOp_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOp_1")) return false;
        OperandSize(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // AddressingMode (SEPARATOR AddressingMode)?
    static boolean AsmOperands(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOperands")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = AddressingMode(b, l + 1);
        r = r && AsmOperands_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (SEPARATOR AddressingMode)?
    private static boolean AsmOperands_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOperands_1")) return false;
        AsmOperands_1_0(b, l + 1);
        return true;
    }

    // SEPARATOR AddressingMode
    private static boolean AsmOperands_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AsmOperands_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, SEPARATOR);
        r = r && AddressingMode(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // SymbolDefinition COLON? (OP_ASSIGN|EQU) expr
    public static boolean Assignment(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Assignment")) return false;
        if (!nextTokenIs(b, SYMBOLDEF)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = SymbolDefinition(b, l + 1);
        r = r && Assignment_1(b, l + 1);
        r = r && Assignment_2(b, l + 1);
        r = r && expr(b, l + 1, -1);
        exit_section_(b, m, ASSIGNMENT, r);
        return r;
    }

    // COLON?
    private static boolean Assignment_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Assignment_1")) return false;
        consumeToken(b, COLON);
        return true;
    }

    // OP_ASSIGN|EQU
    private static boolean Assignment_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Assignment_2")) return false;
        boolean r;
        r = consumeToken(b, OP_ASSIGN);
        if (!r) r = consumeToken(b, EQU);
        return r;
    }

    /* ********************************************************** */
    // DataRegister | AddressRegister
    static boolean DataOrAddressRegister(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DataOrAddressRegister")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, null, "<data or address register>");
        r = DataRegister(b, l + 1);
        if (!r) r = AddressRegister(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // DREG
    public static boolean DataRegister(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DataRegister")) return false;
        if (!nextTokenIs(b, "<data register>", DREG)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, DATA_REGISTER, "<data register>");
        r = consumeToken(b, DREG);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // DataRegister !(OP_MINUS|OP_AR_DIV)
    public static boolean DataRegisterDirectAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DataRegisterDirectAddressingMode")) return false;
        if (!nextTokenIsFast(b, DREG)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, DATA_REGISTER_DIRECT_ADDRESSING_MODE, "<AddressingMode>");
        r = DataRegister(b, l + 1);
        r = r && DataRegisterDirectAddressingMode_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // !(OP_MINUS|OP_AR_DIV)
    private static boolean DataRegisterDirectAddressingMode_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DataRegisterDirectAddressingMode_1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NOT_);
        r = !DataRegisterDirectAddressingMode_1_0(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // OP_MINUS|OP_AR_DIV
    private static boolean DataRegisterDirectAddressingMode_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DataRegisterDirectAddressingMode_1_0")) return false;
        boolean r;
        r = consumeTokenFast(b, OP_MINUS);
        if (!r) r = consumeTokenFast(b, OP_AR_DIV);
        return r;
    }

    /* ********************************************************** */
    // OPSIZE_W|OPSIZE_L
    public static boolean DataWidth(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "DataWidth")) return false;
        if (!nextTokenIs(b, "<.w|.l>", OPSIZE_L, OPSIZE_W)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, DATA_WIDTH, "<.w|.l>");
        r = consumeToken(b, OPSIZE_W);
        if (!r) r = consumeToken(b, OPSIZE_L);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // GLOBAL_LABEL_DEF COLON*
    public static boolean GlobalLabel(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GlobalLabel")) return false;
        if (!nextTokenIs(b, "<global label>", GLOBAL_LABEL_DEF)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, GLOBAL_LABEL, "<global label>");
        r = consumeToken(b, GLOBAL_LABEL_DEF);
        r = r && GlobalLabel_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // COLON*
    private static boolean GlobalLabel_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GlobalLabel_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!consumeToken(b, COLON)) break;
            if (!empty_element_parsed_guard_(b, "GlobalLabel_1", c)) break;
        }
        return true;
    }

    /* ********************************************************** */
    // HASH expr
    public static boolean ImmediateData(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ImmediateData")) return false;
        if (!nextTokenIs(b, "<immediate data>", HASH)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, IMMEDIATE_DATA, "<immediate data>");
        r = consumeToken(b, HASH);
        r = r && expr(b, l + 1, -1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // DataOrAddressRegister DataWidth? (OP_AR_MUL IndexScale)?
    public static boolean IndexRegister(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IndexRegister")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, INDEX_REGISTER, "<index register>");
        r = DataOrAddressRegister(b, l + 1);
        r = r && IndexRegister_1(b, l + 1);
        r = r && IndexRegister_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // DataWidth?
    private static boolean IndexRegister_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IndexRegister_1")) return false;
        DataWidth(b, l + 1);
        return true;
    }

    // (OP_AR_MUL IndexScale)?
    private static boolean IndexRegister_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IndexRegister_2")) return false;
        IndexRegister_2_0(b, l + 1);
        return true;
    }

    // OP_AR_MUL IndexScale
    private static boolean IndexRegister_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IndexRegister_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, OP_AR_MUL);
        r = r && IndexScale(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // expr
    public static boolean IndexScale(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "IndexScale")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, INDEX_SCALE, "<scale value>");
        r = expr(b, l + 1, -1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // AsmInstruction | MacroCall
    static boolean Instruction(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Instruction")) return false;
        if (!nextTokenIs(b, "", MACRO_INVOCATION, MNEMONIC)) return false;
        boolean r;
        r = AsmInstruction(b, l + 1);
        if (!r) r = MacroCall(b, l + 1);
        return r;
    }

    /* ********************************************************** */
    // Instruction|PreprocessorDirective
    static boolean InstructionOnly(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "InstructionOnly")) return false;
        boolean r;
        r = Instruction(b, l + 1);
        if (!r) r = PreprocessorDirective(b, l + 1);
        return r;
    }

    /* ********************************************************** */
    // LocalLabel | GlobalLabel
    static boolean Label(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Label")) return false;
        if (!nextTokenIs(b, "", GLOBAL_LABEL_DEF, LOCAL_LABEL_DEF)) return false;
        boolean r;
        r = LocalLabel(b, l + 1);
        if (!r) r = GlobalLabel(b, l + 1);
        return r;
    }

    /* ********************************************************** */
    // LabelWithInstruction | LabelOnly | InstructionOnly
    static boolean LabelInsts(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LabelInsts")) return false;
        boolean r;
        r = LabelWithInstruction(b, l + 1);
        if (!r) r = LabelOnly(b, l + 1);
        if (!r) r = InstructionOnly(b, l + 1);
        return r;
    }

    /* ********************************************************** */
    // Label
    static boolean LabelOnly(PsiBuilder b, int l) {
        return Label(b, l + 1);
    }

    /* ********************************************************** */
    // Label (Instruction|PreprocessorDirective)
    static boolean LabelWithInstruction(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LabelWithInstruction")) return false;
        if (!nextTokenIs(b, "", GLOBAL_LABEL_DEF, LOCAL_LABEL_DEF)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = Label(b, l + 1);
        r = r && LabelWithInstruction_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // Instruction|PreprocessorDirective
    private static boolean LabelWithInstruction_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LabelWithInstruction_1")) return false;
        boolean r;
        r = Instruction(b, l + 1);
        if (!r) r = PreprocessorDirective(b, l + 1);
        return r;
    }

    /* ********************************************************** */
    // LOCAL_LABEL_DEF COLON?
    public static boolean LocalLabel(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LocalLabel")) return false;
        if (!nextTokenIs(b, "<local label>", LOCAL_LABEL_DEF)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, LOCAL_LABEL, "<local label>");
        r = consumeToken(b, LOCAL_LABEL_DEF);
        r = r && LocalLabel_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // COLON?
    private static boolean LocalLabel_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LocalLabel_1")) return false;
        consumeToken(b, COLON);
        return true;
    }

    /* ********************************************************** */
    // line*
    static boolean M68kFile(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "M68kFile")) return false;
        while (true) {
            int c = current_position_(b);
            if (!line(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "M68kFile", c)) break;
        }
        return true;
    }

    /* ********************************************************** */
    // MACRO_INVOCATION PlainOperands?
    public static boolean MacroCall(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroCall")) return false;
        if (!nextTokenIs(b, MACRO_INVOCATION)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, MACRO_INVOCATION);
        r = r && MacroCall_1(b, l + 1);
        exit_section_(b, m, MACRO_CALL, r);
        return r;
    }

    // PlainOperands?
    private static boolean MacroCall_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroCall_1")) return false;
        PlainOperands(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ((MacroNameDefinition COLON? MACRO_TAG)|(MACRO_TAG MacroNameDefinition)) MacroPlainLine* MACRO_END_TAG
    public static boolean MacroDefinition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroDefinition")) return false;
        if (!nextTokenIs(b, "<macro definition>", MACRO_NAME, MACRO_TAG)) return false;
        boolean r, p;
        Marker m = enter_section_(b, l, _NONE_, MACRO_DEFINITION, "<macro definition>");
        r = MacroDefinition_0(b, l + 1);
        p = r; // pin = 1
        r = r && report_error_(b, MacroDefinition_1(b, l + 1));
        r = p && consumeToken(b, MACRO_END_TAG) && r;
        exit_section_(b, l, m, r, p, null);
        return r || p;
    }

    // (MacroNameDefinition COLON? MACRO_TAG)|(MACRO_TAG MacroNameDefinition)
    private static boolean MacroDefinition_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroDefinition_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = MacroDefinition_0_0(b, l + 1);
        if (!r) r = MacroDefinition_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MacroNameDefinition COLON? MACRO_TAG
    private static boolean MacroDefinition_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroDefinition_0_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = MacroNameDefinition(b, l + 1);
        r = r && MacroDefinition_0_0_1(b, l + 1);
        r = r && consumeToken(b, MACRO_TAG);
        exit_section_(b, m, null, r);
        return r;
    }

    // COLON?
    private static boolean MacroDefinition_0_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroDefinition_0_0_1")) return false;
        consumeToken(b, COLON);
        return true;
    }

    // MACRO_TAG MacroNameDefinition
    private static boolean MacroDefinition_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroDefinition_0_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, MACRO_TAG);
        r = r && MacroNameDefinition(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // MacroPlainLine*
    private static boolean MacroDefinition_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroDefinition_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!MacroPlainLine(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "MacroDefinition_1", c)) break;
        }
        return true;
    }

    /* ********************************************************** */
    // MACRO_NAME
    public static boolean MacroNameDefinition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroNameDefinition")) return false;
        if (!nextTokenIs(b, MACRO_NAME)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, MACRO_NAME);
        exit_section_(b, m, MACRO_NAME_DEFINITION, r);
        return r;
    }

    /* ********************************************************** */
    // MACRO_LINE
    public static boolean MacroPlainLine(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "MacroPlainLine")) return false;
        if (!nextTokenIs(b, MACRO_LINE)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, MACRO_LINE);
        exit_section_(b, m, MACRO_PLAIN_LINE, r);
        return r;
    }

    /* ********************************************************** */
    // OPSIZE_BS|OPSIZE_W|OPSIZE_L
    public static boolean OperandSize(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "OperandSize")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, OPERAND_SIZE, "<.s|.b|.w|.l>");
        r = consumeToken(b, OPSIZE_BS);
        if (!r) r = consumeToken(b, OPSIZE_W);
        if (!r) r = consumeToken(b, OPSIZE_L);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // (expr|AddressingMode) (SEPARATOR (expr|AddressingMode))*
    static boolean PlainOperands(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PlainOperands")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = PlainOperands_0(b, l + 1);
        r = r && PlainOperands_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // expr|AddressingMode
    private static boolean PlainOperands_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PlainOperands_0")) return false;
        boolean r;
        r = expr(b, l + 1, -1);
        if (!r) r = AddressingMode(b, l + 1);
        return r;
    }

    // (SEPARATOR (expr|AddressingMode))*
    private static boolean PlainOperands_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PlainOperands_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!PlainOperands_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "PlainOperands_1", c)) break;
        }
        return true;
    }

    // SEPARATOR (expr|AddressingMode)
    private static boolean PlainOperands_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PlainOperands_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, SEPARATOR);
        r = r && PlainOperands_1_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // expr|AddressingMode
    private static boolean PlainOperands_1_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PlainOperands_1_0_1")) return false;
        boolean r;
        r = expr(b, l + 1, -1);
        if (!r) r = AddressingMode(b, l + 1);
        return r;
    }

    /* ********************************************************** */
    // PreprocessorKeyword PreprocessorOperands?
    public static boolean PreprocessorDirective(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PreprocessorDirective")) return false;
        if (!nextTokenIs(b, "<preprocessor directive>", DATA_DIRECTIVE, OTHER_DIRECTIVE)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PREPROCESSOR_DIRECTIVE, "<preprocessor directive>");
        r = PreprocessorKeyword(b, l + 1);
        r = r && PreprocessorDirective_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // PreprocessorOperands?
    private static boolean PreprocessorDirective_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PreprocessorDirective_1")) return false;
        PreprocessorOperands(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // DATA_DIRECTIVE | OTHER_DIRECTIVE
    public static boolean PreprocessorKeyword(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PreprocessorKeyword")) return false;
        if (!nextTokenIs(b, "<preprocessor keyword>", DATA_DIRECTIVE, OTHER_DIRECTIVE)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PREPROCESSOR_KEYWORD, "<preprocessor keyword>");
        r = consumeToken(b, DATA_DIRECTIVE);
        if (!r) r = consumeToken(b, OTHER_DIRECTIVE);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // expr
    static boolean PreprocessorOperand(PsiBuilder b, int l) {
        return expr(b, l + 1, -1);
    }

    /* ********************************************************** */
    // PreprocessorOperand (SEPARATOR PreprocessorOperand)*
    static boolean PreprocessorOperands(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PreprocessorOperands")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = PreprocessorOperand(b, l + 1);
        r = r && PreprocessorOperands_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (SEPARATOR PreprocessorOperand)*
    private static boolean PreprocessorOperands_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PreprocessorOperands_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!PreprocessorOperands_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "PreprocessorOperands_1", c)) break;
        }
        return true;
    }

    // SEPARATOR PreprocessorOperand
    private static boolean PreprocessorOperands_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PreprocessorOperands_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, SEPARATOR);
        r = r && PreprocessorOperand(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ROUND_L expr SEPARATOR PC ROUND_R
    public static boolean ProgramCounterIndirectWithDisplacementNewAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithDisplacementNewAddressingMode")) return false;
        if (!nextTokenIsFast(b, ROUND_L)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE, "<AddressingMode>");
        r = consumeTokenFast(b, ROUND_L);
        r = r && expr(b, l + 1, -1);
        r = r && consumeTokens(b, 0, SEPARATOR, PC, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // (ROUND_L PC ROUND_R) | (expr ROUND_L PC ROUND_R)
    public static boolean ProgramCounterIndirectWithDisplacementOldAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithDisplacementOldAddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE, "<AddressingMode>");
        r = ProgramCounterIndirectWithDisplacementOldAddressingMode_0(b, l + 1);
        if (!r) r = ProgramCounterIndirectWithDisplacementOldAddressingMode_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ROUND_L PC ROUND_R
    private static boolean ProgramCounterIndirectWithDisplacementOldAddressingMode_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithDisplacementOldAddressingMode_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, ROUND_L, PC, ROUND_R);
        exit_section_(b, m, null, r);
        return r;
    }

    // expr ROUND_L PC ROUND_R
    private static boolean ProgramCounterIndirectWithDisplacementOldAddressingMode_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithDisplacementOldAddressingMode_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = expr(b, l + 1, -1);
        r = r && consumeTokens(b, 0, ROUND_L, PC, ROUND_R);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ROUND_L (expr SEPARATOR)? PC SEPARATOR IndexRegister ROUND_R
    public static boolean ProgramCounterIndirectWithIndexNewAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithIndexNewAddressingMode")) return false;
        if (!nextTokenIsFast(b, ROUND_L)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PROGRAM_COUNTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE, "<AddressingMode>");
        r = consumeTokenFast(b, ROUND_L);
        r = r && ProgramCounterIndirectWithIndexNewAddressingMode_1(b, l + 1);
        r = r && consumeTokens(b, 0, PC, SEPARATOR);
        r = r && IndexRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (expr SEPARATOR)?
    private static boolean ProgramCounterIndirectWithIndexNewAddressingMode_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithIndexNewAddressingMode_1")) return false;
        ProgramCounterIndirectWithIndexNewAddressingMode_1_0(b, l + 1);
        return true;
    }

    // expr SEPARATOR
    private static boolean ProgramCounterIndirectWithIndexNewAddressingMode_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithIndexNewAddressingMode_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = expr(b, l + 1, -1);
        r = r && consumeToken(b, SEPARATOR);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // expr? ROUND_L PC SEPARATOR IndexRegister ROUND_R
    public static boolean ProgramCounterIndirectWithIndexOldAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithIndexOldAddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PROGRAM_COUNTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE, "<AddressingMode>");
        r = ProgramCounterIndirectWithIndexOldAddressingMode_0(b, l + 1);
        r = r && consumeTokens(b, 0, ROUND_L, PC, SEPARATOR);
        r = r && IndexRegister(b, l + 1);
        r = r && consumeToken(b, ROUND_R);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // expr?
    private static boolean ProgramCounterIndirectWithIndexOldAddressingMode_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterIndirectWithIndexOldAddressingMode_0")) return false;
        expr(b, l + 1, -1);
        return true;
    }

    /* ********************************************************** */
    // CURRENT_PC_SYMBOL
    public static boolean ProgramCounterReference(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ProgramCounterReference")) return false;
        if (!nextTokenIs(b, CURRENT_PC_SYMBOL)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, CURRENT_PC_SYMBOL);
        exit_section_(b, m, PROGRAM_COUNTER_REFERENCE, r);
        return r;
    }

    /* ********************************************************** */
    // DataRegister | AddressRegister | SpecialRegister
    public static boolean Register(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Register")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _COLLAPSE_, REGISTER, "<Register>");
        r = DataRegister(b, l + 1);
        if (!r) r = AddressRegister(b, l + 1);
        if (!r) r = SpecialRegister(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // (RegisterRange|DataOrAddressRegister) (OP_AR_DIV (RegisterRange|DataOrAddressRegister))*
    public static boolean RegisterListAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterListAddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, REGISTER_LIST_ADDRESSING_MODE, "<register list>");
        r = RegisterListAddressingMode_0(b, l + 1);
        r = r && RegisterListAddressingMode_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // RegisterRange|DataOrAddressRegister
    private static boolean RegisterListAddressingMode_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterListAddressingMode_0")) return false;
        boolean r;
        r = RegisterRange(b, l + 1);
        if (!r) r = DataOrAddressRegister(b, l + 1);
        return r;
    }

    // (OP_AR_DIV (RegisterRange|DataOrAddressRegister))*
    private static boolean RegisterListAddressingMode_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterListAddressingMode_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!RegisterListAddressingMode_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "RegisterListAddressingMode_1", c)) break;
        }
        return true;
    }

    // OP_AR_DIV (RegisterRange|DataOrAddressRegister)
    private static boolean RegisterListAddressingMode_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterListAddressingMode_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokenFast(b, OP_AR_DIV);
        r = r && RegisterListAddressingMode_1_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // RegisterRange|DataOrAddressRegister
    private static boolean RegisterListAddressingMode_1_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterListAddressingMode_1_0_1")) return false;
        boolean r;
        r = RegisterRange(b, l + 1);
        if (!r) r = DataOrAddressRegister(b, l + 1);
        return r;
    }

    /* ********************************************************** */
    // (DataRegister OP_MINUS DataRegister) | (AddressRegister OP_MINUS AddressRegister) | (DataRegister OP_MINUS AddressRegister)
    public static boolean RegisterRange(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterRange")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, REGISTER_RANGE, "<register range>");
        r = RegisterRange_0(b, l + 1);
        if (!r) r = RegisterRange_1(b, l + 1);
        if (!r) r = RegisterRange_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // DataRegister OP_MINUS DataRegister
    private static boolean RegisterRange_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterRange_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = DataRegister(b, l + 1);
        r = r && consumeToken(b, OP_MINUS);
        r = r && DataRegister(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // AddressRegister OP_MINUS AddressRegister
    private static boolean RegisterRange_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterRange_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = AddressRegister(b, l + 1);
        r = r && consumeToken(b, OP_MINUS);
        r = r && AddressRegister(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // DataRegister OP_MINUS AddressRegister
    private static boolean RegisterRange_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RegisterRange_2")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = DataRegister(b, l + 1);
        r = r && consumeToken(b, OP_MINUS);
        r = r && AddressRegister(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // REG_CCR | REG_SR | REG_USP | REG_VBR
    public static boolean SpecialRegister(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SpecialRegister")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SPECIAL_REGISTER, "<special register>");
        r = consumeToken(b, REG_CCR);
        if (!r) r = consumeToken(b, REG_SR);
        if (!r) r = consumeToken(b, REG_USP);
        if (!r) r = consumeToken(b, REG_VBR);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // SpecialRegister
    public static boolean SpecialRegisterDirectAddressingMode(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SpecialRegisterDirectAddressingMode")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SPECIAL_REGISTER_DIRECT_ADDRESSING_MODE, "<AddressingMode>");
        r = SpecialRegister(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // SYMBOLDEF
    public static boolean SymbolDefinition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SymbolDefinition")) return false;
        if (!nextTokenIs(b, SYMBOLDEF)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, SYMBOLDEF);
        exit_section_(b, m, SYMBOL_DEFINITION, r);
        return r;
    }

    /* ********************************************************** */
    // SYMBOL
    public static boolean SymbolReference(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SymbolReference")) return false;
        if (!nextTokenIs(b, SYMBOL)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, SYMBOL);
        exit_section_(b, m, SYMBOL_REFERENCE, r);
        return r;
    }

    /* ********************************************************** */
    // !<<eof>> (MacroDefinition | statement) (<<eof>>|EOL)
    static boolean line(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "line")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = line_0(b, l + 1);
        r = r && line_1(b, l + 1);
        r = r && line_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // !<<eof>>
    private static boolean line_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "line_0")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NOT_);
        r = !eof(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // MacroDefinition | statement
    private static boolean line_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "line_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = MacroDefinition(b, l + 1);
        if (!r) r = statement(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // <<eof>>|EOL
    private static boolean line_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "line_2")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = eof(b, l + 1);
        if (!r) r = consumeToken(b, EOL);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Assignment
    //             | LabelInsts
    public static boolean statement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "statement")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
        r = Assignment(b, l + 1);
        if (!r) r = LabelInsts(b, l + 1);
        exit_section_(b, l, m, r, false, M68kParser::statement_recover);
        return r;
    }

    /* ********************************************************** */
    // !(EOL)
    static boolean statement_recover(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "statement_recover")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NOT_);
        r = !statement_recover_0(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (EOL)
    private static boolean statement_recover_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "statement_recover_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokenFast(b, EOL);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Expression root: expr
    // Operator priority table:
    // 0: BINARY(binary_logical_or_expr)
    // 1: BINARY(binary_logical_and_expr)
    // 2: BINARY(binary_cmp_eq_expr) BINARY(binary_cmp_ne_expr)
    // 3: BINARY(binary_cmp_lt_expr) BINARY(binary_cmp_le_expr) BINARY(binary_cmp_gt_expr) BINARY(binary_cmp_ge_expr)
    // 4: BINARY(binary_add_expr) BINARY(binary_sub_expr)
    // 5: BINARY(binary_mul_expr) BINARY(binary_div_expr) BINARY(binary_mod_expr)
    // 6: BINARY(binary_bitwise_or_expr)
    // 7: BINARY(binary_bitwise_xor_expr)
    // 8: BINARY(binary_bitwise_and_expr)
    // 9: BINARY(binary_shift_l_expr) BINARY(binary_shift_r_expr)
    // 10: PREFIX(unary_plus_expr) PREFIX(unary_minus_expr) PREFIX(unary_not_expr) PREFIX(unary_compl_expr)
    // 11: ATOM(ref_expr) ATOM(literal_expr) PREFIX(paren_expr)
    public static boolean expr(PsiBuilder b, int l, int g) {
        if (!recursion_guard_(b, l, "expr")) return false;
        addVariant(b, "<expression>");
        boolean r, p;
        Marker m = enter_section_(b, l, _NONE_, "<expression>");
        r = unary_plus_expr(b, l + 1);
        if (!r) r = unary_minus_expr(b, l + 1);
        if (!r) r = unary_not_expr(b, l + 1);
        if (!r) r = unary_compl_expr(b, l + 1);
        if (!r) r = ref_expr(b, l + 1);
        if (!r) r = literal_expr(b, l + 1);
        if (!r) r = paren_expr(b, l + 1);
        p = r;
        r = r && expr_0(b, l + 1, g);
        exit_section_(b, l, m, null, r, p, null);
        return r || p;
    }

    public static boolean expr_0(PsiBuilder b, int l, int g) {
        if (!recursion_guard_(b, l, "expr_0")) return false;
        boolean r = true;
        while (true) {
            Marker m = enter_section_(b, l, _LEFT_, null);
            if (g < 0 && consumeTokenSmart(b, OP_LOGICAL_OR)) {
                r = expr(b, l, 0);
                exit_section_(b, l, m, BINARY_LOGICAL_OR_EXPR, r, true, null);
            } else if (g < 1 && consumeTokenSmart(b, OP_LOGICAL_AND)) {
                r = expr(b, l, 1);
                exit_section_(b, l, m, BINARY_LOGICAL_AND_EXPR, r, true, null);
            } else if (g < 2 && consumeTokenSmart(b, OP_CMP_EQ)) {
                r = expr(b, l, 2);
                exit_section_(b, l, m, BINARY_CMP_EQ_EXPR, r, true, null);
            } else if (g < 2 && consumeTokenSmart(b, OP_CMP_NOT_EQ)) {
                r = expr(b, l, 2);
                exit_section_(b, l, m, BINARY_CMP_NE_EXPR, r, true, null);
            } else if (g < 3 && consumeTokenSmart(b, OP_CMP_LT)) {
                r = expr(b, l, 3);
                exit_section_(b, l, m, BINARY_CMP_LT_EXPR, r, true, null);
            } else if (g < 3 && consumeTokenSmart(b, OP_CMP_LT_EQ)) {
                r = expr(b, l, 3);
                exit_section_(b, l, m, BINARY_CMP_LE_EXPR, r, true, null);
            } else if (g < 3 && consumeTokenSmart(b, OP_CMP_GT)) {
                r = expr(b, l, 3);
                exit_section_(b, l, m, BINARY_CMP_GT_EXPR, r, true, null);
            } else if (g < 3 && consumeTokenSmart(b, OP_CMP_GT_EQ)) {
                r = expr(b, l, 3);
                exit_section_(b, l, m, BINARY_CMP_GE_EXPR, r, true, null);
            } else if (g < 4 && consumeTokenSmart(b, OP_PLUS)) {
                r = expr(b, l, 4);
                exit_section_(b, l, m, BINARY_ADD_EXPR, r, true, null);
            } else if (g < 4 && consumeTokenSmart(b, OP_MINUS)) {
                r = expr(b, l, 4);
                exit_section_(b, l, m, BINARY_SUB_EXPR, r, true, null);
            } else if (g < 5 && consumeTokenSmart(b, OP_AR_MUL)) {
                r = expr(b, l, 5);
                exit_section_(b, l, m, BINARY_MUL_EXPR, r, true, null);
            } else if (g < 5 && consumeTokenSmart(b, OP_AR_DIV)) {
                r = expr(b, l, 5);
                exit_section_(b, l, m, BINARY_DIV_EXPR, r, true, null);
            } else if (g < 5 && consumeTokenSmart(b, OP_AR_MOD)) {
                r = expr(b, l, 5);
                exit_section_(b, l, m, BINARY_MOD_EXPR, r, true, null);
            } else if (g < 6 && binary_bitwise_or_expr_0(b, l + 1)) {
                r = expr(b, l, 6);
                exit_section_(b, l, m, BINARY_BITWISE_OR_EXPR, r, true, null);
            } else if (g < 7 && consumeTokenSmart(b, OP_BITWISE_XOR)) {
                r = expr(b, l, 7);
                exit_section_(b, l, m, BINARY_BITWISE_XOR_EXPR, r, true, null);
            } else if (g < 8 && consumeTokenSmart(b, OP_BITWISE_AND)) {
                r = expr(b, l, 8);
                exit_section_(b, l, m, BINARY_BITWISE_AND_EXPR, r, true, null);
            } else if (g < 9 && consumeTokenSmart(b, OP_AR_SHIFT_L)) {
                r = expr(b, l, 9);
                exit_section_(b, l, m, BINARY_SHIFT_L_EXPR, r, true, null);
            } else if (g < 9 && consumeTokenSmart(b, OP_AR_SHIFT_R)) {
                r = expr(b, l, 9);
                exit_section_(b, l, m, BINARY_SHIFT_R_EXPR, r, true, null);
            } else {
                exit_section_(b, l, m, null, false, false, null);
                break;
            }
        }
        return r;
    }

    public static boolean unary_plus_expr(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "unary_plus_expr")) return false;
        if (!nextTokenIsSmart(b, OP_PLUS)) return false;
        boolean r, p;
        Marker m = enter_section_(b, l, _NONE_, null);
        r = consumeTokenSmart(b, OP_PLUS);
        p = r;
        r = p && expr(b, l, 10);
        exit_section_(b, l, m, UNARY_PLUS_EXPR, r, p, null);
        return r || p;
    }

    public static boolean unary_minus_expr(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "unary_minus_expr")) return false;
        if (!nextTokenIsSmart(b, OP_MINUS)) return false;
        boolean r, p;
        Marker m = enter_section_(b, l, _NONE_, null);
        r = consumeTokenSmart(b, OP_MINUS);
        p = r;
        r = p && expr(b, l, 10);
        exit_section_(b, l, m, UNARY_MINUS_EXPR, r, p, null);
        return r || p;
    }

    // OP_BITWISE_OR|OP_UNARY_NOT
    private static boolean binary_bitwise_or_expr_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "binary_bitwise_or_expr_0")) return false;
        boolean r;
        r = consumeTokenSmart(b, OP_BITWISE_OR);
        if (!r) r = consumeTokenSmart(b, OP_UNARY_NOT);
        return r;
    }

    public static boolean unary_not_expr(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "unary_not_expr")) return false;
        if (!nextTokenIsSmart(b, OP_UNARY_NOT)) return false;
        boolean r, p;
        Marker m = enter_section_(b, l, _NONE_, null);
        r = consumeTokenSmart(b, OP_UNARY_NOT);
        p = r;
        r = p && expr(b, l, 10);
        exit_section_(b, l, m, UNARY_NOT_EXPR, r, p, null);
        return r || p;
    }

    public static boolean unary_compl_expr(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "unary_compl_expr")) return false;
        if (!nextTokenIsSmart(b, OP_UNARY_COMPL)) return false;
        boolean r, p;
        Marker m = enter_section_(b, l, _NONE_, null);
        r = consumeTokenSmart(b, OP_UNARY_COMPL);
        p = r;
        r = p && expr(b, l, 10);
        exit_section_(b, l, m, UNARY_COMPL_EXPR, r, p, null);
        return r || p;
    }

    // SymbolReference|ProgramCounterReference
    public static boolean ref_expr(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ref_expr")) return false;
        if (!nextTokenIsSmart(b, CURRENT_PC_SYMBOL, SYMBOL)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, REF_EXPR, "<expression>");
        r = SymbolReference(b, l + 1);
        if (!r) r = ProgramCounterReference(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // BINARY|DECIMAL|HEXADECIMAL|OCTAL|STRINGLIT
    public static boolean literal_expr(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "literal_expr")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, LITERAL_EXPR, "<expression>");
        r = consumeTokenSmart(b, BINARY);
        if (!r) r = consumeTokenSmart(b, DECIMAL);
        if (!r) r = consumeTokenSmart(b, HEXADECIMAL);
        if (!r) r = consumeTokenSmart(b, OCTAL);
        if (!r) r = consumeTokenSmart(b, STRINGLIT);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    public static boolean paren_expr(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "paren_expr")) return false;
        if (!nextTokenIsSmart(b, ROUND_L)) return false;
        boolean r, p;
        Marker m = enter_section_(b, l, _NONE_, null);
        r = consumeTokenSmart(b, ROUND_L);
        p = r;
        r = p && expr(b, l, -1);
        r = p && report_error_(b, consumeToken(b, ROUND_R)) && r;
        exit_section_(b, l, m, PAREN_EXPR, r, p, null);
        return r || p;
    }

}
