#include <iostream>

struct Cerc{
    double raza;
};

int main(){
    Cerc a;
    std::cout<<"Introdu raza: "<<std::endl;
    std::cin>>a.raza;
    double perimetru = 2 * 3.14 * a.raza;
    double arie = 3.14 * a.raza * a.raza;
    std::cout<<"Arie: "<<arie<<" Perimetru: "<<perimetru<<std::endl;
    return 0;
}