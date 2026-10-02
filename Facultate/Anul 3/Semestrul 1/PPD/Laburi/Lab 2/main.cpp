#include <iostream>
#include <vector>
#include <queue>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <chrono>
#include <random>

class BoundedQueue {
private:
    size_t capacity;
    std::queue<long long> q;
    std::mutex mtx;
    std::condition_variable cv_producer;
    std::condition_variable cv_consumer;
    bool done = false;

public:
    explicit BoundedQueue(size_t cap) : capacity(cap) {}

    void push(long long val) {
        std::unique_lock<std::mutex> lock(mtx);
        cv_producer.wait(lock, [this]() {
            return q.size() < capacity;
        });

        q.push(val);
        lock.unlock();
        cv_consumer.notify_one();
    }

    bool pop(long long& val) {
        std::unique_lock<std::mutex> lock(mtx);
        cv_consumer.wait(lock, [this]() {
            return !q.empty() || done;
        });

        if (q.empty() && done) {
            return false;
        }

        val = q.front();
        q.pop();
        lock.unlock();
        cv_producer.notify_one();
        return true;
    }

    void finish() {
        {
            std::lock_guard<std::mutex> lock(mtx);
            done = true;
        }
        cv_consumer.notify_all();
    }
};

void computeProduct(const std::vector<int>& v1, const std::vector<int>& v2, BoundedQueue& queue) {
    for (size_t i = 0; i < v1.size(); ++i) {
        long long prod = 1LL * v1[i] * v2[i];
        queue.push(prod);
    }
    queue.finish();
}

void sumProduct(long long& total_sum, BoundedQueue& queue) {
    long long sum = 0;
    long long val = 0;
    while (queue.pop(val)) {
        sum += val;
    }
    total_sum = sum;
}

int main() {
    const size_t N = 100000; // dimensiunea vectorilor
    std::vector<int> v1(N), v2(N);

    // Initializare vectori cu valori test
    for (size_t i = 0; i < N; ++i) {
        v1[i] = static_cast<int>(i % 100);
        v2[i] = static_cast<int>((i + 1) % 100);
    }

    // Calcul secvential pentru validare
    auto start_seq = std::chrono::high_resolution_clock::now();
    long long expected_sum = 0;
    for (size_t i = 0; i < N; ++i) {
        expected_sum += 1LL * v1[i] * v2[i];
    }
    auto end_seq = std::chrono::high_resolution_clock::now();
    double seq_time = std::chrono::duration<double, std::milli>(end_seq - start_seq).count();

    std::cout << "Dimensiune vectori: N = " << N << "\n";
    std::cout << "Suma secventiala: " << expected_sum << " (Timp: " << seq_time << " ms)\n\n";

    // Testare cu diferite dimensiuni ale cozii
    std::vector<size_t> queue_sizes = {1, 5, 10, 50, 100, 500, 1000, 5000, 10000};

    std::cout << "Queue Size\tTimp (ms)\tRezultat\tStatus\n";
    std::cout << "--------------------------------------------------------\n";

    for (size_t q_size : queue_sizes) {
        BoundedQueue queue(q_size);
        long long total_sum = 0;

        auto start = std::chrono::high_resolution_clock::now();

        std::thread producer(computeProduct, std::cref(v1), std::cref(v2), std::ref(queue));
        std::thread consumer(sumProduct, std::ref(total_sum), std::ref(queue));

        producer.join();
        consumer.join();

        auto end = std::chrono::high_resolution_clock::now();
        double duration = std::chrono::duration<double, std::milli>(end - start).count();

        bool ok = (total_sum == expected_sum);
        std::cout << q_size << "\t\t" 
                  << duration << " ms\t" 
                  << total_sum << "\t" 
                  << (ok ? "OK" : "FAILED") << "\n";
    }

    return 0;
}