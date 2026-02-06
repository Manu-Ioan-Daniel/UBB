https://github.com/antonia-04/UBB-Informatica-Computer-Science/blob/main/Anul%202/Semestrul%201/Baze%20de%20date/Examen/2.jpg

https://github.com/antonia-04/UBB-Informatica-Computer-Science/blob/main/Anul%202/Semestrul%201/Baze%20de%20date/Examen/1.jpg

**1. O bază de date aflată în prima formă normală (1NF) este:**

A. O bază de date în care fiecare atribut este unic

B. O bază de date în care fiecare înregistrare e unică

C. O bază de date în care fiecare tabel este unic

D. O bază de date în care fiecare atribut are doar valori atomice

### Raspuns
D.

**2. Care din următoarele opțiuni NU este o variantă de translatare a unei relații de moștenire între două clase A (super-clasă) și B (sub-clasă) în tabelele unui model relațional?**

A. Creează tabela A și se denormalizează atributele lui B

B. Creează tabelele A și B și se adaugă o tabelă de legătură.

C. Creează tabelele A și B, fiecare cu propriile atribute din modelul obiectual

D. Creează tabela B și denormalizează atributele lui A

### Raspuns

B.

**3. Ce implică o constrângere de integritate referențială?**

A. Fiecare rând dintr-o tabelă are o valoare unică.

B. Fiecare atribut dintr-un tabel are o valoare unică.

C. O cheie externă dintr-o tabelă corespunde unei chei primare dintr-o altă tabelă.

D. Fiecare tabel dintr-o bază de date are o valoare unică.

### Raspuns

C.

**4. Folosim operatorul algebric relațional $\bowtie$ (join natural) pentru:**

A. Proiectarea unui anumit set de atribute dintr-o relație

B. Filtrarea tuplurilor dintr-o relație

C. Combinarea a două relații pe baza atributelor cu același nume

D. Determinarea produsului cartezian a două relații

### Raspuns

C.

**5. Care dintre următoarele afirmații NU reprezintă o proprietate a unui arbore B de grad m?**

A. Fiecare nod conține cel mult m-1 valori ale cheii de căutare

B. Fiecare nod are cel puțin [m/2] valori ale cheii de căutare (excepție făcând rădăcina)

C. Fiecare nod are cel puțin m subarbori

D. Toate frunzele sunt poziționate la același nivel.

### Raspuns

C.

**6. În relația $R(A, B, C, D, E)$ cu dependențele funcționale $AB \rightarrow C$, $C \rightarrow B$, și $D \rightarrow E$, numărul supercheilor este:**

A. 2

B. 4

C. 6

D. 8

### Raspuns

ABD este cheie si supercheie,ACD este cheie si supercheie,ABDC,ABDE,ABDEC,ACDE

C.

**7. Ce este un cilindru al unui hard disk?**

A. Mulțimea tuturor discurilor hard-diskului dispuse unul deasupra celuilalt

B. Mulțimea tuturor sectoarelor unei piste

C. Mulțimea tuturor pistelor, de pe toate discurile, care se află la aceeași distanță de centrul discului

D. Mulțimea tuturor blocurilor de memorie dispuse secvențial pe o pistă.

### Raspuns

C.

**8. Care e scopul principal al normalizării?**

A. Îmbunătățirea performanței accesului

B. Creșterea nivelului de securitate a bazei de date

C. Eliminarea redundanței datelor și păstrarea integrității datelor

D. Simplificarea interogării

### Raspuns

C.

**9. Fie $\alpha$, $\beta$ și $\gamma$ trei mulțimi de atribute ale unei relații. Conform regulilor de inferență ce caracterizează dependențele funcționale, dacă $\alpha \rightarrow \beta$ și $\alpha \rightarrow \gamma$, atunci:**

A. $\beta\gamma \rightarrow \alpha$

B. $\beta \rightarrow \gamma$

C. $\alpha \rightarrow \beta\gamma$

D. $\gamma \rightarrow \beta$

### Raspuns

C.

**10. Cum îmbunătățește Buffer Manager-ul performanța unui sistem de baze de date?**

A. Prin creșterea cantității de date care pot fi stocate în baza de date

B. Prin reducerea numărului de accesări ale discului necesare pentru a prelua date

C. Prin reducerea cantității de memorie necesară pentru stocarea datelor în baza de date

D. Prin reducerea timpului necesar pentru procesarea datelor din baza de date de către procesorul central (CPU)

### Raspuns

B.

**11. Care este diferența dintre cheile primare și cele externe?**

A. Atât cheile primare, cât și cheile externe sunt utilizate pentru a crea legături între tabele.

B. Atât cheile primare, cât și cheile externe sunt utilizate pentru a identifica în mod unic înregistrările din cadrul tabelelor.

C. O cheie primară este utilizată pentru a identifica în mod unic o înregistrare în cadrul unui tabel, în timp ce o cheie externă este utilizată pentru a crea o legătură între două tabele.

D. O cheie primară este utilizată pentru a crea o legătură între două tabele, în timp ce o cheie externă este utilizată pentru a identifica în mod unic o înregistrare în cadrul unui tabel.

### Raspuns

C.


**12. Care din următoarele reprezintă o politică de înlocuire a paginilor de memorie utilizată de un Buffer Manager?**

A. First in first out (FIFO)

B. Random replacement policy (RRP)

C. Redundant array of independent disks (RAID)

D. Most recently used (MRU)

### Raspuns

A,B,D.

**13. Care dintre următoarele interogări, precedate de $\rho(I_1, Înmatriculări), \rho(I_2, Înmatriculări)$, afișează persoanele care dețin cel puțin două mașini?**

A. $\pi_{I_1.Proprietar} (\sigma_{I_1.Proprietar = I_2.Proprietar \land I_1.NrMasina \neq I_2.NrMasina} (I_1 \times I_2))$

B. $\pi_{I_1.Proprietar} (\sigma_{I_1.Proprietar \neq I_2.Proprietar \land I_1.NrMasina = I_2.NrMasina} (I_1 \times I_2))$

C. $\pi_{I_1.Proprietar} (\sigma_{I_1.Proprietar = I_2.Proprietar \land I_1.NrMasina = I_2.NrMasina} (I_1 \times I_2))$

D. $\displaystyle \pi_{I_1.Proprietar} (\sigma_{I_1.Proprietar \neq I_2.Proprietar \land I_1.NrMasina \neq I_2.NrMasina} (I_1 \times I_2))$

### Raspuns

A.

**14. Care este diferența dintre COUNT(DISTINCT b) și COUNT(b) în SQL?**

A. Nu există nicio diferență.

B. COUNT(DISTINCT b) numără doar valorile unice ale lui b, în timp ce COUNT(b) numără toate valorile lui b.

C. COUNT(b) numără doar valorile unice nenule ale lui b, în timp ce COUNT(DISTINCT b) numără toate valorile lui b.

D. Sunt utilizate în scopuri diferite și nu pot fi comparate.
### Raspuns

B.

**15. Ce este o coliziune în indexarea cu organizare directă?**

A. Două înregistrări au aceeași valoare a funcției de dispersie.

B. Două înregistrări au aceeași cheie primară.

C. Două înregistrări au aceeași valoare într-un anumit câmp.

D. Două înregistrări au aceeași valoare în toate câmpurile.

### Raspuns

A.

**16. În cele ce urmează, puteți presupune că relația R(a, b, c) conține valori NULL și poate avea duplicate.**

**Q1: SELECT DISTINCT a, b FROM R;**

**Q2: SELECT a, b FROM R GROUP BY a, b;**

A. Q1 și Q2 produc același răspuns.

B. Q1 este întotdeauna conținut în răspunsul lui Q2.

C. Q2 este întotdeauna conținut în răspunsul lui Q1.

D. Q1 și Q2 produc răspunsuri diferite.

### Raspuns
A.

**17. Pentru următoarele interogări se poate considera o structură arbitrară a lui R:**

**Q1: (SELECT * FROM R) UNION (SELECT * FROM R);**

**Q2: SELECT * FROM R;**

A. Q1 și Q2 produc același răspuns.

B. Q1 este întotdeauna conținut în răspunsul lui Q2.

C. Q2 este întotdeauna conținut în răspunsul lui Q1.

D. Q1 și Q2 produc răspunsuri diferite.
### Raspuns

B.

**18. În următoarele expresii scrise în algebra relațională, R și S au aceeași structură arbitrară dar care conține atributul _a_:**

**Q1: $\pi_a(R) - \pi_a(S)$**

**Q2: $\pi_a(R - S)$**

A. Q1 și Q2 produc același răspuns.

B. Q1 este întotdeauna conținut în răspunsul lui Q2.

C. Q2 este întotdeauna conținut în răspunsul lui Q1.

D. Q1 și Q2 produc răspunsuri diferite.

### Raspuns

D.

**19. În cele ce urmează, puteți presupune că relația R(a, b, c) nu conține valori NULL și poate avea duplicate.**

**Q1: SELECT a, SUM(b) FROM R GROUP BY a;**

**Q2: SELECT a, SUM(b) FROM R GROUP BY a HAVING COUNT(*) > 1;**

A. Q1 și Q2 produc același răspuns.

B. Q1 este întotdeauna conținut în răspunsul lui Q2.

C. Q2 este întotdeauna conținut în răspunsul lui Q1.

D. Q1 și Q2 produc răspunsuri diferite.

### Raspuns

C.

**20. Ce se poate spune despre următoarele interogări?**

**Q1: SELECT R.a FROM R, S WHERE R.b = S.b;**

**Q2: SELECT R.a FROM R WHERE R.b IN (SELECT S.b FROM S);**

A. Q1 și Q2 produc același răspuns.

B. Q1 este întotdeauna conținut în răspunsul lui Q2.

C. Q2 este întotdeauna conținut în răspunsul lui Q1.

D. Q1 și Q2 produc răspunsuri diferite.

### Raspuns

C.

### **Subiectul B: Arbori B**

Se dă următorul arbore B de ordin 5. Determinați structura arborelui după adăugarea valorilor 550 și 375 în această ordine.

![[Pasted image 20260203235029.png]]

#### Raspuns

![[Pasted image 20260204003229.png]]
### **Subiectul C: Interogări SQL și Cardinalitate**

Se dă instanța unei relații cu schema **P(CodP, A, B, C, D, E)**, CodP fiind cheie primară.

| **CodP** | **A** | **B** | **C** | **D** | **E** |
| -------- | ----- | ----- | ----- | ----- | ----- |
| 1        | 78    | 1     | c1    | d1    | 7     |
| 2        | 35    | 4     | c1    | d2    | 40    |
| 3        | 300   | 3     | c4    | d2    | 7     |
| 4        | 560   | 8     | c2    | d1    | 4     |
| 5        | 50    | 4     | c3    | d3    | 5     |

Cât este diferența între cardinalitatea rezultatului primei interogări și cardinalitatea rezultatului celei de-a doua interogări?

**Interogarea 1:**

SQL

```
SELECT D, C, SUM(B)
FROM P
GROUP BY D, C
HAVING SUM(B) > 4
```

#### aici cardinalul e 1 mai sus

**Interogarea 2:**

SQL

```
SELECT E, D, SUM(A)
FROM P
GROUP BY E, D
HAVING SUM(A) BETWEEN 200 AND 350
```

#### aici cardinalul e 1 mai sus

#### Raspuns

Diferenta este 0

### **Subiectul D: Dependențe Funcționale și Forme Normale**

Se dă relația **R(A, B, C, D)** cu următoarele dependențe funcționale: $F = \{B \rightarrow AD, C \rightarrow D, A \rightarrow C\}$. R nu are atribute repetitive.

**a.** Determinați cheile candidat ale lui R.
#### Raspuns

B este o cheie candidat si atat

**b.** Justificați pentru fiecare din formele normale 2NF, 3NF și BCNF dacă relația este sau nu în acea formă normală.

#### Raspuns

2NF - nu avem dependendente partiale,deoarece B este o cheie formata dintr un singur atribut
3NF - C->D deci nu este in 3NF,pentru ca avem dependente tranzitive(C->D,A->C)
BCNF- la fel ca 3NF(orice ce nu este in 3NF nu este in BCNF)

**c.** Dacă este cazul, determinați o descompunere a lui R care să fie în BCNF.
#### Raspuns

Alegem C->D deci (A,B,C,D) se descompune in (A,B,C),(C,D) dupa care alegem A->C si ajungem la (A,B),(A,C),(C,D) 

facem in 3NF practice

F = {B->A,B->D,C->D,A->C} 

avem un singur atribut in partea stanga=>nu avem atribute redundante,eliminam dependente functionale reduntante


B->D este dependenta functionala redundanta pentru ca B+ pentru F - {B->D} este {A,C,D} si D apartine acestei multimi deci acoperirea minimala pentru F este {B->A,C->D,A->C}
Relatiile pentru fiecare dependenta functionala sunt (B,A),(C,D),(A,C) si relatia pentru cheie este (B) $\subseteq$  (B,A) deci relatia pentru cheie este redundanta.




