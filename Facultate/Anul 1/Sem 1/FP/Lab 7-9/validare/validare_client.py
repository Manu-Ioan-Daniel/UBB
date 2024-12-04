from exceptii.erori import ValidationError
class ValidatorClient:
    def valideaza_client(self,client):
        erori=""
        if(int(client.get_id())<0):
            erori+="Id invalid\n"
        if(client.get_nume()==""):
            erori+="Name invalid\n"
        if(client.get_cnp()==""):
            erori+="Cnp invalid\n"
        if len(erori)>0:
            raise ValidationError(erori)