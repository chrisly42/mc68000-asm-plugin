// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import de.platon42.intellij.plugins.m68k.psi.impl.*;

public interface M68kTypes {

    IElementType ABSOLUTE_ADDRESS_ADDRESSING_MODE = new M68kElementType("ABSOLUTE_ADDRESS_ADDRESSING_MODE");
    IElementType ADDRESSING_MODE = new M68kElementType("ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER = new M68kElementType("ADDRESS_REGISTER");
    IElementType ADDRESS_REGISTER_DIRECT_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_DIRECT_ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER_INDIRECT_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_INDIRECT_ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER_INDIRECT_POST_INC_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_INDIRECT_POST_INC_ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER_INDIRECT_PRE_DEC_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_INDIRECT_PRE_DEC_ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE");
    IElementType ADDRESS_REGISTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE = new M68kElementType("ADDRESS_REGISTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE");
    IElementType ADDRESS_SIZE = new M68kElementType("ADDRESS_SIZE");
    IElementType ASM_INSTRUCTION = new M68kElementType("ASM_INSTRUCTION");
    IElementType ASM_OP = new M68kElementType("ASM_OP");
    IElementType ASM_OPERANDS = new M68kElementType("ASM_OPERANDS");
    IElementType ASSIGNMENT = new M68kElementType("ASSIGNMENT");
    IElementType BINARY_ADD_EXPR = new M68kElementType("BINARY_ADD_EXPR");
    IElementType BINARY_BITWISE_AND_EXPR = new M68kElementType("BINARY_BITWISE_AND_EXPR");
    IElementType BINARY_BITWISE_OR_EXPR = new M68kElementType("BINARY_BITWISE_OR_EXPR");
    IElementType BINARY_BITWISE_XOR_EXPR = new M68kElementType("BINARY_BITWISE_XOR_EXPR");
    IElementType BINARY_CMP_EQ_EXPR = new M68kElementType("BINARY_CMP_EQ_EXPR");
    IElementType BINARY_CMP_GE_EXPR = new M68kElementType("BINARY_CMP_GE_EXPR");
    IElementType BINARY_CMP_GT_EXPR = new M68kElementType("BINARY_CMP_GT_EXPR");
    IElementType BINARY_CMP_LE_EXPR = new M68kElementType("BINARY_CMP_LE_EXPR");
    IElementType BINARY_CMP_LT_EXPR = new M68kElementType("BINARY_CMP_LT_EXPR");
    IElementType BINARY_CMP_NE_EXPR = new M68kElementType("BINARY_CMP_NE_EXPR");
    IElementType BINARY_DIV_EXPR = new M68kElementType("BINARY_DIV_EXPR");
    IElementType BINARY_LOGICAL_AND_EXPR = new M68kElementType("BINARY_LOGICAL_AND_EXPR");
    IElementType BINARY_LOGICAL_OR_EXPR = new M68kElementType("BINARY_LOGICAL_OR_EXPR");
    IElementType BINARY_MOD_EXPR = new M68kElementType("BINARY_MOD_EXPR");
    IElementType BINARY_MUL_EXPR = new M68kElementType("BINARY_MUL_EXPR");
    IElementType BINARY_SHIFT_L_EXPR = new M68kElementType("BINARY_SHIFT_L_EXPR");
    IElementType BINARY_SHIFT_R_EXPR = new M68kElementType("BINARY_SHIFT_R_EXPR");
    IElementType BINARY_SUB_EXPR = new M68kElementType("BINARY_SUB_EXPR");
    IElementType DATA_REGISTER = new M68kElementType("DATA_REGISTER");
    IElementType DATA_REGISTER_DIRECT_ADDRESSING_MODE = new M68kElementType("DATA_REGISTER_DIRECT_ADDRESSING_MODE");
    IElementType DATA_WIDTH = new M68kElementType("DATA_WIDTH");
    IElementType EXPR = new M68kElementType("EXPR");
    IElementType GLOBAL_LABEL = new M68kElementType("GLOBAL_LABEL");
    IElementType IMMEDIATE_DATA = new M68kElementType("IMMEDIATE_DATA");
    IElementType LABEL = new M68kElementType("LABEL");
    IElementType LITERAL_EXPR = new M68kElementType("LITERAL_EXPR");
    IElementType LOCAL_LABEL = new M68kElementType("LOCAL_LABEL");
    IElementType MACRO_CALL = new M68kElementType("MACRO_CALL");
    IElementType OPERAND_SIZE = new M68kElementType("OPERAND_SIZE");
    IElementType PAREN_EXPR = new M68kElementType("PAREN_EXPR");
    IElementType PREPROCESSOR_DIRECTIVE = new M68kElementType("PREPROCESSOR_DIRECTIVE");
    IElementType PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE = new M68kElementType("PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE");
    IElementType PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE = new M68kElementType("PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE");
    IElementType PROGRAM_COUNTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE = new M68kElementType("PROGRAM_COUNTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE");
    IElementType PROGRAM_COUNTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE = new M68kElementType("PROGRAM_COUNTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE");
    IElementType REF_EXPR = new M68kElementType("REF_EXPR");
    IElementType REGISTER = new M68kElementType("REGISTER");
    IElementType SPECIAL_REGISTER = new M68kElementType("SPECIAL_REGISTER");
    IElementType SPECIAL_REGISTER_DIRECT_ADDRESSING_MODE = new M68kElementType("SPECIAL_REGISTER_DIRECT_ADDRESSING_MODE");
    IElementType STATEMENT = new M68kElementType("STATEMENT");
    IElementType UNARY_COMPL_EXPR = new M68kElementType("UNARY_COMPL_EXPR");
    IElementType UNARY_MINUS_EXPR = new M68kElementType("UNARY_MINUS_EXPR");
    IElementType UNARY_NOT_EXPR = new M68kElementType("UNARY_NOT_EXPR");
    IElementType UNARY_PLUS_EXPR = new M68kElementType("UNARY_PLUS_EXPR");

    IElementType AREG = new M68kTokenType("AREG");
    IElementType BINARY = new M68kTokenType("BINARY");
    IElementType CNOP_TAG = new M68kTokenType("CNOP_TAG");
    IElementType COLON = new M68kTokenType(":");
    IElementType COMMENT = new M68kTokenType("COMMENT");
    IElementType DECIMAL = new M68kTokenType("DECIMAL");
    IElementType DOLLAR = new M68kTokenType("$");
    IElementType DOT = new M68kTokenType(".");
    IElementType DREG = new M68kTokenType("DREG");
    IElementType ELSE_TAG = new M68kTokenType("ELSE_TAG");
    IElementType ENDC_TAG = new M68kTokenType("ENDC_TAG");
    IElementType END_TAG = new M68kTokenType("END_TAG");
    IElementType EOL = new M68kTokenType("EOL");
    IElementType EQU = new M68kTokenType("EQU");
    IElementType EVEN_TAG = new M68kTokenType("EVEN_TAG");
    IElementType FAIL_TAG = new M68kTokenType("FAIL_TAG");
    IElementType HASH = new M68kTokenType("#");
    IElementType HEXADECIMAL = new M68kTokenType("HEXADECIMAL");
    IElementType IF_TAG = new M68kTokenType("IF_TAG");
    IElementType INCBIN_TAG = new M68kTokenType("INCBIN_TAG");
    IElementType INCLUDE_TAG = new M68kTokenType("INCLUDE_TAG");
    IElementType MACRO_END_TAG = new M68kTokenType("MACRO_END_TAG");
    IElementType MACRO_TAG = new M68kTokenType("MACRO_TAG");
    IElementType OCTAL = new M68kTokenType("OCTAL");
    IElementType OPSIZE_BS = new M68kTokenType("OPSIZE_BS");
    IElementType OPSIZE_WL = new M68kTokenType("OPSIZE_WL");
    IElementType OP_AR_DIV = new M68kTokenType("/");
    IElementType OP_AR_MOD = new M68kTokenType("%");
    IElementType OP_AR_MUL = new M68kTokenType("*");
    IElementType OP_AR_SHIFT_L = new M68kTokenType("<<");
    IElementType OP_AR_SHIFT_R = new M68kTokenType(">>");
    IElementType OP_ASSIGN = new M68kTokenType("=");
    IElementType OP_BITWISE_AND = new M68kTokenType("&");
    IElementType OP_BITWISE_OR = new M68kTokenType("|");
    IElementType OP_BITWISE_XOR = new M68kTokenType("^");
    IElementType OP_CMP_EQ = new M68kTokenType("==");
    IElementType OP_CMP_GT = new M68kTokenType(">");
    IElementType OP_CMP_GT_EQ = new M68kTokenType(">=");
    IElementType OP_CMP_LT = new M68kTokenType("<");
    IElementType OP_CMP_LT_EQ = new M68kTokenType("<=");
    IElementType OP_CMP_NOT_EQ = new M68kTokenType("!=");
    IElementType OP_CMP_NOT_EQ2 = new M68kTokenType("<>");
    IElementType OP_LOGICAL_AND = new M68kTokenType("&&");
    IElementType OP_LOGICAL_OR = new M68kTokenType("||");
    IElementType OP_MINUS = new M68kTokenType("-");
    IElementType OP_PLUS = new M68kTokenType("+");
    IElementType OP_UNARY_COMPL = new M68kTokenType("~");
    IElementType OP_UNARY_NOT = new M68kTokenType("!");
    IElementType PC = new M68kTokenType("PC");
    IElementType REG_CCR = new M68kTokenType("REG_CCR");
    IElementType REG_SR = new M68kTokenType("REG_SR");
    IElementType REG_USP = new M68kTokenType("REG_USP");
    IElementType REG_VBR = new M68kTokenType("REG_VBR");
    IElementType REPT_END_TAG = new M68kTokenType("REPT_END_TAG");
    IElementType REPT_TAG = new M68kTokenType("REPT_TAG");
    IElementType ROUND_L = new M68kTokenType("(");
    IElementType ROUND_R = new M68kTokenType(")");
    IElementType SECTION_TAG = new M68kTokenType("SECTION_TAG");
    IElementType SEMICOLON = new M68kTokenType(";");
    IElementType SEPARATOR = new M68kTokenType(",");
    IElementType SQUARE_L = new M68kTokenType("[");
    IElementType SQUARE_R = new M68kTokenType("]");
    IElementType STRINGLIT = new M68kTokenType("STRINGLIT");
    IElementType SYMBOL = new M68kTokenType("SYMBOL");
    IElementType WHITE_SPACE = new M68kTokenType("WHITE_SPACE");

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            if (type == ABSOLUTE_ADDRESS_ADDRESSING_MODE) return new M68kAbsoluteAddressAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER) return new M68kAddressRegisterImpl(node);
            else if (type == ADDRESS_REGISTER_DIRECT_ADDRESSING_MODE)
                return new M68kAddressRegisterDirectAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER_INDIRECT_ADDRESSING_MODE)
                return new M68kAddressRegisterIndirectAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER_INDIRECT_POST_INC_ADDRESSING_MODE)
                return new M68kAddressRegisterIndirectPostIncAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER_INDIRECT_PRE_DEC_ADDRESSING_MODE)
                return new M68kAddressRegisterIndirectPreDecAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE)
                return new M68kAddressRegisterIndirectWithDisplacementNewAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE)
                return new M68kAddressRegisterIndirectWithDisplacementOldAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE)
                return new M68kAddressRegisterIndirectWithIndexNewAddressingModeImpl(node);
            else if (type == ADDRESS_REGISTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE)
                return new M68kAddressRegisterIndirectWithIndexOldAddressingModeImpl(node);
            else if (type == ADDRESS_SIZE) return new M68kAddressSizeImpl(node);
            else if (type == ASM_INSTRUCTION) return new M68kAsmInstructionImpl(node);
            else if (type == ASM_OP) return new M68kAsmOpImpl(node);
            else if (type == ASM_OPERANDS) return new M68kAsmOperandsImpl(node);
            else if (type == ASSIGNMENT) return new M68kAssignmentImpl(node);
            else if (type == BINARY_ADD_EXPR) return new M68kBinaryAddExprImpl(node);
            else if (type == BINARY_BITWISE_AND_EXPR) return new M68kBinaryBitwiseAndExprImpl(node);
            else if (type == BINARY_BITWISE_OR_EXPR) return new M68kBinaryBitwiseOrExprImpl(node);
            else if (type == BINARY_BITWISE_XOR_EXPR) return new M68kBinaryBitwiseXorExprImpl(node);
            else if (type == BINARY_CMP_EQ_EXPR) return new M68kBinaryCmpEqExprImpl(node);
            else if (type == BINARY_CMP_GE_EXPR) return new M68kBinaryCmpGeExprImpl(node);
            else if (type == BINARY_CMP_GT_EXPR) return new M68kBinaryCmpGtExprImpl(node);
            else if (type == BINARY_CMP_LE_EXPR) return new M68kBinaryCmpLeExprImpl(node);
            else if (type == BINARY_CMP_LT_EXPR) return new M68kBinaryCmpLtExprImpl(node);
            else if (type == BINARY_CMP_NE_EXPR) return new M68kBinaryCmpNeExprImpl(node);
            else if (type == BINARY_DIV_EXPR) return new M68kBinaryDivExprImpl(node);
            else if (type == BINARY_LOGICAL_AND_EXPR) return new M68kBinaryLogicalAndExprImpl(node);
            else if (type == BINARY_LOGICAL_OR_EXPR) return new M68kBinaryLogicalOrExprImpl(node);
            else if (type == BINARY_MOD_EXPR) return new M68kBinaryModExprImpl(node);
            else if (type == BINARY_MUL_EXPR) return new M68kBinaryMulExprImpl(node);
            else if (type == BINARY_SHIFT_L_EXPR) return new M68kBinaryShiftLExprImpl(node);
            else if (type == BINARY_SHIFT_R_EXPR) return new M68kBinaryShiftRExprImpl(node);
            else if (type == BINARY_SUB_EXPR) return new M68kBinarySubExprImpl(node);
            else if (type == DATA_REGISTER) return new M68kDataRegisterImpl(node);
            else if (type == DATA_REGISTER_DIRECT_ADDRESSING_MODE)
                return new M68kDataRegisterDirectAddressingModeImpl(node);
            else if (type == DATA_WIDTH) return new M68kDataWidthImpl(node);
            else if (type == GLOBAL_LABEL) return new M68kGlobalLabelImpl(node);
            else if (type == IMMEDIATE_DATA) return new M68kImmediateDataImpl(node);
            else if (type == LABEL) return new M68kLabelImpl(node);
            else if (type == LITERAL_EXPR) return new M68kLiteralExprImpl(node);
            else if (type == LOCAL_LABEL) return new M68kLocalLabelImpl(node);
            else if (type == MACRO_CALL) return new M68kMacroCallImpl(node);
            else if (type == OPERAND_SIZE) return new M68kOperandSizeImpl(node);
            else if (type == PAREN_EXPR) return new M68kParenExprImpl(node);
            else if (type == PREPROCESSOR_DIRECTIVE) return new M68kPreprocessorDirectiveImpl(node);
            else if (type == PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_NEW_ADDRESSING_MODE)
                return new M68kProgramCounterIndirectWithDisplacementNewAddressingModeImpl(node);
            else if (type == PROGRAM_COUNTER_INDIRECT_WITH_DISPLACEMENT_OLD_ADDRESSING_MODE)
                return new M68kProgramCounterIndirectWithDisplacementOldAddressingModeImpl(node);
            else if (type == PROGRAM_COUNTER_INDIRECT_WITH_INDEX_NEW_ADDRESSING_MODE)
                return new M68kProgramCounterIndirectWithIndexNewAddressingModeImpl(node);
            else if (type == PROGRAM_COUNTER_INDIRECT_WITH_INDEX_OLD_ADDRESSING_MODE)
                return new M68kProgramCounterIndirectWithIndexOldAddressingModeImpl(node);
            else if (type == REF_EXPR) return new M68kRefExprImpl(node);
            else if (type == SPECIAL_REGISTER) return new M68kSpecialRegisterImpl(node);
            else if (type == SPECIAL_REGISTER_DIRECT_ADDRESSING_MODE)
                return new M68kSpecialRegisterDirectAddressingModeImpl(node);
            else if (type == STATEMENT) return new M68kStatementImpl(node);
            else if (type == UNARY_COMPL_EXPR) return new M68kUnaryComplExprImpl(node);
            else if (type == UNARY_MINUS_EXPR) return new M68kUnaryMinusExprImpl(node);
            else if (type == UNARY_NOT_EXPR) return new M68kUnaryNotExprImpl(node);
            else if (type == UNARY_PLUS_EXPR) return new M68kUnaryPlusExprImpl(node);
            throw new AssertionError("Unknown element type: " + type);
        }
    }
}
