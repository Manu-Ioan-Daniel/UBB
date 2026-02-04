
- _Obține triplete (cu vârsta studenților + alte două expresii) pentru studenții al căror nume începe și se termină cu B și conține cel puțin trei caractere._
    
```SQL
SELECT S.age, age1=S.age-5, 2*S.age AS age2
FROM   Students S
WHERE  S.name LIKE 'B_%B'
```

- **AS** și **=** sunt două moduri de redenumire a câmpurilor în rezultat.
    
- **LIKE** e folosit pentru comparatii pe siruri de caractere. `_` reprezinta orice caracter si `%` stands reprezinta 0 sau mai multe caractere arbitrare.


##  **Operatori de agregare**


- COUNT (*)

- COUNT ( [DISTINCT] A)

- SUM ( [DISTINCT] A)

- AVG ( [DISTINCT] A)

- MAX (A)

- MIN (A)
###  GROUP BY / HAVING (Imaginea 8)




```SQL
SELECT [DISTINCT] target-list
FROM   relation-list
WHERE  qualification
GROUP BY grouping-list
HAVING group-qualification
```

- Un _grup_ este o mulțime de tupluri care au aceeași valoare pentru toate atributele din _grouping-list_.
    
- _target-list_ conține (i) <u>nume de atribute</u> sau (ii) termeni ce utilizează operatori de agregare (e.g., MIN (_S.age_)).
    
    - numele de atribute (i) trebuie să fie o submulțime a _grouping-list_.
        
    - Intuitiv, fiecare tuplu din rezultat corespunde unui _grup_, și toate atributele vor avea o singură valoare per grup.


### Sortarea rezultatului interogărilor (Imaginea 9)

- ORDER BY _column_ [ ASC | DESC] [...]
    
