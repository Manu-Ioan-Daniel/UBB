(defun helperListaNoduriNivel (l nivel nivelCurent)
  (cond
    ((null l) nil)
    ((= nivel nivelCurent) (list (car l)))
    (t
     (append
      (helperListaNoduriNivel (cadr l) nivel (+ nivelCurent 1))
      (helperListaNoduriNivel (caddr l) nivel (+ nivelCurent 1))))))

(defun listaNoduriNivel (l nivel)
  (helperListaNoduriNivel l nivel 0))


(defun helperNivelMaximNoduri (l nivelCurent nivelMax lungMax)
  (cond
    ((null (listaNoduriNivel l nivelCurent)) nivelMax)
    ((> (length (listaNoduriNivel l nivelCurent)) lungMax)
      (helperNivelMaximNoduri l (1+ nivelCurent ) nivelCurent (length (listaNoduriNivel l nivelCurent))))
    (t
     (helperNivelMaximNoduri l (+ nivelCurent 1) nivelMax lungMax))))

(defun nivelMaximNoduri (l)
  (helperNivelMaximNoduri l 0 0 0))

(defun main (l)
  (print (nivelMaximNoduri l))
  (print (listaNoduriNivel l (nivelMaximNoduri l))))
  
(main '(A (B (F (I) (J))) (C (D (G) (H)) (E))))

