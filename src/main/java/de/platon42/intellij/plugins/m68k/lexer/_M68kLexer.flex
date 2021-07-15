package de.platon42.intellij.plugins.m68k.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.*;
import static de.platon42.intellij.plugins.m68k.lexer.LexerUtil.*;

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
%ignorecase

EOL=\R
WHITE_SPACE=\s+

EOL=\R
WHITE_SPACE=\p{Blank}+
IF_TAG=(if[:letter:]*)
AREG=((a[0-7])|sp)
DREG=(d[0-7])
ASSIGNMENT=(([:letter:]|_)(([:letter:]|[:digit:])|_)*((\p{Blank}+equ\p{Blank}+)|(\p{Blank}+set\p{Blank}+)|\p{Blank}*=\p{Blank}*))
LOCAL_LABEL=(\.([:letter:]|_)(([:letter:]|[:digit:])|_)*:?)|(([:letter:]|_)(([:letter:]|[:digit:])|_)*\$:?)
LOCAL_LABEL_WC=(\.([:letter:]|_)(([:letter:]|[:digit:])|_)*:)|(([:letter:]|_)(([:letter:]|[:digit:])|_)*\$:)
GLOBAL_LABEL=(([:letter:]|_)(([:letter:]|[:digit:])|_)*:?:?)
GLOBAL_LABEL_WC=(([:letter:]|_)(([:letter:]|[:digit:])|_)*::?)
MNEMONIC=(([:letter:])+)
SYMBOL=(([:letter:]|_|\.)(([:letter:]|[:digit:])|[_\$])*)
OPSIZE_BS=(\.[bs])
OPSIZE_WL=(\.[wl])
BINARY=(%[01]+)
HEXADECIMAL=(\$[0-9a-f]+)
OCTAL=(@[0-7]+)
DECIMAL=([0-9]+)
STRINGLIT=(`([^`\\]|\\.)*`|'([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")|<([^`\\]|\\.)*>
COMMENT=([;].*+)
HASH_COMMENT=([#;*].*+)

%state NOSOL,INSTRPART,ASMINSTR,ASMOPS,ASSIGNMENT,WAITEOL

%%
<YYINITIAL> {
  {WHITE_SPACE}       { yybegin(NOSOL); return WHITE_SPACE; }
  {EOL}               { return WHITE_SPACE; }
  {ASSIGNMENT}        { yybegin(ASSIGNMENT); yypushback(pushbackAssignment(yytext())); return SYMBOLDEF; }
  {LOCAL_LABEL}       { yybegin(INSTRPART); return LOCAL_LABEL_DEF; }
  {GLOBAL_LABEL}      { yybegin(INSTRPART); return GLOBAL_LABEL_DEF; }

  {HASH_COMMENT}      { return COMMENT; }
}

<NOSOL> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return WHITE_SPACE; }
  {LOCAL_LABEL_WC}    { yybegin(INSTRPART); return LOCAL_LABEL_DEF; }
  {GLOBAL_LABEL_WC}   { yybegin(INSTRPART); return GLOBAL_LABEL_DEF; }
  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { yybegin(INSTRPART); return SYMBOL; } }
  {SYMBOL}            { yybegin(INSTRPART); return SYMBOL; }

  {HASH_COMMENT}      { yybegin(YYINITIAL); return COMMENT; }
}

<INSTRPART> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  "even"              { return EVEN_TAG; }
  "cnop"              { return CNOP_TAG; }
  "section"           { return SECTION_TAG; }
  "include"           { return INCLUDE_TAG; }
  "incbin"            { return INCBIN_TAG; }
  "else"              { return ELSE_TAG; }
  "endc"              { return ENDC_TAG; }
  "macro"             { return MACRO_TAG; }
  "endm"              { return MACRO_END_TAG; }
  "rept"              { return REPT_TAG; }
  "endr"              { return REPT_END_TAG; }
  "fail"              { return FAIL_TAG; }
  "end"               { return END_TAG; }

  {IF_TAG}            { return IF_TAG; }

  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { return SYMBOL; } }
  {SYMBOL}            { return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<ASMINSTR> {
  {WHITE_SPACE}       { yybegin(ASMOPS); return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {OPSIZE_BS}         { return OPSIZE_BS; }
  {OPSIZE_WL}         { return OPSIZE_WL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<ASSIGNMENT> {
  {WHITE_SPACE}       { return WHITE_SPACE; } // FIXME space optionally introduces comment
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {BINARY}            { return BINARY; }
  {HEXADECIMAL}       { return HEXADECIMAL; }
  {OCTAL}             { return OCTAL; }
  {DECIMAL}           { return DECIMAL; }
  {STRINGLIT}         { return STRINGLIT; }

  "equ"               { return EQU; }
  "set"               { return EQU; }

  "<<"                { return OP_AR_SHIFT_L; }
  ">>"                { return OP_AR_SHIFT_R; }
  "&&"                { return OP_LOGICAL_AND; }
  "||"                { return OP_LOGICAL_OR; }
  "=="                { return OP_CMP_EQ; }
  "<>"                { return OP_CMP_NOT_EQ; }
  ">="                { return OP_CMP_GT_EQ; }
  "<="                { return OP_CMP_LT_EQ; }
  "!="                { return OP_CMP_NOT_EQ; }
  "<"                 { return OP_CMP_LT; }
  ">"                 { return OP_CMP_GT; }
  "&"                 { return OP_BITWISE_AND; }
  "|"                 { return OP_BITWISE_OR; }
  "^"                 { return OP_BITWISE_XOR; }
  ":"                 { return COLON; }
  ";"                 { return SEMICOLON; }
  "["                 { return SQUARE_L; }
  "]"                 { return SQUARE_R; }
  "("                 { return ROUND_L; }
  ")"                 { return ROUND_R; }
  "."                 { return DOT; }
  "$"                 { return DOLLAR; }
  "="                 { return OP_ASSIGN; }
  "!"                 { return OP_UNARY_NOT; }
  "~"                 { return OP_UNARY_COMPL; }
  "+"                 { return OP_PLUS; }
  "-"                 { return OP_MINUS; }
  "*"                 { return OP_AR_MUL; }
  "/"                 { return OP_AR_DIV; }
  "%"                 { return OP_AR_MOD; }

  {SYMBOL}            { return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<ASMOPS> {
  {WHITE_SPACE}       { return WHITE_SPACE; } // FIXME space optionally introduces comment
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {BINARY}            { return BINARY; }
  {HEXADECIMAL}       { return HEXADECIMAL; }
  {OCTAL}             { return OCTAL; }
  {DECIMAL}           { return DECIMAL; }
  {STRINGLIT}         { return STRINGLIT; }

  {OPSIZE_BS}         { return OPSIZE_BS; }
  {OPSIZE_WL}         { return OPSIZE_WL; }

  {AREG}              { return AREG; }
  {DREG}              { return DREG; }
  "pc"                { return PC; }
  "ccr"               { return REG_CCR; }
  "sr"                { return REG_SR; }
  "usp"               { return REG_USP; }
  "vbr"               { return REG_VBR; }

  "<<"                { return OP_AR_SHIFT_L; }
  ">>"                { return OP_AR_SHIFT_R; }
  "&&"                { return OP_LOGICAL_AND; }
  "||"                { return OP_LOGICAL_OR; }
  "=="                { return OP_CMP_EQ; }
  "<>"                { return OP_CMP_NOT_EQ; }
  ">="                { return OP_CMP_GT_EQ; }
  "<="                { return OP_CMP_LT_EQ; }
  "!="                { return OP_CMP_NOT_EQ; }
  "<"                 { return OP_CMP_LT; }
  ">"                 { return OP_CMP_GT; }
  "&"                 { return OP_BITWISE_AND; }
  "|"                 { return OP_BITWISE_OR; }
  "^"                 { return OP_BITWISE_XOR; }
  ":"                 { return COLON; }
  ";"                 { return SEMICOLON; }
  "["                 { return SQUARE_L; }
  "]"                 { return SQUARE_R; }
  "("                 { return ROUND_L; }
  ")"                 { return ROUND_R; }
  ","                 { return SEPARATOR; }
  "."                 { return DOT; }
  "#"                 { return HASH; }
  "$"                 { return DOLLAR; }
  "="                 { return OP_ASSIGN; }
  "!"                 { return OP_UNARY_NOT; }
  "~"                 { return OP_UNARY_COMPL; }
  "+"                 { return OP_PLUS; }
  "-"                 { return OP_MINUS; }
  "*"                 { return OP_AR_MUL; }
  "/"                 { return OP_AR_DIV; }
  "%"                 { return OP_AR_MOD; }

  {SYMBOL}            { return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }

}

<WAITEOL>
{
  {EOL}               { yybegin(YYINITIAL); return EOL; }
}

[^] { return BAD_CHARACTER; }
