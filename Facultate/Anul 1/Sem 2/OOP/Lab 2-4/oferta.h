#pragma once
/**
 * structura unei Oferte
 */
typedef struct {
    char* tip;
    float suprafata;
    char* adresa;
    float pret;
}Oferta;

/**
 *
 * @param adresa char*,adresa ofertei
 * @param suprafata float,suprafata ofertei
 * @param tip char*,tipul ofertei
 * @param pret float,pretul ofertei
 * @return un pointer catre o Oferta
 */
Oferta* createOferta(const char* adresa,float suprafata, const char* tip,float pret);

/**
 * destructorul pentru Oferta
 * @param o pointer catre o Oferta
 */
void destroyOferta(Oferta* o);

/**
 *
 * @param o un pointer catre o Oferta
 * @return un deepcopy al ofertei
 */
Oferta* copyOferta(const Oferta* o);

/**
 * functie folosita pentru testele din oferta.c
 */
void testOferta();