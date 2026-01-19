
$$f(x) = \begin{cases} b \cdot e^{b(1-x)}, & x > 1 \\ 0, & x \leq 1, \end{cases}$$


a) Sa se afle in functie de b $P(\{X > 2\} \cup \{X < 3\})$, $P(X < 2)$.
b) Avem datele statistice $2.5, 2.3, 3.1, 3.2, 1.4$. Folosind metoda verosimilității maxime, să se determine valoarea parametrului necunoscut $b$.
$$
a)\int_{-\infty}^{\infty} f(t) \, dt = 1\implies \int_{1}^{\infty} be^{b(1-t)} \, dt = 1\implies-e^{b(1-t)}\Big |_{1}^{\infty} = 1 \implies b>0
$$
$$
F(x) = \begin{cases}
0, & x\leq1 \\
1-e^{b(1-x)}, & x>1
\end{cases}
$$
$$
P(X>2 \cup X<3) = P(X>2) + P(X<3) - P(2<X<3) = 1-F(2) + F(3) - F(3)+F(2) =1
$$
$$
b) \overline {x_{5}} = 2.5
$$
$$
L(x_{1},x_{2},\dots x_{n};b) = \prod_{k=1}^{5} f(x_{k}) = b^{5} \cdot  e^{5b} \cdot  e^{-b\sum_{k=1}^{5} x_{k}}
$$
$$
\ln L(x_{1},x_{2},\dots x_{n};b) = 5\ln b + 5b -b\sum_{k=1}^{5} x_{k}
$$
$$
\frac {\partial \ln L}{\partial b} = 0\implies \frac{5}{b} + 5 - \sum_{k=1}^{5}x_{k} = 0\implies 5+5b-b\sum_{k=1}^{5} x_{k} = 0 \implies   b = \frac{5}{\sum_{k=1}^{5} x_{k} -5} = \frac{1}{\overline {x_{5}} - 1}
$$
$$
\frac {\partial^{2} \ln L}{\partial b^{2}} = -\frac{5}{b^{2}} <0 \implies \text{un estimator pentru b este } \hat{b}(x_{1},x_{2},\dots x_{5}) = \frac{1}{\overline {x_{5}}-1}
$$
$$
b \approx  \hat{b}(2.5,2.3.3.1,3.2,1.4) = \frac{1}{1.5} = \frac{2}{3}
$$


