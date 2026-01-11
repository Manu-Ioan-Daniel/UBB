### Exercitiul 1

Definim în PROLOG predicatul `f(list, integer)` având modelul de flux `(i, o)`:

Prolog

```
f([],0).
f([H|T],P):-!, H mod 2 =:= 0, f(T,P1), P is P1*H.
f([_|T],P):-f(T,P1),P is P1.
```

Care este rezultatul evaluării **f([1,2,3,4,5,6,7,8], P)**? Modificați definiția predicatului `f` astfel încât să calculeze numărul valorilor impare din listă.

#### Rezolvare

`rezultatul este false,datorita cut ului pus gresit in a doua clauza,ceea ce il previne in a intra in a 3 a clauza mereu`

```prolog
f([],0).
f([H|T],P):-H mod 2 =:= 1,! ,f(T,P1), P is P1 + 1.
f([_|T],P):-f(T,P).
```

---

### Exercitiul 2

Dându-se o listă formată din numere întregi, să se scrie un program PROLOG care șterge al 2-lea, al 6-lea, al 10-lea.... element din listă. Explicați algoritmul propus.

#### Exemplu

`L=[1,2,3,4,5,6,7,8]` obtinem  `[1,3,4,5,7,8]`.

#### Rezolvare

```prolog
sterge([],_,[]).
sterge([H|T],0,Rez):-
	sterge(T,4,Rez),!.
sterge([H|T],N,[H|Rez]):-
	N1 is N-1,
	sterge(T,N1,Rez).
stergere(L,Rez):-
	sterge(L,2,Rez).
```

---

### Exercitiul 3

Dându-se o listă formată din numere întregi, să se genereze lista submulțimilor cu $k$ elemente în progresie aritmetică. Explicați algoritmul propus.

#### Exemplu

pentru lista $L=[1,5,2,9,3]$ și $k=3 \Rightarrow [[1,2,3], [1,5,9], [1,3,5]]$ (nu neapărat în această ordine).

```prolog

select([H|T],H,T).
select([H|T],X,[H|Rez]):-
	select(T,X,Rez).


aranjamente_aux(_,0,_,_,_,[]).

aranjamente_aux(L,K,2,Prev,R,[X|Rez]):-
	K>0,
	R \= 0,
	select(L,X,List),
	K1 is K-1,
	R is X-Prev,
	aranjamente_aux(List,K1,2,Prev,R,Rez).
aranjamente_aux(L,K,State,Prev,R,[X|Rez]):-
	K>0,
	K1 is K-1,
	select(L,X,List),
	State\=2,
        (State = 0 -> aranjamente_aux(List,K1,1,X,R,Rez);
        R is X-Prev,R>0,aranjamente_aux(List,K1,2,X,R,Rez)).


aranjamente(L,K,Rez):-
	findall(A,aranjamente_aux(L,K,0,_,_,A),Rez).


```

---

### Exercitiul 4

Se consideră o listă neliniară. Să se scrie o funcție care să aibă ca rezultat lista inițială în care atomii de pe nivelul $k$ au fost înlocuiți cu 0 (nivelul superficial se consideră 1).

#### Exemplu

pentru lista (a (1 (2 b)) (c (d)))

a) $k=2 \Rightarrow (a (0 (2 b)) (0 (d)))$

b) $k=1 \Rightarrow (0 (1 (2 b)) (c (d)))$

c) $k=4 \Rightarrow$ lista nu se modifică

```lisp

(defun inlocuire(L K)
	(cond
		((null L) nil)
		((AND (atom L) (= K 0))
			0
		)
		((atom L) L)
		(t
			(mapcar #'(LAMBDA (subL) (inlocuire subL (- K 1)))
				L
			)
		)
	)
)

```

---

### Exercitiul 5

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care **suma atomilor numerici de la nivelurile impare este număr par** – nivelul superficial al listei se consideră 1. Prelucrarea se va face folosind o funcție MAP.

#### Exemplu

lista `(A 1 (B 2) (1 C 4) (D 1 (6 F)) ((G 4) 6))` are 4 astfel de subliste: `(B 2)`, `(6 F)`, `(G 4)` și `((G 4) 6)`.

[[Examen 12#Exercitiul 5]]