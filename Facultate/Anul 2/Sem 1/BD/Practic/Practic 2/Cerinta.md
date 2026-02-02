### **Enunț**

- Să se creeze baza de date a unei aplicații care gestionează notele date de utilizatori restaurantelor pe care le-au frecventat.
    
- Entitățile de interes sunt: **tipuri de restaurante**, **restaurante**, **orașe** și **utilizatori**.
    
- Fiecare tip de restaurant are un nume și o descriere.
    
- Un restaurant are un nume, o adresă, un număr de telefon, aparține unui singur oraș și unui singur tip de restaurant. Un tip de restaurant poate conține mai multe restaurante, dar fiecare restaurant are doar un singur tip.
    
- Fiecare oraș are un nume. Un oraș are mai multe restaurante, dar fiecare restaurant aparține unui singur oraș.
    
- Un utilizator are un nume de utilizator, o adresă de email și o parolă.
    
- Fiecare utilizator poate da note mai multor restaurante, iar fiecare restaurant poate primi mai multe note. Un utilizator poate da o singură notă fiecărui restaurant. Nota este un număr real.
    

### **Cerințe**

1. **Model Relațional (4 puncte):** Scrieți un script SQL care creează un model relațional pentru a reprezenta datele.
    
2. **Procedură Stocată (3 puncte):** Creați o procedură stocată care primește un restaurant, un utilizator și o notă și adaugă nota dată de către utilizator restaurantului. Dacă utilizatorul a dat deja o notă acelui restaurant, valoarea notei va fi actualizată.
    
3. **Funcție (2 puncte):** Creați o funcție care primește ca parametru de intrare adresa de email a unui utilizator și afișează numele tipului de restaurant, numele și numărul de telefon al restaurantului, orașul, nota, numele și adresa de email a utilizatorului pentru toate evaluările utilizatorului care are adresa de email egală cu cea transmisă prin intermediul parametrului de intrare.