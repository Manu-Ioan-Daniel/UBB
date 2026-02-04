
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

