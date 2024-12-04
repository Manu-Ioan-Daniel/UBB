
from domeniu.client import Client
from exceptii.erori import ValidationError
from exceptii.erori import RepoError
from infrastructura.repo_client import RepoClient
from infrastructura.repo_film import RepoFilm
from domeniu.film import Film
from business.service_film import ServiceFilm
from business.service_client import ServiceClient
from infrastructura.repo_inchiriere import RepoInchiriere
from validare.validare_client import ValidatorClient
from validare.validare_filme import ValidatorFilm
from domeniu.inchiriere import Inchiriere
from validare.validare_inchiriere import ValidatorInchiriere
from business.service_inchiriere import ServiceInchiriere
import unittest
from domeniu.inchiriereDTO import InchiriereDTO
from infrastructura.repo_film_fisier import FileRepoFilm
from infrastructura.repo_client_fisier import FileRepoClient
from infrastructura.repo_inchiriere_fisier import FileRepoInchiriere

import random
import string

class Teste:
    def __init__(self):
        self.ruleaza_toate_testele()
    def test_repo_filme(self):
        repo_film=RepoFilm()
        film=Film(23,"Manu","un baiat si o fata","romantic")
        film2 = Film(23, "Manuu", "un baiat si o fata", "romantic")

        #test adauga film

        repo_film.adauga_film(film)
        assert film in repo_film.get_all()
        try:
            repo_film.adauga_film(film)
            assert False
        except RepoError as re:
            assert True

        #test modifica film

        repo_film.modifica_film(film.get_id(),film2)
        assert film2 in repo_film.get_all()
        assert film not in repo_film.get_all()
        try:
            repo_film.modifica_film(24,film2)
            assert False
        except RepoError as re:
            assert True

        #test sterge film

        repo_film.sterge_film(film2.get_id())
        assert film2 not in repo_film.get_all()
        try:
            repo_film.sterge_film(film2.get_id())
            assert False
        except RepoError as re:
            assert True

        #test cauta film

        repo_film.adauga_film(film)
        film_cautat=repo_film.cauta_film(film.get_id())
        assert film == film_cautat
        try:
            repo_film.cauta_film(100)
            assert False
        except RepoError as re:
            assert True

        #sfarsit teste repo filme
    def test_repo_clienti(self):
        repo_client=RepoClient()
        client=Client(23,"Manu","505")
        client2 = Client(23, "Manuu", "505")

        #test adauga client

        repo_client.adauga_client(client)
        assert client in repo_client.get_all()
        try:
            repo_client.adauga_client(client)
            assert False
        except RepoError as re:
            assert True

        #test modifica client

        repo_client.modifica_client(client.get_id(),client2)
        assert client2 in repo_client.get_all()
        assert client not in repo_client.get_all()
        try:
            repo_client.modifica_client(24,client2)
            assert False
        except RepoError as re:
            assert True

        #test sterge client

        repo_client.sterge_client(client2.get_id())
        assert client2 not in repo_client.get_all()
        try:
            repo_client.sterge_client(client2.get_id())
            assert False
        except RepoError as re:
            assert True

        #test cauta client

        repo_client.adauga_client(client)
        client_cautat=repo_client.cauta_client(client.get_id())
        assert client == client_cautat
        try:
            repo_client.cauta_client(100)
            assert False
        except RepoError as re:
            assert True

        #sfarsit test repo client

    def test_repo_inchirieri(self):
        repo_inchiriere=RepoInchiriere()
        client = Client(23, "Manu", "505")
        client2 = Client(23, "Manuu", "505")
        film = Film(23, "Manu", "un baiat si o fata", "romantic")
        film2 = Film(23, "Manuu", "un baiat si o fata", "romantic")
        inchiriere = Inchiriere(23,film, client)
        inchiriere2 = Inchiriere(23,film2, client2)

        #test adauga inchiriere

        repo_inchiriere.adauga_inchiriere(inchiriere)
        assert inchiriere in repo_inchiriere.get_all()
        try:
            repo_inchiriere.adauga_inchiriere(inchiriere)
            assert False
        except RepoError as re:
            assert True

        #test modifica inchiriere

        repo_inchiriere.modifica_inchiriere(inchiriere.get_id(),inchiriere2)
        assert inchiriere2 in repo_inchiriere.get_all()
        assert inchiriere not in repo_inchiriere.get_all()
        try:
            repo_inchiriere.modifica_inchiriere(24,inchiriere2)
            assert False
        except RepoError as re:
            assert True

        #test sterge inchiriere

        repo_inchiriere.sterge_inchiriere(inchiriere2.get_id())
        assert inchiriere2 not in repo_inchiriere.get_all()
        try:
            repo_inchiriere.sterge_inchiriere(inchiriere2.get_id())
            assert False
        except RepoError as re:
            assert True

        #test cauta inchiriere

        repo_inchiriere.adauga_inchiriere(inchiriere)
        inchiriere_cautat=repo_inchiriere.cauta_inchiriere(inchiriere.get_id())
        assert inchiriere == inchiriere_cautat
        try:
            repo_inchiriere.cauta_inchiriere(100)
            assert False
        except RepoError as re:
            assert True

        #sfarsit test repo inchiriere

    def test_validare_film(self):
        validator_film=ValidatorFilm()
        film=Film(23,"Manu","un baiat si o fata","romantic")
        try:
            validator_film.valideaza_film(film)
            assert True
        except ValidationError as ve:
            assert False
        film = Film(-1, "", "", "")
        try:
            validator_film.valideaza_film(film)
            assert False
        except ValidationError as ve:
            assert str(ve)=="Id invalid\nGen invalid\nTitlu invalid\nDescriptie invalid\n"

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

    def test_service_client(self):
        repo_client=RepoClient()
        validator_client=ValidatorClient()
        client=Client(23,"Manu","505")
        client2=Client(23,"Manuu","505")
        service_client=ServiceClient(validator_client,repo_client)

        #test adauga client

        service_client.adauga_client(23,"Manu","505")
        assert client in service_client.get_all()
        try:
            service_client.adauga_client(23,"Manu","505")
            assert False
        except RepoError as re:
            assert True

        #test modifica client

        service_client.modifica_client(client.get_id(),"Manuu","505")
        assert client not in service_client.get_all()
        assert client2 in service_client.get_all()
        try:
            service_client.modifica_client(24,"Manuu","505")
            assert False
        except RepoError as re:
            assert True

        #test sterge client

        service_client.sterge_client(client2.get_id())
        assert client2 not in service_client.get_all()
        try:
            service_client.sterge_client(client2.get_id())
            assert False
        except RepoError as re:
            assert True

        #test cauta client

        service_client.adauga_client(23,"Manu","505")
        client_cautat=service_client.cauta_client(client.get_id())
        assert client == client_cautat
        try:
            service_client.cauta_client(100)
            assert False
        except RepoError as re:
            assert True

        #test genereaza clienti random

        random.seed(1)
        repo_client=RepoClient()
        repo_client2=RepoClient()
        service_client=ServiceClient(validator_client,repo_client)
        service_client2 = ServiceClient(validator_client, repo_client2)
        service_client.adauga_client(1,"gmlquca","8470472990059")
        service_client.adauga_client(2,"rzsntyoirtyykxxcq", "7278959591294")
        service_client2.clienti_random()
        assert service_client.get_all() == service_client2.get_all()

        #sfarsit teste service client

    def test_service_film(self):
        repo_film=RepoFilm()
        validator_film=ValidatorFilm()
        service_film=ServiceFilm(validator_film,repo_film)
        film=Film(23,"Manu","un baiat si o fata","romantic")
        film2=Film(23,"Manuu","un baiat si o fata","romantic")

        #test adauga film

        service_film.adauga_film(23,"Manu","un baiat si o fata","romantic")
        assert film in service_film.get_all()
        try:
            service_film.adauga_film(23,"Manu","un baiat si o fata","romantic")
            assert False
        except RepoError as re:
            assert True

        #test modifica film

        service_film.modifica_film(film.get_id(),"Manuu","un baiat si o fata","romantic")
        assert film not in service_film.get_all()
        assert film2 in service_film.get_all()
        try:
            service_film.modifica_film(24,"Manuu","un baiat si o fata","romantic")
            assert False
        except RepoError as re:
            assert True

        #test sterge film

        service_film.sterge_film(film2.get_id())
        assert film2 not in service_film.get_all()
        try:
            service_film.sterge_film(film2.get_id())
            assert False
        except RepoError as re:
            assert True
        #sfarsit teste service film

    def test_service_inchiriere(self):
        repo_inchiriere=RepoInchiriere()
        repo_film=RepoFilm()
        repo_client=RepoClient()
        validator_inchiriere=ValidatorInchiriere()
        service_inchiriere=ServiceInchiriere(repo_inchiriere,validator_inchiriere,repo_film,repo_client)
        client=Client(25,"Manu","505")
        film=Film(23,"Manu","un baiat si o fata","romantic")
        client2=Client(25,"Manuu","505")
        film2=Film(23,"Manuu","un baiat si o fata","romantic")
        inchiriere=Inchiriere(32,film,client)
        inchiriere2=Inchiriere(32,film2,client2)

        #test adauga inchiriere

        service_inchiriere.adauga_inchiriere(inchiriere.get_id(),film,client)
        assert inchiriere in service_inchiriere.get_all()
        try:
            service_inchiriere.adauga_inchiriere(inchiriere.get_id(),film,client)
            assert False
        except RepoError as re:
            assert True

        #test modifica inchiriere

        service_inchiriere.modifica_inchiriere(inchiriere.get_id(),film2,client2)
        assert inchiriere2 in service_inchiriere.get_all()
        assert inchiriere not in service_inchiriere.get_all()
        try:
            service_inchiriere.modifica_inchiriere(24,film2,client2)
            assert False
        except RepoError as re:
            assert True

        #test sterge inchiriere

        service_inchiriere.sterge_inchiriere(inchiriere.get_id())
        assert inchiriere not in service_inchiriere.get_all()
        try:
            service_inchiriere.sterge_inchiriere(100)
            assert False
        except RepoError as re:
            assert True

        #test cauta client

        service_inchiriere.adauga_inchiriere(inchiriere.get_id(),film,client)
        inchiriere_cautat=service_inchiriere.cauta_inchiriere(inchiriere.get_id())
        assert inchiriere == inchiriere_cautat
        try:
            service_inchiriere.cauta_inchiriere(100)
            assert False
        except RepoError as re:
            assert True
        #sfarsit teste service client
    def test_validator_inchiriere(self):
        validator_inchiriere=ValidatorInchiriere()
        film=Film(23,"Manu","un baiat si o fata","romantic")
        client=Client(25,"Manu","505")
        inchiriere=Inchiriere(32,film,client)
        try:
            validator_inchiriere.valideaza_inchiriere(inchiriere)
            assert True
        except RepoError as re:
            assert False
        inchiriere=Inchiriere(-1,film,client)
        try:
            validator_inchiriere.valideaza_inchiriere(inchiriere)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid"
    def test_top(self):
        repo_client = RepoClient()
        repo_film=RepoFilm()
        repo_inchiriere=RepoInchiriere()
        validator_inchiriere=ValidatorInchiriere()
        service_inchiriere=ServiceInchiriere(repo_inchiriere,validator_inchiriere,repo_film,repo_client)




    def ruleaza_toate_testele(self):
        self.test_repo_filme()
        self.test_repo_clienti()
        self.test_repo_inchirieri()
        self.test_validare_film()
        self.test_validare_client()
        self.test_validator_inchiriere()
        self.test_service_client()
        self.test_service_film()
        self.test_service_inchiriere()
class TesteUnitTest(unittest.TestCase):
    def setUp(self):
        self.__cale_fisier_filme="filme_test.txt"
        self.__repo_film_fisier=FileRepoFilm(self.__cale_fisier_filme)
        with open(self.__cale_fisier_filme,"w") as f:
            f.write("")
        self.__cale_fisier_clienti="clienti_test.txt"
        self.__repo_client_fisier=FileRepoClient(self.__cale_fisier_clienti)
        with open(self.__cale_fisier_clienti,"w") as f:
            f.write("")
        self.__cale_fisier_inchirieri="inchirieri_test.txt"
        self.__repo_inchiriere_fisier=FileRepoInchiriere(self.__cale_fisier_inchirieri)
        with open(self.__cale_fisier_inchirieri,"w") as f:
            f.write("")
    def testRepoClient(self):
        self.assertEqual(len(self.__repo_client_fisier.get_entitati()),0)
        client_test_1=Client(1,"Ion","1234567890123")
        client_test_2 = Client(2, "Ana", "1234567890123")
        self.__repo_client_fisier.adauga_entitate(client_test_1)
        self.assertEqual(len(self.__repo_client_fisier.get_entitati()),1)
        self.assertEqual(client_test_1, self.__repo_client_fisier.cauta_entitate_dupa_id(1))
        try:
            self.__repo_client_fisier.adauga_entitate(client_test_1)
            assert False
        except RepoError as re:
            assert True
        self.__repo_client_fisier.sterge_entitate(1)
        self.assertNotIn(client_test_1,self.__repo_client_fisier.get_entitati())
        self.__repo_client_fisier.adauga_entitate(client_test_1)
        self.__repo_client_fisier.modifica_entitate(1,client_test_2)
        self.assertEqual(self.__repo_client_fisier.cauta_entitate_dupa_id(1),client_test_2)



if __name__=="__main__":
    teste=Teste()
    teste.ruleaza_toate_testele()
    unittest.main()

