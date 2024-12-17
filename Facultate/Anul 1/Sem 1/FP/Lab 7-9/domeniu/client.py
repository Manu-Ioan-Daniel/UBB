class Client:

    def __init__(self,id_client,nume,cnp):
        """
        Initializeaza un client cu un id,nume si cnp
        :param id_client:numar mai mare ca 0
        :param nume:string nevid
        :param cnp:string nevid de numere
        """
        self.__id_client=id_client
        self.__nume=nume
        self.__cnp=cnp
    def get_id(self):
        return self.__id_client
    def get_nume(self):
        return self.__nume
    def get_cnp(self):
        return self.__cnp
    def set_id_client(self,id_client):
        self.__id_client=id_client
    def set_nume(self,nume):
        self.__nume=nume
    def set_cnp(self,cnp):
        self.__cnp=cnp
    def __eq__(self,other):
        if isinstance(other,Client):
            return self.__id_client == other.get_id() and self.__nume == other.get_nume() and self.__cnp == other.get_cnp()
        return False
    def __str__(self):
        return f"{self.__id_client} - {self.__nume} - {self.__cnp}\n"


