
#ifndef VALIDATOR_H
#define VALIDATOR_H
#include "../domeniu/oferta.h"
#include "../errors/errors.h"
Errors valideazaOferta(Oferta o);
void strip(char* s);
void testValidator();
#endif //VALIDATOR_H
