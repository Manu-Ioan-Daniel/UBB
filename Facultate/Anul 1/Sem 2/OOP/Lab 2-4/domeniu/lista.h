//lista.h
#ifndef LISTA_H
#include "oferta.h"
#include <stdlib.h>
#include <assert.h>
#define LISTA_H
#define INIT_CAPACITY 100
typedef struct{
    int len;
    int capacitate;
    Oferta* oferte ;

}Lista;

void resizeList(Lista* l);
Lista* createList();
void destroyList(Lista* l);
void testList();
#endif //LISTA_H