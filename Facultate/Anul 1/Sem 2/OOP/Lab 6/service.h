#pragma once
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
public:
    explicit Service(Repo& repo,Validator& validator): repo(repo), validator(validator) {
    }

    void addDisciplinaService(const string& denumire, int nrOre, const string& tip, const string& cadruDidactic) const;
    [[nodiscard]] LinkedList<Disciplina>& getAll() const {
        return repo.getAll();
    }
    void modificaDisciplinaService(const string& denumire,const string& tip,const string& denumireNoua,const string& tipNou,int nrOreNou,const string& cadruDidacticNou) const;
    void stergeDisciplinaService(const string& denumire,const string& tip) const;

    [[nodiscard]]LinkedList<Disciplina> filtrareDisciplineDupaOre(int nrOre) const;

    [[nodiscard]]LinkedList<Disciplina> filtrareDisciplineDupaCadruDidactic(const string &cadruDidactic) const;

    [[nodiscard]]LinkedList<Disciplina> sortareDisciplineDupaOre() const;

    [[nodiscard]]LinkedList<Disciplina> sortareDisciplineDupaDenumire() const;

    [[nodiscard]]LinkedList<Disciplina> sortareDisciplineDupaTipSiCadruDidactic() const;

    [[nodiscard]] Disciplina cautaDisciplinaService(const string& denumire,const string& tip) const;
    void adaugaDisciplinaContractService(const string& denumire) const;
    void golesteContractService() const;
    void genereazaContractService(int nrDiscipline) const;
    [[nodiscard]] LinkedList<Disciplina>& getContract() const{return repo.getContract();};
    [[nodiscard]] int getContractSize() const{return repo.getContractSize();};



};
void testService();
