//
// Created by Andra Mateș on 15.03.2025.
//
#include "ui.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void uiPrintMenu() {
    printf("Meniu Administrator imobil\n");
    printf("1. Adauga cheltuiala\n");
    printf("2. Modifica cheltuiala\n");
    printf("3. Sterge cheltuiala\n");
    printf("4. Filtrare cheltuieli\n");
    printf("5. Sorteaza cheltuieli\n");
    printf("6. Afiseaza toate cheltuielile\n");
    printf("7.Undo\n");
    printf("0. Iesire\n");
}
void uiUndo(Service* service) {
    undo(service);
}

bool uiValidateInputTypeInt(char input[], Error* error) {
    int number = atoi(input);
    if (number == 0) {
        setError(error, "value error: input invalid!\n");
        return false;
    }
    return true;
}

bool uiValidateInputTypeDouble(char input[], Error* error) {
    double number = atof(input);
    if (number == 0) {
        setError(error, "value error: input invalid!\n");
        return false;
    }
    return true;
}

void uiAddExpense(Service* service) {
    int apNumber;
    double sum;
    char type[20];
    char input[20];
    Error error;

    // printf("Introduceti numarul apartamentului: ");
    // scanf("%d", &apNumber);
    // printf("Introduceti suma: ");
    // scanf("%lf", &sum);
    // printf("Introduceti tipul (apa, canal, incalzire, gaz): ");
    // scanf("%s", type);

    printf("Introduceti numarul apartamentului: ");
    scanf("%s", input);
    if (uiValidateInputTypeInt(input, &error)) {
        apNumber = atoi(input);
    }
    else {
        printf("%s\n", error.errorMessage);
        return;
    }
    printf("Introduceti suma: ");
    scanf("%s", input);
    if (uiValidateInputTypeDouble(input, &error)) {
        sum = atof(input);
    }
    else {
        printf("%s\n", error.errorMessage);
        return;
    }
    printf("Introduceti tipul (apa, canal, incalzire, gaz): ");
    scanf("%s", type);

    if (addExpenseService(service, apNumber, sum, type, &error)) {
        printf("Cheltuiala a fost adaugata cu succes!\n");
    }
    else {
        printf("%s\n", error.errorMessage);
    }
}

void uiModifyExpense(Service* service) {
    int apNumber;
    double newSum;
    char oldType[20], newType[20];
    char input[20];
    Error error;
    printf("Introduceti datele pentru cheltuiala de modificat:\n");
    printf("Introduceti numarul apartamentului: ");
    scanf("%s", input);
    if (uiValidateInputTypeInt(input, &error)) {
        apNumber = atoi(input);
    }
    else {
        printf("%s\n", error.errorMessage);
        return;
    }

    printf("Introduceti tipul cheltuielii: ");
    scanf("%s", oldType);

    printf("Introduceti noile date:\n");

    printf("Introduceti suma: ");
    scanf("%s", input);
    if (uiValidateInputTypeDouble(input, &error)) {
        newSum = atof(input);
    }
    else {
        printf("%s\n", error.errorMessage);
        return;
    }
    printf("Introduceti tipul cheltuielii: ");
    scanf("%s", newType);

    if (modifyExpenseService(service, apNumber, oldType, newSum, newType, &error)) {
        printf("Cheltuiala a fost modificata cu succes!\n");
    }
    else {
        printf("%s\n", error.errorMessage);
    }

}

void uiDeleteExpense(Service* service) {
    int apNumber;
    char type[20];
    char input[20];
    Error error;
    printf("Introduceti numarul apartamentului: ");
    scanf("%s", input);
    if (uiValidateInputTypeInt(input, &error)) {
        apNumber = atoi(input);
    }
    else {
        printf("%s\n", error.errorMessage);
        return;
    }
    printf("Introduceti tipul cheltuielii: ");
    scanf("%s", type);
    if (deleteExpenseService(service, apNumber, type, &error)) {
        printf("Cheltuiala a fost stearsa cu succes!\n");
    }
    else {
        printf("%s\n", error.errorMessage);
    }
}

void uiPrintList(Service* service, List* expenses) {
    if (getLength(expenses) == 0) {
        printf("Nu exista nicio cheltuiala!\n");
    }
    else {
        printf("Lista de cheltuieli este:\n");
        for (int i = 0; i < getLength(expenses); i++) {
            printf("nr. ap.: %d, suma: %.2f, tip: %s\n", getApNumber(get(expenses, i)), getSum(get(expenses, i)), getType(get(expenses, i)));
        }
    }
}

void uiFilterByApartment(Service* service) {
    int apNumber;
    char input[20];
    Error error;
    printf("Introduceti numarul apartamentului: ");
    scanf("%s", input);
    if (uiValidateInputTypeInt(input, &error)) {
        apNumber = atoi(input);
    }
    else {
        printf("%s\n", error.errorMessage);
        return;
    }
    List* filteredExpenses = filterExpensesByAp(service, apNumber);
    uiPrintList(service, filteredExpenses);
    destroyList(filteredExpenses);
}

void uiFilterBySum(Service* service) {
    double sum;
    char input[20];
    Error error;
    printf("Introduceti suma: ");
    scanf("%s", input);
    if (uiValidateInputTypeDouble(input, &error)) {
        sum = atof(input);
    }
    else {
        printf("%s\n", error.errorMessage);
        return;
    }

    List* filteredExpenses = filterExpensesBySum(service, sum);
    uiPrintList(service, filteredExpenses);
    destroyList(filteredExpenses);
}

void uiFilterByType(Service* service) {
    char type[20];
    printf("Introduceti tipul cheltuielii: ");
    scanf("%s", type);

    List* filteredExpenses = filterExpensesByType(service, type);
    uiPrintList(service, filteredExpenses);
    destroyList(filteredExpenses);
}

void uiFilter(Service* service) {
    int choice;
    printf("Alegeti criteriul de filtrare:\n");
    printf("1. Filtrare dupa apartament\n");
    printf("2. Filtrare dupa suma\n");
    printf("3. Filtrare dupa tip\n");
    printf("Optiune: ");
    scanf("%d", &choice);

    switch (choice) {
        case 1:
            uiFilterByApartment(service);
        break;
        case 2:
            uiFilterBySum(service);
            break;
        case 3:
            uiFilterByType(service);
            break;
        default:
            printf("Optiune invalida!\n");
            break;
    }
}

void uiSort(Service* service) {
    int choice, criteria;
    bool ascending;

    printf("Cum vrei sa sortezi cheltuielile?\n");
    printf("1. Dupa suma\n");
    printf("2. Dupa tip\n");
    printf("Alege o optiune: ");
    scanf("%d", &criteria);

    printf("Cum vrei sa fie ordonat?\n");
    printf("1. Crescator\n");
    printf("2. Descrescator\n");
    printf("Alege o optiune: ");
    scanf("%d", &choice);
    ascending = (choice == 1);

    List* sortedExpenses = sortExpenses(service, criteria, ascending);
    uiPrintList(service, sortedExpenses);
    destroyList(sortedExpenses);
}

void uiPrintExpenses(Service* service) {
    List* expenses = getExpensesService(service);

    if (getLength(expenses) == 0) {
        printf("Nu exista nicio cheltuiala!\n");
    }
    else {
        printf("Lista de cheltuieli este:\n");
        for (int i = 0; i < getLength(expenses); i++) {
            printf("nr. ap.: %d, suma: %.2f, tip: %s\n", getApNumber(get(expenses, i)), getSum(get(expenses, i)), getType(get(expenses, i)));
        }
    }
    destroyList(expenses);
}

void runApp() {
    Service* service = createService();
    char input[20];
    int cmd;
    do {
        uiPrintMenu();
        printf("Introduceti comanda: ");
        scanf("%s", &input);
        cmd = atoi(input);
        if (input[0] != '0' && cmd == 0) cmd = -1;
        switch (cmd) {
            case 1:
                uiAddExpense(service);
                break;
            case 2:
                uiModifyExpense(service);
                break;
            case 3:
                uiDeleteExpense(service);
                break;
            case 4:
                uiFilter(service);
                break;
            case 5:
                uiSort(service);
                break;
            case 6:
                uiPrintExpenses(service);
                break;
            case 7:
                uiUndo(service);
                break;
            case 0:
                break;
            default:
                printf("Comanda invalida!\n");
        }
    } while (cmd != 0);
    destroyService(service);
    printf("Gata!!!!!!!!\n");
}