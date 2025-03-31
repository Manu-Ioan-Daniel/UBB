//
// Created by Deny on 23-03-2025.
//

#include "validator.h"
#include <string.h>
#include <assert.h>
Errors valideazaOferta(Oferta* o){
    Errors errors=SUCCES;
    if(o->pret<0)
        errors |= PRET_INVALID;
    if(o->suprafata<0)
        errors |= SUPRAFATA_INVALIDA;
    if(strcmp(o->tip,"apartament")!=0 && strcmp(o->tip,"casa")!=0 && strcmp(o->tip,"teren")!=0)
        errors |= TIP_INVALID;;
    if(strcmp(o->adresa,"")==0)
        errors |= ADRESA_INVALIDA;
    return errors;
}

//Teste

void testValidator() {
    Oferta* o=createOferta("adresa",1,"tip",1);
    assert(valideazaOferta(o)==TIP_INVALID);
    destroyOferta(o);
    o=createOferta("adresa",1,"",1);
    assert(valideazaOferta(o)==TIP_INVALID);
    destroyOferta(o);
    o=createOferta("adresa",1,"casa",1);
    assert(valideazaOferta(o)==SUCCES);
    o->pret=-1;
    assert(valideazaOferta(o) == PRET_INVALID);
    o->suprafata=-1;
    assert(valideazaOferta(o)==(SUPRAFATA_INVALIDA|PRET_INVALID));
    destroyOferta(o);
    o=createOferta("",-1,"casa",-1);
    assert(valideazaOferta(o)==(ADRESA_INVALIDA|SUPRAFATA_INVALIDA|PRET_INVALID));
    destroyOferta(o);

}