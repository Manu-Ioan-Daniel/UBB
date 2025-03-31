
#include "validator.h"

void Validator::validateDisciplina(const string &denumire, const int nrOre, const string &tip, const string &cadruDidactic) {
    std::string errors;
    if (denumire.empty()){
        errors+="Denumirea invalida\n";
    }
    if (nrOre<0){
        errors+="Numarul de ore invalid\n";
    }
    if (tip.empty()) {
        errors+="Tip invalid\n";
    }
    if (cadruDidactic.empty()) {
        errors+="Cadru didactic invalid\n";
    }
    if (!errors.empty()) {
        throw ValidationError(errors);
    }

}
