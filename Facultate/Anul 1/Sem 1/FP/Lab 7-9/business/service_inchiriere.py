from domeniu.inchiriere import Inchiriere
import random

class ServiceInchiriere:
    def __init__(self, repo_inchiriere, validator_inchiriere, repo_film, repo_client):
        self._repo_inchiriere = repo_inchiriere
        self._validator_inchiriere = validator_inchiriere
        self._repo_film = repo_film
        self._repo_client = repo_client
        self._base_id=1
    def sterge_inchiriere(self,inchiriere_id):
        inchiriere=self._repo_inchiriere.cauta_inchiriere(inchiriere_id)
        self._validator_inchiriere.valideaza_inchiriere(inchiriere)
        self._repo_inchiriere.sterge_inchiriere(inchiriere_id)
        if(inchiriere_id==self._base_id):
            self._base_id-=1
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
        self._base_id+=1

    def genereaza_inchiriere(self):

        for i in range(1, random.randint(3,5)):
            film_id = random.randint(1, len(self._repo_film.get_all()))
            client_id = random.randint(1, len(self._repo_client.get_all()))
            film = self._repo_film.cauta_film(film_id)
            client = self._repo_client.cauta_client(client_id)
            self.adauga_inchiriere(self._base_id, film, client)
            self._base_id+=1

    def top_clienti(self):
        top={}
        if len(self._repo_inchiriere.get_all())==0:
            return "Nu exista inchirieri"
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_client().get_id()]=0
        for inchiriere in self._repo_inchiriere.get_all():
            top[inchiriere.get_client().get_id()]+=1
        top=sorted(top.items(),key=lambda x:x[1],reverse=True)
        if(len(top)<3):
            return top
        return top




    def get_all(self):
        return self._repo_inchiriere.get_all()


