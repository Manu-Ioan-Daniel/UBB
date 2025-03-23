#ifndef UI_H
#define UI_H
#include "../infrastructura/service.h"
#include <stdio.h>
void run(Lista*);
void handle_errors(Errors);
void adaugaOfertaUI(Lista*);
void stergeOfertaUI(Lista*);
void modificaOfertaUI(Lista*);
void afisareOferteUI(Lista*);
void sortareUI(Lista*);
void filtrareUI(Lista*);
void printeazaOferte(Lista*);

#endif