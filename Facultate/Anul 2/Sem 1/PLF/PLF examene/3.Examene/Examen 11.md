### Exercitiul 1

Fie L o listă numerică și următoarea definiție de predicat `f(list, integer, integer)` având modelul de flux `(i, i, o)`:

```prolog
f([], _, 0).
f([H|T], V, P):- !,H > V , f(T, V, P1), P = P1 * H.
f([_|T], V, P):- f(T, V, P).

```

Precizați rezultatul următoarei întrebări: **Goal: f([2, 5, 6, 9, 7, 6, 9], 4, P)**.

#### Raspuns
`o sa afiseze false pentru ca 2>4 este fals,si cut ul nu il lasa sa continue pe clauza urmatoare->nu mai are nici o clauza pe care poate intra,deci cooked`

---

### Exercitiul 2

Se dă o listă formată din numere întregi și liste de numere întregi. Să se scrie un program Turbo PROLOG care șterge din fiecare sublistă elementul maxim. Explicați algoritmul propus.

```prolog

sterge([],[]):-!.
sterge([H|T],[RezH|Rez]):-
	is_list(H),
	maxim(H,Max),
	sterge_element(H,Max,RezH),
	sterge(T,Rez),!.
	
sterge([H|T],[H|Rez]):-
	sterge(T,Rez).
	
maxim([E],E):-!.
maxim([H|T],Rez):-
	maxim(T,RezT),
	(H < RezT -> Rez is RezT;Rez is H).
	
sterge_element([],_,[]):-!.	
sterge_element([H|T],H,Rez):-
	sterge_element(T,H,Rez),!.
sterge_element([H|T],E,[H|Rez]):-
	sterge_element(T,E,Rez).
		
```

#### Exemplu

pentru lista `[1, [2, 4, 3, 4], 5, [6, 7]]` $\Rightarrow$ `[1, [2, 3], 5, [6]]`.

---

### Exercitiul 3

Pentru o valoare $N$ dată, să se genereze lista permutărilor cu elementele $N, N+1, ..., 2N-1$ având proprietatea că valoarea absolută a diferenței dintre două valori consecutive din permutare este $\le 2$. Explicați algoritmul propus.

[[Examen 4#Subiectul II]]

---

### Exercitiul 4

Dați două implementări recursive pentru următoarea cerință: să se substituie valorile numerice cu o valoare $e$ dată la orice nivel al unei liste neliniare.

#### Exemplu

pentru lista `(1 d (2 f (3)))`, $e=0$ rezultă lista `(0 d (0 f (0)))`.

#### Implementare 1

```lisp
(defun replace-all-map (tree new-val)
  (cond
    ((numberp tree) new-val)
    ((listp tree)
     (mapcar #'(lambda (subtree) 
                 (replace-all-map subtree new-val)) 
             tree))
    (t tree)))

(print (replace-all-map '(1 (b (2)) (c (d (e)) (f))) 0))
```


#### Implementare 2

```lisp
(defun replace-all (L E)
	(cond
		((null L) nil)
		((listp (car L))
			(cons (replace-all (car L) E)
				(replace-all (cdr L) E)
			)
		)
		((numberp (car L))
			(cons
				E
				(replace-all (cdr L) E)
			)
		)
		(t
			(cons
				(car L)
				(replace-all (cdr L) E)
			)
		)
	)
)


```

---

### Exercitiul 5

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care **atomul numeric maxim de pe nivelurile impare este par** – nivelul superficial al listei se consideră 1. Prelucrarea se va face folosind o funcție MAP.

#### Exemplu

 `(A (B 2) (1 C 4) (1 (6 F)) ((G 4) 6))` are 3 astfel de subliste: lista, `(B 2)`, `(1 C 4)` și `((G 4) 6)`.

```lisp

(defun max-odd-levels (l k)
  (cond
    ((numberp l) (if (oddp k) l -1)) 
    ((atom l) -1)             
    (t (apply #'max 
              (mapcar #'(lambda (v) (max-odd-levels v (1+ k))) 
                      l)))))
                      
(defun count-sublists (l)
  (if (atom l) 
      0
      (let ((m (max-odd-levels l 1)))
        (+  (if (evenp m) 
                1 
                0
            )
           (apply #'+ (mapcar #'count-sublists l))
        )
      )
  )
)
```
