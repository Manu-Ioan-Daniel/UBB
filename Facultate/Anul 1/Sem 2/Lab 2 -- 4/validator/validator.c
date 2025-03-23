
#include "validator.h"


Errors valideazaOferta(Oferta o){
    Errors errors=SUCCES;
    
    strip(o.adresa);
    strip(o.tip);
    if(o.pret<0)
        errors |= PRET_INVALID;
    if(o.suprafata<0)
        errors |= SUPRAFATA_INVALIDA;
    if(strcmp(o.tip,"apartament")!=0 && strcmp(o.tip,"casa")!=0 && strcmp(o.tip,"teren")!=0)
        errors |= TIP_INVALID;;
    if(strcmp(o.adresa,"")==0)
        errors |= ADRESA_INVALIDA;
    return errors;
}
void strip(char* s) {
    char* d = s;
    do {
        while (*d == ' ')
            ++d;

    } while ((*s++ = *d++));
}

//Teste

void testValidator() {
    Oferta o;
    strcpy(o.adresa,"adresa");
    strcpy(o.tip,"tip");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==TIP_INVALID);
    strcpy(o.tip,"");
    assert(valideazaOferta(o)==TIP_INVALID);
    strcpy(o.tip,"tip");
    o.pret=-1;
    assert(valideazaOferta(o) & TIP_INVALID + PRET_INVALID);
    o.pret=1;
    o.suprafata=-1;
    assert(valideazaOferta(o)& TIP_INVALID + SUPRAFATA_INVALIDA);
    o.suprafata=1;
    strcpy(o.tip,"apartament");
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"casa");
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"teren");
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"altceva");
    assert(valideazaOferta(o)==TIP_INVALID);
    strcpy(o.tip,"apartament");
    strcpy(o.adresa,"");
    assert(valideazaOferta(o)==ADRESA_INVALIDA);
    strcpy(o.tip,"apartament  ");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"casa");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"teren");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"apartament");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"apartament");
    strcpy(o.adresa,"adresa");
    o.pret=1;
    o.suprafata=1;
    assert(valideazaOferta(o)==SUCCES);
    strcpy(o.tip,"apartament");
}