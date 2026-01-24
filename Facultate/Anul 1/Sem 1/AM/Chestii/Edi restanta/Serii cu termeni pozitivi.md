
## Criteriul condensarii al lui Cauchy
$$
\text{fie }x_{n} \text{ un sir descrecator de numere pozitive} \implies \sum_{n=1}^{\infty} x_{n} \sim \sum_{n=1}^{\infty} 2^{n}x_{2^{n}}
$$

### Exemplu

$$
\text{Natura seriei armonice generalizata} \sum_{n=1}^{\infty} \frac{1}{n^{p}} 
$$
$$
p \leq 0 \implies \sum_{n=1}^{\infty} \frac{1}{n^{p}} divergenta
$$
$$
p>0\text{ }x_{n} = \frac{1}{n^{p}} 
\text{ sir descrescator cu termeni pozitivi deci aplicam criteriul}
$$
$$
\sum_{n=1}^{\infty} \frac{1}{n^{p}} \sim \sum_{n=1}^{\infty} 2^{n}\cdot \frac{1}{2^{np}} = \sum_{n=1}^{\infty} \frac{1}{2^{n(p-1)}} = \sum_{n=1}^{\infty} \left(\frac{1}{2^{n}}\right)^{p-1}
$$
$$
r = \left( \frac{1}{2} \right)^{p-1};\text{seria converge daca |r|<1} \implies  \left( \frac{1}{2} \right)^{p-1}<1 \implies (1-p)\cdot \ln2<0\implies p>1
$$
$$
\text{deci daca p } \in(0,1) \implies \text{suma este divergenta}
$$
## Criteriul comparatiei + sub forma de limita

$$
\text{fie } \sum_{n=1}^{\infty} x_{n} \text{ si } \sum_{i=1}^{\infty} y_{n}  \text{ doua s.t.p}
$$
$$
\text{daca } \sum_{n=1}^{\infty} x_{n} \text{ convergenta} \text{ si } \exists n_{0} \in \mathbb{N}  \ a.i \ \forall n\geq n_{0}, \ y_{n}\leq x_{n}  \implies \sum_{n=1}^{\infty} y_{n} \ convergenta
$$

$$
\text{daca } \sum_{n=1}^{\infty} x_{n} \text{ divergenta} \text{ si } \exists n_{0} \in \mathbb{N}  \ a.i \ \forall n\geq n_{0}, \ y_{n} \geq x_{n}  \implies \sum_{n=1}^{\infty} y_{n} \ divergenta
$$

$$
\text{daca } \exists \lim_{ n \to \infty } \frac{x_{n}}{y_{n}} = l \in (0,\infty) \implies \sum_{n=1}^{\infty} x_{n} \sim \sum_{n=1}^{\infty} y_{n}
$$
$$
\text{daca } l = 0 \text{ si } \sum_{n=1}^{n} y_{n} \text{ convergenta} \implies \sum_{n=1}^{\infty} x_{n} \text{ convergenta}
$$
$$
\text{daca } l = +\infty \text{ si } \sum_{n=1}^{\infty} y_{n} \text{ divergenta} \implies \sum_{n=1}^{\infty} x_{n} \text{ divergenta}
$$


## Alte criterii importante

Fie $(x_n)$ un șir cu termeni strict pozitivi. Au loc:
### Criteriul raportului
Dacă $\exists \lim_{n \to \infty} \frac{x_n}{x_{n+1}} = D \in \overline{\mathbb{R}}$ atunci:

- **i)** Dacă $D > 1 \implies \sum x_n$ este convergentă
    
- **ii)** Dacă $D < 1 \implies \sum x_n$ este divergentă
    

### Criteriul lui Raabe - Duhamel

Dacă $\exists \lim_{n \to \infty} n \cdot \left( \frac{x_n}{x_{n+1}} - 1 \right) = R \in \overline{\mathbb{R}}$ atunci:

- **i)** Dacă $R > 1 \implies \sum x_n$ este convergentă
    
- **ii)** Dacă $R < 1 \implies \sum x_n$ este divergentă

### Criteriul lui Bertrand

Dacă $\exists \lim_{n \to \infty} \ln n \cdot \left[ n \cdot \left( \frac{x_n}{x_{n+1}} - 1 \right) - 1 \right] = B \in \overline{\mathbb{R}}$ atunci:

- **i)** Dacă $B > 1 \implies \sum x_n$ este convergentă
    
- **ii)** Dacă $B < 1 \implies \sum x_n$ este divergentă

### Criteriul radical al lui Cauchy
daca $\exists \lim_{n \to \infty} \sqrt[n]{x_n} = C \in \overline{\mathbb{R}}$.

- **i)** Dacă $\frac{1}{C} > 1 \implies \sum x_n$ este convergentă
    
- **ii)** Dacă $\frac{1}{C} < 1\implies \sum x_n$ este divergentă


## Exercitii

$$
1. \sum_{n=1}^{\infty} \frac{1}{\sqrt[n]{ n }} \sim \sum_{n=1}^{\infty} 2^{n}\cdot \frac{1}{2} \text{ divergenta}
$$
$$
2. \sum_{n=1}^{\infty} \frac{2^{n}}{n+3^{n}}
$$
$$
\text{fie }b_{n} = \frac{2^{n}}{3^{n}} \text{ si } a_{n} = \frac{2^{n}}{n+3^{n}}
$$
$$
\lim_{ n \to \infty } \frac{a_{n}}{b_{n} } =\lim_{ n \to \infty } \frac{\frac{2^{n}}{n+3^{n}}}{\frac{2^{n}}{3^{n}}} = \lim_{ n \to \infty } \frac{2^{n}}{n+3^{n}} \cdot  \frac{3^{n}}{2^{n}} = \lim_{ n \to \infty } \frac{3^{n}}{n+3^{n}} = 1 \in(0,\infty) \implies \sum_{n=1}^{\infty} \frac{2^{n}}{n+3^{n}} \sim \sum_{n=1}^{\infty} \left( \frac{2}{3} \right)^{n} \text{ convergenta}
$$

$$
3. \sum_{n=1}^{\infty } \sin^{3} \frac{1}{n}
$$
$$
\text{fie } a_{n} = \sin^{3} \frac{1}{n};b_{n}  = \frac{1}{n^{3}}
$$
$$
\lim_{ n \to \infty } \frac{\sin\left( \frac{1}{n} \right)}{\frac{1}{n}} = 1 \implies \lim_{ n \to \infty } \frac{\sin^{3}\left( \frac{1}{n^{3}} \right)}{\frac{1}{n^{3}}} = \lim_{ n \to \infty } \frac{a_{n}}{b_{n}} = 1 \implies \sum_{n=1}^{\infty} \sin^{3}\left( \frac{1}{n} \right) \sim \sum_{n=1}^{\infty} \frac{1}{n^{3}} \text{ convergenta}
$$


$$
\sum_{n=1}^{\infty} \frac{1}{1+\sqrt{ 2 }+\dots+\sqrt{ n }}
$$
$$
b_{n} = \frac{1}{n\sqrt{ n }};(\text{alegem } b_{n} \text{ ca  1 supra n* ultimul termen din suma dinauntrul sumei(adica}  \ \sqrt{ n })
$$
$$
a_{n} = \frac{1}{1+\sqrt{ 2 }+\dots+\sqrt{ n }}
$$
$$
\lim_{ n \to \infty } \frac{a_{n}}{b_{n}} = \lim_{ n \to \infty } \frac{n\sqrt{ n }}{1+\sqrt{ 2 }+\dots+\sqrt{ n }} 
$$
$$
\text{fie } x_{n} = n\sqrt{ n } \text{ si } y_{n} = 1+\sqrt{ 2 }+\dots+\sqrt{ n } \text{ crescator si nemarginit}
$$
$$
\lim_{ n \to \infty } \frac{x_{n+1}-x_{n}}{y_{n+1}-y_{n}} = \lim_{ n \to \infty } \frac{(n+1)\sqrt{ n+1 } - n\sqrt{ n }}{\sqrt{ n+1 }} = \lim_{ n \to \infty } \frac{(n+1)^{3}-n^{3}}{\sqrt{ n+1 }((n+1)\sqrt{ n+1 } + n\sqrt{ n })} = \frac{3}{2} \in(0,\infty) \implies \dots
$$



$$

$$


