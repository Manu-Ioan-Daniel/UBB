from domeniu.client import Client
class ServiceClient:
    def __init__(self,_validator_client,_repo_clienti):
        self._validator_client = _validator_client
        self._repo_clienti = _repo_clienti
    def adauga_client(self,client_id,name,cnp):
        client = Client(client_id,name,cnp)
        self._validator_client.valideaza_client(client)
        self._repo_clienti.adauga_client(client)
    def modifica_client(self,client_id,name,cnp):
        client = Client(client_id,name,cnp)
        self._validator_client.valideaza_client(client)
        self._repo_clienti.modifica_client(client_id,client)
    def sterge_client(self,client_id):
        client = self._repo_clienti.cauta_client(client_id)
        self._validator_client.valideaza_client(client)
        self._repo_clienti.sterge_client(client_id)
    def cauta_client(self,client_id):
        client = self._repo_clienti.cauta_client(client_id)
        self._validator_client.valideaza_client(client)
        return self._repo_clienti.cauta_client(client_id)
    def get_all(self):
        return self._repo_clienti.get_all()
