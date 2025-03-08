#ifndef SERVICE_H
#define SERVICE_H
#include "../repository/repo.h"
#include "../validator/validator.h"
#include <string.h>
#include <assert.h>

void adaugaOfertaService(char[],float,char[],float,Lista*);
void stergeOfertaService(char[],Lista*);
void modificaOfertaService(char[],char[],float,char[],float,Lista*);
Lista* filtrareDupaSuprafata(float,Lista*);
Lista* filtrareDupaTip(char[],Lista*);
Lista* filtrareDupaPret(float,Lista*);
void testeService();
Lista* sortare(Lista*,int);
#endif //SERVICE_H