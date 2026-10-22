#pragma once

#include <vector>
#include <string>
#include <algorithm>
#include <ostream>

class SymbolTable {
    std::vector<std::string> symbols;

public:
    void insert(const std::string& symbol) {
        if (const auto it = std::lower_bound(symbols.begin(), symbols.end(), symbol); it == symbols.end() || *it != symbol)
            symbols.insert(it, symbol);
    }

    [[nodiscard]] int position(const std::string& symbol) const {
        return static_cast<int>(std::lower_bound(symbols.begin(), symbols.end(), symbol) - symbols.begin()) + 1;
    }

    [[nodiscard]] const std::string& at(const int position) const {
        return symbols[position - 1];
    }

    void save(std::ostream& out) const {
        for (const std::string& symbol : symbols)
            out << symbol << '\n';
    }
};
