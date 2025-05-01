#pragma once
#include "disciplina.h"
#include "Repo.h"

class ActiuneUndo {
public:
    virtual void doUndo()=0;
    virtual ~ActiuneUndo()=default;

};

class UndoAdauga final:public ActiuneUndo {
private:
    Disciplina disciplinaAdaugata;
    Repo& repoDiscipline;
public:
    UndoAdauga(Repo& r,const Disciplina& d):disciplinaAdaugata(d),repoDiscipline(r){}
    void doUndo() override {
        repoDiscipline.stergeDisciplina(disciplinaAdaugata.getDenumire(),disciplinaAdaugata.getTip());
    }
};

class UndoSterge final :public ActiuneUndo {
    private:
    Repo& repoDiscipline;
    Disciplina disciplinaStearsa;
    public:
    UndoSterge(Repo& r, const Disciplina& d):repoDiscipline(r),disciplinaStearsa(d){}
    void doUndo() override {
        repoDiscipline.addDisciplina(disciplinaStearsa);
    }
};

class UndoModifica final :public ActiuneUndo {
private:
    Disciplina dVeche,dNoua;
    Repo& repoDiscipline;
public:
    UndoModifica(Repo& r, const Disciplina& dN, const Disciplina& dV):dVeche(dV),dNoua(dN),repoDiscipline(r){}
    void doUndo() override {
        repoDiscipline.modificaDisciplina(dVeche,dNoua);
    }
};