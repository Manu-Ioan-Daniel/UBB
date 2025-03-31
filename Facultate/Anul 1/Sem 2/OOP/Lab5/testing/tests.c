//
// Created by Andra Mateș on 17.03.2025.
//
#include "tests.h"
#include "../utils/list.h"
#include "../domain/expense.h"
#include "../validation/validator.h"
#include "../infrastructure/repo.h"
#include "../business/service.h"

#include <stdio.h>
#include <string.h>
#include <assert.h>

void testList() {
    List* list = createList(2, (DestroyFunc) destroyExpense, (CopyFunc) copyExpense);
    assert(list != NULL);
    assert(getLength(list) == 0);
    assert(getCapacity(list) == 2);

    Expense* exp1 = createExpense(1, 100.0, "apa");
    Expense* exp2 = createExpense(2, 200.0, "gaz");
    add(list, exp1);
    add(list, exp2);

    assert(getLength(list) == 2);
    assert(get(list, 0) == exp1);
    assert(get(list, 1) == exp2);

    Expense* exp3 = createExpense(3, 150.0, "incalzire");
    add(list, exp3);
    assert(getLength(list) == 3);
    assert(getCapacity(list) == 4);

    delete(list, 1);
    assert(getLength(list) == 2);
    assert(get(list, 0) == exp1);
    assert(get(list, 1) == exp3);

    List* copy = copyList(list);
    assert(copy != NULL);
    assert(getLength(copy) == getLength(list));
    assert(getCapacity(copy) == getCapacity(list));

    // Verifică dacă elementele copiate sunt copii separate (nu aceleași adrese)
    assert(copy->elems[0] != list->elems[0]);
    assert(copy->elems[1] != list->elems[1]);

    destroyList(list);
    destroyList(copy);
    printf("test list passed!\n");
}


void testDomain() {
    Expense* e1 = createExpense(10, 100.5, "apa");
    assert(e1 != NULL);
    assert(getApNumber(e1) == 10);
    assert(getSum(e1) == 100.5);
    assert(strcmp(getType(e1), "apa") == 0);

    Expense* e2 = copyExpense(e1);
    assert(e2 != NULL);
    assert(getApNumber(e2) == getApNumber(e1));
    assert(getSum(e2) == getSum(e1));
    assert(strcmp(getType(e2), getType(e1)) == 0);

    destroyExpense(e1);
    destroyExpense(e2);

    printf("test domain passed!\n");
}


void testValidator() {
    Error error;

    Expense* e1 = createExpense(1, 200.0, "apa");
    assert(validateExpense(e1, &error) == true);
    assert(strlen(error.errorMessage) == 0);
    destroyExpense(e1);

    Expense* e2 = createExpense(-1, 200.0, "gaz");
    assert(validateExpense(e2, &error) == false);
    assert(strstr(error.errorMessage, "nr apartament invalid") != NULL);
    destroyExpense(e2);

    Expense* e3 = createExpense(2, -50.0, "incalzire");
    assert(validateExpense(e3, &error) == false);
    assert(strstr(error.errorMessage, "suma invalida") != NULL);
    destroyExpense(e3);

    Expense* e4 = createExpense(3, 100.0, "electricitate");
    assert(validateExpense(e4, &error) == false);
    assert(strstr(error.errorMessage, "tip invalid") != NULL);
    destroyExpense(e4);

    printf("test validator passed!\n");
}

void testRepo() {
    Error error;
    Repo* repo = createRepo();
    assert(getRepoLength(repo) == 0);

    Expense* e1 = createExpense(1, 100.0, "apa");
    Expense* e2 = createExpense(2, 200.0, "gaz");
    Expense* e3 = createExpense(1, 150.0, "gaz");

    assert(addExpense(repo, e1, &error) == true);
    assert(addExpense(repo, e2, &error) == true);
    assert(addExpense(repo, e3, &error) == true);


    assert(getRepoLength(repo) == 3);

    Expense* duplicate = createExpense(1, 120.0, "apa");
    assert(addExpense(repo, duplicate, &error) == false);
    destroyExpense(duplicate);

    assert(searchPos(repo, 1, "apa") != -1);
    assert(searchPos(repo, 2, "gaz") != -1);
    assert(searchPos(repo, 3, "apa") == -1);


    Expense* newExpense = createExpense(1, 180.0, "apa");
    assert(modifyExpense(repo, newExpense, 2, "gaz", &error) == false);
    assert(modifyExpense(repo, newExpense, 1, "apa", &error) == true);
    assert(getSum(get(repo->expenses, searchPos(repo, 1, "apa"))) == 180.0);
    destroyExpense(newExpense);

    assert(deleteExpense(repo, e2, &error) == true);
    assert(getRepoLength(repo) == 2);
    assert(searchPos(repo, 2, "gaz") == -1);

    destroyRepo(repo);
    destroyExpense(e1);
    destroyExpense(e2);
    destroyExpense(e3);

    printf("test repo passed!\n");
}

void testFilterExpensesByAp() {
    Error error;
    Service* service = createService();

    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "incalzire", &error);
    addExpenseService(service, 2, 250.0, "canal", &error);

    List* filteredByAp1 = filterExpensesByAp(service, 1);
    assert(getLength(filteredByAp1) == 2);
    destroyList(filteredByAp1);

    List* filteredByAp2 = filterExpensesByAp(service, 2);
    assert(getLength(filteredByAp2) == 2);
    destroyList(filteredByAp2);

    List* filteredByApNonExistent = filterExpensesByAp(service, 3);
    assert(getLength(filteredByApNonExistent) == 0);
    destroyList(filteredByApNonExistent);

    destroyService(service);
}

void testFilterExpensesBySum() {
    Error error;
    Service* service = createService();

    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "incalzire", &error);
    addExpenseService(service, 2, 200.0, "canal", &error);

    List* filteredBySum200 = filterExpensesBySum(service, 200.0);
    assert(getLength(filteredBySum200) == 2);
    destroyList(filteredBySum200);

    List* filteredBySum150 = filterExpensesBySum(service, 150.0);
    assert(getLength(filteredBySum150) == 1);
    destroyList(filteredBySum150);

    List* filteredBySumNonExistent = filterExpensesBySum(service, 500.0);
    assert(getLength(filteredBySumNonExistent) == 0);
    destroyList(filteredBySumNonExistent);

    destroyService(service);
}

void testFilterExpensesByType() {
    Error error;
    Service* service = createService();

    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "incalzire", &error);
    addExpenseService(service, 2, 250.0, "canal", &error);

    List* filteredByTypeApa = filterExpensesByType(service, "apa");
    assert(getLength(filteredByTypeApa) == 1);
    destroyList(filteredByTypeApa);

    List* filteredByTypeGaz = filterExpensesByType(service, "gaz");
    assert(getLength(filteredByTypeGaz) == 1);
    destroyList(filteredByTypeGaz);

    List* filteredByTypeNonExistent = filterExpensesByType(service, "electricitate");
    assert(getLength(filteredByTypeNonExistent) == 0);
    destroyList(filteredByTypeNonExistent);

    destroyService(service);
}

void testSortExpensesBySumAsc() {
    Error error;
    Service* service = createService();

    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "incalzire", &error);
    addExpenseService(service, 2, 250.0, "canal", &error);

    List* sortedBySumAsc = sortExpenses(service, 1, true);
    assert(getLength(sortedBySumAsc) == 4);
    assert(getSum(get(sortedBySumAsc, 0)) == 100.0);
    assert(getSum(get(sortedBySumAsc, 1)) == 150.0);
    assert(getSum(get(sortedBySumAsc, 2)) == 200.0);
    assert(getSum(get(sortedBySumAsc, 3)) == 250.0);
    destroyList(sortedBySumAsc);

    destroyService(service);
}

void testSortExpensesBySumDesc() {
    Error error;
    Service* service = createService();

    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "incalzire", &error);
    addExpenseService(service, 2, 250.0, "canal", &error);


    List* sortedBySumDesc = sortExpenses(service, 1, false);
    assert(getLength(sortedBySumDesc) == 4);
    assert(getSum(get(sortedBySumDesc, 0)) == 250.0);
    assert(getSum(get(sortedBySumDesc, 1)) == 200.0);
    assert(getSum(get(sortedBySumDesc, 2)) == 150.0);
    assert(getSum(get(sortedBySumDesc, 3)) == 100.0);
    destroyList(sortedBySumDesc);

    destroyService(service);
}

void testSortExpensesByTypeAsc() {
    Error error;
    Service* service = createService();

    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "canal", &error);
    addExpenseService(service, 2, 250.0, "canal", &error);

    List* sortedByTypeAsc = sortExpenses(service, 2, true);
    assert(getLength(sortedByTypeAsc) == 4);
    assert(strcmp(getType(get(sortedByTypeAsc, 0)), "apa") == 0);
    assert(strcmp(getType(get(sortedByTypeAsc, 1)), "canal") == 0);
    assert(strcmp(getType(get(sortedByTypeAsc, 2)), "canal") == 0);
    assert(strcmp(getType(get(sortedByTypeAsc, 3)), "gaz") == 0);
    destroyList(sortedByTypeAsc);

    destroyService(service);
}

void testSortExpensesByTypeDesc() {
    Error error;
    Service* service = createService();

    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "incalzire", &error);
    addExpenseService(service, 2, 250.0, "canal", &error);

    List* sortedByTypeDesc = sortExpenses(service, 2, false);
    assert(getLength(sortedByTypeDesc) == 4);
    assert(strcmp(getType(get(sortedByTypeDesc, 0)), "incalzire") == 0);
    assert(strcmp(getType(get(sortedByTypeDesc, 1)), "gaz") == 0);
    assert(strcmp(getType(get(sortedByTypeDesc, 2)), "canal") == 0);
    assert(strcmp(getType(get(sortedByTypeDesc, 3)), "apa") == 0);
    destroyList(sortedByTypeDesc);

    destroyService(service);
}



void testService() {
    Error error;
    Service* service = createService();

    assert(getRepo(service) != NULL);
    assert(getRepo(service)->expenses != NULL);
    assert(getRepo(service)->expenses->length == 0);

    assert(addExpenseService(service, 1, 100.0, "apa", &error) == true);
    assert(addExpenseService(service, 2, 200.0, "gaz", &error) == true);
    assert(addExpenseService(service, 1, 150.0, "canal", &error) == true);

    assert(addExpenseService(service, 2, 250.0, "elec", &error) == false);

    List* expenses = getExpensesService(service);
    assert(getLength(expenses) == 3);
    destroyList(expenses);


    assert(modifyExpenseService(service, -1, "apa", 200, "ele", &error) == false);

    assert(addExpenseService(service, 1, 100.0, "apa", &error) == false);
    assert(strcmp(error.errorMessage, "eroare de repo: exista deja o cheltuiala de acest tip a acestui apartament!\n") == 0);

    assert(modifyExpenseService(service, 1, "apa", 120.0, "apa", &error) == true);
    assert(modifyExpenseService(service, 2, "gaz", 250.0, "gaz", &error) == true);

    assert(modifyExpenseService(service, 99, "nonexistent", 500.0, "gaz", &error) == false);
    assert(strcmp(error.errorMessage, "eroare de repo: nu exista aceasta cheltuiala!\n") == 0);

    assert(deleteExpenseService(service, -1, "ele", &error) == false);
    assert(deleteExpenseService(service, 1, "apa", &error) == true);

    assert(deleteExpenseService(service, 1, "apa", &error) == false);
    assert(strcmp(error.errorMessage, "eroare de repo: nu exista aceasta cheltuiala!\n") == 0);

    destroyService(service);

    testFilterExpensesByAp();
    testFilterExpensesBySum();
    testFilterExpensesByType();
    testSortExpensesBySumAsc();
    testSortExpensesBySumDesc();
    testSortExpensesByTypeAsc();
    testSortExpensesByTypeDesc();

    printf("test service passed!\n");
}

void testUndo() {
    Service* service = createService();
    Error error;
    addExpenseService(service, 1, 100.0, "apa", &error);
    addExpenseService(service, 2, 200.0, "gaz", &error);
    addExpenseService(service, 1, 150.0, "incalzire", &error);
    addExpenseService(service, 2, 250.0, "canal", &error);
    undo(service);
    assert(getRepoLength(service->repo) == 3);
    undo(service);
    assert(getRepoLength(service->repo) == 2);
    undo(service);
    assert(getRepoLength(service->repo) == 1);
    undo(service);
    assert(getRepoLength(service->repo) == 0);
    assert(undo(service)==false);
    destroyService(service);
}
void runTests() {
    testList();
    testDomain();
    testValidator();
    testRepo();
    testService();
    testUndo();
    printf("\ntest ok toate\n");
}