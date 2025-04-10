
#include "Repo.h"
#include <cassert>
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

}