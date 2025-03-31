#pragma once
#include <string>
using std::string;
class Disciplina {
    private:
        string denumire;
        int nrOre;
        string tip;
        string cadruDidactic;
    public:
        [[nodiscard]]int getNrOre() const;
        [[nodiscard]]string getDenumire() const;
        [[nodiscard]]string getTip() const;
        [[nodiscard]]string getCadruDidactic() const;
        Disciplina(string denumire, const int nrOre, string tip, string cadruDidactic):denumire{std::move(denumire)},nrOre{nrOre},tip{std::move(tip)},cadruDidactic{std::move(cadruDidactic)} {
        }
        ~Disciplina()=default;
        Disciplina(const Disciplina& d)= default;
        Disciplina()=default;
        Disciplina& operator=(const Disciplina& d) = default;
        bool operator==(const Disciplina& d) const {
            return denumire==d.denumire && tip==d.tip;
        }




};