#pragma once

typedef struct {
    char* tip;
    float suprafata;
    char* adresa;
    float pret;
}Oferta;

Oferta* createOferta(char* adresa,float suprafata, char* tip,float pret);
void destroyOferta(Oferta* o);
Oferta* copyOferta(Oferta* o);
void testOferta();