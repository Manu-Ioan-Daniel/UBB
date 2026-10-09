#include <iostream>

int main(){
    int a, b, suma;
    std::cout << "Introdu a: " << std::endl;
    std::cin >> a;
    std::cout << "Introdu b: " << std::endl;
    std::cin >> b;
    
    if(a > b)
        suma = a + b;
    else
        suma = a - b;
        
    std::cout << "Rezultat: " << suma << std::endl;
}