#include <iostream>

#include "problema 4.h"
#include "problema1.h"
#include "problema2.h"
#include "problema3.h"
#include "problema5.h"

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
int main() {
    while (true) {
       int cmd=0;
        std::cout<<"1.Problema 1\n2.Problema 2\n3.Problema 3\n4.Problema 4\n5.Problema 5.\n0.Exit\n";
        std::cin>>cmd;
        switch (cmd) {
            case 1:
                problema1();
                break;
            case 2:
                problema2();
                break;
            case 3:
                problema3();
                break;
            case 4:
                problema4();
                break;
            case 5:
                problema5();
                break;
            case 0:
                return 0;
            default:
                std::cout<<"Comanda invalida\n";
        }
    }

}

// TIP See CLion help at <a
// href="https://www.jetbrains.com/help/clion/">jetbrains.com/help/clion/</a>.
//  Also, you can try interactive lessons for CLion by selecting
//  'Help | Learn IDE Features' from the main menu.