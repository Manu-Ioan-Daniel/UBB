### Exercitiul 1

Dați două implementări PROLOG pentru calculul sumei primelor $N$ numere naturale. De exemplu, pentru $N=4$ rezultatul este 10. Explicați fiecare implementare propusă.

#### Implementare 1

```prolog
suma(0,0):-!.
suma(N,S):-
	N>0,
	N1 is N-1,
	suma(N1,S2),
	S is N + S2.
```

#### Implementare 2

```prolog
suma(N,S):-
	N>0,
	S is N*(N+1)//2.

```
---

### Exercitiul 2

Dându-se o listă formată din numere întregi, să se genereze lista aranjamentelor cu $N$ elemente având suma $S$ dată. Explicați algoritmul propus.

#### Exemplu

pentru lista $L=[2, 7, 4, 5, 3]$, $N=2$ și $S=7 \Rightarrow [[2, 5], [5, 2], [3, 4], [4, 3]]$ (nu neapărat în această ordine).

#### Rezolvare

[[Examen 8#Exercitiul 2]]

---

### Exercitiul 3

Fie următoarea definiție de funcție:

```lisp
(DEFUN F(L) (COND ((NULL L) nil)
                  ((LISTP (CAR L)) (APPEND (F (CAR L)) (F (CDR L)) (CAR (F (CAR L)))))
                  (T (LIST (CAR L)))))
```

Dați o soluție pentru a evita dublul apel recursiv `(F (CAR L))`. Nu se vor folosi `SET`, `SETQ`, `SETF`.

#### Rezolvare

```lisp
(defun F(L)
	(cond
		((null L) nil)
		(t
			(
				(LAMBDA (G)
					(if (listp (car L))
						(append G (F (cdr L)) (car G))
						(list (car L))
					)
					
				)
				(F (car L))
			)	
		)
	)
)
```
---

### Exercitiul 4

Se consideră o listă neliniară. Să se scrie o funcție care să aibă ca rezultat lista inițială din care au fost eliminate toate aparițiile unui element $e$.
#### Exemplu

a) dacă lista este (1 (2 A (3 A)) (A)) și $e$ este $A \Rightarrow (1 (2 (3)) NIL)$

b) dacă lista este (1 (2 (3))) și $e$ este $A \Rightarrow (1 (2 (3)))$
#### Rezolvare

``` lisp

(defun elimina(L E)
	(cond
		((null L) nil)
		((AND (atom L) (equal L E))
		  nil
		)
		((atom L) (list L))
		(t
		    (list(mapcan #'(LAMBDA (subL) (elimina subL E)) L))
		)
	)
)   
(defun eliminaPeBune (L E)
  (car (elimina L E))
)
(print (eliminaPeBune '(A (B 2) (1 C (C) 4) (1 (6 F)) ((G 4) 6)) 'C))

```



---

### Exercitiul 5

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care **suma atomilor numerici de la nivelurile impare este număr par** – nivelul superficial al listei se consideră 1. Prelucrarea se va face folosind o funcție MAP.

#### Rezolvare

```lisp

(defun suma(L K)
	(cond
		((null L) 0)
		((AND (numberp L) (oddp K)) L)
		((atom L) 0)
		(t
			(apply #'+ (mapcar #'(LAMBDA (subL) (suma subL (+ 1 K))
			
								) 
							   L
						)
			
			)
		)
	)
)

(defun chestie(L)
  (cond
  
    ;((null L) 0)
    ((atom L)
      0
    )
    (t
      (+ 
        (if (evenp (suma L 0))
          1
          0
        )
        (apply #'+ (mapcar #'chestie L))
      )
    )
    
  )
)
	
```