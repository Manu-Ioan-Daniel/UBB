from domeniu.client import Client
import string
import random
class ServiceClient:
    def __init__(self,_validator_client,_repo_clienti,_repo_clienti_fisier):
        self._validator_client = _validator_client
        self._repo_clienti = _repo_clienti
        self._repo_clienti_fisier=_repo_clienti_fisier
        self.__base_id=1
    def adauga_client(self,id_client,name,cnp):
        client = Client(id_client,name,cnp)
        self._validator_client.valideaza_client(client)
        self._repo_clienti.adauga_client(client)
        self.__base_id+=1
    def adauga_client_fisier(self,id_client,name,cnp):
        client = Client(id_client,name,cnp)
        self._validator_client.valideaza_client(client)
        self._repo_clienti_fisier.adauga_client(client)
        self._base_id+=1
    def sterge_client_fisier(self,id_client):
        self._repo_clienti_fisier.sterge_client(id_client)
        if(id_client==self.__base_id):
            self.__base_id-=1
    def modifica_client_fisier(self,id_client,name,cnp):
        client=Client(id_client,name,cnp)
        self._validator_client.valideaza_client(client)
        self._repo_clienti_fisier.modifica_client(id_client,client)
    def cauta_client_fisier(self,id_client):
        return self._repo_clienti_fisier.cauta_client(id_client)

    def modifica_client(self,client_id,name,cnp):
        client = Client(client_id,name,cnp)
        self._validator_client.valideaza_client(client)
        self._repo_clienti.modifica_client(client_id,client)
    def sterge_client(self,client_id):
        client = self._repo_clienti.cauta_client(client_id)
        self._validator_client.valideaza_client(client)
        self._repo_clienti.sterge_client(client_id)
        if(client_id==self.__base_id):
            self.__base_id-=1
    def cauta_client(self,client_id):
        client = self._repo_clienti.cauta_client(client_id)
        self._validator_client.valideaza_client(client)
        return self._repo_clienti.cauta_client(client_id)
    def clienti_random(self):
        random.seed(1)
        nr_clienti=random.randint(1,6)
        for nr_client in range(nr_clienti):
            lenght_nume_client=random.randint(5,20)
            nume_client="".join(random.choices(string.ascii_lowercase,k=lenght_nume_client))
            cnp_client="".join(random.choices(string.digits,k=13))
            self.adauga_client(self.__base_id,nume_client,cnp_client)
    def clienti_random_fisier(self):
        random.seed(1)
        nr_clienti = random.randint(1, 6)
        for nr_client in range(nr_clienti):
            lenght_nume_client = random.randint(5, 20)
            nume_client = "".join(random.choices(string.ascii_lowercase, k=lenght_nume_client))
            cnp_client = "".join(random.choices(string.digits, k=13))
            self.adauga_client_fisier(self.__base_id, nume_client, cnp_client)

    def get_all(self):
        return self._repo_clienti.get_all()
    def get_all_fisier(self):
        return self._repo_clienti_fisier.get_all()

