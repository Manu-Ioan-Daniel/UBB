#ifndef MYQUEUE_H
#define MYQUEUE_H
#include <condition_variable>
#include <queue>

template <typename T>
class MyQueue {

    std::queue<T> m_queue;
    std::mutex m_mutex;
    std::condition_variable m_cond;

public:

    void push(T item)
    {

        std::unique_lock lock(m_mutex);
        m_queue.push(item);
        m_cond.notify_one();
    }

    T pop()
    {

        std::unique_lock lock(m_mutex);
        m_cond.wait(lock,
                    [this]() { return !m_queue.empty(); });
        T item = m_queue.front();
        m_queue.pop();
        return item;
    }
};



#endif //MYQUEUE_H
