### Exercitiul 1

**i)** Estimați parametrul necunoscut $p \in (0, 1)$ pentru distribuția binomială a unei caracteristici cercetate: $X \sim Bino(N, p)$, unde $N \in \mathbb{N}^*$ este cunoscut, cu metoda momentelor, respectiv metoda verosimilității maxime. Sunt estimatorii obținuți nedeplasați, respectiv consistenți?

**ii)** Într-o urnă sunt bile albe și negre. Proporția de bile albe $p \in (0, 1)$ este necunoscută. În urma a $n = 6$ serii a câte $N = 5$ extrageri cu returnarea bilei extrase în urnă s-au obținut: $3, 4, 2, 0, 2,$ respectiv $1$ bile albe. Estimați valoarea lui $p$ cu metoda momentelor, respectiv metoda verosimilității maxime.

$$
i) -\text{Metoda momentelor}
$$
$$
E(X) = \sum_{k=1}^{N} k\cdot C_{N}^{k} \cdot p^{k}\cdot (1-p)^{N-k} = Np
$$
$$ 
E(X) = \frac{1}{n} \sum_{k=1}^{n} x_{k} \implies Np = \frac{1}{n}\sum_{k=1}^{n} x_{k} \implies \hat{p} = \frac{1}{N \cdot  n}\sum_{k=1}^{n} x_{k}
$$
$$
\text{testam daca estimatorul obtinut este nedeplasat}
$$
$$
E(\hat{p}(X_{1},X_{2},\dots,X_{N})) = \frac{1}{N\cdot n}(E(X_{1})+E(X_{2})+\dots+E(X_{n})) = \frac{n \cdot  N \cdot  p}{n \cdot  N} =p,\text{ deci este nedeplasat}
$$


$$
\text{din LTNM} \implies \overline {X_{n}}\to E(X) \implies \overline {\frac{X_{n}}{N}}\to p,\text{dar stim ca } \hat{p} = \overline {X}_{n}/N \text{ deci } \hat{p}\to p
$$
$$i) - \text{ Metoda verosimilității maxime}$$

$$L(x_{1},x_{2},\dots, x_{n};p) = \prod_{i=1}^{n} P(X = x_{i}) = \prod_{i=1}^{n} C_{N}^{x_{i}} \cdot p^{x_{i}}\cdot (1-p)^{N-x_{i}} = p^{\sum x_{i}} \cdot (1-p)^{nN - \sum x_{i}} \prod_{i=1}^{n} C_{N}^{x_{i}}$$

$$\text{Fie } C = \prod_{i=1}^{n} C_{N}^{x_{i}}$$

$$\ln L(x_{1},x_{2}, \dots, x_{n};p) = \ln C + \left( \sum_{i=1}^{n} x_{i} \right) \ln p + \left( nN - \sum_{i=1}^{n} x_{i} \right) \ln(1-p)$$

$$\frac {\partial \ln L}{\partial p} = 0 \implies \frac{\sum x_{i}}{p} - \frac{nN - \sum x_{i}}{1-p} = 0 \implies (1-p) \sum x_{i} = p(nN - \sum x_{i})$$

$$\sum x_{i} - p \sum x_{i} = pnN - p \sum x_{i} \implies \sum x_{i} = pnN \implies \hat{p} = \frac{\sum x_{i}}{nN} = \frac{\overline{X}_{n}}{N}$$
$$
ii) - \text{ Metoda momentelor si a verosimilitatii maxime ca mai sus }
$$
$$

$$
### Exercitiul 2

 i) O caracteristică cercetată $X$ are funcția de densitate

$$f_X(x) = \begin{cases} \lambda^2 x e^{-\lambda x}, & x > 0, \\ 0, & x \le 0, \end{cases}$$

unde $\lambda > 0$ este fixat. Estimați parametrul necunoscut $\lambda$ cu metoda momentelor, respectiv metoda verosimilității maxime. Sunt estimatorii obținuți consistenți?

**ii)** Durata culorii roșii (în minute) $X$ a unui anumit semafor are funcția de densitate $f_X$ dată mai sus, cu parametrul $\lambda > 0$ necunoscut. Un taximetrist (curios din fire) a observat următoarele durate (în minute) ale culorii roșii pentru acest semafor: $1, \frac{3}{2}, 3, 2, 3, \frac{5}{2}, 1, 2$. Aplicați metoda momentelor, respectiv metoda verosimilității maxime, pentru a estima valoarea lui $\lambda$, folosind datele furnizate de taximetrist.

$$
i) -\text{ Metoda momentelor}
$$
$$
 E(X) = \int_{-\infty}^{\infty} t\cdot f(t) \, dt = \int_{0}^{\infty} \lambda^{2}t^{2}e^{-\lambda t}  \, dt = \lambda^{2}\int_{0}^{\infty}t^{2}e^{-\lambda t}  \, dt \stackrel{\text{x=}\lambda t}{=} \frac{1}{\lambda}\int_{0}^{\infty} x^{2}e^{-x}  \, dx  = 
$$
$$
=\frac{1}{\lambda}\left( -x^{2}e^{-x}\Big |_{0}^{\infty} + \int_{0}^{\infty} 2xe^{-x} \, dx  \right) = \frac{2}{\lambda}(-xe^{-x}\Big |_{0}^{\infty}  -e^{-x}\Big |_{0}^{\infty} ) = \frac{2}{\lambda}
$$
$$
E(X) = \frac{1}{n}\sum_{k=1}^{n} x_{k} \implies  \hat{\lambda} = \frac{2n}{\sum_{k=1}^{n} x_{k}} = \frac{2}{\overline {x_{n}}}
$$
$$
i) - \text{Metoda verosimilitatii }
$$
$$
L(x_{1},x_{2},\dots x_{n};p) = \prod_{k=1}^{n} f_{X}(x_{k}) = \lambda^{2n} \cdot e^{-\lambda\left( \sum_{k=1}^{n} x_{k} \right)} \cdot \prod_{k=1}^{n} x_{k}
$$
$$
\text{fie P} = \prod_{k=1}^{n} x_{k}
$$
$$
\ln L = \ln P + 2n\ln \lambda -\lambda \cdot  \sum_{k=1}^{n} x_{k}
$$
$$
\frac {\partial \ln L}{\partial \lambda} = 0\implies \frac{2n}{\lambda} - \sum_{k=1}^{n} x_{k} = 0\implies  \hat{\lambda} = \frac{2}{\overline {x_{n}}} 
$$
$$
\frac {\partial^{2} \ln L}{\partial \lambda^{2}}<0 \iff -\frac{2n}{\lambda^{2}}<0\text{ adevarat rege}
$$

$$
\text{din LTNM } \overline {X_{n}}\to E(X_{n}) = \frac{2}{\lambda} \implies
\frac{2}{\overline {X_{n}}} \to \lambda \implies  \hat{\lambda} \to \lambda
$$
$$
ii)\text{ la fel ca mai sus dar cu date}
$$

### Exercitiul 3

**a)** Un specialist IT instalează un anumit soft nou pe $64$ de calculatoare și s-a constatat că: timpul mediu de instalare este de $25$ de minute, iar abaterea standard empirică este de $5$ minute. Calculați un interval de încredere de $95\%$ pentru media timpului de instalare.

**b)** Se știe că timpul de instalare (în minute) al softului pe un calculator urmează distribuția $N(20, 8)$. Dacă un specialist instalează softul (independent) pe două calculatoare, să se aproximeze probabilitatea ca timpul total de instalare să se încadreze în intervalul $[36, 44]$.

$$
a) \overline {X_{64}} = 25,S_{n} = 5,\alpha = 0.05;
$$
$$
\text{un interval de incredere mentru media timpului de instalare este: }
$$
$$
\left( \overline {X_{64}} - \frac{5}{8} \cdot t_{0.975},\overline {X_{64}} + \frac{5}{8} \cdot  t_{0.975} \right) = \left( 25-\frac{5}{8} \cdot 1.998,25 + \frac{5}{8} \cdot  1.998  \right) =(23.75125,26.24875)
$$
$$
b) \mu = E(X) = 20,n = 2,\sigma = \sqrt{ V(X) } = 2\sqrt{ 2 }

$$
$$
\frac{\overline {X_{2}}-20 }{\frac{2\sqrt{ 2 }}{\sqrt{ 2 }}} \sim N(0,1)
$$
$$
P(18\leq \overline {X_{2}}\leq22) = P(-2\leq \overline {X_{2}}-20\leq2) = P(-1\leq \frac{\overline {X_{2}}-20}2\leq 1) \approx \text{norm.cdf(1,0,1)} - \text{norm.cdf(-1},0,1) = 0.6826
$$

### Exercitiul 4

Un statistician studiază caracteristica $X$ a unei persoane dintr-o populație dată: dacă o persoană aleasă aleator preferă ciocolata neagră, atunci $X$ ia valoarea $1$; dacă o persoană aleasă aleator preferă ciocolata albă, atunci $X$ ia valoarea $0$. Fie $\overline{X}_{100}$ media de selecție a lui $X$ pentru $100$ de variabile de selecție. Folosind TLC, estimați $P(0,49 < \overline{X}_{100} < 0,51)$, știind că proporția de persoane care preferă ciocolata neagră este $50\%$ din populația dată.

$$
n = 100;X \sim Bernoulli\left( \frac{1}{2} \right);E(X) = \frac{1}{2};V(X) = \frac{1}{2} - \frac{1}{4} = \frac{1}{4} \implies \sigma = \frac{1}{2};\mu = \frac{1}{2}; \frac{\sigma}{\sqrt{ n }} = \frac{1}{20}
$$
$$
P(0.49<\overline {X_{100}}<0.51) = P(-0.01<\overline {X_{100}}-0.5<0.01) = P\left( -0.2< \frac{\overline {X_{100}} - 0.5}{\frac{1}{20}} < 0.2 \right) = \dots
$$
### Exercitiul 5

Considerăm următoarele date statistice pentru masa corporală a persoanelor dintr-o anumită populație: $71$ kg; $68$ kg; $77$ kg; $69$ kg; $65$ kg. Presupunem că masa corporală este o caracteristică ce urmează distribuția normală.

**a)** Știind că varianța/dispersia masei corporale este $20$, determinați un interval de încredere bilateral cu nivelul de încredere $95\%$ pentru media masei corporale.

**b)** Știind că varianța/dispersia masei corporale este necunoscută, determinați un interval de încredere bilateral cu nivelul de încredere $95\%$ pentru media masei corporale.

**c)** Determinați un interval de încredere bilateral cu nivelul de încredere $95\%$ pentru varianța masei corporale.

$$
a) \overline {x_{4}} = 70; V(X) = 20 \implies \sigma = 2\sqrt{ 5 },\alpha = 0.05
$$

$$
\frac{\overline {X_{n}} - \mu}{\frac{\sigma}{\sqrt{ n }}} \sim N(0,1) \implies
\mu \approx \overline {X_{n}} - \frac{\sigma}{\sqrt{ n }} \cdot  N(0,1)
$$
$$
(70 - \sqrt{ 5 } \cdot z_{0.975},70 + \sqrt{ 5 } \cdot  z_{0.975})
$$
$$
b) s_{5}^{2} = \frac{1}{4} \sum_{k=1}^{5} (x_{k} - \overline {x_{4}})^{2} = 20 \implies s_{5} = 2\sqrt{ 5 }
$$
$$
(70 - \sqrt{ 5 } \cdot t_{0.975},70 + \sqrt{ 5 } \cdot  t_{0.975})
$$

$$
c) \left( \frac{4}{c_{0.975}} \cdot 20, \frac{4}{c_{0.025}} \cdot  20 \right) = \dots
$$

### Exercitiul 6

Un provider de internet își asigură clienții că viteza conexiunii la internet este în medie $250$ Mbps între orele $20:00$ și $22:00$. Pe de altă parte, providerul susține că în acest interval orar conexiunea nu este stabilă, având o abatere standard de $40$ Mbps. În urma unei selecții de $100$ de clienți s-a constatat că valoarea mediei de selecție este $242$ Mbps pentru viteza conexiunii între orele specificate. Să se construiască un interval de încredere unilateral stâng cu nivelul de încredere $95\%$ pentru media vitezei conexiunii.

$$
\sqrt{ V(X) } = \sigma = 40,\overline {X_{100}} = 242,\alpha = 0.05
$$
$$
\left( -\infty,242 - \frac{40}{10} \cdot  z_{0.05} \right) = (-\infty,242-4\cdot z_{0.05}) =(-\infty,242 - 4 \cdot  (-1.64)) = \dots
$$

$$

$$

### Exercitiul 7

O companie dorește înlocuirea unui sistem de frânare pentru un anumit tip de mașină cu unul nou, care să reducă semnificativ distanța de frânare. Media distanței de frânare pentru vechiul sistem este mai mare sau egală decât $50$ m, pentru o viteză de $80$ km/h pe ploaie. În urma testării a $100$ de mașini cu noul sistem de frânare instalat, pentru o viteză de $80$ km/h pe ploaie, s-a constatat că valoarea mediei de selecție este $49$ m și că valoarea abaterii standard de selecție este $1$ m pentru distanța de frânare a acestui eșantion. Să se construiască un interval de încredere unilateral drept cu nivelul de încredere $95\%$ pentru media distanței de frânare a noului sistem.

$$
\overline {x_{100}} = 49;s_{n} = 1;\alpha = 0.05
$$
$$
\left( 49 - \frac{1}{10} \cdot t_{0.95} \right) = \dots
$$