class InchiriereDTO:
    def __init__(self,id_inchiriere,id_film,id_client):
        self.__id_inchiriere = id_inchiriere
        self.__id_film = id_film
        self.__id_client = id_client
    def get_inchiriere_id(self):
        return self.__id_inchiriere
    def get_film_id(self):
        return self.__id_film
    def get_client_id(self):
        return self.__id_client
