from exceptii.erori import ValidationError
class ValidatorFilm:
    def valideaza_film(self,film):
        erori=""
        if(film.get_id()<0):
            erori+="Id invalid\n"
        if(film.get_gen()==""):
            erori+="Gen invalid\n"
        if(film.get_title()==""):
            erori+="Titlu invalid\n"
        if(film.get_description()==""):
            erori+="Descriptie invalid\n"
        if len(erori)>0:
            raise ValidationError(erori)


