//
// Created by Lenovo on 3/23/2025.
//

#include "oferta.h"

#include <assert.h>
#include <string.h>
#include <stdlib.h>
Oferta* createOferta(const char* adresa,float suprafata, const char* tip, const float pret) {
    Oferta* o = malloc(sizeof(Oferta));
    o->adresa=malloc(strlen(adresa)+1);
    strcpy(o->adresa,adresa);
    o->suprafata=suprafata;
    o->tip=malloc(strlen(tip)+1);
    strcpy(o->tip,tip);
    o->pret=pret;
    return o;
}
void destroyOferta(Oferta* o) {
    free(o->adresa);
    free(o->tip);
    free(o);
}
Oferta* copyOferta(const Oferta* o) {
    Oferta* o2 = (Oferta*)malloc(sizeof(Oferta));
    o2->adresa=malloc(strlen(o->adresa)+1);
    strcpy(o2->adresa,o->adresa);
    o2->tip=malloc(strlen(o->tip)+1);
    strcpy(o2->tip,o->tip);
    o2->pret=o->pret;
    o2->suprafata=o->suprafata;
    return o2;
}
void testOferta() {
    Oferta* o1=createOferta("adresa1",1,"apartament",1);
    assert(strcmp(o1->adresa,"adresa1")==0 && strcmp(o1->tip,"apartament")==0 && o1->pret==1 && o1->suprafata==1);
    Oferta* o2=copyOferta(o1);
    assert(strcmp(o2->adresa,"adresa1")==0 && strcmp(o2->tip,"apartament")==0 && o2->pret==1 && o2->suprafata==1);
    destroyOferta(o1);
    destroyOferta(o2);

}