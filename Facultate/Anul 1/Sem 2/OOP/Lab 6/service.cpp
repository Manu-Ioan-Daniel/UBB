
#include "service.h"
#include <algorithm>
#include <cassert>
#include <iostream>

void Service::addDisciplinaService(const string& denumire,int nrOre,const string& tip,const string& cadruDidactic){

    Validator::validateDisciplina(denumire,nrOre,tip,cadruDidactic);
    const Disciplina d{denumire,nrOre,tip,cadruDidactic};
    repo.addDisciplina(d);
    undoActions.push_back(std::make_unique<UndoAdauga>(repo,d));

}
Disciplina Service::cautaDisciplinaService(const string &denumire, const string &tip) const {
    Validator::validateDisciplina(denumire,20,tip,"salut");
    Disciplina d = repo.cautaDisciplina(denumire, tip);
    return d;
}
void Service::modificaDisciplinaService(const string &denumire, const string &tip,const string& denumireNoua,const string& tipNou,int nrOreNou,const string& cadruDidacticNou) {
    Validator::validateDisciplina(denumire,20,tip,"salut");
    Validator::validateDisciplina(denumireNoua,nrOreNou,tipNou,cadruDidacticNou);
    const Disciplina& d = cautaDisciplinaService(denumire,tip);
    const Disciplina disciplinaNoua{denumireNoua,nrOreNou,tipNou,cadruDidacticNou};
    repo.modificaDisciplina(disciplinaNoua,d);
    undoActions.push_back(std::make_unique<UndoModifica>(repo,disciplinaNoua,d));
}
void Service::stergeDisciplinaService(const string &denumire, const string &tip){
    Validator::validateDisciplina(denumire,20,tip,"salut");
    auto disciplina=repo.cautaDisciplina(denumire,tip);
    repo.stergeDisciplina(denumire,tip);
    undoActions.push_back(std::make_unique<UndoSterge>(repo,disciplina));
}
vector<Disciplina>Service::filtrareDisciplineDupaOre(const int nrOre) const {
    vector<Disciplina> discipline=getAll();
    vector<Disciplina> disciplineFiltrate;
    for (const auto& d:discipline) {
        if (d.getNrOre()>=nrOre) {
            disciplineFiltrate.push_back(d);
        }
    }

    return disciplineFiltrate;
}
vector<Disciplina> Service::filtrareDisciplineDupaCadruDidactic(const string& cadruDidactic) const {
    if (cadruDidactic.empty()) {
        throw ValidationError("Cadru didactic invalid!");
    }
    const vector<Disciplina> discipline=getAll();
    vector<Disciplina> disciplineFiltrate;
    for (const auto& d:discipline) {
        if (d.getCadruDidactic()==cadruDidactic) {
            disciplineFiltrate.push_back(d);
        }
    }

    return disciplineFiltrate;
}
vector<Disciplina> Service::sortareDisciplineDupaOre() const {
    vector<Disciplina> discipline=getAll();
    std::ranges::sort(discipline,[](const Disciplina& d1, const Disciplina& d2) {return d1.getNrOre() < d2.getNrOre();});
    return discipline;
}
vector<Disciplina> Service::sortareDisciplineDupaDenumire() const {
    vector<Disciplina> discipline=getAll();
    std::ranges::sort(discipline,[](const Disciplina& d1, const Disciplina& d2) {return d1.getDenumire() < d2.getDenumire();});
    return discipline;
}
vector <Disciplina> Service::sortareDisciplineDupaTipSiCadruDidactic() const {
    vector<Disciplina> discipline=getAll();
    std::ranges::sort(discipline,[](const Disciplina& d1, const Disciplina& d2)
        {if (d1.getTip() == d2.getTip()) {
            return d1.getCadruDidactic() < d2.getCadruDidactic();
        }
        return d1.getTip() < d2.getTip();});

    return discipline;
}
void Service::adaugaDisciplinaContractService(const string &denumire){
    if (denumire.empty()) {
        throw ValidationError("Denumire Invalida!");
    }
    for (auto& d:contract.getAll()) {
        if (d.getDenumire()==denumire) {
            throw ServiceException("Disciplina deja adaugata in contract!");
        }
    }
    for (auto& d:getAll()) {
        if (d.getDenumire()==denumire) {
            contract.adaugaDisciplinaContract(d);
            return;
        }
    }
    throw ServiceException("Nu exista disciplina cu denumirea: "+denumire);
}
void Service::golesteContractService() {
    contract.golesteContract();
}
void Service::genereazaContractService(const int nrDiscipline) {
    if (nrDiscipline<=0) {
        throw ValidationError("Numar de discipline invalid!\n");
    }
    if (nrDiscipline>getAll().size()) {
        throw ServiceException("Numar de discipline prea mare!\n");
    }
    contract.genereazaContract(nrDiscipline,getAll());
}
std::map<string,int> Service::statistici() const{
    std::map<string,int> statistica;
    for (const auto& d:getAll()) {
        statistica[d.getDenumire()]++;
    }
    return statistica;
}
void Service::undo() {
    if (undoActions.empty()) {
        throw ServiceException("Nu mai exista actiuni de undo!");
    }
    undoActions.back()->doUndo();
    undoActions.pop_back();
}
void Service::exportCSVService(const string& filename) const {
    contract.exportCSV(filename);
}
void testService() {
    //test adaugare

    Repo r;
    Validator v;
    Service s{r,v};
    s.addDisciplinaService("mate",5,"laborator","popescu");
    const auto& all=s.getAll();
    assert(all.size()==1);
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
    assert(s.getAll().empty());
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
    const vector<Disciplina> disciplineFiltrate=s.filtrareDisciplineDupaOre(50);
    assert(disciplineFiltrate.size()==50);
    for (auto it=disciplineFiltrate.begin();it!=disciplineFiltrate.end();++it) {
        assert(it->getNrOre()>=50);
    }
    //test filtrareDupaCadruDidactic
    const vector<Disciplina> disciplineFiltrateCadruDidactic=s.filtrareDisciplineDupaCadruDidactic("popescu");
    assert(disciplineFiltrateCadruDidactic.size()==100);
    for (auto it=disciplineFiltrateCadruDidactic.begin();it!=disciplineFiltrateCadruDidactic.end();++it) {
        assert(it->getCadruDidactic()=="popescu");
    }
    //test sortareDisciplineDupaOre
    const vector<Disciplina> disciplineSortateDupaOre=s.sortareDisciplineDupaOre();
    assert(disciplineSortateDupaOre.size()==100);
    for (auto it=disciplineSortateDupaOre.begin();it!=disciplineSortateDupaOre.end();++it) {
        auto it2=it;
        ++it2;
        if (it2!=disciplineSortateDupaOre.end()) {
            assert(it->getNrOre()<=it2->getNrOre());
        }
    }
    //test sortareDisciplineDupaDenumire
    const vector<Disciplina> disciplineSortateDupaDenumire=s.sortareDisciplineDupaDenumire();
    assert(disciplineSortateDupaDenumire.size()==100);
    for (auto it=disciplineSortateDupaDenumire.begin();it!=disciplineSortateDupaDenumire.end();++it) {
        auto it2=it;
        ++it2;
        if (it2!=disciplineSortateDupaDenumire.end()) {
            assert(it->getDenumire()<=it2->getDenumire());
        }
    }
    //test sortareDisciplineDupaTipSiCadruDidactic
    for (int i = 0;i<10;i++) {
        s.addDisciplinaService("mate"+std::to_string(i),i,"laborator"+std::to_string(i),"popescu");
    }
    const vector<Disciplina> disciplineSortateDupaTipSiCadruDidactic=s.sortareDisciplineDupaTipSiCadruDidactic();
    assert(disciplineSortateDupaTipSiCadruDidactic.size()==110);
    for (auto it=disciplineSortateDupaTipSiCadruDidactic.begin();it!=disciplineSortateDupaTipSiCadruDidactic.end();++it) {
        auto it2=it;
        ++it2;
        if (it2!=disciplineSortateDupaTipSiCadruDidactic.end()) {
            if (it->getTip()==it2->getTip()) {
                assert(it->getCadruDidactic()<=it2->getCadruDidactic());
            }
        }
    }
    //test adauga disciplina in contract
    s.addDisciplinaService("matematica",5,"tip","cadru");
    s.adaugaDisciplinaContractService("matematica");
    assert(s.getContractSize()==1);
    assert(s.getContract().getAll()[0].getDenumire()=="matematica");
    try {
        s.adaugaDisciplinaContractService("");
        //assert(false);
    }catch (ValidationError&) {
        assert(true);
    }
    try {
        s.adaugaDisciplinaContractService("salut");
        //assert(false);
    }catch (ServiceException&) {
        assert(true);
    }

    //test goleste contract

    s.golesteContractService();
    assert(s.getContractSize()==0);

    //test genereaza contract

    for (int i = 0;i<100;i++) {
        s.addDisciplinaService("mat"+std::to_string(i),i,"laborator","popescu");
    }
    s.genereazaContractService(50);
    assert(s.getContractSize()==50);
    try {
        s.genereazaContractService(1000);
        //assert(false);
    }catch (ServiceException &e) {
        assert(true);
    }
    try {
        s.genereazaContractService(-1);
        //assert(false);
    }catch (ValidationError& e) {
        assert(true);
    }
    //test statistici
    Repo r2;
    Service s2(r2,v);
    for (int i = 0;i<100;i++) {
        s2.addDisciplinaService("mat"+std::to_string(i),i,"laborator","popescu");
    }
    const auto& statistica=s2.statistici();
    assert(statistica.size()==100);
    for (const auto& [cheie,val]:statistica) {
        assert(val==1);
    }
    //test undo

    for (int i = 99;i>=0;i--) {
        s2.undo();
        assert(s2.getAll().size()==i);
    }
    try {
        s2.undo();
        //assert(false);
    }catch (ServiceException&) {
        assert(true);
    }
    for (int i = 0;i<100;i++) {
        s2.addDisciplinaService("mat"+std::to_string(i),i,"laborator","popescu");
    }
    s2.modificaDisciplinaService("mat0","laborator","mat0","laborator",5,"popescu");
    s2.undo();
    assert(s2.getAll().size()==100);
    assert(s2.getAll()[0].getNrOre()==0);
    s2.stergeDisciplinaService("mat0","laborator");
    s2.undo();
    assert(s2.getAll().size()==100);
    assert(s2.getAll()[99].getNrOre()==0);
    assert(s2.getAll()[99].getDenumire()=="mat0");
    s2.getContract().exportCSV("mama.txt");
}