### Subiectul I

#### Exercitiul 1

Ce se va obține prin evaluarea formei `(DEFUN H() (FUNCTION (LAMBDA (X) (APPEND X (MAPCAR #'(LAMBDA (L) (CAR L)) X)))))`? Care este rezultatul evaluării formei `(FUNCALL (H) '((2 3) (4 5)))`? Justificați răspunsul.

##### Rezolvare
`((2 3) (4 5) 2 4)` 
Lambda(L) (CAR L) imi ia o lista ca si parametru si returneaza primul element din acea lista,iar mapcar de acest lambda imi transforma fiecare lista din X in primul element din fiecare lista.
Append ul adauga la X,rezultatul lambda ului,adica primul element din fiecare lista din X.

#### Exercitiul 2

Definim în PROLOG predicatul `w(integer, integer)` având modelul de flux $(o, i)$ și predicatul `r(integer, integer)` având modelul de flux $(i, o)$:

```prolog
w(N,N).
w(J,I) :- I < 10, I1 is I + 1, w(J, I1).

r(K, J) :- w(J, K), write(J), write(" "), fail.
```

Care este rezultatul evaluării `r(6, J)`? Justificați.
r(6,J) = w(J,6)

##### Raspuns

`6 7 8 9 10`
`false`
cand apelam r(6,J) se executa w(J,6) deci J=6 din cauza primei clauze,scrie 6,un spatiu,si da fail
dupa trece pe a doua clauza,si se ajunge la J=7,scrie 7,un spatiu,si da fail,si tot asa pana la J=10,cand  intra pe a doua clauza dar conditia este falsa,si afiseaza false.

#### Exercitiul 3

Se definește în LISP funcția `F` prin `(DEFUN F(L) (* (CAR L) (CADR L)))`. Fie următoarele forme: `(SETQ Q 'F)` urmată de `(SETQ P Q)`. Ce se va obține prin evaluarea formei `(APPLY P '((2 3 4)))`? Justificați răspunsul.
##### Raspuns

`6` pentru ca din cauza la `SETQ Q 'F` leaga simbolul Q de 'F si atunci `SETQ P Q` inseamna ca P->Q->'F deci `APPLY P` inseamna defapt `APPLY 'F` ->deci aplicam functia `F` pe lista [2,3,4] ->inmultim primul element cu al doilea->raspunsul este `6`

#### Exercitiul 4

Definim în PROLOG predicatul `p` având modelul de flux $(o)$:

```prolog
p(100).
p(N) :- write(N), N1 is N - 1, p(N1), nl.
```

Precizați rezultatul următoarei întrebări: `p(0)`. Justificați.

##### Raspuns

O sa afiseze o eroare pentru ca stack ul o sa ramana fara memorie,deoarece o sa continue pana la infinit.

---

### Subiectul II

Să se scrie un program PROLOG care generează lista submulțimilor cu **N** elemente din mulțimea **[A, B]** (A, B numere întregi, $A < B$), astfel încât suma elementelor dintr-o submulțime să fie număr par. Se vor scrie modelele matematice și de flux pentru predicatele folosite.

#### **Exemplu:** 
pentru lista $A=1, B=4$ și $N=2 \Rightarrow [[1,3], [2,4]]$

#### Rezolvare

```prolog
%helperCombinations(i,i,i,o)
helperCombinations(_,0,[],0).
helperCombinations([H|T],K,[H|Rez],S):-
    K>0,
    K1 is K-1,
    helperCombinations(T,K1,Rez,S1),
    S is S1+H.
helperCombinations([_|T],K,Rez,S):-
    K>0,
    helperCombinations(T,K,Rez,S).
    
%combinations(i,i,o)
combinations(L,K,Rez):-
    findall(C,(helperCombinations(L,K,C,S),0 is S mod 2),Rez).

%intervalToList(i,i,i,o)
intervalToList(_,B,B,[B]):-!.
intervalToList(A,B,I,[I|Rez]):-
    I1 is I+1,
    intervalToList(A,B,I1,Rez).

#combinationsInterval(i,i,i,o)
combinationsInterval(A,B,N,Rez):-
    intervalToList(A,B,A,R),
    combinations(R,N,Rez).

```

$$
helperCombinations(L,K,S) = \begin{cases}
\text{lista vida },S = 0,& \text{daca } K=0 \\
l_{1} \cup helperCombinations([l_{2},l_{3},\dots l_{n}],K-1,S+l_{1}),& \text{daca } K>0  \\
helperCombinations([l_{2},l_{3},\dots l_{n}],K,S),& \text{daca } K>0
\end{cases}
$$
$$
combinations(L,K) = \cup \ helperCombinations(L,K,S) \text{ unde } S\%2 = 0
$$
$$
\text{intervalToList}(A,B,I) = \begin{cases}
[B],& \text{ daca } I=B \\
I \cup \text{intervalToList}(A,B,I+1),& \text{ altfel }
\end{cases}
$$

$$
combinationsInterval(A,B,N) = combinations(\text{intervalToList}(A,B,A),N)
$$
---

### Subiectul III

Un arbore n-ar se reprezintă în LISP astfel: `(nod subarbore1 subarbore2 ...)`. Se cere să se determine lista nodurilor de pe nivelul **k**. Nivelul rădăcinii se consideră 0. Se va folosi o funcție **MAP**.

#### **Exemplu:** 
pentru arborele `(a (b (g)) (c (d (e)) (f)))`

- a) $k=2 \Rightarrow (g \ d \ f)$
    
- b) $k=5 \Rightarrow ()$
#### Rezolvare

```lisp
(DEFUN lista-noduri-nivel(tree nivelCur k)
	(cond 
		((null tree) nil)
		((= nivelCur k)
			(list (car tree))
		)
		(t
			(mapcan #'(lambda (subtree) (lista-noduri-nivel subtree (+ 1 nivelCur) k)) (cdr tree))
		)
	 )
)
(print (lista-noduri-nivel '(a (b (g)) (c (d (e)) (f))) 0 2))
```
##### Output
`(G D F)`
