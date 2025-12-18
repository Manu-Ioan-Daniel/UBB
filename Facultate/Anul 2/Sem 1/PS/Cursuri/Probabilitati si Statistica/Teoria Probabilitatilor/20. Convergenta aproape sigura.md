
## Șir de Variabile Aleatoare Independente

**Def. 25.** $(X_n)_n$ este **șir de v.a. independente**, daca $\forall \{i_1, \dots, i_k\} \subset \mathbb{N}$ v.a. $X_{i_1}, \dots, X_{i_k}$ sunt independente, adică pentru oricare $x_{i_1}, \dots, x_{i_k} \in \mathbb{R}$ are loc
$$
P(X_{i_1} \le x_{i_1}, \dots, X_{i_k} \le x_{i_k}) = \displaystyle P(X_{i_1} \le x_{i_1}) \cdot \dots \cdot P(X_{i_k} \le x_{i_k}).
$$

### Exemplu:

a) $X_n$ = v.a. care indica numarul aparut la a $n$-a aruncare a unui zar $\implies (X_n)_n$ șir de v.a. independente.
b) Se arunca o moneda.
$$
X_n = \displaystyle
\begin{cases}
0 : \text{la a } n\text{-a aruncare a aparut cap,} \\
1 : \text{la a } n\text{-a aruncare a aparut pajura.}
\end{cases}
$$
$\implies (X_n)_n$ șir de v.a. independente.
c) $X_n$ = v.a. care indica numarul aparut la al $n$-lea joc de ruleta $\implies (X_n)_n$ șir de v.a. independente.

---

## Convergenta Aproape Sigura (a.s.)

### Definitie
Șirul de v.a. $(X_n)_n$ **converge aproape sigur (a.s.)** la v.a. $X$, daca
$$
P\left(\{\omega \in \Omega : \displaystyle \lim_{n\to\infty} X_n(\omega) = X(\omega)\}\right) = 1.
$$
**Notatie:** $X_n \xrightarrow{\text{a.s.}} X$ 

* Cu alte cuvinte, convergenta aproape sigura $X_n \xrightarrow{\text{a.s.}} X$ impune ca șirul $\{X_n(\omega)\}_n$ sa convearga la $X(\omega)$ pentru fiecare $\omega \in \Omega$, cu exceptia unei multimi "mici" de probabilitate nula.
* Daca $X_n \xrightarrow{\text{a.s.}} X$ atunci evenimentul $M = \{\omega \in \Omega : (X_n(\omega))_n \text{ nu converge la } X(\omega)\}$ are $P(M) = 0$.

### Exemplu:

Fie $\Omega := [0, 1]$ spatiul de selectie, $P$ probabilitatea pe $[0, 1]$ (care este numita masura Lebesgue pe $[0, 1]$), adica pentru $\forall \alpha < \beta$ din $[0, 1]$ are loc
$$
P([\alpha, \beta]) = P([\alpha, \beta)) = P((\alpha, \beta]) = P((\alpha, \beta)) = \beta - \alpha \quad (\text{lungimea intervalului})
$$

1. Fie $X_n(\omega) = \omega + \omega^n + (1 - \omega)^n$, $\omega \in [0, 1]$, $n \ge 1 \implies X_n \xrightarrow{\text{a.s.}} ???$
$$
\lim_{n\to\infty} X_n(\omega) = \displaystyle
\begin{cases}
\omega & \text{pentru } \omega \in (0, 1) \\
1 & \text{pentru } \omega = 0 \\
2 & \text{pentru } \omega = 1.
\end{cases}
$$
Fie $X(\omega) = \omega$ pentru fiecare $\omega \in \Omega$.
$$
\implies \{\omega \in \Omega : \displaystyle \lim_{n\to\infty} X_n(\omega) = \omega\} = (0, 1)
$$
$$
\implies P(\{\omega \in \Omega : \displaystyle \lim_{n\to\infty} X_n(\omega) = \omega\}) = P((0, 1)) = 1.
$$
Deci, $X_n \xrightarrow{\text{a.s.}} X$.

2. $X_n(\omega) = (-1)^n \omega (1 - \omega)$, $\omega \in [0, 1]$, $n \ge 1$; converge $\{X_n\}_n \xrightarrow{\text{a.s.}} \text{?}$

 $(X_n)_n$ nu converge a.s. spre o v.a.; șirul $\{X_n(\omega)\}_n$ este convergent doar pentru $\omega \in \{0, 1\}$, iar $P(\{0, 1\}) = 0$.
