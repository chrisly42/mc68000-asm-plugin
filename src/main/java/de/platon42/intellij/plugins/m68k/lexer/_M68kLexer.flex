package de.platon42.intellij.plugins.m68k.lexer;

import com.intellij.lexer.FlexLexer;
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

  public M68kLexerPrefs getLexerPrefs()
  {
      return lexerPrefs;
  }

  public _M68kLexer(M68kLexerPrefs lexerPrefs) {
      this((java.io.Reader)null);
      this.lexerPrefs = lexerPrefs;
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
MACRO_DEF_LEFT=(([:letter:]|_)(([:letter:]|[:digit:]|_))*)\p{Blank}+macro\p{Blank}*
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
HEXADECIMAL=(\$[0-9a-f]+)
OCTAL=(@[0-7]+)
DECIMAL=([0-9]+)
STRINGLIT=(`([^`\\\r\n]|\\.)*`|'([^'\\\r\n]|\\.)*'|\"([^\"\\\r\n]|\\.)*\")
PLAINPARAM=(`([^`\\\r\n]|\\.)*`|'([^'\\\r\n]|\\.)*'|\"([^\"\\\r\n]|\\.)*\")|<([^>\\\r\n]|\\.)*>|([^,;\p{Blank}\r\n])+ // why does \R not work, I have no idea
COMMENT=([;].*+)
HASH_COMMENT=([#;*].*+)
SKIP_TO_EOL=[^\r\n]+
PLAIN_MACRO_LINE=[^;\r\n]+

%state NOSOL,INSTRPART,ASMINSTR,ASMOPS,ASMOPS_OP,ASSIGNMENT,EXPR,EXPR_OP,MACROCALL,WAITEOL,MACRODEF,MACROLINE,MACROTERMINATION,MACROWAITEOL

%%
<YYINITIAL> {
  {WHITE_SPACE}       { yybegin(NOSOL); eatOneWhitespace = false; return WHITE_SPACE; }
  {EOL}               { return WHITE_SPACE; }
  {ASSIGNMENT}        { yybegin(ASSIGNMENT); eatOneWhitespace = true; yypushback(pushbackAssignment(yytext())); return SYMBOLDEF; }
  {MACRO_DEF_LEFT}    { yybegin(MACRODEF); yypushback(pushbackAfterFirstToken(yytext())); return MACRO_NAME; }
  {LOCAL_LABEL}       { yybegin(INSTRPART); eatOneWhitespace = false; yypushback(pushbackLabelColons(yytext())); return LOCAL_LABEL_DEF; }
  {GLOBAL_LABEL}      { yybegin(INSTRPART); eatOneWhitespace = false; yypushback(pushbackLabelColons(yytext())); return GLOBAL_LABEL_DEF; }

  {HASH_COMMENT}      { return COMMENT; }
}

<NOSOL> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return WHITE_SPACE; }
  {LOCAL_LABEL_WC}    { yybegin(INSTRPART); yypushback(pushbackLabelColons(yytext())); return LOCAL_LABEL_DEF; }
  {GLOBAL_LABEL_WC}   { yybegin(INSTRPART); yypushback(pushbackLabelColons(yytext())); return GLOBAL_LABEL_DEF; }
  "macro"             { yybegin(MACRODEF); return MACRO_TAG; }
  {DIRECTIVE_KEYWORD} {
                        if(isAsmMnemonicWithSize(yytext())) { yybegin(ASMINSTR); yypushback(2); return MNEMONIC; }
                        if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; }
                        if(isDataDirective(yytext())) { yybegin(EXPR); return DATA_DIRECTIVE; }
                        if(isOtherDirective(yytext())) { yybegin(EXPR); return OTHER_DIRECTIVE; }
                        return handleMacroMode(this);
                      }
//  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { yybegin(INSTRPART); return SYMBOL; } }
  {MACRONAME}         { return handleMacroMode(this); }
  {HASH_COMMENT}      { yybegin(YYINITIAL); return COMMENT; }
}

<INSTRPART> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  ":"                 { return COLON; }

  {DIRECTIVE_KEYWORD} {
                        if(isAsmMnemonicWithSize(yytext())) { yybegin(ASMINSTR); yypushback(2); return MNEMONIC; }
                        if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; }
                        if(isDataDirective(yytext())) { yybegin(EXPR); return DATA_DIRECTIVE; }
                        if(isOtherDirective(yytext())) { yybegin(EXPR); return OTHER_DIRECTIVE; }
                        return handleMacroMode(this);
                      }
//  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { return SYMBOL; } }
  {MACRONAME}         { return handleMacroMode(this); }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<ASMINSTR> {
  {WHITE_SPACE}       { yybegin(ASMOPS); return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {OPSIZE_BS}         { return OPSIZE_BS; }
  {OPSIZE_W}          { return OPSIZE_W; }
  {OPSIZE_L}          { return OPSIZE_L; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<MACROCALL> {
  {WHITE_SPACE}       { return handleEolCommentWhitespace(this); }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  ","                 { return SEPARATOR; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }

  {PLAINPARAM}        { return STRINGLIT; }
}

<ASSIGNMENT> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  "equ"|"set"         { yybegin(EXPR); return EQU; }

  ":"                 { return COLON; }
  "="                 { yybegin(EXPR); return OP_ASSIGN; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<EXPR> {
  {WHITE_SPACE}       { return handleEolCommentWhitespace(this); }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {BINARY}            { yybegin(EXPR_OP); return BINARY; }
  {HEXADECIMAL}       { yybegin(EXPR_OP); return HEXADECIMAL; }
  {OCTAL}             { yybegin(EXPR_OP); return OCTAL; }
  {DECIMAL}           { yybegin(EXPR_OP); return DECIMAL; }
  {STRINGLIT}         { yybegin(EXPR_OP); return STRINGLIT; }

  "^"                 { return OP_BITWISE_XOR; }
  ","                 { return SEPARATOR; }
  "("                 { return ROUND_L; }
  ")"                 { yybegin(EXPR_OP); return ROUND_R; }
  "!"                 { return OP_UNARY_NOT; }
  "~"                 { return OP_UNARY_COMPL; }
  "+"                 { return OP_PLUS; }
  "-"                 { return OP_MINUS; }
  "*"                 { yybegin(EXPR_OP); return CURRENT_PC_SYMBOL; }

  {BONUSSYMBOL}       { yybegin(EXPR_OP); return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<EXPR_OP> {
  {WHITE_SPACE}       { return handleEolCommentWhitespace(this); }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  "<<"                { yybegin(EXPR); return OP_AR_SHIFT_L; }
  ">>"                { yybegin(EXPR); return OP_AR_SHIFT_R; }
  "&&"                { yybegin(EXPR); return OP_LOGICAL_AND; }
  "||"                { yybegin(EXPR); return OP_LOGICAL_OR; }
  "=="|"="            { yybegin(EXPR); return OP_CMP_EQ; }
  "<>"|"!="           { yybegin(EXPR); return OP_CMP_NOT_EQ; }
  ">="                { yybegin(EXPR); return OP_CMP_GT_EQ; }
  "<="                { yybegin(EXPR); return OP_CMP_LT_EQ; }
  "<"                 { yybegin(EXPR); return OP_CMP_LT; }
  ">"                 { yybegin(EXPR); return OP_CMP_GT; }
  "&"                 { yybegin(EXPR); return OP_BITWISE_AND; }
  "|"|"!"             { yybegin(EXPR); return OP_BITWISE_OR; }
  "^"                 { yybegin(EXPR); return OP_BITWISE_XOR; }
//  ":"                 { return COLON; }
//  ";"                 { return SEMICOLON; }
//  "["                 { return SQUARE_L; }
//  "]"                 { return SQUARE_R; }
  ","                 { yybegin(EXPR); return SEPARATOR; }
  "("                 { yybegin(EXPR); return ROUND_L; }
  ")"                 { return ROUND_R; }
//  "."                 { return DOT; }
//  "$"                 { return DOLLAR; }
  "~"                 { yybegin(EXPR); return OP_UNARY_COMPL; }
  "+"                 { yybegin(EXPR); return OP_PLUS; }
  "-"                 { yybegin(EXPR); return OP_MINUS; }
  "*"                 { yybegin(EXPR); return OP_AR_MUL; }
  "%"|"//"            { yybegin(EXPR); return OP_AR_MOD; }
  "/"                 { yybegin(EXPR); return OP_AR_DIV; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<ASMOPS> {
  {WHITE_SPACE}       { return handleEolCommentWhitespace(this); }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {BINARY}            { yybegin(ASMOPS_OP); return BINARY; }
  {HEXADECIMAL}       { yybegin(ASMOPS_OP); return HEXADECIMAL; }
  {OCTAL}             { yybegin(ASMOPS_OP); return OCTAL; }
  {DECIMAL}           { yybegin(ASMOPS_OP); return DECIMAL; }
  {STRINGLIT}         { yybegin(ASMOPS_OP); return STRINGLIT; }

  {AREG}              { yybegin(ASMOPS_OP); return AREG; }
  {DREG}              { yybegin(ASMOPS_OP); return DREG; }
  "sp"|"a7"           { yybegin(ASMOPS_OP); return REG_SP; }
  "pc"                { yybegin(ASMOPS_OP); return PC; }
  "ccr"               { yybegin(ASMOPS_OP); return REG_CCR; }
  "sr"                { yybegin(ASMOPS_OP); return REG_SR; }
  "usp"               { yybegin(ASMOPS_OP); return REG_USP; }
  "vbr"               { yybegin(ASMOPS_OP); return REG_VBR; }

  "^"                 { return OP_BITWISE_XOR; }
  ","                 { return SEPARATOR; }
  "("                 { return ROUND_L; }
  ")"                 { yybegin(ASMOPS_OP); return ROUND_R; }
  "!"                 { return OP_UNARY_NOT; }
  "~"                 { return OP_UNARY_COMPL; }
  "+"                 { return OP_PLUS; }
  "-"                 { return OP_MINUS; }
  "*"                 { yybegin(ASMOPS_OP); return CURRENT_PC_SYMBOL; }

  "#"                 { return HASH; }

  {SYMBOL}            { yybegin(ASMOPS_OP); return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<ASMOPS_OP> {
  {WHITE_SPACE}       { return handleEolCommentWhitespace(this); }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {OPSIZE_BS}         { return OPSIZE_BS; }
  {OPSIZE_W}          { return OPSIZE_W; }
  {OPSIZE_L}          { return OPSIZE_L; }

  "<<"                { yybegin(ASMOPS); return OP_AR_SHIFT_L; }
  ">>"                { yybegin(ASMOPS); return OP_AR_SHIFT_R; }
  "&&"                { yybegin(ASMOPS); return OP_LOGICAL_AND; }
  "||"                { yybegin(ASMOPS); return OP_LOGICAL_OR; }
  "=="|"="            { yybegin(ASMOPS); return OP_CMP_EQ; }
  "<>"|"!="           { yybegin(ASMOPS); return OP_CMP_NOT_EQ; }
  ">="                { yybegin(ASMOPS); return OP_CMP_GT_EQ; }
  "<="                { yybegin(ASMOPS); return OP_CMP_LT_EQ; }
  "<"                 { yybegin(ASMOPS); return OP_CMP_LT; }
  ">"                 { yybegin(ASMOPS); return OP_CMP_GT; }
  "&"                 { yybegin(ASMOPS); return OP_BITWISE_AND; }
  "|"|"!"             { yybegin(ASMOPS); return OP_BITWISE_OR; }
  "^"                 { yybegin(ASMOPS); return OP_BITWISE_XOR; }
//  ":"                 { return COLON; }
//  ";"                 { return SEMICOLON; }
//  "["                 { return SQUARE_L; }
//  "]"                 { return SQUARE_R; }
  ","                 { yybegin(ASMOPS); return SEPARATOR; }
  "("                 { yybegin(ASMOPS); return ROUND_L; }
  ")"                 { return ROUND_R; }
//  "."                 { return DOT; }
//  "$"                 { return DOLLAR; }
  "~"                 { yybegin(ASMOPS); return OP_UNARY_COMPL; }
  "+"                 { yybegin(ASMOPS); return OP_PLUS; }
  "-"                 { yybegin(ASMOPS); return OP_MINUS; }
  "*"                 { yybegin(ASMOPS); return OP_AR_MUL; }
  "%"|"//"            { yybegin(ASMOPS); return OP_AR_MOD; }
  "/"                 { yybegin(ASMOPS); return OP_AR_DIV; }

  {SYMBOL}            { return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
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
