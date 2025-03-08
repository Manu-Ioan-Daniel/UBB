
#ifndef VALIDATOR_H
#define VALIDATOR_H
#include "../domeniu/oferta.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
int valideazaOferta(Oferta o);
void strip(char* s);
void testValidator();
#endif //VALIDATOR_H
