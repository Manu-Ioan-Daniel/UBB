#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <regex>
#include <algorithm>

using namespace std;

vector<string> keywords = {
    "struct", "int", "double", "main", "return", "if", "else", "while",
    "std::cout", "std::cin", "std::endl", "#include", "<iostream>"
};
vector<string> operators = {"==", "!=", "<=", ">=", "<<", ">>", "=", "+", "-", "*", "/", "<", ">"};

string escape(const string& s) {
    string res;
    for (const char c : s) {
        if (c == '+' || c == '*' || c == '.' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']' || c == '^' || c == '$' || c == '|' || c == '?' || c == '\\') {
            res += '\\';
        }
        res += c;
    }
    return res;
}

void analyze(const string& file_path) {
    ifstream f(file_path);

    auto kw_sorted = keywords;
    sort(kw_sorted.begin(), kw_sorted.end(), [](const string& a, const string& b) {
        return a.length() > b.length();
    });

    string kw_regex;
    for (size_t i = 0; i < kw_sorted.size(); i++) {
        if (i > 0) kw_regex += "|";
        kw_regex += escape(kw_sorted[i]);
    }

    auto op_sorted = operators;
    sort(op_sorted.begin(), op_sorted.end(), [](const string& a, const string& b) {
        return a.length() > b.length();
    });

    string op_regex;
    for (size_t i = 0; i < op_sorted.size(); i++) {
        if (i > 0) op_regex += "|";
        op_regex += escape(op_sorted[i]);
    }

    string sep_regex = R"([;.\(\){}])";

    regex token_regex(
        "(" + kw_regex + ")"            // cuvinte cheie
        + "|(" + op_regex + ")"         // operatori
        + "|(" + sep_regex + ")"        // separatori
        + R"(|("[^"]*"))"               // sir de caractere
        + R"(|(\b\d+(?:\.\d+)?\b))"     // numere cu virgula sau fara
        + R"(|(\b[a-zA-Z]\w*\b))"       // identificatori
    );

    string line;
    while (getline(f, line)) {
        int pos = 0;
        while (pos < line.length()) {
            if (isspace(line[pos])) {
                pos++;
                continue;
            }

            smatch match;
            auto start = line.cbegin() + pos;
            auto end = line.cend();

            if (!regex_search(start, end, match, token_regex, regex_constants::match_continuous)) {
                pos++;
                continue;
            }

            if (match[1].matched) {
                cout << "Cuvant cheie: " << match[1].str() << "\n";
            } else if (match[2].matched) {
                cout << "Operator: " << match[2].str() << "\n";
            } else if (match[3].matched) {
                cout << "Separator: " << match[3].str() << "\n";
            } else if (match[4].matched) {
                cout << "Constanta: " << match[4].str() << "\n";
            } else if (match[5].matched) {
                cout << "Constanta: " << match[5].str() << "\n";
            } else if (match[6].matched) {
                cout << "Identificator: " << match[6].str() << "\n";
            }

            pos += match.length();
        }
    }
}

int main(const int argc, char* argv[]) {
    const string file_name = (argc > 1) ? argv[1] : "../Lab 1/coduri sursa/sursa_1.cpp";
    analyze(file_name);
    return 0;
}