#pragma once

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <set>
#include <map>

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
    std::map<std::pair<std::string, char>, std::string> tranzMap;

    [[nodiscard]] std::string urmatoareaStare(const std::string& st, char c) const {
        auto it = tranzMap.find({st, c});
        if (it != tranzMap.end()) {
            return it->second;
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
        tranzMap.clear();
    }

    bool citesteDinFisier(const std::string& nume) {
        std::ifstream fin(nume);
        if (!fin.is_open()) {
            fin.open("../" + nume);
            if (!fin.is_open()) return false;
        }
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
                    tranzMap[{tag, simb}] = dest;
                    stari.insert(tag);
                    stari.insert(dest);
                    alfabet.insert(simb);
                }
            }
        }
        return !stari.empty() && !stareInitiala.empty();
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
