//repo.c
#include "repo.h"
void adaugaOferta(Oferta o, Lista *l)
{
    if(l->len==l->capacitate)
        resizeList(l);
    for (int i=0;i<l->len;i++) {
        if (strcmp(l->oferte[i].adresa,o.adresa)==0) {
            addRepoError("Exista deja o oferta cu aceasta adresa.\n");
            return;
        }
    }
    l->oferte[l->len]=o;
    l->len++;
}

int cautaOferta(char adresa[],Lista *l)
{
    for(int i=0;i<l->len;i++)
        if(strcmp(l->oferte[i].adresa,adresa)==0)
            return i;
    addRepoError("Nu exista oferta cu adresa specificata.\n");
    return -1;
}
void modificaOferta(char adresa[],Oferta o,Lista *l)
{
    int poz = cautaOferta(adresa,l);
    if (poz==-1)
        return;
    l->oferte[poz]=o;
}
void stergeOferta(char adresa[],Lista* l)
{
    int poz = cautaOferta(adresa,l);
    for(int i=poz;i<l->len-1;i++)
        l->oferte[i]=l->oferte[i+1];
    l->len--;
     
}
void testRepo() {
    Lista l;
    createList(&l);
    Oferta o;
    strcpy(o.adresa,"adresa");
    strcpy(o.tip,"tip");
    o.pret=1;
    o.suprafata=1;

    //test adauga oferta

    adaugaOferta(o,&l);
    assert(l.len==1);
    assert(strcmp(l.oferte[0].adresa,"adresa")==0);
    assert(strcmp(l.oferte[0].tip,"tip")==0);
    assert(l.oferte[0].pret==1);
    assert(l.oferte[0].suprafata==1);
    adaugaOferta(o,&l);
    assert(strcmp(RepoError,"Exista deja o oferta cu aceasta adresa.\n")==0);
    clearRepoError();
    //test modifica oferta

    Oferta o2;
    strcpy(o2.adresa,"adresa2");
    strcpy(o2.tip,"tip2");
    o2.pret=2;
    o2.suprafata=2;
    modificaOferta("adresa",o2,&l);
    assert(l.len==1);
    assert(strcmp(l.oferte[0].adresa,"adresa2")==0);
    assert(strcmp(l.oferte[0].tip,"tip2")==0);
    assert(l.oferte[0].pret==2);
    assert(l.oferte[0].suprafata==2);
    modificaOferta("adresa3",o2,&l);
    assert(strcmp(RepoError,"Nu exista oferta cu adresa specificata.\n")==0);
    clearRepoError();


    //test cauta oferta

    Oferta oferta = l.oferte[cautaOferta("adresa2",&l)];
    assert(strcmp(oferta.adresa,"adresa2")==0);
    assert(strcmp(oferta.tip,"tip2")==0);
    assert(oferta.pret==2);
    assert(oferta.suprafata==2);
    cautaOferta("adresa3",&l);
    assert(strcmp(RepoError,"Nu exista oferta cu adresa specificata.\n")==0);
    clearRepoError();

    //test sterge oferta

    stergeOferta("adresa2",&l);
    assert(l.len==0);
    destroyList(&l);



}