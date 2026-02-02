Creați o bază de date pentru gestiunea mersului trenurilor. Baza de date va conține informații despre rutele tuturor trenurilor.

- **Entitățile de interes** pentru domeniul problemei sunt: **trenuri**, **tipuri de tren**, **stații** și **rute**.
    
- **Trenuri:** Fiecare tren are un nume și aparține unui tip.
    
- **Tipuri de tren:** Tipul trenului are o descriere.
    
- **Rute:** Fiecare rută are un nume, un tren asociat și o listă de stații.
    
- **Stații:** Stația are un nume.
    
- **Program:** Pentru fiecare stație de pe o rută se rețin **ora sosirii** și **ora plecării**. Orele sunt reprezentate ca perechi oră/minut (exemplu: trenul ajunge la 5 PM și pleacă la 5:10 PM).
    

---

### **Cerințe**

1. **Modelul Relațional (4 puncte):** Scrieți un script SQL care creează un model relațional pentru a reprezenta datele descrise mai sus.
    
2. **Procedură Stocată (3 puncte):** Creați o procedură stocată care primește o rută, o stație, ora sosirii, ora plecării și adaugă noua stație rutei. Dacă stația există deja în cadrul acelei rute, se actualizează ora sosirii și ora plecării.
    
3. **View (2 puncte):** Creați un view care afișează numele rutelor care conțin toate stațiile definite în baza de date.
    
4. **Oficiu:** 1 punct.