
### Subiectul I

#### Exercitiul 1

1. Se definește în LISP funcția `Fc` prin:

```lisp
(DEFUN Fc(x) #'(LAMBDA (y) (MAPCAR y (CDDR x))))
```

Care este rezultatul evaluării formei `(FUNCALL (Fc (QUOTE ((1) (2) (3) (4)))) #'CAR)`? 
##### Raspuns
**(3 4)**
#### Exercitiul 2

2. Definim în PROLOG predicatul `f(list, list)` având modelul de flux $(i, o)$:

``` prolog
f([], []).
f([H|T], [H|Rez]) :- H > 0, f(T, Rez), !.
f([_|T], Rez) :- f(T, Rez).
```

Care este rezultatul evaluării `f([2,-1,3,-2,7], Rez)`? Justificați.
##### Raspuns

[2,3,7]
#### Exercitiul 3

3. Se definește în LISP funcția `G` prin:

``` lisp
(DEFUN G(L) (LIST (CAR L) (CADDR L)))
```

Fie următoarele evaluări `(SETQ Q 'G)` urmată de `(SETQ P 'Q)`. Ce se va obține prin evaluarea formei `(APPLY (EVAL P) (LIST '(A B C)))`? Justificați.

##### Raspuns

Rezultatul evaluării este **`(A C)`**, deoarece variabila `P` pointează către `Q`, care la rândul său pointează către funcția `G`, astfel încât `(EVAL P)` returnează simbolul `G`, iar `APPLY` apelează această funcție pe lista `(A B C)`, extrăgând primul și al treilea element.

#### Exercitiul 4

Definim în PROLOG predicatul `f(list, integer)` cu modelul de flux $(i, o)$:

```prolog
f([], 0).
f([H|T], S) :- f(T, S1), S1 is S - H.
```
Care este rezultatul evaluării `f([1,2,3,4,5,6,7,8], S)`? Justificați.

##### Raspuns

Eroare.Cand se ajunge la penultimul element,o sa incerce 0 is S- (penultimul element), ceea ce da o eroare.

---

### Subiectul II

Să se scrie un program PROLOG care generează lista aranjamentelor de $k$ elemente dintr-o listă de numere întregi, având produsul $P$ dat. Se vor scrie modelele matematice și modelele de flux pentru predicatele folosite.
#### Exemplu
Pentru lista `[2, -5, 3, -4, 10]`, $k=2$ și $P=20 \Rightarrow [[2,10],[10,2],[-5,-4],[-4,-5]]$ (nu neapărat în această ordine).

#### Raspuns

```
%selecter(i,o,o)

selecter([H|T],H,T).
selecter([H|T],X,[H|Rest]):-selecter(T,X,Rest).
selecter(E,[E|T],T).
selecter(X,[H|T],[H|Rest]):-selecter(X,T,Rest).

%aranjamente(i,i,i,i,o)

aranjamente(P,P,0,_,[]):-!.
aranjamente(P,Pcur,K,List,[X|Rest]):-
    K>0,
    selecter(X,List,Remaining),
    K1 is K-1,
    X=\=0,
    P=\=0,
    0 is P mod X,
    Pcurdat is Pcur*X,
    aranjamente(P,Pcurdat,K1,Remaining,Rest),!.
aranjamente(0,Pcur,K,List,[X|Rest]):-
    K>0,
    selecter(X,List,Remaining),
    K1 is K-1,
    Pcurdat is Pcur*X,
    aranjamente(0,Pcurdat,K1,Remaining,Rest).

%solutie(i,i,i,o)

solutie(P,K,Lista,Rez):-
    findall(Arr, aranjamente(P,1,K,Lista,Arr),Rez).
```

$$
selecter([x_{1},x_{2},x_{3},\dots x_{n}]) = \begin{cases}
(E,[x_{2},x_{3},\dots x_{n}]), & \text{ daca } x_{1}=E \\
(E, [x_{1}\cup L]), & \text{ daca } (E,L) = selecter(x_{2},x_{3},\dots x_{n})
\end{cases}
$$
$$
aranjamente(P,P_{cur},K,[x_{1},x_{2},x_{3},\dots x_{n}]) = \begin{cases}
\text{lista vida }, & \text{daca } P = P_{cur} \text{ si } K=0 \\
X \cup aranjamente(P,P_{cur} \cdot  X,K-1,L), & \text{daca } (X,L) = selectez([x_{1},x_{2},\dots,x_{n}]) \text{ si } K\neq0 \text{ si } X\neq0 \text{ si } P\neq0 \text{ si } P \% X = 0 \\
X \cup aranjamente(P,P_{cur} \cdot  X,K-1,L), & \text{daca } (X,L) = selectez([x_{1},x_{2},\dots,x_{n}]) \text{ si } P=0
\end{cases}
$$

$$
solutie(P,K,L) = \cup \text{ }aranjamente(P,1,K,L)
$$


---
### Subiectul III

Un arbore n-ar se reprezintă în LISP astfel: `(nod subarbore1 subarbore2 ...)`. Se cere să se înlocuiască nodurile de pe nivelurile impare din arbore cu o valoare $e$ dată. Nivelul rădăcinii se consideră a fi $0$. Se va folosi o funcție `MAP`.

#### Exemplu
pentru arborele `(a (b (g)) (c (d (e)) (f)))` și $e=h \Rightarrow (a (h (g)) (h (d (h)) (f)))$.
#### Rezolvare

```lisp
(defun replace-odd-levels (tree e nivelCur)
  (cond
    ((null tree) nil)
    (t
      (cons 
        (if (oddp nivelCur) e (car tree))
        (mapcar #'(lambda (subtree) (replace-odd-levels subtree e (+ 1 nivelCur))) (cdr tree)))))
)

(print(replace-odd-levels '(a (b (g)) (c (d (e)) (f))) 'h 0))
```
##### Output
`Output:(A (H (G)) (H (D (H)) (F)))`