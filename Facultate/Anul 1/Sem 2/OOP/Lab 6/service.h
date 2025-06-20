#pragma once
#include <map>
#include <memory>
#include "Contract.h"
#include "Repo.h"
#include "undo.h"
#include "validator.h"
#include "observer.h"
class ServiceException final: public std::exception {
    string msg;
public:
    explicit ServiceException(string m):msg{std::move(m)}{
    }
    [[nodiscard]] string getMsg() const {
        return msg;
    }
};
class Service:public Observable {
    Repo& repo;
    Validator& validator;
    Contract contract;
    std::vector<std::unique_ptr<ActiuneUndo>> undoActions;

public:
    explicit Service(Repo& repo,Validator& validator): repo(repo), validator(validator) {
    }

    void addDisciplinaService(const string& denumire, int nrOre, const string& tip, const string& cadruDidactic);
    [[nodiscard]] vector<Disciplina>& getAll() const {
        return repo.getAll();
    }
    void modificaDisciplinaService(const string& denumire,const string& tip,const string& denumireNoua,const string& tipNou,int nrOreNou,const string& cadruDidacticNou) ;
    void stergeDisciplinaService(const string& denumire,const string& tip) ;

    [[nodiscard]]vector<Disciplina> filtrareDisciplineDupaOre(int nrOre) const;

    [[nodiscard]]vector<Disciplina> filtrareDisciplineDupaCadruDidactic(const string &cadruDidactic) const;

    [[nodiscard]]vector<Disciplina> sortareDisciplineDupaOre();

    [[nodiscard]]vector<Disciplina> sortareDisciplineDupaDenumire() const;

    [[nodiscard]]vector<Disciplina> sortareDisciplineDupaTipSiCadruDidactic() const;

    [[nodiscard]] Disciplina cautaDisciplinaService(const string& denumire,const string& tip) const;
    void adaugaDisciplinaContractService(const string& denumire);
    void golesteContractService();
    void genereazaContractService(int nrDiscipline);
    [[nodiscard]] Contract& getContract() {
        return contract;
    }

    [[nodiscard]] std::map<string,int> statistici() const;
    [[nodiscard]] int getContractSize() const {
        return contract.getSize();
    }
    void undo();

    void exportCSVService(const string &filename) const;
};
void testService();
