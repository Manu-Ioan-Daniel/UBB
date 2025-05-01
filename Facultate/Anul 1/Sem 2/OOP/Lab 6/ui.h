#pragma once
#include "service.h"


class Ui {
    Service& service;
    static void readInteger(int &x,const string& msg);
    void adaugaDisciplina() const;
    void undo() const;
    void modificaDisciplina() const;

    void stergeDisciplina() const;
    void cautaDisciplina() const;
    void afiseazaDiscipline() const;
public:

    explicit Ui(Service& service):service{service}{}
    void startUi() const;


};


