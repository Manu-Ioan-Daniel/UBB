## Formule generale
$$
0.P(\overline {A}) = 1-P(A)
$$
$$
1.P(A\cap B) = P(A) \cdot  P(B) \text{ daca A si B sunt independente}
$$
$$
2.P(A|B) = \frac{P(A\cap B)}{P(B)}
$$
$$
3.P(A\cup B) = P(A) + P(B) - P(A\cap B)
$$
$$
4.P(A\cap B) = 0 \text{ inseamna ca A si B sunt disjuncte}
$$
$$
5.P(A) = P(A \cap B) + P(A \cap \overline {B}) \text{ daca A depinde de B}
$$
$$
6.P(A\cap B|C) = P(A|C) + P(B|C) \text{ daca A si B sunt conditional independente}
$$

## Formule la variabile aleatoare discrete

$7.\text{De stiut distribtuiile de la variabile discrete}$ - [[8.Distributia de probabilitate|de aici ]]

### Formule la valoarea medie

**Valoarea medie** a unei variabile aleatoare discrete (numerice) $X$, care ia valorile $\{x_i, i \in I\}$, este
$$
E(X) = \sum_{i \in I} x_i P(X = x_i),
$$
$$
E(g(X)) = \sum_{i\in I} g(x_{i})P(X = x_{i}) 
$$
$$
9.E(aX+b) = aE(X) +b
$$
$$
10.E(X\cdot Y) = E(X) \cdot  E(Y) \text{ daca X si Y sunt independente}
$$
$$
11.E(X+Y) = E(X) + E(Y)
$$
## Formule pt functia de repartitie
$$
12.F(x) = P(X \le x) = \sum_{i \in I: x_i \le x} P(X = x_i) \quad \forall x \in \mathbb{R}.
$$
13.$F(b) - F(a) = P(X \le b) - P(X \le a) = P(a < X \le b) \quad \forall a, b \in \mathbb{R}, a < b$.
14.$F$ este **monoton crescatoare**, adica pentru orice $x_1 < x_2$ rezulta $F(x_1) \le F(x_2)$.
15.$F$ este **continua la dreapta**, adica $\displaystyle \lim_{x\searrow x_0} F(x) = F(x_0) \quad \forall x_0 \in \mathbb{R}$.
16.$\lim_{x\to\infty} F(x) = 1$ si $\lim_{x\to-\infty} F(x) = 0$.

## Formule pt variabile aleatoare continue

$$
17.F(x) = P(X \le x) = \int_{-\infty}^{x} f(t)dt , \quad \forall x \in \mathbb{R},\text{f reprezinta functia de densitate, F reprezinta functia de repartitie}
$$
### Proprietati la functia de repartitie si densitate
1. $f(t) \ge 0$ pentru orice $t \in \mathbb{R}$; ^0b4226
2. $\displaystyle \int_{-\infty}^{\infty} f(t) dt = 1$; ^de6ab1
3. $P(X = a) = 0 \quad \forall a \in \mathbb{R}$;
4. $\displaystyle F(b)-F(a)=P(a \le X \le b)=P(a < X \le b)=P(a \le X < b)=P(a < X < b)=\int_{a}^{b} f(t)dt,\quad$ pentru $\forall a < b, a, b \in \mathbb{R}$ 
5. $F$ este o functie monoton crescatoare si continua pe $\mathbb{R}$;
6. $\displaystyle \lim_{x\to\infty} F(x) = 1$ si $\displaystyle\lim_{x\to-\infty} F(x) = 0$.
7. (daca $F$ este derivabila în punctul $x$, atunci $F'(x) = f(x)$.

### Valoarea medie

$$
E(X) = \int_{-\infty}^{\infty} xf(x) \, dx 
$$
$$
E(g(X)) = \int_{-\infty}^{\infty} g(x)f(x) \, dx 
$$
### Varianta

$$
V(X) = E([X-E(X)]^{2}) = E(X^{2}) -E^{2}(X)
$$
$$
V(aX+b) = a^{2}V(X)
$$
## LTNM(Legea tare a numerelor mari)

$$
\frac{1}{n}(X_{1}+X_{2}+X_{3}+\dots+X_{n}) \to E(X_{n}) \text{ cand } n\to \infty
$$
$$
\text{in plus, } \frac{1}{n}(g(X_{1}) + g(X_{2}) + \dots +g(X_{n}))\to E(g(X_{n}))
$$


