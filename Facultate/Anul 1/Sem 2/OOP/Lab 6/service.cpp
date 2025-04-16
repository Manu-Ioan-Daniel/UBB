
#include "service.h"

#include <cassert>
#include <iostream>

void Service::addDisciplinaService(const string& denumire,int nrOre,const string& tip,const string& cadruDidactic) const {

    Validator::validateDisciplina(denumire,nrOre,tip,cadruDidactic);
    const Disciplina d{denumire,nrOre,tip,cadruDidactic};
    repo.addDisciplina(d);

}
Disciplina Service::cautaDisciplinaService(const string &denumire, const string &tip) const {
    Validator::validateDisciplina(denumire,20,tip,"salut");
    Disciplina d = repo.cautaDisciplina(denumire, tip);
    return d;
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
LinkedList<Disciplina>Service::filtrareDisciplineDupaOre(const int nrOre) const {
    LinkedList<Disciplina> discipline=getAll();
    LinkedList<Disciplina> disciplineFiltrate;
    for (const auto& d:discipline) {
        if (d.getNrOre()>=nrOre) {
            disciplineFiltrate.push_back(d);
        }
    }

    return disciplineFiltrate;
}
LinkedList<Disciplina> Service::filtrareDisciplineDupaCadruDidactic(const string& cadruDidactic) const {
    const LinkedList<Disciplina> discipline=getAll();
    LinkedList<Disciplina> disciplineFiltrate;
    for (const auto& d:discipline) {
        if (d.getCadruDidactic()==cadruDidactic) {
            disciplineFiltrate.push_back(d);
        }
    }

    return disciplineFiltrate;
}
LinkedList<Disciplina> Service::sortareDisciplineDupaOre() const {
    LinkedList<Disciplina> discipline=getAll();
    discipline.sort([](const Disciplina& d1, const Disciplina& d2) {
        return d1.getNrOre() < d2.getNrOre();
    });
    return discipline;
}
LinkedList<Disciplina> Service::sortareDisciplineDupaDenumire() const {
    LinkedList<Disciplina> discipline=getAll();
    discipline.sort([](const Disciplina& d1, const Disciplina& d2) {
        return d1.getDenumire() < d2.getDenumire();
    });
    return discipline;
}
LinkedList <Disciplina> Service::sortareDisciplineDupaTipSiCadruDidactic() const {
    LinkedList<Disciplina> discipline=getAll();
    discipline.sort([](const Disciplina& d1, const Disciplina& d2) {
        if (d1.getTip() == d2.getTip()) {
            return d1.getCadruDidactic() < d2.getCadruDidactic();
        }
        return d1.getTip() < d2.getTip();
    });
    return discipline;
}
void Service::adaugaDisciplinaContractService(const string &denumire) const {
    if (denumire.empty()) {
        throw ValidationError("Denumire Invalida!");
    }
    for (auto& d:getAll()) {
        if (d.getDenumire()==denumire) {
            repo.adaugaDisciplinaContract(d);
            return;
        }
    }
    throw ServiceException("Nu exista disciplina cu denumirea: "+denumire);
}
void Service::golesteContractService()const {
    repo.golesteContract();
}
void Service::genereazaContractService(const int nrDiscipline)const {
    if (nrDiscipline<=0) {
        throw ValidationError("Numar de discipline invalid!");
    }
    repo.genereazaContract(nrDiscipline);
}
void testService() {
    //test adaugare

    Repo r;
    Validator v;
    const Service s{r,v};
    s.addDisciplinaService("mate",5,"laborator","popescu");
    const auto& all=s.getAll();
    assert(all.getSize()==1);
    assert(*all.begin()==Disciplina("mate",5,"laborator","popescu"));
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


    //test modifica disciplina

    s.modificaDisciplinaService("mate","laborator","info","seminar",6,"ionescu");
    assert(*s.getAll().begin()==Disciplina("info",6,"seminar","ionescu"));
    try {
        s.modificaDisciplinaService("mate","laborator","","",0,"");
        //assert(false);
    }catch (ValidationError&) {
        assert(true);
    }

    //test sterge disciplina

    s.stergeDisciplinaService("info","seminar");
    assert(s.getAll().getSize()==0);
    try {
        s.stergeDisciplinaService("","");
        //assert(false);
    }catch (ValidationError&) {
        assert(true);
    }

    //test filtrareDupaOre

    for (int i = 0;i<100;i++) {
        s.addDisciplinaService("mate"+std::to_string(i),i,"laborator","popescu");
    }
    const LinkedList<Disciplina> disciplineFiltrate=s.filtrareDisciplineDupaOre(50);
    assert(disciplineFiltrate.getSize()==50);
    for (auto it=disciplineFiltrate.begin();it!=LinkedList<Disciplina>::end();++it) {
        assert(it->getNrOre()>=50);
    }
    //test filtrareDupaCadruDidactic
    const LinkedList<Disciplina> disciplineFiltrateCadruDidactic=s.filtrareDisciplineDupaCadruDidactic("popescu");
    assert(disciplineFiltrateCadruDidactic.getSize()==100);
    for (auto it=disciplineFiltrateCadruDidactic.begin();it!=LinkedList<Disciplina>::end();++it) {
        assert(it->getCadruDidactic()=="popescu");
    }
    //test sortareDisciplineDupaOre
    const LinkedList<Disciplina> disciplineSortateDupaOre=s.sortareDisciplineDupaOre();
    assert(disciplineSortateDupaOre.getSize()==100);
    for (auto it=disciplineSortateDupaOre.begin();it!=LinkedList<Disciplina>::end();++it) {
        auto it2=it;
        ++it2;
        if (it2!=LinkedList<Disciplina>::end()) {
            assert(it->getNrOre()<=it2->getNrOre());
        }
    }
    //test sortareDisciplineDupaDenumire
    const LinkedList<Disciplina> disciplineSortateDupaDenumire=s.sortareDisciplineDupaDenumire();
    assert(disciplineSortateDupaDenumire.getSize()==100);
    for (auto it=disciplineSortateDupaDenumire.begin();it!=LinkedList<Disciplina>::end();++it) {
        auto it2=it;
        ++it2;
        if (it2!=LinkedList<Disciplina>::end()) {
            assert(it->getDenumire()<=it2->getDenumire());
        }
    }
    //test sortareDisciplineDupaTipSiCadruDidactic
    for (int i = 0;i<10;i++) {
        s.addDisciplinaService("mate"+std::to_string(i),i,"laborator"+std::to_string(i),"popescu");
    }
    const LinkedList<Disciplina> disciplineSortateDupaTipSiCadruDidactic=s.sortareDisciplineDupaTipSiCadruDidactic();
    assert(disciplineSortateDupaTipSiCadruDidactic.getSize()==110);
    for (auto it=disciplineSortateDupaTipSiCadruDidactic.begin();it!=LinkedList<Disciplina>::end();++it) {
        auto it2=it;
        ++it2;
        if (it2!=LinkedList<Disciplina>::end()) {
            if (it->getTip()==it2->getTip()) {
                assert(it->getCadruDidactic()<=it2->getCadruDidactic());
            }
        }
    }
    //test adauga disciplina in contract
    s.addDisciplinaService("matematica",5,"tip","cadru");
    s.adaugaDisciplinaContractService("matematica");
    assert(s.getContractSize()==1);
    assert(s.getContract().begin()->getDenumire()=="matematica");
    try {
        s.adaugaDisciplinaContractService("");
        assert(false);
    }catch (ValidationError&) {
        assert(true);
    }

    //test goleste contract

    s.golesteContractService();
    assert(s.getContractSize()==0);
}