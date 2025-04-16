#pragma once
#include <functional>


template<typename T> class Iterator;

template <typename T>
class LinkedList {
    friend class Iterator<T>;
    struct Node {
        T data;
        Node* next;
        Node* prev;
        explicit Node(const T& val): data(val), next(nullptr), prev(nullptr) {}
    };
    Node* head;
    Node* tail;
    int size;
public:
    using iterator = Iterator<T>;

    LinkedList(): head(nullptr), tail(nullptr), size(0) {}
    ~LinkedList() {
        while (head)
            pop_front();
    }

    LinkedList(const LinkedList& other): head(nullptr), tail(nullptr), size(0) {
        Node* current = other.head;
        while (current) {
            push_back(current->data);
            current = current->next;
        }
    }

    LinkedList& operator=(const LinkedList& other) {
        if (this != &other) {
            while (head)
                pop_front();
            Node* current = other.head;
            while (current) {
                push_back(current->data);
                current = current->next;
            }
        }
        return *this;
    }

    void push_back(const T&);
    void push_front(const T&);
    void pop_back();
    void pop_front();
    void erase(iterator pos);
    void insert(const T& val, iterator pos);

    [[nodiscard]] int getSize() const { return size; }
    [[nodiscard]] bool isEmpty() const { return size == 0; }

    iterator begin() const { return iterator(head); }
    static iterator end() { return iterator(nullptr); }

    Node* mergeSort(Node* head, const std::function<bool(const T&, const T&)>& comparator);
    Node* merge(Node*, Node*, const std::function<bool(const T&, const T&)>& comparator);
    Node* getMiddle(Node*);
    void sort(const std::function<bool(const T&, const T&)>& comparator);
};

template <typename T>
class Iterator {
    friend class LinkedList<T>;
public:
    typename LinkedList<T>::Node* current;
    explicit Iterator(typename LinkedList<T>::Node* node): current(node) {}

    T& operator*() const { return current->data; }
    T* operator->() const { return &current->data; }

    Iterator operator++() {
        if (current) current = current->next;
        return *this;
    }

    Iterator operator++(int) {
        Iterator temp = *this;
        ++(*this);
        return temp;
    }

    Iterator operator--() {
        if (current) current = current->prev;
        return *this;
    }

    Iterator operator--(int) {
        Iterator temp = *this;
        --(*this);
        return temp;
    }

    bool operator==(const Iterator& other) const { return current == other.current; }
    bool operator!=(const Iterator& other) const { return current != other.current; }
};

template<typename T>
void LinkedList<T>::push_back(const T& val) {
    Node* newNode = new Node(val);
    if (!tail) {
        head = tail = newNode;
    } else {
        newNode->prev = tail;
        tail->next = newNode;
        tail = newNode;
    }
    ++size;
}

template<typename T>
void LinkedList<T>::push_front(const T& val) {
    Node* newNode = new Node(val);
    if (!head) {
        head = tail = newNode;
    } else {
        newNode->next = head;
        head->prev = newNode;
        head = newNode;
    }
    ++size;
}

template<typename T>
void LinkedList<T>::pop_back() {
    if (!tail) return;
    const Node* temp = tail;
    tail = tail->prev;
    if (tail) tail->next = nullptr;
    else head = nullptr;
    delete temp;
    --size;
}

template<typename T>
void LinkedList<T>::pop_front() {
    if (!head) return;
    const Node* temp = head;
    head = head->next;
    if (head) head->prev = nullptr;
    else tail = nullptr;
    delete temp;
    --size;
}

template<typename T>
void LinkedList<T>::erase(iterator pos) {
    if (!pos.current) return;
    Node* node = pos.current;

    if (node == head) {
        pop_front();
    } else if (node == tail) {
        pop_back();
    } else {
        Node* prevNode = node->prev;
        Node* nextNode = node->next;
        if (prevNode) prevNode->next = nextNode;
        if (nextNode) nextNode->prev = prevNode;
        delete node;
        --size;
    }
}

template<typename T>
void LinkedList<T>::insert(const T& val, iterator pos) {
    if (!pos.current || pos.current == head) {
        push_front(val);
        return;
    }

    Node* newNode = new Node(val);
    Node* prevNode = pos.current->prev;

    newNode->next = pos.current;
    newNode->prev = prevNode;
    if (prevNode) prevNode->next = newNode;
    pos.current->prev = newNode;

    ++size;
}

template <typename T>
typename LinkedList<T>::Node* LinkedList<T>::mergeSort(Node* head, const std::function<bool(const T&, const T&)>& comparator) {
    if (!head || !head->next) return head;

    Node* middle = getMiddle(head);
    Node* nextToMiddle = middle->next;
    middle->next = nullptr;
    if (nextToMiddle) nextToMiddle->prev = nullptr;

    Node* left = mergeSort(head, comparator);
    Node* right = mergeSort(nextToMiddle, comparator);

    return merge(left, right, comparator);
}

template <typename T>
typename LinkedList<T>::Node* LinkedList<T>::merge(Node* left, Node* right, const std::function<bool(const T&, const T&)>& comparator) {
    if (!left) return right;
    if (!right) return left;

    if (comparator(left->data, right->data)) {
        left->next = merge(left->next, right, comparator);
        if (left->next) left->next->prev = left;
        left->prev = nullptr;
        return left;
    }
    right->next = merge(left, right->next, comparator);
    if (right->next) right->next->prev = right;
    right->prev = nullptr;
    return right;
}

template <typename T>
typename LinkedList<T>::Node* LinkedList<T>::getMiddle(Node* head) {
    if (!head) return head;
    Node* slow = head;
    Node* fast = head->next;

    while (fast && fast->next) {
        slow = slow->next;
        fast = fast->next->next;
    }
    return slow;
}

template <typename T>
void LinkedList<T>::sort(const std::function<bool(const T&, const T&)>& comparator) {
    head = mergeSort(head, comparator);


    tail = head;
    while (tail && tail->next) {
        tail = tail->next;
    }
}
