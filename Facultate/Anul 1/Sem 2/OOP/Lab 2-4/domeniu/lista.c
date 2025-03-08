#include "lista.h"
void resizeList(Lista* l){
    
    l->capacitate*=2;
    Oferta* aux=malloc(l->capacitate*sizeof(Oferta));
    for(int i=0;i<l->len;i++)
        aux[i]=l->oferte[i];
    free(l->oferte);
    l->oferte=aux;
}
void createList(Lista* l){
    l->len=0;
    l->capacitate=INIT_CAPACITY;
    l->oferte=malloc(l->capacitate*sizeof(Oferta));
}
void destroyList(Lista* l)
{
    free(l->oferte);
    l->oferte=NULL;
    l->len=0;
    l->capacitate=0;
}


//Teste

void testList() {
    Lista l;
    createList(&l);
    assert(l.capacitate == INIT_CAPACITY);
    assert(l.len==0);
    resizeList(&l);
    assert(l.capacitate == 2*INIT_CAPACITY);
    destroyList(&l);
    assert (l.capacitate==0);
    assert(l.len==0);
    assert(l.oferte==NULL);
}