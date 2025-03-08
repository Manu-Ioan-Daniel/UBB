#include "../domeniu/lista.h"

#ifndef REPO_H
#define REPO_H
#include <string.h>
#include <assert.h>
#include <stdio.h>
#include "../errors/errors.h"
#include "../domeniu/oferta.h"
void adaugaOferta(Oferta,Lista*);
void stergeOferta(char[],Lista*);
void modificaOferta(char[],Oferta,Lista*);
int cautaOferta(char[],Lista*);
void testRepo();
#endif //REPO_H