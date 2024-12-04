from domeniu.inchiriereDTO import InchiriereDTO
from infrastructura.repo import Repo
class FileRepoInchiriere(Repo):
    def __init__(self,cale_fisier):
        super().__init__()
        self.__cale_fisier=cale_fisier
    def citeste_tot_din_fisier(self):
        with open(self.__cale_fisier,"r") as f:
            self._entitati.clear()
            lines=f.readlines()
            for line in lines:
                line.strip()
                if line!="":
                    line.split(",")
                    id_entitate=int(line[0])
                    id_film=line[1]
                    id_client=line[2]
                    inchiriere_dto=InchiriereDTO(id_entitate,id_film,id_client)
                    self._entitati[id_entitate]=inchiriere_dto
    def scrie_tot_in_fisier(self):
        with open(self.__cale_fisier,"w") as f:
            for id_entitate in self._entitati:
                inchiriere_dto=self._entitati[id_entitate]
                f.write(f"{inchiriere_dto.get_id_entitate()},{inchiriere_dto.get_id_film()},{inchiriere_dto.get_id_client()}\n")
    def adauga_entitate(self,entitate):
        self.citeste_tot_din_fisier()
        Repo.adauga_entitate(self,entitate)
        self.scrie_tot_in_fisier()
    def sterge_entitate(self,id_entitate):
        self.citeste_tot_din_fisier()
        Repo.sterge_entitate(self,id_entitate)
        self.scrie_tot_in_fisier()
    def modifica_entitate(self,id_entitate,entitate):
        self.citeste_tot_din_fisier()
        Repo.modifica_entitate(self,id_entitate,entitate)
        self.scrie_tot_in_fisier()
    def cauta_entitate_dupa_id(self,id_entitate):
        self.citeste_tot_din_fisier()
        return Repo.cauta_entitate_dupa_id(self,id_entitate)
    def get_entitati(self):
        return [x for x in self._entitati.values()]