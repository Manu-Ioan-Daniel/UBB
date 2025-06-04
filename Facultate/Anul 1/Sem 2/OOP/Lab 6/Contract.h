#pragma once
#include <vector>
#include "observer.h"
#include "disciplina.h"
using std::vector;
class Contract:public Observable {
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
    void exportCSV(const string& filename) const;
};
