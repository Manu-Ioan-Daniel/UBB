## Exercitiul 1
Un cod $C$ de lungime 5 format din cifre din mulțimea $\{0, 1, 2, 3, 4, 6, 8, 9\}$ este generat aleator.
Fie evenimentele:
- $A$: „$C$ conține cifre distincte”; 
- $B$: „$C$ conține doar cifre pare”.
**a)** Numărul total de coduri posibile (de tipul lui $C$) în care cifra 2 apare exact de două ori, cifra 4 apare exact de 2 ori, iar cifra 8 o dată (în orice ordine) este ......

**b)** Numărul total de coduri posibile (de tipul lui $C$) cu cifre distincte, în care apar exact două cifre impare și exact trei cifre pare (în orice ordine) este ......

**c)** Probabilitatea $P(A)$ este ......

**d)** Probabilitatea $P(A \setminus B)$ este ......

**e)** Probabilitatea condiționată $P(B|A)$ este ......

$$
a) \frac{5!}{2! \cdot  2! \cdot  1!} ;b) A_{3}^{2} \cdot A_{5}^{2} \cdot  \frac{5!}{3! \cdot  2!} ;c) \frac{A_{8}^{5}}{8^{5}}
$$
$$
d)P(A \setminus B) = P(A) - P(A \cap B) = \frac{A_{8}^{5} - 5!}{8^{5}}
$$
$$
e)P(B|A) = \frac{P(B \cap A)}{P(A)} = \frac{5!}{A_{8}^{5}}
$$
## Exercitiul 2

2.Fie variabilele aleatoare independente $X$ și $Y$, având distribuțiile

$$X \sim \begin{pmatrix} -1 & 1 \\ \frac{1}{2} & \frac{1}{2} \end{pmatrix} \text{ și } Y \sim \begin{pmatrix} -1 & 0 & 1 \\ \frac{1}{4} & \frac{1}{4} & \frac{1}{2} \end{pmatrix}.$$

Să se calculeze $E(X \cdot Y)$ și funcția de repartiție a variabilei aleatoare $X \cdot Y$.
(A se justifica soluția acestei probleme.)

$$
X \cdot  Y \sim \begin{pmatrix}
-1 & 0 & 1 \\
\frac{3}{8}  & \frac{2}{8} & \frac{3}{8}
\end{pmatrix} \implies F_{(X,Y)}(x) = \begin{cases}
0, & x<-1 \\
\frac{3}{8}, & -1\leq x<0 \\
\frac{5}{8}, & 0\leq x<1 \\
1, & altfel
\end{cases}
$$
$$
E(X \cdot  Y) = -\frac{3}{8} + \frac{3}{8} = 0
$$

