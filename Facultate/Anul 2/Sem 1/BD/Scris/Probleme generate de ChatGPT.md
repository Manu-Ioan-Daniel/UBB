
## Problema 1

Fie relația R(A;B;C;D;E) și mulțimea de dependențe funcționale:

F={A→BC;  B→D;  C→E;  D→A}

1. Determinați toate cheile candidate ale relației R.
    
2. Găsiți o acoperire minimală pentru F.
    
3. Verificați dacă R este în BCNF. Justificați.
    
4. Dacă nu este, determinați o descompunere în BCNF fără pierderi.
    
5. Analizați dacă descompunerea păstrează dependențele.
    
6. Verificați dacă R este în 3NF. Justificați.
    
7. Realizați o descompunere în 3NF folosind algoritmul standard.

### Raspuns

1. A+ = {B,C,D,E,A} toate atributule apartin deci A este cheie candidat,B la fel,D la fel
2. F = {A->B,A->C,B->D,C->E,D->A} este deja o acoperire minimala,nu avem atribute redundante si nici dependente functionale redundante
3. in C->E C nu este supercheie =>nu este in BCNF(este o dependenta tranzitiva)
4. Alegem C->E si relatia (A,B,C,D,E) se descompune in (A,B,C,D),(C,E) care este in BCNF
5. da,descompunerea pastreaza toate dependentele
6. nu este in 3NF,C->E este tranzitiva,C nu este supercheie si E nu este nici atribut prim
7. F = {A->BC,B->D,C->E,D->A} deci (A,B,C,D,E) se descompune in (A,B,C),(B,D),(C,E),(D,A) si cheile candidat sunt continute in aceste multimi,deci nu le mai scriem

## Problema 2

Fie relația R(A;B;C;D) și mulțimea de dependențe funcționale:

F={AB→C;C→D;D→B}

1. Determinați toate cheile candidate ale relației R.
    
2. Găsiți o acoperire minimală pentru F.
    
3. Verificați dacă R este în BCNF.
    
4. Determinați o descompunere în BCNF fără pierderi.
    
5. Spuneți dacă descompunerea păstrează dependențele.
    
6. Verificați dacă R este în 3NF.
    
7. Determinați o descompunere în 3NF folosind algoritmul standard.

### Raspuns

1. AB,AC,AD
2. A+ = {A},deci B nu este atribut redundant in AB->C =>nu avem atribute redundante,nu avem nici dependente functionale redundante,deci este deja o acoperire minimala
3. C->D,C nu este supercheie,deci nu este in BCNF
4. Alegem C->D si (A,B,C,D) se descompune in (A,B,C) (C,D) care este in BCNF
5. nu s au pastrat dependentele,D->B nu a fost pastrata
6. AB->C AB este supercheie,deci e ok,C->D D este atribut prim,face parte in cheia candidat AD,D->B B este atribut prim,face parte din AB,deci este in 3NF
7. (A,B,C),(C,D),(D,B),(A,D)

## Problema 3

Fie relația R(A;B;C;D;E) și mulțimea de dependențe funcționale:

F={AB→C;A→C;AC→D;B→E;DE→F;D→F}

1. Determinați toate cheile candidate ale lui R.
    
2. Reduceți FD-urile la o acoperire minimală.
    
3. Verificați dacă R este în BCNF.
    
4. Dacă nu este, realizați o descompunere BCNF fără pierderi.
    
5. Analizați păstrarea dependențelor.
    
6. Verificați dacă R este în 3NF.
    
7. Determinați o descompunere 3NF completă.

### Raspuns

1. AB este singura cheie candidat
2. C este atribut redundant in AC->D deoarece A+ = {A,C,D,F} D apartine A+ deci F echivalent cu {AB->C,A->C,A->D,B->E,DE->F,D->F}
 B este atribut redundant in AB->C deoarece A+ = {A,C,D,F} deci F echivalent cu {A->C,A->D,B->E,DE->F,D->F} si E este atribut redundant in DE->F deci F echivalent cu {A->C,A->D,B->E,D->F}
 care este o acoperire minimala
 3. in B->E B nu este supercheie deci nu este in BCNF
 4. B->E nu respecta BCNF deci R se descompune in (A,B,C,D,F),(B,E)
 D->F nu respecta BCNF(D nu este supercheie) deci R se descompune in (A,B,C,D),(B,E),(D,F)
 AC->D nu respecta BCNF(AD nu este supercheie) deci R se descompune in (A,B,C),(B,E),(D,F),(A,C,D) care este in BCNF
 5. nu se pastreaza toate dependentele
 6. nu este in 3NF deoarece in B->E B nu este supercheie si E nu este atribut prim
 7. folosim acoperirea minimala G = {A->C,A->D,B->E,D->F} si ajungem la descompunerea (A,C,D),(B,E),(D,F),(A,B) (A,B) fiind generata de cheie