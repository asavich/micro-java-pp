package org.microjava.syntax;

import java_cup.runtime.*;

%%

%{
	private Symbol newSymbol(int type)
	{
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol newSymbol(int type, Object val)
	{
		return new Symbol(type, yyline+1, yycolumn, val);
	}
%}

%cup

%xstate COMMENT

%eofval{
	return newSymbol(sym.EOF);
%eofval}

%line
%column
%class Lexer
%public

%%

" " {}
"\b" {}
"\t" {}
"\r\n" {}
"\f" {}
"++" { return newSymbol(sym.PLUSPLUS); }
"/" { return newSymbol(sym.DIVIDE); }
"!=" { return newSymbol(sym.NOTEQUAL); }
"(" { return newSymbol(sym.LPAREN); } 
">" { return newSymbol(sym.GREATER); }
";" { return newSymbol(sym.SEMI); }
"<" { return newSymbol(sym.LESS); }
"-" { return newSymbol(sym.MINUS); }
")" { return newSymbol(sym.RPAREN); }
"&&" { return newSymbol(sym.AND); }
"<=" { return newSymbol(sym.LESSEQUAL); }
"||" { return newSymbol(sym.OR); }
"," { return newSymbol(sym.COMMA); }
"class" { return newSymbol(sym.CLASS); }
"+" { return newSymbol(sym.PLUS); }
"=" { return newSymbol(sym.ASSIGN); }
"if" { return newSymbol(sym.IF); }
"." { return newSymbol(sym.DOT); }
"--" { return newSymbol(sym.MINUSMINUS); }
"return" { return newSymbol(sym.RETURN); }
"==" { return newSymbol(sym.EQUAL); }
"new" { return newSymbol(sym.NEW); }
"%" { return newSymbol(sym.MOD); }
"break" { return newSymbol(sym.BREAK); }
"void" { return newSymbol(sym.VOID); }
">=" { return newSymbol(sym.GREATEREQUAL); }
"*" { return newSymbol(sym.TIMES); }
"{" { return newSymbol(sym.LBRACE); }
"else" { return newSymbol(sym.ELSE); }
"[" { return newSymbol(sym.LSQUARE); }
"read" { return newSymbol(sym.READ); }
"]" { return newSymbol(sym.RSQUARE); }
"final" { return newSymbol(sym.FINAL); }
"while" { return newSymbol(sym.WHILE); }
"}" { return newSymbol(sym.RBRACE); }
"print" { return newSymbol(sym.PRINT); }
"//" {yybegin(COMMENT); }
<COMMENT>. {yybegin(COMMENT); }
<COMMENT>"\r\n" {yybegin(YYINITIAL); }
[0-9]+ {return newSymbol(sym.NUMBER, new Integer(yytext())); }
[:jletter:][:jletterdigit:]* {return newSymbol (sym.IDENT, yytext());}
"'"[\040-176]"'" {return newSymbol(sym.CHARCONST, new Character(yytext().charAt(1))); }
. {System.err.println("Leksicka greska na liniji " + (yyline + 1) + " : " + yytext() ); }