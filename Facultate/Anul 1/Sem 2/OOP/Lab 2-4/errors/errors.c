#include "errors.h"
#include <string.h>

char RepoError[256] = "";
char ValidationError[256] = "";

void addRepoError(char* error) {
    strcat(RepoError, error);
}

void addValidationError(char* error) {
    strcat(ValidationError, error);
}
void clearRepoError() {
    RepoError[0] = '\0';
}

void clearValidationError() {
    ValidationError[0] = '\0';
}