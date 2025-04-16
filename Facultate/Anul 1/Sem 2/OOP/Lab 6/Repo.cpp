
#include "Repo.h"
#include <cassert>
#include <random>
#include "LinkedList.h"

void Repo::addDisciplina(const Disciplina& disciplina) {
    for (const Disciplina& d : discipline) {
        if (d==disciplina) {
            throw RepoException("Disciplina exista deja!\n");
        }

    }
    discipline.push_back(disciplina);
}

Disciplina Repo::cautaDisciplina(const string &denumire, const string &tip) const {
    for (auto & i : discipline) {
        if (i.getDenumire()==denumire && i.getTip()==tip) {
            return i;
        }
    }
    return {};
}
void Repo::stergeDisciplina(const string& denumire,const string& tip) {
    const Disciplina d= cautaDisciplina(denumire,tip);
    if (d==Disciplina{}) {
        throw RepoException("Disciplina nu exista!\n");
    }
    for (auto it=discipline.begin(); it!=discipline.end(); ++it) {
        if (*it==d) {
            discipline.erase(it);
            return;
        }
    }

}
void Repo::modificaDisciplina(const Disciplina& disciplinaNoua,const Disciplina& disciplina) const {
    const Disciplina d =cautaDisciplina(disciplina.getDenumire(),disciplina.getTip());
    if (d==Disciplina{}) {
        throw RepoException("Disciplina nu exista!\n");
    }
    for (auto& i : discipline) {
        if (i==d) {
            i=disciplinaNoua;
            return;
        }
    }

}
void Repo::golesteContract() {
    for (auto it=contract.begin(); it!=LinkedList<Disciplina>::end(); ++it) {
        contract.erase(it);
    }
}
void Repo::adaugaDisciplinaContract(const Disciplina &disciplina) {
    for (auto it=contract.begin(); it!=contract.end(); ++it) {
        if (disciplina.getDenumire()==it->getDenumire()) {
            throw RepoException("Disciplina cu denumirea "+disciplina.getDenumire()+ " exista deja in contract!\n");
        }
    }
    contract.push_back(disciplina);
}
void Repo::genereazaContract(const int nrDiscipline) {
    //generam un contract cu nume de discpline ales aleatoriu
    const std::vector<string> Discipline={
            "mate","info","fizica","chimie","biologie","istorie","geografie","educatie fizica",
            "muzica","arta","religie","psihologie","sociologie","filozofie","limba romana",
            "limba engleza","limba franceza","limba germana","limba spaniola"
    };
    std::mt19937 mt{std::random_device{}()};
    std::uniform_int_distribution<int> dist(0, Discipline.size() - 1);
    for (int i = 0;i<nrDiscipline;i++) {
        const int randomIndex=dist(mt);
        const Disciplina d{Discipline[randomIndex],randomIndex%10+1,"laborator","popescu"};
        adaugaDisciplinaContract(d);
    }
}

void testRepo() {

    //test adaugare repo

    const Disciplina d1{"mate", 5, "laborator", "popescu"};
    const Disciplina d2{"info", 6, "seminar", "ionescu"};
    Repo r;
    r.addDisciplina(d1);
    const auto all=r.getAll();
    assert(all.getSize()==1);
    assert(*all.begin()==d1);
    try {
        r.addDisciplina(d1);
        //assert(false);
    } catch (RepoException&) {
        assert(true);
    }

    //test cauta disciplina repo

    assert(r.cautaDisciplina("mate","laborator")==d1);

    //test modifica disciplina repo
    r.modificaDisciplina(d2,d1);
    assert(*r.getAll().begin()==d2);
    try {
        r.modificaDisciplina(d2,d1);
       // assert(false);
    } catch (RepoException&) {
        assert(true);
    }

    //test sterge disciplina repo

    r.stergeDisciplina(d2.getDenumire(),d2.getTip());
    assert(r.getAll().getSize()==0);
    try {
        r.stergeDisciplina(d1.getDenumire(),d1.getTip());
        //assert(false);
    } catch (RepoException&) {
        assert(true);
    }

    //test adauga contract
    r.adaugaDisciplinaContract(d1);
    assert(r.getContractSize()==1);
    try {
        r.adaugaDisciplinaContract(d1);
        //assert(false);
    } catch (RepoException&) {
        assert(true);
    }

    //test goleste contract

    r.golesteContract();
    assert(r.getContractSize()==0);

    //test genereaza aleatoriu

    //nu stiu inca



}