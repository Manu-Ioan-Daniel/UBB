//
// Created by Deny on 16-04-2025.
//

#include "Contract.h"
#include <algorithm>
#include <random>
#include <chrono>
void Contract::golesteContract() {
    listaDiscipline.erase(listaDiscipline.begin(),listaDiscipline.end());
}
void Contract::genereazaContract(const int nrDiscipline,vector<Disciplina> discipline) {
    golesteContract();
    const auto seed= std::chrono::system_clock::now().time_since_epoch().count();
    std::ranges::shuffle(discipline, std::default_random_engine(seed));
    std::ranges::copy_n(discipline.begin(), nrDiscipline, std::back_inserter(listaDiscipline));
}
void Contract::adaugaDisciplinaContract(const Disciplina &disciplina) {
    listaDiscipline.push_back(disciplina);
}
