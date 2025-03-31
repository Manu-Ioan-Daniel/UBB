//
// Created by Andra Mateș on 15.03.2025.
//
#include "repo.h"
#include "../domain/expense.h"
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#define CAPACITY 10

Repo* createRepo() {
    Repo* repo = (Repo*) malloc(sizeof(Repo));
    if (repo == NULL) return NULL;

    repo->expenses = createList(CAPACITY, (DestroyFunc) destroyExpense, (CopyFunc) copyExpense);
    if (repo->expenses == NULL) {
        free(repo);
        return NULL;
    }

    return repo;
}

void destroyRepo(Repo* repo) {
    if (repo == NULL) return;
    if (repo->expenses != NULL) {
        destroyList(repo->expenses);
        repo->expenses = NULL;
    }
    free(repo);
}

int searchPos(Repo* repo, int apNumber, char* type) {
    if (repo == NULL) return -1;
    for (int i = 0; i < getLength(repo->expenses); i++) {
        Expense* expense = get(repo->expenses, i);
        if (strcmp(expense->type, type) == 0 && expense->apNumber == apNumber) {
            return i;
        }
    }
    return -1;
}

bool addExpense(Repo* repo, Expense* expense, Error* error) {
    if (repo == NULL || expense == NULL) return false;

    int foundPos = searchPos(repo, expense->apNumber, expense->type);
    if (foundPos != -1) {
        error->errorMessage[0] = '\0';
        setError(error, "eroare de repo: exista deja o cheltuiala de acest tip a acestui apartament!\n");
        return false;
    }

    Expense* copy = copyExpense(expense);
    add(repo->expenses, copy);
    return true;
}

bool modifyExpense(Repo* repo, Expense* expense, int oldApNumber, char* oldType, Error* error) {
    if (repo == NULL || expense == NULL) return false;
    //verific daca exista cheltuiala
    int foundPos = searchPos(repo, oldApNumber, oldType);
    if (foundPos == -1) { // daca nu o gaseste
        error->errorMessage[0] = '\0';
        setError(error, "eroare de repo: nu exista aceasta cheltuiala!\n");
        return false;
    }
    //verific daca cheltuiala noua exista deja
    int duplicatePos = searchPos(repo, getApNumber(expense), getType(expense));
    if (duplicatePos != -1 && duplicatePos != foundPos) { // daca exista deja
        error->errorMessage[0] = '\0';
        setError(error, "eroare de repo: exista deja o cheltuiala de acest tip a acestui apartament!\n");
        return false;
    }
    Expense* oldExpense = get(repo->expenses, foundPos);
    destroyExpense(oldExpense);

    Expense* copy = copyExpense(expense);
    repo->expenses->elems[foundPos] = copy;
    return true;
}

bool deleteExpense(Repo* repo, Expense* expense, Error* error) {
    if (repo == NULL || expense == NULL) return false;

    int foundPos = searchPos(repo, getApNumber(expense), getType(expense));
    if (foundPos == -1) {
        error->errorMessage[0] = '\0';
        setError(error, "eroare de repo: nu exista aceasta cheltuiala!\n");
        return false;
    }

    delete(repo->expenses, foundPos);
    return true;
}

int getRepoLength(Repo* repo) {
    if (repo == NULL) return -1;
    return getLength(repo->expenses);
}

List* getExpenses(Repo* repo) {
    if (repo == NULL) return NULL;
    return copyList(repo->expenses);
}

