#include "lista.h"

#include <string.h>

void resizeList(Lista* l){
    
    l->capacitate*=2;
    Oferta* aux=malloc(l->capacitate*sizeof(Oferta));
    for(int i=0;i<l->len;i++)
        aux[i]=l->oferte[i];
    free(l->oferte);
    l->oferte=aux;
}
Lista* createList(){
    Lista* l=malloc(sizeof(Lista));
    l->len=0;
    l->capacitate=INIT_CAPACITY;
    l->oferte=malloc(l->capacitate*sizeof(Oferta));
    return l;
}
void destroyList(Lista* l)
{
    free(l->oferte);
    free(l);
}


//Teste

void testList() {
    Lista* l=createList();
    assert(l->capacitate == INIT_CAPACITY);
    assert(l->len==0);
    for (int i = 0;i<10;i++) {
        l->oferte[i].pret=i;
        l->oferte[i].suprafata=i;
        strcat(l->oferte[i].tip,"apartament");
        strcat(l->oferte[i].adresa,"mama");
        l->len+=1;
    }
    resizeList(l);
    assert(l->capacitate == 2*INIT_CAPACITY);
    destroyList(l);
}