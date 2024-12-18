from exceptii.erori import RepoError
class RepoFilm():
    def __init__(self):
        self.__filme={}
    def adauga_film(self,film):
        if film.get_id() in self.__filme:
            raise RepoError("Film cu acelasi id existent")
        self.__filme[film.get_id()] = film
    def sterge_film(self,film_id):
        if film_id not in self.__filme:
            raise RepoError("Nu exista film cu acest id")
        self.__filme.pop(film_id)
    def modifica_film(self,id_film,film):
        if id_film not in self.__filme:
            raise RepoError("Nu exista film cu acest id")

        self.__filme[id_film] = film
    def cauta_film(self,id_film):
        if id_film not in self.__filme:
            raise RepoError("Nu exista film cu acest id")
        return self.__filme[id_film]

    def get_all(self,lista=None,index=0):
        # todo remember this is recursive
        lista=list(self.__filme.values())
        if(index==len(lista)):
            return []
        return [lista[index]]+self.get_all(lista,index+1)
