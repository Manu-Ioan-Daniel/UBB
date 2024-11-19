class ServiceInchiriere:
    def __init(self,repo_inchiriere,validator_inchiriere,repo_filme,repo_clienti):
        self.__repo_inchiriere = repo_inchiriere
        self.__validator_inchiriere = validator_inchiriere
        self.__repo_filme = repo_filme
        self.__repo_clienti = repo_clienti
    def sterge_inchiriere(self,inchiriere):
        self.__validator_inchiriere.valideaza_inchiriere(inchiriere)
        self.__repo_inchiriere.sterge_inchiriere(inchiriere.get_id_inchiriere())
