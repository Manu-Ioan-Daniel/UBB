
#include <iostream>
#include <thread>
#include <vector>
#include <chrono>
#include <cstdlib>
#include <functional>

int n;
int nr_threads;

int calculateOneElement(const std::vector<std::vector<int>>& a,
                        const std::vector<std::vector<int>>& b,
                        const int i, const int j) {
    int sum = 0;
    for (int k = 0; k < n; k++) {
        sum += a[i][k] * b[k][j];
    }
    return sum;
}
//metoda 1
void product(const std::vector<std::vector<int>>& a,
             const std::vector<std::vector<int>>& b,
             std::vector<std::vector<int>>& result,
             const int threadNo) {

    const int total_elements = n * n;
    const int chunk_size = total_elements / nr_threads;
    const int start = threadNo * chunk_size;
    const int end = threadNo == nr_threads - 1 ? total_elements : start + chunk_size;

    for (int k = start; k < end; k++) {
        const int i = k / n;
        const int j = k % n;
        result[i][j] = calculateOneElement(a, b, i, j);
    }
}
//metoda 2
void product2(const std::vector<std::vector<int>>& a,
              const std::vector<std::vector<int>>& b,
              std::vector<std::vector<int>>& result,
              const int threadNo) {

    const int total_elements = n * n;
    const int chunk_size = total_elements / nr_threads;
    const int start = threadNo * chunk_size;
    const int end = threadNo == nr_threads - 1 ? total_elements : start + chunk_size;

    for (int k = start; k < end; k++) {
        const int i = k % n;
        const int j = k / n;
        result[i][j] = calculateOneElement(a, b, i, j);
    }
}

//metoda 3
void product3(const std::vector<std::vector<int>>& a,
              const std::vector<std::vector<int>>& b,
              std::vector<std::vector<int>>& result,
              const int threadNo) {

    const int total_elements = n * n;

    for (int k = threadNo; k < total_elements; k += nr_threads) {
        const int i = k / n;
        const int j = k % n;
        result[i][j] = calculateOneElement(a, b, i, j);
    }
}

int main() {

    int testCases[4][2] =
    {
        {4, 5},
        {9, 4},
        {100, 4},
        {300, 4}
    };

    for (const auto& pair : testCases) {
        n = pair[0];
        nr_threads = pair[1];

        std::vector a(n, std::vector<int>(n));
        std::vector b(n, std::vector<int>(n));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = rand() % 10;
                b[i][j] = rand() % 10;
            }
        }

        std::cout << "Testing n = " << n
                  << " with " << nr_threads << " threads\n";

        {
            std::vector result(n, std::vector(n, 0));
            std::vector<std::thread> threads;
            threads.reserve(nr_threads);

            auto start = std::chrono::high_resolution_clock::now();

            for (int i = 0; i < nr_threads; i++) {
                threads.emplace_back(product, std::ref(a), std::ref(b),
                                     std::ref(result), i);
            }

            for (auto& t : threads) {
                t.join();
            }

            auto end = std::chrono::high_resolution_clock::now();
            auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

            std::cout << "Method 1 (row by row): "
                      << duration.count() << " ms\n";
        }

        {
            std::vector result(n, std::vector(n, 0));
            std::vector<std::thread> threads;
            threads.reserve(nr_threads);

            auto start = std::chrono::high_resolution_clock::now();

            for (int i = 0; i < nr_threads; i++) {
                threads.emplace_back(product2, std::ref(a), std::ref(b),
                                     std::ref(result), i);
            }

            for (auto& t : threads) {
                t.join();
            }

            auto end = std::chrono::high_resolution_clock::now();
            auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

            std::cout << "Method 2 (column by column): "
                      << duration.count() << " ms\n";
        }

        {
            std::vector result(n, std::vector(n, 0));
            std::vector<std::thread> threads;
            threads.reserve(nr_threads);

            auto start = std::chrono::high_resolution_clock::now();

            for (int i = 0; i < nr_threads; i++) {
                threads.emplace_back(product3, std::ref(a), std::ref(b),
                                     std::ref(result), i);
            }

            for (auto& t : threads) {
                t.join();
            }

            auto end = std::chrono::high_resolution_clock::now();
            auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

            std::cout << "Method 3 (every k-th element): "
                      << duration.count() << " ms\n";
        }

        std::cout << "\n";
    }

    return 0;
}
