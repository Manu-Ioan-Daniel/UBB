
În LISP, funcțiile din familia **MAP** sunt folosite pentru a aplica o funcție asupra elementelor uneia sau mai multor liste. Diferența principală dintre ele constă în **cum construiesc rezultatul final**.
### 1. MAPCAR

Este cea mai utilizată. Aplică funcția pe fiecare element și returnează o **listă nouă** cu rezultatele.

- **Sintaxă:** `(MAPCAR #'<funcție> <lista1> <lista2> ...)`

- **Exemplu:**

```lisp
   (mapcar #'(lambda (x) (* x 10)) '(1 2 3))
   ;; Rezultat: (10 20 30)
```
_Dacă dai mai multe liste, funcția trebuie să accepte tot atâția parametri:_

``` lisp
   (mapcar #'+ '(1 2 3) '(10 20 30))
   ;; Rezultat: (11 22 33)
   ```

### 2. MAPCAN

Este similară cu `MAPCAR`, dar se așteaptă ca funcția aplicată să returneze **liste**, pe care apoi le "lipește" (le concatenează) folosind `NCONC`. Este utilă pentru filtrare și transformare simultană.

- **Sintaxă:** `(MAPCAN #'<funcție> <lista>)`

- **Exemplu:** (Vrem să dublăm doar numerele pare și să eliminăm restul)

  ```lisp
   (mapcan #'(lambda (x) (if (evenp x) (list x x) nil)) '(1 2 3 4))
   ;; Rezultat: (2 2 4 4)
   ;; Explicație: Pentru 1 returnează NIL (nimic), pentru 2 returnează (2 2).
   ```


---

### 3. MAPLIST

Spre deosebire de `MAPCAR` (care ia elementele pe rând), `MAPLIST` aplică funcția pe **toată lista**, apoi pe **(cdr lista)**, apoi pe **(cddr lista)** și tot așa.

- **Sintaxă:** `(MAPLIST #'<funcție> <lista>)`

- **Exemplu:**

 ```lisp
   (maplist #'(lambda (x) x) '(A B C))
   ;; Rezultat: ((A B C) (B C) (C))
   ```

