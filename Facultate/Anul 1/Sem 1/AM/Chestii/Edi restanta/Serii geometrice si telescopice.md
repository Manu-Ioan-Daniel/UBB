

Seriile geometrice $(\sum ar^n)$ converg la $(\frac{a}{1-r})$ pentru $(|r| < 1)$ și diverg dacă $(|r| \ge 1)$.

Seriile telescopice $(\sum (a_n - a_{n+1}))$ se rezolvă calculând limita șirului sumelor parțiale  
$(S_n = a_1 - a_{n+1})$, iar convergența depinde de limita $(\lim_{n \to \infty} a_{n+1})$.

---

## Serii Geometrice

O serie geometrică este de forma:

$$
\sum_{n=0}^{\infty} ar^n = a + ar + ar^2 + \dots
$$

unde:
- $a \neq 0$ este primul termen;
- $r$ este rația.

### Formula sumei parțiale

$S_n = a \frac{1 - r^n}{1 - r}, \quad r \neq 1$

### Condiție de convergență

Seria este convergentă dacă și numai dacă $|r| < 1$

### Formula sumei totale

Dacă $|r| < 1$, atunci $S = \frac{a}{1 - r}$

### Divergență

Dacă $|r| \ge 1$, seria este divergentă.

---

## Serii Telescopice

O serie telescopică este o serie de forma:

$$
\sum_{n=m}^{\infty} x_n
$$

unde termenul general este:

$x_n = a_n - a_{n+1}$

### Formula sumei parțiale

Prin anularea termenilor intermediari:

$$
\begin{aligned}
S_n &= (a_m - a_{m+1}) + (a_{m+1} - a_{m+2}) + \dots + (a_n - a_{n+1}) \\
&= a_m - a_{n+1}
\end{aligned}
$$

### Condiție de convergență

Seria este convergentă dacă există limita finită:

$\lim_{n \to \infty} a_{n+1} = L$

### Formula sumei totale

$S = a_m - \lim_{n \to \infty} a_{n+1}$


## Exercitii

$$
1.\sum_{n=1}^{\infty} \left( \frac{1}{5} \right)^{n} = \lim_{ n \to \infty } \left( \frac{1}{5} \right) \cdot  \frac{\left( 1-\frac{1}{5^{n}} \right)}{1-\frac{1}{5}} = \frac{\frac{1}{5}}{\frac{4}{5}} = \frac{1}{4}
$$
$$
2.\sum_{n=1}^{\infty} \frac{1}{\sqrt{ n }+\sqrt{ n-1 }} = \sum_{n=1}^{\infty} \sqrt{ n }-\sqrt{ n-1 } = \lim_{ x \to \infty } \sum_{n=1}^{x} \sqrt{ n }-\sqrt{ n-1 } = \lim_{ x \to \infty } \sqrt{ 1 } - \sqrt{ 0 } + \sqrt{ 2 } - \sqrt{ 1 } + \dots +\sqrt{ x-1 } - \sqrt{ x-2 } + \sqrt{ x } - \sqrt{ x-1 } = \infty
$$
$$
3.\sum_{n=1}^{\infty} \frac{1}{4n^{2}-1} = \sum_{n=1}^{\infty} \frac{1}{(2n-1)(2n+1)} = \frac{1}{2}\sum_{n=1}^{\infty} \frac{2n+1-(2n-1)}{(2n-1)(2n+1)} = \frac{1}{2}\sum_{n=1}^{\infty} \frac{1}{2n-1} - \frac{1}{2n+1} = \frac{1}{2}\left( 1-\lim_{ n \to \infty } \frac{1}{2n-1} \right) = \frac{1}{2}
$$

