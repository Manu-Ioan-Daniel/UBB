#pragma once
#include <vector>
#include "disciplina.h"
using std::vector;
class RepoException :public std::exception{
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
    void stergeDisciplina(const string& denumire,const string& tip);
    [[nodiscard]] Disciplina cautaDisciplina(const string &denumire, const string &tip);
    void modificaDisciplina(const Disciplina& disciplinaNoua,const Disciplina& disciplina);
    [[nodiscard]] vector <Disciplina> getAll() const{
        return discipline;
    }
    vector<Disciplina>& getAll(){
        return discipline;
    }

};
void testRepo();
