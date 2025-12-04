(defun elimina-din-n-in-n (lst n)
  (labels ((helper (l i)
             (cond
               ((null l) nil)
               ((= i n) (helper (cdr l) 1))
               (t (cons (car l) (helper (cdr l) (1+ i)))))))
    (helper lst 1)))


(defun vale (lst)
  (labels ((helper (l state)
             (cond
               ((or (null l) (null (cdr l)))
                (if (= state 1) t 'false))
               ((= state 0)
                (let ((a (car l))
                      (b (cadr l)))
                  (cond
                    ((> a b) (helper (cdr l) 0))
                    ((< a b) (helper (cdr l) 1))
                    (t 'false))))
               ((= state 1)
                (let ((a (car l))
                      (b (cadr l)))
                  (if (< a b)
                      (helper (cdr l) 1)
                      'false))))))
    (helper lst 0)))


(defun min-lista (lst)
  (labels ((helper (l current-min)
             (cond
               ((null l) current-min)
               ((listp (car l))
                (helper (cdr l) (helper (car l) current-min)))
               ((numberp (car l))
                (helper (cdr l) (min (car l) current-min)))
               (t (helper (cdr l) current-min)))))
    (helper lst most-positive-fixnum)))


(defun sterge-max (lst)
(labels ((get-max (l current-max)
          (cond 
            ((null l) current-max)
            ((not (numberp (car l))) (get-max (cdr l) current-max))
            ((> (car l) current-max) (get-max (cdr l) (car l)))
            (T (get-max (cdr l) current-max))))
         
         (sterge (l max)
          (cond 
            ((null l) nil)
            ((and (numberp (car l)) (= (car l) max)) (sterge (cdr l) max))
            (T (cons (car l) (sterge (cdr l) max))))))
  
  (let ((max-val (get-max lst most-negative-fixnum)))
    (if (/= max-val most-negative-fixnum)
        (sterge lst max-val)
        lst))))

	