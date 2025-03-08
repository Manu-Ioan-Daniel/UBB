#include "ui.h"
void run(Lista* l){
    char optiune[100];
    while(optiune[0]!='0'){
        printf("Introdu optiunea dorita:\n1.Adauga oferta\n2.Sterge oferta\n3.Modifica oferta\n4.Afiseaza ofertele\n5.Sortare oferte\n6.Printeaza oferte\n7.Filtrare\n0.Iesire\n");
        scanf("%s",optiune);
        getchar();
        if(strlen(optiune)>1){
            printf("Optiune invalida\n");
            optiune[0]='1';
            continue;
        }
        switch(optiune[0]){
            case '1':
                adaugaOfertaUI(l);
                break;
            case '2':
                stergeOfertaUI(l);
                break;
            case '3':
                modificaOfertaUI(l);
                break;
            case '4':
                afisareOferteUI(l);
                break;
            case '5':
                sortareUI(l);
                break;
            case '6':
                printeazaOferte(l);
                break;
            case '7':
                filtrareUI(l);
                break;
            case '0':
                break;
            default:
                printf("Optiune invalida\n");
                optiune[0]='1';
        }
    }
    
}
void printeazaOferte(Lista* l){
    for(int i=0;i<l->len;i++)
        printf("Adresa:%s Tip:%s Suprafata:%.2f Pret:%.2f\n",l->oferte[i].adresa,l->oferte[i].tip,l->oferte[i].suprafata,l->oferte[i].pret);
}
void getFloat(const char* mesaj, float* f) {
    char input[100];
    while (1) {
        printf("%s", mesaj);
        fgets(input, 100, stdin);
        if (sscanf(input, "%f", f) == 1) {
            break;
        } else {
            printf("Introdu un numar valid\n");
        }
    }
}


void adaugaOfertaUI(Lista* l){
    char adresa[100],tip[12];
    float suprafata,pret;
    printf("Introdu adresa:");
    fgets(adresa,100,stdin);
    adresa[strlen(adresa)-1]='\0';
    printf("Introdu tipul:");
    fgets(tip,12,stdin);
    tip[strlen(tip)-1]='\0';
    getFloat("Introdu suprafata:",&suprafata);
    getFloat("Introdu pret:",&pret);
    adaugaOfertaService(adresa,pret,tip,suprafata,l);
    if (strlen(ValidationError)>0) {
        printf("%s",ValidationError);
        clearValidationError();
        return;
    }
    if (strlen(RepoError)>0) {
        printf("%s",RepoError);
        clearRepoError();
    }
}
void stergeOfertaUI(Lista* l){
    char adresa[100];
    printf("Introdu adresa ofertei pe care vrei sa o stergi:");
    fgets(adresa,100,stdin);
    adresa[strlen(adresa)-1]='\0';
    stergeOfertaService(adresa,l);
    if (strlen(ValidationError)>0) {
        printf("%s",ValidationError);
        clearValidationError();
        return;
    }
    if (strlen(RepoError)>0) {
        printf("%s",RepoError);
        clearRepoError();
    }

}
void modificaOfertaUI(Lista* l) {
    char adresa[100], adresaNoua[100], tipNou[12];
    float suprafataNoua, pretNou;

    printf("Introdu adresa ofertei pe care vrei sa o modifici:");
    fgets(adresa, 100, stdin);
    adresa[strlen(adresa) - 1] = '\0';


    printf("Introdu noua adresa:");
    fgets(adresaNoua, 100, stdin);
    adresaNoua[strlen(adresaNoua) - 1] = '\0';


    printf("Introdu noul tip:");
    fgets(tipNou, 12, stdin);
    tipNou[strlen(tipNou) - 1] = '\0';


    getFloat("Introdu noua suprafata:", &suprafataNoua);
    getFloat("Introdu noul pret:", &pretNou);

    modificaOfertaService(adresa, adresaNoua, suprafataNoua, tipNou, pretNou, l);

    if (strlen(ValidationError) > 0) {
        printf("%s", ValidationError);
        clearValidationError();
        return;
    }
    if (strlen(RepoError) > 0) {
        printf("%s", RepoError);
        clearRepoError();
    }
}
void afisareOferteUI(Lista* l){
    if (l->len==0)
        printf("Nu exista oferte\n");
    for(int i=0;i<l->len;i++)
        printf("Adresa:%s Tip:%s Suprafata:%.2f Pret:%.2f\n",l->oferte[i].adresa,l->oferte[i].tip,l->oferte[i].suprafata,l->oferte[i].pret);
}
void sortareUI(Lista* l){
    char reverse[50];
    Lista* rez;
    printf("Cum vrei sortarea?(crescatoare/descrescatoare):");
    fgets(reverse,50,stdin);
    reverse[strlen(reverse)-1]='\0';
    if(strcmp(reverse,"descrescatoare")==0)
        rez=sortare(l,1);
    else if (strcmp(reverse,"crescatoare")==0)
        rez=sortare(l,0);
    else {
        printf("%s","Sortare invalida\n");
        return;
    }
    afisareOferteUI(rez);
    destroyList(rez);
}
void filtrareUI(Lista* l) {
    char filtru[50];
    Lista* filtrate=malloc(sizeof(Lista));
    printf("Introdu un filtru:");
    fgets(filtru,50,stdin);
    filtru[strlen(filtru)-1]='\0';
    if (strcmp(filtru,"suprafata")==0) {
        float suprafata;
        getFloat("Introdu o suprafata:",&suprafata);
        filtrate=filtrareDupaSuprafata(suprafata,l);
        afisareOferteUI(filtrate);
        destroyList(filtrate);

    }
    else if (strcmp(filtru,"tip")==0) {
        char tip[12];
        printf("Introdu un tip:");
        fgets(tip,12,stdin);
        tip[strlen(tip)-1]='\0';
        filtrate=filtrareDupaTip(tip,l);
        afisareOferteUI(filtrate);
        destroyList(filtrate);


    }
    else if (strcmp(filtru,"pret")==0) {
        float pret;
        getFloat("Introdu un pret:",&pret);
        filtrate=filtrareDupaPret(pret,l);
        afisareOferteUI(filtrate);
        destroyList(filtrate);

    }else {
        printf("Filtru invalid.Filtru poate fi doar tip,pret,suprafata\n");
    }
    if (strlen(ValidationError) > 0){
        printf("%s", ValidationError);
        clearValidationError();
    }
    if (strlen(RepoError)>0) {
        printf("%s", RepoError);
        clearRepoError();

    }

}
