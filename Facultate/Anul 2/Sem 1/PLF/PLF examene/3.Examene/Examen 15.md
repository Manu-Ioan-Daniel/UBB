
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
	    
	    ) (FUNCALL F (CAR L)))
    )
  )
)

```

---

### Exercitiul I.2

Let `L` be a numerical list and consider the following PROLOG definition for the predicate `f(list, integer)`, with the flow model `(i, o)`:

Prolog

```
f([], -1).
f([H|T], S) :- f(T, S1), S1 < H, !, S is H.
f([_|T], S) :- f(T, S1), S is S1.
```

Give a solution to avoid the recursive call `f(T, S1)` in both clauses without redefining the predicate. Justify the answer.

---

### Exercitiul I.3

The LISP function `G` is defined by `(DEFUN G(L) (LIST (CAR L) (CAR L)))`. In order to rename the function `G` we execute `(SETQ Q 'G)` followed by `(SETQ P Q)`. What is the result of evaluating the form `(FUNCALL P '(A B C))`? Justify the answer.

---

### Exercitiul I.4

Consider the PROLOG predicate `f(list, integer)` with the flow model `(i, o)`:

Prolog

```
f([], 0).
f([H|T], S) :- f(T, S1), S1 is S - H.
```

Give the result of the evaluation `f([1, 2, 3, 4, 5, 6, 7, 8], S)`? Justify the answer.

---

### Exercitiul II

Write a PROLOG program to generate **arrangements of k elements** from a list of integer numbers, with the **product P given**. Write the mathematical model, flow model and the meaning of all variables for each predicate used.

**Eg:** for list `L = [2, 5, 3, 4, 10]`, `k = 2` and `P = 20` $\Rightarrow$ `[[2, 10], [10, 2], [5, 4], [4, 5]]` (not necessarily in this order).

---

### Exercitiul III

An n-ary tree is represented in LISP: `(root subtree1 subtree2 ... )`. Write a function to **replace all nodes from the odd levels** in the tree with a given value **e**. The level of root is considered 0. Use a **MAP function**. Write the mathematical model and the meaning of all parameters for each function used.

**Eg:** for tree `(a (b (g)) (c (d (e)) (f)))` and `e = h` $\Rightarrow$ `(a (h (g)) (h (d (h)) (h)))`