from exceptii.erori import RepoError
class RepoClient:
    def __init__(self):
        self.__clienti={}
    def adauga_client(self,client):
        if client.get_id() in self.__clienti:
            raise RepoError("Client cu acelasi id existent")
        self.__clienti[client.get_id()]=client
    def sterge_client(self,client_id):
        if client_id not in self.__clienti:
            raise RepoError("Nu exista client cu acest id")
        self.__clienti.pop(client_id)
    def modifica_client(self,id_client,client):
        if id_client not in self.__clienti:
            raise RepoError("Nu exista client cu acest id")
        self.__clienti[id_client]=client
    def cauta_client(self,client_id):
        if client_id not in self.__clienti:
            raise RepoError("Nu exista client cu acest id")
        return self.__clienti[client_id]
    def get_all(self):
        return[x for x in self.__clienti.values()]
