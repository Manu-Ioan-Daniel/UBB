//
// Created by Andra Mateș on 15.03.2025.
//

#ifndef REPO_H
#define REPO_H

#include "../utils/list.h"
#include "../domain/expense.h"
#include "../errors/error.h"
#include <stdbool.h>

typedef struct {
    List* expenses;
} Repo;


/**
 * Creează un repository nou pentru cheltuieli.
 * @return Un pointer către structura Repo nou creată.
 */
Repo* createRepo();

/**
 * Distruge un repository și eliberează memoria asociată acestuia.
 * @param repo Repository-ul care trebuie distrus.
 */
void destroyRepo(Repo* repo);

/**
 * Caută poziția unei cheltuieli în repository.
 * @param repo Repository-ul unde se face căutarea.
 * @param apNumber Numărul apartamentului.
 * @param type Tipul cheltuielii.
 * @return Poziția în listă a cheltuielii, sau -1 dacă nu există.
 */
int searchPos(Repo* repo, int apNumber, char* type);

/**
 * Adaugă o cheltuială nouă în repository.
 * @param repo Repository-ul în care se adaugă cheltuiala.
 * @param expense Cheltuiala care trebuie adăugată.
 * @param error Structura în care se va salva eroarea, dacă există.
 * @return true dacă adăugarea a avut succes, false altfel.
 */
bool addExpense(Repo* repo, Expense* expense, Error* error);

/**
 * Modifică o cheltuială existentă din repository.
 * @param repo Repository-ul unde se face modificarea.
 * @param expense Noua cheltuială care va înlocui cheltuiala veche.
 * @param oldApNumber Numărul apartamentului pentru cheltuiala veche.
 * @param oldType Tipul cheltuielii vechi.
 * @param error Structura pentru stocarea unei eventuale erori.
 * @return true dacă modificarea a avut succes, false altfel.
 */
bool modifyExpense(Repo* repo, Expense* expense, int oldApNumber, char* oldType, Error* error);

/**
 * Șterge o cheltuială din repository.
 * @param repo Repository-ul din care se șterge cheltuiala.
 * @param expense Cheltuiala care trebuie ștearsă.
 * @param error Structura pentru stocarea unei eventuale erori.
 * @return true dacă ștergerea a avut succes, false altfel.
 */
bool deleteExpense(Repo* repo, Expense* expense, Error* error);

/**
 * Returnează numărul total de cheltuieli din repository.
 * @param repo Repository-ul care conține cheltuielile.
 * @return Numărul de cheltuieli din repository.
 */
int getRepoLength(Repo* repo);

/**
 * Returnează o cheltuială de la o anumită poziție.
 * @param repo Repository-ul care conține cheltuielile.
 * @param pos Poziția cheltuielii în listă.
 * @return Pointer la cheltuiala de la poziția dată, sau NULL dacă poziția este invalidă.
 */
Expense* getExpense(Repo* repo, int pos);

/**
 * Returnează lista de cheltuieli din repository.
 * @param repo Repository-ul care conține cheltuielile.
 * @return Un pointer la lista de cheltuieli.
 */
List* getExpenses(Repo* repo);


#endif //REPO_H
