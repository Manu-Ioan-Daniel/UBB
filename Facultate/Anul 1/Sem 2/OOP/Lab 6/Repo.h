#pragma once
#include <vector>
#include "disciplina.h"
using std::vector;
class RepoException {
    string msg;
public:
    explicit RepoException(string m):msg{std::move(m)}{
    }
    [[nodiscard]] string getMsg() const {
        return msg;
    }

};
class Repo {
    vector<Disciplina> discipline;
public:
    void addDisciplina(const Disciplina& disciplina);
    [[nodiscard]] vector <Disciplina> getAll() const{
        return discipline;
    }

};