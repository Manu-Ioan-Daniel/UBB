#pragma once

#include "oferta.h"
#include "errors.h"

/**
 * functie care valideaza o oferta
 * @param o Oferta*
 * @return erorile de validare(TIP_INVALID,ADRESA_INVALIDA,etc.)
 */
Errors valideazaOferta(Oferta* o);

/**
 * functie de test pentru validator.c
 */
void testValidator();
