#pragma once

typedef struct {
    char* tip;
    float suprafata;
    char* adresa;
    float pret;
}Oferta;

Oferta* createOferta(const char* adresa,float suprafata, const char* tip,float pret);
void destroyOferta(Oferta* o);
Oferta* copyOferta(const Oferta* o);
void testOferta();