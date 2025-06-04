//
// Created by Deny on 16-04-2025.
//

#include "Contract.h"
#include <algorithm>
#include <random>
#include <chrono>
#include <fstream>
void Contract::golesteContract() {
    listaDiscipline.erase(listaDiscipline.begin(),listaDiscipline.end());
    notify();
}
void Contract::genereazaContract(const int nrDiscipline,vector<Disciplina> discipline) {
    golesteContract();
    const auto seed= std::chrono::system_clock::now().time_since_epoch().count();
    std::ranges::shuffle(discipline, std::default_random_engine(seed));
    std::ranges::copy_n(discipline.begin(), nrDiscipline, std::back_inserter(listaDiscipline));
    notify();
}
void Contract::adaugaDisciplinaContract(const Disciplina &disciplina) {
    listaDiscipline.push_back(disciplina);
    notify();
}
void Contract::exportCSV(const string& filename) const {
    std::ofstream out(filename);
    if (!out.is_open()) {
        throw std::runtime_error("Could not open file");
    }
    for (const auto& d:listaDiscipline) {
        out<<d.getDenumire()<<" ,"<<d.getTip()<<" ,"<<d.getCadruDidactic()<<", "<<d.getNrOre()<<"."<<std::endl;
    }
    out.close();
}