#pragma once
#include "service.h"
void adaugaOfertaUI(Service* service);
void stergeOfertaUI(Service* service);
void modificaOfertaUI(Service* service);
void afisareOferteUI(List* service);
void sortareUI(Service* service);
void filtrareUI(Service* service);
void printMenu();
void getFloat(const char* mesaj, float* f);
void handleErrors(Errors errors);
void run();