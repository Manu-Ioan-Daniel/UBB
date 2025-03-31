#pragma once
#include <string>
using std::string;
class Validator {
public:
    static void validateDisciplina(const string& denumire, int nrOre, const string& tip, const string& cadruDidactic);
};
class ValidationError {
private:
    string msg;
public:
    [[nodiscard]] string getError() const {
        return msg;
    }
    explicit ValidationError(string m):msg{std::move(m)}{
    }
};

