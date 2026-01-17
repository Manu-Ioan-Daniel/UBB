
## Exerciții recapitulative

### Exercitiul 1

Fie vectorul $U = [1, 2, 3, 1, 2, 4, 1, 2, 2, 4]$ și vectorul de date aleatoare alese:

1. cu returnare din $U$: $X = [U_{i_1}, \dots, U_{i_5}]$;
    
2. fără returnare din $U$: $Y = [U_{j_1}, U_{j_2}, U_{j_3}]$.
    

Fie $Z$ variabila aleatoare care indică de câte ori apare $1$ în vectorul $X$.

- **a)** Determinați: $P(Z = 3)$, $P(\{Z < 3\} \cup \{Z > 4\})$, $P(Z < 3 \mid Z \geq 1)$, $P(Y = [1, 2, 3])$, $P(Y(2) \text{ este un număr par})$.
    
- **b)** Să se scrie distribuția de probabilitate a variabilei aleatoare $Z$.

$$
P(Z = 3) = C_{5}^{3} \cdot  \Big(\frac{3}{10}\Big)^{3} \cdot  (\frac{7}{10})^{2} = \dots
$$
$$
P(Z<3 \cup Z>4 ) = \sum_{i=0}^{2} C_{5}^{i}\cdot \left( \frac{3}{10} \right)^{i} \cdot  \left( \frac{7}{10} \right)^{5-i} 
$$
$$
P(Z<3 | Z\geq1) = \frac{P(Z<3 \cap Z\geq1)}{P(Z\geq1)} = 
$$
$$
\frac{\sum_{i=1}^{2} C_{5}^{i} \cdot  \left( \frac{3}{10} \right)^{i} \cdot \left( \frac{7}{10} \right)^{5-i}}{\sum_{i=1}^{5} C_{5}^{i} \cdot  \left( \frac{3}{10} \right)^{i} \cdot \left( \frac{7}{10} \right)^{5-i} }
$$
$$
P(Y = [1,2,3]) = \frac{3}{10} \cdot  \frac{4}{9} \cdot  \frac{1}{8}
$$

$$
P(Y(2) \text{ par }) = P(Y(2) \text{ par} \cap Y(1)\text{ par})+P(Y(2) \text{ par}\cap Y(1) \text{ nu este par}) = \frac{5}{9} \cdot  \frac{6}{10}  + \frac{6}{9} \cdot  \frac{4}{10} = \frac{54}{90} =\frac{3}{5}
$$
$$
b) Z \sim \begin{pmatrix}
k \\
C_{5}^{k} \cdot  \left( \frac{3}{10} \right)^{k} \cdot  \left( \frac{7}{10} \right)^{5-k}
\end{pmatrix}, \ \text{unde k = } \overline {0,5}
$$
### Exercitiul 2

Fie $x_1 = 1, x_2 = 0, x_3 = 3, x_4 = 2, x_5 = 0, x_6 = 1, x_7 = 4, x_8 = 5$ date statistice pentru caracteristica $X$, care are următoarea distribuție:

$$P(X = k) = p(1 - p)^k \text{ pentru } k \in \{0, 1, 2, \dots\},$$

iar $p \in (0, 1)$ este parametru necunoscut. Folosind metoda verosimilității maxime, estimați valoarea parametrului necunoscut $p$.

$$
L(x_{1},x_{2},\dots,x_{8};p) = p^{8}\cdot (1-p)^{16}
$$
$$
\ln L(\dots) = 8\ln p+16\ln(1-p)
$$
$$
\frac {\partial \ln L}{\partial p} = \frac{8}{p} -\frac{16}{1-p} = 0 \implies8-8p - 16p = 0 \implies p=\frac{1}{3}
$$
$$
\frac {\partial^{2} L}{\partial p^{2}} = -\frac{8}{p^{2}} -\frac{16}{(1-p)^{2}}<0
$$

### Exercițiul 3

Timpul $T$ (în minute) de așteptare a unui autobuz este o variabilă aleatoare care are funcția de densitate $f : \mathbb{R} \to \mathbb{R}$,

$$f(t) = \begin{cases} \frac{1}{\delta}, & t \in [a, a + \delta] \\ 0, & t \notin [a, a + \delta] \end{cases}$$

unde $a \geq 0$ și $\delta > 0$ sunt parametri necunoscuți. Varianța lui $T$ este $V(T) = \frac{3}{4}$.

- **a)** Calculați în funcție de $a$ și $\delta$: $E(T)$, $E(T^2)$.

$$
E(T) = \int_{-\infty}^{\infty} t \cdot f(t) \, dt = \int_{a}^{a+\delta} t\cdot \frac{1}{\delta} \, dt = \frac{t^{2}}{2\delta}\Big |_{a}^{a+\delta}  = a + \frac{\delta}{2}
$$
$$
E(T^{2}) = V(T) + E^{2}(T) = \frac{3}{4} + \left( a+\frac{\delta}{2} \right)^{2}
$$
    
- **b)** Determinați valoarea lui $\delta$.

$$
E(T^{2}) =\int_{a}^{a+\delta} \frac{t^{2}}{\delta} \, dt = \frac{t^{3}}{3\delta}\Big |_{a}^{a+\delta}  = \frac{1}{3\delta}(3a^{2}\delta + 3a\delta^{2} + \delta^{3}) = a^{2} + a\delta + \frac{\delta^{2}}{3} \implies \frac{3}{4}+a^{2}+a\delta + \frac{\delta^{2}}{4} = a^{2}+a\delta+\frac{\delta^{2}}{3}
$$
$$
\frac{\delta^{2}+3}{4} = \frac{\delta^{2}}{3} \implies \delta^{2} = 9 \implies \delta = 3
$$
    
- **c)** Pentru $75$ de timpi independenți de așteptare ai lui $T$ s-a obținut media de selecție $1.5$ (minute). Să se determine valoarea intervalului de încredere bilateral cu nivelul de încredere $95\%$ pentru valoarea medie a lui $T$, folosind tabelul următor:
    

| **norm.ppf(0.975, 0, 1)** | **norm.ppf(0.05, 0, 1)** | **t.ppf(0.975, 74)** | **chi2.ppf(0.025, 74)** | **chi2.ppf(0.975, 74)** |
| ------------------------- | ------------------------ | -------------------- | ----------------------- | ----------------------- |
| $1.96$                    | $-1.65$                  | $2$                  | $52$                    | $100$                   |



$$
\overline {X_{70}} = 1.5;\alpha = 0.05;\sigma = \sqrt{ \frac{3}{4} } = \frac{\sqrt{ 3 }}{2}
$$

$$
\text{un interval de inceredere bilateral este: }\left( \overline {X_{n}} -\frac{\sigma}{\sqrt{ n }} \cdot z_{1-\frac{\alpha}{2}} ,\overline {X_{n}} + \frac{\sigma}{\sqrt{ n }} \cdot  z_{1-\frac{\alpha}{2}}\right) = \left( 1.5 - \frac{1}{10} \cdot  1.96,1.5 + \frac{1}{10} \cdot  1.96 \right)
$$
### Exercițiul 4

**4.** Pentru transmisia unui mesaj se alege aleator unul din cele două canale de transmisie disponibile:

$$2 \text{ canale posibile} \longrightarrow \begin{cases} \xrightarrow{p_1 = 0.4} \text{prin canalul 1, timpul de transmisie este } T_1 \sim Unif[1, 5] \text{ (ms)}, \\ \xrightarrow{p_2 = 0.6} \text{prin canalul 2, timpul de transmisie este } T_2 \sim Unif[1, 3] \text{ (ms)}. \end{cases}$$

Pentru timpul $T$ de transmisie a mesajului să se calculeze $P(T < 2)$, $P(T = 2)$ și $P(T > 2)$.

$$
P(T<2) = P(T<2 \cap T \text{ este canalul 1}) + P(T<2 \cap T \text{ este canalul 2}) = \frac{4}{10} \cdot  \int_{1}^{2} \frac{1}{4} \, dt +\frac{6}{10} \int_{1}^{2} \frac{1}{2} \, dt = \frac{2}{5} 
$$
$$
P(T = 2) = 0 \text{ pentru ca probabilitatea de a alege un punct dintr un interval este mereu 0,pentru ca in acel interval sunt o infinitate de puncte}
$$

$$
P(T>2) =\frac{4}{10} \cdot  \int_{2}^{5} \frac{1}{4} \, dt + \frac{6}{10} \cdot  \int_{2}^{3} \frac{1}{2} \, dt = \frac{3}{5}   =1-P(T<2) = 1-\frac{2}{5}
$$

### Exericitul 5

O tombolă are 2 bilete câștigătoare și 8 bilete necâștigătoare. Se extrag succesiv 3 bilete (fără returnare).

**a)** $P(\text{"nu s-a extras niciun bilet câștigător"}) = ?;$

**b)** Fie $X$ v.a. (variabila aleatorie) care indică numărul de bilete câștigătoare extrase. Să se calculeze 
$E(X)$.

$$
a) \frac{8}{10} \cdot  \frac{7}{9} \cdot  \frac{6}{8}
$$
$$
b) P(X = k) = \frac{C_{2}^{k} \cdot C_{8}^{3-k}}{C_{10}^{3}} ,\text{ unde k = } \overline {0,2} \implies E(X) = 0 \cdot  P(X = 0) + 1\cdot P(X = 1) + 2\cdot P(X = 2) = \dots
$$

### Exericitul 6

Se studiază caracteristica: timpul de producție a unui anumit produs. Această caracteristică se presupune a fi normal distribuită și s-a realizat un eșantion cu 25 de valori (în minute). Pe baza acestora s-a obținut o valoare medie de 8.1 minute și o abatere standard de 1.6 minute. Construiți intervale de încredere 95% bilaterale pentru: **a)** abaterea standard a timpului de prelucrare; **b)** media timpului de prelucrare. Conform normelor de producție timpul de prelucrare ar trebui să fie cel mult 8.6 minute, iar abaterea standard cel mult 2 minute. În baza datelor statistice, se poate concluziona că normele de producție sunt îndeplinite?

|**x**|**0.02**|**0.025**|**0.04**|**0.05**|**0.95**|**0.96**|**0.975**|**0.995**|
|---|---|---|---|---|---|---|---|---|
|**norm.ppf($x, 0, 1$)**|-2.05|-1.96|-1.75|-1.64|1.64|1.75|1.96|2.58|
|**t.ppf($x, 24$)**|-2.17|-2.06|-1.83|-1.71|1.71|1.83|2.06|2.8|
|**chi2.ppf($x, 24$)**|11.992|12.4|13.35|13.85|36.42|37.39|39.36|45.6|

$$
\overline {X_{25}} = 8.1;S_{n} =1.6;\alpha = 0.05
$$
$$
\text{un interval de incredere bilateral pentru abaterea standard este : } \left( \sqrt \frac{n-1}{c_{1-\frac{\alpha}{2}}} \cdot S_{n},  \sqrt \frac{n-1}{c_{\frac{\alpha}{2}}} \cdot  S_{n}  \right)  = \left( \sqrt \frac{24}{c_{0.975}} \cdot  1.6, \sqrt{ \frac{24}{c_{0.025}} } \cdot  1.6\right)
$$
$$
\text{un interval de incredere pentru media timpului de prelucrare este:}
$$
$$
\left( \overline {X_{n}} -\frac{S_{n}}{\sqrt{ n }} \cdot  t_{1-\frac{\alpha}{2}}, \overline {X_{n}} + \frac{S_{n}}{\sqrt{ n }} \cdot  t_{1-\frac{\alpha}{2}} \right) = \left( 8.1 - \frac{1.6}{5} \cdot t_{0.975},8.1 + \frac{1.6}{5} \cdot  t_{0.975} \right) = \dots
$$

### **Exercițiul 7**

Pentru ce valoare a constantei $c \in \mathbb{R}$ funcția $f : \mathbb{R} \to \mathbb{R}$ definită prin

$$f(x) = \begin{cases} cx & : 0 \leq x \leq 3, \\ c(6 - x) & : 3 < x \leq 6, \\ 0 & : \text{altfel} \end{cases}$$

este funcție de densitate a unei v.a. $X$?

Să se determine funcția de repartiție a v.a. $X$. Fie evenimentele $A = \{X < 3\}$ și $B = \{\frac{3}{2} \leq X \leq \frac{9}{2}\}$. Să se calculeze $P(A \cap B)$, $P(A|B)$ și $P(A \cup \bar{B})$.

$$
\text{f este functie de densitate a lui X} \iff \int_{-\infty}^{\infty} f(t) \, dt = 1 \iff \int_{0}^{3} ct \, dt + \int_{3}^{6}  c(6-t) \, dt  = 1 \iff \frac{9c}{2} + 18c - 18c + \frac{9c}{2} \iff 9c = 1 \iff c = \frac{1}{9}
$$
$$
f(x)\geq0 \iff \begin{cases}
\frac{1}{9}x\geq0, & 0\leq x \leq3 \\
\frac{1}{9}(6-x)\geq0, & 3<x\leq6  \\
0\geq0, & altfel
\end{cases} \text{ adevarat}
$$
$$
\text{deci,cum} \ f(x) \geq0 \ \forall x \in \mathbb{R} \text{ si } \int_{-\infty}^{\infty} f(t) \, dt = 1,\text{ f este functie de densitate} 
$$
$$
F(X) = \int_{-\infty}^{x} f(t) \, dt  = \begin{cases}
\int_{-\infty}^{0} 0 \, dt, & x<0  \\
F(0) + \int_{0}^{x}f(t)  \, dt, & 0\leq x\leq3 \\
F(3) + \int_{3}^{x}  f(t)\, dt , & 3<x\leq6 \\
F(6), & x>6
\end{cases}
= \begin{cases}
0, & x<0  \\
\frac{x^{2}}{18}, & 0\leq x\leq3 \\
\frac{1}{2} + \frac{2}{3}(x-3) - \frac{x^{2}}{18} + \frac{1}{2}, & 3<x\leq6 \\
1, & x>6
\end{cases}
$$

$$
P\left( X<3 \cap \frac{3}{2}\leq X\leq \frac{9}{2} \right) = P\left( \frac{3}{2}\leq X < 3 \right) = F(3) - F\left( \frac{3}{2} \right) = \frac{1}{2} - \frac{1}{8} = \frac{3}{8}
$$
$$
P(A|B) = \frac{P(A \cap B)}{P(B)} = \frac{\frac{3}{8}}{F\left( \frac{9}{2} \right) - F\left( \frac{3}{2} \right)} = \frac{3}{8} \cdot  \frac{4}{3} = \frac{1}{2}
$$
$$
P(A \cup \overline {B}) = P(A) + P(\overline {B}) - P(A \cap \overline {B}) = \frac{1}{2} + 1 -\frac{3}{4} - P(A \cap \overline {B}) = \frac{3}{4} - \frac{1}{8} = \frac{5}{8}
$$
$$
P(A \cap B) + P(A \cap \overline {B}) = P(A) \implies P(A\cap \overline {B}) = P(A) - P(A\cap B) = \frac{1}{2} - \frac{3}{8} = \frac{1}{8}
$$


