from exceptii.erori import RepoError
class Repo():
    def __init__(self):
        self._entitati={}

    def adauga_entitate(self,entitate):
        if entitate.get_id() in self._entitati:
            raise RepoError(f"Entitatea cu id-ul {entitate.get_id()} exista deja!")
        self._entitati[entitate.get_id()]=entitate
    def sterge_entitate(self,id_entitate):
        if id_entitate not in self._entitati:
            raise RepoError(f"Nu exista entitate cu id-ul {id_entitate}!")
        del self._entitati[id_entitate]
    def cauta_entitate_dupa_id(self,id_entitate):
        if id_entitate not in self._entitati:
            raise RepoError(f"Nu exista entitate cu id-ul {id_entitate}!")
        return self._entitati[id_entitate]
    def modifica_entitate(self,id_entitate,entitate):
        if id_entitate not in self._entitati:
            raise RepoError(f"Nu exista entitate cu id-ul {id_entitate}!")
        self._entitati[id_entitate]=entitate
    def get_entitati(self):
        return [x for x in self._entitati.values()]
    def __len__(self):
        return len(self._entitati)