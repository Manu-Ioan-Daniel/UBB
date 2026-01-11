### Subiectul I

#### Exercitiul 1

Se definește în LISP funcția `Fct` prin `(DEFMACRO Fct(X) (LIST 'SETF '(CADR X) 2))`. Variabila `X` este inițializată prin `(SETQ X '(5 6 7))`. Care este valoarea lui `X` în urma evaluării formei `(Fct X)`? Justificați răspunsul.

##### Rezolvare:

`(5 2 7)`
Fiind un macro, `(Fct X)` se extinde mai întâi în `(SETF (CADR X) 2)`. Deoarece `X` este `(5 6 7)`, `(CADR X)` accesează al doilea element al listei (valoarea 6). Evaluarea formei extinse modifică distructiv acest element, înlocuindu-l cu 2.

#### Exercitiul 1 V2

Se definește în LISP funcția `Fct` prin `(DEFMACRO Fct(X) (LIST 'SETQ '(CADR X) 2))`. Variabila `X` este inițializată prin `(SETQ X '(5 6 7))`. Care este valoarea lui `X` în urma evaluării formei `(Fct X)`? Justificați răspunsul.
##### Rezolvare

Da eroare pentru ca `SETQ` asteapta sa primeasca un simbol ca prim parametru,dar noi ii dam `(CADR X)`
#### Exercitiul 2

Definim în PROLOG predicatul `q(list, list, integer)` având modelul de flux $(o, i, o)$:

```prolog
q([], [], 0).
q(Rez, [H|T], L) :- H > 0, q(Rez, T, L1), L is L1 + 1, !.
q(L, L, 0).
```

Care este rezultatul evaluării `q(Rez, [6, 7, -2, 9], L)`? Justificați.
##### Rezolvare:

`Rez = [-2,9], L = 2`
Programul numara numarul de numere pozitive pana la primul numar negativ si returneaza lista formata din numerele de la primul numar negativ pana la ultimul numar din lista.
#### Exercitiul 3

Se definește în LISP funcția `G` prin `(DEFUN G(L) (LIST (CAR L) (CAR L)))`. Fie evaluarea `(SETQ Q 'G)` urmată de `(SETQ P 'Q)`. Ce se va obține prin evaluarea formei `(FUNCALL (EVAL P) '(A B C))`? Justificați.
##### Rezolvare:

`(A A)`

Justificare: `(EVAL P)` returnează valoarea lui `Q`, care este simbolul 'G. `(FUNCALL 'G '(A B C))` apelează funcția `G` cu argumentul listă. Funcția `G` ia primul element `(A)` și construiește o listă cu acesta repetat de două ori.

#### Exercitiul 4

Fie `L` o listă numerică și următoarea definiție de predicat PROLOG `g(list, list)` având modelul de flux $(i, o)$:


```
g([], []).
g([_|T], S) :- !, g(T, S).
g([H|T], [H|S]) :- H mod 2 =:= 0, g(T, S).
```

Precizați rezultatul următoarei întrebări: `g([1, 2, 3], L)`. Justificați.
##### Rezolvare:
`L = []`

A doua clauza se executa mereu,si din cauza cut ului,nu va intra niciodata pe a 3 a clauza,deci o sa returneze lista vida.

---

### Subiectul II

Pentru o valoare **N** dată, să se genereze lista permutărilor cu elementele $N, N+1, \dots, 2N-1$ având proprietatea că valoarea absolută a diferenței dintre două valori consecutive din permutare este $\leq 2$. Se vor scrie modelele matematice și modelele de flux pentru predicatele folosite.

#### Rezolvare

```prolog
pick([H|T],H,T).
pick([H|T],X,[H|Rez]):-
    pick(T,X,Rez).

helperPermutations([],_,[]):-!.
helperPermutations([E],E,[E]):-!.
helperPermutations(L,H,[H|P]):-
    pick(L,H,Rez),
    helperPermutations(Rez,Prev,P),
    Dif is Prev-H,
    abs(Dif) =< 2.


permutations(L,Rez):-
    findall(P,helperPermutations(L,_,P),Rez).


rezolvare(N,Rez):-
    intervalToList(N,N,Elemente),
    permutations(Elemente,Rez).

intervalToList(N,I,[I]):-
    I is 2*N-1,!.
intervalToList(N,I,[I|Rez]):-
    I1 is I + 1,
    intervalToList(N,I1,Rez).

```
##### Model Matematic

$$
\text{pick}([l_{1},l_{2},\dots l_{n}]) = \begin{cases}
(E,[l_{2},l_{3},\dots l_{n}]),& \text{ daca } l_{1} = E \\
(E,[l_{1} \cup L]) ,& \text{ daca } (E,L) = \text{pick}(T)
\end{cases}
$$
$$
\text{helperPermutations}(L) = \begin{cases}
\emptyset,& daca \ L = \emptyset \\
([l_{1}],l_{1}),& daca \ L  = [l_{1}] \\
([H \cup \mathrm{Re}z],H ),& daca \ (\mathrm{Re}z,Prev) = \text{helperPermutations}(\mathrm{Re}st) \ \text{ unde }(H,\mathrm{Re}st) = \text{pick}(L) \text{ si |(Prev - H)|}\leq2
\end{cases}
$$
$$
\text{permutations}(L) = \cup \text{helperPermutation}(L)
$$
$$
\text{intervalToList}(N,I) = \begin{cases}
[2N-1],& daca \ I = 2N-1 \\
I \cup \text{intervalToList}(N,I+1),& altfel
\end{cases}
$$
$$
rezolvare(N) = \text{permutations}(\text{intervalToList(N,N)})
$$

---

### Subiectul III

Un arbore n-ar se reprezintă în LISP astfel: `(nod subarbore1 subarbore2 ...)`. Se cere să se determine calea de la rădăcină către un nod dat. **Se va folosi o funcție MAP.**

#### **Exemplu:** 
pentru arborele `(a (b (g)) (c (d (e)) (f)))`

- a) `nod = e` $\Rightarrow$ `(a c d e)`
    
- b) `nod = v` $\Rightarrow$ `()`


#### Rezolvare

```lisp
(DEFUN Functie (tree nod)
	(cond
		((null tree) nil)	
		(t
			(if (eq (car tree) nod)
				(list nod) 
				(let 
					(
						(cale-copii 
							(mapcan
								#'(LAMBDA (subtree)
									(Functie subtree nod)
								)
								(cdr tree)
							)
						) 
					)
				    (if cale-copii
					    (cons (car tree) cale-copii)
					    nil		
				    )
        ) 
			)
		)
	)
)

(print (Functie '(a (b (g)) (c (d (e)) (f))) 'e))

```

sau

```lisp
(defun exista (tree e)
	(cond
		((null tree) nil)
		((listp (car tree))
		  (or (exista (car tree) e) (exista (cdr tree) e))
		)
		(t
		  (if (eq (car tree) e) t (exista (cdr tree) e))
		)
	)
)

(defun cale(tree e)
	(cond
		((null tree) nil)
		((eq (car tree) e) (list (car tree)))
		((exista tree e)
		  (cons
		    (car tree)
		    (mapcan 
		      #'(LAMBDA (subtree) (cale subtree e))
		      (cdr tree)
		    )
		  )
		)
	)
)
```
##### Output
`(A C D E)`
