
### 1. Ce dracu' este `SETF`?

Dacă `SETQ` funcționează doar pentru variabile simple, **`SETF`** (prescurtare de la **Set Field**) este "tăticul" atribuirii. El poate modifica **locații de memorie**, nu doar nume de variabile.

Imaginează-ți că ai o listă `X = (5 6 7)`.

- Dacă vrei să schimbi toată lista, folosești `SETQ X '(1 2 3)`.
    
- Dacă vrei să schimbi doar al doilea element (pe 6), nu poți folosi SETQ pe o funcție. Folosești SETF:
    
    (setf (cadr x) 2) $\rightarrow$ acum X este (5 2 7).
    

**Regulă simplă:** `SETF` se uită la primul argument, vede "unde" în memorie pointează acesta (un element de listă, un câmp de structură, etc.) și pune valoarea acolo.
