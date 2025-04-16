#pragma once
#include "Contract.h"
#include "Repo.h"
#include "validator.h"
class ServiceException final: public std::exception {
    string msg;
public:
    explicit ServiceException(string m):msg{std::move(m)}{
    }
    [[nodiscard]] string getMsg() const {
        return msg;
    }
};
class Service {
    Repo& repo;
    Validator& validator;
    Contract contract;
public:
    explicit Service(Repo& repo,Validator& validator): repo(repo), validator(validator) {
    }

    void addDisciplinaService(const string& denumire, int nrOre, const string& tip, const string& cadruDidactic) const;
    [[nodiscard]] vector<Disciplina>& getAll() const {
        return repo.getAll();
    }
    void modificaDisciplinaService(const string& denumire,const string& tip,const string& denumireNoua,const string& tipNou,int nrOreNou,const string& cadruDidacticNou) const;
    void stergeDisciplinaService(const string& denumire,const string& tip) const;

    [[nodiscard]]vector<Disciplina> filtrareDisciplineDupaOre(int nrOre) const;

    [[nodiscard]]vector<Disciplina> filtrareDisciplineDupaCadruDidactic(const string &cadruDidactic) const;

    [[nodiscard]]vector<Disciplina> sortareDisciplineDupaOre() const;

    [[nodiscard]]vector<Disciplina> sortareDisciplineDupaDenumire() const;

    [[nodiscard]]vector<Disciplina> sortareDisciplineDupaTipSiCadruDidactic() const;

    [[nodiscard]] Disciplina cautaDisciplinaService(const string& denumire,const string& tip) const;
    void adaugaDisciplinaContractService(const string& denumire);
    void golesteContractService();
    void genereazaContractService(int nrDiscipline);
    [[nodiscard]] Contract& getContract() {
        return contract;
    }
    [[nodiscard]] int getContractSize() const {
        return contract.getSize();
    }

};
void testService();
