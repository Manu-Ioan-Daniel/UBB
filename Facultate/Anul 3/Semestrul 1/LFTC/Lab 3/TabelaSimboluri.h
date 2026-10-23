#pragma once

#include <vector>
#include <string>
#include <ostream>

class TabelaSimboluri {
    std::vector<std::string> symbols;

public:
    void insert(const std::string& symbol) {
        int left = 0;
        int right = static_cast<int>(symbols.size()) - 1;
        int insertPos = static_cast<int>(symbols.size());

        while (left <= right) {
            const int mid = left + (right - left) / 2;
            if (symbols[mid] == symbol) {
                return;
            }
            if (symbols[mid] < symbol) {
                left = mid + 1;
            } else {
                insertPos = mid;
                right = mid - 1;
            }
        }

        symbols.insert(symbols.begin() + insertPos, symbol);
    }

    [[nodiscard]] int position(const std::string& symbol) const {
        int left = 0;
        int right = static_cast<int>(symbols.size()) - 1;

        while (left <= right) {
            const int mid = left + (right - left) / 2;
            if (symbols[mid] == symbol) {
                return mid + 1;
            }
            if (symbols[mid] < symbol) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    [[nodiscard]] const std::string& at(const int position) const {
        return symbols[position - 1];
    }

    void save(std::ostream& out) const {
        for (const auto & symbol : symbols) {
            out << symbol << '\n';
        }
    }
};