#pragma once
#include <vector>

#include "disciplina.h"
using std::vector;
class Contract {
private:
    vector<Disciplina>listaDiscipline;
public:
    void golesteContract();
    void adaugaDisciplinaContract(const Disciplina& disciplina);
    [[nodiscard]] vector<Disciplina>& getAll() {
        return listaDiscipline;
    }
    [[nodiscard]] int getSize() const {
        return listaDiscipline.size();
    }
    void genereazaContract(int nrDiscipline,vector<Disciplina> discipline);
};
