(defun elimina-din-n-in-n (lst n)
  (helperElimina lst n 1))

(defun helperElimina (lst n i)
        (cond
               ((null lst) nil)
               ((= i n) (helperElimina (cdr lst) n 1))
               (t (cons (car lst) (helperElimina (cdr lst) n (1+ i))))))
(defun vale (lst)
  (helperVale lst 0))

(defun helperVale (l state)
  (cond
    ((or (null l) (null (cdr l)))
     (if (= state 1) t nil))
    ((= state 0)
     (cond
       ((> (car l) (cadr l)) (helperVale (cdr l) 0))
       ((< (car l) (cadr l)) (helperVale (cdr l) 1))
       (t nil)))
    ((= state 1)
     (if (< (car l) (cadr l))
         (helperVale (cdr l) 1)
         nil))))



(defun min-lista (lst)
  (helperMin lst most-positive-fixnum))

(defun helperMin (l current-min)
  (cond
    ((null l) current-min)
    ((listp (car l))
     (helperMin (cdr l) (helperMin (car l) current-min)))
    ((numberp (car l))
     (helperMin (cdr l) (min (car l) current-min)))
    (t (helperMin (cdr l) current-min))))



(defun sterge-max (lst)
  (helper-sterge lst (helper-get-max lst most-negative-fixnum)))

(defun helper-get-max (l current-max)
  (cond
    ((null l) current-max)
    ((not (numberp (car l))) (helper-get-max (cdr l) current-max))
    ((> (car l) current-max) (helper-get-max (cdr l) (car l)))
    (t (helper-get-max (cdr l) current-max))))

(defun helper-sterge (l max)
  (cond
    ((null l) nil)
    ((and (numberp (car l)) (= (car l) max)) (helper-sterge (cdr l) max))
    (t (cons (car l) (helper-sterge (cdr l) max)))))


(print (elimina-din-n-in-n '(1 2 3 4 5 6 7 8 9) 3))
;; Așteptat: (1 2 4 5 7 8)     ; elimină 3, 6, 9
(print (vale '(9 7 5 3 4 6 8)))
;; Așteptat: T      ; descrește până la 3, apoi crește
(print (vale '(5 4 4 6 7)))
;; Așteptat: FALSE  ; egalitatea 4–4 strică valea

(print (min-lista '(10 (5 2) (20 (1 30)) 7)))
;; Așteptat: 1
(print (sterge-max '(1 5 a 3 5 2 5 4)))
;; Așteptat: (1 3 2 4)    ; maximul este 5, toate 5 sunt eliminate

