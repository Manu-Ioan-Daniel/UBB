//
// Created by Andra Mateș on 15.03.2025.
//

#ifndef ERROR_H
#define ERROR_H

typedef struct {
    char errorMessage[1000];
} Error;

/**
 * This function sets the message for an Error object.
 * @param error Error object
 * @param errorMessage char*
 */
void setError(Error *error, char* errorMessage);

#endif //ERROR_H
