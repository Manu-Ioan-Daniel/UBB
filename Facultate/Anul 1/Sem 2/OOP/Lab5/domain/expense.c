//
// Created by Andra Mateș on 15.03.2025.
//
#include "expense.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

Expense* createExpense(int apNumber, double sum, char* type) {
    Expense* expense = (Expense*)malloc(sizeof(Expense));
    if (expense == NULL) return NULL;

    expense->apNumber = apNumber;
    expense->sum = sum;

    expense->type = (char*)malloc(sizeof(char)*strlen(type) + 1);
    strcpy(expense->type, type);

    return expense;
}

void destroyExpense(Expense* expense) {
    if (expense == NULL) return;
    free(expense->type);
    expense->type = NULL;
    free(expense);
    expense = NULL;
}

Expense* copyExpense(Expense* expense) {
    if (expense == NULL) return NULL;
    Expense* newExpense = (Expense*)malloc(sizeof(Expense));
    if (newExpense == NULL) return NULL;

    newExpense->type = (char*)malloc(sizeof(char)*strlen(expense->type) + 1);
    if (newExpense->type == NULL) {
        free(newExpense);
        return NULL;
    }
    strcpy(newExpense->type, expense->type);

    newExpense->apNumber = expense->apNumber;
    newExpense->sum = expense->sum;

    return newExpense;
}

int getApNumber(Expense* expense) {
    return expense->apNumber;
}
double getSum(Expense* expense) {
    return expense->sum;
}
char* getType(Expense* expense) {
    return expense->type;
}