#include <iostream>

int main(){
    int n;
    int i;
    int x;
    int suma;
    suma = 0;
    std::cout<<"Introdu n: "<<std::endl;
    std::cin>>n;
    i = 1;
    while(i <= n){
        std::cout<<"Introdu numarul: "<<std::endl;
        std::cin>>x;
        suma = suma + x;
        i = i + 1;
    }
    std::cout<<"Suma: "<<suma<<std::endl;
    return 0;
}