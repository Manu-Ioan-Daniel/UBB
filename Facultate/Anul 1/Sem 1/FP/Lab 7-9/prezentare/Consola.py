from exceptii.erori import RepoError
from exceptii.erori import ValidationError
class Consola:
    def __init__(self,_service_filme,_service_clienti):
        self.__service_filme = _service_filme
        self.__service_clienti = _service_clienti
        self.__comenzi={
            "print_filme":self.__ui_print_filme,
            "adauga_film":self.__ui_adauga_film,
            "modifica_film":self.__ui_modifica_film,
            "sterge_film":self.__ui_sterge_film,
            "print_clienti":self.__ui_print_clienti,
            "adauga_client":self.__ui_adauga_clienti,
            "modifica_client":self.__ui_modifica_clienti,
            "sterge_client":self.__ui_sterge_client,
            "help":self.__ui_help

        }
    def __ui_help(self):
        print("Toate comenzile:print_filme\nadauga_film\nmodifica_film\nsterge_film\nprint_clienti\nadauga_client\nmodifica_client\nsterge_client")
    def __ui_print_filme(self):
        filme=self.__service_filme.get_all()
        if len(filme)==0:
            print("Nu exista filme")
            return
        print("Filmele sunt: ")
        for film in filme:
            print(film)
    def __ui_adauga_film(self):
        id_film=int(input("ID film: "))
        titlu_film=input("Tilu film: ")
        descriptie_film=input("Descriptie film: ")
        gen_film=input("Gen film: ")
        self.__service_filme.adauga_film(id_film,titlu_film,descriptie_film,gen_film)
    def __ui_modifica_film(self):
        id_film=int(input("ID-ul filmului pe care il modifici: "))
        titlu_film=input("Titlul dorit: ")
        descriptie_film=input("Descriptie dorita: ")
        gen_film=input("Gen dorit: ")
        self.__service_filme.modifica_film(id_film,titlu_film,descriptie_film,gen_film)
    def __ui_sterge_film(self):
        id_film=int(input("ID-ul filmului pe care vrei sa il stergi: "))
        self.__service_filme.sterge_film(id_film)
    def __ui_print_clienti(self):
        clienti=self.__service_clienti.get_all()
        if len(clienti)==0:
            print("Nu exista clienti")
            return
        for client in clienti:
            print(client)
    def __ui_adauga_clienti(self):
        id_client=int(input("ID client: "))
        nume_client=input("Nume client: ")
        cnp_client=input("CNP client: ")
        self.__service_clienti.adauga_client(id_client,nume_client,cnp_client)
    def __ui_modifica_clienti(self):
        id_client=int(input("ID-ul clientului pe care vrei sa il modifici: "))
        nume_client=input("Numele pe care il doresti: ")
        cnp_client=input("CNP-ul dorit: ")
        self.__service_clienti.modifica_client(id_client,nume_client, cnp_client)
    def __ui_sterge_client(self):
        id_client=int(input("ID-ul clientului pe care vrei sa il stergi: "))
        self.__service_clienti.sterge_client(id_client)
    def run(self):
        while True:
            nume_comanda = input("Introdu comanda: ")
            nume_comanda = nume_comanda.lower()
            nume_comanda = nume_comanda.strip()
            if nume_comanda=="exit":
                break
            if nume_comanda=="":
                continue

            if nume_comanda in self.__comenzi:
                try:
                    self.__comenzi[nume_comanda]()
                except ValidationError as ve:
                    print(f"Eroare de validare: {ve}")
                except RepoError as re:
                    print(f"Eroare de repo: {re}")
                except ValueError as ve:
                    print(f"Valoare numerica invalida: {ve}")
            else:
                print("Comanda invalida")

