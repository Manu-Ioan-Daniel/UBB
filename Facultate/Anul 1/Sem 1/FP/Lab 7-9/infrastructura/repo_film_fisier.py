from domeniu.film import Film
from infrastructura.repo import Repo
class FileRepoFilm(Repo):
    def __init__(self,cale_fisier):
        super().__init__()
        self.__cale_fisier=cale_fisier
        open(self.__cale_fisier,"w").close()
    def citeste_tot_din_fisier(self):
        with open (self.__cale_fisier,"r") as f:
            self._entitati.clear()
            linii=f.readlines()
            for linie in linii:
                linie=linie.strip()
                if linie!="":
                    parti=linie.split(",")
                    id_film=int(parti[0])
                    titlu=parti[1]
                    descriere=parti[2]
                    gen=parti[3]
                    self._entitati[id_film]=Film(id_film,titlu,descriere,gen)
    def scrie_tot_in_fisier(self):
        with open(self.__cale_fisier,"w") as f:
            for id_film in self._entitati:
                film=self._entitati[id_film]
                f.write(f"{film.get_id()},{film.get_title()},{film.get_description()},{film.get_gen()}\n")
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