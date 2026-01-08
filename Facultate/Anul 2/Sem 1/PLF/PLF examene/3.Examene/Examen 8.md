
### Exercitiul 1

Dati două implementări Turbo Prolog pentru transformarea unei liste numerice într-o mulțime. Explicați fiecare implementare propusă.
#### Raspuns

##### **IMPLEMENTARE 1**

```prolog

set([],[]):-!.
set([H|T],[H|Rez]):-
	\+ contains(T,H),
	set(T,Rez),!.
set([_|T],Rez):-
	set(T,Rez).


contains([H|_],H):-!.
contains([_|T],E):-
	contains(T,E).
	
```

##### **IMPLEMENTARE 2**

```prolog
set([],[]):-!.
set([H|T],[H|Rez]):-
	stergeElement(T,_,Rez1),
	set(Rez1,Rez).

stergeElement([],_,[]).
stergeElement([H|T],H,Rez):-
	stergeElement(T,H,Rez),!.
stergeElement([H|T],E,[H|Rez]):-
	stergeElement(T,E,Rez).

```
### Exercitiul 2

Să se scrie un program Turbo PROLOG care generează lista aranjamentelor de k elemente dintr-o listă de numere întregi, având o sumă S dată. Explicați algoritmul propus.
#### Exemplu
pentru lista `[6, 5, 3, 4]`, $k=2$ și S=9 $\Rightarrow$ `[[6,3], [3,6], [5,4], [4,5]]` (nu neapărat în această ordine).
#### Rezolvare

```prolog
select([H|T],H,T).
select([H|T],X,[H|Rez]):-
	select(T,X,Rez).
	
	
aranjamente(_,0,0,[]):-!.
aranjamente(L,K,S,[E|Rez]):-
	K>0,
	select(L,E,List),
	K1 is K-1,
	E =< S,
	S1 is S-E,
	aranjamente(List,K1,S1,Rez).
	
solutie(L,K,S,Rez):-
	findall(A,aranjamente(L,K,S,A),Rez).



```

### Exercitiul 3

Se definește funcția $G$ prin `(DEFUN G(L) (+ (CAR L) (CADR L)))`. Pentru redenumirea funcției $G$ se efectuează `(SETQ H 'F)` `(SET H 'G)`. Ce se va obține prin evaluarea formei `(F '(2 3 4 5 6))`? Justificați răspunsul și dați o soluție pentru o astfel de situație.
#### Rezolvare
Eroare.
### Exercitiul 4

Să se scrie un program LISP și să se deducă complexitatea pentru următoarea cerință: Se dă o listă liniară numerică și se cere să se scrie de două ori elementele din $N$ în $N$. De exemplu, pentru lista `(1 2 3 4 5)` și $N=2$ rezultă lista `(1 2 2 3 4 4 5)`.

```lisp
(defun adaugaN (L N Contor)
	(cond
	  ((null L) nil)
		((= N Contor)
			(append (list (car L) (car L))
				(adaugaN (cdr L) N 1)
			)
		)
		(t
			(append (list (car L))
				(adaugaN (cdr L) N (+ 1 Contor))
			)
		)
	)	
)
```

#### Rezolvare

### Exercitiul 5

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care ultimul atom numeric (la orice nivel) este impar. Prelucrarea se va face folosind o funcție MAP.
#### Exemplu: 
lista `(A (B 2) (1 C 4) (D 1 (9 F)) ((G 7) 6))` are 3 astfel de subliste: `(D 1 (9 F))`, `(9 F)` și `(G 7)`.

```lisp

(defun ultimAtom (L lastNumber)
	(cond
		((null L)
			(if (oddp lastNumber)
				t
				nil
			)
		)
		((listp (car L))
			(or (ultimAtom (cdr L) lastNumber) (ultimAtom (car L) lastNumber))
		)
		((numberp (car L))
			(ultimAtom (cdr L) (car L))
		)
		(t
			(ultimAtom (cdr L) lastNumber)
		)
	)
)
(defun verificare (L)
	(cond
		((atom L) 0)
		(t  (+  (if (ultimAtom L 0) 1 0)
				    (apply #'+ (mapcar #'verificare L))
			  )
		)
		
	)
)
```

