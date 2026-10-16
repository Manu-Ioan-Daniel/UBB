#ifndef MYQUEUE_H
#define MYQUEUE_H

#include <condition_variable>
#include <mutex>
#include <queue>

template <typename T>
class MyQueue {
    int capacity;
    std::queue<T> queue;
    std::mutex mutex;
    std::condition_variable not_full;
    std::condition_variable not_empty;

public:
    explicit MyQueue(const int capacity = 10) : capacity(capacity) {}

    void push(T item) {
        std::unique_lock lock(mutex);
        not_full.wait(lock, [this]() { return queue.size() < capacity; });
        queue.push(item);
        not_empty.notify_one();
    }

    T pop() {
        std::unique_lock lock(mutex);
        not_empty.wait(lock, [this]() { return !queue.empty(); });
        T item = queue.front();
        queue.pop();
        not_full.notify_one();
        return item;
    }
};

#endif //MYQUEUE_H
