//
// Created by Andra Mateș on 15.03.2025.
//

#ifndef EXPENSE_H
#define EXPENSE_H

typedef struct {
    int apNumber;
    double sum;
    char* type;
} Expense;

/**
 * Creează o nouă cheltuială.
 * @param apNumber Numărul apartamentului.
 * @param sum Suma cheltuită.
 * @param type Tipul cheltuielii.
 * @return Pointer la structura nou creată de tip Expense.
 */
Expense* createExpense(int apNumber, double sum, char* type);

/**
 * Distruge o cheltuială și eliberează memoria alocată.
 * @param expense Cheltuiala care trebuie distrusă.
 */
void destroyExpense(Expense* expense);

/**
 * Creează o copie a unei cheltuieli.
 * @param expense Cheltuiala care trebuie copiată.
 * @return Pointer la o nouă cheltuială identică cu cea originală.
 */
Expense* copyExpense(Expense* expense);

//getters and setters
int getApNumber(Expense* expense);
double getSum(Expense* expense);
char* getType(Expense* expense);

#endif //EXPENSE_H
