#ifndef ERRORS_H
#define ERRORS_H

extern char RepoError[256];
extern char ValidationError[256];

void addRepoError(char* error);
void addValidationError(char* error);
void clearRepoError();
void clearValidationError();

#endif // ERRORS_H