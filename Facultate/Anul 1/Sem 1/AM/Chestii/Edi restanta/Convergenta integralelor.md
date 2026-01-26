
### 1. Criteriul de convergență pentru integrala improprie cu singularitate în capătul drept $b$

**Propoziție:** Dacă $a, b, p \in \mathbb{R}$, $f:[a, b) \to [0, +\infty)$ este o funcție pozitivă și local integrabilă pe $[a, b)$ și există limita:

$$\lim_{x \nearrow b} (b-x)^p \cdot f(x) = \lambda$$

Atunci:

- **i)** Dacă $p < 1$ și $\lambda < +\infty \implies \int_a^{b-0} f(x)dx$ este **convergentă**.
    
- **ii)** Dacă $p \ge 1$ și $\lambda > 0 \implies \int_a^{b-0} f(x)dx$ este **divergentă**.
    

---

### 2. Criteriul de convergență pentru interval infinit $[a, +\infty)$

**Propoziție:** Dacă $a, p \in \mathbb{R}$, $f:[a, +\infty) \to [0, +\infty)$ este o funcție pozitivă și local integrabilă pe $[a, +\infty)$ și există limita:

$$\lim_{x \to +\infty} x^p \cdot f(x) = \lambda$$

Atunci:

- **i)** Dacă $p > 1$ și $\lambda < +\infty \implies \int_a^{+\infty} f(x)dx$ este **convergentă**.
    
- **ii)** Dacă $p \le 1$ și $\lambda > 0 \implies \int_a^{+\infty} f(x)dx$ este **divergentă**.
    

---

### 3. Criteriul de convergență pentru integrala improprie cu singularitate în capătul stâng $a$


**Propoziție:** Dacă $a, b, p \in \mathbb{R}$, $f:(a, b] \to [0, +\infty)$ este o funcție pozitivă și local integrabilă pe $(a, b]$ și există limita:

$$\lim_{x \searrow a} (x-a)^p \cdot f(x) = \lambda$$

Atunci:

- **i)** Dacă $p < 1$ și $\lambda < +\infty \implies \int_{a+0}^{b} f(x)dx$ este **convergentă**.
    
- **ii)** Dacă $p \ge 1$ și $\lambda > 0 \implies \int_{a+0}^{b} f(x)dx$ este **divergentă**.
    

---

### 4. Criteriul de convergență pentru interval infinit $(-\infty, b]$

**Propoziție:** Dacă $b, p \in \mathbb{R}$, $f:(-\infty, b] \to [0, +\infty)$ este o funcție pozitivă și local integrabilă pe $(-\infty, b]$ și există limita:

$$\lim_{x \to -\infty} (-x)^p \cdot f(x) = \lambda$$
Atunci:

- **i)** Dacă $p > 1$ și $\lambda < +\infty \implies \int_{-\infty}^{b} f(x)dx$ este **convergentă**.
    
- **ii)** Dacă $p \le 1$ și $\lambda > 0 \implies \int_{-\infty}^{b} f(x)dx$ este **divergentă**.

### Exemple

$$
1.\int_{0}^{1} \frac{1}{\sqrt{ 1-x^{2} }\sqrt{ 2-x^{2} }} \, dx 
$$
$$
\lim_{ x \to 1,x<1 } (1-x)^{p} \cdot  \frac{1}{\sqrt{ 1-x^{2} }\sqrt{ 2-x^{2} }} = 
$$
$$
\text{pt p} = \frac{1}{2} \lim_{ x \to 1,x<1 } \frac{(1-x)^{1/2}}{\sqrt{ (1+x) }\sqrt{ 1-x }\sqrt{ 2-x^{2} }}  = \frac{1}{2}
$$
$$
\text{cum } \lambda=\frac{1}{2}<+\infty  \text{ si } p =\frac{1}{2}<1 \implies \text{integrala este convergenta}
$$

$$
2.I(a) = \int_{1}^{\infty} \frac{x^{a}}{\sqrt{ x+1 }} \, dx ;I(-1) = ?
$$
$$
\lim_{ x \to \infty } x^{p} \cdot f(x) = \lim_{ x \to \infty }  \frac{x^{p+a}}{\sqrt{ x+1 }}
$$
$$
\text{fie } p =\frac{1}{2}-a \implies \lim_{ x \to \infty } x^{1/2-a}\cdot f(x) = \lim_{ x \to \infty } \frac{\sqrt{ x }}{\sqrt{ x+1 }} = 1
$$
$$
\text{daca } p>1 \implies \frac{1}{2}-a>1 \implies a<-\frac{1}{2} \implies \text{integrala este convergenta pentru a<}-\frac{1}{2}
$$
$$
\text{daca p<=1} \implies \frac{1}{2}-a\leq1 \implies a\geq -\frac{1}{2} \implies\text{integrala este divergenta pentru a}\geq-\frac{1}{2}
$$
$$
I\left( \frac{1}{2} \right) = \int_{1}^{\infty} \frac{\sqrt{ x }}{\sqrt{ x+1 }} \, dx \stackrel{u=\sqrt{ x+1 }}{=} \int_{\sqrt{ 2 }}^{\infty} 2\sqrt{ u^{2}-1 }  \, du
$$
$$
u = \sqrt{ x+1 } \implies du = \frac{1}{2\sqrt{ x+1 }}dx \implies dx = 2\sqrt{ x+1 } \ du = 2u \ du
$$
$$
u=\sqrt{ x+1 } \implies u^{2}=x+1\implies \sqrt{ x } = \sqrt{ u^{2}-1 }
$$
$$
x = 1\implies u=\sqrt{ 2 }; \lim_{ x \to \infty } \sqrt{ x+1 } = \infty
$$
$$
\text{Substitutiile in integrale se fac cand este prezenta derivata unei functii pe care o recunosti}
$$
$$
2 \int_{\sqrt{ 2 }}^{\infty}  \frac{u^{2}-1}{\sqrt{ u^{2}-1 }} \, du  = 2\int_{\sqrt{ 2 }}^{\infty} \frac{u^{2}}{\sqrt{ u^{2}-1 }} \, du -2\int_{\sqrt{ 2 }}^{\infty} \frac{1}{\sqrt{ u^{2}-1 }} \, du   = 2\int_{\sqrt{ 2 }}^{\infty} u \cdot  \frac{u}{\sqrt{ u^{2}-1 }} \, du -2\ln(u+\sqrt{ u^{2}-1 } ) \Big |_{\sqrt{ 2 }}^{\infty} 
$$
=$$
2\int_{\sqrt{ 2 }}^{\infty} u \cdot  (\sqrt{ u^{2}-1 })' \, du -\dots = \lim_{ x \to \infty }  2\left( u\sqrt{ u^{2}-1 }\Big |_{\sqrt{ 2 }}^{x}  - \int_{\sqrt{ 2 }}^{x} \sqrt{ u^{2}-1 } \, du  \right) - \dots
$$
$$
\lim_{ x \to \infty }  2\int_{\sqrt{ 2 }}^{x} \sqrt{ u^{2}-1 } \, du = \lim_{ x \to \infty } 2u\sqrt{ u^{2 }-1}\Big |_{\sqrt{ 2 }}^{x}  -2\int_{\sqrt{ 2 }}^{x} \sqrt{ u^{2}-1 } \, du - 2\ln(u+\sqrt{ u^{2}-1 })\Big |_{\sqrt{ 2 }}^{x} 
$$
$$
4\int_{\sqrt{ 2 }}^{\infty} \sqrt{ u^{2}-1 } \, du = 2\lim_{ x \to \infty } x\sqrt{ x^{2}-1 }-\sqrt{ 2 } -2\ln(x+\sqrt{ x^{2}-1 }) + 2\ln(\sqrt{ 2 }) = \dots \text{ doar asa pt edi}
$$
$$
\text{pt a = -1} =I(-1) = \int_{1}^{\infty} \frac{1}{x\sqrt{ x+1 }} \, dx \stackrel{u=\sqrt{ x+1 }}{=} \int_{\sqrt{ 2 }}^{\infty} \frac{2}{u^{2}-1} \, du = \ln \Big|\frac{(u-1)}{u+1}\Big| \Big |_{\sqrt{ 2 }}^{\infty}  = \ln\left( \frac{\sqrt{ 2 }+1}{\sqrt{ 2 }-1} \right) = \ln(3+2\sqrt{ 2 })
$$
$$

$$




