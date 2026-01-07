În LISP, `LAMBDA` este folosit pentru a crea o **funcție anonimă** (o funcție fără nume), pe care o poți folosi pe loc. 
### Sintaxa de bază

```lisp
(LAMBDA (parametru1 parametru2 ...)
    corp-functie)
```

Dacă vrei să o folosești ca pe un obiect (pentru a o trimite ca argument), se folosește prefixul `#'` (care este o prescurtare pentru funcția `FUNCTION`):

```lisp
#'(LAMBDA (x) (* x x))
```

---

### Componentele sintaxei

1. **Cuvântul cheie `LAMBDA`**: Îi spune interpretorului că urmează o definiție de funcție.
    
2. **Lista de parametri**: O listă (poate fi și goală `()`) care conține variabilele pe care funcția le va folosi.
    
3. **Corpul funcției**: Una sau mai multe instrucțiuni care vor fi executate. Rezultatul ultimei instrucțiuni este cel returnat de funcție.
    

---

### Cum se utilizează?

#### 1. Apelată direct

Poți pune o expresie lambda direct în poziția de funcție (prima poziție într-o listă):


```lisp
((LAMBDA (x y) (+ x y)) 5 10)
;; Rezultat: 15
```

Aici, `x` devine 5, `y` devine 10, iar corpul funcției le adună.

#### 2. Folosită cu `FUNCALL`

Este modul în care funcționează exercițiul tău:


```lisp
(setq operatie #'(LAMBDA (n) (* n 2)))
(funcall operatie 7)
;; Rezultat: 14
```

#### 3. Folosită cu `MAPCAR` (cel mai des întâlnit caz)

Dacă vrei să dublezi toate elementele unei liste:

``` lisp
(mapcar #'(LAMBDA (x) (* x 2)) '(1 2 3))
;; Rezultat: (2 4 6)
```

---

### De ce apare `#'` în fața lui `LAMBDA`?

În Common Lisp, funcțiile și variabilele stau în "sertare" diferite.

- Dacă scrii simplu `(LAMBDA ...)`, Lisp vede o listă.
    
- Dacă scrii `#'(LAMBDA ...)`, simbolul `#'` îi spune: _"Nu trata asta ca pe o listă obișnuită, ci trateaz-o ca pe o **funcție** (obiect executabil)"_.