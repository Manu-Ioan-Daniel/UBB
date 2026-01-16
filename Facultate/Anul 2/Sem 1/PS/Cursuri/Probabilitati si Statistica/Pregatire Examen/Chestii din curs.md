### Exercitiul 1

 Folosind metoda verosimilității maxime să se estimeze parametrul $\theta := p \in (0, 1)$ al distribuției Bernoulli,

$$X \sim \begin{pmatrix} 0 & 1 \\ 1 - p & p \end{pmatrix},$$

cu datele statistice: $0, 1, 1, 0, 0, 0, 1, 0, 1, 0$.

$$
L(x_{1},x_{2},\dots,x_{n};p) = p^{x_{1}} \cdot  (1-p)^{1-x_{1}} \cdot  \dots p^{x_{n}} \cdot  (1-p)^{1-x_{n}} = p^{x_{1}+x_{2}+\dots+x_{n}} \cdot (1-p)^{n-(x_{1}+x_{2}+\dots+x_{n})}
$$
$$
\ln L(x_{1},x_{2},\dots,x_{n};p) = (x_{1}+x_{2}+\dots+x_{n}) \cdot  \ln p + (n-(x_{1}+x_{2}+\dots+x_{n}))\ln(1-p)
$$
$$
\frac {\partial \ln L}{\partial p} = \frac{x_{1}+x_{2}+\dots+x_{n}}{p} + \frac{-n+(x_{1}+x_{2}+\dots+x_{n})}{(1-p)} = 0 \implies (1-p)(x_{1}+x_{2}+\dots+x_{n})
$$
$$
- np +p\cdot (x_{1}+x_{2}+\dots+x_{n}) = 0 \implies p = \frac{x_{1}+x_{2}+\dots+x_{n}}{n}
$$
$$
\frac {\partial^{2} \ln L}{\partial p^{2}} = -\frac{x_{1}+x_{2}+\dots+x_{n}}{p^{2}}- \frac{n - (x_{1}+x_{2}+\dots+x_{n})}{(1-p)^{2}} = \dots <0
$$
$$
\hat{p}(x_{1},x_{2},\dots,x_{n}) = \frac{0+1+1+0+0+0+1+0+1+0}{10} = 0.4
$$
Este estimatorul $\hat{p}(X_{1},X_{2},\dots,X_{n})$ nedaplasat pentru parametrul p?

$$
E(\hat{p}) = E\left( \frac{X_{1}+X_{2}+\dots+X_{n}}{n} \right) = \frac{1}{n}\sum_{k=1}^{n} E(X_{k}) = p,\text{ deci este micule baka}
$$


### Exercitiul 2

Fie $X \sim Exp(\lambda)$. Să se determine cuantila de ordin $\alpha$.
Cazul 1:$z_{\alpha}\leq0$
$$
f(t) = \begin{cases}
\lambda e^{-\lambda t},&\text{daca t>0} \\
0,&\text{altfel}
\end{cases}
$$
$$
F(z_{\alpha}) = \alpha \implies \int_{-\infty}^{z_{\alpha}} \lambda e^{-\lambda t} \, dt =\alpha \implies \text{alpha nu poate fi 0}
$$
Cazul 2:$z_{\alpha}>0$
$$
F(z_{\alpha}) = \alpha \implies \int_{-\infty}^{z_{\alpha}} \lambda e^{-\lambda t} \, dt = -e^{-\lambda t} \Big |_{0}^{z_{\alpha}} = 1-e^{-z_{\alpha}\lambda} = \alpha \implies -z_{\alpha}\lambda = \ln(1-\alpha) \implies z_{\alpha} = -\frac{\ln(1-\alpha)}{\lambda}
$$

#### Exercitiul 2.2

Fie $X \sim \begin{pmatrix} 1 & 3 & 5 & 7 \\ 0.2 & 0.35 & 0.35 & 0.1 \end{pmatrix}$ v.a. discretă. Să se determine mediana $z_{0.5}$.

$$
P(X < z_{0.5}) \leq 0.5 \leq P(X \leq z_{0.5})
$$
$$
P(X<3) = 0.2\leq0.5;P(X\leq 3) = 0.55 \ge 0.5 \implies P(X<3)\leq0.5\leq P(X\leq3) \implies z_{0.5} = 3
$$
### Exercitiul 3

- **Volumul eșantionului ($n$):** 200.
    
- **Media de selecție ($\bar{x}_{200}$):** 10 minute.
    
- **Obiectiv:** Putem construi un interval (aleator) care să acopere valoarea reală a parametrului necunoscut studiat cu o anumită probabilitate dată (numită nivel de încredere)? Pe baza datelor din eșantion acest interval aleator va deveni un interval numeric.

hell no

### Exercitiul 4

Dacă $(X_n)_{1 \le n \le 100}$ sunt variabile de selecție pentru caracteristica $X \sim Bernoulli(0.5)$, să se estimeze $P(0.35 < \bar{X}_{100} < 0.65)$, folosind P.20 (TLC).

$$
\mu = E(X) = 0.5;\sigma^{2} = V(X) = E(X^{2}) - E^{2}(X) = 0.25;\sigma = 0.5
$$
$$
P(0.35 < \overline {X}_{100} < 0.65) = P(-0.15 < \overline {X_{100}} - 0.5 < 0.15) = P(-3 < 20\cdot \overline {X_{100}} - 10 < 3) \
$$
$$
\approx F_{N(0,1)}(3) - F_{N(0,1)}(-3) \approx 0.9973
$$
### Exericitul 5

Un profesor a înregistrat pe parcursul mai multor ani rezultatele elevilor săi la un anumit tip de test. Punctajul unui elev este o v.a. $X \in (0, 100)$, având abaterea standard egală cu 10. Media de selecție a calificativelor a 144 de elevi este 68. Dacă $\alpha = 0.05$, să se construiască un interval de încredere bilateral pentru valoarea medie (teoretică) $E(X)$ a punctajului obținut de un elev la test.

$$
Std(X) = 10;\overline {X_{144}} = 68;\alpha = 0.05
$$
$$
z_{1-\frac{\alpha}{2}} = \frac{1}{\sqrt{ 2\pi }}\int_{0}^{1-\alpha/2} e^{-t^{2}/2} \, dt = \dots
$$
$$
\big(\overline {X_{144}} - \frac{\sigma}{12} \cdot  z_{1-\frac{\alpha}{2}},\overline {X_{144}} + \frac{\sigma}{12} \cdot  z_{1-\frac{\alpha}{2}}\big)  = (68-\frac{10}{12} \cdot z_{1-\frac{\alpha}{2}},68 + \frac{10}{12} \cdot  z_{1-\frac{\alpha}{2}})
$$
### Exercitiul 6

O companie dorește să estimeze greutatea medie a noului său model de laptop. Un eșantion de 100 laptopuri indică o greutate medie de 1.35 kg, cu o abatere standard empirică de 0.03 kg. Să se determine un interval de încredere de 99% bilateral pentru greutatea medie a noului model de laptop.

$$
\overline {X_{100}} = 1.35;S_{n} = 0.03;\alpha = 0.01
$$
$$
\left( \overline {X_{n}} -\frac{S_{n}}{\sqrt{ n }}  \cdot  t_{1-\frac{\alpha}{2}},\overline {X_{n}} + \frac{S_{n}}{\sqrt{ n }}\cdot t_{1-\frac{\alpha}{2}}\right) = \left( 1.35 - \frac{0.03}{10} \cdot  lmao,1.35 + \frac{0.03}{10} \cdot  lmao \right)
$$

### Exericitul 6

Media de selecție a lungimii a 100 de șuruburi produse de o anumită firmă este 15.5 cm, iar varianța de selecție este 0.09 cm$^2$. Să se construiască un interval de încredere 99% bilateral pentru varianța (teoretică) a lungimii șuruburilor. Dacă varianța este prea mare (adică peste 0.099 cm$^2$), aparatul, care produce șuruburile, trebuie reglat. Se presupune că lungimea unui șurub (produs de această firmă) are o distribuție normală.

$$
\overline {X_{100}} = 15.5;S^{2}_{n} = 0.09;\alpha = 0.01
$$

$$
\frac{n-1}{\sigma^{2}} \cdot S^{2}_{n} = \chi^{2}(n-1) \implies \sigma^{2} = \frac{n-1}{\chi^{2}(n-1)} \cdot S^{2}_{n}
$$
$$
\Big(\frac{n-1}{c_{1-\frac{\alpha}{2}}} \cdot S_{n}^{2}, \frac{n-1}{c_{\frac{\alpha}{2}}} \cdot  S_{n}^{2}\Big) \implies \dots
$$

### Exercitiul 7

Durata de funcționare a unui anumit tip de baterie este 500 de ore. Pe baza unui eșantion s-au testat 64 de baterii și s-a obținut media de 525 de ore și abaterea standard de 25 de ore. Să se construiască un interval de încredere 99%:

- **a)** bilateral pentru media (teoretică);
    
- **b)** unilateral stâng pentru abaterea standard teoretică (marginea inferioară este 0 și se cere să se calculeze marginea superioară) a duratei de funcționare a acestui tip de baterii (se presupune că durata de funcționare a acestui tip de baterie urmează distribuția normală).'

$$
\overline {X_{n}} -\frac{S_{n}}{\sqrt{ n }} \cdot  T(n-1) \approx \mu
$$
$$
\text{un interval de incredere bilateral este: } \Big (525-\frac{25}{8} \cdot t_{1-0.005},525+\frac{25}{8} \cdot t_{1-0.005} \Big )
$$
$$
\chi^{2}(n-1) \sim \frac{(n-1)}{\sigma^{2}}\cdot S_{n}^{2} \implies \sigma^{2} \approx \frac{n-1}{\chi^{2}(n-1)}\cdot S_{n}^{2} \implies \sigma \approx \sqrt{ \frac{n-1}{\chi^{2}(n-1)} } \cdot S_{n}
$$

$$
\left( 0, \sqrt{\frac{63}{c_{0.01}}} \cdot  25 \right)
$$

