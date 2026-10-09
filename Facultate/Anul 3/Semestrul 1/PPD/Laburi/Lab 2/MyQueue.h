#ifndef MYQUEUE_H
#define MYQUEUE_H

#include <condition_variable>
#include <mutex>
#include <queue>

template <typename T>
class MyQueue {
    int m_capacity;
    std::queue<T> m_queue;
    std::mutex m_mutex;
    std::condition_variable m_cond_not_full;
    std::condition_variable m_cond_not_empty;

public:
    explicit MyQueue(int capacity = 10) : m_capacity(capacity) {}

    void push(T item) {
        std::unique_lock lock(m_mutex);
        m_cond_not_full.wait(lock, [this]() { return (int)m_queue.size() < m_capacity; });
        m_queue.push(item);
        m_cond_not_empty.notify_one();
    }

    T pop() {
        std::unique_lock lock(m_mutex);
        m_cond_not_empty.wait(lock, [this]() { return !m_queue.empty(); });
        T item = m_queue.front();
        m_queue.pop();
        m_cond_not_full.notify_one();
        return item;
    }
};

#endif //MYQUEUE_H
