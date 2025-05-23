#pragma once
#include "service.h"
#include <QWidget>
#include <QListWidget>
#include <QHBoxLayout>
#include <QFormLayout>
#include <QLineEdit>
#include <QPushButton>
#include <QMessageBox>
#include <QTableWidget>
#include <ranges>
#include <QHeaderView>

class ModifyDialog final : public QDialog {
    Q_OBJECT
private:
    QLineEdit* disciplinaNoua;
    QLineEdit* nrOreNou;
    QLineEdit* tipNou;
    QLineEdit* cadruDidacticNou;
    QPushButton* confirmButton;
public:
    explicit ModifyDialog(QWidget* parent = nullptr) : QDialog(parent) {
        const auto layout = new QFormLayout(this);
        disciplinaNoua = new QLineEdit;
        nrOreNou = new QLineEdit;
        tipNou = new QLineEdit;
        cadruDidacticNou = new QLineEdit;
        confirmButton = new QPushButton("Confirma");

        layout->addRow("Disciplina:", disciplinaNoua);
        layout->addRow("Tip:", tipNou);
        layout->addRow("Cadru didactic:", cadruDidacticNou);
        layout->addRow("Nr ore:", nrOreNou);
        layout->addWidget(confirmButton);
        connect(confirmButton, &QPushButton::clicked,this, &ModifyDialog::accept);

    }

    [[nodiscard]] std::string getName() const { return disciplinaNoua->text().toStdString(); }
    [[nodiscard]] std::string getType() const { return tipNou->text().toStdString(); }
    [[nodiscard]] std::string getTeacher() const { return cadruDidacticNou->text().toStdString(); }
    int getHours() const { return std::stoi(nrOreNou->text().toStdString()); }

    void setInitialData(const std::string& name, const std::string& type, const std::string& teacher, const int hours) const {
        disciplinaNoua->setText(QString::fromStdString(name));
        tipNou->setText(QString::fromStdString(type));
        cadruDidacticNou->setText(QString::fromStdString(teacher));
        nrOreNou->setText(QString::number(hours));
    }
};
class ResultWindow final : public QDialog {
    Q_OBJECT
private:
    QListWidget* resultList;
public:
    ResultWindow(const std::vector<Disciplina>& discipline, QWidget* parent = nullptr) : QDialog(parent) {
        setWindowTitle("Rezultate");
        resize(400, 300);
        auto* layout = new QVBoxLayout(this);
        resultList = new QListWidget(this);

        for (const auto& d : discipline) {
            resultList->addItem(QString::fromStdString(d.getDenumire()+" "+std::to_string(d.getNrOre())+" "+d.getTip()+" "+d.getCadruDidactic()));
        }
        layout->addWidget(resultList);
    }
};
class ExportDialogue final: public QDialog {
    Q_OBJECT
private:
    QLineEdit* filename;
    QPushButton* confirmButton;
public:
    explicit ExportDialogue(QWidget* parent=nullptr) {
        setWindowTitle("Export CSV");
        auto* layout = new QVBoxLayout(this);

        filename = new QLineEdit(this);

        confirmButton = new QPushButton("Exportă", this);
        layout->addWidget(filename);
        layout->addWidget(confirmButton);

        connect(confirmButton, &QPushButton::clicked, this, &ExportDialogue::accept);
    }
    std::string getFilename() const {
        return filename->text().toStdString();
    }
};
class Ui final :public QWidget {
private:
    Service &service;
    Validator &validator;
    QVBoxLayout* layoutStatistics = new QVBoxLayout;
    QPushButton* exitButton=new QPushButton{"&Exit"};
    QPushButton* addButton=new QPushButton{"&Adauga"};
    QPushButton* modifyButton=new QPushButton{"&Modifica"};
    QPushButton* deleteButton=new QPushButton{"&Sterge"};
    QPushButton* searchButton=new QPushButton{"&Cauta"};
    QPushButton* filterByHoursButton=new QPushButton{"&FiltrareDupaOre"};
    QPushButton* filterByTeacherButton=new QPushButton{"&FiltrareDupaCadruDidactic"};
    QPushButton* sortByTypeButton=new QPushButton{"&SortareDupaTip"};
    QPushButton* sortByNameButton=new QPushButton{"&SortareDupaCadruDidactic"};
    QPushButton* addContractButton=new QPushButton{"&AdaugaDisciplinaContract"};
    QPushButton* clearContractButton=new QPushButton{"&GolesteContract"};
    QPushButton* generateContractButton=new QPushButton{"&GenereazaContract"};
    QPushButton* statisticsButton=new QPushButton{"&Statistici"};
    QPushButton* exportCSVButton=new QPushButton{"&ExportCSV"};
    QPushButton* undoButton=new QPushButton{"&Undo"};
    QLineEdit* disciplina=new QLineEdit;
    QLineEdit* tip=new QLineEdit;
    QLineEdit* cadruDidactic=new QLineEdit;
    QLineEdit* nrOre=new QLineEdit;
    QListWidget* list=new QListWidget;
    QTableWidget* contractTable=new QTableWidget;
    void startUi() {
        auto *lyMain=new QHBoxLayout;
        setLayout(lyMain);
        setWindowTitle("Lab 10-11");
        layoutStatistics->setAlignment(Qt::AlignTop);
        lyMain->addLayout(layoutStatistics);
        const auto leftLy=new QVBoxLayout;
        leftLy->addWidget(list);
        leftLy->addWidget(contractTable);
        contractTable->clear();
        contractTable->setColumnCount(4);
        contractTable->setHorizontalHeaderLabels({"Disciplina", "Nr Ore", "Tip", "Cadru Didactic"});



        const auto lyBtnFilterSort=new QHBoxLayout;
        lyBtnFilterSort->addWidget(filterByHoursButton);
        lyBtnFilterSort->addWidget(filterByTeacherButton);
        lyBtnFilterSort->addWidget(sortByTypeButton);
        lyBtnFilterSort->addWidget(sortByNameButton);
        lyBtnFilterSort->addWidget(undoButton);
        leftLy->addLayout(lyBtnFilterSort);

        const auto rightLy=new QVBoxLayout;

        const auto formLy = new QFormLayout;
        formLy->addRow("&Disciplina",disciplina);
        formLy->addRow("&Tip",tip);
        formLy->addRow("&Cadru didactic",cadruDidactic);
        formLy->addRow("&Numar ore",nrOre);
        rightLy->addLayout(formLy);

        const auto lyBtn=new QHBoxLayout;
        lyBtn->addWidget(addButton);
        lyBtn->addWidget(modifyButton);
        lyBtn->addWidget(deleteButton);
        lyBtn->addWidget(searchButton);


        const auto lyBtnContract=new QHBoxLayout;
        lyBtnContract->addWidget(addContractButton);
        lyBtnContract->addWidget(clearContractButton);
        lyBtnContract->addWidget(generateContractButton);
        lyBtnContract->addWidget(statisticsButton);
        lyBtnContract->addWidget(exportCSVButton);

        rightLy->addLayout(lyBtn);
        rightLy->addLayout(lyBtnContract);
        rightLy->addStretch();
        rightLy->addWidget(exitButton);

        lyMain->addLayout(leftLy);
        lyMain->addLayout(rightLy);

    }
    void initConnect() {
        connect(exitButton,QPushButton::clicked,[&]() {
           QMessageBox::information(nullptr,"Exit","Exiting the application");
           close();
        });
        connect(addButton,QPushButton::clicked,[&]() {
            try {
                service.addDisciplinaService(disciplina->text().toStdString(),std::stoi(nrOre->text().toStdString()),tip->text().toStdString(),cadruDidactic->text().toStdString());
                loadData();
            } catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(modifyButton,QPushButton::clicked,[&]() {
            try {
                validator.validateDisciplina(disciplina->text().toStdString(),std::stoi(nrOre->text().toStdString()),tip->text().toStdString(),cadruDidactic->text().toStdString());
            }catch (ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
                return;
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
                return;
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
                return;
            }
            if (const auto modifyDialog=new ModifyDialog(this); modifyDialog->exec()==QDialog::Accepted) {
                try {
                    service.modificaDisciplinaService(disciplina->text().toStdString(),tip->text().toStdString(),modifyDialog->getName(),modifyDialog->getType(),modifyDialog->getHours(),modifyDialog->getTeacher());
                    loadData();
                }catch (const ValidationError& e) {
                    QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
                }catch (const std::out_of_range&) {
                    QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
                }catch (const std::invalid_argument&) {
                    QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
                }catch (const RepoException& e){
                    QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
                }
            }


        });
        connect(deleteButton,QPushButton::clicked,[&]() {
            if (!nrOre->text().isEmpty() || !cadruDidactic->text().isEmpty()) {
                QMessageBox::information(this,"Error","Nu are rost sa completezi numarul de ore sau cadru didactic!");
            }
            try {
                service.stergeDisciplinaService(disciplina->text().toStdString(),tip->text().toStdString());
                loadData();
            }catch (const ValidationError& e) {
                    QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                    QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                    QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                    QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }catch (const ServiceException& e) {
                    QMessageBox::warning(this,"Service Error",QString::fromStdString(e.getMsg()));
            }


        });
        connect(searchButton,QPushButton::clicked,[&]() {
            if (!nrOre->text().isEmpty() || !cadruDidactic->text().isEmpty()) {
                QMessageBox::information(this,"Error","Nu are rost sa completezi numarul de ore sau cadru didactic!");
            }
            try {
                const auto d=service.cautaDisciplinaService(disciplina->text().toStdString(),tip->text().toStdString());
                if (d==Disciplina()) {
                    QMessageBox::information(this,"Cautare","Disciplina cautata nu exista!");
                    return;
                }
                QMessageBox::information(this,"Disciplina:",QString::fromStdString(d.getDenumire()+" "+std::to_string(d.getNrOre())+" "+d.getTip()+" "+d.getCadruDidactic()+" exista!"));
            }catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(filterByHoursButton,QPushButton::clicked,[&]() {
            try {
                const auto discipline=service.filtrareDisciplineDupaOre(std::stoi(nrOre->text().toStdString()));
                const auto resultWindow=new ResultWindow(discipline,this);
                resultWindow->show();
            }catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(filterByTeacherButton,QPushButton::clicked,[&]() {
            try {
                const auto discipline=service.filtrareDisciplineDupaCadruDidactic(cadruDidactic->text().toStdString());
                const auto resultWindow=new ResultWindow(discipline,this);
                resultWindow->show();
            }catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(sortByTypeButton,QPushButton::clicked,[&]() {
            try {
                const auto discipline=service.sortareDisciplineDupaTipSiCadruDidactic();
                const auto resultWindow=new ResultWindow(discipline,this);
                resultWindow->show();
            }catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(sortByNameButton,QPushButton::clicked,[&]() {
            try {
                const auto discipline=service.sortareDisciplineDupaDenumire();
                const auto resultWindow=new ResultWindow(discipline,this);
                resultWindow->show();
            }catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(addContractButton,QPushButton::clicked,[&]() {
            try {
                service.adaugaDisciplinaContractService(disciplina->text().toStdString());
                loadData();
            }catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }catch (const ServiceException& e){
                QMessageBox::warning(this,"Service Error",QString::fromStdString(e.getMsg()));
            }

        });
        connect(clearContractButton,QPushButton::clicked,[&]() {
            service.golesteContractService();
            loadData();
        });
        connect(generateContractButton,QPushButton::clicked,[&]() {
            try {
                service.genereazaContractService(std::stoi(nrOre->text().toStdString()));
                loadData();
            }catch (const ValidationError& e) {
                QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
            }catch (const std::out_of_range&) {
                QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
            }catch (const std::invalid_argument&) {
                QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
            }catch (const RepoException& e){
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }catch (const ServiceException& e){
                QMessageBox::warning(this,"Service Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(statisticsButton,QPushButton::clicked,[&]() {
            const auto statistica=service.statistici();
            std::string result;
            for (const auto& [cheie,val]:statistica) {
                result+=cheie+" "+std::to_string(val)+"\n";
            }
            QMessageBox::information(this,"Statistici",QString::fromStdString(result));
        });
        connect(exportCSVButton,QPushButton::clicked,[&]() {
            if (const auto filename_dialogue=new ExportDialogue(this); filename_dialogue->exec()==QDialog::Accepted) {
                const auto filename=filename_dialogue->getFilename();
                try {
                    service.exportCSVService(filename);
                    QMessageBox::information(this,"Export","Contractul a fost exportat in fisierul "+QString::fromStdString(filename));
                }catch (const ValidationError& e) {
                    QMessageBox::warning(this,"Validation Error",QString::fromStdString(e.getError()));
                }catch (const std::out_of_range&) {
                    QMessageBox::warning(this,"Validation Error","Numar de ore invalid!");
                }catch (const std::invalid_argument&) {
                    QMessageBox::warning(this,"Validation Error","Introdu numarul de ore corect!");
                }catch (const RepoException& e){
                    QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
            }

        });
        connect(undoButton,QPushButton::clicked,[&]() {
            try {
                service.undo();
                loadData();
            }catch (const ServiceException& e) {
                QMessageBox::warning(this,"Service Error",QString::fromStdString(e.getMsg()));
            }catch (const RepoException& e) {
                QMessageBox::warning(this,"Repo Error",QString::fromStdString(e.getMsg()));
            }
        });
        connect(list,QListWidget::itemSelectionChanged,[&]() {
            const auto sel=list->selectedItems();
            if (sel.isEmpty()) {
                disciplina->setText("");
                tip->setText("");
                cadruDidactic->setText("");
                nrOre->setText("");
                return;
            }
            const auto item=sel.at(0);
            const auto text = item->text().toStdString();
            std::istringstream iss(text);
            std::string disciplinaStr, tipStr, cadruStr, nrOreStr;
            iss >> disciplinaStr >> tipStr >> cadruStr >> nrOreStr;
            disciplina->setText(QString::fromStdString(disciplinaStr));
            tip->setText(QString::fromStdString(tipStr));
            cadruDidactic->setText(QString::fromStdString(cadruStr));
            nrOre->setText(QString::fromStdString(nrOreStr));

        });

    }

    void loadData(){
        list->clear();
        contractTable->clear();
        list->addItem("Lista de discipline:");
        for (const auto& d : service.getAll()) {
            list->addItem(QString::fromStdString("Disciplina:"+d.getDenumire() + " Tip: " + d.getTip() + " Cadru didactic:" + d.getCadruDidactic() + " Numar ore: " + std::to_string(d.getNrOre())));
        }

        contractTable->setRowCount(0); // clear table rows
        contractTable->setColumnCount(4); // ensure column count is correct
        contractTable->setHorizontalHeaderLabels({"Disciplina", "Nr Ore", "Tip", "Cadru Didactic"}); // reset headers
        contractTable->horizontalHeader()->setStretchLastSection(true);
        const auto& contract = service.getContract().getAll();
        contractTable->setRowCount(contract.size());
        int row = 0;
        for (const auto& d : contract) {
            contractTable->setItem(row, 0, new QTableWidgetItem(QString::fromStdString(d.getDenumire())));
            contractTable->setItem(row, 1, new QTableWidgetItem(QString::number(d.getNrOre())));
            contractTable->setItem(row, 2, new QTableWidgetItem(QString::fromStdString(d.getTip())));
            contractTable->setItem(row, 3, new QTableWidgetItem(QString::fromStdString(d.getCadruDidactic())));
            row++;
        }
        loadDisciplineButtons();
    }
    void loadDisciplineButtons(){
        QLayoutItem* child;
        while ((child = layoutStatistics->takeAt(0))!=nullptr) {
            delete child->widget();
            delete child;
        }
        std::map<std::string,int> disciplineCount;
        for (const auto& d:service.getAll()) {
            disciplineCount[d.getDenumire()]++;
        }
        for (const auto& [cheie,val]:disciplineCount) {
            QPushButton* button=new QPushButton(QString::fromStdString(cheie));
            connect(button,&QPushButton::clicked,[this,val]() {
                QMessageBox::information(this,"Discipline info",QString::fromStdString("Numar: "+std::to_string(val)));
            });
            layoutStatistics->addWidget(button);
        }
    }
public:
    Ui(Service &service,Validator &validator):service{service},validator{validator} {
        startUi();
        initConnect();
        loadData();

    }

};


