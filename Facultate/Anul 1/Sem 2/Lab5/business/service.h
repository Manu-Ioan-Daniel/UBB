//
// Created by Andra Mateș on 15.03.2025.
//

#ifndef SERVICE_H
#define SERVICE_H

#include "../infrastructure/repo.h"
#include <stdbool.h>

typedef struct {
    Repo* repo;
} Service;

/**
 * Creează un nou service pentru gestionarea cheltuielilor.
 * @return Un pointer către structura Service nou creată.
 */
Service* createService();

/**
 * Distruge un service și eliberează memoria asociată acestuia.
 * @param service Service care trebuie distrus.
 */
void destroyService(Service* service);

/**
 * Validează si adaugă o cheltuială în repo.
 * @param service Service
 * @param apNumber Numărul apartamentului.
 * @param sum Suma cheltuielii.
 * @param type Tipul cheltuielii.
 * @param error Structura pentru gestionarea erorilor.
 * @return true dacă adăugarea a avut succes, false altfel.
 */
bool addExpenseService(Service* service, int apNumber, double sum, char* type, Error* error);

/**
 * Modifică o cheltuială existentă din repo.
 * @param service Service
 * @param oldApNumber Numărul apartamentului pentru cheltuiala veche.
 * @param oldType Tipul cheltuielii vechi.
 * @param newSum Noua sumă a cheltuielii.
 * @param newType Noul tip de cheltuială.
 * @param error Structura pentru gestionarea erorilor.
 * @return true dacă modificarea a avut succes, false altfel.
 */
bool modifyExpenseService(Service* service, int oldApNumber, char* oldType, double newSum, char* newType, Error* error);

/**
 * Șterge o cheltuială din repo.
 * @param service Service
 * @param apNumber Numărul apartamentului.
 * @param type Tipul cheltuielii.
 * @param error Structura pentru gestionarea erorilor.
 * @return true dacă ștergerea a avut succes, false altfel.
 */
bool deleteExpenseService(Service* service, int apNumber, char* type, Error* error);

/**
 * Returnează repository-ul asociat service-ului.
 * @param service Service care gestionează repository-ul.
 * @return Un pointer către repository.
 */
Repo* getRepo(Service* service);

/**
 * Returnează lista tuturor cheltuielilor gestionate de repo.
 * @param service Service
 * @return Un pointer la lista de cheltuieli.
 */
List* getExpensesService(Service* service);

/**
 * Filtrează cheltuielile după numărul apartamentului.
 * @param service Service.
 * @param apNumber Numărul apartamentului pentru care se face filtrarea.
 * @return O listă cu cheltuielile filtrate.
 */
List* filterExpensesByAp(Service* service, int apNumber);

/**
 * Filtrează cheltuielile care au o sumă dată.
 * @param service Service
 * @param sum Suma dată
 * @return O listă cu cheltuielile filtrate.
 */
List* filterExpensesBySum(Service* service, double sum);

/**
 * Filtrează cheltuielile după tip.
 * @param service Service
 * @param type Tipul cheltuielii pentru filtrare.
 * @return O listă cu cheltuielile filtrate.
 */
List* filterExpensesByType(Service* service, char* type);

/**
 * Sortează cheltuielile după un criteriu specific.
 * @param service Service
 * @param criteria Criteriul de sortare (ex: după sumă sau tip).
 * @param ascending true pentru sortare crescătoare, false pentru descrescătoare.
 * @return O listă cu cheltuielile sortate.
 */
List* sortExpenses(Service* service, int criteria, bool ascending);
#endif //SERVICE_H
