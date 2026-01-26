
## Gradientul

$$
\nabla f(x^{0}) = \left( \frac {\partial f}{\partial x_{1}}(x^{0}),\frac {\partial f}{\partial x_{2}}(x^{0}),\dots \frac {\partial f}{\partial x_{m}}(x^{0}) \right)
$$
$$
x^{0} = (x_{1}^{0},x_{2}^{0},\dots x^{0}_{m})
$$
### Exemplu

$$
f(x,y) = x^{2}+2xy \implies \nabla f(x,y) = (2x+2y,2x)
$$
## Diferentiala

$$
df(x^{0})(u) = \nabla f(x^{0})\cdot u
$$
### Exemplu

$$
f(x,y) = x^{2}+2xy \implies \nabla f(x,y) = (2x+2y,2x)
$$
$$
df(x,y)(u) =(2x+2y,2x) \cdot  \begin{pmatrix}
u_{1} \\
u_{2}
\end{pmatrix}  = u_{1}\cdot (2x+2y) + u_{2}\cdot 2x
$$
## Matricea Jacobiana

$$
J(f)(x^{0}) = \begin{pmatrix}
\frac {\partial f_{1}}{\partial x_{1}},\frac {\partial f_{1}}{\partial x_{2}},\dots \frac {\partial f_{1}}{\partial x_{m}} \\
\frac {\partial f_{2}}{\partial x_{1}},\frac {\partial f_{2}}{\partial x_{2}},\dots \frac {\partial f_{2}}{\partial x_{m}}  \\
\dots \\
\frac {\partial f_{p}}{\partial x_{1}},\frac {\partial f_{p}}{\partial x_{2}},\dots \frac {\partial f_{p}}{\partial x_{m}} \\
\end{pmatrix}
$$
### Exemplu

$$
f(x,y) = (2x+3y,7x+5y,x^{2}+7xy)
$$
$$
J(f)(x,y) = \begin{pmatrix}
2 & 3 \\
7 & 5 \\
2x+7y & 7x
\end{pmatrix}
$$
## Diferentiala de ordinul 2

$$
d^{2}f(x^{0})(u) = \sum_{j=1}^{m} \sum_{i=1}^{m} \frac {\partial^{2} f}{\partial x_{j}\cdot \partial x_{i}}(x^{0}) \cdot  u_{i} \cdot  u_{j}
$$

### Exemplu

$$
f(x) =x^3 + 3y^{3}
$$
$$
d^{2}f(x,y)(u) = \sum_{j=1}^{2} \sum_{i=1}^{2}  \frac {\partial^{2} f}{\partial x_{i} \cdot  \partial x_{j}} (x,y) \cdot  u_{i} \cdot  u_{j} = \sum_{j=1}^{2}  \frac {\partial^{2} f}{\partial x \cdot  \partial x_{j}} (x,y) \cdot  u_{1} \cdot  u_{j} + \frac {\partial^{2} f}{\partial y \partial x_{j}}(x,y) \cdot  u_{2} \cdot  u_{j} = 
$$
$$
\frac {\partial^{2} f}{\partial x^{2}}(x,y) \cdot  u_{1}^{2} + \frac {\partial^{2} f}{\partial y \partial x} (x,y)\cdot u_{1}\cdot u_{2} + \frac {\partial^{2} f}{\partial x \partial y} (x,y)\cdot u_{1}\cdot u_{2} + \frac {\partial^{2} f}{\partial y^{2}}(x,y) \cdot  u_{2}^{2} = 6x \cdot  u_{1}^{2} + 0 + 0 + 18y \cdot  u_{2}^{2}
$$

## Matricea Hesiana

$$H(f)(x^{0}) = \begin{pmatrix} \frac{\partial^2 f}{\partial x_1^2}(x^0) & \frac{\partial^2 f}{\partial x_1 \partial x_2}(x^0) & \dots & \frac{\partial^2 f}{\partial x_1 \partial x_m}(x^0) \\ \frac{\partial^2 f}{\partial x_2 \partial x_1}(x^0) & \frac{\partial^2 f}{\partial x_2^2}(x^0) & \dots & \frac{\partial^2 f}{\partial x_2 \partial x_m}(x^0) \\ \vdots & \vdots & \ddots & \vdots \\ \frac{\partial^2 f}{\partial x_m \partial x_1}(x^0) & \frac{\partial^2 f}{\partial x_m \partial x_2}(x^0) & \dots & \frac{\partial^2 f}{\partial x_m^2}(x^0) \end{pmatrix}$$
### Exemplu

$$
f(x,y) = x^{3}+y^{3}
$$
$$
H(f)(x,y) = \begin{pmatrix}
6x  &  0 \\
0 & 6y
\end{pmatrix}
$$
## Teorema lui Fermat

$$
x^{0} \text{ este punct de extrem pentru f} \implies \nabla f(x^{0} ) = 0_{m}
$$
$$
daca  \nabla f(x^{0}) = 0_{m } \text{ NU rezulta ca x}^{0} \text{ este punct de extrem,ci rezulta ca este punct critic}
$$
PUNCTELE CRITICE CARE NU SUNT PUNCTE DE EXTREM SE NUMESC **PUNCTE SA**

## Forma patratica

$$\Phi(u) = \sum_{i=1}^{m} \sum_{j=1}^{m} c_{ij} \cdot u_i \cdot u_j$$



- Dacă $\Phi$ este **pozitiv definită**, înseamnă că $\Phi(u) > 0, \forall u \in \mathbb{R}^m \setminus \{0_m\}$.
    
- Dacă $\Phi$ este **negativ definită**, înseamnă că $\Phi(u) < 0, \forall u \in \mathbb{R}^m \setminus \{0_m\}$.
    
- Dacă $\Phi$ este **indefinită**, înseamnă că $\exists u, v \in \mathbb{R}^m$ a.î. $\Phi(u) < 0 < \Phi(v)$.

- diferențiala de ordinul 2 a funcției $f$ în punctul $x^0$ este exact forma pătratică asociată matricei hessiene $H(f)(x^0)$.

### Exemplu
$$
\begin{pmatrix}
2  & 3 \\
4 & 5
\end{pmatrix} \implies
\phi(u) = 2\cdot u_{1}^{2} + 7\cdot u_{1}\cdot u_{2} + 5\cdot u_{2}^{2}

$$

### 3. Criteriul lui Sylvester 

Fie $\Delta_k = \det(c_{ij})_{i,j=\overline{1,k}}$ determinanții lui Sylvester. Atunci:

1. $\Phi$ este **pozitiv definită** $\iff \Delta_k > 0, \forall k = \overline{1,m}$.
    
2. $\Phi$ este **negativ definită** $\iff (-1)^k \cdot \Delta_k > 0, \forall k = \overline{1,m}$.

3. daca nu e nici una nici alta,este nedefinita

### Exemplu
$$
\begin{pmatrix}
2  & 3  & 4 & 5 \\
4 & 5 & 6 & 7 \\
8 & 9 & 10 & 11 \\
12 & 13 & 14 & 15
\end{pmatrix} 
$$
$$
\Delta_{1} = 2 > 0;
\Delta_{2} = \begin{vmatrix}
2 & 3 \\
4 & 5 
\end{vmatrix} = -2;\Delta_{3} = \begin{vmatrix}
2 & 3 & 4 \\
4 & 5 & 6 \\
8 & 9 & 10
\end{vmatrix} = \dots \implies \text{nu este nici negativ nici pozitiv definita,cooked}
$$

### 4. Teorema pentru punctul $x^0$ 

Fie $f$ o funcție de clasă $C^2$ și $\nabla f(x^0) = 0_m$. Natura punctului $x^0$ este determinată de diferențiala de ordinul 2, $d^2f(x^0)$, care este forma pătratică asociată matricei hessiene $H(f)(x^0)$:

1. Dacă $d^2f(x^0)$ este **pozitiv definită** $\implies x^0$ este **punct de minim local**.
    
2. Dacă $d^2f(x^0)$ este **negativ definită** $\implies x^0$ este **punct de maxim local**.
    
3. Dacă $d^2f(x^0)$ este **indefinită** $\implies x^0$ este **punct șa**.


## Exercitiu

$$
1.f(x_{1},x_{2}) = x_{1}^{2} + x_{1}x_{2} + x_{2}^{2} + ax_{1} + bx_{2}
$$
$$
\nabla f(x_{1},x_{2}) = (2x_{1}+x_{2}+a,2x_{2}+x_{1}+b) = (0,0)
$$
$$
\begin{cases}
2x_{1}+x_{2}=-a \\
2x_{2}+x_{1} = -b
\end{cases} \implies 3x_{1} = -2a+b \implies x_{1} = \frac{-2a+b}{3};x_{2} = \frac{a-2b}{3}
$$
$$
H(f)(x_{1},x_{2}) = \begin{pmatrix}
2 & 1 \\
1 & 2
\end{pmatrix}
$$
$$
H(f)\left( \frac{-2a+b}{3}, \frac{a-2b}{3} \right) = \begin{pmatrix}
2 & 1 \\
1 & 2
\end{pmatrix}
$$
$$
\Delta_{1} = \begin{vmatrix}
2
\end{vmatrix} = 2>0;\Delta_{2} = \begin{vmatrix}
2 & 1 \\
1 & 2
\end{vmatrix} = 3>0 \text{ ambele mai mari ca 0 deci rezulta din Teorma lui Sylvmanca mi ai curul ca }  d^{2}f \text{ este pozitiv definita}
$$
$$
\implies \text{ punctul } \left( \frac{-2a+b}{3}, \frac{a-2b}{3} \right) \text{este un punct de minim local}
$$

$$
2.f(x,y) = x^{2}+y^{2}+(1-x)^{3}
$$
$$
\nabla f(x,y) = (0,0) \implies (2x-3(1-x)^{2},2y) = (0,0) \implies y=0;2x-3(1-2x+x^{2})=0 \implies3x^{2}-8x+3 = 0
$$
$$
\implies x_{1} = \frac{4}{3}-\frac{\sqrt{ 7 }}{3},x_{2}=\frac{4}{3}+\frac{\sqrt{ 7 }}{3}
$$
$$
H(f)(x,y) = \begin{pmatrix}
8-6x & 0 \\
0 & 2
\end{pmatrix}
$$
$$
H(f)\left( \frac{4}{3}-\frac{\sqrt{ 7 }}{3},0 \right) = \begin{pmatrix}
2\sqrt{ 7 } & 0 \\
0 & 2
\end{pmatrix};\Delta_{1} = 2\sqrt{ 7 },\Delta_{2} = 4\sqrt{ 7 };\implies d^{2}f\left( \frac{4}{3}-\frac{\sqrt{ 7 }}{3},0 \right)(u) \text{ este pozitiv definita} \implies \text{punctul } \dots \text{ este de minim local}
$$
$$
H(f)\left( \frac{4}{3}+\frac{\sqrt{ 7 }}{3},0 \right) = \begin{pmatrix}
-2\sqrt{ 7 } & 0 \\
0 & 2
\end{pmatrix} ;\Delta_{1} = -2\sqrt{ 7 };\Delta_{2} = -4\sqrt{ 7 } \implies \text{ diferentiala de ordin 2 este nedefinita deci punct sa}
$$


## Puncte de extrem conditionat

### 1. Definirea Mulțimii Restricțiilor $S$

Fie $F = (F_1, \dots, F_p) : A \to \mathbb{R}^p$ o funcție vectorială. Mulțimea $S$ este definită ca:

$$S = \{x \in A \mid F_1(x) = \dots = F_p(x) = 0\}$$

Aceasta se numește **mulțimea restricțiilor**.

Un punct $x^0 \in S$ se numește **punct de extrem condiționat** al lui $f$ relativ la $S$ dacă $x^0$ este punct de extrem local al funcției restricționate $f|_S$.

---

### 2. Metoda Multiplicatorilor lui Lagrange

Pentru a găsi aceste puncte, folosim **funcția lui Lagrange** $L: A \times \mathbb{R}^p \to \mathbb{R}$,doar daca rang(J(f)) = p


$$L(x, y) = f(x) + y_1 \cdot F_1(x) + \dots + y_p \cdot F_p(x)$$

unde numerele $(\lambda_1, \dots, \lambda_p) = \lambda$ se numesc **multiplicatorii lui Lagrange**.

---

### 3. Condiția Necesară (Teorema)

Dacă $x^0$ este un punct de extrem condiționat, atunci funcția $L$ admite un punct critic de forma $(x^0, \lambda)$. Asta înseamnă că gradientul funcției Lagrange în acel punct este zero:

$$\nabla L(x^0, \lambda) = 0_{m+p}$$

### Exemplu

$$
S = \{x,y \in \mathbb{R}^{2}|x^{2}+xy+y^{2}=1\} 
$$
$$
\text{determinati valoarea minima f}_{S} \text{ a functiei } f:\mathbb{R}^{2}\to \mathbb{R} ,f(x,y) = x+y
$$
$$
\text{fie F}(x,y) = x^{2}+xy+y^{2}-1 \implies S = \{x,y \in \mathbb{R}^{2}|F(x,y) = 0\} 
$$
$$

\text{introducem functia lui Lagrange:}L(x,y,\lambda) = f(x,y) + \lambda F(x,y) = x+y+\lambda(x^{2}+xy+y^{2}-1)
$$
$$
\nabla L(x,y,\lambda) = (0,0,0) \implies (1+2\lambda x+\lambda y,1+2\lambda y+\lambda x,x^{2}+xy+y^{2}-1) = (0,0,0) \implies
$$
$$
\begin{cases}
1+2\lambda x+\lambda y=0 \\
1+2\lambda y+\lambda x=0 \\
x^{2}+xy+y^{2}-1=0
\end{cases}\implies 1+3\lambda x = 0 \implies x = -\frac{1}{3\lambda};y = -\frac{1}{3\lambda}\implies x=y
$$
$$
x^{2}+x^{2}+x^{2}-1= 0\implies x^{2}=\frac{1}{3} \implies x=y=\pm \frac{1}{\sqrt{ 3 }}
$$
$$
f\left( \frac{1}{\sqrt{ 3 }}, \frac{1}{\sqrt{ 3 }} \right) = \frac{2}{\sqrt{ 3 }}
$$
$$
f\left( \frac{1}{\sqrt{ 3 }},-\frac{1}{\sqrt{ 3 }} \right) = 0
$$
$$
f\left( \frac{-1}{\sqrt{ 3 }}, \frac{1}{\sqrt{ 3 }} \right) = 0
$$
$$
f\left( -\frac{1}{\sqrt{ 3 }}, \frac{-1}{\sqrt{ 3 }} \right) = -\frac{2}{\sqrt{ 3 }} \implies \text{min f}_{S} = -\frac{2}{\sqrt{ 3 }}
$$
