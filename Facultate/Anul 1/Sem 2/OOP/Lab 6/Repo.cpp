
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
    if (const auto it=std::ranges::find_if(discipline,[&denumire,&tip](const Disciplina& d) {return d.getDenumire()==denumire && d.getTip()==tip;}); it!=discipline.end()) {
        return *it;
    }
    return {};
}
void Repo::stergeDisciplina(const string& denumire,const string& tip) {
    const Disciplina d= cautaDisciplina(denumire,tip);
    if (d==Disciplina{}) {
        throw RepoException("Disciplina nu exista!\n");
    }
    std::erase_if(discipline, [&d](const Disciplina& disc){return disc == d;});

}
void Repo::modificaDisciplina(const Disciplina& disciplinaNoua,const Disciplina& disciplina) {
    const Disciplina d =cautaDisciplina(disciplina.getDenumire(),disciplina.getTip());
    if (d==Disciplina{}) {
        throw RepoException("Disciplina nu exista!\n");
    }
    std::replace_if(discipline.begin(), discipline.end(), [&d](const Disciplina& disc) {return disc == d;}, disciplinaNoua);

}

void testRepo() {

    //test adaugare repo

    const Disciplina d1{"mate", 5, "laborator", "popescu"};
    const Disciplina d2{"info", 6, "seminar", "ionescu"};
    Repo r;
    r.addDisciplina(d1);
    const auto all=r.getAll();
    assert(all.size()==1);
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
    assert(r.getAll().empty());
    try {
        r.stergeDisciplina(d1.getDenumire(),d1.getTip());
        //assert(false);
    } catch (RepoException&) {
        assert(true);
    }





}