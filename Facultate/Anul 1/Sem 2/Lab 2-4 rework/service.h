#pragma once
#include "repo.h"

/**
 * structura unui Service
 */
typedef struct {
    Repo* repo;

} Service;

/**
 * functie care creeaza un Service gol
 * @return un Service*
 */
Service* createService();

/**
 * destructor pentru service
 * @param service Service*
 */
void destroyService(Service* service);

/**
 * functie care adauga o oferta
 * @param service Service*
 * @param adresa char*
 * @param pret float
 * @param tip char*
 * @param suprafata float
 * @return SUCCES daca operatia s a realizat cu succes,eroare de validare daca datele introduse sunt invalide,REPO_ERROR daca oferta exista deja
 */
int adaugaOfertaService(Service* service, char* adresa, float pret, char* tip, float suprafata);

/**
 * functie care sterge o oferta
 * @param service Service*
 * @param adresa char*,adresa ofertei pe care o stergem
 * @return SUCCES daca operatia s a realizat cu succes,REPO_ERROR daca oferta nu exista,ADRESA_INVALIDA daca adresa e invalida
 */
int stergeOfertaService(Service* service, char* adresa);

/**
 * functie care modifica o oferta existenta la o oferta introdusa
 * @param service Service*
 * @param adresa char*,adresa ofertei pe care o modificam
 * @param adresaNoua char*
 * @param suprafataNoua float
 * @param tipNou char*
 * @param pretNou float
 * @return SUCCES daca operatia s a realizat cu succes,REPO_ERROR daca oferta nu exista,ADRESA_INVALIDA daca adresa e invalida,PRET_INVALID daca pretul e invalid,SUPRAFATA_INVALIDA daca suprafata e invalida
 */
int modificaOfertaService(Service* service,char* adresa,char* adresaNoua,float suprafataNoua,char* tipNou,float pretNou);

/**
 * functie care filtreaza ofertele dupa suprafata
 * @param service Service*
 * @param suprafata float
 * @return o lista cu ofertele care au suprafata mai mica sau egala cu suprafata data
 */
List* filtrareDupaSuprafata(Service* service,float suprafata);

/**
 * functie care filtereaza ofertele dupa tip
 * @param service Service*
 * @param tip char*
 * @return O lista cu ofertele care u tipul dat
 */
List* filtrareDupaTip(Service* service,char* tip);

/**
 * functie care filtreaza ofertele dupa tip
 * @param service Service*
 * @param pret float
 * @return o lista cu ofertele care au pretul mai mare sau egal cu pretul dat
 */
List* filtrareDupaPret(Service* service,float pret);

/**
 * functie care sorteaza ofertele dupa pret,iar daca preturile sunt egale dupa tip
 * @param service Service*
 * @param reverse int,ne spune daca sortam cresc/descresc
 * @return o lista sortata crescator/descrescator in functie de reverse
 */
List* sortare(Service* service,int reverse);

/**
 * functie care returneaza toate ofertele din repo-ul din service
 * @param service Service*
 * @return O lista cu toate elementele din repo-ul din service
 */
List* get_all( Service* service);

/**
 * functie pentru testele din service.c
 */
void testeService();

/**
 *
 * @param o1 void,o oferta*
 * @param o2 void*,o oferta
 * @return 1 daca o1<o2,-1 daca o1>o2
 */
int compareOffers(const void* o1,const void* o2);

/**
 *
 * @param o1 void*,o oferta
 * @param o2 void*,o oferta
 * @return 1 daca o1>o2,-1 daca o1<o2
 */
int compareOffersReverse(const void* o1,const void* o2);

