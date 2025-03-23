#include "service.h"

#include "../ui/ui.h"


int adaugaOfertaService(char adresa[],float pret,char tip[],float suprafata,Lista* l)
{
    Oferta o;
    strcpy(o.adresa,adresa);
    o.pret=pret;
    strcpy(o.tip,tip);
    o.suprafata=suprafata;
    Errors errors = valideazaOferta(o);

    if (errors!=SUCCES)
        return errors;
    if (adaugaOferta(o,l)!=SUCCES)
        return REPO_ERROR;
    return SUCCES;

    
}


int stergeOfertaService(char adresa[],Lista* l)
{
    Oferta o;
    if (strcmp(adresa,"")==0)
        return ADRESA_INVALIDA;
    if (stergeOferta(adresa,l)!=SUCCES)
        return REPO_ERROR;
    return SUCCES;
}


int modificaOfertaService(char adresa[],char adresaNoua[],float suprafataNoua,char tipNou[],float pretNou,Lista* l)
{
    Oferta o;
    if (strcmp(adresa,"")==0)
        return ADRESA_INVALIDA;
    strcpy(o.adresa,adresaNoua);
    o.pret=pretNou;
    strcpy(o.tip,tipNou);
    o.suprafata=suprafataNoua;
    Errors errors = valideazaOferta(o);
    if (errors!=SUCCES)
        return errors;
    if (modificaOferta(adresa,o,l)!=SUCCES)
        return REPO_ERROR;
    return SUCCES;
}


Lista* sortare(Lista *l, int reverse) {

    Lista* rez=createList();
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
    Lista* rez=createList();
    if (pret<0) {
        destroyList(rez);
        return NULL;
    }
    for (int i = 0; i < l->len; i++)
        if (l->oferte[i].pret>=pret)

            adaugaOferta(l->oferte[i], rez);
    return rez;

}
Lista* filtrareDupaSuprafata(float suprafata,Lista* l) {
    Lista* rez=createList();
    if (suprafata<0) {
        destroyList(rez);
        return NULL;
    }
    for (int i = 0; i < l->len; i++)
        if (l->oferte[i].suprafata>=suprafata)
            adaugaOferta(l->oferte[i], rez);
    return rez;
}
Lista* filtrareDupaTip(char tip[],Lista* l) {
    Lista* rez=createList();
    if (strcmp(tip,"casa")!=0 && strcmp(tip,"apartament")!=0 && strcmp(tip,"teren")!=0) {
        destroyList(rez);
        return NULL;

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
    Lista* l=createList();


    //test adaugaOfertaService

    assert(adaugaOfertaService(o1.adresa,o1.pret,o1.tip,o1.suprafata,l)==SUCCES);
    assert(l->len==1);
    assert(strcmp(l->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(l->oferte[0].tip,o1.tip)==0);
    assert(l->oferte[0].pret==o1.pret);
    assert(l->oferte[0].suprafata==o1.suprafata);
    assert(adaugaOfertaService(o2.adresa,o2.pret,o2.tip,o2.suprafata,l)==SUCCES);
    assert(l->len==2);
    assert(strcmp(l->oferte[1].adresa,o2.adresa)==0);
    assert(strcmp(l->oferte[1].tip,o2.tip)==0);
    assert(l->oferte[1].pret==o2.pret);
    assert(l->oferte[1].suprafata==o2.suprafata);
    assert(adaugaOfertaService(o3.adresa,o3.pret,o3.tip,o3.suprafata,l)==SUCCES);
    assert(l->len==3);
    assert(strcmp(l->oferte[2].adresa,o3.adresa)==0);
    assert(strcmp(l->oferte[2].tip,o3.tip)==0);
    assert(l->oferte[2].pret==o3.pret);
    assert(l->oferte[2].suprafata==o3.suprafata);
    assert(adaugaOfertaService(o1.adresa,o1.pret,o1.tip,o1.suprafata,l)==REPO_ERROR);
    assert(adaugaOfertaService("",-1,"altceva",-1,l) & (TIP_INVALID + SUPRAFATA_INVALIDA + PRET_INVALID + ADRESA_INVALIDA));

    //test stergeOfertaService

    assert(stergeOfertaService(o1.adresa,l)==SUCCES);
    assert(l->len==2);
    for (int i = 0;i<l->len;i++)
        assert(strcmp(l->oferte[i].adresa,o1.adresa)!=0);
    assert(stergeOfertaService(o1.adresa,l)==REPO_ERROR);
    assert(stergeOfertaService("",l)==ADRESA_INVALIDA);
    //test modificaOfertaService

    assert(modificaOfertaService(o2.adresa,o1.adresa,o1.suprafata,o1.tip,o1.pret,l)==SUCCES);
    assert(l->len==2);
    assert(strcmp(l->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(l->oferte[0].tip,o1.tip)==0);
    assert(l->oferte[0].pret==o1.pret);
    assert(l->oferte[0].suprafata==o1.suprafata);
    assert(modificaOfertaService("",o1.adresa,o1.suprafata,o1.tip,o1.pret,l)==ADRESA_INVALIDA);
    assert(modificaOfertaService(o2.adresa,"",-1,"",-1,l) & (TIP_INVALID + ADRESA_INVALIDA + SUPRAFATA_INVALIDA + PRET_INVALID));
    assert(modificaOfertaService("salut","ok",1,"casa",1,l)==REPO_ERROR);


    //test filtrareDupaTip

    destroyList(l);
    l=createList();
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

    Lista *filtrate = filtrareDupaTip("casa", l);
    assert(filtrate->len==1);
    assert(strcmp(filtrate->oferte[0].adresa,o2.adresa)==0);
    assert(strcmp(filtrate->oferte[0].tip,o2.tip)==0);
    assert(filtrate->oferte[0].pret==o2.pret);
    assert(filtrate->oferte[0].suprafata==o2.suprafata);
    destroyList(filtrate);
    filtrate=filtrareDupaTip("apartament",l);
    assert(filtrate->len==1);
    assert(strcmp(filtrate->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(filtrate->oferte[0].tip,o1.tip)==0);
    assert(filtrate->oferte[0].pret==o1.pret);
    assert(filtrate->oferte[0].suprafata==o1.suprafata);
    destroyList(filtrate);
    filtrate=filtrareDupaTip("teren",l);
    assert(filtrate->len==1);
    assert(strcmp(filtrate->oferte[0].adresa,o3.adresa)==0);
    assert(strcmp(filtrate->oferte[0].tip,o3.tip)==0);
    assert(filtrate->oferte[0].pret==o3.pret);
    assert(filtrate->oferte[0].suprafata==o3.suprafata);
    destroyList(filtrate);
    filtrate=filtrareDupaTip("altceva",l);
    assert(filtrate==NULL);
    //test sortare

    filtrate=sortare(l,0);
    assert(filtrate->len==3);
    assert(strcmp(filtrate->oferte[0].adresa,o3.adresa)==0);
    assert(strcmp(filtrate->oferte[1].adresa,o1.adresa)==0);
    assert(strcmp(filtrate->oferte[2].adresa,o2.adresa)==0);
    destroyList(filtrate);
    filtrate=sortare(l,1);
    assert(filtrate->len==3);
    assert(strcmp(filtrate->oferte[0].adresa,o2.adresa)==0);
    assert(strcmp(filtrate->oferte[1].adresa,o1.adresa)==0);
    assert(strcmp(filtrate->oferte[2].adresa,o3.adresa)==0);
    destroyList(filtrate);
    l->oferte[2].pret=l->oferte[1].pret;
    filtrate=sortare(l,0);
    assert(filtrate->len==3);
    assert(strcmp(filtrate->oferte[0].adresa,o1.adresa)==0);
    assert(strcmp(filtrate->oferte[1].adresa,o2.adresa)==0);
    assert(strcmp(filtrate->oferte[2].adresa,o3.adresa)==0);
    destroyList(filtrate);
    filtrate=sortare(l,1);
    assert(filtrate->len==3);
    assert(strcmp(filtrate->oferte[0].adresa,o3.adresa)==0);
    assert(strcmp(filtrate->oferte[1].adresa,o2.adresa)==0);
    assert(strcmp(filtrate->oferte[2].adresa,o1.adresa)==0);
    destroyList(filtrate);
    //test filtrare dupa pret

    filtrate=filtrareDupaPret(-1,l);
    assert(filtrate==NULL);
    filtrate=filtrareDupaPret(3,l);
    assert(filtrate->len==2);
    assert(filtrate->oferte[0].pret==4 && strcmp(filtrate->oferte[0].adresa,o2.adresa)==0);
    assert(filtrate->oferte[1].pret==4 && strcmp(filtrate->oferte[1].adresa,o3.adresa)==0);

    //test filtrare dupa suprafata

    filtrate=filtrareDupaSuprafata(-1,l);
    assert(filtrate==NULL);
    filtrate=filtrareDupaSuprafata(2,l);
    assert(filtrate->len==2);
    assert(strcmp(filtrate->oferte[0].adresa,o1.adresa)==0 && strcmp(filtrate->oferte[1].adresa,o3.adresa)==0);

}