//
// Created by Lenovo on 3/23/2025.
//

#include "repo.h"

#include <assert.h>
#include <stdlib.h>
#include <string.h>

Repo* createRepo() {
    Repo* r = malloc(sizeof(Repo));
    r->oferte=createList(destroyOferta,copyOferta);
    return r;
}
void destroyRepo(Repo* repo) {
    for (int i = 0;i<getLength(repo->oferte);i++)
        destroyOferta(getElem(repo->oferte,i));
    destroyList(repo->oferte);
    free(repo);


}
int cautaOferta(Repo* repo,char* adresa) {
    for (int i = 0;i<getLength(repo->oferte);i++) {
        Oferta* o = getElem(repo->oferte,i);
        if (strcmp(o->adresa,adresa)==0) {
            return i;
        }
    }
    return -1;

}
int adaugaOferta(Repo* repo,Oferta* o) {

    int poz =cautaOferta(repo,o->adresa);
    if (poz!=-1) {
        return REPO_ERROR;
    }
    addElem(repo->oferte,o);
    return SUCCES;
}
int stergeOferta(Repo* repo,char* adresa) {
    int poz = cautaOferta(repo,adresa);
    if (poz==-1) {
        return REPO_ERROR;
    }
    deleteElem(repo->oferte,poz);
    return SUCCES;
}
int modificaOferta(Repo* repo,char* adresa,Oferta* o) {
    int poz = cautaOferta(repo,adresa);
    if (poz==-1) {
        return REPO_ERROR;
    }
    setElem(repo->oferte,poz,o);
    return SUCCES;
}
List* getOferte(Repo* repo) {
    return repo->oferte;
}
void testRepo() {
    Oferta* o1=createOferta("adresa1",1,"casa",1);
    Oferta* o2=createOferta("adresa2",2,"casa",2);
    Oferta* o3=createOferta("adresa3",3,"casa",3);
    Repo* repo=createRepo();

    //test adauga oferta

    assert(adaugaOferta(repo,o1)==SUCCES);
    assert(getLength(repo->oferte)==1);
    assert(getElem(repo->oferte,0)==o1);
    assert(adaugaOferta(repo,o2)==SUCCES);
    assert(getLength(repo->oferte)==2);
    assert(getElem(repo->oferte,1)==o2);
    assert(adaugaOferta(repo,o1)==REPO_ERROR);

    //test modifica oferta

    assert(modificaOferta(repo,"adresaadad",o1)==REPO_ERROR);
    assert(modificaOferta(repo,o1->adresa,o3)==SUCCES);
    o1=createOferta("adresa1",1,"casa",1);
    assert(getElem(repo->oferte,0)==o3);

    //test sterge oferta

    assert(stergeOferta(repo,o1)==REPO_ERROR);
    assert(stergeOferta(repo,o3->adresa)==SUCCES);
    assert(getLength(repo->oferte)==1);
    o3=createOferta("adresa3",3,"casa",3);
    assert(cautaOferta(repo,o3->adresa)==-1);
    destroyRepo(repo);
    destroyOferta(o1);
    destroyOferta(o3);
}