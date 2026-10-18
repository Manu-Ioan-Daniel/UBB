#include <iostream>
#include <thread>
#include <vector>

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

void product(const std::vector<std::vector<int>>& a,
             const std::vector<std::vector<int>>& b,
             std::vector<std::vector<int>>& result,
             const int threadNo) {

    const int total_elements = n * n;
    const int chunk_size = total_elements / nr_threads;
    const int start = threadNo * chunk_size;
    const int end = threadNo == nr_threads - 1 ? total_elements : start + chunk_size;
    for (int k = start; k < end; k++) {
        const int i = k/n;
        const int j = k%n;
        result[i][j] = calculateOneElement(a, b, i, j);
        std::cout<<"Thread with number: "<< threadNo << "is done calculating element[" << i << "][" << j << "]:" << result[i][j] << std::endl;
    }
}

int main() {

    int testCases[5][2] =
    {
        {2,3},
        {3,4},
        {3,3},
        {3,5},
        {4,5},
    };
    for (const auto& pair: testCases) {
        n = pair[0];
        nr_threads = pair[1];
        std::vector a(n, std::vector<int>(n));
        std::vector b(n, std::vector<int>(n));
        std::vector result(n, std::vector(n, 0));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = rand()%10;
                b[i][j] = rand()%10;
            }
        }
        std::vector<std::thread> threads;
        threads.reserve(nr_threads);
        auto start = std::chrono::high_resolution_clock::now();
        for (int i = 0; i < nr_threads; i++) {
            threads.emplace_back(product, std::ref(a), std::ref(b), result, i);
        }
        for (auto& t : threads) {
            t.join();
        }
        auto end = std::chrono::high_resolution_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);
        std::cout<<"Execution took " << duration.count() << " ms with n = " << n << " and number of threads = " << nr_threads << "\n";
    }

    return 0;
}