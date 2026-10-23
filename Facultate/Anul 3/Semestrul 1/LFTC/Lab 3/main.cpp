#include "TabelaSimboluri.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <regex>
#include <algorithm>
#include <cctype>

using namespace std;

struct StructuraFIP {
    int code;
    int position;
};

struct Pereche {
    int code;
    std::string value;
};

const vector<string> atoms = {
    "struct", "int", "double", "return", "if", "else", "while",
    "std::cout", "std::cin", "std::endl", "#include", "<iostream>",
    "==", "!=", "<=", ">=", "<<", ">>", "=", "+", "-", "*", "/", "<", ">",
    ";", ".", "(", ")", "{", "}"
};


int main(int argc, char* argv[]) {
    const string fileName = argc > 1 ? argv[1] : "../Lab 1/cod cu eroare/eroare_2.cpp";
    ifstream source(fileName);
    if (!source) {
        cerr << "fisier inaccesibil: " << fileName << '\n';
        return 1;
    }

    vector<string> sortedAtoms = atoms;
    sort(sortedAtoms.begin(), sortedAtoms.end(), [](const string& a, const string& b) {
        return a.size() > b.size();
    });

    const regex identifier(R"([a-zA-Z]\w*)");
    const regex number(R"(\d+(?:\.\d+)?)");
    const regex stringConstant(R"("[^"]*")");
    vector<Pereche> tokens;
    TabelaSimboluri ts;
    string line;
    int lineNumber = 0;

    while (getline(source, line)) {
        ++lineNumber;
        int pos = 0;
        while (pos < line.size()) {
            if (isspace(static_cast<unsigned char>(line[pos]))) {
                ++pos;
                continue;
            }

            string value;
            int code = -1;
            for (const string& atom : sortedAtoms) {
                if (line.compare(pos, atom.size(), atom) != 0)
                    continue;

                value = atom;
                code = static_cast<int>(find(atoms.begin(), atoms.end(), atom) - atoms.begin()) + 2;
                break;
            }

            if (code == -1) {
                auto start = line.cbegin() + pos;
                if (smatch match; regex_search(start, line.cend(), match, stringConstant, regex_constants::match_continuous)) {
                    value = match.str();
                    code = 1;
                } else if (regex_search(start, line.cend(), match, number, regex_constants::match_continuous)) {
                    value = match.str();
                    code = 1;
                    int end = pos + value.size();
                    while (end < line.size() && isalnum(static_cast<unsigned char>(line[end])))
                        ++end;
                    if (end != pos + value.size()) {
                        value = line.substr(pos, end - pos);
                        code = -1;
                    }
                } else if (regex_search(start, line.cend(), match, identifier, regex_constants::match_continuous)) {
                    value = match.str();
                    code = 0;
                }
            }

            if (code == -1) {
                if (value.empty()) {
                    value = line[pos] == '"' ? line.substr(pos) : line.substr(pos, 1);
                }
                cerr << "eroare lexicala, linia " << lineNumber
                     << ", coloana " << pos + 1 << ": " << value << '\n';
            } else {
                tokens.push_back({.code = code, .value = value});
                if (code <= 1)
                    ts.insert(value);
            }
            pos += value.size();
        }
    }

    vector<StructuraFIP> fip;
    fip.reserve(tokens.size());
    for (const auto&[code, value] : tokens)
        fip.push_back({code, code <= 1 ? ts.position(value) : -1});

    ofstream tsFile("TS.txt");
    ofstream fipFile("FIP.txt");
    ts.save(tsFile);
    for (const auto&[code, position] : fip)
        fipFile << code << ' ' << position << '\n';
    cout << "fip si ts salvate\n";
    return 0;
}
