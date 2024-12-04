from business import service_inchiriere
from exceptii.erori import RepoError
from exceptii.erori import ValidationError
class Consola:
    def __init__(self,_service_filme,_service_clienti,_service_inchiriere):
        self.__service_filme = _service_filme
        self.__service_clienti = _service_clienti
        self.__service_inchiriere = _service_inchiriere
        self.__comenzi={
            "print_filme":self.__ui_print_filme,
            "adauga_film":self.__ui_adauga_film,
            "modifica_film":self.__ui_modifica_film,
            "sterge_film":self.__ui_sterge_film,
            "print_clienti":self.__ui_print_clienti,
            "adauga_client":self.__ui_adauga_clienti,
            "modifica_client":self.__ui_modifica_clienti,
            "sterge_client":self.__ui_sterge_client,
            "cauta_film":self.__ui_cauta_film,
            "cauta_client":self.__ui_cauta_client,
            "adauga_inchiriere":self.__ui_adauga_inchiriere,
            "modifica_inchiriere":self.__ui_modifica_inchiriere,
            "sterge_inchiriere":self.__ui_sterge_inchiriere,
            "cauta_inchiriere":self.__ui_cauta_inchiriere,
            "print_inchiriere":self.__ui_print_inchiriere,
            "creeaza_clienti_random":self.__ui_creeaza_clienti_random,
            "creeaza_filme_random":self.__ui_creeaza_filme_random,
            "creeaza_inchirieri_random":self.__ui_creeaza_inchirieri_random,
            "top_30_clienti":self.__ui_top_30_clienti,
            "top_clienti":self.__ui_top_clienti,
            "top_filme":self.__ui_top_filme,
            "print_filme_fisier":self.__ui_print_filme_fisier,
            "print_clienti_fisier":self.__ui_print_clienti_fisier,
            "print_inchiriere_fisier":self.__ui_print_inchiriere_fisier,
            "cauta_film_fisier":self.__ui_cauta_film_fisier,
            "cauta_client_fisier":self.__ui_cauta_client_fisier,
            "cauta_inchiriere_fisier":self.__ui_cauta_inchiriere_fisier,
            "adauga_film_fisier":self.__ui_adauga_film_fisier,
            "adauga_client_fisier":self.__ui_adauga_clienti_fisier,
            "adauga_inchiriere_fisier":self.__ui_adauga_inchiriere_fisier,
            "modifica_film_fisier":self.__ui_modifica_film_fisier,
            "modifica_client_fisier":self.__ui_modifica_clienti_fisier,
            "modifica_inchiriere_fisier":self.__ui_modifica_inchiriere_fisier,
            "sterge_film_fisier":self.__ui_sterge_film_fisier,
            "sterge_client_fisier":self.__ui_sterge_client_fisier,
            "sterge_inchiriere_fisier":self.__ui_sterge_inchiriere_fisier,
            "1":self.__ui_creeaza_clienti_random_fisier,
            "2":self.__ui_creeaza_filme_random_fisier,
            "3":self.__ui_creeaza_inchirieri_random_fisier,
            "top_30_clienti_fisier":self.__ui_top_30_clienti_fisier,
            "top_clienti_fisier":self.__ui_top_clienti_fisier,
            "top_filme_fisier":self.__ui_top_filme_fisier,
            "help":self.__ui_help

        }
    def __ui_print_filme_fisier(self):
        filme=self.__service_filme.get_all_fisier()
        if len(filme)==0:
            print("Nu exista filme")
            return
        print("Filmele sunt: ")
        for film in filme:
            print(film)
    def __ui_print_clienti_fisier(self):
        clienti=self.__service_clienti.get_all_fisier()
        if len(clienti)==0:
            print("Nu exista clienti")
            return
        for client in clienti:
            print(client)
    def __ui_print_inchiriere_fisier(self):
        inchirieri=self.__service_inchiriere.get_all_fisier()
        if len(inchirieri)==0:
            print("Nu exista inchirieri")
            return
        for inchiriere in inchirieri:
            print(inchiriere)
    def __ui_cauta_film_fisier(self):
        id_film=int(input("ID film: "))
        film=self.__service_filme.cauta_film_fisier(id_film)
        print(film)
    def __ui_cauta_client_fisier(self):
        id_client=int(input("ID client: "))
        client=self.__service_clienti.cauta_client_fisier(id_client)
        print(client)
    def __ui_cauta_inchiriere_fisier(self):
        id_inchiriere=int(input("ID inchiriere: "))
        inchiriere=self.__service_inchiriere.cauta_inchiriere_fisier(id_inchiriere)
        print(inchiriere)
    def __ui_adauga_film_fisier(self):
        id_film=int(input("ID film: "))
        titlu_film=input("Titlu film: ")
        descriere_film=input("Descriere film: ")
        gen_film=input("Gen film: ")
        self.__service_filme.adauga_film_fisier(id_film,titlu_film,descriere_film,gen_film)
    def __ui_adauga_clienti_fisier(self):
        id_client=int(input("ID client: "))
        nume_client=input("Nume client: ")
        cnp_client=input("CNP client: ")
        self.__service_clienti.adauga_client_fisier(id_client,nume_client,cnp_client)
    def __ui_adauga_inchiriere_fisier(self):
        id_inchiriere=int(input("ID inchiriere: "))
        film_id=int(input("ID film: "))
        client_id=int(input("ID client: "))
        self.__service_inchiriere.adauga_inchiriere_fisier(id_inchiriere,film_id,client_id)
    def __ui_modifica_film_fisier(self):
        id_film=int(input("ID film: "))
        titlu_film=input("Titlu film: ")
        descriere_film=input("Descriere film: ")
        gen_film=input("Gen film: ")
        self.__service_filme.modifica_film_fisier(id_film,titlu_film,descriere_film,gen_film)
    def __ui_modifica_clienti_fisier(self):
        id_client=int(input("ID client: "))
        nume_client=input("Nume client: ")
        cnp_client=input("CNP client: ")
        self.__service_clienti.modifica_client_fisier(id_client,nume_client,cnp_client)
    def __ui_modifica_inchiriere_fisier(self):
        id_inchiriere=int(input("ID inchiriere: "))
        film_id=int(input("ID film: "))
        client_id=int(input("ID client: "))
        self.__service_inchiriere.modifica_inchiriere_fisier(id_inchiriere,film_id,client_id)
    def __ui_sterge_film_fisier(self):
        id_film=int(input("ID film: "))
        self.__service_filme.sterge_film_fisier(id_film)
        for inchiriereDTO in self.__service_inchiriere.get_all_fisier():
            if id_film==inchiriereDTO.get_film_id():
                self.__service_inchiriere.sterge_inchiriere_fisier(inchiriereDTO.get_id())
    def __ui_sterge_client_fisier(self):
        id_client=int(input("ID client: "))
        self.__service_clienti.sterge_client_fisier(id_client)
        for inchiriereDTO in self.__service_inchiriere.get_all_fisier():
            if id_client==inchiriereDTO.get_client_id():
                self.__service_inchiriere.sterge_inchiriere_fisier(inchiriereDTO.get_id())
    def __ui_sterge_inchiriere_fisier(self):
        id_inchiriere=int(input("ID inchiriere: "))
        self.__service_inchiriere.sterge_inchiriere_fisier(id_inchiriere)
    def __ui_top_30_clienti_fisier(self):
        if(len(self.__service_inchiriere.get_all_fisier())==0):
            print("Nu exista inchirieri")
            return
        for list in self.__service_inchiriere.top_30_clienti_fisier():
            print(f"Clientul {self.__service_clienti.cauta_client_fisier(list[0]).get_nume()} a inchiriat {list[1]} filme")
    def __ui_top_clienti_fisier(self):
        if(len(self.__service_inchiriere.get_all_fisier())==0):
            print("Nu exista inchirieri")
            return
        for list in self.__service_inchiriere.top_clienti_fisier():
            print(f"Clientul {self.__service_clienti.cauta_client_fisier(list[0]).get_nume()} a inchiriat {list[1]} filme")
    def __ui_top_filme_fisier(self):
        if(len(self.__service_inchiriere.get_all_fisier())==0):
            print("Nu exista inchirieri")
            return
        for list in self.__service_inchiriere.top_filme_fisier():
            print(f"Filmul {self.__service_filme.cauta_film_fisier(list[0]).get_title()} a fost inchiriat de {list[1]} ori")
    def __ui_cauta_film(self):
        id_film=int(input("ID film: "))
        film=self.__service_filme.cauta_film(id_film)
        print(film)
    def __ui_cauta_client(self):
        id_client=int(input("ID client: "))
        client=self.__service_clienti.cauta_client(id_client)
        print(client)
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
        if(len(self.__service_filme.get_all())==0):
            print("Nu exista filme")
            return
        id_film=int(input("ID-ul filmului pe care vrei sa il stergi: "))
        self.__service_filme.sterge_film(id_film)
        for inchiriere in self.__service_inchiriere.get_all():
            if id_film==inchiriere.get_film().get_id():
                self.__service_inchiriere.sterge_inchiriere(inchiriere.get_id())
    def __ui_top_filme(self):
        if(len(self.__service_inchiriere.get_all())==0):
            print("Nu exista inchirieri")
            return
        for list in self.__service_inchiriere.top_filme():
            print(f"Filmul {self.__service_filme.cauta_film(list[0]).get_titlu()} a fost inchiriat de {list[1]} ori")
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
        if(len(self.__service_clienti.get_all())==0):
            print("Nu exista clienti")
            pass
        id_client=int(input("ID-ul clientului pe care vrei sa il stergi: "))
        self.__service_clienti.sterge_client(id_client)
        for inchiriere in self.__service_inchiriere.get_all():
            if id_client==inchiriere.get_client().get_id():
                self.__service_inchiriere.sterge_inchiriere(inchiriere.get_id())
    def __ui_top_30_clienti(self):
        if (len(self.__service_inchiriere.get_all()) == 0):
            print("Nu exista inchirieri")
            return
        for list in self.__service_inchiriere.top_30_clienti():
            print(f"Clientul {self.__service_clienti.cauta_client(list[0]).get_nume()} a inchiriat {list[1]} filme")
    def __ui_top_clienti(self):
        if (len(self.__service_inchiriere.get_all()) == 0):
            print("Nu exista inchirieri")
            return
        for list in self.__service_inchiriere.top_clienti():
            print(f"Clientul {self.__service_clienti.cauta_client(list[0]).get_nume()} a inchiriat {list[1]} filme")
    def __ui_sterge_inchiriere(self):
        if(len(self.__service_inchiriere.get_all())==0):
            print("Nu exista inchirieri")
            pass
        id_inchiriere=int(input("ID-ul inchirierii pe care vrei sa o stergi: "))
        self.__service_inchiriere.sterge_inchiriere(id_inchiriere)
    def __ui_cauta_inchiriere(self):
        id_inchiriere=int(input("ID-ul inchirierii pe care vrei sa o cauti: "))
        self.__service_inchiriere.cauta_inchiriere(id_inchiriere)
    def __ui_adauga_inchiriere(self):
        id_inchiriere=int(input("ID-ul dorit: "))
        film_id=int(input("ID-ul film-ului: "))
        client_id=int(input("ID-ul client-ului: "))
        film=self.__service_filme.cauta_film(film_id)
        client=self.__service_clienti.cauta_client(client_id)
        self.__service_inchiriere.adauga_inchiriere(id_inchiriere,film,client)
    def __ui_modifica_inchiriere(self):
        id_inchiriere=int(input("ID-ul inchirierii pe care vrei sa il modifici: "))
        film_id=int(input("ID-ul noului film: "))
        client_id=int(input("ID-ul noului client: "))
        film=self.__service_filme.cauta_film(film_id)
        client=self.__service_clienti.cauta_client(client_id)
        self.__service_inchiriere.modifica_inchiriere(id_inchiriere,film,client)
    def __ui_print_inchiriere(self):
        inchirieri=self.__service_inchiriere.get_all()
        if len(inchirieri)==0:
            print("Nu exista inchirieri")
            return
        for inchiriere in inchirieri:
            print(inchiriere)
    def __ui_creeaza_inchirieri_random(self):
        self.__service_inchiriere.creeaza_inchiriere_random()
    def __ui_creeaza_filme_random(self):
        self.__service_filme.filme_random()
    def __ui_creeaza_clienti_random(self):
        self.__service_clienti.clienti_random()
    def __ui_creeaza_clienti_random_fisier(self):
        self.__service_clienti.clienti_random_fisier()
    def __ui_creeaza_filme_random_fisier(self):
        self.__service_filme.filme_random_fisier()
    def __ui_creeaza_inchirieri_random_fisier(self):
        self.__service_inchiriere.creeaza_inchiriere_random_fisier()

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

