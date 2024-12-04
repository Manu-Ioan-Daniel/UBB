from infrastructura.repo import Repo
from domeniu.client import Client
class FileRepoClient(Repo):
    def __init__(self, cale_fisier):
        super().__init__()
        self.__cale_fisier = cale_fisier
        open(self.__cale_fisier, 'w').close()

    def __citeste_tot_din_fisier(self):
        with open(self.__cale_fisier, "r") as f:
            self._entitati.clear()
            linii = f.readlines()
            for linie in linii:
                linie = linie.strip()
                if linie != "":
                    parti = linie.split(",")
                    id_client = int(parti[0])
                    nume = parti[1]
                    cnp = parti[2]
                    self._entitati[id_client] = Client(id_client, nume, cnp)

    def __scrie_tot_in_fisier(self):
        with open(self.__cale_fisier, "w") as f:
            for id_client in self._entitati:
                client = self._entitati[id_client]
                f.write(f"{client.get_id()},{client.get_nume()},{client.get_cnp()}\n")

    def get_entitati(self):
        self.__citeste_tot_din_fisier()
        print(self._entitati)
        return Repo.get_entitati(self)

    def __len__(self):
        self.__citeste_tot_din_fisier()
        return Repo.__len__(self)

    def adauga_client(self, client):
        self.__citeste_tot_din_fisier()
        Repo.adauga_entitate(self,client)
        self.__scrie_tot_in_fisier()

    def cauta_client(self, id_client):
        self.__citeste_tot_din_fisier()
        return Repo.cauta_entitate_dupa_id(self, id_client)

    def sterge_client(self, id_client):
        self.__citeste_tot_din_fisier()
        Repo.sterge_entitate(self, id_client)
        self.__scrie_tot_in_fisier()

    def modifica_client(self, id_client, client):
        self.__citeste_tot_din_fisier()
        Repo.modifica_entitate(self, id_client, client)
        self.__scrie_tot_in_fisier()