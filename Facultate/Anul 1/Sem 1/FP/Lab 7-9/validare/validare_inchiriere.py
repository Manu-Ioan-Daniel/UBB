from exceptii.erori import ValidationError
class validator_inchiriere:
    def valideaza_inchiriere(self, inchiriere):
        erori=""
        if inchiriere.get_id_inchiriere()<0:
            erori+="Id invalid"
        if len(erori)>0:
            raise ValidationError(erori)

