

### Problema 1

Într-o urnă sunt 5 bile roșii, 3 bile galbene, 2 albastre. Se extrag 3 fără returnare. Să se afle probabilitatea ca:

- **a)** toate bilele sunt roșii
    
- **b)** bilele au culori distincte
    
- **c)** prima bilă roșie, celelalte albastre
    
- **d)** cel puțin o bilă albastră
    
- **e)** cel mult o bilă roșie
    

$$
a) \frac{1}{2} \cdot  \frac{4}{9} \cdot  \frac{3}{8} = \frac{12}{144} = \frac{1}{12};b) \frac{5}{10} \cdot  \frac{3}{9} \cdot  \frac{2}{8} \cdot  3!;c) \frac{5}{10}\cdot \frac{2}{9} \cdot  \frac{1}{8};d) \frac{2}{10} \cdot  \frac{8}{9} \cdot  \frac{7}{8} \cdot  3 + \frac{2}{10}  \cdot  \frac{1}{9}  \cdot  3 ;e) \frac{5}{10} \cdot  \frac{4}{9} \cdot  \frac{3}{8} + \frac{5}{10} \cdot  \frac{5}{9} \cdot  \frac{4}{8} \cdot  3 
$$
$$
\text{ sau cu multinomiala}
$$
$$
a) \frac{C_{5}^{3}}{C_{10}^{3}};b) \frac{C_{5}^{1} \cdot  C_{3}^{1} \cdot  C_{2}^{1}}{C_{10}^{3}};c) \frac{\frac{C_{5}^{1}}{3} \cdot  C_{2}^{2}}{C_{10}^{3}};d)1-\frac{C_{8}^{3}}{C_{10}^{3}} e) \frac{C_{5}^{3}}{C_{10}^{3}} + \frac{C_{5}^{1} \cdot C_{5}^{2}}{C_{10}^{3}}
$$
### Problema 2

Un program returnează aleator 1 cu $p = \frac{2}{5}$ și 0 cu $p = \frac{3}{5}$. Se apelează programul de $n$ ori. Fie $Z$ - nr. biți de 1 obținuți.

- **a)** $P(Z=2) = ?$ dacă $n = 4$
    
- **b)** Distribuția și $n = ?$ dacă $E(Z) = 4$

$$
a) Z \sim Bino\left( 4, \frac{2}{5} \right) \implies P(Z = 2) = C_{4}^{2} \cdot  \left( \frac{2}{5} \right)^{2} \cdot \left( \frac{3}{5} \right)^{2}
$$
$$
b) E(Z) = 4 \implies \frac{2n}{5} =4 \implies n=10 \implies Z \sim Bino\left( 10, \frac{2}{5} \right) = \begin{pmatrix}
k \\
C_{10}^{k} \cdot  \left( \frac{2}{5} \right)^{k} \cdot  \left( \frac{3}{5} \right)^{10-k}
\end{pmatrix}
$$
    

### Problema 3

$R$ timp în minute, variabilă aleatoare:

$$f(x) = \begin{cases} \frac{1}{T}, & x \in [b-T, b] \\ 0, & \text{altfel} \end{cases}$$

- **a)** $E(R), E(R^2)$
    
- **b)** $T = ?$ dacă $V(R) = 3$
    
- **c)** 300 de timpi independenți de întârziere $R$ s-au obținut, medie de selecție 25 minute. Valoarea intervalului de încredere bilateral cu nivel de încredere $95\%$ pentru valoarea medie a lui $R$ folosind tabelul următor:
    
| **norminv** | **tinv(0.975, 299)** | **norminv(0.975, 0, 1)** | **chi2inv(0.025, 0, 1)** |
| ----------- | -------------------- | ------------------------ | ------------------------ |
| -1,65       | 1,97                 | 1,96                     | 239                      |

$$
a)E(R) = \int_{-\infty}^{\infty} tf(t) \, dt = \int_{-\infty}^{b-T} t \cdot 0 \, dt + \int_{b-T}^{b} t\cdot \frac{1}{T} \, dt  +\int_{b}^{\infty} t\cdot 0 \, dt = \frac{1}{T} \cdot  \frac{t^{2}}{2}\Big |_{b-T}^{b}  = b-\frac{T}{2}
$$
$$
E(R^{2}) = \int_{-\infty}^{\infty} t^{2}\cdot f(t) \, dt  = \int_{b-T}^{b} \frac{t^{2}}{T} \, dt = \frac{t^{3}}{3T}\Big |_{b-T}^{b}  = \dots 
$$
$$
b)V(R) = 3\implies E(R^{2}) - E^{2}(R) = 3 \implies T=\dots
$$
$$
c)n =300;\overline {x_{300}} = 25,\alpha = 0.05,\sigma^{2} = V(R) = 3 \implies \sigma = \sqrt{ 3 }
$$
$$
\left( \overline {x_{n}} - \frac{\sigma}{\sqrt{ n }} \cdot z_{1-\frac{\alpha}{2}}, \overline {x_{n}} + \frac{\sigma}{\sqrt{ n }} \cdot  z_{1-\frac{\alpha}{2}} \right) = \left( 25-\frac{1}{10} \cdot  z_{0.975},25+\frac{1}{10} \cdot  z_{0.975} \right)
$$

