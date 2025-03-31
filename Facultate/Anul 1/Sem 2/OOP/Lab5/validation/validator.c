//
// Created by Andra Mateș on 09.03.2025.
//
#include "validator.h"
#include <string.h>
#include <stdbool.h>
#include "../domain/expense.h"
#include "../errors/error.h"

bool validateExpense(Expense* expense, Error *error) {
    error->errorMessage[0] = '\0';
    if (getApNumber(expense) < 0) {
        setError(error, "eroare de validare: nr apartament invalid!\n");
    }
    if (getSum(expense)< 0) {
        setError(error, "eroare de validare: suma invalida!\n");
    }
    char *type = getType(expense);
    if (strcmp(type, "apa") != 0 && strcmp(type, "canal") != 0
        && strcmp(type, "incalzire") != 0
        && strcmp(type, "gaz") != 0) {
        setError(error, "eroare de validare: tip invalid!\n");
        }
    if (error->errorMessage[0] != '\0') {
        return false;
    }
    return true;
}