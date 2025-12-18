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
  
(main '(1 (2 (4 (6 (7 (8)(9 (10 (11))))))(5)) (3 (12) (13 (14) (15 (16 (17 (18 (19) (20)))))))))

