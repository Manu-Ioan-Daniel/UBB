#include "ui.h"
#include <stdio.h>
#include <string.h>


void run() {
    char optiune[100]="";
    Service* service=createService();
    while (optiune[0]!='0' || strlen(optiune)>2) {
        printMenu();
        printf("Introdu optiunea dorita: ");
        fgets(optiune, 100, stdin);
        if (strlen(optiune)>2) {
            printf("Optiune invalida\n");
            continue;
        }
        switch (optiune[0]) {
            case '1':
                adaugaOfertaUI(service);
                break;
            case '2':
                stergeOfertaUI(service);
                break;
            case '3':
                modificaOfertaUI(service);
                break;
            case '4':
                afisareOferteUI(service->repo->oferte);
                break;
            case '5':
                sortareUI(service);
                break;
            case '6':
                filtrareUI(service);
                break;
            case '0':
                break;
            default:
                printf("Optiune invalida\n");
                break;
        }
    }
    destroyService(service);
}
void printMenu() {
    printf("Meniu:\n");
    printf("1. Adauga oferta\n");
    printf("2. Sterge oferta\n");
    printf("3. Modifica oferta\n");
    printf("4. Afiseaza ofertele\n");
    printf("5. Sortare oferte\n");
    printf("6. Filtrare\n");
    printf("0. Iesire\n");
}
void adaugaOfertaUI(Service* service) {
    char adresa[100], tip[100];
    float suprafata, pret;
    printf("Introdu adresa: ");
    fgets(adresa, 100, stdin);
    adresa[strlen(adresa)-1] = '\0';
    printf("Introdu tipul: ");
    fgets(tip, 100, stdin);
    tip[strlen(tip)-1] = '\0';
    getFloat("Introdu suprafata: ", &suprafata);
    getFloat("Introdu pret: ", &pret);
    Errors errors = adaugaOfertaService(service, adresa, pret, tip, suprafata);
    if (errors != SUCCES) {
        handleErrors(errors);
    }
}
void handleErrors(Errors errors) {
    if (errors & REPO_ERROR)
        printf("Eroare de repo\n");
    if (errors & TIP_INVALID)
        printf("Tip invalid\n");
    if (errors & ADRESA_INVALIDA)
        printf("Adresa invalida\n");
    if (errors & PRET_INVALID)
        printf("Pret invalid\n");
    if (errors & SUPRAFATA_INVALIDA) {
        printf("Suprafata invalida\n");
    }
}

void getFloat(const char* mesaj, float* f) {
        char input[100];
        while (1) {
            printf("%s", mesaj);
            fgets(input, 100, stdin);
            if (sscanf(input, "%f", f) == 1) {
                break;
            }
            printf("Introdu un numar valid\n");

        }
}
void stergeOfertaUI(Service* service) {
    char adresa[100];
    printf("Introdu adresa ofertei pe care vrei:");
    fgets(adresa, 100, stdin);
    adresa[strlen(adresa)-1] = '\0';
    Errors errors = stergeOfertaService(service, adresa);
    if (errors != SUCCES) {
        handleErrors(errors);
    }

}
void modificaOfertaUI(Service* service) {
    char adresa[100],adresaNoua[100],tipNou[100];
    float suprafataNoua,pretNou;
    printf("Introdu adresa ofertei pe care vrei sa o modifici:");
    fgets(adresa, 100, stdin);
    adresa[strlen(adresa)-1] = '\0';

    printf("Introdu noua adresa: ");
    fgets(adresaNoua, 100, stdin);
    adresaNoua[strlen(adresaNoua)-1] = '\0';

    printf("Introdu tipul: ");
    fgets(tipNou, 100, stdin);
    tipNou[strlen(tipNou)-1] = '\0';

    getFloat("Introdu suprafata noua: ", &suprafataNoua);
    getFloat("Introdu pretul nou: ", &pretNou);

    Errors errors=modificaOfertaService(service,adresa,adresaNoua,suprafataNoua,tipNou,pretNou);
    if (errors!=SUCCES)
        handleErrors(errors);

}
void afisareOferteUI(List* l) {
    if (getLength(l)==0) {
        printf("Nu exista oferte\n");
        return;
    }
    for (int i = 0; i < getLength(l); i++) {
        Oferta* o = getElem(l, i);
        printf("Adresa: %s, Tip: %s, Suprafata: %.2f, Pret: %.2f\n", o->adresa, o->tip, o->suprafata, o->pret);
    }
}
void sortareUI(Service* service) {
    char reverse[50];
    List* rez;
    printf("Cum vrei sortarea?(crescatoare/descrescatoare):");
    fgets(reverse,50,stdin);
    reverse[strlen(reverse)-1]='\0';

    if(strcmp(reverse,"descrescatoare")==0)
        rez=sortare(service,1);
    else if (strcmp(reverse,"crescatoare")==0)
        rez=sortare(service,0);
    else {
        printf("%s","Criteriu invalid\n");
        return;
    }
    if (rez==NULL) {
        printf("Nu exista oferte!\n");
        return;
    }
    afisareOferteUI(rez);
    destroyList(rez);
}
void filtrareUI(Service* service) {
    char filtru[50];
    List* filtrate;
    printf("Introdu un filtru:");
    fgets(filtru,50,stdin);
    filtru[strlen(filtru)-1]='\0';
    if (strcmp(filtru,"suprafata")==0) {
        float suprafata;
        getFloat("Introdu o suprafata:",&suprafata);
        filtrate=filtrareDupaSuprafata(service,suprafata);
        if (filtrate==NULL) {
            printf("Eroare la filtrare.\n");
            return;
        }
        afisareOferteUI(filtrate);
        destroyList(filtrate);

    }
    else if (strcmp(filtru,"tip")==0) {
        char tip[12];
        printf("Introdu un tip:");
        fgets(tip,12,stdin);
        tip[strlen(tip)-1]='\0';
        filtrate=filtrareDupaTip(service,tip);
        if (filtrate==NULL) {
            printf("Eroare la filtrare.\n");
            return;
        }
        afisareOferteUI(filtrate);
        destroyList(filtrate);

    }
    else if (strcmp(filtru,"pret")==0) {
        float pret;
        getFloat("Introdu un pret:",&pret);
        filtrate=filtrareDupaPret(service,pret);
        if (filtrate==NULL) {
            printf("Eroare la filtrare.\n");
            return;
        }
        afisareOferteUI(filtrate);
        destroyList(filtrate);

    }else
        printf("Filtru invalid.Filtru poate fi doar tip,pret,suprafata\n");
}
