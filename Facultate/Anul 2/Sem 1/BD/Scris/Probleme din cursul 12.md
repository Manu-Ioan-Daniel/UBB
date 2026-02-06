
## Problema 1

Fie următoarea instanță a relației _Studenți_:

|**sid**|**nume**|**email**|**varsta**|**nota**|
|---|---|---|---|---|
|2833|Jones|jones@scs.ubbcluj.ro|19|9|
|2877|Smith|smith@scs.ubbcluj.ro|20|8|
|2976|Jones|jones@math.ubbcluj.ro|21|10|
|2765|Mary|mary@math.ubbcluj.ro|22|7.7|
|3000|Dave|dave@cs.ubbcluj.ro|18|5.5|
|3010|Smith|smith2@scs.ubbcluj.ro|20|7|
|3020|Sam|sam@scs.ubbcluj.ro|19|9.5|

---

### Cerințe:

1. **Dați exemplu de un atribut (sau mulțime de atribute) ce nu poate fi cheie candidat, considerând că instanța de mai sus este legală.**
    
2. **Putem deduce dacă un atribut (sau mulțime de atribute) este cheie candidat, considerând că instanța de mai sus este legală?**


### Raspunsuri

1. numele nu poate fi cheie candidat deoarece sunt doua inregistrari cu acelasi nume,deci nu putem deduce totul despre acel student de la un singur nume,varsta din nou din acelasi motiv,
2. nu putem deduce chei candidat doar dintr o instanta a unei relatii,putem deduce doar din toate instantele posibile legale ale acelei relatii

## Problema 2

|**Tabel**|**Atribute**|**Descriere**|
|---|---|---|
|**Actors**|`(ID, Name, YoB)`|`ID` este identificator unic; `Name` și `YoB` (anul nașterii) sunt atributele actorului.|
|**Movies**|`(ID, Title, Year)`|`ID` este identificator unic; `Title` și `Year` reprezintă titlul și anul producției.|
|**Casting**|`(MovieID, ActorID, Charac)`|`MovieID` și `ActorID` referă înregistrări din _Movies_ și _Actors_. Actorul joacă personajul `Charac`.|

### Cerinte

1. Numele actorilor ce au jucat personajul "Fletcher Christian" într-o producție a filmului "Mutiny on the Bounty", alături de anul productiei filmului;

```sql
SELECT A.Name,M.Year
FROM Actors A
JOIN Casting C ON C.ActorID = A.ID
JOIN Movies M ON C.MovieID = M.ID
WHERE C.Charac = 'Fletcher Christian' AND M.Title = 'Mutiny on the Bounty'
```

2. Găsiți numele actorilor care au jucat personajele “Superman” și “Clark Kent” in aceeași producție a unui film;

```sql
SELECT DISTINCT A.Name
FROM Actors A
JOIN Casting C ON C.ActorID = A.ID
JOIN Casting C1 ON C1.ActorID = A.ID
WHERE C.MovieID = C1.MovieID AND C.Charac = 'Superman' AND C1.Charac = 'Clark Kent'
```

3. Numele actorilor care au jucat două personaje diferite în acelasi film;

```sql
SELECT DISTINCT A.Name
FROM Actors A
JOIN Casting C ON C.ActorID = A.ID
JOIN Casting C1 ON C1.ActorID = A.ID
WHERE C.MovieID = C1.MovieID AND C.Charac <> C1.Charac
```

4. Perechi de nume a doi actori diferiți care au jucat același caracter în producții diferite ale aceluiași film

```sql
SELECT A1.Nume,A2.Nume
FROM Actors A1,Actors A2
JOIN Casting C1 ON C1.ActorID = A1.ID
JOIN Casting C2 ON C2.ActorID = A2.ID
JOIN Movies M1 ON M1.ID = C1.MovieID
JOIN Movies M2 ON M2.ID = C2.MovieID
WHERE C1.Charac = C2.Charac AND M1.Year <> M2.Year AND M1.Title = M2.Title AND A1.ID <> A2.ID
```

## Problema 3

Fie următoarea structură:

- **Suppliers** (`sid`, `sname`, `address`)
    
- **Products** (`pid`, `pname`, `color`)
    
- **Catalog** (`sid`, `pid`, `cost`)
    

_Câmpurile cheie sunt subliniate. Relația Catalog conține prețurile practicate de un furnizor pentru un produs particular._
### Cerințe:

1. Explicați ce returnează următoarea interogare exprimată în algebră relațională:

$$\pi_{sname}(\pi_{sid}((\sigma_{color='red'} Products) \bowtie (\sigma_{cost<100} Catalog)) \bowtie Suppliers)$$

Toate numele supplierilor care vand produse cu culoare rosie si pret <100

2. Explicați ce returnează următoarea interogare exprimată în algebră relațională:$$\pi_{sname}(\pi_{sid}((\sigma_{color='red'} \text{ Products}) \bowtie (\sigma_{cost<100} \text{ Catalog}) \bowtie \text{ Suppliers}))$$
Lipseste o paranteza a drq experienta aia in lisp o am degeaba,nu returneaza nimic ca nu exista field ul sname 

3. Explicați ce returnează următoarea interogare exprimată în algebră relațională:
$$\pi_{sname}((\sigma_{color='red'} \text{ Products}) \otimes (\sigma_{cost<100} \text{ Catalog}) \otimes \text{ Suppliers}) \cap$$
$$\pi_{sname}((\sigma_{color='green'} \text{ Products}) \otimes (\sigma_{cost<100} \text{ Catalog}) \otimes \text{ Suppliers})$$

primul alege numele tuturor producatorilor care au trimis



## Problema 6

Fie relația $R(A; B; C; D; E)$ cu mulțimea de DF:

$F = \{AB \to CDE; AC \to BDE; B \to C; C \to B; C \to D; B \to E\}$.

1. Găsiți toate cheile lui $R$.
    
2. Determinați o acoperire minimală pentru $F$.
    
3. Este $R$ în BCNF? Explicați.
    
4. Determinați o descompunere BCNF a lui $R$, cu joncțiuni fără pierderi.
    
5. Soluția găsită la 4 păstrează dependențele? Explicați.
    
6. Este $R$ în 3NF? Explicați.
    
7. Determinați o descompunere 3NF a lui $R$ folosind algoritmul descris în curs.

### Raspuns

1. AB,AC
2. F = {AB->C,AB->D,AB->E,AC->B,AC->D,AC->E,B->C,C->B,C->D,B->E}

B+ = {C,D,E,B} deci cum C,D,E apartin B+ rezulta ca A este atribut reduntant pentru AB->C,AB->D,AB->E deci ajungem la {B->C,B->D,B->E,AC->B,AC->D,AC->E,C->B,C->D}

C+ = {D,E,B} deci cum D,E,B apartin C+ rezulta ca A este atribut reduntant  pentru AC->B,AC->D,AC->E => ajungem la {B->C,B->D,B->E,C->B,C->D,C->E}

Calculam B+ pe baza F-{B->D} si ajungem la B+ = {C,D,E,B} cum D apartine => B->D redundanta
F_2 = {B->C,B->E,C->B,C->D,C->E}

Calculam B+ pe baza  F_2 - {B->E}  si ajungem la B+ = {B,C,D,E} si cum E apartine =>B->E este redundant ajungem la F_3 = {B->C,C->B,C->D,C->E} gata asta e 

3. Nu este in BCNF,avem dependente partiale de exemplu B->E,C->D
4. (A,B,C,D,E) => (A,B,C),(C,D),(B,E)
5. nu
6. nu
7. descompunere 3NF F_3 = {B->C,C->BDE} => (A,B,C,D,E) descompunem in (B,C),(C,B,D,E),(A,B),(A,C) deoarece A,B nu este inclus nici in (B,C) nici in (C,B,D,E) la fel si AC