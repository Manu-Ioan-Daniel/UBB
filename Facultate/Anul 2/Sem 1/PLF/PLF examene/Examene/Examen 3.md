### Subiectul I

#### Exercitiul 1

Fie următoarea definiție de funcție LISP:

```lisp
(DEFUN Fct(F L)
  (COND
    ((NULL L) NIL)
    ((FUNCALL F (CAR L)) (CONS (FUNCALL F (CAR L)) (Fct F (CDR L))))
    (T NIL)
  )
)
```

Dați o soluție pentru a evita dublul apel **`(FUNCALL F (CAR L))`**. Nu se vor folosi `SET`, `SETQ`, `SETF`.

##### Raspuns
```lisp
(DEFUN Fct(F L)
  (COND
    ((NULL L) NIL)
    (T
	    (
		    (LAMBDA (Rez) 
				(IF Rez 
				    (CONS Rez (Fct F (CDR L))) NIL
				)
			)
			(FUNCALL F (CAR L))
		)
    )
   )
)	

```

#### Exercitiul 2

Fie $L$ o listă numerică și următoarea definiție de predicat PROLOG **`f(list, integer)`**, având modelul de flux $(i, o)$:

``` prolog
f([], -1).
f([H|T], S) :- H > 0, f(T, S1), S1 < H, ! , S is H.
f([_|T], S) :- f(T, S).
```

Dați o soluție pentru a evita apelul recursiv **`f(T, S)`** în ambele clauze, fără a redefini predicatul.

##### Raspuns

```prolog
f([], -1).
f([H|T], S) :-f(T, S1), ((S1<H)->S is H;S is S1).
```

#### Exercitiul 3

Se definește în LISP funcția `G` prin **`(DEFUN G(L) (LIST (CAR L) (CAR L)))`**. Pentru redenumirea funcției `G` se efectuează **`(SETQ Q 'G)`** urmat de **`(SETQ P Q)`**. Ce se va obține prin evaluarea formei **`(FUNCALL P '(A B C))`**? Justificați răspunsul.

##### Raspuns

`(A A)` deoarece `SETQ Q 'G` atribuie simbolului Q valoarea simbolului 'G,iar `SETQ P Q` atribuie simbolului P valoarea curenta a lui Q,deci primeste si el valoarea simbolului 'G, deci `FUNCALL P` devine `FUNCALL 'G` deci apelam functia G pe lista (A,B,C).Functia G returneaza o lista formata din primul element al listei primite ca si parametru de 2 ori.


#### Exercitiul 4

Definim în PROLOG predicatul **`f(list, integer)`** cu modelul de flux $(i, o)$:

```prolog
f([], 0).
f([H|T], S) :- f(T, S1), S1 is S - H.
```

Care este rezultatul evaluării **`f([1,2,3,4,5,6,7,8], S)`**? Justificați răspunsul.

##### Raspuns



---

### Subiectul II

Să se scrie un program PROLOG care generează lista aranjamentelor de $k$ elemente dintr-o listă de numere întregi, având produsul $P$ dat. Se vor scrie modelele matematice și modelele de flux pentru predicatele folosite (nu neapărat în această ordine).

#### **Exemplu:** 
pentru lista `[2, 5, 3, 4, 10]`, $k=2$ și $P=20 \Rightarrow [[2,10],[10,2],[5,4],[4,5]]$.

#### Rezolvare

[[Examen 1#Subiectul II]]

---

### Subiectul III

Un arbore n-ar se reprezintă în LISP astfel (nod subarbore1 subarbore2). Se cere să se înlocuiască nodurile de pe nivelurile impare din arbore cu o valoare $e$ dată. Nivelul rădăcinii se consideră a fi 0. **Se va folosi o funcție MAP**.

#### **Exemplu:** 
pentru arborele `(a (b (g)) (c (d (e)) (f)))` și $e=h \Rightarrow (a (h (g)) (h (d (h)) (f)))$.

#### Rezolvare

[[Examen 1#Subiectul III]]