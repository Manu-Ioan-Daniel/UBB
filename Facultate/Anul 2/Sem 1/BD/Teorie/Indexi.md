

- **Indecşii** sunt fişiere/structuri de date speciale utilizate pentru accelerarea execuţiei interogărilor.
    
- Un **index** se creează pe baza unei _chei de căutare_
    
    - _Cheia de căutare_ e un atribut/set de atribute folosit(e) pentru a căuta înregistrările dintr-o tabelă/fişier.
        
    - _Cheia de căutare_ este diferită de cheia primară / cheia candidat / supercheia
        
- Un index conţine o colecţie de înregistrări speciale şi permite regăsirea eficientă a _“tuturor înregistrărilor tabelei indexate pentru care cheia de căutare are valoarea $k$”_.
    


# Caracteristicile indecşilor

- _Propagarea modificărilor_
    
    - Adăugarea/ştergerea de înregistrări în tabela indexată implică actualizarea tuturor indecşilor definiţi pentru acea tabelă.
        
    - Orice modificare a valorilor câmpurilor ce aparţin cheii de căutare presupune actualizarea indecşilor bazaţi pe acea cheie de căutare
        

---

# Caracteristicile indecşilor

- _Dimensiunea indecşilor_
    
    - Deoarece utilizarea unui index presupune citirea acestuia în memoria internă $\rightarrow$ dimensiune indexului trebuie să fie redusă.
        
    - Ce se întâmplă dacă indexul e (totuşi) prea mare?
        
        - Utilizare structură de indexare parţială
            
        - Se indexează fişierul index (stratificat sau pe o structură arborescentă)
            

---

# Clasificarea indecşilor

- Indecşi _primari_ vs _secundari_:
    
    - Un index _primar_ se formează pe baza unei chei de căutare ce include cheia primară.
        
    - Un index e _unic_ atunci când cheia de căutare conţine o cheie candidat
        
        - Nu vor fi intrări duplicate
            
    - În general, indecşii _secundari_ conţin duplicate
        

---

# Clasificarea indecşilor

- Indecşi _clustered_ (grupat) vs. _un-clustered_ (negrupat):
    
    - Un index este _clustered_ (grupat) dacă ordinea înregistrărilor este aceeaşi (sau foarte apropiată) cu ordinea intrărilor din index.
        
    - Varianta (1) implică grupare; de asemenea în practică, gruparea implică utilizarea primei variante de structurare a indecşilor.
        
    - O tabelă poate fi indexată grupat (_clustered_) pentru cel mult o cheie de căutare.
        
    - Costul regăsirii datelor prin intermediul unui index e influenţat _decisiv_ de grupare!
        

---

- Pentru a construi indecşi grupaţi:
    
    - Se ordonează înregistrările în cadrul fişierului (_heap file_)
        
    - Se rezervă spaţiu în fiecare pagină de memorie, pentru a se absorbi viitoarele inserări
        
        - dacă spaţiul suplimentar e consumat, se vor forma liste înlănţuite de pagini suplimentare
            
        - E necesară o reorganizare periodică pentru a se asigura o performanţă ridicată
            
- Întreţinerea indecşilor grupaţi e foarte costisitoare
    

---

# Clasificarea indecşilor

- Indecşi _denşi_ vs. _rari_:
    
- Un index este _dens_ dacă are cel puţin o intrare pentru fiecare valoare a cheii de căutare (ce se regăseşte în înregistrări)
    
    - Mai multe intrări pot avea aceeaşi valoare pentru cheia de căutare în cazul utilizării variantei (2) de stocare
        
    - Varianta (1) presupune implicit ca indexul e dens.
        
- Un index e _rar_ dacă memorează o intrare pentru fiecare pagină de înregistrări
    
    - Toţi indecşii rari sunt grupaţi (_clustered_)!
        
    - Indecşii rari sunt mai compacţi.