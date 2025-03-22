#ifndef SERVICE_H
#define SERVICE_H
#include "../repository/repo.h"
#include "../validator/validator.h"
#include <string.h>
#include <assert.h>

///
/// @param adresa - adresa ofertei pe care o adaugam,string nevid
/// @param supfrafata  - suprafata ofertei pe care o adaugam, float
/// @param tip - tipul ofertei,poate fi "casa","apartament" sau "teren"
/// @param pret - pretul ofertei, float
/// @param l -lista de oferte
/// @brief functie care adauga o oferta in lista de oferte
void adaugaOfertaService(char adresa[],float supfrafata,char tip[],float pret,Lista* l) ;

///
/// @param adresa - adresa ofertei pe care o stergem,string
/// @param l -lista de oferte
/// @brief functie care sterge o oferta din lista de oferte
void stergeOfertaService(char adresa[],Lista* l);
void modificaOfertaService(char[],char[],float,char[],float,Lista*);
Lista* filtrareDupaSuprafata(float,Lista*);
Lista* filtrareDupaTip(char[],Lista*);
Lista* filtrareDupaPret(float,Lista*);
void testeService();
Lista* sortare(Lista*,int);
#endif //SERVICE_H