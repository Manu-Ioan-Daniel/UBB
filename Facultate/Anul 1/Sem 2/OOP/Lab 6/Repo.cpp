
#include "Repo.h"
void Repo::addDisciplina(const Disciplina& disciplina) {
    for (const Disciplina& d : discipline) {
        if (d==disciplina) {
            throw RepoException("Disciplina exista deja!");
        }
        discipline.push_back(d);
    }
}