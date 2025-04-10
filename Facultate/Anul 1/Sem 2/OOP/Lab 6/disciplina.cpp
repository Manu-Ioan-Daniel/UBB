
#include "disciplina.h"

#include <cassert>

int Disciplina::getNrOre() const {
    return nrOre;
}
string Disciplina::getDenumire() const {
    return denumire;
}
string Disciplina::getTip() const {
    return tip;
}
string Disciplina::getCadruDidactic() const {
    return cadruDidactic;
}
void testDisciplina(){
    Disciplina d1={"mate",5,"laborator","popescu"};
    assert(d1.getDenumire()=="mate");
    assert(d1.getNrOre()==5);
    assert(d1.getTip()=="laborator");
    assert(d1.getCadruDidactic()=="popescu");
}

