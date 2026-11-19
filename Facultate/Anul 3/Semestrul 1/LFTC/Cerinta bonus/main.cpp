#include "AutomatFinit.h"
#include <iostream>

using namespace std;

int main() {
    StateMachine af;
    if (!af.citesteDinFisier("automat.txt") && !af.citesteDinFisier("../automat.txt")) {
        cout << "eroare deschidere fisier automat.txt\n";
        return 1;
    }

    af.afiseazaStari();
    af.afiseazaAlfabet();
    af.afiseazaStariFinale();
    af.afiseazaTranzitii();

    return 0;
}
