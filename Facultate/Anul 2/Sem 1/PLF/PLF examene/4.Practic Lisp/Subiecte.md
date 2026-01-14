### Problema 1

Dându-se o listă neliniară, se cere să se elimine din listă toate elementele care se repetă.
#### **Exemplu**
Input: `(1 (2 c f 1 (d 2 c 4) e))` Output: `((f (d 4) e))`

#### Rezolvare

```lisp
(defun numara (lista e)
  (cond
    ((null lista) 0)
    ((atom lista)
      (if (eq lista e) 1 0)
    )
    (t
      (apply #'+ (mapcar #'(LAMBDA (subL) (numara subL e)) lista))
    )
  )
)

(defun make_set_aux(lista listaCopy)
  (cond
    ((null lista) nil)
    ((listp (car lista))
      (cons (make_set_aux (car lista) listaCopy) (make_set_aux (cdr lista) listaCopy))
    )
    ((> (numara listaCopy (car lista)) 1)
      (make_set_aux (cdr lista) listaCopy)
    )
    (t
      (cons (car lista) (make_set_aux (cdr lista) listaCopy))
    )
  )
)
(defun make_set(lista)
  (make_set_aux lista lista)
)

(print (make_set '(1 (2 c f 1 (d 2 c 4) e))))

```

### Problema 2

Definiți o funcție care determină suma a două numere scrise în reprezentare de listă, fără a converti listele în numere.
#### **Exemplu**

`(1 1 1) (2 2 2) -> (3 3 3)`

#### Rezolvare

```lisp
(defun invers (lista)
  (cond
    ((null lista) nil)
    ((atom lista) lista)
    (t
      (append (invers (cdr lista)) (list (car lista)))
    )
  )
)

(defun suma_aux (lista1 lista2 r)
  (cond
    ((null lista1) 
      (if (> r 0)
        (list r)
        nil
      )
    )
    (t 
      (cons (mod (+ (car lista1) (car lista2) r) 10) (suma_aux (cdr lista1) (cdr lista2) (floor (+ (car lista1) (car lista2)) 10)))
    )
  )
)

(defun suma (lista1 lista2)
  (invers (suma_aux (invers lista1) (invers lista2) 0))
)
(print (suma '(5 4 9) '(9 7 1)))

```