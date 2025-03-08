#include "service.h"


void adaugaOfertaService(char adresa[],float pret,char tip[],float suprafata,Lista* l)
{
    Oferta o;
    strcpy(o.adresa,adresa);
    o.pret=pret;
    strcpy(o.tip,tip);
    o.suprafata=suprafata;
    if (valideazaOferta(o)==0)
        return;
    adaugaOferta(o,l);

    
}


void stergeOfertaService(char adresa[],Lista* l)
{
    Oferta o;
    strcpy(o.adresa,adresa);
    strcpy(o.tip,"apartament");
    o.pret=0;
    o.suprafata=0;
    if (valideazaOferta(o) == 0)
        return;
    stergeOferta(adresa,l);
}


void modificaOfertaService(char adresa[],char adresaNoua[],float suprafataNoua,char tipNou[],float pretNou,Lista* l)
{
    Oferta o;
    strcpy(o.adresa,adresaNoua);
    o.pret=pretNou;
    strcpy(o.tip,tipNou);
    o.suprafata=suprafataNoua;
    if (valideazaOferta(o) == 0)
        return;
    modificaOferta(adresa,o,l);
}


Lista* sortare(Lista *l, int reverse) {

    Lista* rez = malloc(sizeof(Lista));
    createList(rez);
    for (int i = 0; i < l->len; i++)
        adaugaOferta(l->oferte[i], rez);

    for (int i = 0; i < rez->len - 1; i++) {
        for (int j = i + 1; j < rez->len; j++) {
            int cmp = (reverse) ? (rez->oferte[i].pret < rez->oferte[j].pret) : (rez->oferte[i].pret > rez->oferte[j].pret);
            if (cmp) {
                Oferta aux = rez->oferte[i];
                rez->oferte[i] = rez->oferte[j];
                rez->oferte[j] = aux;
            } else if (rez->oferte[i].pret == rez->oferte[j].pret) {
                cmp = (reverse) ? (strcmp(rez->oferte[i].tip, rez->oferte[j].tip) < 0) : (strcmp(rez->oferte[i].tip, rez->oferte[j].tip) > 0);
                if (cmp) {
                    Oferta aux = rez->oferte[i];
                    rez->oferte[i] = rez->oferte[j];
                    rez->oferte[j] = aux;
                }
            }
        }
    }
    return rez;
}
Lista* filtrareDupaPret(float pret,Lista* l) {
    Lista* rez = malloc(sizeof(Lista));
    createList(rez);
    if (pret<0) {
        addValidationError("Pret invalid\n");
        return rez;
    }
    for (int i = 0; i < l->len; i++)
        if (l->oferte[i].pret>=pret)

            adaugaOferta(l->oferte[i], rez);
    return rez;

}
Lista* filtrareDupaSuprafata(float suprafata,Lista* l) {
    Lista* rez = malloc(sizeof(Lista));
    createList(rez);
    if (suprafata<0) {
        addValidationError("Suprafata invalida\n");
        return rez;
    }
    for (int i = 0; i < l->len; i++)
        if (l->oferte[i].suprafata>=suprafata)
            adaugaOferta(l->oferte[i], rez);
    return rez;
}
Lista* filtrareDupaTip(char tip[],Lista* l) {
    Lista* rez = malloc(sizeof(Lista));
    createList(rez);
    if (strcmp(tip,"casa")!=0 && strcmp(tip,"apartament")!=0 && strcmp(tip,"teren")!=0) {
        addValidationError("Tip invalid\n");
        return rez;

    }
    for (int i = 0; i < l->len; i++) {
        if (strcmp(l->oferte[i].tip,tip)==0) {
            adaugaOferta(l->oferte[i], rez);
        }
    }
    return rez;

}

//Teste

void testeService() {
    Oferta o1,o2,o3;
    o1.pret=2;
    o1.suprafata=2;
    strcpy(o1.tip,"apartament");
    strcpy(o1.adresa,"adresa1");
    o2.pret=4;
    o2.suprafata=1;
    strcpy(o2.tip,"casa");
    strcpy(o2.adresa,"adresa2");
    o3.pret=1;
    o3.suprafata=3;
    strcpy(o3.tip,"teren");
    strcpy(o3.adresa,"adresa3");
    Lista* l=malloc(sizeof(Lista));
    createList(l);

    //test adaugaOfertaService

    adaugaOfertaService(o1.adresa,o1.pret,o1.tip,o1.suprafata,l);
    assert(l->len==1);
    assert(strcmp(l->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(l->oferte[0].tip,o1.tip)==0);
    assert(l->oferte[0].pret==o1.pret);
    assert(l->oferte[0].suprafata==o1.suprafata);
    adaugaOfertaService(o2.adresa,o2.pret,o2.tip,o2.suprafata,l);
    assert(l->len==2);
    assert(strcmp(l->oferte[1].adresa,o2.adresa)==0);
    assert(strcmp(l->oferte[1].tip,o2.tip)==0);
    assert(l->oferte[1].pret==o2.pret);
    assert(l->oferte[1].suprafata==o2.suprafata);
    adaugaOfertaService(o3.adresa,o3.pret,o3.tip,o3.suprafata,l);
    assert(l->len==3);
    assert(strcmp(l->oferte[2].adresa,o3.adresa)==0);
    assert(strcmp(l->oferte[2].tip,o3.tip)==0);
    assert(l->oferte[2].pret==o3.pret);
    assert(l->oferte[2].suprafata==o3.suprafata);

    //test stergeOfertaService

    stergeOfertaService(o1.adresa,l);
    assert(l->len==2);
    for (int i = 0;i<l->len;i++)
        assert(strcmp(l->oferte[i].adresa,o1.adresa)!=0);

    //test modificaOfertaService

    modificaOfertaService(o2.adresa,o1.adresa,o1.suprafata,o1.tip,o1.pret,l);
    assert(l->len==2);
    assert(strcmp(l->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(l->oferte[0].tip,o1.tip)==0);
    assert(l->oferte[0].pret==o1.pret);
    assert(l->oferte[0].suprafata==o1.suprafata);

    //test filtrareDupaTip

    destroyList(l);
    createList(l);
    adaugaOfertaService(o1.adresa,o1.pret,o1.tip,o1.suprafata,l);
    assert(l->len==1);
    assert(strcmp(l->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(l->oferte[0].tip,o1.tip)==0);
    assert(l->oferte[0].pret==o1.pret);
    assert(l->oferte[0].suprafata==o1.suprafata);
    adaugaOfertaService(o2.adresa,o2.pret,o2.tip,o2.suprafata,l);
    assert(l->len==2);
    assert(strcmp(l->oferte[1].adresa,o2.adresa)==0);
    assert(strcmp(l->oferte[1].tip,o2.tip)==0);
    assert(l->oferte[1].pret==o2.pret);
    assert(l->oferte[1].suprafata==o2.suprafata);
    adaugaOfertaService(o3.adresa,o3.pret,o3.tip,o3.suprafata,l);
    assert(l->len==3);
    assert(strcmp(l->oferte[2].adresa,o3.adresa)==0);
    assert(strcmp(l->oferte[2].tip,o3.tip)==0);
    assert(l->oferte[2].pret==o3.pret);
    assert(l->oferte[2].suprafata==o3.suprafata);

    Lista* filtrate=malloc(sizeof(Lista));
    filtrate=filtrareDupaTip("casa",l);
    assert(filtrate->len==1);
    assert(strcmp(filtrate->oferte[0].adresa,o2.adresa)==0);
    assert(strcmp(filtrate->oferte[0].tip,o2.tip)==0);
    assert(filtrate->oferte[0].pret==o2.pret);
    assert(filtrate->oferte[0].suprafata==o2.suprafata);
    filtrate=filtrareDupaTip("apartament",l);
    assert(filtrate->len==1);
    assert(strcmp(filtrate->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(filtrate->oferte[0].tip,o1.tip)==0);
    assert(filtrate->oferte[0].pret==o1.pret);
    assert(filtrate->oferte[0].suprafata==o1.suprafata);
    filtrate=filtrareDupaTip("teren",l);
    assert(filtrate->len==1);
    assert(strcmp(filtrate->oferte[0].adresa,o3.adresa)==0);
    assert(strcmp(filtrate->oferte[0].tip,o3.tip)==0);
    assert(filtrate->oferte[0].pret==o3.pret);
    assert(filtrate->oferte[0].suprafata==o3.suprafata);
    clearValidationError();
    filtrate=filtrareDupaTip("altceva",l);
    assert(filtrate->len==0);
    assert(strcmp(ValidationError,"Tip invalid\n")==0);

    //test sortare

    filtrate=sortare(l,0);
    assert(filtrate->len==3);
    assert(strcmp(filtrate->oferte[0].adresa,o3.adresa)==0);
    assert(strcmp(filtrate->oferte[1].adresa,o1.adresa)==0);
    assert(strcmp(filtrate->oferte[2].adresa,o2.adresa)==0);
    filtrate=sortare(l,1);
    assert(filtrate->len==3);
    assert(strcmp(filtrate->oferte[0].adresa,o2.adresa)==0);
    assert(strcmp(filtrate->oferte[1].adresa,o1.adresa)==0);
    assert(strcmp(filtrate->oferte[2].adresa,o3.adresa)==0);


}