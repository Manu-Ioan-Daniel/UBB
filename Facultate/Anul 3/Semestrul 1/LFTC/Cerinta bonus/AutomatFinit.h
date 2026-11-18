#pragma once

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <set>
#include <map>

struct State {
    std::string description;
    bool isAcceptState = false;
    std::multimap<char, std::string> transitions;
};

class StateMachine {
    std::string description;
    std::string startState;
    std::map<std::string, State> states;
    std::set<char> alphabet;

public:
    bool citesteDinFisier(const std::string& nume) {
        std::ifstream fin(nume);
        if (!fin.is_open()) return false;
        states.clear();
        alphabet.clear();
        startState.clear();

        std::string linie;
        bool inTranzitii = false;
        while (std::getline(fin, linie)) {
            if (linie.empty()) continue;
            std::stringstream ss(linie);
            std::string tag;
            ss >> tag;
            if (tag == "descriere:") {
                std::getline(ss >> std::ws, description);
            } else if (tag == "stari:") {
                std::string s;
                while (ss >> s) {
                    states[s].description = s;
                }
            } else if (tag == "alfabet:") {
                char c;
                while (ss >> c) {
                    alphabet.insert(c);
                }
            } else if (tag == "stare_initiala:") {
                ss >> startState;
            } else if (tag == "stari_finale:") {
                std::string s;
                while (ss >> s) {
                    states[s].isAcceptState = true;
                }
            } else if (tag == "tranzitii:") {
                inTranzitii = true;
            } else if (inTranzitii) {
                std::string dest;
                char simb;
                if (ss >> simb >> dest) {
                    states[tag].description = tag;
                    states[tag].transitions.insert({simb, dest});
                    states[dest].description = dest;
                    alphabet.insert(simb);
                }
            }
        }
        return !states.empty() && !startState.empty();
    }

    void afiseazaStari() const {
        std::cout << "stari: ";
        for (const auto& [nume, st] : states) {
            std::cout << nume << " ";
        }
        std::cout << "\n";
    }

    void afiseazaAlfabet() const {
        std::cout << "simboluri alfabet: ";
        for (char c : alphabet) {
            std::cout << c << " ";
        }
        std::cout << "\n";
    }

    void afiseazaStariFinale() const {
        std::cout << "stari finale: ";
        for (const auto& [nume, st] : states) {
            if (st.isAcceptState) {
                std::cout << nume << " ";
            }
        }
        std::cout << "\n";
    }

    void afiseazaTranzitii() const {
        std::cout << "tranzitii:\n";
        for (const auto& [nume, st] : states) {
            for (const auto& [simb, dest] : st.transitions) {
                std::cout << "  " << nume << " -" << simb << "-> " << dest << "\n";
            }
        }
    }
};

using AutomatFinit = StateMachine;
