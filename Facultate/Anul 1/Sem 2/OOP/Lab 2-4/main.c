#include <stdio.h>
#include "ui/ui.h"
void runTests();
int main(void){
    runTests();
    Lista* l=createList();
    run(l);
    destroyList(l);
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

