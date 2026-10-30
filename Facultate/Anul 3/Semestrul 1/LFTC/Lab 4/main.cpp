#include "AutomatFinit.h"
#include <iostream>

using namespace std;

void meniu() {
    cout << "\n1. Citire din fisier\n"
         << "2. Citire de la tastatura\n"
         << "3. Afiseaza stari\n"
         << "4. Afiseaza alfabet\n"
         << "5. Afiseaza tranzitii\n"
         << "6. Afiseaza stari finale\n"
         << "7. Afiseaza stare initiala\n"
         << "8. Verifica determinism\n"
         << "9. Verifica secventa\n"
         << "10. Cel mai lung prefix\n"
         << "0. Iesire\n"
         << "optiune: ";
}

int main(int argc, char* argv[]) {
    AutomatFinit af;
    string fisier = argc > 1 ? argv[1] : "constante_intregi.txt";
    af.citesteDinFisier(fisier);

    int opt;
    while (meniu(), cin >> opt && opt != 0) {
        if (opt == 1) {
            cout << "fisier: ";
            cin >> fisier;
            if (af.citesteDinFisier(fisier)) cout << "incarcat cu succes\n";
            else cout << "eroare deschidere fisier\n";
        } else if (opt == 2) {
            af.citesteDeLaTastatura();
        } else if (opt == 3) {
            af.afiseazaStari();
        } else if (opt == 4) {
            af.afiseazaAlfabet();
        } else if (opt == 5) {
            af.afiseazaTranzitii();
        } else if (opt == 6) {
            af.afiseazaStariFinale();
        } else if (opt == 7) {
            af.afiseazaStareInitiala();
        } else if (opt == 8) {
            cout << (af.esteDeterminist() ? "este AFD\n" : "este AFN (nedeterminist)\n");
        } else if (opt == 9) {
            if (!af.esteDeterminist()) {
                cout << "automatul nu este determinist\n";
            } else {
                string w;
                cout << "secventa: ";
                cin >> w;
                cout << (af.verificaSecventa(w) ? "acceptata\n" : "neacceptata\n");
            }
        } else if (opt == 10) {
            if (!af.esteDeterminist()) {
                cout << "automatul nu este determinist\n";
            } else {
                string w;
                cout << "secventa: ";
                cin >> w;
                auto [ok, pref] = af.celMaiLungPrefix(w);
                if (!ok) cout << "niciun prefix acceptat\n";
                else cout << "prefix acceptat: " << pref << "\n";
            }
        }
    }
    return 0;
}
