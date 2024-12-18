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
from sortare.bingo_sort import my_bingo_sort
from sortare.merge_sort import my_merge_sort
import random
import string
class TesteUnitTest(unittest.TestCase):
    def setUp(self):
        self.__cale_fisier_filme="filme_test.txt"
        self.__repo_film_fisier=FileRepoFilm(self.__cale_fisier_filme)
        self.__repo_film=RepoFilm()
        self.__repo_client=RepoClient()
        self.__repo_inchiriere=RepoInchiriere()
        self.__validator_film=ValidatorFilm()
        self.__validator_client=ValidatorClient()
        self.__validator_inchiriere=ValidatorInchiriere()
        self.__service_film=ServiceFilm(self.__validator_film,self.__repo_film)
        self.__service_client=ServiceClient(self.__validator_client,self.__repo_client)
        self.__service_inchiriere=ServiceInchiriere(self.__repo_inchiriere,self.__validator_inchiriere,self.__repo_film,self.__repo_client)
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
        self.client = Client(23, "Manu", "505")
        self.client2 = Client(23, "Manuu", "505")
        self.film = Film(23, "Manu", "un baiat si o fata", "romantic")
        self.film2 = Film(23, "Manuu", "un baiat si o fata", "romantic")
        self.inchiriere = Inchiriere(23, self.film, self.client)
        self.inchiriere2 = Inchiriere(23, self.film2, self.client2)
    def testRepoFilm(self):
        #adauga film repo
        film_test_1=self.film
        film_test_2=self.film2
        self.__repo_film.adauga_film(film_test_1)
        self.assertEqual(len(self.__repo_film.get_all()),1)
        self.assertEqual(film_test_1,self.__repo_film.get_all()[0])
        with self.assertRaises(RepoError):
            self.__repo_film.adauga_film(film_test_1)
        #modifica film repo
        self.__repo_film.modifica_film(23,film_test_2)
        self.assertEqual(self.__repo_film.get_all()[0],film_test_2)
        with self.assertRaises(RepoError):
            self.__repo_film.modifica_film(24,film_test_2)
        #sterge film repo
        self.__repo_film.sterge_film(23)
        self.assertNotIn(film_test_1,self.__repo_film.get_all())
        with self.assertRaises(RepoError):
            self.__repo_film.sterge_film(23)
        #cauta film repo
        self.__repo_film.adauga_film(film_test_1)
        self.assertEqual(self.__repo_film.cauta_film(23),film_test_1)
        with self.assertRaises(RepoError):
            self.__repo_film.cauta_film(100)

    def testRepoClient(self):


        # test adauga client

        self.__repo_client.adauga_client(self.client)
        self.assertEqual(self.__repo_client.get_all()[0], self.client)
        with self.assertRaises(RepoError):
            self.__repo_client.adauga_client(self.client)

        # test modifica client

        self.__repo_client.modifica_client(self.client.get_id(), self.client2)
        self.assertEqual(self.__repo_client.get_all()[0], self.client2)
        self.assertNotIn(self.client, self.__repo_client.get_all())
        with self.assertRaises(RepoError):
            self.__repo_client.modifica_client(24, self)

        # test sterge client

        self.__repo_client.sterge_client(self.client2.get_id())
        self.assertNotIn(self.client2, self.__repo_client.get_all())
        with self.assertRaises(RepoError):
            self.__repo_client.sterge_client(self.client2.get_id())

        # test cauta client

        self.__repo_client.adauga_client(self.client)
        client_cautat = self.__repo_client.cauta_client(self.client.get_id())
        self.assertEqual(self.client, client_cautat)
        with self.assertRaises(RepoError):
            self.__repo_client.cauta_client(100)

    def testRepoInchiriere(self):
        #test adauga inchiriere
        self.__repo_inchiriere.adauga_inchiriere(self.inchiriere)
        self.assertEqual(self.__repo_inchiriere.get_all()[0],self.inchiriere)
        with self.assertRaises(RepoError):
            self.__repo_inchiriere.adauga_inchiriere(self.inchiriere)

        #test modifica inchiriere

        self.__repo_inchiriere.modifica_inchiriere(self.inchiriere.get_id(),self.inchiriere2)
        self.assertEqual(self.__repo_inchiriere.get_all()[0],self.inchiriere2)
        self.assertNotIn(self.inchiriere,self.__repo_inchiriere.get_all())
        with self.assertRaises(RepoError):
            self.__repo_inchiriere.modifica_inchiriere(24,self.inchiriere2)

        #test sterge inchiriere

        self.__repo_inchiriere.sterge_inchiriere(self.inchiriere2.get_id())
        self.assertNotIn(self.inchiriere2,self.__repo_inchiriere.get_all())
        with self.assertRaises(RepoError):
            self.__repo_inchiriere.sterge_inchiriere(self.inchiriere2.get_id())

        #test cauta inchiriere

        self.__repo_inchiriere.adauga_inchiriere(self.inchiriere)
        inchiriere_cautata=self.__repo_inchiriere.cauta_inchiriere(self.inchiriere.get_id())
        self.assertEqual(self.inchiriere,inchiriere_cautata)
        with self.assertRaises(RepoError):
            self.__repo_inchiriere.cauta_inchiriere(100)

    def testValidatorFilm(self):

        self.__validator_film.valideaza_film(self.film)
        film = Film(-1, "", "", "")
        with self.assertRaises(ValidationError) as ve:
            self.__validator_film.valideaza_film(film)
        self.assertEqual(str(ve.exception),"Id invalid\nGen invalid\nTitlu invalid\nDescriptie invalid\n")


    def testValidatorClient(self):

            self.__validator_client.valideaza_client(self.client)
            client = Client(-1, "", "")
            with self.assertRaises(ValidationError) as ve:
                self.__validator_client.valideaza_client(client)
            self.assertEqual(str(ve.exception),"Id invalid\nName invalid\nCnp invalid\n")



    def testValidatorInchiriere(self):
        self.__validator_inchiriere.valideaza_inchiriere(self.inchiriere)
        inchiriere = Inchiriere(-1, self.film, self.client)
        with self.assertRaises(ValidationError):
            self.__validator_inchiriere.valideaza_inchiriere(inchiriere)


    def testServiceFilm(self):

        #test adauga film service

        self.__service_film.adauga_film(23,"Manu","un baiat si o fata","romantic")
        self.assertEqual(self.__service_film.get_all()[0],self.film)
        with self.assertRaises(RepoError):
            self.__service_film.adauga_film(23,"Manu","un baiat si o fata","romantic")

        #test modifica film service

        self.__service_film.modifica_film(23,"Manuu","un baiat si o fata","romantic")
        self.assertEqual(self.__service_film.get_all()[0],self.film2)
        self.assertNotIn(self.film,self.__service_film.get_all())
        with self.assertRaises(RepoError):
            self.__service_film.modifica_film(24,"Manuu","un baiat si o fata","romantic")

        #test sterge film service

        self.__service_film.sterge_film(23)
        self.assertNotIn(self.film2,self.__service_film.get_all())
        with self.assertRaises(RepoError):
            self.__service_film.sterge_film(23)

        #test cauta film

        self.__service_film.adauga_film(23,"Manu","un baiat si o fata","romantic")
        film_cautat=self.__service_film.cauta_film(23)
        self.assertEqual(film_cautat,self.film)
        with self.assertRaises(RepoError):
            self.__service_film.cauta_film(100)


        #test genereaza filme random

        self.__service_film.sterge_film(23)
        self.__service_film.filme_random()
        filme=self.__service_film.get_all()
        self.assertTrue(2<=len(filme)<=6)
        for film in filme:
            self.assertTrue(len(film.get_title()) >= 5)
            self.assertTrue(len(film.get_description()) >= 5)
            self.assertTrue(len(film.get_gen()) >= 1)

    def testServiceClient(self):

        #test adauga client service

        self.__service_client.adauga_client(23,"Manu","505")
        self.assertEqual(self.__service_client.get_all()[0],self.client)
        with self.assertRaises(RepoError):
            self.__service_client.adauga_client(23, "Manu", "505")

        #test modifica client service

        self.__service_client.modifica_client(23,"Manuu","505")
        self.assertEqual(self.__service_client.get_all()[0],self.client2)
        self.assertNotIn(self.client,self.__service_client.get_all())
        with self.assertRaises(RepoError):
            self.__service_client.modifica_client(24,"Manuu","505")

        #test sterge client service

        self.__service_client.sterge_client(23)
        self.assertNotIn(self.client2,self.__service_client.get_all())
        with self.assertRaises(RepoError):
            self.__service_client.sterge_client(23)

        #test cauta client service

        self.__service_client.adauga_client(23,"Manu","505")
        client_cautat=self.__service_client.cauta_client(23)
        self.assertEqual(client_cautat,self.client)
        with self.assertRaises(RepoError):
            self.__service_client.cauta_client(100)

        #test genereaza clienti random

        self.__service_client.sterge_client(23)
        self.__service_client.clienti_random()
        clienti=self.__service_client.get_all()
        self.assertTrue(2<=len(clienti)<=6)
        for client in clienti:
            self.assertTrue(len(client.get_nume()) >= 5)
            self.assertEqual(len(client.get_cnp()),13)

    def testServiceInchiriere(self):

        #test adauga inchiriere service

        self.__service_inchiriere.adauga_inchiriere(23,self.film,self.client)
        self.assertEqual(self.__service_inchiriere.get_all()[0],self.inchiriere)
        with self.assertRaises(RepoError):
            self.__service_inchiriere.adauga_inchiriere(23,self.film,self.client)

        #test modifica inchiriere service

        self.__service_inchiriere.modifica_inchiriere(23,self.film2,self.client2)
        self.assertEqual(self.__service_inchiriere.get_all()[0],self.inchiriere2)
        self.assertNotIn(self.inchiriere,self.__service_inchiriere.get_all())
        with self.assertRaises(RepoError):
            self.__service_inchiriere.modifica_inchiriere(24,self.film2,self.client2)

        #test sterge inchiriere service

        self.__service_inchiriere.sterge_inchiriere(23)
        self.assertNotIn(self.inchiriere2,self.__service_inchiriere.get_all())
        with self.assertRaises(RepoError):
            self.__service_inchiriere.sterge_inchiriere(23)

        #test cauta inchiriere service

        self.__service_inchiriere.adauga_inchiriere(23,self.film,self.client)
        inchiriere_cautata=self.__service_inchiriere.cauta_inchiriere(23)
        self.assertEqual(inchiriere_cautata,self.inchiriere)
        with self.assertRaises(RepoError):
            self.__service_inchiriere.cauta_inchiriere(100)


        #test top clienti

        filme=[
            Film(1,"avengers","1234567890123","1234567890123"),
            Film(2,"salut","1234567890123","1234567890123"),
            Film(3,"accelerare","1234567890123","1234567890123"),
            Film(4,"intrare","1234567890123","1234567890123"),
            Film(5,"iesire","1234567890123","1234567890123")
        ]
        clienti=[
            Client(1,"Ion","1234567890123"),
            Client(2,"Ana","1234567890123"),
            Client(3,"Maria","1234567890123"),
            Client(4,"Andrei","1234567890123"),
            Client(5,"Vlad","1234567890123")
        ]
        inchirieri=[
            Inchiriere(1,filme[0],clienti[0]),
            Inchiriere(2,filme[1],clienti[1]),
            Inchiriere(3,filme[2],clienti[2]),
            Inchiriere(4,filme[0],clienti[1]),
            Inchiriere(5,filme[1],clienti[1]),
            Inchiriere(6,filme[2],clienti[1]),
            Inchiriere(7,filme[3],clienti[1]),
            Inchiriere(8,filme[4],clienti[0]),
            Inchiriere(9,filme[1],clienti[0]),
            Inchiriere(10,filme[3],clienti[3]),
            Inchiriere(11,filme[4],clienti[4])

        ]
        for inchiriere in inchirieri:
            self.__service_inchiriere.adauga_inchiriere(inchiriere.get_id(),inchiriere.get_film(),inchiriere.get_client())
        top_clienti=self.__service_inchiriere.top_clienti()
        self.assertEqual(top_clienti[0],(clienti[1].get_id(),5))
        self.assertEqual(top_clienti[1],(clienti[0].get_id(),3))
        self.assertEqual(top_clienti[2],(clienti[4].get_id(),1))
        self.assertEqual(top_clienti[3],(clienti[3].get_id(),1))
        self.assertEqual(top_clienti[4],(clienti[2].get_id(),1))

        #test top_30_clienti

        top_30_clienti=self.__service_inchiriere.top_30_clienti()
        self.assertEqual(top_30_clienti[0],(clienti[1].get_id(),5))
        self.assertEqual(top_30_clienti[1],(clienti[0].get_id(),3))
        self.assertEqual(len(top_30_clienti),2)

        #test top_filme

        top_filme=self.__service_inchiriere.top_filme()
        self.assertEqual(top_filme[0],(filme[1].get_id(),3))
        self.assertEqual(top_filme[1],(filme[4].get_id(),2))
        self.assertEqual(top_filme[2],(filme[3].get_id(),2))
        self.assertEqual(top_filme[3],(filme[2].get_id(),2))
        self.assertEqual(top_filme[4],(filme[0].get_id(),2))




    def testRepoClient_fisier(self):
        self.assertEqual(len(self.__repo_client_fisier.get_entitati()),0)
        client_test_1=Client(1,"Ion","1234567890123")
        client_test_2 = Client(1, "Ana", "1234567890123")
        self.__repo_client_fisier.adauga_client(client_test_1)
        self.assertEqual(len(self.__repo_client_fisier.get_entitati()),1)
        self.assertEqual(client_test_1, self.__repo_client_fisier.get_entitati()[0])
        try:
            self.__repo_client_fisier.adauga_entitate(client_test_1)
            assert False
        except RepoError as re:
            assert True
        self.__repo_client_fisier.sterge_client(1)
        self.assertNotIn(client_test_1,self.__repo_client_fisier.get_entitati())
        self.__repo_client_fisier.adauga_client(client_test_1)
        self.__repo_client_fisier.modifica_client(1,client_test_2)
        self.assertEqual(self.__repo_client_fisier.cauta_client(1),client_test_2)
    def testRepoFilm_fisier(self):
        self.assertEqual(len(self.__repo_film_fisier.get_entitati()),0)
        film_test_1=Film(1,"Ion","1234567890123","1234567890123")
        film_test_2=Film(1,"Ana","1234567890123","1234567890123")
        self.__repo_film_fisier.adauga_entitate(film_test_1)
        self.assertEqual(len(self.__repo_film_fisier.get_entitati()),1)
        self.assertEqual(film_test_1,self.__repo_film_fisier.get_entitati()[0])
        try:
            self.__repo_film_fisier.adauga_entitate(film_test_1)
            assert False
        except RepoError as re:
            assert True
        self.__repo_film_fisier.sterge_entitate(1)
        self.assertNotIn(film_test_1,self.__repo_film_fisier.get_entitati())
        self.__repo_film_fisier.adauga_entitate(film_test_1)
        self.__repo_film_fisier.modifica_entitate(1,film_test_2)
        self.assertEqual(self.__repo_film_fisier.cauta_entitate_dupa_id(1),film_test_2)
    def testRepoInchiriere_fisier(self):
        self.assertEqual(len(self.__repo_inchiriere_fisier.get_entitati()),0)
        inchiriere_test_1=InchiriereDTO(1,1,1)
        inchiriere_test_2=InchiriereDTO(1,2,2)
        self.__repo_inchiriere_fisier.adauga_entitate(inchiriere_test_1)
        self.assertEqual(len(self.__repo_inchiriere_fisier.get_entitati()),1)
        self.assertEqual(inchiriere_test_1,self.__repo_inchiriere_fisier.get_entitati()[0])
        try:
            self.__repo_inchiriere_fisier.adauga_entitate(inchiriere_test_1)
            assert False
        except RepoError as re:
            assert True
        self.__repo_inchiriere_fisier.sterge_entitate(1)
        self.assertNotIn(inchiriere_test_1,self.__repo_inchiriere_fisier.get_entitati())
        self.__repo_inchiriere_fisier.adauga_entitate(inchiriere_test_1)
        self.__repo_inchiriere_fisier.modifica_entitate(1,inchiriere_test_2)
        self.assertEqual(self.__repo_inchiriere_fisier.cauta_entitate_dupa_id(1),inchiriere_test_2)
    def test_bingo_sort(self):
        lista=[5,4,3,2,1]
        lista=my_bingo_sort(lista,key=lambda x:x)
        self.assertEqual(lista,[1,2,3,4,5])
        lista=[2,7,3,1,5]
        lista=my_bingo_sort(lista,key=lambda x:x,reverse=True)
        self.assertEqual(lista,[7,5,3,2,1])

        lista_clienti=[Client(1,"Ionela","1234567890123"),Client(2,"Ana","1234567890123"),Client(3,"Maria","1234567890123")]
        lista_clienti=my_bingo_sort(lista_clienti,key=lambda x:x.get_id(),reverse=True)
        self.assertEqual(lista_clienti,[Client(3,"Maria","1234567890123"),Client(2,"Ana","1234567890123"),Client(1,"Ionela","1234567890123")])
        lista_clienti=my_bingo_sort(lista_clienti,key=lambda x:len(x.get_nume()))
        self.assertEqual(lista_clienti,[Client(2,"Ana","1234567890123"),Client(3,"Maria","1234567890123"),Client(1,"Ionela","1234567890123")])
    def test_merge_sort(self):
        lista=[5,4,3,2,1]
        lista=my_merge_sort(lista,key=lambda x:x)
        self.assertEqual(lista,[1,2,3,4,5])
        lista=[2,7,3,1,5]
        lista=my_merge_sort(lista,key=lambda x:x,reverse=True)
        self.assertEqual(lista,[7,5,3,2,1])
        lista_clienti=[Client(1,"Ionela","1234567890123"),Client(2,"Ana","1234567890123"),Client(3,"Maria","1234567890123")]
        lista_clienti=my_merge_sort(lista_clienti,key=lambda x:x.get_id(),reverse=True)
        self.assertEqual(lista_clienti,[Client(3,"Maria","1234567890123"),Client(2,"Ana","1234567890123"),Client(1,"Ionela","1234567890123")])
        lista_clienti=my_merge_sort(lista_clienti,key=lambda x:len(x.get_nume()))
        self.assertEqual(lista_clienti,[Client(2,"Ana","1234567890123"),Client(3,"Maria","1234567890123"),Client(1,"Ionela","1234567890123")])



