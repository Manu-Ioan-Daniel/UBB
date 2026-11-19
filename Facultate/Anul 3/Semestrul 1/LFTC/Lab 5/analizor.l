%option noyywrap nounput noinput yylineno never-interactive

%{
#include <iostream>
#include <string>

void adaugaAtom(const std::string& valoare, int cod);
void adaugaAtomFix(const std::string& valoare);
bool eroareLexicala = false;

void raporteazaEroare(const char* valoare, int linie) {
    std::cerr << "eroare lexicala, linia " << linie << ": " << valoare << '\n';
    eroareLexicala = true;
}
%}

ID      [a-zA-Z][a-zA-Z0-9_]*
NUMAR   [0-9]+(\.[0-9]+)?
SIR     \"[^\"\r\n]*\"

%%

[ \t\r\n]+                      { }
"struct"|"int"|"double"|"main"|"return"|"if"|"else"|"while" { adaugaAtomFix(yytext); }
"std::cout"|"std::cin"|"std::endl"|"#include"|"<iostream>" { adaugaAtomFix(yytext); }
"=="|"!="|"<="|">="|"<<"|">>"    { adaugaAtomFix(yytext); }
[-=+*/<>;.(){}]                  { adaugaAtomFix(yytext); }
{NUMAR}[a-zA-Z_][a-zA-Z0-9_]*   { raporteazaEroare(yytext, yylineno); }
{NUMAR}|{SIR}                    { adaugaAtom(yytext, 1); }
{ID}                            { adaugaAtom(yytext, 0); }
\"[^\"\r\n]*                     { raporteazaEroare(yytext, yylineno); }
.                               { raporteazaEroare(yytext, yylineno); }

%%
