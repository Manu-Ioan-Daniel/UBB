#pragma once
#include "lista.h"
#include "errors.h"
#include "oferta.h"
typedef struct {
    List* oferte;
}Repo;
Repo* createRepo();
void destroyRepo();
int adaugaOferta(const Repo*,Oferta* o);
int  stergeOferta(const Repo*, const char* adresa);
int  modificaOferta(const Repo*, const char* adresa,Oferta* o);
int cautaOferta(const Repo*, const char* adresa);
List* getOferte(const Repo*);
void testRepo();

