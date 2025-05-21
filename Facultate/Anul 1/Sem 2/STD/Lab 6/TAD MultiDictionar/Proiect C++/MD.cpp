#include "MD.h"
#include "IteratorMD.h"
#include <cmath>
#include <iostream>
#include <algorithm>
//Complexitate:Theta(1)
int MD::hashPrim(const TCheie c) const {
    return abs(c) % capacitate;
}
//Complexitate:Theta(1)
int MD::hashSecund(const TCheie c) const {
    return 1 + abs(c) % (capacitate - 1);
}
//Complexitate:Theta(1)
int MD::dubluHashing(TCheie c, int i) const {
    return (hashPrim(c) + i * hashSecund(c)) % capacitate;
}
//Complexitate:Theta(n)
MD::MD() {
    capacitate = INITIAL_CAPACITY;
    nrElemente = 0;
    tabela = new Entry[capacitate];
    for (int i = 0; i < capacitate; i++) {
        tabela[i].key = NULL_TCHEIE;
        tabela[i].occupied = false;
        tabela[i].deleted = false;
    }
}
//Complexitate:Theta(n)
void MD::redimensionare() {
    const int oldCap = capacitate;
    Entry* oldTabela = tabela;
    capacitate *= 2;
    tabela = new Entry[capacitate];

    for (int i = 0; i < capacitate; i++) {
        tabela[i].key = NULL_TCHEIE;
        tabela[i].occupied = false;
        tabela[i].deleted = false;
    }
    nrElemente = 0;
    for (int i = 0; i < oldCap; i++) {
        if (oldTabela[i].occupied) {
            for (const TValoare& v : oldTabela[i].values)
                adauga(oldTabela[i].key, v);
        }
    }
    delete[] oldTabela;
}
//Complexitate-best case:Theta(1),worst case:Theta(n),average case:Theta(1),Overall:O(n)
void MD::adauga(const TCheie c, const TValoare v) {
    int i = 0;
    int pos;
    while (true) {
        pos = dubluHashing(c, i);
        if (!tabela[pos].occupied || (tabela[pos].occupied && tabela[pos].key == c && !tabela[pos].deleted)) {
            break;
        }
        i++;
        if (i >= capacitate) {
            redimensionare();
            i = 0;
        }
    }

    if (!tabela[pos].occupied || tabela[pos].deleted) {
        tabela[pos].key = c;
        tabela[pos].values = vector<TValoare>{v};
        tabela[pos].occupied = true;
        tabela[pos].deleted = false;
    } else {
        tabela[pos].values.push_back(v);
    }
    nrElemente++;
}
//Complexitate:best case-Theta(1),worst case-Theta(n),average-Theta(1),overall-O(n)
vector<TValoare> MD::cauta(const TCheie c) const {
    int i=0;
    while (i<capacitate) {
        const int pos=dubluHashing(c,i);
        if (!tabela[pos].occupied && !tabela[pos].deleted) return {};
        if (tabela[pos].key==c && tabela[pos].occupied && !tabela[pos].deleted) {
            return tabela[pos].values;
        }
        i++;
    }
    return {};
}
//Complexitate-Best case:Theta(1),worst case:Theta(n),average case:Theta(1),overall:O(n)
bool MD::sterge(const TCheie c, const TValoare v) {
    int i = 0;
    while (i < capacitate) {
        const int pos = dubluHashing(c, i);
        if (!tabela[pos].occupied && !tabela[pos].deleted)
            return false;

        if (tabela[pos].key == c && !tabela[pos].deleted) {
            auto& vec = tabela[pos].values;
            if (auto it = ranges::find(vec, v); it != vec.end()) {
                vec.erase(it);
                nrElemente--;
                if (vec.empty()) {
                    tabela[pos].occupied = false;
                    tabela[pos].deleted = true;
                }
                return true;
            }
        }
        i++;
    }
    return false;
}

//Complexitate:Best case:Theta(m),worst case:Theta(n+m),average case:nu stiu,overall:O(n+m)
//Pseudocod:
/*
 *preconditii:md este un MD
 *postconditii:orice pereche (cheie, valoare) din md apartine multidictionarului curent
 *Functie adaugaInexistente(MD mdCurent,MD md)
 *  IteratorMD it<-iterator(md)
 *  count<-0
 *  cat timp valid(it) executa
 *      (cheie,valoare)<-element(it)
 *      ?? values<-cauta(mdCurent,cheie)
 *      daca empty(values) atunci
 *          adauga(mdCurent,cheie,valoare)
 *          count<-count+1
 *          urmator(it)
 *          continua
 *      sf.daca
 *      daca valoare nu apartine values executa
 *          adauga(mdCurent,cheie,valoare)
 *          count<-count+1
 *      sf.daca
 *      urmator(it)
 *  sf.cat timp
 *  adaugaInexistente<-count
 *sf.functie
 */

int MD::adaugaInexistente(const MD &md) {
    auto it=md.iterator();
    int count=0;
    while (it.valid()) {
        const auto [cheie, valoare]=it.element();
        if (const auto values=cauta(cheie); ranges::find(values, valoare) == values.end()) {
            adauga(cheie, valoare);
            count++;
        }
        it.urmator();
    }
    return count;
}
//Complexitate:Theta(1)
int MD::dim() const {
    return nrElemente;
}
//Complexitate:Theta(1)
bool MD::vid() const {
    return nrElemente == 0;
}
//Complexitate:Theta(1)
IteratorMD MD::iterator() const {
    return IteratorMD(*this);
}

MD::~MD() {
    delete[] tabela;
}
