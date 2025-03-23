#pragma once
#include "repo.h"
typedef struct {
    Repo* repo;

} Service;

Service* createService();
void destroyService(Service* service);
int adaugaOfertaService(Service* service, char* adresa, float pret, char* tip, float suprafata);
int stergeOfertaService(Service* service, char* adresa);
int modificaOfertaService(Service* service,char* adresa,char* adresaNoua,float suprafataNoua,char* tipNou,float pretNou);
List* filtrareDupaSuprafata(Service* service,float suprafata);
List* filtrareDupaTip(Service* service,char* tip);
List* filtrareDupaPret(Service* service,float pret);
List* sortare(Service* service,int reverse);
List* get_all( Service* service);
void testeService();
int compareOffers(const void* o1,const void* o2);
int compareOffersReverse(const void* o1,const void* o2);

