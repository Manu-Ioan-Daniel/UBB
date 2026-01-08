
## 2. Apostroful

### Ce face

`'` oprește evaluarea unei forme și o tratează ca **date**.

`'(+ 1 2) ; => (+ 1 2)`

Fără quote:

`(+ 1 2) ; => 3`

### Echivalent

`'a  ≡  (quote a)`

### Când îl folosești

- când vrei **liste ca date**
    
- când vrei **simboli**, nu valori
    

`(setq x '+) ; x este simbolul +`

---

## 3. `setq` – setare simplă de variabilă

### Ce face

Atribuie o valoare unei variabile.

`(setq x 10) (setq y (+ x 5)) ; y = 15`

### Limitări

- doar simboluri simple
    
- nu funcționează pe structuri complexe
    

---

## 4. `setf` – setare generalizată

### Ce face

Poate seta **orice loc modificabil**.

`(setf x 20) (setf (car lst) 99) (setf (aref v 0) 7) (setf (gethash 'a h) 42)`

### Diferență față de `setq`

- `setq` → doar variabile
    
- `setf` → orice structură
    

---

## 5. `eval` – evaluare de cod la runtime

### Ce face

Execută o formă Lisp construită dinamic.

`(eval '(+ 2 3)) ; => 5`

### De ce e periculos

`(eval user-input) ; risc mare`

### Regula de aur

> Dacă poți evita `eval`, evită-l.

---

## 6. `funcall` – apel de funcție

### Ce face

Apelează o funcție care este stocată într-o variabilă sau expresie.

`(funcall #'+ 2 3) ; => 5`

Cu funcție într-o variabilă:

`(setq f #'*) (funcall f 3 4) ; => 12`

---

## 7. `apply` – apel cu listă de argumente

### Ce face

Apelează o funcție unde argumentele sunt într-o listă.

`(apply #'+ '(1 2 3 4)) ; => 10`

### Diferență față de `funcall`

`(funcall #'+ 1 2 3) (apply   #'+ '(1 2 3))`

---

## 8. `#` – reader macro

`#` este interpretat de **reader**, nu de evaluator.

### Exemple

`#(1 2 3) ; vector #\a      ; caracter`

---

## 9. `#'` – FUNCTION QUOTE

### Ce face

Returnează **obiectul funcție**, nu simbolul.

`'+    ; simbol #'+   ; funcție`

### De ce contează

`(funcall '+ 1 2)  ; ERROR (funcall #'+ 1 2) ; => 3`

### Regula practică

- `'` → simbol / date
    
- `#'` → funcție
    

---

## 10. Rezumat scurt

|Concept|Rol|
|---|---|
|`'`|oprește evaluarea|
|`setq`|setează variabile|
|`setf`|setează orice|
|`eval`|execută cod|
|`funcall`|apelează funcție|
|`apply`|funcție + listă|
|`#`|reader macro|
|`#'`|