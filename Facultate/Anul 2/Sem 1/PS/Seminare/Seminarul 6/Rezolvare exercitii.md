### Exercitiul 1

Într-un joc, se aruncă trei monede. Un jucător câștigă 1 euro pentru fiecare apariție a unui cap și pierde 8 euro în cazul apariției a trei pajuri. Calculați pentru suma de bani a jucătorului: funcția de repartiție, valoarea medie și deviația standard.

$$
X \sim \begin{pmatrix}
-8 & 1 & 2 & 3 \\
\frac{1}{8} & \frac{3}{8} & \frac{3}{8} & \frac{1}{8}
\end{pmatrix}
$$

$$
F(x) = P(S\leq x) = \begin{cases}
0, & \text{daca x<-8} \\
\frac{1}{8}, & \text{daca -8} \leq x <1 \\
\frac{1}{2}, & \text{daca } 1 \leq x <2  \\
\frac{7}{8}, & \text{daca } 2\leq x < 3 \\
1, & \text{daca x} >3
\end{cases}
$$
$$
E(X) = -1 + \frac{3}{8} + \frac{6}{8} + \frac{3}{8} = \frac{1}{2}
$$
$$
V(X) = E(X^{2}) - E^{2}(X) = 8+\frac{3}{8} + \frac{12}{8} + \frac{9}{8} - \frac{1}{4} = \frac{86}{8} = \frac{43}{4} \implies \sqrt{ V(X) } = \frac{\sqrt{ 43 }}{2}
$$

### Exercitiul 2

Un jucător de darts ochește discul roșu (denumit “bullseye”) cu centrul în centrul țintei și diametru $1$ cm. La o aruncare, distanța dintre centrul țintei și punctul nimerit de săgeata jucătorului urmează distribuția uniformă pe intervalul $[a, b]$, unde $0 \le a < b$, cu valoarea medie $\frac{3}{2}$ cm și deviația standard $\frac{\sqrt{3}}{2}$ cm. Aruncările jucătorului sunt independente. 
Determinați:
- a) probabilitatea ca jucătorul să nimerească discul roșu;
- b) probabilitatea ca jucătorul să nimerească de $2$ ori discul roșu din $10$ aruncări.
**Funcția de densitate pentru distribuția uniformă $Unif[a, b]$ este $f : \mathbb{R} \to \mathbb{R}$ definită prin $f(x) = \begin{cases} \frac{1}{b-a}, & x \in [a, b] \\ 0, & x \notin [a, b] \end{cases}$.**

$$
a) E(X) = \frac{3}{2}\implies \int_{-\infty}^{\infty} xf(x) \, dx  = \frac{3}{2} \implies \int_{a}^{b} \frac{x}{b-a} \, dx = \frac{a+b}{2} = \frac{3}{2} \implies a+b =3
$$
$$
V(X) = \frac{3}{4} \implies -\frac{9}{4}+E(X^{2}) = \frac{3}{4} \implies -\frac{9}{4}+\int_{a}^{b} \frac{x^{2}}{b-a}  \, dx =\frac{3}{4}\implies -\frac{9}{4}+\frac{a^{2}+b^{2}+ab}{3} = \frac{3}{4}
$$
$$
9-ab=9\implies ab = 0\implies a = 0 \text{ deoarece b>0} \implies b = 3 
$$
$$
P(X\leq0.5) = \int_{0}^{0.5} \frac{1}{3} \, dx  = \frac{1}{6}
$$
$$
b)\text{fie X} \sim Binom\left( 10, \frac{1}{6} \right) \implies P(X = 2) = C_{10}^2 \cdot \left( \frac{1}{6} \right)^{2} \cdot  \left( \frac{5}{6} \right)^{8} = \dots
$$

### Exercitiul 3

Fie $X \sim \begin{pmatrix} 1 & 2 & 3 & 4 \\ 0.1 & 0.2 & 0.3 & 0.4 \end{pmatrix}$ și $\Omega$ spațiul de selecție. Fie $(X_n)_n$ un șir de variabile aleatoare independente definite pe $\Omega$, care au aceeași distribuție ca $X$.**

**a) Fie, pentru $n \in \mathbb{N}^*$, v.a. $Y_n(\omega) = \begin{cases} 1, & \text{dacă } X_n(\omega) \le 3 \\ 0, & \text{dacă } X_n(\omega) > 3 \end{cases}, \omega \in \Omega$.Ce distribuție are $Y_n$? Spre ce valoare converge a.s. șirul $\left(\frac{1}{n}(Y_1 + \dots + Y_n)\right)_n$?


**b) Pentru $n \in \mathbb{N}^*$, fie**

$$Z_n : \Omega \to [0, 1] \quad Z_n(\omega) = \frac{\# \{i \in \{1, \dots, n\} : X_i(\omega) \le 3\}}{n}$$
Ce relație avem între $Y_1 + \dots + Y_n$ și $Z_n$? Folosind a), determinați limita a.s. pentru $(Z_n)_n$.
$$
a) Y_{n} \sim \begin{pmatrix}
0 & 1 \\
0.4 & 0.6
\end{pmatrix} \text{pentru ca }P(X_{n}\leq3) = 0.1+0.2+0.3=0.6 \text{ si } P(X_{n}>3) = 0.4
$$
$$
\text{din LTNM} \implies \frac{1}{n}(Y_{1}+Y_{2}+\dots+Y_{n})\to E(Y_{n}) = 0.6
$$

$$
b)Z_{n} = \frac{1}{n}\sum_{k=1}^{n} Y_{k} \to 0.6
$$

### Exercitiul 4

**Durata (în minute) a unei plăți pentru o factură la un ghișeu într-o bancă urmează distribuția continuă $Unif[1, 3]$. Știind că duratele oricăror plăți sunt independente, demonstrați că:**

**i) media aritmetică a duratelor plăților a $n$ facturi converge a.s. la $2$ minute, când $n \to \infty$.**

**ii) media geometrică a duratelor plăților a $n$ facturi converge a.s. la $\frac{3\sqrt{3}}{e}$ minute, când $n \to \infty$.**

**iii) media armonică a duratelor plăților a $n$ facturi converge a.s. la $\frac{2}{\ln 3}$ minute, când $n \to \infty$.**

$$
i)\text{fie X} \sim Unif[1,3] \text{ si } X_{1},X_{2},\dots X_{n} \text{ variabile aleatoare cu aceeasi distributie ca si X}
$$
$$
\frac{1}{n}(X_{1}+X_{2}+\dots+X_{n}) \to E(X_{n}) = 2\text{ din LTNM}
$$
$$
ii) \frac{1}{n}\sum_{k=1}^{n} \ln(X_{k}) \to E(\ln(X)) = \int_{1}^{3} \frac{\ln(x)}{2} \, dx = x\ln x\Big |_{1}^{3}  - x\Big |_{1}^{3} = \frac{3\ln3}{2}-1  \implies
$$
$$
e^{1/n \sum_{k=1}^{n} \ln(X_{k})} \to \frac{3\sqrt{ 3 }}{e} \implies (X_{1} \cdot  X_{2} \cdot  .. \cdot  X_{n})^{1/n} \to \frac{3\sqrt{ 3 }}{e}
$$

$$
iii)\text{ nu pica media armonica da o drq de treaba}
$$

### Exercitiul 5

prea usor

### Exercitiul 6

Fie v.a. $U \sim Unif[1, 3]$. Să se calculeze $E(U^2)$. Folosind rezultatul obținut, să se justifice de ce $U^2$ nu urmează distribuția $Unif[1, 9]$

$$
E(U^{2}) = \int_{-\infty}^{\infty} x^{2}f(x) \, dx = \int_{1}^{3} \frac{x^{2}}{2} \, dx = \frac{x^{3}}{6}\Big |_{1}^{3} =\frac{13}{3}
$$
$$
\text{Fie X} \sim Unif[1,9] \implies E(X) = \int_{-\infty}^{\infty} xg(x) \, dx = \int_{1}^{9} \frac{x}{8} \, dx = \frac{x^{2}}{16}\Big |_{1}^{9}   = 5 \neq \frac{13}{3} \implies U^{2}\text{ nu urmeaza distributia Unif[1,9]}  
$$
### Exercitiul 7

foarte asemenator cu cel de deasupra
### Exercitiul 8

Timpii de funcționare (în ore) a două baterii sunt două variabile aleatoare independente $X \sim Unif[0, 2]$ și $Y \sim Exp(1)$. Fie $T = \min\{X, Y\}$ timpul de funcționare a bateriilor legate în serie. Calculați: $P(X < 0,5)$, $P(T > 1)$, $P(T < 1 | X \ge 1)$.

$$
P(X<0.5) = \int_{0}^{0.5} \frac{1}{2} \, dx = \frac{1}{4} 
$$
$$
P(T>1)  = P(X>1 \cap Y>1) = P(X>1) \cdot  P(Y>1) = \int_{1}^{2} \frac{1}{2} \, dx \cdot  \int_{1}^{\infty} e^{-x} \, dx   = \frac{1}{2e}
$$
$$
P(T<1|X\geq1) = P(Y<1) = \int_{0}^{1} e^{-t} \, dt  = 1-\frac{1}{e}
$$
