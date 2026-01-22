
## **Teorema Cesaro-Stolz**

Fie $(a_n)_n$ un șir iar $(b_n)_n$ un șir strict monoton nemărginit. Dacă există

$$\lim_{n \to \infty} \frac{a_{n+1} - a_n}{b_{n+1} - b_n} = l \in \overline{\mathbb{R}}$$

atunci există $\lim_{n \to \infty} \frac{a_n}{b_n}$ și este egală cu $l$.

$$
\text{ O consecinta a teoremei lui Cezaro-Stolz este:}\lim_{ n \to \infty } \sqrt[n]{ a_{n}  } = \lim_{ n \to \infty } \frac{a_{n+1}}{a_{n}},daca \ \lim_{ n \to \infty } \frac{a_{n+1}}{a_{n}} \text{exista}
$$
## Exercitii rezolvate

### Exercitiul 1


$$
a_{n} = \frac{1}{n}\left( 1+\frac{1}{2}+\frac{1}{3}+\dots+\frac{1}{n} \right)
$$
$$
\lim_{ n \to \infty } a_{n} = ?
$$

#### Rezolvare
$$
\text{fie }x_{n} = \left( 1+\frac{1}{2}+\dots+\frac{1}{n} \right)
\text{ si }y_{n} = n;y_{n} \text{ strict crescator si }\lim_{ n \to \infty } y_{n} = \infty \implies nemarginit
$$
$$
\lim_{ n \to \infty } \frac{x_{n+1}-x_{n}}{y_{n+1}-y_{n}} = \lim_{ n \to \infty } \frac{\frac{1}{n+1}}{n+1-n} = \lim_{ n \to \infty } \frac{1}{n+1} = 0 \in \overline {\mathbb{R}},\text{unde } \overline {\mathbb{R}} = \mathbb{R} \cup\{-\infty,+\infty \}
$$
### Exercitiul 2

#### Rezolvare

$$
a_{n} = \frac{\ln n}{n};\lim_{ n \to \infty } a_{n} =?
$$
$$
\text{fie } x_{n} = \ln n\text{ si } y_{n} = n\text{ strict crescator si nemarginit}
$$
$$
\lim_{ n \to \infty } \frac{x_{n+1}-x_{n}}{y_{n+1}-y_{n}} = \lim_{ n \to \infty } \frac{\ln(n+1) - \ln n}{1} = \lim_{ n \to \infty } \ln\left(  \frac{n+1}{n} \right) = \ln 1 = 0\in \overline {\mathbb{R}}
$$

### Exercitiul 3

$$
a_{n} = \sqrt[n^{2}]{ n! } = n!^\frac{1}{n^{2}};\lim_{ n \to \infty } a_{n} = ?
$$

#### Rezolvare

$$
\text{fie}\lim_{ n \to \infty } a_{n} = l \in \overline {\mathbb{R}} \implies \lim_{ n \to \infty } \ln(a_{n}) = \ln l \implies \lim_{ n \to \infty }  \frac{\ln n!}{n^{2}} = \ln l
$$
$$
\text{fie }x_{n} = \ln n! \text{ si } y_{n} = n^{2} \text{ strict crescator si nemarginit}
$$
$$
\lim_{ n \to \infty } \frac{x_{n+1}-x_{n}}{y_{n+1}-y_{n}} = \lim_{ n \to \infty } \frac{\ln(n+1)}{2n+1}  = 0 \implies \ln l = 0 \implies l = 1
$$
$$
\text{fie }  u_{n} = \ln(n+1) \text{ si } v_{n} = 2n+1 \text{ strict crescator si nemarginit}
$$
$$
\lim_{ n \to \infty } \frac{u_{n+1}-u_{n}}{v_{n+1}-v_{n}} = \lim_{ n \to \infty } \frac{\ln\left( \frac{n+1}{n} \right)}{1} = \ln1=0 \in \overline {\mathbb{R}}\implies \lim_{ n \to \infty } \frac{u_{n}}{v_{n}} = 0
$$


### Exercitiul 4

#### Rezolvare

$$
a_{n} =\frac{1^{p} + 3^{p} + \dots + (2n-1)^{p}}{n^{p+1}},p \in \mathbb{N},p\geq1;\lim_{ n \to \infty } a_{n}  = ?
$$
$$
\text{fie } x_{n} = 1^{p} + 3^{p} + \dots + (2n-1)^{p}\text{ si } y_{n} = n^{p+1}
$$
$$
\lim_{ n \to \infty } \frac{(2n+1)^{p}}{(n+1)^{p+1} - n^{p+1}} = \lim_{ n \to \infty } \frac{(2n+1)^{p}}{n^{p} \sum_{k=0}^{p} \frac{C_{p+1}^{k}\cdot n^{k}}{n^{p}}} = \lim_{ n \to \infty } \frac{(2n+1)^{p}}{n^{p}\cdot C_{p+1}^{p}} = \lim_{ n \to \infty } \frac{(2n+1)^{p}}{n^{p}\cdot \frac{(p+1)!}{p!}} = 
$$
$$
=\lim_{ n \to \infty } \left(\frac{2n+1}{n}\right)^{p} \cdot \frac{1}{p+1} = \frac{2^{p}}{p+1} \in \overline {\mathbb{R}} \implies \lim_{ n \to \infty } \frac{x_{n}}{y_{n}} = \frac{2^{p}}{p+1}
$$
$$
(n+1)^{p+1} -n^{p+1} = \sum_{k=0}^{p+1} C_{p+1}^{k}n^{k}\cdot 1^{p+1-k} - n^{p+1} = 
$$
$$
=\sum_{k=0}^{p+1}C_{p+1}^{k}\cdot n^{k} - n^{p+1} = \sum_{k=0}^{p} C_{p+1}^{k} \cdot  n^{k}  = n^{p}\sum_{k=0}^{p} \frac{C_{p+1}^{k} \cdot n^{k}}{n^{p}}
$$
$$
\text{Binomul lui Newton:} (a+b)^{n} = \sum_{k=1}^{n} C_{n}^{k} \cdot a^{k}\cdot b^{n-k}
$$
### Exercitiul 5
$$
x_{n} = \sqrt[n]{ \frac{3^{3n} \cdot  (n!)^{3}}{(3n)!}};\lim_{ n \to \infty } x_{n} = ?
$$
#### Rezolvare


$$
\text{fie }a_{n} = \frac{3^{3n} \cdot  (n!)^{3}}{(3n)!}
$$
$$
\lim_{ n \to \infty } \frac{a_{n+1}}{a_{n}} = \lim_{ n \to \infty } \frac{3^{3n+3}\cdot ((n+1)!)^{3}}{(3n+3)!} \cdot \frac{(3n)!}{3^{3n}\cdot (n!)^{3}} = \lim_{ n \to \infty } \frac{27\cdot (n+1)^{3}}{(3n+1)(3n+2)(3n+3)} = 1 \in \overline {\mathbb{R}} \implies \lim_{ n \to \infty } x_{n} = 1
$$


### Exercitiul 6

$$
x_{n} = \frac{[1^{p}\cdot x] + [2^{p}\cdot x]+\dots+[n^{p}\cdot x]}{n^{p+1}};\lim_{ n \to \infty } x_{n} = ?
$$
$$
\text{fie } a_{n} = [1^{p}\cdot x] + [2^{p}\cdot x]+\dots+[n^{p}\cdot x] \text{ si } b_{n} = n^{p} \text{ strict crescator si nemarginit daca p>0}
$$
$$
\lim_{ n \to \infty } \frac{[(n+1)^{p}\cdot x]}{(n+1)^{p+1} - n^{p+1}} = \lim_{ n \to \infty } \frac{[(n+1)^{p}\cdot x]}{n^{p} \cdot  (p+1)} = \frac{1}{p+1} \cdot  \lim_{ n \to \infty } \frac{[(n+1)^{p}\cdot x]}{n^{p}}
$$
$$
\text{stim ca } x-1 < [x] \leq x \implies(n+1)^{p} \cdot  x - 1 < \dots \leq (n+1)^{p}\cdot x \implies (n+1)^{p} \cdot  \frac{x}{n^{p}} -\frac{1}{n^{p}} < \dots \leq (n+1)^{p}\cdot \frac{x}{n^{p}}
$$
$$
\implies x< \lim_{ n \to \infty } \dots \leq x \implies \frac{1}{p+1}\lim_{ n \to \infty } \frac{[(n+1)^{p}\cdot x]}{n^{p}} = \frac{x}{p+1} 
$$
$$

$$

