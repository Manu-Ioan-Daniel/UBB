
#include "Repo.h"
#include <cassert>


void Repo::addDisciplina(const Disciplina& disciplina) {
    for (const Disciplina& d : discipline) {
        if (d==disciplina) {
            throw RepoException("Disciplina exista deja!\n");
        }

    }
    discipline.push_back(disciplina);
}
int Repo::cautaDisciplina(const string& denumire,const string& tip) const {
    for (int i=0;i<discipline.size();i++) {
        if (discipline[i].getDenumire()==denumire && discipline[i].getTip()==tip) {
            return i;
        }
    }
    throw RepoException("Disciplina nu exista!\n");
}
void Repo::stergeDisciplina(const string& denumire,const string& tip) {
    const int poz=cautaDisciplina(denumire,tip);
    discipline.erase(discipline.begin()+poz);
}
void Repo::modificaDisciplina(const Disciplina& disciplinaNoua,const Disciplina& disciplina) {
    const int poz=cautaDisciplina(disciplina.getDenumire(),disciplina.getTip());
    discipline[poz]=disciplinaNoua;
}
void testRepo() {

    //test adaugare repo

    const Disciplina d1{"mate", 5, "laborator", "popescu"};
    const Disciplina d2{"info", 6, "seminar", "ionescu"};
    Repo r;
    r.addDisciplina(d1);
    const auto all=r.getAll();
    assert(all.size()==1);
    assert(all[0]==d1);
    try {
        r.addDisciplina(d1);
        //assert(false);
    } catch (RepoException&) {
        assert(true);
    }

    //test cauta disciplina repo

    assert(r.cautaDisciplina("mate","laborator")==0);
    try{
        r.cautaDisciplina("","");
        //assert(false);
    }catch (RepoException&) {
        assert(true);
    }

    //test modifica disciplina repo
    r.modificaDisciplina(d2,d1);
    assert(r.getAll()[0]==d2);
    try {
        r.modificaDisciplina(d2,d1);
       // assert(false);
    } catch (RepoException&) {
        assert(true);
    }

    //test sterge disciplina repo

    r.stergeDisciplina(d2.getDenumire(),d2.getTip());
    assert(r.getAll().empty());
    try {
        r.stergeDisciplina(d1.getDenumire(),d1.getTip());
        //assert(false);
    } catch (RepoException&) {
        assert(true);
    }

}