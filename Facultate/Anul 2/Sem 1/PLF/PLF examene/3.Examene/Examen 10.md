### Exercitiul 1

Se definește în LISP funcția `INC` prin `(DEFUN INC(X) (+ 1 X))`. Variabila `A` este inițializată prin `(SETQ A 1)`. În scopul incrementării valorii variabilei `A` se efectuează `(INC A)`. Care va fi efectul acestei evaluări? Explicați ce se întâmplă în timpul evaluării `(INC A)`. Justificați răspunsul și dați o soluție în această situație.
#### Rezolvare

Valoarea returnata de `(INC A)` va fi `2` dar valoarea variabilei A nu se schimba in urma apelului acestei functii.Solutia este sa facem `(SETQ A (INC A))`.

---

### Exercitiul 2

Dați două implementări PROLOG pentru calculul sumei primelor $N$ numere naturale divizibile cu 3. De exemplu, pentru $N=4$ rezultatul este 30. Explicați fiecare implementare propusă.

#### Rezolvare
##### Implementare 1

```prolog
suma(0,0):-!.
suma(N,S):-
	N>0,
	N1 is N-1,
	suma(N1,S2),
	S is 3*N + S2.
```

##### Implementare 2

```prolog
suma(0,Scur,Scur):-!.
suma(N,Scur,S):-
	N>0,
	N1 is N-1,
	Scur2 is Scur + N*3,
	suma(N1,Scur2,S).
suma(N,S):-suma(N,0,S).
```

---

### Exercitiul 3

Să se scrie un program PROLOG care generează lista aranjamentelor de $k$ elemente dintr-o listă de numere întregi, având o sumă $S$ dată. Explicați algoritmul propus.

[[Examen 1#Subiectul II]]

#### Exemplu

pentru lista `[6, 5, 3, 4]`, $k=2$ și $S=9 \Rightarrow [[6, 3], [3, 6], [5, 4], [4, 5]]$ (nu neapărat în această ordine).

---

### Exercitiul 4

Să se scrie un program LISP și să se deducă complexitatea pentru următoarea cerință: Se dă o listă liniară numerică și se cere să se scrie de două ori elementele din $N$ în $N$. De exemplu, pentru lista `(1 2 3 4 5)` și $N=2$ rezultă lista `(1 2 2 3 4 4 5)`.

[[Examen 8#Exercitiul 4]]

---

### Exercitiul 5

Se dă o listă neliniară. Să se scrie un program LISP pentru determinarea numărului de subliste de la orice nivel pentru care **primul atom (la orice nivel) este nenumeric**. Prelucrarea se va face folosind o funcție MAP.

[[Examen 6#Subiectul 5]]