### Enunt

**C. Se dă instanța unei relații cu schema R($\underline{CodR}$, A, B, C, D, E), CodR fiind cheie primară.**

| **CodR** | **A** | **B** | **C** | **D** | **E** |
| -------- | ----- | ----- | ----- | ----- | ----- |
| 1        | 7     | b1    | 12    | 50    | e1    |
| 2        | 7     | b1    | 15    | 80    | e2    |
| 3        | 6     | b2    | 18    | 30    | e2    |
| 4        | 9     | b1    | 29    | 100   | e3    |
| 5        | 10    | b2    | 34    | 150   | e1    |

**Cât este diferența între cardinalitatea rezultatului primei interogări și cardinalitatea rezultatului celei de a doua interogări?**

|**Interogarea 1**|**Interogarea 2**|
|---|---|
|`SELECT A, B, COUNT(D)`|`SELECT B, E, SUM(C)`|
|`FROM R`|`FROM R`|
|`GROUP BY A, B`|`GROUP BY B, E`|
|`HAVING COUNT(D)<>1`|`HAVING SUM(C) NOT IN (10,30,15,40)`|
#### Raspuns
diferenta este 1 - 4 = -3

### Enunt

**D. Se dă relația R(M, N, P, Q) cu următoarele dependențe funcționale: F = {P → Q, M → N, Q → M}. R nu are atribute repetitive.**

**a.** Determinați cheile candidat ale lui R.

**b.** Justificați pentru fiecare din formele normale 2NF, 3NF și BCNF dacă relația este sau nu în acea formă normală.

**c.** Dacă este cazul, determinați o descompunere a lui R care să fie în BCNF.

#### Raspuns

a. P si atat,pentru ca P este singurul atribut din care putem deduce toate celelalte atribute
b. Nu avem dependendente partiale pentru ca cheia noastra este formata dintr un singur atribut,deci este 2NF,avem dependendente tranzitive (M->N,Q->M) deci nu este in 3NF si nici in BCNF

c.Alegem M->N si (M,N,P,Q) se descompune in (M,P,Q) si (M,N) apoi alegem Q->M si ajungem la
(P,Q) ,(M,N), (Q,M) care este ok pentru ca se pastreaza toate dependentele functionale

>[!REGELEGPT]
Această descompunere este foarte bună deoarece, așa cum ai observat, **păstrează toate dependențele funcționale** originale, ceea ce nu este întotdeauna garantat la descompunerea în BCNF!
