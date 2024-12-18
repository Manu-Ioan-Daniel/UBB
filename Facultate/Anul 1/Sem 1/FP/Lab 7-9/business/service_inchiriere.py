from domeniu.inchiriere import Inchiriere
from domeniu.inchiriereDTO import InchiriereDTO
from sortare.merge_sort import sortare
import random

class ServiceInchiriere:
    def __init__(self, repo_inchiriere, validator_inchiriere, repo_film, repo_client,repo_inchiriere_fisier=None,repo_film_fisier=None,repo_client_fisier=None):
        self._repo_inchiriere = repo_inchiriere
        self._validator_inchiriere = validator_inchiriere
        self._repo_film = repo_film
        self._repo_client = repo_client
        self._repo_film_fisier=repo_film_fisier
        self._repo_client_fisier=repo_client_fisier
        self._repo_inchiriere_fisier=repo_inchiriere_fisier
        self._base_id=1
        self._base_id_fisier=1
    def sterge_inchiriere(self,inchiriere_id):
        inchiriere=self._repo_inchiriere.cauta_inchiriere(inchiriere_id)
        self._validator_inchiriere.valideaza_inchiriere(inchiriere)
        self._repo_inchiriere.sterge_inchiriere(inchiriere_id)
        if(inchiriere_id==self._base_id):
            self._base_id-=1
    def sterge_inchiriere_fisier(self,inchiriere_id):
        self._repo_inchiriere_fisier.sterge_inchiriere(inchiriere_id)
        if(inchiriere_id==self._base_id_fisier):
            self._base_id_fisier-=1
    def cauta_inchiriere_fisier(self,inchiriere_id):
        return self._repo_inchiriere_fisier.cauta_inchiriere(inchiriere_id)
    def adauga_inchiriere_fisier(self,inchiriere_id,id_film,id_client):
        inchiriereDTO=InchiriereDTO(inchiriere_id,id_film,id_client)
        self._repo_inchiriere_fisier.adauga_entitate(inchiriereDTO)
        if self._base_id_fisier<inchiriere_id:
            self._base_id_fisier=inchiriere_id
        self._base_id_fisier+=1
    def modifica_inchiriere_fisier(self,inchiriere_id,id_film,id_client):
        inchiriereDTO=InchiriereDTO(inchiriere_id,id_film,id_client)
        self._repo_inchiriere_fisier.modifica_inchiriere(inchiriere_id,inchiriereDTO)
    def creeaza_inchiriere_random_fisier(self):
        for i in range(1, random.randint(3,5)):

            film_id = random.randint(1, len(self._repo_film_fisier.get_entitati()))
            client_id = random.randint(1, len(self._repo_client_fisier.get_entitati()))
            self.adauga_inchiriere_fisier(self._base_id_fisier, film_id, client_id)
    def modifica_inchiriere(self,inchiriere_id,film,client):
        inchiriere=Inchiriere(inchiriere_id,film,client)
        self._validator_inchiriere.valideaza_inchiriere(inchiriere)
        self._repo_inchiriere.modifica_inchiriere(inchiriere_id,inchiriere)
    def cauta_inchiriere(self,inchiriere_id):
        inchiriere=self._repo_inchiriere.cauta_inchiriere(inchiriere_id)
        self._validator_inchiriere.valideaza_inchiriere(inchiriere)
        return inchiriere
    def adauga_inchiriere(self,inchiriere_id,film,client):
        inchiriere=Inchiriere(inchiriere_id,film,client)
        self._validator_inchiriere.valideaza_inchiriere(inchiriere)
        self._repo_inchiriere.adauga_inchiriere(inchiriere)
        if(self._base_id<inchiriere_id):
            self._base_id=inchiriere_id
        self._base_id+=1

    def creeaza_inchiriere_random(self):

        for i in range(1, random.randint(3,5)):
            film_id = random.randint(1, len(self._repo_film.get_all()))
            client_id = random.randint(1, len(self._repo_client.get_all()))
            film = self._repo_film.cauta_film(film_id)
            client = self._repo_client.cauta_client(client_id)
            self.adauga_inchiriere(self._base_id, film, client)

    def top_30_clienti(self):
        top={}
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_client().get_id()]=0
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_client().get_id()]+=1
        top=sortare(list(top.items()),key=lambda x:x[1],reverse=True)
        if(len(top)<3):
            return top[0]
        return top[:int(len(top)*3/10)+1]
    def top_30_clienti_fisier(self):
        top={}
        for inchiriereDTO in self._repo_inchiriere_fisier.get_entitati():
            top[inchiriereDTO.get_client_id()]=0

        for inchiriereDTO in self._repo_inchiriere_fisier.get_entitati():
            top[inchiriereDTO.get_client_id()]+=1
        top=sortare(list(top.items()),key=lambda x:x[1],reverse=True)
        if(len(top)<3):
            return top
        return top[:int(len(top)*3/10)+1]
    def top_clienti_fisier(self):
        top={}
        for inchiriereDTO in self._repo_inchiriere_fisier.get_entitati():
            top[inchiriereDTO.get_client_id()]=0
        for inchiriereDTO in self._repo_inchiriere_fisier.get_entitati():
            top[inchiriereDTO.get_client_id()]+=1
        top=sortare(list(top.items()),key=lambda x:x[1],reverse=True)
        return top
    def top_clienti(self):
        top={}
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_client().get_id()]=0
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_client().get_id()]+=1
        top=sortare(list(top.items()),key=lambda x:x[1],reverse=True)
        return top
    def top_filme_fisier(self):
        top={}
        for inchiriere in self._repo_inchiriere_fisier.get_entitati():
            top[inchiriere.get_film_id()]=0
        for inchiriere in self._repo_inchiriere_fisier.get_entitati():
            top[inchiriere.get_film_id()]+=1
        top=sortare(list(top.items()),key=lambda x:x[1],reverse=True)
        return top

    def top_filme(self):
        top={}
        if len(self._repo_inchiriere.get_all())==0:
            return "Nu exista inchirieri"
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_film().get_id()]=0
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_film().get_id()]+=1
        top=sortare(list(top.items()),key=lambda x:x[1],reverse=True)
        return top

    def get_all(self):
        return self._repo_inchiriere.get_all()
    def get_all_fisier(self):
        return self._repo_inchiriere_fisier.get_entitati()

