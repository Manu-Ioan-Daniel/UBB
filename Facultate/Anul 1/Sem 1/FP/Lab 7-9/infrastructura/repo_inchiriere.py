from exceptii.erori import RepoError
class RepoInchiriere:
    def __init__(self):
        self.__inchirieri = {}
    def adauga_inchiriere(self, inchiriere):
        if inchiriere.get_id() in self.__inchirieri:
            raise RepoError("Inchiriere cu acelasi id existent")
        self.__inchirieri[inchiriere.get_id()] = inchiriere
    def sterge_inchiriere(self, inchiriere_id):
        if inchiriere_id not in self.__inchirieri:
            raise RepoError("Nu exista inchiriere cu acest id")
        self.__inchirieri.pop(inchiriere_id)
    def modifica_inchiriere(self, id_inchiriere, inchiriere):
        if id_inchiriere not in self.__inchirieri:
            raise RepoError("Nu exista inchiriere cu acest id")
        self.__inchirieri[id_inchiriere] = inchiriere
    def cauta_inchiriere(self, id_inchiriere):
        if id_inchiriere not in self.__inchirieri:
            raise RepoError("Nu exista inchiriere cu acest id")
        return self.__inchirieri[id_inchiriere]
    def get_all(self):
        return [x for x in self.__inchirieri.values()]
