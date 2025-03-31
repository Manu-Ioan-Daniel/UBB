#pragma once
#include "lista.h"
#include "oferta.h"
#include "errors.h"
/**
 * structura unui Repo
 */
typedef struct {
    List* oferte;
}Repo;

/**
 * functie care creeaza un Repo gol
 * @return un pointer catre un Repo
 */
Repo* createRepo();

/**
 * destructorul pentru Repo
 */
void destroyRepo();

/**
 * functie care adauga o oferta la un repo
 * @param o un pointer catre o Oferta
 * @return SUCCES daca oferta a fost adaugata cu succes, REPO_ERROR daca oferta exista deja
 */
int adaugaOferta(const Repo*,Oferta* o);

/**
 * functie care sterge o oferta cu adresa data dintr un repo
 * @param adresa char*,adresa ofertei pe care o stergem
 * @return SUCCES daca oferta a fost stearsa cu succes, REPO_ERROR daca oferta nu exista
 */
int  stergeOferta(const Repo*, const char* adresa);

/**
 *
 * @param adresa char*,adresa unei oferte
 * @param o Oferta*
 * @return SUCCES daca oferta a fost modificata cu succes, REPO_ERROR daca oferta nu exista sau daca exista deja o oferta cu noua adresa
 */
int  modificaOferta(const Repo*, const char* adresa,Oferta* o);

/**
 * functie care cauta o oferta cu adresa data intr un repo
 * @param adresa char*,adresa ofertei pe care o cautam
 * @return pozitia ofertei daca aceasta exista,-1 altfel
 */
int cautaOferta(const Repo*, const char* adresa);

/**
 *
 * @return o lista cu toate ofertele din Repo
 */
List* getOferte(const Repo*);

/**
 * functie de test pentru repo.c
 */
void testRepo();

