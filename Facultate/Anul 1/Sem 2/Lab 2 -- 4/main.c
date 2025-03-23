
#include "ui/ui.h"
void runTests() {
    testeService();
    testValidator();
    testRepo();
    testList();

}
int main(void){
    runTests();
    Lista* l=createList();
    run(l);
    destroyList(l);
    return 0;
}
