from domeniu.film import Film
class ServiceFilm:
    def __init__(self,_validator_film,_repo_film):
        self._validator_film=_validator_film
        self._repo_film=_repo_film
    def adauga_film(self,film_id,titlu,descriere,gen):
        film=Film(film_id,titlu,descriere,gen)
        self._validator_film.valideaza_film(film)
        self._repo_film.adauga_film(film)
    def modifica_film(self,film_id,titlu,descriere,gen):
        film=Film(film_id,titlu,descriere,gen)
        self._validator_film.valideaza_film(film)
        self._repo_film.modifica_film(film_id,film)
    def sterge_film(self,film_id):
        film=self._repo_film.cauta_film(film_id)
        self._validator_film.valideaza_film(film)
        self._repo_film.sterge_film(film_id)
    def cauta_film(self,film_id):
        film=self._repo_film.cauta_film(film_id)
        self._validator_film.valideaza_film(film)
        return film
    def get_all(self):
        return [x for x in self._repo_film.get_all()]