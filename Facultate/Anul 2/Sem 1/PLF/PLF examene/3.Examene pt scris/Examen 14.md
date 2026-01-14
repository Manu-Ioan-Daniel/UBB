Iată enunțurile din imaginea încărcată, transcrise în formatul solicitat de tine.

---

### Exercitiul I.1

Se consideră următoarea definiție a unei funcții în LISP:

```lisp
(DEFUN F(L1 L2)
  (APPEND (F (CAR L1) L2)
          (COND
            ((NULL L1) (CDR L2))
            (T (LIST (F (CAR L1) L2) (CAR L2)))
          )
  )
)
```

Propuneți o soluție pentru a evita apelul recursiv dublu `(F (CAR L1) L2)`. Nu se vor utiliza `SET`, `SETQ` sau `SETF`. Justificați răspunsul.
#### Rezolvare

```lisp
(defun F(L1 L2)
	((LAMBDA (G)
		(append G
			(cond
				((null L1) (cdr L2))
				(T (LIST G (car L2))
					
				)
			)
		)
	) (F (CAR L1) L2))
)
```

---

### Exercitiul I.2

Fie `L` o listă numerică și următoarea definiție PROLOG pentru predicatul `f(list, integer)`, cu modelul de flux `(i, o)`:


```prolog
f([], 0).
f([H|T], S) :- f(T, S1), S1 < H, !, S is H.
f([_|T], S) :- f(T, S1), S is S1.
```

Propuneți o soluție pentru a evita apelul recursiv `f(T, S1)` în ambele clauze, fără a redefini predicatul. Justificați răspunsul.

```prolog
f([],0).
f([H|T],S):-
	f(T,S1),
	(S1<H -> S is H;S is S1).
```

---

### Exercitiul I.3

Funcția LISP `G` este definită prin `(DEFUN G(L) (MAPCON #'LIST L))`. Care este rezultatul evaluării formei `(APPLY #'APPEND (MAPCON #'G '(1 2)))`? Justificați răspunsul.
#### Rezolvare

Functia `G(L)` returneaza o lista cu toate cozile lui L.
deci `(MAPCON #'G '(1 2))` returneaza `((1 2) (2) (2))` => `APPEND` pe lista asta o sa dea `(1 2 2 2)`

---

### Exercitiul I.4

Fie `L` o listă numerică și următoarea definiție PROLOG pentru predicatul `f(list, list)` cu modelul de flux `(i, o)`:

```prolog
f([], []).
f([H|T], [H|S]) :- f(T, S).
f([H|T], S) :- H mod 2 =:= 0, f(T, S).
```

Care este rezultatul următorului scop: `?- f([1,2,3], L).`? Justificați răspunsul.

#### Rezolvare

o sa afiseze `[1,2,3] si [1,3]` prima solutie vine din cauza primei clauze apelate incontinuu pana la lista vida,iar cea de a 2 a solutie vine de la backtracking cand ajunge la lista `[2,3]`
si intra pe a 2 a clauza.

---

### Exercitiul II

Se dă o listă de numere întregi. Să se genereze în PROLOG lista tuturor permutărilor care au proprietatea că valoarea absolută a diferenței dintre oricare două valori consecutive din permutare este $\le 3$. Scrieți modelul matematic, modelul de flux și semnificația variabilelor pentru fiecare predicat folosit.

#### **Exemplu:** 
pentru lista `L = [2, 7, 5]` $\Rightarrow$ `[[2, 5, 7], [7, 5, 2]]` (nu neapărat în această ordine).

[[Examen 4#Subiectul II]]

---

### Exercitiul III

Se dă o listă neliniară. Să se scrie o funcție LISP care să elimine toți atomii numerici divizibili cu 3 de la toate nivelurile. Se va folosi o funcție MAP. Scrieți modelul matematic și semnificația parametrilor pentru fiecare funcție utilizată.

#### **Exemplu:** 
- pentru lista a) `(1 (2 A (3 A)) (6))` $\Rightarrow$ `(1 (2 A (A)) NIL)`

- pentru lista b) `(1 (2 (C)))` $\Rightarrow$ `(1 (2 (C)))`

#### Rezolvare

```lisp
;parametrul L reprezinta o lista pe care lucram
(defun eliminare (L)
	(cond
		((null L) nil)
		((AND (numberp L) (= (mod L 3) 0))
			nil
		)
		((atom L) (list L))
		(t
			(list(mapcan #'eliminare L))
		)
	)
)
;parametrul L reprezinta o lista
(defun elimina (L)
	(car (eliminare L)
)
```

$$
\text{eliminare(L)} = \begin{cases}
 \text{lista vida,} & \text{daca l e vida} \\
\text{lista vida},& \text{daca l este numar si l\%3 = 0} \\
[L],& \text{daca L este un atom} \\
\underset{l\in L}{concat}  \text{ eliminare(l)},& \text{atlfel}
\end{cases}
$$

