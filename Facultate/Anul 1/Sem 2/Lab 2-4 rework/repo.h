#pragma once
#include "lista.h"
#include "errors.h"
#include "oferta.h"
typedef struct {
    List* oferte;
}Repo;
Repo* createRepo();
void destroyRepo();
int adaugaOferta(Repo*,Oferta* o);
int  stergeOferta(Repo*,char* adresa);
int  modificaOferta(Repo*,char* adresa,Oferta* o);
int cautaOferta(Repo*,char* adresa);
List* getOferte(Repo*);
void testRepo();

