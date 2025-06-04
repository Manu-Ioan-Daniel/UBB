#pragma once
#include "service.h"
#include "observer.h"
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qwidget.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qmessagebox.h>
#include <QTextEdit>
#include <string>

class ContractCRUDGUI final : public QWidget, public Observer {
public:
    explicit ContractCRUDGUI(Service& service,QWidget* parent=nullptr) : service{service}, contract(service.getContract()), QWidget(parent) {
        initGUI();
        loadData();
        initConnect();
    }

    void update() override {
        loadData();
    }

private:
    Service& service;
    Contract& contract;
    QListWidget* listaContract = new QListWidget{};
    QLineEdit* nrAdaugare = new QLineEdit;
    QPushButton* butonGolireContract = new QPushButton{ "&Golește contractul" };
    QPushButton* butonAdaugareRandomInContract = new QPushButton{ "&Adaugă random în contract" };

    void initGUI() {
        const auto layoutPrincipalContract = new QVBoxLayout{};
        const auto layoutButoaneContractInchirieri = new QHBoxLayout{};
        const auto casetaAdaugareRandom = new QFormLayout;

        QLabel* textCosInchirieri = new QLabel("Contract de discipline:");

        casetaAdaugareRandom->addRow(nrAdaugare);
        nrAdaugare->setPlaceholderText("Contract number to add");
        nrAdaugare->setFixedWidth(185);

        layoutButoaneContractInchirieri->addWidget(butonAdaugareRandomInContract);
        layoutButoaneContractInchirieri->addWidget(butonGolireContract);

        setLayout(layoutPrincipalContract);

        layoutPrincipalContract->addWidget(textCosInchirieri);
        layoutPrincipalContract->addWidget(listaContract);
        layoutPrincipalContract->addLayout(layoutButoaneContractInchirieri);
        layoutPrincipalContract->addLayout(casetaAdaugareRandom);
    }

    void loadData() {
        listaContract->clear();
        for (const auto& disciplina : contract.getAll()) {
            QString item = QString::fromStdString(disciplina.getDenumire()) + " - " +
                           QString::fromStdString(disciplina.getTip()) + " - " +
                           QString::number(disciplina.getNrOre()) + " - " +
                           QString::fromStdString(disciplina.getCadruDidactic());
            listaContract->addItem(item);
        }
    }

    void initConnect() {
        contract.addObserver(this);
        connect(butonGolireContract, QPushButton::clicked, [&]() {
            service.golesteContractService();
            loadData();
            });

        connect(butonAdaugareRandomInContract, QPushButton::clicked, [&]() {
            if (nrAdaugare->text().toStdString() == "")
                QMessageBox::information(this, "Atenție!", "Introduceți un număr de discipline de generat!");

            const auto discipline=service.getAll();
            if (nrAdaugare->text().toInt() <= discipline.size())
                try {
                    contract.genereazaContract(std::stoi(nrAdaugare->text().toStdString()), discipline);
                    loadData();
                } catch (const ValidationError& e) {
                    QMessageBox::warning(this, "Validation Error", QString::fromStdString(e.getError()));
                } catch (const ServiceException& e) {
                    QMessageBox::warning(this, "Service Error", QString::fromStdString(e.getMsg()));
                }
            else
                QMessageBox::warning(this, "Atenție!", "Nu există suficiente discipline introduse!");
            });
    }

    ~ContractCRUDGUI() override {
        service.removeObserver(this);
    }
};