
#include "ui.h"
#include <iostream>
using std::cout;
using std::cin;

void Ui::startUi() {
    int cmd=0;
    while (true) {
        cout<<"0.Exit\n1.Adauga disciplina\n2.Modifica disciplina\n3.Sterge disciplina\n4.Cauta disciplina\n5.Afiseaza discipline\n";
        readInteger(cmd,"Introdu optiunea dorita: ");
        if (cmd==0) {
            break;
        }
        if (cmd==1) {
            try {
                adaugaDisciplina();
            } catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }

        }
        if (cmd==2) {
            try {
                modificaDisciplina();
            } catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }

        }
        if (cmd==3) {
            try {
                stergeDisciplina();
            } catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }

        }
        if (cmd==4) {
            try{
                cautaDisciplina();
            } catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==5) {
            afiseazaDiscipline();
        }
    }


}

void Ui::readInteger(int &x,const string& msg) {
    while (true) {
        cout<<msg;
        std::string input;
        getline(cin,input);
        try {
            x = std::stoi(input);
            break;
        } catch (const std::invalid_argument& e) {
            std::cerr << "Invalid input! Please enter a valid number." << std::endl;
        }
    }
}
void Ui::adaugaDisciplina() const {
    string denumire,tip,cadruDidactic;
    int nrOre;
    cout<<"Denumire: ";
    getline(cin,denumire);
    readInteger(nrOre,"Numar ore:");
    cout<<"Tip: ";
    getline(cin,tip);
    cout<<"Cadru didactic: ";
    getline(cin,cadruDidactic);
    service.addDisciplinaService(denumire,nrOre,tip,cadruDidactic);
}
void Ui::modificaDisciplina() const {
    string denumire,tip,cadruDidactic,denumireNoua,tipNou;
    int nrOreNou;
    cout<<"Denumirea disciplinei pe care vrei sa o modifici: ";
    getline(cin,denumire);
    cout<<"Tipul disciplinei pe care vrei sa o modifici: ";
    getline(cin,tip);
    cout<<"Denumire noua: ";
    getline(cin,denumireNoua);
    cout<<"Tip nou: ";
    getline(cin,tipNou);
    readInteger(nrOreNou,"Numar ore nou: ");
    cout<<"Cadru didactic nou: ";
    getline(cin,cadruDidactic);
    service.modificaDisciplinaService(denumire,tip,denumireNoua,tipNou,nrOreNou,cadruDidactic);
}

void Ui::stergeDisciplina() const {
    string denumire,tip;
    cout<<"Denumirea disciplinei pe care vreo sa o stergi: ";
    getline(cin,denumire);
    cout<<"Tipul disciplinei pe care vrei sa o stergi: ";
    getline(cin,tip);
    service.stergeDisciplinaService(denumire,tip);
}
void Ui::cautaDisciplina() const {
    string denumire,tip;
    cout<<"Denumirea disciplinei pe care vrei sa o cauti: ";
    getline(cin,denumire);
    cout<<"Tipul disciplinei pe care vrei sa o cauti: ";
    getline(cin,tip);
    const Disciplina& d=service.cautaDisciplinaService(denumire,tip);
    cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
}
void Ui::afiseazaDiscipline() const {
    const auto& discipline=service.getAll();
    for (const auto& d:discipline) {
        cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
    }
}

