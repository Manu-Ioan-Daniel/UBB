
#include "ui.h"
#include <iostream>
using std::cout;
using std::cin;

void Ui::startUi() const {
    int cmd=0;
    while (true) {
        cout<<"0.Exit\n1.Adauga disciplina\n2.Modifica disciplina\n3.Sterge disciplina\n4.Cauta disciplina\n5.Afiseaza discipline\n6.Filtrare discipline dupa ore\n7.Filtrare discipline dupa cadru didactic\n8.Sortare discipline dupa ore\n9.Sortare discipline dupa denumire\n10.Sortare discipline dupa tip si cadru didactic\n11.Adauga disciplina in contract\n12.Goleste contract\n13.Genereaza contract\n14.Statistici\n15.Undo\n16.Export CSV\n";
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
            }catch (ServiceException& e) {
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
            }catch (ServiceException& e) {
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
            }catch (ServiceException& e) {
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
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==5) {
            afiseazaDiscipline();
        }
        if (cmd==6) {
            try {
                int nrOre;
                readInteger(nrOre,"Numar ore: ");
                const auto& discipline=service.filtrareDisciplineDupaOre(nrOre);
                for (const auto& d:discipline) {
                    cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
                }
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==7) {
            try {
                string cadruDidactic;
                cout<<"Cadru didactic: ";
                getline(cin,cadruDidactic);
                for (const auto& discipline=service.filtrareDisciplineDupaCadruDidactic(cadruDidactic); const auto& d:discipline) {
                    cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
                }
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==8) {
            try {
                for (const auto& discipline=service.sortareDisciplineDupaOre(); const auto& d:discipline) {
                    cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
                }
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==9) {
            try {
                for (const auto& discipline=service.sortareDisciplineDupaDenumire(); const auto& d:discipline) {
                    cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
                }
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==10) {
            try {
                for (const auto& discipline=service.sortareDisciplineDupaTipSiCadruDidactic(); const auto& d:discipline) {
                    cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
                }
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==11) {
            try {
                string denumire;
                cout<<"Denumire: ";
                getline(cin,denumire);
                service.adaugaDisciplinaContractService(denumire);
                cout<<"Avem "<<service.getContract().getSize()<<" discipline in contract\n";
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==12) {
            try {
                service.golesteContractService();
                cout<<"Avem "<<service.getContract().getSize()<<" discipline in contract\n";
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==13) {
            try {
                int nrDiscipline;
                readInteger(nrDiscipline,"Numar discipline: ");
                service.genereazaContractService(nrDiscipline);
                cout<<"Avem "<<service.getContract().getSize()<<" discipline in contract\n";
            }
            catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==14) {
            try {
                for (const auto& discipline=service.statistici(); const auto&[fst, snd]:discipline) {
                    cout<<fst<<" "<<snd<<"\n";
                }
            }catch (ValidationError& e) {
                cout<<e.getError();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }
        }
        if (cmd==15) {
            try {
                undo();
            }catch (RepoException& e) {
                cout<<e.getMsg();
            }catch (ServiceException& e) {
                cout<<e.getMsg();
            }catch (ValidationError& e) {
                cout<<e.getError();
            }
        }
        if (cmd==16) {
            cout<<"Introdu numele fisierului:";
            string filename;
            getline(cin,filename);
            service.exportCSVService(filename);
        }
        if (cmd<0 || cmd>13) {
            cout<<"Optiune invalida!\n";
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
        } catch (const std::invalid_argument&) {
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
    for (const auto& discipline=service.getAll(); const auto& d:discipline) {
        cout<<d.getDenumire()<<" "<<d.getNrOre()<<" "<<d.getTip()<<" "<<d.getCadruDidactic()<<"\n";
    }
}

void Ui::undo() const {
    service.undo();
}