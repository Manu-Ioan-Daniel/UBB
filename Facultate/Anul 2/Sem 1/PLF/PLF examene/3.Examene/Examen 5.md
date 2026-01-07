
### Subiectul I

#### Exercitiul 1

Fie următoarea definiție de funcție LISP:

``` lisp
(DEFUN F(L)
  (COND
    ((NULL L) 0)
    ((> (F (CAR L)) 2) (+ (CAR L) (F (CDR L))))
    (T (F (CAR L)))
  )
)
```

Dati o soluție pentru a evita dublul apel recursiv `(F (CAR L))`. Nu se vor folosi `SET`, `SETQ`, `SETF`.
##### Rezolvare

``` lisp
(DEFUN F(L)
  (COND
    ((NULL L) 0)
    (T
	    (
		    (LAMBDA (Fct)
			    (if (> Fct 2)
				    (+ (CAR L) (F (CDR L)))
				    Fct
			    )
		    )
		    (F (CAR L))
		  )
    )
  )
)
```

#### Exercitiul 2

Fie L o listă numerică și următoarea definiție de predicat PROLOG `f(list, integer)`, având modelul de flux $(i, o)$:


``` prolog
f([], 0).
f([H|T], S) :- f(T, S1), H < S1,!,  S is H + S1.
f([_|T], S) :- f(T, S1), S is S1 + 2.
```

Dați o soluție pentru a evita apelul recursiv `f(T, S1)` în ambele clauze, fără a redefini predicatul.

##### Rezolvare

```prolog
f([], 0).
f([H|T], S) :- f(T, S1), (H < S1 -> S is H + S1 ; S is S1 + 2).
```

#### Exercitiul 3

Se definește în LISP funcția `F` prin:

``` lisp
(DEFUN F(X &REST Y)
  (COND
    ((NULL Y) X)
    (T (APPEND X (MAPCAR #'CAR Y)))
  )
)
```

Ce se va obține prin evaluarea formei `(APPEND (F '(1 2)) (F '(3 4) '(5 6) '(7 8)))`?

(1 2 3 4 5 7) 
##### Rezolvare

- **Pas 1:** `(F '(1 2))` -> `Y` este nil, deci returnează `X`, adică `(1 2)`.
    
- **Pas 2:** `(F '(3 4) '(5 6) '(7 8))` -> `X` este `(3 4)`, `Y` este `((5 6) (7 8))`. `(MAPCAR #'CAR Y)` extrage `(5 7)`. `APPEND` combină `(3 4)` cu `(5 7)`, rezultând `(3 4 5 7)`.
    
- **Pas 3:** `(APPEND '(1 2) '(3 4 5 7))` rezultă `(1 2 3 4 5 7)`.
    
**Rezultat final:** `(1 2 3 4 5 7)`

#### Exercitiul 4

Definim în PROLOG predicatele `p(integer), q(integer), r(integer)` având modelele de flux $(o)$ și predicatul `s`:

```prolog
p(1). p(2).
q(1). q(2).
r(1). r(2).
s :- !, p(X), q(Y), r(Z), write(X, Y, Z), nl.
```

Precizați rezultatul următoarei întrebări: `s.`

##### Rezolvare

nu afiseaza nimic pt ca nu exista functie write in prolog cu 3 parametri deci o sa avem mare eroare in gaoaza.

---

### Subiectul II

Să se scrie un program PROLOG care să genereze lista submulțimilor cu cel puțin **N** elemente având suma divizibilă cu 3. Se vor scrie modelele matematice și de flux.

#### Rezolvare

```prolog
combinations(_,0,[]).
combinations([H|T],K,[H|Rez]):-
	K>0,
	K1 is K-1,
	combinations(T,K1,Rez).
combinations([_|T],K,Rez):-
	K>0,
	combinations(T,K,Rez).

solution(L,N,Rez):-
	findall(C,(combinations(L,N,C), suma(C,Suma), 0 is Suma mod 3),Rez).
	
suma([],0):-!.	
suma([H|T],Rez):-
	suma(T,RezT),
	Rez is H + RezT.


```

#### Modele Matematice:

$$
combinations(L,K) = \begin{cases}
\emptyset ,&  \text{ daca l este vida} \\
l_{1} \cup combinations(T,K-1),& \text{ daca } K>0 \\
combinations(T,K),& \text{ daca } K>0
\end{cases}
$$
$$
\text{suma}(L) = \begin{cases}
0,& \text{ daca l este vida} \\
H+\text{suma(T)},& \text{ altfel}
\end{cases}
$$
$$
solution(L,N) = \cup combinations(L,K)
$$


---

### Subiectul III

Să se scrie o funcție LISP care să aibă ca rezultat lista inițială în care atomii de pe nivelul **k** au fost înlocuiți cu **0** (nivelul superficial se consideră 1). **Se va folosi o funcție MAP.**

#### Rezolvare


```lisp
(defun mata (tree k nivelCur)
	(cond
		((null tree) nil)
		(t
		  (cons 
			(if (= k nivelCur)
				0
				(car tree)
			)
			(mapcar 
				#'(LAMBDA (subtree)
					(mata subtree k (+ 1 nivelCur))
				)
				(cdr tree)
			)
		   )
		)
	)
)

```

#### **Exemple de rulare conform cerinței:**

- a) `k=2` pentru `(a (1 (2 (b))) (c (d)))` $\Rightarrow$ `(a (0 (2 (b))) (0 (d)))`
    
- b) `k=1` $\Rightarrow$ `0` (dacă întreaga listă e considerată atom/structură la nivel 1) sau înlocuirea elementelor de top.
    
- c) `k=4` $\Rightarrow$ lista rămâne nemodificată dacă nu are adâncime 4.
    
