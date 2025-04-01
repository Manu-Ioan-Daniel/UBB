//
// Created by Deny on 23-03-2025.
//

#include "service.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "validator.h"

Service* createService() {
    Service* service=malloc(sizeof(Service));
    service->repo=createRepo();
    return service;
}
void destroyService(Service* service) {
    destroyRepo(service->repo);
    free(service);
}
int adaugaOfertaService(Service* s, char* adresa, float pret, char* tip, float suprafata) {
    Oferta* o=createOferta(adresa,suprafata,tip,pret);
    Errors errors=valideazaOferta(o);
    if (errors!=SUCCES) {
        destroyOferta(o);
        return errors;
    }
    if (adaugaOferta(s->repo,o)!=SUCCES) {
        destroyOferta(o);
        return REPO_ERROR;
    }
    return SUCCES;
}
int stergeOfertaService(Service* s, char* adresa) {
    if (strcmp(adresa,"")==0)
        return ADRESA_INVALIDA;
    if (stergeOferta(s->repo,adresa)!=SUCCES)
        return REPO_ERROR;
    return SUCCES;
}
int modificaOfertaService(Service* s, char* adresa, char* adresaNoua, float suprafataNoua, char* tipNou, float pretNou) {
    Oferta* o=createOferta(adresaNoua,suprafataNoua,tipNou,pretNou);
    if (strcmp(adresa,"")==0) {
        destroyOferta(o);
        return ADRESA_INVALIDA;
    }
    Errors errors=valideazaOferta(o);
    if (errors!=SUCCES) {
        destroyOferta(o);
        return errors;
    }
    if (modificaOferta(s->repo,adresa,o)!=SUCCES) {
        destroyOferta(o);
        return REPO_ERROR;
    }
    return SUCCES;
}
List* filtrareDupaSuprafata(Service* s, float suprafata) {
    List* rez=createList(destroyOferta,copyOferta);
    List* all = get_all(s);
    if (getLength(all)==0) {
        destroyList(rez);
        return NULL;
    }
    for (int i = 0;i<getLength(all);i++) {
        Oferta* o = copyOferta(getElem(all,i));
        if (o->suprafata<=suprafata) {
            addElem(rez,o);
        }else {
            destroyOferta(o);
        }
    }
    return rez;
}
List* filtrareDupaTip(Service* s, char* tip) {
    List* rez=createList(destroyOferta,copyOferta);
    List* all = get_all(s);
    if (getLength(all)==0) {
        destroyList(rez);
        return NULL;
    }
    for (int i = 0;i<getLength(all);i++) {
        Oferta* o = copyOferta(getElem(all,i));
        if (strcmp(o->tip,tip)==0) {
            addElem(rez,o);
        }else {
            destroyOferta(o);
        }
    }
    return rez;
}
List* filtrareDupaPret(Service* s,float pret) {
    List* rez=createList(destroyOferta,copyOferta);\
    List* all=get_all(s);
    if (getLength(all)==0) {
        destroyList(rez);
        return NULL;
    }
    for (int i = 0;i<getLength(all);i++) {
        Oferta* o = copyOferta(getElem(all,i));
        if (o->pret>=pret) {
            addElem(rez,o);
        }else {
            destroyOferta(o);
        }
    }
    return rez;
}
int compareOffers(const void* o1, const void* o2) {
    Oferta* of1 = *(Oferta**)o1;
    Oferta* of2 = *(Oferta**)o2;

    if (of1->pret < of2->pret)
        return -1;
    if (of1->pret > of2->pret)
        return 1;
    int tipCmp = strcmp(of1->tip, of2->tip);
    if (tipCmp != 0)
        return tipCmp;
    return 0;
}
int compareOffersReverse(const void* o1,const void* o2) {
    Oferta* of1 = *(Oferta**)o1;
    Oferta* of2 = *(Oferta**)o2;

    if (of1->pret<of2->pret)
        return 1;
    if (of1->pret>of2->pret)
        return -1;
    if (strcmp(of1->tip,of2->tip)<0)
        return 1;
    return -1;
}
List* sortare(Service* s, int reverse) {
    if (getLength(get_all(s))==0)
        return NULL;
    List* rez=createList(destroyOferta,copyOferta);
    for (int i = 0;i<getLength(get_all(s));i++) {
        Oferta* o = copyOferta(getElem(get_all(s),i));
        addElem(rez,o);

    }
    if (reverse) {
        qsort(rez->elems,rez->length,sizeof(Oferta*),compareOffersReverse);

    }else
        qsort(rez->elems,rez->length,sizeof(Oferta*),compareOffers);
    return rez;
}

List* get_all(Service* service){
    return service->repo->oferte;
}


void testeService() {

    Service* s=createService();
    Oferta* aux;

    //test adaugaOfertaService

    assert(adaugaOfertaService(s,"adresa1",1,"casa",1)==SUCCES);
    assert(adaugaOfertaService(s,"adresa2",2,"casa",2)==SUCCES);
    assert(adaugaOfertaService(s,"adresa3",3,"casa",3)==SUCCES);
    assert(adaugaOfertaService(s,"adresa1",1,"casa",1)==REPO_ERROR);
    assert(adaugaOfertaService(s,"adresa1",-1,"casa",1)==PRET_INVALID);

    //test modificaOfertaService

    assert(modificaOfertaService(s,"adresa7","adresa1",1,"casa",1)==REPO_ERROR);
    assert(modificaOfertaService(s,"adresa2","adresa1",1,"casa",1)==REPO_ERROR);
    assert(modificaOfertaService(s,"","adresa1",1,"casa",1)==ADRESA_INVALIDA);
    assert(modificaOfertaService(s,"adresa1","",-1,"casa",1)==ADRESA_INVALIDA+SUPRAFATA_INVALIDA);
    assert(modificaOfertaService(s,"adresa2","adresa5",1,"casa",1)==SUCCES);
    List* all = get_all(s);
    assert(getLength(all)==3);
    aux=getElem(all,1);
    assert(strcmp(aux->adresa,"adresa5")==0);
    destroyService(s);
    s=createService();
    adaugaOfertaService(s,"adresa1",1,"casa",1);
    adaugaOfertaService(s,"adresa2",2,"casa",2);
    adaugaOfertaService(s,"adresa3",3,"casa",3);

    //test stergeOfertaService

    assert(stergeOfertaService(s,"adresa7")==REPO_ERROR);
    assert(stergeOfertaService(s,"")==ADRESA_INVALIDA);
    assert(stergeOfertaService(s,"adresa1")==SUCCES);
    assert(cautaOferta(s->repo,"adresa1")==-1);
    assert(getLength(get_all(s))==2);

    //test filtre

    adaugaOfertaService(s,"adresa1",1,"apartament",1);
    List* filtrate=filtrareDupaSuprafata(s,1);
    assert(getLength(filtrate)==1);
    Oferta* o1=getElem(filtrate,0);
    assert(strcmp(o1->adresa,"adresa1")==0);
    destroyList(filtrate);
    filtrate=filtrareDupaTip(s,"casa");
    assert(getLength(filtrate)==2);
    o1=getElem(filtrate,0);
    Oferta* o2=getElem(filtrate,1);
    assert(strcmp(o1->adresa,"adresa2")==0);
    assert(strcmp(o2->adresa,"adresa3")==0);
    destroyList(filtrate);
    filtrate=filtrareDupaPret(s,3);
    assert(getLength(filtrate)==1);
    o1=getElem(filtrate,0);
    assert(strcmp(o1->adresa,"adresa3")==0);
    destroyList(filtrate);
    destroyService(s);
    s=createService();
    assert(filtrareDupaSuprafata(s,1)==NULL);
    assert(filtrareDupaTip(s,"casa")==NULL);
    assert(filtrareDupaPret(s,1)==NULL);
    destroyService(s);

    //test sortare

    s=createService();
    adaugaOfertaService(s,"adresa2",2,"apartament",2);
    adaugaOfertaService(s,"adresa1",1,"apartament",1);
    adaugaOfertaService(s,"adresa3",3,"apartament",3);

    List* sortate=sortare(s,0);
    assert(getLength(sortate)==3);
    o1=getElem(sortate,0);
    o2=getElem(sortate,1);
    Oferta* o3=getElem(sortate,2);
    assert(strcmp(o1->adresa,"adresa1")==0);
    assert(strcmp(o2->adresa,"adresa2")==0);
    assert(strcmp(o3->adresa,"adresa3")==0);
    destroyList(sortate);
    sortate=sortare(s,1);
     assert(getLength(sortate)==3);
     o1=getElem(sortate,0);
     o2=getElem(sortate,1);
     o3=getElem(sortate,2);
     assert(strcmp(o1->adresa,"adresa3")==0);
     assert(strcmp(o2->adresa,"adresa2")==0);
     assert(strcmp(o3->adresa,"adresa1")==0);
     destroyList(sortate);
     destroyService(s);
     s=createService();
     assert(sortare(s,0)==NULL);
     adaugaOfertaService(s,"adresa1",1,"apartament",1);
     adaugaOfertaService(s,"adresa2",1,"casa",1);
     adaugaOfertaService(s,"adresa3",1,"casa",1);
     sortate=sortare(s,0);
     assert(getLength(sortate)==3);
     o1=getElem(sortate,0);
     o2=getElem(sortate,1);
     o3=getElem(sortate,2);
     assert(strcmp(o1->adresa,"adresa1")==0);
     assert(strcmp(o2->adresa,"adresa2")==0);
     assert(strcmp(o3->adresa,"adresa3")==0);
     destroyList(sortate);
     sortate=sortare(s,1);
     assert(getLength(sortate)==3);
     o1=getElem(sortate,0);
     o2=getElem(sortate,1);
     o3=getElem(sortate,2);
     assert(strcmp(o1->adresa,"adresa2")==0);
     assert(strcmp(o2->adresa,"adresa3")==0);
     assert(strcmp(o3->adresa,"adresa1")==0);
     destroyList(sortate);
    destroyService(s);

}