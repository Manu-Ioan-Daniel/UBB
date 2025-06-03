#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;

bool rel(TElem e1, TElem e2) {
    return e1 <= e2;
}

Colectie::Colectie() {
    radacina = nullptr;
    dimensiune = 0;
}

void Colectie::adauga(TElem e) {
    Nod** p = &radacina;

    while (*p != nullptr) {
        if ((*p)->element == e) {
            (*p)->frecventa++;
            dimensiune++;
            return;
        } else if (rel(e, (*p)->element)) {
            p = &((*p)->stanga);
        } else {
            p = &((*p)->dreapta);
        }
    }

    *p = new Nod(e);
    dimensiune++;
}

bool Colectie::sterge(TElem e) {
    Nod** p = &radacina;
    while (*p != nullptr && (*p)->element != e) {
        if (rel(e, (*p)->element)) {
            p = &((*p)->stanga);
        } else {
            p = &((*p)->dreapta);
        }
    }

    if (*p == nullptr) return false;

    if ((*p)->frecventa > 1) {
        (*p)->frecventa--;
        dimensiune--;
        return true;
    }

    // Nodul trebuie șters
    Nod* toDelete = *p;
    if ((*p)->stanga == nullptr) {
        *p = (*p)->dreapta;
    } else if ((*p)->dreapta == nullptr) {
        *p = (*p)->stanga;
    } else {
        // 2 copii – înlocuim cu succesor
        Nod** succ = &((*p)->dreapta);
        while ((*succ)->stanga != nullptr) {
            succ = &((*succ)->stanga);
        }

        (*p)->element = (*succ)->element;
        (*p)->frecventa = (*succ)->frecventa;

        Nod* tmp = *succ;
        *succ = (*succ)->dreapta;
        delete tmp;
        dimensiune--;
        return true;
    }

    delete toDelete;
    dimensiune--;
    return true;
}

bool Colectie::cauta(TElem e) const {
    Nod* p = radacina;
    while (p != nullptr) {
        if (p->element == e) return true;
        if (rel(e, p->element)) p = p->stanga;
        else p = p->dreapta;
    }
    return false;
}

int Colectie::nrAparitii(TElem e) const {
    Nod* p = radacina;
    while (p != nullptr) {
        if (p->element == e) return p->frecventa;
        if (rel(e, p->element)) p = p->stanga;
        else p = p->dreapta;
    }
    return 0;
}

int Colectie::dim() const {
    return dimensiune;
}

bool Colectie::vida() const {
    return dimensiune == 0;
}

IteratorColectie Colectie::iterator() const {
    return IteratorColectie(*this);
}

void distruge(Nod* p) {
    if (p == nullptr) return;
    distruge(p->stanga);
    distruge(p->dreapta);
    delete p;
}

Colectie::~Colectie() {
    distruge(radacina);
}
