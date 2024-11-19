class Film:
    def __init__(self,id_film,titlu,descriere,gen):
        self.__id_film = id_film
        self.__titlu = titlu
        self.__descriere = descriere
        self.__gen = gen
    def get_id(self):
        return self.__id_film
    def get_title(self):
        return self.__titlu
    def get_description(self):
        return self.__descriere
    def get_gen(self):
        return self.__gen
    def set_id(self,id_film):
        self.__id_film = id_film
    def set_title(self,titlu):
        self.__titlu = titlu
    def set_description(self,descriere):
        self.__descriere = descriere
    def set_gen(self,gen):
        self.__gen = gen
    def __eq__(self,other):
        if isinstance(other,Film):
            return self.__id_film == other.get_id() and self.__gen == other.get_gen() and self.get_title() == other.get_title() and self.get_description() == other.get_description()
        return False
    def __hash__(self):
        return hash((self.__id_film, self.__gen,self.__titlu,self.__descriere))
    def __str__(self):
        return (f"{self.__id_film} - {self.__titlu} - {self.__descriere} - {self.__gen}\n")
