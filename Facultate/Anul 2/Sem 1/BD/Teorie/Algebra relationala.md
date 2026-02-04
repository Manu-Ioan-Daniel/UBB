### Proiecția
$L = (a_{1}, \dots, a_{n})$ este o listă de atribute (sau _o lista de coloane_) ale relației R

Returnează o relație eliminând toate atributele care nu sunt în L

$$\pi_{L}(R) = \{t \mid t_{1} \in R \wedge t.a_{1} = t_{1}.a_{1} \wedge \dots \wedge t.a_{n} = t_{1}.a_{n}\}$$

Iată conținutul din imaginile încărcate, transcris exact în format Markdown:



## Selecția

- Selectează tuplurile unei relații $R$ care verifică o condiție $c$ (numită și _predicat de selecție_).
    
    $$\sigma_c(R) = \{ t \mid t \in R \wedge c \}$$
    
    $$\sigma_{\text{grade} > 8}(\text{Enrolled}) = \{ t \mid t \in \text{Enrolled} \wedge \text{grade} > 8 \}$$
    



## Compunere

Rezultatul unei interogări este o relație

$$
\pi_{\text{cid, grade}}(\sigma_{\text{grade} > 8}(\text{Enrolled}))
$$

## Reuniune, intersecție, diferență

- $R_1 \cup R_2 = \{ t \mid t \in R_1 \vee t \in R_2 \}$
    
- $R_1 \cap R_2 = \{ t \mid t \in R_1 \wedge t \in R_2 \}$
    
- $R_1 - R_2 = \{ t \mid t \in R_1 \wedge t \notin R_2 \}$
    

Relațiile $R_1$ și $R_2$ trebuie să fie _compatibile_:

- același număr de atribute (aceeași _aritate_)
    
- atributele aflate pe aceeași poziție au domenii **compatibile și același nume**
    



## Reuniune, intersecție, diferență în SQL

|**R1​∪R2​**|**R1​∩R2​**|**R1​−R2​**|
|---|---|---|
|**SELECT DISTINCT ***<br><br>  <br><br>**FROM $R_1$**<br><br>  <br><br>**UNION**<br><br>  <br><br>**SELECT DISTINCT ***<br><br>  <br><br>**FROM $R_2$**|**SELECT DISTINCT ***<br><br>  <br><br>**FROM $R_1$**<br><br>  <br><br>**INTERSECT**<br><br>  <br><br>**SELECT DISTINCT ***<br><br>  <br><br>**FROM $R_2$**|**SELECT DISTINCT ***<br><br>  <br><br>**FROM $R_1$**<br><br>  <br><br>**EXCEPT**<br><br>  <br><br>**SELECT DISTINCT ***<br><br>  <br><br>**FROM $R_2$**|



- Combinarea a doua relații $R_1$ și $R_2$ cu respectarea condiției $c$
    
    $$R_1 \bowtie_c R_2 = \sigma_c(R_1 \times R_2)$$
    

---

- Combină două relații pe baza egalității atributelor ce au _același nume_ și proiectează doar unul dintre atributele redundante
    
    $$R_1 \bowtie R_2$$
    



## Câtul

- Nu este un operator de bază, însă este util în anumite situații (simplifică mult interogarea)
    
- Fie $R_1$ cu 2 atribute, $x$ și $y$ și $R_2$ cu un atribut $y$:
    
    $R_1 / R_2 = \{ <x> \mid \exists <x,y> \in R_1 \quad \forall <y> \in R_2 \}$
    
    adică, **$R_1 / R_2$ conține toate tuplurile $x$ a.î. pentru _fiecare_ dintre tuplurile $y$ din $R_2$, există câte un tupl $xy$ în $R_1$.**
    
    _Sau:_ Dacă mulțimea valorilor $y$ asociate cu o valoare $x$ din $R_1$ conține toate valorile $y$ din $R_2$, atunci $x$ va fi returnat în rezultat $R_1 / R_2$.
    
- Generalizând, $x$ și $y$ pot reprezenta orice mulțime de atribute; $y$ este mulțimea atributelor din $R_2$, și $x \cup y$ reprezintă atributele lui $R_1$.
    



## Redenumirea

- Dacă atributele și relațiile au aceleași nume (de exemplu la _join_-ul unei relații cu ea însăși) este necesar să putem redenumi una din ele
    
    $$\rho(R'(N_1 \rightarrow N'_1, N_2 \rightarrow N'_2), R)$$
    
    notație alternativă: $\rho_{R'(N'1, N'2)}(R)$
