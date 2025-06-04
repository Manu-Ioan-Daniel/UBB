#pragma once
#include <vector>
#include <algorithm>
class Observer {
public:
    // Invoked when Observable change
    virtual void update() = 0;
};
// Derive from this class if you want to provide notifications
class Observable {
private:
    std::vector<Observer*> observers;
public:
    // Observers use this method to register for notification
    void addObserver(Observer *obs) {
        observers.push_back(obs);
    }
    // Observers use this to cancel the registration
    // !!!Before an observer is destroyed need to cancel the registration
    void removeObserver(Observer *obs) {
        std::erase(observers,obs);
    }
protected:
    /* Invoked by the observable object
    in order to notify interested observer */
    void notify() {
        for (const auto obs : observers) {
            obs->update();
        }
    }
};