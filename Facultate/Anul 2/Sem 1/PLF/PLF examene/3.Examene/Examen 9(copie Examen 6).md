
[[Examen 6|Vezi rezolvarea aici]]

### Exercitiul 1

Dați două implementări Turbo Prolog pentru calculul lui $x^n$. Explicați fiecare implementare propusă.



---

### Exercitiul 2

Dându-se o listă formată din numere întregi, să se genereze lista permutărilor având proprietatea că valoarea absolută a diferenței dintre două valori consecutive din permutare este $\le 3$. Explicați algoritmul propus.

#### Exemplu

pentru lista $L = [2, 7, 5] \Rightarrow [[2, 5, 7], [7, 5, 2]]$ (nu neapărat în această ordine).




---

### Exercitiul 3

Fie următoarea definiție de funcție:

Lisp

```
(DEFUN F(L) (COND ((NULL L) 0)
                  ((> (F (CAR L)) 2) (+ (CAR L) (F (CDR L))))
                  (T (F (CAR L)))))
```

Dați o soluție pentru a evita dublul apel recursiv `(F (CAR L))`. Nu se vor folosi `SET`, `SETQ`, `SETF`.

---

### Exercitiul 4

Un arbore n-ar se reprezintă în LISP astfel: `(nod subarbore1 subarbore2 ...)`. Se cere să se determine calea de la rădăcină către un nod dat. Indicație: se va putea folosi o funcție MAP.

#### Exemplu

pentru arborele (a (b (g)) (c (d (e)) (f)))

a) nod = e $\Rightarrow$ (a, c, d, e)

b) nod = v $\Rightarrow$ ()

---

### Exercitiul 5

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care primul atom numeric (la orice nivel) este impar. Prelucrarea se va face folosind o funcție MAP.

#### Exemplu

lista `(A (B 2) (1 C 4) (D 1 (5 F)) ((G 4) 6))` are 3 astfel de subliste: `(1 C 4)`, `(D 1 (5 F))` și `(5 F)`.