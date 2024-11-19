from exceptii.erori import ValidationError
class ValidatorInchiriere:
    def valideaza_inchiriere(self, inchiriere):
        erori=""
        if inchiriere.get_id()<0:
            erori+="Id invalid"
        if len(erori)>0:
            raise ValidationError(erori)

