
#include "validator.h"

#include "../errors/errors.h"

int valideazaOferta(Oferta o){
    char erori[100]="";
    strip(o.adresa);
    strip(o.tip);
    if(o.pret<0)
        strcat(erori,"Pretul nu poate fi negativ\n");
    if(o.suprafata<0)
        strcat(erori,"Suprafata nu poate fi negativa\n");
    if(strcmp(strlwr(o.tip),"apartament")!=0 && strcmp(strlwr(o.tip),"casa")!=0 && strcmp(strlwr(o.tip),"teren")!=0)
        strcat(erori,"Tipul trebuie sa fie apartament, casa sau teren\n");
    if(strcmp(strlwr(o.adresa),"")==0)
        strcat(erori,"Adresa nu poate fi vida\n");
    if(strcmp(erori,"")!=0){
        addValidationError(erori);
        return 0;
    }
    return 1;
}
void strip(char* s) {
    char* d = s;
    do {
        while (*d == ' ') {
            ++d;
        }
    } while ((*s++ = *d++));
}

//Teste

void testValidator() {
    Oferta o;
    strcpy(o.adresa,"adresa");
    strcpy(o.tip,"tip");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==0);
    strcpy(o.tip,"");
    assert(valideazaOferta(o)==0);
    strcpy(o.tip,"tip");
    o.pret=-1;
    assert(valideazaOferta(o)==0);
    o.pret=1;
    o.suprafata=-1;
    assert(valideazaOferta(o)==0);
    o.suprafata=1;
    strcpy(o.tip,"apartament");
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"casa");
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"teren");
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"altceva");
    assert(valideazaOferta(o)==0);
    strcpy(o.tip,"apartament");
    strcpy(o.adresa,"");
    assert(valideazaOferta(o)==0);
    strcpy(o.tip,"apartament");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"casa");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"teren");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"apartament");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"apartament");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==1);
    strcpy(o.tip,"apartament");
}