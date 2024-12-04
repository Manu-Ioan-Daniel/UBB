class InchiriereDTO:
    def __init__(self,id_inchiriere,id_film,id_client):
        self.__id_inchiriere = id_inchiriere
        self.__id_film = int(id_film)
        self.__id_client = int(id_client)
    def get_id(self):
        return self.__id_inchiriere
    def get_film_id(self):
        return self.__id_film
    def get_client_id(self):
        return self.__id_client
    def __eq__(self,other):
        if isinstance(other,InchiriereDTO):
            return self.__id_inchiriere==other.get_id()
        return False
    def __str__(self):
        return f"{self.__id_inchiriere},{self.__id_film},{self.__id_client}"