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
WHITE_SPACE=\p{Blank}+
AREG=((a[0-7])|sp)
DREG=(d[0-7])
ASSIGNMENT=(([:letter:]|_)(([:letter:]|[:digit:])|_)*((\p{Blank}+equ\p{Blank}+)|(\p{Blank}+set\p{Blank}+)|\p{Blank}*=\p{Blank}*))
LOCAL_LABEL=(\.([:letter:]|_)(([:letter:]|[:digit:])|_)*:?)|(([:letter:]|_)(([:letter:]|[:digit:])|_)*\$:?)
LOCAL_LABEL_WC=(\.([:letter:]|_)(([:letter:]|[:digit:])|_)*:)|(([:letter:]|_)(([:letter:]|[:digit:])|_)*\$:)
GLOBAL_LABEL=(([:letter:]|_)(([:letter:]|[:digit:])|_)*:?:?)
GLOBAL_LABEL_WC=(([:letter:]|_)(([:letter:]|[:digit:])|_)*::?)
//MNEMONIC=(([:letter:])+)
SYMBOL=(([:letter:]|_|\.)(([:letter:]|[:digit:]|[_\$]))*)
MACRONAME=(([:letter:]|_)(([:letter:]|[:digit:]|_))*)
DIRECTIVE_KEYWORD=(([:letter:])(([:letter:]))*)(\..)?
OPSIZE_BS=(\.[bs])
OPSIZE_W=(\.w)
OPSIZE_L=(\.l)
BINARY=(%[01]+)
HEXADECIMAL=(\$[0-9a-f]+)
OCTAL=(@[0-7]+)
DECIMAL=([0-9]+)
STRINGLIT=(`([^`\\]|\\.)*`|'([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
PLAINPARAM=(`([^`\\]|\\.)*`|'([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")|<([^>\\]|\\.)*>|([^,;\p{Blank}\r\n])+ // why does \R not work, I have no idea
COMMENT=([;].*+)
HASH_COMMENT=([#;*].*+)

%state NOSOL,INSTRPART,ASMINSTR,ASMOPS,ASSIGNMENT,EXPR,EXPR_OP,MACROCALL,WAITEOL

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
  {DIRECTIVE_KEYWORD} {
                        if(isAsmMnemonicWithSize(yytext())) { yybegin(ASMINSTR); yypushback(2); return MNEMONIC; }
                        if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; }
                        if(isDataDirective(yytext())) { yybegin(EXPR); return DATA_DIRECTIVE; }
                        if(isOtherDirective(yytext())) { yybegin(EXPR); return OTHER_DIRECTIVE; }
                        yybegin(MACROCALL); return MACRO_INVOKATION;
                      }
//  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { yybegin(INSTRPART); return SYMBOL; } }
  {MACRONAME}         { yybegin(MACROCALL); return MACRO_INVOKATION; }
  {HASH_COMMENT}      { yybegin(YYINITIAL); return COMMENT; }
}

<INSTRPART> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {DIRECTIVE_KEYWORD} {
                        if(isAsmMnemonicWithSize(yytext())) { yybegin(ASMINSTR); yypushback(2); return MNEMONIC; }
                        if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; }
                        if(isDataDirective(yytext())) { yybegin(EXPR); return DATA_DIRECTIVE; }
                        if(isOtherDirective(yytext())) { yybegin(EXPR); return OTHER_DIRECTIVE; }
                        yybegin(MACROCALL); return MACRO_INVOKATION;
                      }
//  {MNEMONIC}          { if(isAsmMnemonic(yytext())) { yybegin(ASMINSTR); return MNEMONIC; } else { return SYMBOL; } }
  {MACRONAME}         { yybegin(MACROCALL); return MACRO_INVOKATION; }

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
  {WHITE_SPACE}       { return WHITE_SPACE; } // FIXME space optionally introduces comment
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }

  ","                 { return SEPARATOR; }

  {PLAINPARAM}        { return STRINGLIT; }
}

<ASSIGNMENT> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  {EOL}               { yybegin(YYINITIAL); return EOL; }

  "equ"|"set"         { yybegin(EXPR); return EQU; }

  "="                 { yybegin(EXPR); return OP_ASSIGN; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<EXPR> {
  {WHITE_SPACE}       { return WHITE_SPACE; } // FIXME space optionally introduces comment
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

  {SYMBOL}            { yybegin(EXPR_OP); return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }
}

<EXPR_OP> {
  {WHITE_SPACE}       { return WHITE_SPACE; } // FIXME space optionally introduces comment
  {EOL}               { yybegin(YYINITIAL); return EOL; }

//  {BINARY}            { return BINARY; }
//  {HEXADECIMAL}       { return HEXADECIMAL; }
//  {OCTAL}             { return OCTAL; }
//  {DECIMAL}           { return DECIMAL; }
//  {STRINGLIT}         { return STRINGLIT; }

  "<<"                { yybegin(EXPR); return OP_AR_SHIFT_L; }
  ">>"                { yybegin(EXPR); return OP_AR_SHIFT_R; }
  "&&"                { yybegin(EXPR); return OP_LOGICAL_AND; }
  "||"                { yybegin(EXPR); return OP_LOGICAL_OR; }
  "=="                { yybegin(EXPR); return OP_CMP_EQ; }
  "<>"                { yybegin(EXPR); return OP_CMP_NOT_EQ; }
  ">="                { yybegin(EXPR); return OP_CMP_GT_EQ; }
  "<="                { yybegin(EXPR); return OP_CMP_LT_EQ; }
  "!="                { yybegin(EXPR); return OP_CMP_NOT_EQ; }
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
  "="                 { yybegin(EXPR); return OP_ASSIGN; }
  "~"                 { yybegin(EXPR); return OP_UNARY_COMPL; }
  "+"                 { yybegin(EXPR); return OP_PLUS; }
  "-"                 { yybegin(EXPR); return OP_MINUS; }
  "*"                 { yybegin(EXPR); return OP_AR_MUL; }
  "%"|"//"            { yybegin(EXPR); return OP_AR_MOD; }
  "/"                 { yybegin(EXPR); return OP_AR_DIV; }

//  {SYMBOL}            { return SYMBOL; }

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
  {OPSIZE_W}          { return OPSIZE_W; }
  {OPSIZE_L}          { return OPSIZE_L; }

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
//  ":"                 { return COLON; }
//  ";"                 { return SEMICOLON; }
//  "["                 { return SQUARE_L; }
//  "]"                 { return SQUARE_R; }
  "("                 { return ROUND_L; }
  ")"                 { return ROUND_R; }
  ","                 { return SEPARATOR; }
//  "."                 { return DOT; }
  "#"                 { return HASH; }
//  "$"                 { return DOLLAR; }
  "="                 { return OP_ASSIGN; }
  "!"                 { return OP_UNARY_NOT; }
  "~"                 { return OP_UNARY_COMPL; }
  "+"                 { return OP_PLUS; }
  "-"                 { return OP_MINUS; }
  "*"                 { return OP_AR_MUL; }
  "%"|"//"            { return OP_AR_MOD; }
  "/"                 { return OP_AR_DIV; }

  {SYMBOL}            { return SYMBOL; }

  {COMMENT}           { yybegin(WAITEOL); return COMMENT; }

}

<WAITEOL>
{
  {EOL}               { yybegin(YYINITIAL); return EOL; }
}

[^] { return BAD_CHARACTER; }
