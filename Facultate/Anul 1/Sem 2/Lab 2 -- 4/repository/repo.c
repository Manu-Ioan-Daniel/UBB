//repo.c
#include "repo.h"
int adaugaOferta(Oferta o, Lista *l)
{
    if(l->len==l->capacitate)
        resizeList(l);
    for (int i=0;i<l->len;i++) {
        if (strcmp(l->oferte[i].adresa,o.adresa)==0){
            return REPO_ERROR;
        }
    }
    l->oferte[l->len]=o;
    l->len++;
    return SUCCES;
}

int cautaOferta(char adresa[],Lista *l)
{
    for(int i=0;i<l->len;i++)
        if(strcmp(l->oferte[i].adresa,adresa)==0)
            return i;
    return -1;
}
int modificaOferta(char adresa[],Oferta o,Lista *l)
{
    int poz = cautaOferta(adresa,l);
    if (poz==-1)
        return REPO_ERROR;
    l->oferte[poz]=o;
    return SUCCES;
}
int stergeOferta(char adresa[],Lista* l)
{
    int poz = cautaOferta(adresa,l);
    if (poz==-1)
        return REPO_ERROR;
    for(int i=poz;i<l->len-1;i++)
        l->oferte[i]=l->oferte[i+1];
    l->len--;
    return SUCCES;
     
}

//Teste

void testRepo() {
    Lista* l=createList();
    Oferta o;
    strcpy(o.adresa,"adresa");
    strcpy(o.tip,"tip");
    o.pret=1;
    o.suprafata=1;

    // test adauga oferta
    assert(adaugaOferta(o, l)==SUCCES);
    assert(l->len == 1);
    assert(strcmp(l->oferte[0].adresa, "adresa") == 0);
    assert(strcmp(l->oferte[0].tip, "tip") == 0);
    assert(l->oferte[0].pret == 1);
    assert(l->oferte[0].suprafata == 1);
    assert(adaugaOferta(o, l)==REPO_ERROR);
    for (int i = 0;i<100;i++) {
        o.pret=i;
        o.adresa[strlen(o.adresa)-2]=i;
        adaugaOferta(o,l);
    }
    destroyList(l);
    createList(l);
    strcpy(o.adresa,"adresa");
    o.pret=1;
    assert(adaugaOferta(o, l)==SUCCES);
    assert(l->len == 1);
    assert(strcmp(l->oferte[0].adresa, "adresa") == 0);
    assert(strcmp(l->oferte[0].tip, "tip") == 0);
    assert(l->oferte[0].pret == 1);
    assert(l->oferte[0].suprafata == 1);
    assert(adaugaOferta(o, l)==REPO_ERROR);
    // test modifica oferta
    Oferta o2;
    strcpy(o2.adresa, "adresa2");
    strcpy(o2.tip, "tip2");
    o2.pret = 2;
    o2.suprafata = 2;
    assert(modificaOferta("adresa", o2, l)==SUCCES);
    assert(l->len == 1);
    assert(strcmp(l->oferte[0].adresa, "adresa2") == 0);
    assert(strcmp(l->oferte[0].tip, "tip2") == 0);
    assert(l->oferte[0].pret == 2);
    assert(l->oferte[0].suprafata == 2);
    assert(modificaOferta("adresa3", o2, l)==REPO_ERROR);

    // test cauta oferta
    assert(cautaOferta("adresa2",l)!=-1);
    Oferta oferta = l->oferte[cautaOferta("adresa2", l)];
    assert(strcmp(oferta.adresa, "adresa2") == 0);
    assert(strcmp(oferta.tip, "tip2") == 0);
    assert(oferta.pret == 2);
    assert(oferta.suprafata == 2);
    assert(cautaOferta("adresa3", l)==-1);


    // test sterge oferta
    assert(stergeOferta("adresa2", l)==SUCCES);
    assert(l->len == 0);
    assert(stergeOferta("adresa2", l)==REPO_ERROR);
    destroyList(l);


}