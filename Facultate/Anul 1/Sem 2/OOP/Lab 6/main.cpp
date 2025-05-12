
#include "ui.h"

#include <QApplication>



int main(int argc,char* argv[]) {
    QApplication app(argc,argv);
    testRepo();
    testService();
    testDisciplina();
    Repo r;
    Validator v;
    Service s{r,v};
    Ui ui{s,v};
    ui.show();
    return QApplication::exec();
}

