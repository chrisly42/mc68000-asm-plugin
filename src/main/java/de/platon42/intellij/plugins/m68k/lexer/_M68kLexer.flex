package de.platon42.intellij.plugins.m68k.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.*;

%%

%{
  public _M68kLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _M68kLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

EOL=\R
WHITE_SPACE=\p{Blank}+
EQU=([eE][qQ][uU])
EVEN_TAG=((\p{Blank}+)?[eE][vV][eE][nN])
CNOP_TAG=((\p{Blank}+)?[cC][nN][oO][pP])
SECTION_TAG=((\p{Blank}+)?[sS][eE][cC][tT][iI][oO][nN])
INCLUDE_TAG=((\p{Blank}+)?[iI][nN][cC][lL][uU][dD][eE])
INCBIN_TAG=((\p{Blank}+)?[iI][nN][cC][bB][iI][nN])
IF_TAG=((\p{Blank}+)?[iI][fF][:letter:]*)
ELSE_TAG=((\p{Blank}+)?[eE][lL][sS][eE])
ENDC_TAG=((\p{Blank}+)?[eE][nN][dD][cC])
MACRO_TAG=((\p{Blank}+)?[mM][aA][cC][rR][oO])
MACRO_END_TAG=((\p{Blank}+)?[eE][nN][dD][mM])
REPT_TAG=((\p{Blank}+)?[rR][eE][pP][tT])
REPT_END_TAG=((\p{Blank}+)?[eE][nN][dD][rR])
FAIL_TAG=((\p{Blank}+)?[fF][aA][iI][lL])
END_TAG=((\p{Blank}+)?[eE][nN][dD]).*
AREG=(([aA][0-7])|sp|SP)
DREG=([dD][0-7])
PC=([pP][cC])
REG_CCR=([cC][cC][rR])
REG_SR=([sS][rR])
REG_USP=([uU][sS][pP])
REG_VBR=([vV][bB][rR])
SYMBOL=(([:letter:]|_)(([:letter:]|[:digit:])|_)*)
OPSIZE_BS=(\.([bB]|[sS]))
OPSIZE_WL=(\.([wW]|[lL]))
BINARY=(%[01]+)
HEXADECIMAL=(\$[0-9a-fA-F]+)
OCTAL=(@[0-7]+)
DECIMAL=([0-9]+)
STRINGLIT=(`([^`\\]|\\.)*`|'([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")|<([^`\\]|\\.)*>
COMMENT=(\p{Blank}*?[;*].*+)

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return WHITE_SPACE; }

  ":"                  { return COLON; }
  ";"                  { return SEMICOLON; }
  "["                  { return SQUARE_L; }
  "]"                  { return SQUARE_R; }
  "("                  { return ROUND_L; }
  ")"                  { return ROUND_R; }
  ","                  { return SEPARATOR; }
  "."                  { return DOT; }
  "#"                  { return HASH; }
  "$"                  { return DOLLAR; }
  "="                  { return OP_ASSIGN; }
  "!"                  { return OP_UNARY_NOT; }
  "~"                  { return OP_UNARY_COMPL; }
  "+"                  { return OP_PLUS; }
  "-"                  { return OP_MINUS; }
  "*"                  { return OP_AR_MUL; }
  "/"                  { return OP_AR_DIV; }
  "%"                  { return OP_AR_MOD; }
  "<<"                 { return OP_AR_SHIFT_L; }
  ">>"                 { return OP_AR_SHIFT_R; }
  "&"                  { return OP_BITWISE_AND; }
  "|"                  { return OP_BITWISE_OR; }
  "^"                  { return OP_BITWISE_XOR; }
  "&&"                 { return OP_LOGICAL_AND; }
  "||"                 { return OP_LOGICAL_OR; }
  "=="                 { return OP_CMP_EQ; }
  "<"                  { return OP_CMP_LT; }
  ">"                  { return OP_CMP_GT; }
  ">="                 { return OP_CMP_GT_EQ; }
  "<="                 { return OP_CMP_LT_EQ; }
  "!="                 { return OP_CMP_NOT_EQ; }
  "<>"                 { return OP_CMP_NOT_EQ2; }

  {EOL}                { return EOL; }
  {WHITE_SPACE}        { return WHITE_SPACE; }
  {EQU}                { return EQU; }
  {EVEN_TAG}           { return EVEN_TAG; }
  {CNOP_TAG}           { return CNOP_TAG; }
  {SECTION_TAG}        { return SECTION_TAG; }
  {INCLUDE_TAG}        { return INCLUDE_TAG; }
  {INCBIN_TAG}         { return INCBIN_TAG; }
  {IF_TAG}             { return IF_TAG; }
  {ELSE_TAG}           { return ELSE_TAG; }
  {ENDC_TAG}           { return ENDC_TAG; }
  {MACRO_TAG}          { return MACRO_TAG; }
  {MACRO_END_TAG}      { return MACRO_END_TAG; }
  {REPT_TAG}           { return REPT_TAG; }
  {REPT_END_TAG}       { return REPT_END_TAG; }
  {FAIL_TAG}           { return FAIL_TAG; }
  {END_TAG}            { return END_TAG; }
  {AREG}               { return AREG; }
  {DREG}               { return DREG; }
  {PC}                 { return PC; }
  {REG_CCR}            { return REG_CCR; }
  {REG_SR}             { return REG_SR; }
  {REG_USP}            { return REG_USP; }
  {REG_VBR}            { return REG_VBR; }
  {SYMBOL}             { return SYMBOL; }
  {OPSIZE_BS}          { return OPSIZE_BS; }
  {OPSIZE_WL}          { return OPSIZE_WL; }
  {BINARY}             { return BINARY; }
  {HEXADECIMAL}        { return HEXADECIMAL; }
  {OCTAL}              { return OCTAL; }
  {DECIMAL}            { return DECIMAL; }
  {STRINGLIT}          { return STRINGLIT; }
  {COMMENT}            { return COMMENT; }

}

[^] { return BAD_CHARACTER; }
