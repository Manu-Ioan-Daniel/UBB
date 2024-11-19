class Inchiriere:
    def __init__(self,id_inchiriere,film,client):
        self.__film=film
        self.__client=client
        self.__id_inchiriere=id_inchiriere
    def get_id(self):
        return self.__id_inchiriere
    def get_film(self):
        return self.__film
    def get_client(self):
        return self.__client
    def set_film(self,film):
        self.__film=film
    def set_client(self,client):
        self.__client=client
    def __str__(self):
        return f"{self.__id_inchiriere} - {self.__film.get_title()} - {self.__client.get_nume()}"
    def __eq__(self,other):
        if isinstance(other,Inchiriere):
            return self.__id_inchiriere == other.get_id()
        return False
    def __hash__(self):
        return hash((self.__id_inchiriere,self.__film,self.__client))

