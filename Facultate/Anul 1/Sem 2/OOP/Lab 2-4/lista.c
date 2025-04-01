//
// Created by Lenovo on 3/23/2025.
//

#include "lista.h"
#include "oferta.h"
#include <assert.h>
#include <stdlib.h>



List* createList(const DestroyFn destroyFn , const CopyFn copyFn) {
    List* l = malloc(sizeof(List));
    l->capacity=INIT_CAPACITY;
    l->elems=(TElem*)malloc(sizeof(TElem)*INIT_CAPACITY);
    l->length=0;
    l->copyFn=copyFn;
    l->destroyFn=destroyFn;
    return l;
}
void destroyList(List* l) {
    for (int i = 0; i < l->length; i++)
        l->destroyFn(getElem(l, i));
    free(l->elems);
    free(l);
}
void resizeList(List* l) {
    l->capacity*=2;
    TElem* aux = (TElem*)malloc(sizeof(TElem)*l->capacity);
    for (int i = 0; i < l->length; i++)
        aux[i]=l->elems[i];
    free(l->elems);
    l->elems=aux;
}
void addElem(List* l, TElem elem) {
    if (l->length==l->capacity)
        resizeList(l);
    l->elems[l->length]=elem;
    l->length++;

}
void deleteElem(List *l,int poz) {
    l->destroyFn(getElem(l,poz));
    for (int i = poz; i < l->length-1; i++) {
        l->elems[i]=l->elems[i+1];
    }
    l->length-=1;
}
void setElem(const List *l,int poz,TElem elem) {
    l->destroyFn(l->elems[poz]);
    l->elems[poz]=elem;
}
TElem getElem(const List* l,int poz) {
    return l->elems[poz];
}
int getLength(const List* l) {
    return l->length;

}
void testList() {
    List* l = createList(destroyOferta,copyOferta);
    Oferta* o1=createOferta("adresa1",1,"apartament",1);
    Oferta* o2=createOferta("adresa2",2,"casa",2);
    Oferta* o3=createOferta("adresa3",3,"casa",3);
    addElem(l,o1);
    assert(getLength(l)==1);
    assert(l->elems[0]==o1);
    addElem(l,o2);
    assert(getLength(l)==2);
    assert(l->elems[1]==o2);
    assert(getElem(l,0)==o1);
    assert(getElem(l,1)==o2);
    assert(l->length==2);
    setElem(l,1,o3);
    assert(l->elems[1]==o3);
    deleteElem(l,0);
    assert(getLength(l)==1);
    for (int i = 0;i<11;i++) {
        Oferta* o = createOferta("adresa1",(float)i,"apartament",1);
        addElem(l,o);
    }
    assert(l->capacity==20);
    destroyList(l);

}
