### Chei Primare 

- O mulțime de atribute reprezintă o **cheie** a unei relații dacă:
    
    1. Nu există două tuple care au aceleași valori pentru toate atributele
        
        **ȘI**
        
    2. Aceste lucru nu este adevărat pentru nici o submulțime a cheii
        
- Dacă a 2-a afirmație este falsă $\rightarrow$ **super cheie**
    
- Daca există >1 cheie pentru o relație $\rightarrow$ **chei candidat**
    
- Una dintre cheile candidat este selectată ca **cheie primară**

- O mulțime de atribute $\alpha$ reprezintă o **supercheie** a relației R (având mulțimea de DF F) dacă
    
    $$F \Rightarrow \alpha \rightarrow R$$
    
- O mulțime de atribute $\alpha$ e o **cheie** a relației R dacă
    
    (1) $\alpha$ este o supercheie, și
    
    (2) nici o submulțime a lui $\alpha$ nu e supercheie
    
    (adică, pentru fiecare $\beta \subset \alpha, \beta \rightarrow R \notin F^+$)
    
- Un atribut $A \in R$ se numește atribut **prim** dacă A face parte dintr-o cheie a lui R; în caz contrar, A se numește atribut **neprim**.
    