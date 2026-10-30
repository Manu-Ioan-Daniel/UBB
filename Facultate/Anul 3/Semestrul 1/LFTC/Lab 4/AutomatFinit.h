#pragma once

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <set>

struct Tranzitie {
    std::string sursa;
    char simbol;
    std::string destinatie;
};

class AutomatFinit {
    std::set<std::string> stari;
    std::set<char> alfabet;
    std::string stareInitiala;
    std::set<std::string> stariFinale;
    std::vector<Tranzitie> tranzitii;

    [[nodiscard]] std::string urmatoareaStare(const std::string& st, char c) const {
        for (const auto&[sursa, simbol, destinatie] : tranzitii) {
            if (sursa == st && simbol == c) {
                return destinatie;
            }
        }
        return "";
    }

public:
    void clear() {
        stari.clear();
        alfabet.clear();
        stareInitiala.clear();
        stariFinale.clear();
        tranzitii.clear();
    }

    bool citesteDinFisier(const std::string& nume) {
        std::ifstream fin(nume);
        if (!fin.is_open()) return false;
        clear();

        std::string linie;
        bool inTranzitii = false;
        while (std::getline(fin, linie)) {
            if (linie.empty()) continue;
            std::stringstream ss(linie);
            std::string tag;
            ss >> tag;
            if (tag == "stari:") {
                std::string s;
                while (ss >> s) stari.insert(s);
            } else if (tag == "alfabet:") {
                char c;
                while (ss >> c) alfabet.insert(c);
            } else if (tag == "stare_initiala:") {
                ss >> stareInitiala;
            } else if (tag == "stari_finale:") {
                std::string s;
                while (ss >> s) stariFinale.insert(s);
            } else if (tag == "tranzitii:") {
                inTranzitii = true;
            } else if (inTranzitii) {
                std::string dest;
                char simb;
                if (ss >> simb >> dest) {
                    tranzitii.push_back({tag, simb, dest});
                    stari.insert(tag);
                    stari.insert(dest);
                    alfabet.insert(simb);
                }
            }
        }
        return !stari.empty() && !stareInitiala.empty();
    }

    void citesteDeLaTastatura() {
        clear();
        std::string linie, s, dest;
        char c;

        std::cout << "stari: ";
        std::getline(std::cin >> std::ws, linie);
        std::stringstream ss1(linie);
        while (ss1 >> s) stari.insert(s);

        std::cout << "alfabet: ";
        std::getline(std::cin, linie);
        std::stringstream ss2(linie);
        while (ss2 >> c) alfabet.insert(c);

        std::cout << "stare initiala: ";
        std::cin >> stareInitiala;

        std::cout << "stari finale: ";
        std::getline(std::cin >> std::ws, linie);
        std::stringstream ss3(linie);
        while (ss3 >> s) stariFinale.insert(s);

        std::cout << "numar tranzitii: ";
        int n = 0;
        std::cin >> n;
        std::cout << "tranzitii (sursa simbol destinatie):\n";
        while (n--) {
            std::cin >> s >> c >> dest;
            tranzitii.push_back({s, c, dest});
            stari.insert(s);
            stari.insert(dest);
            alfabet.insert(c);
        }
    }

    void afiseazaStari() const {
        std::cout << "stari: ";
        for (const auto& s : stari) std::cout << s << " ";
        std::cout << "\n";
    }

    void afiseazaAlfabet() const {
        std::cout << "alfabet: ";
        for (char c : alfabet) std::cout << c << " ";
        std::cout << "\n";
    }

    void afiseazaTranzitii() const {
        std::cout << "tranzitii:\n";
        for (const auto& t : tranzitii) {
            std::cout << "  " << t.sursa << " " << t.simbol << " " << t.destinatie << "\n";
        }
    }

    void afiseazaStariFinale() const {
        std::cout << "stari finale: ";
        for (const auto& s : stariFinale) std::cout << s << " ";
        std::cout << "\n";
    }

    void afiseazaStareInitiala() const {
        std::cout << "stare initiala: " << stareInitiala << "\n";
    }

    [[nodiscard]] bool esteDeterminist() const {
        for (size_t i = 0; i < tranzitii.size(); ++i) {
            for (size_t j = i + 1; j < tranzitii.size(); ++j) {
                if (tranzitii[i].sursa == tranzitii[j].sursa && tranzitii[i].simbol == tranzitii[j].simbol) {
                    return false;
                }
            }
        }
        return true;
    }

    [[nodiscard]] bool verificaSecventa(const std::string& w) const {
        if (!esteDeterminist()) return false;
        std::string st = stareInitiala;
        for (char c : w) {
            st = urmatoareaStare(st, c);
            if (st.empty()) return false;
        }
        return stariFinale.contains(st);
    }

    [[nodiscard]] std::pair<bool, std::string> celMaiLungPrefix(const std::string& w) const {
        if (!esteDeterminist()) return {false, ""};
        std::string st = stareInitiala;
        int maxLen = stariFinale.contains(st) ? 0 : -1;
        for (int i = 0; i < static_cast<int>(w.size()); ++i) {
            st = urmatoareaStare(st, w[i]);
            if (st.empty()) break;
            if (stariFinale.contains(st)) maxLen = i + 1;
        }
        if (maxLen == -1) return {false, ""};
        return {true, w.substr(0, maxLen)};
    }
};
