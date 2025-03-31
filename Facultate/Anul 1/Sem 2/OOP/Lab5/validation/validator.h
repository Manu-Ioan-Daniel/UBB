//
// Created by Andra Mateș on 15.03.2025.
//

#ifndef VALIDATOR_H
#define VALIDATOR_H

#include <stdbool.h>
#include "../domain/expense.h"
#include "../errors/error.h"

/**
 * This function validates an Expense object
 * @param expense Expense object
 * @param error Error object
 * @return True if there are no errors, False otherwise
 */
bool validateExpense(Expense* expense, Error *error);

#endif //VALIDATOR_H
