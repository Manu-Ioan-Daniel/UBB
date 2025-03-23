#include "../domeniu/lista.h"

#ifndef REPO_H
#define REPO_H
#include <string.h>
#include <assert.h>
#include <stdio.h>
#include "../errors/errors.h"
#include "../domeniu/oferta.h"

///
/// @param o oferta pe care o adaugam in  lista
/// @param l lista de oferte
/// @brief functie care adauga o oferta in lista de oferte
int adaugaOferta(Oferta o,Lista* l);

///
/// @param adresa adresa ofertei pe care o stergem,string nevid
/// @param l o lista de oferte
/// @brief functie care sterge o oferta din lista de oferte
int stergeOferta(char adresa[],Lista* l);
int modificaOferta(char adresa[],Oferta,Lista* l);
int cautaOferta(char adresa[],Lista* l);
void testRepo();
#endif //REPO_H