
### **Subiectul 1**

Fie $L$ o listă numerică și următoarea definiție de predicat având modelul de flux $(i, o)$:

Prolog

```
f([], 0).
f([H|T], S) :- f(T, S1), S1 >= 2, !, S = S1 + H.
f([_|T], S) :- f(T, S1), S = S1 + 1.
```

Dați o soluție pentru a evita apelul recursiv `f(T, S1)` în ambele clauze.

#### Raspuns

```prolog
f([], 0).
f([H|T], S) :- f(T, S1), (S1 >= 2 -> S = S1 + H;S = S1 + 1).
```
---

### **Subiectul 2**

Să se scrie un program Turbo PROLOG pentru eliminarea primelor **n** numere prime ale unei liste. Explicați algoritmul propus.

**Exemplu:** rezultatul eliminării primelor 3 numere prime din lista `[2, 4, 5, 7, 6, 9, 11]` este lista `[4, 6, 9, 11]`.

#### Raspuns

``` prolog
prim(N,D):-
	N>1,
	D is N//2 + 1,!.
prim(N,D):-
	N>1,
	0 =\= N mod D,
	D1 is D+1,
	prim(N,D1).

eliminarePrime([],_,[]):-!.
eliminarePrime(L,0,L):-!.
eliminarePrime([H|T],N,Rez):-
	prim(H,2),
	N1 is N-1,
	eliminarePrime(T,N1,Rez),!.
eliminarePrime([H|T],N,[H|Rez]):-
	eliminarePrime(T,N,Rez).
	

```

---

### **Subiectul 3**

Să se scrie un program Turbo PROLOG care generează lista submulțimilor de sumă **S** dată, cu elementele unei liste. Explicați algoritmul propus.

#### **Exemplu:**
pentru lista `[1, 2, 3, 4, 5, 6, 10]` și `S=10` $\Rightarrow$ `[[1,2,3,4], [1,4,5], [2,3,5], [4,6], [10]]`.
#### Rezolvare

```prolog
submultimi(_,[],0).
submultimi([H|T],[H|Rez],S):-
	H =< S,
	S1 is S-H,
	submultimi(T,Rez,S1).
submultimi([_|T],Rez,S):-
	submultimi(T,Rez,S).
	
solutie(L,N,Rez):-
	findall(S,submultimi(L,S,N),Rez).
```

---

### **Subiectul 4**

Un arbore n-ar se reprezintă în LISP astfel: (nod subarbore1 subarbore2 ...).

Se cere să se înlocuiască nodurile de pe nivelul k din arbore cu o valoare e dată. Nivelul rădăcinii se consideră a fi 1.Sa se foloseasca o functie MAP

#### **Exemplu:** 
pentru arborele `(a (b (g)) (c (d (e)) (f)))` și `e = h`

- **a)** `k = 2` $\Rightarrow$ `(a (h (g)) (h (d (e)) (f)))`
    
- **b)** `k = 4` $\Rightarrow$ `(a (b (g)) (c (d (e)) (f)))`

#### Rezolvare

```lisp
(defun inlocuire (tree k nivelCur e)
  (cond
  
    ((null tree) nil)
    (t
      (cons
      (if(= nivelCur k) e (car tree)
        
      )
      (mapcar #'(lambda (subtree) (inlocuire subtree k (+ 1 nivelCur) e)
      
                )
        (cdr tree)  
      )
      )
    )
  )
)

```

[[Examen 1#Subiectul III|Vezi exercitiu asemanator aici]]

---

### **Subiectul 5**

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care cel mai mare element numeric de la orice nivel este număr par. Prelucrarea se va face folosind o funcție MAP.

**Exemplu:** lista `(A (B 1) (1 C 4) 7 (D 1 (6 F)) ((G 4) 6))` are 5 astfel de subliste: `(1 C 4)`, `(D 1 (6 F))`, `(6 F)`, `((G 4) 6)` și `(G 4)`.

```lisp
(defun celMaiMarePar (L)
	(cond
		((null L) 0)
		((evenp (maxim L -1)) 1)
		(t 0)
	)
)
(defun maxim (L max)
	(cond
		((null L) max)
		((numberp (car L))
			(if (< max (car L))
				(maxim (cdr L) (car L))
				(maxim (cdr L) max)
			)
		)
		((listp (car L))
			(maxim (cdr L) (maxim (car L) max))
		)
		(t
			(maxim (cdr L) max)
		)
	)
)
(defun verificare (L)
	(if (atom L) 
		0	
		(+ (celMaiMarePar L) (apply #'+ (mapcar #'verificare L)))
	)
)


```
