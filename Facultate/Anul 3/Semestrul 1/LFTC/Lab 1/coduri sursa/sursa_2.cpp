#include <iostream>

int main(){
    int a;
    int b;
    std::cout<<"Introdu a: "<<std::endl;
    std::cin>>a;
    std::cout<<"Introdu b: "<<std::endl;
    std::cin>>b;
    while(a != b){
        if(a > b){
            a = a - b;
        }
        else{
            b = b - a;
        }
    }
    std::cout<<"CMMDC: "<<a<<std::endl;
    return 0;
}