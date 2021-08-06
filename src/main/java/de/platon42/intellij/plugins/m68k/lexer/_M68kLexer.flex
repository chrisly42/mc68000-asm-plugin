package de.platon42.intellij.plugins.m68k.lexer;

import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static de.platon42.intellij.plugins.m68k.psi.M68kTypes.*;
import static de.platon42.intellij.plugins.m68k.lexer.LexerUtil.*;

%%

%{
  private M68kLexerPrefs lexerPrefs;
  boolean eatOneWhitespace = false;
  int macroLines = 0;
  int exprState = 0;
  int exprOpState = 0;

  public M68kLexerPrefs getLexerPrefs()
  {
      return lexerPrefs;
  }

  public _M68kLexer(M68kLexerPrefs lexerPrefs) {
      this((java.io.Reader)null);
      this.lexerPrefs = lexerPrefs;
  }

  private void startExpr(int newExprState, int newExprOpState) {
      exprState = newExprState;
      exprOpState = newExprOpState;
      yybegin(exprState);
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
WHITE_SPACE=\p{Blank}+
AREG=(a[0-6])
DREG=(d[0-7])
ASSIGNMENT=(([:letter:]|_)(([:letter:]|[:digit:])|_)*:?((\p{Blank}+equ\p{Blank}+)|(\p{Blank}+set\p{Blank}+)|\p{Blank}*=\p{Blank}*))
LOCAL_LABEL=(\.([:letter:]|_)([:letter:]|[:digit:]|_)*:?)|(([:letter:]|_)([:letter:]|[:digit:]|_)*\$:?)
LOCAL_LABEL_WC=(\.([:letter:]|_)([:letter:]|[:digit:]|_)*:)|(([:letter:]|_)([:letter:]|[:digit:]|_)*\$:)
GLOBAL_LABEL=(([:letter:]|_)([:letter:]|[:digit:]|_)*:?:?)
GLOBAL_LABEL_WC=(([:letter:]|_)([:letter:]|[:digit:]|_)*::?)
MACRO_DEF_LEFT=(([:letter:]|_)(([:letter:]|[:digit:]|_))*)(\p{Blank}+|:\p{Blank}*)macro\p{Blank}*
MACRO_END_TAG=\p{Blank}+endm\p{Blank}*[^;\r\n]*
//MNEMONIC=(([:letter:])+)
SYMBOL=(([:letter:]|_|\.)(([:letter:]|[:digit:]|[_\$]))*)
BONUSSYMBOL=(([:letter:]|_|\.)(([:letter:]|[:digit:]|[_\$\.\\]))*)
MACRONAME=(([:letter:]|_)(([:letter:]|[:digit:]|_))*)
DIRECTIVE_KEYWORD=(([:letter:])(([:letter:]))*)(\..)?
OPSIZE_BS=(\.[bs])
OPSIZE_W=(\.w)
OPSIZE_L=(\.l)
BINARY=(%[01]+)
HEXADECIMAL=(\$[0-9a-fA-F]+)
OCTAL=(@[0-7]+)
DECIMAL=([0-9]+)
STRINGLIT=(`([^`\\\r\n]|\\.)*`|'([^'\\\r\n]|\\.)*'|\"([^\"\\\r\n]|\\.)*\")
PLAINPARAM=(`([^`\\\r\n]|\\.)*`|'([^'\\\r\n]|\\.)*'|\"([^\"\\\r\n]|\\.)*\")|<([^>\\\r\n]|\\.)*>|([^,;\p{Blank}\r\n])+ // why does \R not work, I have no idea
COMMENT=([;].*+)
HASH_COMMENT=([#;*].*+)
SKIP_TO_EOL=[^\r\n]+
PLAIN_MACRO_LINE=[^;\r\n]+

%state NOSOL,INSTRPART,ASMINSTR,ASMOPS,ASMOPS_OP,ASSIGNMENT,EXPR,EXPR_OP,PLAINPARAMS,WAITEOL,MACRODEF,MACROLINE,MACROTERMINATION,MACROWAITEOL

%%
<YYINITIAL, NOSOL>
{
  {EOL}               { yybegin(YYINITIAL); return WHITE_SPACE; }
  {HASH_COMMENT}      { yybegin(YYINITIAL); return COMMENT; }
}

<INSTRPART,ASMINSTR,PLAINPARAMS,ASSIGNMENT,EXPR,EXPR_OP,ASMOPS,ASMOPS_OP>
{
  {EOL}               { yybegin(YYINITIAL); return EOL; }
  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<NOSOL, INSTRPART, ASSIGNMENT>
{
  {WHITE_SPACE}       { return WHITE_SPACE; }
}

<PLAINPARAMS, EXPR, EXPR_OP, ASMOPS, ASMOPS_OP> {
  {WHITE_SPACE}       { return handleEolCommentWhitespace(this); }
}

<YYINITIAL> {
  {WHITE_SPACE}       { yybegin(NOSOL); eatOneWhitespace = false; return WHITE_SPACE; }
  {ASSIGNMENT}        { yybegin(ASSIGNMENT); eatOneWhitespace = true; yypushback(pushbackAssignment(yytext())); return SYMBOLDEF; }
  {MACRO_DEF_LEFT}    { yybegin(MACRODEF); yypushback(pushbackAfterFirstToken(yytext())); return MACRO_NAME; }
  {LOCAL_LABEL}       { yybegin(INSTRPART); eatOneWhitespace = false; yypushback(pushbackLabelColons(yytext())); return LOCAL_LABEL_DEF; }
  {GLOBAL_LABEL}      { yybegin(INSTRPART); eatOneWhitespace = false; yypushback(pushbackLabelColons(yytext())); return GLOBAL_LABEL_DEF; }
}

<NOSOL> {
  "macro"             { yybegin(MACRODEF); return MACRO_TAG; }
  {LOCAL_LABEL_WC}    { yybegin(INSTRPART); yypushback(pushbackLabelColons(yytext())); return LOCAL_LABEL_DEF; }
  {GLOBAL_LABEL_WC}   { yybegin(INSTRPART); yypushback(pushbackLabelColons(yytext())); return GLOBAL_LABEL_DEF; }
//  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { yybegin(INSTRPART); return SYMBOL; } }
}

<NOSOL, INSTRPART>
{
  {DIRECTIVE_KEYWORD} {
                        if(isAsmMnemonicWithSize(yytext())) { yybegin(ASMINSTR); yypushback(2); return MNEMONIC; }
                        if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; }
                        if(isDataDirective(yytext())) { startExpr(EXPR, EXPR_OP); return DATA_DIRECTIVE; }
                        if(isPlainDirective(yytext())) { yybegin(PLAINPARAMS); return OTHER_DIRECTIVE; }
                        if(isOtherDirective(yytext())) { startExpr(EXPR, EXPR_OP); return OTHER_DIRECTIVE; }
                        return handleMacroMode(this);
                      }
  {MACRONAME}         { return handleMacroMode(this); }
}

<INSTRPART,MACRODEF> {
  ":"                 { return COLON; }
//  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { return SYMBOL; } }
}

<ASMINSTR> {
  {WHITE_SPACE}       { startExpr(ASMOPS, ASMOPS_OP); return WHITE_SPACE; }

  {OPSIZE_BS}         { return OPSIZE_BS; }
  {OPSIZE_W}          { return OPSIZE_W; }
  {OPSIZE_L}          { return OPSIZE_L; }
}

<PLAINPARAMS> {
  ","                 { return SEPARATOR; }

  {PLAINPARAM}        { return STRINGLIT; }
}

<ASSIGNMENT> {
  "equ"|"set"         { startExpr(EXPR, EXPR_OP); return EQU; }

  ":"                 { return COLON; }
  "="                 { startExpr(EXPR, EXPR_OP); return OP_ASSIGN; }
}

<EXPR, ASMOPS> {
  {BINARY}            { yybegin(exprOpState); return BINARY; }
  {HEXADECIMAL}       { yybegin(exprOpState); return HEXADECIMAL; }
  {OCTAL}             { yybegin(exprOpState); return OCTAL; }
  {DECIMAL}           { yybegin(exprOpState); return DECIMAL; }
  {STRINGLIT}         { yybegin(exprOpState); return STRINGLIT; }

  "^"                 { return OP_BITWISE_XOR; }
  ","                 { return SEPARATOR; }
  "("                 { return ROUND_L; }
  ")"                 { yybegin(exprOpState); return ROUND_R; }
  "!"                 { return OP_UNARY_NOT; }
  "~"                 { return OP_UNARY_COMPL; }
  "+"                 { return OP_PLUS; }
  "-"                 { return OP_MINUS; }
  "*"                 { yybegin(exprOpState); return CURRENT_PC_SYMBOL; }

}

<EXPR> {
  {BONUSSYMBOL}       { yybegin(exprOpState); return SYMBOL; }
}

<EXPR_OP, ASMOPS_OP> {
  "<<"                { yybegin(exprState); return OP_AR_SHIFT_L; }
  ">>"                { yybegin(exprState); return OP_AR_SHIFT_R; }
  "&&"                { yybegin(exprState); return OP_LOGICAL_AND; }
  "||"                { yybegin(exprState); return OP_LOGICAL_OR; }
  "=="|"="            { yybegin(exprState); return OP_CMP_EQ; }
  "<>"|"!="           { yybegin(exprState); return OP_CMP_NOT_EQ; }
  ">="                { yybegin(exprState); return OP_CMP_GT_EQ; }
  "<="                { yybegin(exprState); return OP_CMP_LT_EQ; }
  "<"                 { yybegin(exprState); return OP_CMP_LT; }
  ">"                 { yybegin(exprState); return OP_CMP_GT; }
  "&"                 { yybegin(exprState); return OP_BITWISE_AND; }
  "|"|"!"             { yybegin(exprState); return OP_BITWISE_OR; }
  "^"                 { yybegin(exprState); return OP_BITWISE_XOR; }

//  ":"                 { return COLON; }
//  ";"                 { return SEMICOLON; }
//  "["                 { return SQUARE_L; }
//  "]"                 { return SQUARE_R; }
  ","                 { yybegin(exprState); return SEPARATOR; }
  "("                 { yybegin(exprState); return ROUND_L; }
  ")"                 { return ROUND_R; }
//  "."                 { return DOT; }
//  "$"                 { return DOLLAR; }
  "~"                 { yybegin(exprState); return OP_UNARY_COMPL; }
  "+"                 { yybegin(exprState); return OP_PLUS; }
  "-"                 { yybegin(exprState); return OP_MINUS; }
  "*"                 { yybegin(exprState); return OP_AR_MUL; }
  "%"|"//"            { yybegin(exprState); return OP_AR_MOD; }
  "/"                 { yybegin(exprState); return OP_AR_DIV; }
}

<ASMOPS> {
  {AREG}              { yybegin(exprOpState); return AREG; }
  {DREG}              { yybegin(exprOpState); return DREG; }
  "sp"|"a7"           { yybegin(exprOpState); return REG_SP; }
  "pc"                { yybegin(exprOpState); return PC; }
  "ccr"               { yybegin(exprOpState); return REG_CCR; }
  "sr"                { yybegin(exprOpState); return REG_SR; }
  "usp"               { yybegin(exprOpState); return REG_USP; }
  "vbr"               { yybegin(exprOpState); return REG_VBR; }

  "#"                 { return HASH; }

  {SYMBOL}            { yybegin(exprOpState); return SYMBOL; }
}

<ASMOPS_OP> {
  {OPSIZE_BS}         { return OPSIZE_BS; }
  {OPSIZE_W}          { return OPSIZE_W; }
  {OPSIZE_L}          { return OPSIZE_L; }
}

<WAITEOL>
{
  {EOL}               { yybegin(YYINITIAL); return EOL; }
  {SKIP_TO_EOL}       { return COMMENT; }
}

// The macro mess starts here

<MACRODEF> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(MACROLINE); macroLines = 0; return WHITE_SPACE; }
  "macro"             { return MACRO_TAG; }
  {MACRONAME}         { return MACRO_NAME; }

  {COMMENT}           { yybegin(MACROWAITEOL); return COMMENT; }
}

<MACROLINE> {
  {EOL}               { return handleMacroLineEol(this); }
  {MACRO_END_TAG}     { yybegin(MACROTERMINATION); return MACRO_END_TAG; }

  {PLAIN_MACRO_LINE}  { return MACRO_LINE; }
  {COMMENT}           { yybegin(MACROWAITEOL); return COMMENT; }
}

<MACROTERMINATION> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }
  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<MACROWAITEOL>
{
  {EOL}               { return handleMacroLineEol(this); }
  {SKIP_TO_EOL}       { return COMMENT; }
}

[^] { return BAD_CHARACTER; }
