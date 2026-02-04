
### 1NF

**Definiție.** O relație se află în _Prima Formă Normală_ (1NF) dacă fiecare atribut al relației poate avea doar valori atomice (deci listele și mulțimile sunt excluse)

---

### 2NF

Spunem că avem o _dependență funcțională parțială_ într-o relație atunci când un atribut _ne-cheie_ este dependent funcțional de o parte a unei chei candidat a relației (dar nu de întreaga cheie).

**Definiție.** O relație se află în _A Doua Formă Normală_ (2NF) dacă este 1NF și nu are dependențe parțiale.


### 3NF

**Definitie.** O relație R ce satisface dependențele funcționale F se află în _A Treia Formă Normală_ (3NF) dacă se află în 2NF și pentru toate $\alpha \rightarrow A$ din $F^{+}$

- $A \in \alpha$ (DF _trivială_), sau
    
- $\alpha$ conține o cheie de-a lui R, sau
    
- A este un atribut prim.
    

 Dacă R este în BCNF, evident este și în 3NF.

 Dacă R este în 3NF, este posibil ca să apară anumite redundanțe. Este un compromis, utilizat atunci când BCNF nu se poate atinge.

Descompunerea cu joncțiune fără pierderi & cu păstrarea dependențelor_ a relației R într-o mulțime de relații 3NF este întotdeauna posibilă.

---

### Forme Normale bazate pe DF

**1NF** - toate valorile atributelor sunt atomice

**2NF** - toate atributele non-cheie depind de **întreaga** cheie (nu sunt dependențe parțiale)

**3NF** - tabele în 2NF și toate atributele non-prime depind **doar** de cheie (nu sunt depedențe tranzitive)

**BCNF** - Toate dependențele sunt date de chei

### BCNF

**Definiție.** O relație R ce satisface dependențele funcționale F se află în _Forma Normală Boyce-Codd_ (BCNF) dacă se află în 2NF și pentru toate $\alpha \rightarrow A$ din $F^{+}$:

- $A \in \alpha$ (DF _trivială_), sau
    
- $\alpha$ conține o cheie a lui R.
    

R este în BCNF dacă singurele dependențe funcționale satisfăcute de R sunt cele corespunzătoare constrângerilor de cheie.


### Descompunerea în BCNF

Fie relația R cu dependențele funcționale F. Dacă $\alpha \rightarrow A$ nu respectă BCNF, descompunem R în

**R - A și $\alpha A$**.






