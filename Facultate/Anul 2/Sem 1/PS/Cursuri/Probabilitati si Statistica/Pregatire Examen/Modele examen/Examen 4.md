
### Exercitiul 1

Fie $X$ o variabilă aleatoare astfel încât $P(X = -1) = P(X = 1) = \frac{1}{6}$, $P(X = 0) = P(X = 2) = \frac{2}{6}$. Se generează aleator și independent 4 valori pentru $X$. Calculați:

(a) $P(\text{"toate valorile sunt egale"}) = \dots$

(b) $P(\text{"toate valorile sunt distincte"}) = \dots$

(c) $P(\text{"cel mult o valoare este 1"}) = \dots$

(d) $P(\text{"cel puțin o valoare este 0 și cel puțin o valoare este 1"}) = \dots$

(e) Funcția de repartiție $F : \mathbb{R} \to [0, 1], F(x) = \dots$

(f) Dacă $(X_n)_n$ este un șir de variabile aleatoare independente și identic distribuite cu $X$, atunci:

$$\frac{1}{n}(X_1^2 + X_2^2 + \dots + X_n^2) \xrightarrow{a.s.} \dots, \text{ când } n \to \infty.$$
$$
X \sim \begin{pmatrix}
-1 & 0 & 1 & 2 \\
\frac{1}{6} & \frac{2}{6} & \frac{1}{6} & \frac{2}{6}
\end{pmatrix}
$$
$$
a)2\cdot \left( \frac{1}{6} \right)^{4} + 2\cdot \left( \frac{2}{6} \right)^{4};b) \frac{1}{6} \cdot  \frac{2}{6} \cdot  \frac{1}{6} \cdot \frac{2}{6} \cdot  4!;c)P(\text{o valoare este 1} ) + P(\text{0 de 1}) = \frac{1}{6} \cdot  4 \cdot  \left( \frac{5}{6} \right)^{3} + \left( \frac{5}{6} \right)^{4}
$$
$$
d)P(\text{minim un 0 si minim un 1}) = 1 -P(\text{0 de 0 sau 0 de 1}) = 1-P(\text{0 de 0}) - P(\text{0 de 1}) + P(\text{0 de 0 si 0 de 1}) =
$$
$$
= 1- \left( \frac{4}{6} \right)^{4} - \left( \frac{5}{6} \right)^{4} + \left( \frac{3}{6} \right)^{4} = \frac{31}{81}
$$
$$
e)F(x) = \begin{cases}
0, & x<-1 \\
\frac{1}{6}, & -1\leq x<0 \\
\frac{3}{6}, & 0\leq x<1 \\
\frac{4}{6}, & 1\leq x<2 \\
1, & altfel
\end{cases}
$$
$$
f) \text{din LTNM stim ca } \frac{1}{n}\sum_{k=1}^{n} X_{k}\to E(X_{n}) \implies \frac{1}{n}\sum_{k=1}^{n} X_{k}^{2} \to E(X^{2}_{n}) = \frac{1}{3} + \frac{8}{6} =\frac{5}{3}
$$
