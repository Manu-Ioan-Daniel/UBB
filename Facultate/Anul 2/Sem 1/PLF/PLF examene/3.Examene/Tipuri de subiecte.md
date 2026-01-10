
# Exercitiul 2

## Enunt 1

Write a Prolog program that, given a list of integer numbers and lists of integer numbers, computes the list in reversed order (each sublist is reversed). Explain the proposed algorithm. For example, for `[1, [2, 4, 3], 5, [6, 7]]` the result is `[[7, 6], 5, [3, 4, 2], 1]`.

```prolog
inverseaza([],R,R):-!. 
inverseaza([H|T],R,Rez):- 
	(is_list(H) -> inverseaza(H,[],RezLista), H1 = RezLista;H1 is H), 
	Rcur = [H1|R], 
	inverseaza(T,Rcur,Rez). 
inverse(L,R):- inverseaza(L,[],R).
```

## Enunt 2

Given a list of integer numbers and a value N, write a Prolog program to remove from the list the elements from the positions N, 2*N, 4*N, $...$ Explain the proposed algorithm. For example, for the list `L = [1, 2, 3, 4, 5, 6, 7, 8]` and N = 2 the result is `[1, 3, 5, 6, 7]`.

```prolog
elimina([],_,_,[]):-!.
elimina([_|T],N,N,Rez):-
    N2 is N * 2,
    NCur2 is N + 1,
    elimina(T,N2,NCur2,Rez),!.
elimina([H|T],N,NCur,[H|Rez]):-
    NCur2 is NCur+1,
    elimina(T,N,NCur2,Rez).

```

## Exercitiul 3

### Enunt 1

Genereaza toate submultimile care au numar de elemente par.

```prolog
combinari([],K,[]):- 0 is K mod 2. 
combinari([H|T],K,[H|R]):- 
	K1 is K+1,
	combinari(T,K1,R). 
	
combinari([_|T],K,R):- 
	combinari(T,K,R). 

combinari(L,R):- findall(C,combinari(L,0,C),R).

```

# Exercitiul 4

## Enunt 1

Give two recursive implementations for the following requirement: Replace all even numerical values in a given nonlinear list with their natural successor. For example, for the list `(1 s 4 (2 f (7)))` the result is `(1 s 5 (3 f (7)))`.

### Implementare 1

```lisp
(defun replacea(L)
	(cond
		((null L) nil)
		((atom L)
			(if (numberp L)
				(+ 1 L)
				L
			)
		)
		(t
			(mapcar #'replacea L)
		)
	)
)


```

### Implementare 2

```lisp
(defun replaceb(L)
	(cond
		((null L) nil)
		((listp (car L))
			(cons (replaceb (car L)) (replaceb (cdr L)))
		)
		((atom (car L))
			(cons
				(if (numberp (car L))
					(+ (car L) 1)
					(car L)
				)
				(replaceb (cdr L))
			)
		)
	)
)

```

### Enunt 2

Write a Lisp program and deduce the complexity for the following requirement. Given a linear numerical list, generate a new list with every N-th element removed. For example, for the list (1 2 3 4 5) and N = 2 the result is (1 3 5).

```lisp

(defun remove(L N PozCur)
	(cond
		((= N PozCur)
			remove((cdr L) N 1)
		)
		(t
			(cons (car L) (remove (cdr L) N (+ 1 PozCur))))
		)
	)
)

```

#### Complexitate

$$
T(N) = 1 +T(N-1) = 1+1+1+\dots+1 \text{ de N ori} = N \implies \text{complexitate }
\theta(n)
$$

## Enunt 3

Suma intre 2 numere scrise sub forma de lista

```lisp
(defun mate (l l2 p)
    (cond
        ((and (null l)(> p 0)) (list p))
        ((null l) nil)
        (t  
          (cons 
            (mod (+ (car l) (car l2) p) 10)
            (mate (cdr l) (cdr l2) (floor (+ (car l) (car l2) p) 10))
          )
        )
    )
        
)

(defun run(l l2 )
  (reverse (mate (reverse l) (reverse l2) 0))
)

(print (run '(9 9 9 9) '(2 3 3 4)))
```

# Exercitiul 5

## Enunt 1

Write a Lisp program to determine the number of sublists at any level of a given list, where the **last atom (at any level) is nonnumeric**. The list processing will be done using a **MAP function**. For example, the list `(A (B 2) (1 C 4) (D 1 (6 F)) ((G 4) 6) F)` has 2 such sublists: `(6 F)`, `(D 1 (6 F))`.

```lisp

(defun verifica (L)
	(cond
		((null L) 0)
		((atom L)
			(if (numberp L)
				0
				1
			)
		)
		(t
			(verifica (car L))
		)
	)
)
(defun invers (L)
	(cond
		((null L) nil)
		((atom L) L)
		(t
			(append (invers (cdr L))
				(invers (car L))
			)
		)
	)
)

(defun nrsub(L)
	(cond
		((null L) 0)
		(t
			(apply #'+ (mapcar #'verifica L))
		)
	)
)

```
