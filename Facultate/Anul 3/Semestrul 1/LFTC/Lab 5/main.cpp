#include "AutomatFinit.h"
#include "TabelaSimboluri.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <algorithm>
#include <cctype>

using namespace std;

struct StructuraFIP {
    int code;
    int position;
};

struct Pereche {
    int code;
    string value;
};

const vector<string> atoms = {
    "struct", "int", "double", "return", "if", "else", "while",
    "std::cout", "std::cin", "std::endl", "#include", "<iostream>",
    "==", "!=", "<=", ">=", "<<", ">>", "=", "+", "-", "*", "/", "<", ">",
    ";", ".", "(", ")", "{", "}"
};

int main(int argc, char* argv[]) {
    AutomatFinit afId;
    AutomatFinit afInt;
    AutomatFinit afReal;

    if (!afId.citesteDinFisier("af_identificatori.txt")) {
        cerr << "eroare deschidere af_identificatori.txt\n";
        return 1;
    }
    if (!afInt.citesteDinFisier("af_intregi.txt")) {
        cerr << "eroare deschidere af_intregi.txt\n";
        return 1;
    }
    if (!afReal.citesteDinFisier("af_reale.txt")) {
        cerr << "eroare deschidere af_reale.txt\n";
        return 1;
    }

    string fileName = argc > 1 ? argv[1] : "../../Lab 1/coduri sursa/sursa_1.cpp";
    ifstream source(fileName);
    if (!source) {
        std::cout<<"Eroare deschidere fisier";
        return 1;
    }

    vector<string> sortedAtoms = atoms;
    ranges::sort(sortedAtoms, [](const string& a, const string& b) {
        return a.size() > b.size();
    });

    vector<Pereche> tokens;
    TabelaSimboluri ts;
    string line;
    int lineNumber = 0;

    while (getline(source, line)) {
        ++lineNumber;
        int pos = 0;
        while (pos < static_cast<int>(line.size())) {
            if (isspace(static_cast<unsigned char>(line[pos]))) {
                ++pos;
                continue;
            }

            string value;
            int code = -1;

            for (const string& atom : sortedAtoms) {
                if (line.compare(pos, atom.size(), atom) != 0)
                    continue;

                bool isAlphaNum = isalnum(static_cast<unsigned char>(atom.back()));
                if (isAlphaNum && pos + static_cast<int>(atom.size()) < static_cast<int>(line.size())) {
                    char nextC = line[pos + atom.size()];
                    if (isalnum(static_cast<unsigned char>(nextC)) || nextC == '_') {
                        continue;
                    }
                }

                value = atom;
                code = static_cast<int>(ranges::find(atoms, atom) - atoms.begin()) + 2;
                break;
            }

            if (code == -1) {
                if (line[pos] == '"') {
                    int endQuote = line.find('"', pos + 1);
                    if (endQuote != string::npos) {
                        value = line.substr(pos, endQuote - pos + 1);
                        code = 1;
                    } else {
                        value = line.substr(pos);
                        code = -1;
                    }
                } else {
                    string rest = line.substr(pos);
                    auto [okReal, prefReal] = afReal.celMaiLungPrefix(rest);
                    auto [okInt, prefInt] = afInt.celMaiLungPrefix(rest);
                    auto [okId, prefId] = afId.celMaiLungPrefix(rest);

                    if (okReal) {
                        int end = pos + static_cast<int>(prefReal.size());
                        while (end < static_cast<int>(line.size()) && (isalnum(static_cast<unsigned char>(line[end])) || line[end] == '_')) {
                            ++end;
                        }
                        if (end != pos + static_cast<int>(prefReal.size())) {
                            value = line.substr(pos, end - pos);
                            code = -1;
                        } else {
                            value = prefReal;
                            code = 1;
                        }
                    } else if (okInt) {
                        int end = pos + static_cast<int>(prefInt.size());
                        while (end < static_cast<int>(line.size()) && (isalnum(static_cast<unsigned char>(line[end])) || line[end] == '_')) {
                            ++end;
                        }
                        if (end != pos + static_cast<int>(prefInt.size())) {
                            value = line.substr(pos, end - pos);
                            code = -1;
                        } else {
                            value = prefInt;
                            code = 1;
                        }
                    } else if (okId) {
                        value = prefId;
                        code = 0;
                    }
                }
            }

            if (code == -1) {
                if (value.empty()) {
                    value = line[pos] == '"' ? line.substr(pos) : line.substr(pos, 1);
                }
                cerr << "eroare lexicala, linia " << lineNumber
                     << ", coloana " << pos + 1 << ": " << value << '\n';
            } else {
                tokens.push_back({code, value});
                if (code <= 1)
                    ts.insert(value);
            }
            pos += static_cast<int>(value.size());
        }
    }

    vector<StructuraFIP> fip;
    fip.reserve(tokens.size());
    for (const auto& [code, value] : tokens)
        fip.push_back({code, code <= 1 ? ts.position(value) : -1});

    ofstream tsFile("TS.txt");
    ofstream fipFile("FIP.txt");
    ts.save(tsFile);
    for (const auto& [code, position] : fip)
        fipFile << code << ' ' << position << '\n';

    if (ifstream("../af_identificatori.txt").is_open()) {
        ofstream tsParent("../TS.txt");
        ofstream fipParent("../FIP.txt");
        ts.save(tsParent);
        for (const auto& [code, position] : fip)
            fipParent << code << ' ' << position << '\n';
    }
    cout << "fip si ts salvate\n";
    return 0;
}
