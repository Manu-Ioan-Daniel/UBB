### Închiderea lui F

**Închiderea lui F**

(notată prin $F^+$)

este mulțimea tuturor DF implicate de $F$

$$F^+ = \{ f \mid F \Rightarrow f \}$$

### Axiomele lui Armstrong

Fie $\alpha, \beta, \gamma \subseteq R$

**Reflexivitate:** Dacă $\beta \subseteq \alpha$, atunci $\alpha \rightarrow \beta$

**Augmentare:** Dacă $\alpha \rightarrow \beta$, atunci $\alpha\gamma \rightarrow \beta\gamma$

**Tranzitivitate:** Dacă $\alpha \rightarrow \beta$ și $\beta \rightarrow \gamma$, atunci $\alpha \rightarrow \gamma$

---

### Reguli de inferență adiționale

**Reuniunea:**

Dacă $\alpha \rightarrow \beta$ și $\alpha \rightarrow \gamma$, atunci $\alpha \rightarrow \beta\gamma$

**Descompunerea:**

Dacă $\alpha \rightarrow \beta$, atunci $\alpha \rightarrow \beta'$ pentru orice $\beta' \subseteq \beta$

### Închiderea atributelor

Fie $\alpha \subseteq R$ și F o mulțime de DF satisfăcute pe R

- **Închiderea lui $\alpha$** (cu respectarea mulțimii F de DF), notată cu $\alpha^+$, este mulțimea de atribute ce sunt determinate funcțional din $\alpha$ pe baza dependențelor funcționale din F; adică
    
    $$\alpha^+ = \{ A \in R \mid F \Rightarrow \alpha \rightarrow A \}$$
    
- Se observă că $F \Rightarrow \alpha \rightarrow \beta$ dacă și numai dacă $\beta \subseteq \alpha^+$ (cu respectarea DF din F)
    

---

### Descompunere cu joncțiune fără pierderi

(Lossless - Join Decomposition)

O descompunere a R (având DF $F$) în $\{R_1, R_2, ..., R_n\}$ este o **descompunere cu joncțiuni fără pierderi** cu respectarea mulțimii $F$ dacă

$$\pi_{R1}(r) \otimes \pi_{R2}(r) \otimes ... \otimes \pi_{Rn}(r) = r$$

pentru orice instanță r din R ce satisface F.

---

### Teorema

**Teorema:** Descompunerea lui R (cu mulțimea F de DF) în $\{R_1, R_2\}$ este cu joncțiuni fără pierderi cu respectarea mulțimii F dacă și numai dacă:

$$F \Rightarrow R_1 \cap R_2 \rightarrow R_1$$
$$
sau
$$
$$F \Rightarrow R_1 \cap R_2 \rightarrow R_2$$

### Corolar

**Corolar:** Dacă $\alpha \rightarrow \beta$ este satisfăcută pe R și $\alpha \cap \beta = \emptyset$, atunci descompunerea lui R în $\{R - \beta, \alpha\beta\}$ este o descompunere cu joncțiuni fără pierderi.

### Proiecția dependențelor funcționale

- Proiecția mulțimii F pe $\alpha$ (notată prin $F_\alpha$) este mulțimea acelor dependențe din $F^+$ care implică doar atribute din $\alpha$, adică:
    
    $$F_\alpha = \{ \beta \rightarrow \gamma \in F^+ \mid \beta\gamma \subseteq \alpha \}$$

### Redundanța in DF

 Un atribut $A \in \alpha$ e redundant în DF $\alpha \rightarrow B$ dacă

$(F - \{\alpha \rightarrow B\}) \cup \{\alpha - A \rightarrow B\} \equiv F$

Pentru a verifica dacă $A \in \alpha$ e redundant în $\alpha \rightarrow B$,  verificam daca B $\in$ $(\alpha - A)^{+}$

---

### Redundanța in DF

O DF $f \in F$ e redundantă dacă F - {$f$} e echivalent cu F

Verificăm că $\alpha \rightarrow A$ e redundantă in F, calculând $\alpha^{+}$ pe baza F - $\{\alpha \rightarrow A\}$. Atunci $\alpha \rightarrow A$ e redundantă în F dacă $A \in \alpha^{+}$


### Acoperire minimală

O acoperire minimală pentru mulțimea F de dependente functionale este o multime G de dependente functionale pentru care:

1. Fiecare DF din G e de forma $\alpha \rightarrow A$
    
2. Pt fiecare DF $\alpha \rightarrow A$ din G, $\alpha$ nu are atribute redundante
    
3. Nu sunt DF redundante in G
    
4. G și F sunt echivalente
    



### Algoritm de calcul al acoperirii minimale pt F:

1. Folosim descompunerea pentru a obține DF cu 1 atribut în partea dreaptă.
    
2. Se elimină atributele redundante
    
3. Se elimină dependențele funcționale redundante
    


