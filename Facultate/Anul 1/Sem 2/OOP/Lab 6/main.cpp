
#include "ui.h"

int main() {

    testRepo();
    testService();
    Repo r;
    Validator v;
    Service s{r,v};
    const Ui ui{s};
    ui.startUi();

    return 0;
}

