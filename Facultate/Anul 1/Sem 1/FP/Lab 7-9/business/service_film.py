from domeniu.film import Film
import random
import string
class ServiceFilm:
    def __init__(self,_validator_film,_repo_film,_repo_film_fisier):
        self._validator_film=_validator_film
        self._repo_film=_repo_film
        self._repo_film_fisier=_repo_film_fisier
        self.__base_id=1
    def adauga_film(self,film_id,titlu,descriere,gen):
        film=Film(film_id,titlu,descriere,gen)
        self._validator_film.valideaza_film(film)
        self._repo_film.adauga_film(film)
        self.__base_id+=1
    def adauga_film_fisier(self,film_id,titlu,descriere,gen):
        film=Film(film_id,titlu,descriere,gen)
        self._validator_film.valideaza_film(film)
        self._repo_film_fisier.adauga_film(film)
        self.__base_id+=1
    def modifica_film_fisier(self,film_id,titlu,descriere,gen):
        film=Film(film_id,titlu,descriere,gen)
        self._validator_film.valideaza_film(film)
        self._repo_film_fisier.modifica_film(film_id,film)
    def cauta_film_fisier(self,film_id):
        return self._repo_film_fisier.cauta_film(film_id)
    def sterge_film_fisier(self,film_id):
        self._repo_film_fisier.sterge_film(film_id)
        if(film_id==self.__base_id):
            self.__base_id-=1
    def filme_random_fisier(self):
        nr_filme=random.randint(1,6)
        for nr_film in range(nr_filme):
            length_titlu_film=random.randint(5,20)
            titlu_film="".join(random.choices(string.ascii_lowercase,k=length_titlu_film))
            length_descriere_film=random.randint(5,20)
            descriere_film="".join(random.choices(string.ascii_lowercase,k=length_descriere_film))
            length_gen_film=random.randint(1,5)
            gen_film="".join(random.choices(string.ascii_lowercase,k=length_gen_film))
            self.adauga_film_fisier(self.__base_id,titlu_film,descriere_film,gen_film)
    def modifica_film(self,film_id,titlu,descriere,gen):
        film=Film(film_id,titlu,descriere,gen)
        self._validator_film.valideaza_film(film)
        self._repo_film.modifica_film(film_id,film)
    def sterge_film(self,film_id):
        film=self._repo_film.cauta_film(film_id)
        self._validator_film.valideaza_film(film)
        self._repo_film.sterge_film(film_id)
        if(film_id==self.__base_id):
            self.__base_id -= 1
    def cauta_film(self,film_id):
        film=self._repo_film.cauta_film(film_id)
        self._validator_film.valideaza_film(film)
        return film
    def filme_random(self):
        nr_filme=random.randint(1,6)
        for nr_film in range(nr_filme):
            length_titlu_film=random.randint(5,20)
            titlu_film="".join(random.choices(string.ascii_lowercase,k=length_titlu_film))
            length_descriere_film=random.randint(5,20)
            descriere_film="".join(random.choices(string.ascii_lowercase,k=length_descriere_film))
            length_gen_film=random.randint(1,5)
            gen_film="".join(random.choices(string.ascii_lowercase,k=length_gen_film))
            self.adauga_film(self.__base_id,titlu_film,descriere_film,gen_film)
    def get_all(self):
        return self._repo_film.get_all()

    def get_all_fisier(self):
        return [x for x in self._repo_film_fisier.get_all()]