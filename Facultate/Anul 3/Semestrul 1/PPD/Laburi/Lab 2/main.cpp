#include <iostream>
#include <vector>
#include <thread>
#include <chrono>
#include "MyQueue.h"

void producer(const std::vector<int>& v1, const std::vector<int>& v2, MyQueue<int>& q) {
    for (int i = 0; i < static_cast<int>(v1.size()); i++) {
        q.push(v1[i] * v2[i]);
    }
}

void consumer(const int n, MyQueue<int>& q, long long& sum) {
    sum = 0;
    for (int i = 0; i < n; i++) {
        sum += q.pop();
    }
}

int main() {
    std::vector v1 = {1, 2, 3};
    std::vector v2 = {3, 2, 1};

    if (v1.size() != v2.size()) {
        std::cout << "Ce faci brothere?\n";
        return 1;
    }

    MyQueue<int> q(2);
    long long sum = 0;

    std::thread t1(producer, std::ref(v1), std::ref(v2), std::ref(q));
    std::thread t2(consumer, static_cast<int>(v1.size()), std::ref(q), std::ref(sum));
    t1.join();
    t2.join();

    std::cout << "Produs scalar: " << sum << "\n";

    int n = 100000;
    std::vector<int> big1(n);
    std::vector<int> big2(n);
    for (int i = 0; i < n; i++) {
        big1[i] = rand() % 10;
        big2[i] = rand() % 10;
    }

    for (std::vector queueSizes = {1, 10, 50, 100, 1000, 10000}; int qSize : queueSizes) {
        MyQueue<int> bigQ(qSize);
        long long bigSum = 0;
        auto start = std::chrono::high_resolution_clock::now();
        std::thread tp(producer, std::ref(big1), std::ref(big2), std::ref(bigQ));
        std::thread tc(consumer, n, std::ref(bigQ), std::ref(bigSum));
        tp.join();
        tc.join();
        auto end = std::chrono::high_resolution_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();
        std::cout << "Coada marime " << qSize << ": " << duration << " ms (suma = " << bigSum << ")\n";
    }

    return 0;
}
