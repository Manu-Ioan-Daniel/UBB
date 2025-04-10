#pragma once
#include <vector>
#include "disciplina.h"
#include "LinkedList.h"
using std::vector;
class RepoException final :public std::exception{
    string msg;
public:
    explicit RepoException(string m):msg{std::move(m)}{
    }
    [[nodiscard]] string getMsg() const {
        return msg;
    }

};
class Repo {
    LinkedList<Disciplina> discipline;
public:
    void addDisciplina(const Disciplina& disciplina);
    void stergeDisciplina(const string& denumire,const string& tip);
    [[nodiscard]] Disciplina cautaDisciplina(const string &denumire, const string &tip) const;
    void modificaDisciplina(const Disciplina& disciplinaNoua,const Disciplina& disciplina) const;
    [[nodiscard]] LinkedList<Disciplina>& getAll(){
        return discipline;
    }

};
void testRepo();
