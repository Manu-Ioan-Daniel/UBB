#pragma once
#include "Repo.h"
#include "validator.h"

class Service {
    Repo& repo;
    Validator& validator;
public:
    explicit Service(Repo& repo,Validator& validator): repo(repo), validator(validator) {
    }

    void addDisciplinaService(const string& denumire, int nrOre, const string& tip, const string& cadruDidactic) const;
    [[nodiscard]] std::vector<Disciplina>& getAll() const {
        return repo.getAll();
    }
    void modificaDisciplinaService(const string& denumire,const string& tip,const string& denumireNoua,const string& tipNou,int nrOreNou,const string& cadruDidacticNou) const;
    void stergeDisciplinaService(const string& denumire,const string& tip) const;
    [[nodiscard]] Disciplina cautaDisciplinaService(const string& denumire,const string& tip) const;



};
void testService();
