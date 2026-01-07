
### Subiectul 1

Dati două implementări Turbo Prolog pentru calculul lui $x^n$. Explicați fiecare implementare propusă.

#### Implementare 1
```prolog

putere(_,0,1):-!.
putere(X,N,Rez):-
	N1 is N-1,
	putere(X,N1,RezT),
	Rez is RezT*X.

```
#### Implementare 2

```prolog
% putere_rapida(Baza, Exponent, Rezultat)
putere_r(_, 0, 1) :- !.
putere_r(X, N, Rez) :-
    N > 0,
    0 is N mod 2, !,
    X2 is X * X,
    N2 is N // 2,
    putere_r(X2, N2, Rez). 
    
putere_r(X, N, Rez) :-
    N > 0,
    N1 is N - 1,
    putere_r(X, N1, Rez1),
    Rez is X * Rez1.  
```
### Subiectul 2

Dându-se o listă formată din numere întregi, să se genereze lista permutărilor având proprietatea că valoarea absolută a diferenței dintre două valori consecutive din permutare este $\le 3$. Explicați algoritmul propus.

Exemplu: pentru lista $L=[2,7,5] \Rightarrow [[2,5,7],[7,5,2]]$ (nu neapărat în această ordine).

[[Examen 4#Subiectul II]]
### Subiectul 3

Fie următoarea definiție de funcție:

```Lisp
(DEFUN F(L) (COND ((NULL L) 0)
                  ((> (F (CAR L)) 2) (+ (CAR L) (F (CDR L))))
                  (T (F (CAR L)))))
```

Dați o soluție pentru a evita dublul apel recursiv `(F (CAR L))`. Nu se vor folosi SET, SETQ, SETF.

[[Examen 3#Subiectul I#Exercitiul 1|Vezi exercitiu asemanator]]

### Subiectul 4

Un arbore n-ar se reprezintă în LISP astfel: (nod subarbore1 subarbore2 ...). Se cere să se determine calea de la rădăcină către un nod dat. Indicație: se va putea folosi o funcție MAP.

Exemplu: pentru arborele `(a (b (g)) (c (d (e)) (f)))`

- **a)** nod=$e \Rightarrow (a, c, e)$
    
- **b)** nod=$v \Rightarrow ()$
    
[[Examen 4#Subiectul III]]
### Subiectul 5

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care primul atom numeric (la orice nivel) este impar. Prelucrarea se va face folosind o funcție MAP.

#### Exemplu: 
lista `(A (B 2) (1 C 4) (D 1 (5 F)) ((G 4) 6))` are 3 astfel de subliste: (`1 C 4)` și `(D 1 (5 F))` și `(5 F)`