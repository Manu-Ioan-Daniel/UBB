#include "TabelaSimboluri.h"
#include <algorithm>
#include <cstdio>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

extern FILE* yyin;
extern bool eroareLexicala;
int yylex();

struct Atom {
    int cod;
    string valoare;
};

// Aceleasi coduri ca in Lab 3, cu "main" adaugat la final.
const vector<string> atomiFicsi = {
    "struct", "int", "double", "return", "if", "else", "while",
    "std::cout", "std::cin", "std::endl", "#include", "<iostream>",
    "==", "!=", "<=", ">=", "<<", ">>", "=", "+", "-", "*", "/", "<", ">",
    ";", ".", "(", ")", "{", "}", "main"
};

vector<Atom> atomi;
TabelaSimboluri ts;

void adaugaAtom(const string& valoare, int cod) {
    atomi.push_back({cod, valoare});
    if (cod <= 1)
        ts.insert(valoare);
}

void adaugaAtomFix(const string& valoare) {
    int cod = static_cast<int>(find(atomiFicsi.begin(), atomiFicsi.end(), valoare)
                               - atomiFicsi.begin()) + 2;
    adaugaAtom(valoare, cod);
}

int main(int argc, char* argv[]) {
    const char* fisier = argc > 1 ? argv[1] : "../Lab 1/coduri sursa/sursa_1.cpp";
    yyin = fopen(fisier, "r");
    if (!yyin) {
        cerr << "fisier inaccesibil: " << fisier << '\n';
        return 1;
    }

    yylex();
    fclose(yyin);

    ofstream fisierTS("TS.txt");
    ofstream fisierFIP("FIP.txt");
    ts.save(fisierTS);
    for (const Atom& atom : atomi) {
        int pozitie = atom.cod <= 1 ? ts.position(atom.valoare) : -1;
        fisierFIP << atom.cod << ' ' << pozitie << '\n';
    }
    fisierTS.close();
    fisierFIP.close();
    if (!fisierTS || !fisierFIP) {
        cerr << "eroare la salvarea FIP sau TS\n";
        return 1;
    }

    cout << "fip si ts salvate\n";
    return eroareLexicala ? 1 : 0;
}
