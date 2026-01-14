(defun maxim (lista max)
  (cond
    ((null lista) max)
    ((numberp (car lista))
      (if (< (car lista) max)
          (maxim (cdr lista) max)
          (maxim (cdr lista) (car lista))
      )
    )
    (t
      (maxim (cdr lista) max)
    )
  )
)

(defun aplatizare (lista)
  (cond
    ((null lista) nil)
    ((atom lista) (list lista))
    (t
      (mapcan #'aplatizare lista)
    )
  )
)

(defun maxim_neliniara(lista)
  (maxim (aplatizare lista) -100)
)

(print (maxim_neliniara '(1 2 3 (4 5 (7)))))