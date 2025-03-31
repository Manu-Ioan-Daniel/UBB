
#include "service.h"

#include <cassert>
#include <iostream>

void Service::addDisciplinaService(const string& denumire,int nrOre,const string& tip,const string& cadruDidactic) const {

    Validator::validateDisciplina(denumire,nrOre,tip,cadruDidactic);
    const Disciplina d{denumire,nrOre,tip,cadruDidactic};
    repo.addDisciplina(d);

}
Disciplina& Service::cautaDisciplinaService(const string &denumire, const string &tip) const {
    Validator::validateDisciplina(denumire,20,tip,"salut");
    const int poz = repo.cautaDisciplina(denumire,tip);
    return repo.getAll()[poz];
}
void Service::modificaDisciplinaService(const string &denumire, const string &tip,const string& denumireNoua,const string& tipNou,int nrOreNou,const string& cadruDidacticNou) const {
    Validator::validateDisciplina(denumire,20,tip,"salut");
    Validator::validateDisciplina(denumireNoua,nrOreNou,tipNou,cadruDidacticNou);
    const Disciplina& d = cautaDisciplinaService(denumire,tip);
    Disciplina disciplinaNoua{denumireNoua,nrOreNou,tipNou,cadruDidacticNou};
    repo.modificaDisciplina(disciplinaNoua,d);
}
void Service::stergeDisciplinaService(const string &denumire, const string &tip) const {
    Validator::validateDisciplina(denumire,20,tip,"salut");
    repo.stergeDisciplina(denumire,tip);
}


void testService() {
    //test adaugare

    Repo r;
    Validator v;
    const Service s{r,v};
    s.addDisciplinaService("mate",5,"laborator","popescu");
    const auto& all=s.getAll();
    assert(all.size()==1);
    assert(all[0]==Disciplina("mate",5,"laborator","popescu"));
    try {
        s.addDisciplinaService("",-1,"","");
        //assert(false);
    }catch (ValidationError&) {
        assert(true);
    }

    //test cauta disciplina

     const Disciplina& d=s.cautaDisciplinaService("mate","laborator");
     const auto &d2 = Disciplina("mate",5,"laborator","popescu");
     assert(d==d2);
     try {
         s.cautaDisciplinaService("","");
         //assert(false);
     }catch (ValidationError&) {
         assert(true);
     }

    //test modifica disciplina

    s.modificaDisciplinaService("mate","laborator","info","seminar",6,"ionescu");
    assert(s.getAll()[0]==Disciplina("info",6,"seminar","ionescu"));
    try {
        s.modificaDisciplinaService("mate","laborator","","",0,"");
        //assert(false);
    }catch (ValidationError&) {
        assert(true);
    }

    //test sterge disciplina

    s.stergeDisciplinaService("info","seminar");
    assert(s.getAll().empty());
    try {
        s.stergeDisciplinaService("","");
        //assert(false);
    }catch (ValidationError&) {
        assert(true);
    }

}