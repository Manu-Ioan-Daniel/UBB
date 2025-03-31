//
// Created by Andra Mateș on 15.03.2025.
//
#include "error.h"
#include <string.h>

void setError(Error *error, char* errorMessage) {
    strcat(error->errorMessage, errorMessage);
}