#include <iostream>
#include <thread>
#include <optional>
#include "MyQueue.h"

auto queue = MyQueue<int>();
std::vector v1 = {1,2,3};
std::vector v2 = {3,2,1};

void producer() {

}

void consumer() {

}

int main() {

    if (v1.size() != v2.size()) {
        std::cout<<"Ce faci brothere?";
        return 1;
    }
    return 0;
}
