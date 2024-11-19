from domeniu.client import Client
from exceptii.erori import ValidationError
from exceptii.erori import RepoError
from infrastructura.repo_client import RepoClient
from infrastructura.repo_film import RepoFilm
from domeniu.film import Film
from business.service_film import ServiceFilm
from business.service_client import ServiceClient
from validare.validare_client import ValidatorClient
from validare.validare_filme import ValidatorFilm

class Teste:
    def test_repo_adauga_client(self):
        repo_client=RepoClient()
        client=Client(23,"Manu","505")
        repo_client.adauga_client(client)
        assert client  in repo_client.get_all()
        try:
            repo_client.adauga_client(client)
            assert False
        except RepoError as re:
            assert True
    def test_repo_sterge_client(self):
        repo_client=RepoClient()
        client=Client(23,"Manu","505")
        try:
            repo_client.sterge_client(client)
            assert False
        except RepoError as re:
            assert True
        repo_client.adauga_client(client)
        repo_client.sterge_client(client.get_id())
        assert client not in repo_client.get_all()
    def test_repo_modifica_client(self):
        repo_client=RepoClient()
        client=Client(23,"Manu","505")
        client2=Client(23,"Manuu","505")
        repo_client.adauga_client(client)
        repo_client.modifica_client(client.get_id(),client2)
        assert client2  in repo_client.get_all()
        assert client not in repo_client.get_all()
        try:
            repo_client.modifica_client(24,client2)
            assert False
        except RepoError as re:
            assert True
    def test_repo_cauta_client(self):
        repo_client = RepoClient()
        client = Client(23, "Manu", "505")
        repo_client.adauga_client(client)
        client2=repo_client.cauta_client(client.get_id())
        assert client == client2
        try:
            client2=repo_client.cauta_client(29)
            assert False
        except RepoError as re:
            assert True

    def test_repo_adauga_film(self):
        repo_film=RepoFilm()
        film = Film(23,"Manu","un baiat si o fata","romantic")
        repo_film.adauga_film(film)
        assert film in repo_film.get_all()
        try:
            repo_film.adauga_film(film)
            assert False
        except RepoError as re:
            assert True
    def test_repo_sterge_film(self):
        repo_film = RepoFilm()
        film = Film(23, "Manu", "un baiat si o fata", "romantic")
        repo_film.adauga_film(film)
        repo_film.sterge_film(film.get_id())
        assert film not in repo_film.get_all()
        try:
            repo_film.sterge_film(film.get_id())
            assert False
        except RepoError as re:
            assert True
    def test_repo_modifica_film(self):
        repo_film = RepoFilm()
        film = Film(23, "Manu", "un baiat si o fata", "romantic")
        repo_film.adauga_film(film)
        film2=Film(23,"Manuu","un baiat si o fata","romantic")
        repo_film.modifica_film(film.get_id(),film2)
        assert film not in repo_film.get_all()
        assert film2 in repo_film.get_all()
        try:
            repo_film.modifica_film(24,film2)
            assert False
        except RepoError as re:
            assert True
    def test_repo_cauta_film(self):
        repo_film = RepoFilm()
        film = Film(23, "Manu", "un baiat si o fata", "romantic")
        repo_film.adauga_film(film)
        film_cautat=repo_film.cauta_film(film.get_id())
        assert film == film_cautat
        try:
            repo_film.cauta_film(100)
            assert False
        except RepoError as re:
            assert True
    def test_validare_client(self):
        validator_client=ValidatorClient()
        client=Client(23,"Manu","505")
        try:
            validator_client.valideaza_client(client)
            assert True
        except ValidationError as ve:
            assert False
        client=Client(-23,"","")
        try:
            validator_client.valideaza_client(client)
            assert False
        except ValidationError as ve:
            assert str(ve)=="Id invalid\nName invalid\nCnp invalid\n"
    def test_validare_film(self):
        validator_film=ValidatorFilm()
        film=Film(23,"Manu","un baiat si o fata","romantic")
        try:
            validator_film.valideaza_film(film)
            assert True
        except ValidationError as ve:
            assert False
        film=Film(-1,"","","")
        try:
            validator_film.valideaza_film(film)
            assert False
        except ValidationError as ve:
            assert str(ve)=="Id invalid\nGen invalid\nTitlu invalid\nDescriptie invalid\n"
    def test_serivice_film_init(self):
        validator_film=ValidatorFilm()
        repo_film=RepoFilm()
        service_film=ServiceFilm(validator_film,repo_film)
        assert service_film._validator_film == validator_film
        assert service_film._repo_film == repo_film
    def test_service_adauga_film(self):
        validator_film=ValidatorFilm()
        repo_film=RepoFilm()
        service_film=ServiceFilm(validator_film,repo_film)
        film=Film(23,"Manu","un baiat si o fata","romantic")
        service_film.adauga_film(film.get_id(),film.get_title(),film.get_description(),film.get_gen())
        assert film in service_film.get_all()
    def test_service_modifica_film(self):
        validator_film = ValidatorFilm()
        repo_film = RepoFilm()
        service_film = ServiceFilm(validator_film, repo_film)
        film = Film(23, "Manu", "un baiat si o fata", "romantic")
        service_film.adauga_film(film.get_id(), film.get_title(), film.get_description(), film.get_gen())
        service_film.modifica_film(film.get_id(), "Manuu","un baiat si o fata","romantic")
        assert film not in service_film.get_all()
        assert service_film.cauta_film(film.get_id()) in service_film.get_all()
    def test_service_elimina_film(self):
        validator_film = ValidatorFilm()
        repo_film = RepoFilm()
        service_film = ServiceFilm(validator_film, repo_film)
        film = Film(23, "Manu", "un baiat si o fata", "romantic")
        service_film.adauga_film(film.get_id(), film.get_title(), film.get_description(), film.get_gen())
        service_film.sterge_film(film.get_id())
        assert film not in service_film.get_all()

    def test_service_cauta_film(self):
        validator_film = ValidatorFilm()
        repo_film = RepoFilm()
        service_film = ServiceFilm(validator_film, repo_film)
        film = Film(23, "Manu", "un baiat si o fata", "romantic")
        service_film.adauga_film(film.get_id(), film.get_title(), film.get_description(), film.get_gen())
        film2=service_film.cauta_film(film.get_id())
        assert film == film2
    def test_service_adauga_client(self):
        validator_client=ValidatorClient()
        repo_client=RepoClient()
        service_client=ServiceClient(validator_client,repo_client)
        client = Client(23,"Manu","505")
        service_client.adauga_client(23,"Manu","505")
        assert client in service_client.get_all()
    def test_service_modifica_client(self):
        validator_client=ValidatorClient()
        repo_client=RepoClient()
        service_client=ServiceClient(validator_client,repo_client)
        client = Client(23,"Manu","505")
        service_client.adauga_client(23,"Manu","505")
        service_client.modifica_client(client.get_id(),"Manuu","505")
        assert client not in service_client.get_all()
        assert service_client.cauta_client(client.get_id()) in service_client.get_all()
    def test_service_sterge_client(self):
        validator_client=ValidatorClient()
        repo_client=RepoClient()
        service_client=ServiceClient(validator_client,repo_client)
        client = Client(23,"Manu","505")
        service_client.adauga_client(23,"Manu","505")
        service_client.sterge_client(client.get_id())
        assert client not in service_client.get_all()
    def test_service_cauta_client(self):
        validator_client=ValidatorClient()
        repo_client=RepoClient()
        service_client=ServiceClient(validator_client,repo_client)
        client = Client(23,"Manu","505")
        service_client.adauga_client(23,"Manu","505")
        client2=service_client.cauta_client(client.get_id())
        assert client==client2



    def ruleaza_toate_testele(self):

        self.test_repo_adauga_client()
        self.test_repo_sterge_client()
        self.test_repo_modifica_client()
        self.test_repo_cauta_client()
        self.test_repo_adauga_film()
        self.test_repo_sterge_film()
        self.test_repo_modifica_film()
        self.test_repo_cauta_film()
        self.test_validare_client()
        self.test_validare_film()
        self.test_serivice_film_init()
        self.test_service_adauga_film()
        self.test_service_modifica_film()
        self.test_service_elimina_film()
        self.test_service_cauta_film()
        self.test_service_adauga_client()
        self.test_service_modifica_client()
        self.test_service_cauta_client()
        self.test_service_sterge_client()
