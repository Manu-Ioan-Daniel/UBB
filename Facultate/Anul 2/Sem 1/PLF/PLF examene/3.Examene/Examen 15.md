
### Exercitiul I.1

Consider the following function definition in LISP:

``` lisp
(DEFUN Fct(F L)
  (COND
    ((NULL L) NIL)
    ((FUNCALL F (CAR L)) (CONS (FUNCALL F (CAR L)) (Fct F (CDR L))))
    (T NIL)
  )
)
```

Give a solution to avoid the double call `(FUNCALL F (CAR L))`. You will not use `SET`, `SETQ`, `SETF` Justify the answer.
#### Rezolvare
```lisp

(DEFUN Fct(F L)
  (COND
    ((NULL L) NIL)
    (T
	    ((LAMBDA (G)
			(if G
				(cons G (Fct F (CDR L)))
				NIL
			)
	    ) (FUNCALL F (CAR L)))
    )
  )
)

```

---

### Exercitiul I.2

Let `L` be a numerical list and consider the following PROLOG definition for the predicate `f(list, integer)`, with the flow model `(i, o)`:

```prolog
f([], -1).
f([H|T], S) :- f(T, S1), S1 < H, !, S is H.
f([_|T], S) :- f(T, S1), S is S1.
```

Give a solution to avoid the recursive call `f(T, S1)` in both clauses without redefining the predicate. Justify the answer.

#### Rezolvare

```prolog
f([], -1).
f([H|T], S) :- f(T, S1), (S1 < H -> S is H;S is S1).
```

am folosit `operatorul if-then` care daca o conditie este adevarata executa prima parte,altfel executa partea de dupa `;`, deci daca S1 < H se va executa S is H,altfel S is S1.

---

### Exercitiul I.3

The LISP function `G` is defined by `(DEFUN G(L) (LIST (CAR L) (CAR L)))`. In order to rename the function `G` we execute `(SETQ Q 'G)` followed by `(SETQ P Q)`. What is the result of evaluating the form `(FUNCALL P '(A B C))`? Justify the answer.

#### Rezolvare

`(A A)`
Q este legat de simbolul G si P este legat de variabila Q,deci P este legat de simbolul G=>`FUNCALL P` devine `FUNCALL 'G` deci o sa imi execute functia G cu lista (A B C) care o sa imi returneze (A A)

---

### Exercitiul I.4

Consider the PROLOG predicate `f(list, integer)` with the flow model `(i, o)`:

Prolog

```
f([], 0).
f([H|T], S) :- f(T, S1), S1 is S - H.
```

Give the result of the evaluation `f([1, 2, 3, 4, 5, 6, 7, 8], S)`? Justify the answer.

`Eroare` pentru ca o sa ajunga la `[8]` si o sa faca `f([],S1]` care ne da S1 = 0 si o sa incerce 0 is S-8,ceea ce da eroare.

---

### Exercitiul II

Write a PROLOG program to generate **arrangements of k elements** from a list of integer numbers, with the **product P given**. Write the mathematical model, flow model and the meaning of all variables for each predicate used.

**Eg:** for list `L = [2, 5, 3, 4, 10]`, `k = 2` and `P = 20` $\Rightarrow$ `[[2, 10], [10, 2], [5, 4], [4, 5]]` (not necessarily in this order).

```prolog
select([H|T],H,T).
select([H|T],X,[H|Rez]):-
	select(T,X,Rez).

aranjamente(_,0,P,P,[]):-!.
aranjamente(L,K,P,PCur,[X|Rez]):-
	K>0,
	select(L,X,Lista),
	K1 is K-1,
	(X = 0 ->X is X;0 is P mod X),
	PCur2 is PCur * X,
	aranjamente(Lista,K1,P,PCur2,Rez).
aranj(L,K,P,Rez):-
	findall(A,aranjamente(L,K,P,1,A),Rez).

	
```

---

### Exercitiul III

An n-ary tree is represented in LISP: `(root subtree1 subtree2 ... )`. Write a function to **replace all nodes from the odd levels** in the tree with a given value **e**. The level of root is considered 0. Use a **MAP function**. Write the mathematical model and the meaning of all parameters for each function used.

**Eg:** for tree `(a (b (g)) (c (d (e)) (f)))` and `e = h` $\Rightarrow$ `(a (h (g)) (h (d (h)) (h)))`

```lisp
(defun replaced (tree e nivCurent)
	(cond
		((null tree) nil)
		
		(t
		  (cons
		    (if (oddp nivCurent) e (car tree))
				(mapcar #'(LAMBDA (subtree) (replaced subtree e (+ 1 nivCurent)))
					(cdr tree)
				)
			)
	  )
	)
)
```

$$

 \text{replaced(tree,e,nivCurent)} = \begin{cases}
\text{lista vida},& \text{daca tree este vid} \\
e\cup L,\text{unde L =}  replaced(l_{2},e,nivCurent+1) \cup replaced(l_{3},e,nivCurent+1),& \text{daca nivCurent \%2 = 1} \\
l_{1}\cup L,\text{unde L =}  replaced(l_{2},e,nivCurent+1) \cup replaced(l_{3},e,nivCurent+1),& \text{altfel}
\end{cases}

$$