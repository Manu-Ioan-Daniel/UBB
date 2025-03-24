//
// Created by Andra Mateș on 15.03.2025.
//
#define CAPACITY 10
#include "service.h"

#include <stdio.h>

#include "../validation/validator.h"

#include <stdlib.h>
#include <string.h>

Service* createService() {
    Service* service = (Service*)malloc(sizeof(Service));
    service->repo = createRepo();
    service->undo_list=createList(CAPACITY,destroyList,copyList);
    return service;
}

void destroyService(Service* service) {
    if (service == NULL) return;
    if (service->repo != NULL) {
        destroyRepo(service->repo);
        service->repo = NULL;
    }
    if (service->undo_list != NULL) {
        destroyList(service->undo_list);
    }
    free(service);
}

bool addExpenseService(Service* service, int apNumber, double sum, char* type, Error* error) {
    Expense* expense = createExpense(apNumber, sum, type);

    if (!validateExpense(expense, error)) {
        destroyExpense(expense);
        return false;
    }
    List* list = copyList(service->repo->expenses);
    add(service->undo_list, list);
    bool response = addExpense(service->repo, expense, error);
    if (!response) {
        delete(service->undo_list,service->undo_list->length-1);
    }
    destroyExpense(expense);
    return response;
}

bool modifyExpenseService(Service* service, int oldApNumber, char* oldType, double newSum, char* newType, Error* error) {
    Expense* newExpense = createExpense(oldApNumber, newSum, newType);

    if (!validateExpense(newExpense, error)) {
        destroyExpense(newExpense);
        return false;
    }
    List* list = copyList(service->repo->expenses);
    add(service->undo_list, list);
    bool response = modifyExpense(service->repo, newExpense, oldApNumber, oldType, error);
    if (!response) {
        delete(service->undo_list,service->undo_list->length-1);
    }
    destroyExpense(newExpense);
    return response;
}

bool deleteExpenseService(Service* service, int apNumber, char* type, Error* error) {
    Expense* expense = createExpense(apNumber, 1, type);

    if (!validateExpense(expense, error)) {
        destroyExpense(expense);
        return false;
    }
    List* list = copyList(service->repo->expenses);
    add(service->undo_list, list);
    bool response = deleteExpense(service->repo, expense, error);
    if (!response) {
        delete(service->undo_list,service->undo_list->length-1);
    }
    destroyExpense(expense);

    return response;
}

List* filterExpensesByAp(Service* service, int apNumber) {
    List* filteredExpenses = createList(CAPACITY, (DestroyFunc) destroyExpense, (CopyFunc) copyExpense);
    for (int i = 0; i < getLength(service->repo->expenses); i++) {
        // Expense* expense = get(service->repo->expenses, i);
        Expense* expense = service->repo->expenses->elems[i];
        Expense* copy = copyExpense(expense);
        if (getApNumber(copy) == apNumber) {
            add(filteredExpenses, copy);
        }
        else {
            destroyExpense(copy);
        }
    }
    return filteredExpenses;
}

List* filterExpensesBySum(Service* service, double sum) {
    List* filteredExpenses = createList(CAPACITY, (DestroyFunc) destroyExpense, (CopyFunc) copyExpense);
    for (int i = 0; i < getLength(service->repo->expenses); i++) {
        // Expense* expense = get(service->repo->expenses, i);
        Expense* expense = service->repo->expenses->elems[i];
        Expense* copy = copyExpense(expense);
        if (getSum(copy) == sum) {
            add(filteredExpenses, copy);
        }
        else {
            destroyExpense(copy);
        }
    }
    return filteredExpenses;
}

List* filterExpensesByType(Service* service, char* type) {
    List* filteredExpenses = createList(CAPACITY, (DestroyFunc) destroyExpense, (CopyFunc) copyExpense);
    for (int i = 0; i < getLength(service->repo->expenses); i++) {
        // Expense* expense = get(service->repo->expenses, i);
        Expense* expense = service->repo->expenses->elems[i];
        Expense* copy = copyExpense(expense);
        if (strcmp(getType(copy), type) == 0) {
            add(filteredExpenses, copy);
        }
        else {
            destroyExpense(copy);
        }
    }
    return filteredExpenses;
}

int compareBySumAsc(const void* a, const void* b) {
    Expense* expenseA = *(Expense**)a;
    Expense* expenseB = *(Expense**)b;
    return (getSum(expenseA) > getSum(expenseB)) - (getSum(expenseA) < getSum(expenseB));
}

int compareBySumDesc(const void* a, const void* b) {
    Expense* expenseA = *(Expense**)a;
    Expense* expenseB = *(Expense**)b;
    return (getSum(expenseB) > getSum(expenseA)) - (getSum(expenseB) < getSum(expenseA));
}

int compareByTypeAsc(const void* a, const void* b) {
    Expense* expenseA = *(Expense**)a;
    Expense* expenseB = *(Expense**)b;
    return strcmp(getType(expenseA), getType(expenseB));
}

int compareByTypeDesc(const void* a, const void* b) {
    Expense* expenseA = *(Expense**)a;
    Expense* expenseB = *(Expense**)b;
    return strcmp(getType(expenseB), getType(expenseA));
}

List* sortExpenses(Service* service, int criteria, bool ascending) {
    List* expenses = copyList(service->repo->expenses);
    if (criteria == 1) { //pt suma
        if (ascending) {
            qsort(expenses->elems, getLength(expenses), sizeof(Expense*), compareBySumAsc);
        }
        else {
            qsort(expenses->elems, getLength(expenses), sizeof(Expense*), compareBySumDesc);
        }
    }
    else if (criteria == 2) {
        if (ascending) {
            qsort(expenses->elems, getLength(expenses), sizeof(Expense*), compareByTypeAsc);
        }
        else {
            qsort(expenses->elems, getLength(expenses), sizeof(Expense*), compareByTypeDesc);
        }
    }
    return expenses;
}
bool undo(Service* service) {
    if (service->undo_list->length==0) {
        return false;
    }
    destroyList(service->repo->expenses);
    List* previousState=service->undo_list->elems[getLength(service->undo_list)-1];
    service->repo->expenses=copyList(previousState);
    delete(service->undo_list,getLength(service->undo_list)-1);
    return true;
}

Repo* getRepo(Service* service) {
    return service->repo;
}

List* getExpensesService(Service* service) {
    return getExpenses(service->repo);
}