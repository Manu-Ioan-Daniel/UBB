#pragma once
#include <vector>
#include <algorithm>
class Observer {
public:
    virtual void update() = 0;
};
class Observable {
    std::vector<Observer*> observers;
public:
    void addObserver(Observer *obs) {
        observers.push_back(obs);
    }
    void removeObserver(Observer *obs) {
        std::erase(observers,obs);
    }
protected:
    void notify() {
        for (const auto obs : observers) {
            obs->update();
        }
    }
};