
## Proprietăți Arbori-B

- **Arbore-B de ordin $m$:**
    
    - Dacă nu e frunză, rădăcina va avea mereu cel puțin 2 subarbori
        
    - Fiecare nod intern are cel puțin $\lceil m/2 \rceil$ sub-arbori (mai puțin rădăcina)
        
    - Fiecare nod intern are cel mult $m$ sub-arbori
        
    - Toate frunzele sunt la același nivel
        
    - Un nod cu $p$ sub-arbori conține $p-1$ chei ordonate ($K_1, K_2, \dots K_{p-1}$)
        
        - $T_1$ conține valori $< K_1$
            
        - $T_i$ conține valori între $K_{i-1}$ și $K_i$
            
        - $T_p$ conține valori $> K_{p-1}$
            

---

## Organizarea în memorie a Arborilor-B

- **Alocare memorie pentru a stoca m-1 chei per nod**
    
    - N – număr de chei stocate
        
    - $K_i$ – valoarea cheii, $AD_i$ – adresa înregistrării
        
    - $Point_i$ – referă un sub-arbore
        
- **Utilizarea spațiului de memoriei pentru _pointeri_ la stocarea cheilor în frunze**
    
    - $m/2 \le N \le m-1$ – pentru noduri interne
        
    - $m/2 \le N \le t$ – pentru noduri terminale
        
    - flag adițional/ semn pentru N
        

---

## Algoritmul procedurii de inserare

1. **Localizare nod pentru inserare**
    
2. **Inserare cheie**
    
3. **Dacă nodul e plin (dimensiunea e depășită):**
    
    A) Se crează un nou nod în care se mută cheile mai mari decât valoarea cheii mediane
    
    B) Se inserează cheia mediană în nodul părinte
    
    C) _Pointerul_ din dreapta cheii va referi noul nod, iar cel din stanga referă vechiul nod ce conține valorile mai mici
    
4. **Daca și nodul părinte e plin:**
    
    A) Dacă nodul părinte e radacină atunci se crează o radacină nouă
    
    B) Se repetă pasul 3 pentru nodul părinte
    

---

## Ștergerea înregistrărilor în Arbore-B

- **Pași de ștergere:**
    
    - se indentifică nodul ce conține valoarea ce trebuie ștearsă
        
    - dacă e un nod intern, _se transferă o valoare din frunze_
        
    - în caz de _subdimensionare_, se realizează o _redistribuire_ sau o _concatenare_
        

---

## Arborii B+ în practică

- **Ordin tipic: 200. Factor de acoperire: 67%.**
    
    - În medie, (nr de valori)/(nr pagini de index) = 133
        
- **Capacități tipice:**
    
    - Înălțime 4: $133^4 = 312,900,700$ înregistrări
        
    - Înălțime 3: $133^3 = 2,352,637$ înregistrări
        
- **În general poate fi memorat în _buffer_-ul din memoria internă:**
    
    - Nivel 1 = 1 pagină = 8 Kbytes
        
    - Nivel 2 = 133 pagini = 1 Mbyte
        
    - Nivel 3 = 17,689 pagini = 133 MBytes
        

---

## Avantaje & Dezavantaje ale Arborilor-B+

- Indecșii rămân echilibrați $\rightarrow$ timp de căutare uniform
    
- Rareori sunt mai mult de 3 - 5 nivele $\rightarrow$ primele nivele sunt păstrate în RAM astfel încât o căutare va necesita doar 2 sau 3 I/O (citiri/scrieri).
    
- În general nodurile au o ocupare de 67% (deci se folosește cu 50% mai mult spațiu decât este necesar)
    
- Datorită versatilității sale este cel mai utilizat mod de a structura indecșii în SGBD-uri relaționale. E una dintre cele mai optimizate componente ale unui SGBD.
    
- Arborii B+ pot fi utilizați pentru indecși _clustered_, rari (dacă tabela e sortată) sau pentru indecși _un-clustered_, denși (în caz contrar).