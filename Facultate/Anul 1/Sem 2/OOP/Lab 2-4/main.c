#include <stdio.h>
#include "ui/ui.h"
void runTests();
int main(void){
    runTests();
    Lista* l=malloc(sizeof(Lista));
    createList(l);
    run(l);
    free(l);
    return 0;
}
void runTests() {
    testeService();
    testValidator();
    testRepo();
    testList();
    clearValidationError();
    clearRepoError();
}

