#include <iostream>
#include <mutex>
#include <thread>
#include <vector>

constexpr int TOTAL_BALANCE = 10000;
constexpr int TOTAL_ACCOUNTS = 50;
constexpr int TOTAL_THREADS = 50;

//we use unique ptr because the mutex object itself is non-movable/non-copyable
struct Account {
    int balance;
    std::unique_ptr<std::mutex> mtx;

    explicit Account(const int b) : balance(b), mtx(std::make_unique<std::mutex>()) {}
};

void transfer(Account& from, Account& to, const int amount) {
    from.mtx->lock();
    to.mtx->lock();
    if (from.balance>=amount) {
        from.balance -= amount;
        to.balance += amount;
    }
    std::cout<<"S a executat un transfer de: "<<amount<<std::endl;
    from.mtx->unlock();
    to.mtx->unlock();
}

void populateAccounts(std::vector<Account>& accounts) {
    constexpr int balance = TOTAL_BALANCE/TOTAL_ACCOUNTS;
    //we avoid using the account constructor and push_back,
    //emplace_back automatically uses the explicit constructor
    for (int i = 0;i<TOTAL_ACCOUNTS;i++) {
        accounts.emplace_back(balance);
    }
}

bool checkAudit(std::vector<Account>& accounts) {
    for (const auto& account : accounts) {
        account.mtx->lock();
    }
    int total = 0;
    for (const auto& account : accounts) {
        total += account.balance;
    }
    for (const auto& account : accounts) {
        account.mtx->unlock();
    }
    return total == TOTAL_BALANCE;
}

int main() {

    std::vector<Account> accounts;
    populateAccounts(accounts);
    std::vector<std::thread> threads;
    threads.reserve(TOTAL_THREADS);
    for (int i = 0;i<TOTAL_THREADS;i++) {
        const int fromIndex = rand()%TOTAL_THREADS;
        const int toIndex = rand()%TOTAL_THREADS;
        if (fromIndex == toIndex)
            continue;

        threads.emplace_back(transfer, std::ref(accounts[fromIndex]), std::ref(accounts[toIndex]), rand() % 20);
        if (i%10 == 0) {
            if (!checkAudit(accounts)) {
                std::cout<<("BAI PUTA CE AI FACUT");
                break;
            }
        }
    }
    for (auto& t : threads) {
        if (t.joinable()) {
            t.join();
        }
    }
    return 0;
}

