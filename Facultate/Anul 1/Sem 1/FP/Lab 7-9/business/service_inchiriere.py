from domeniu.inchiriere import Inchiriere


class ServiceInchiriere:
    def __init__(self,repo_inchiriere,validator_inchiriere,service_film,service_client):
        self._repo_inchiriere = repo_inchiriere
        self._validator_inchiriere = validator_inchiriere
        self._service_film = service_film
        self._service_client = service_client
    def sterge_inchiriere(self,inchiriere_id):
        inchiriere=self._repo_inchiriere.cauta_inchiriere(inchiriere_id)
        self._validator_inchiriere.valideaza_inchiriere(inchiriere)
        self._repo_inchiriere.sterge_inchiriere(inchiriere_id)
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

    def get_all(self):
        return self._repo_inchiriere.get_all()


