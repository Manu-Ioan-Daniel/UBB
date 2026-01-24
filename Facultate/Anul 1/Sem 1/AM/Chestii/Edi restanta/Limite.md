


$$
1.x_{n} = \frac{\sqrt[3]{ n }}{3^{n}};\lim_{ n \to \infty } x_{n } = 0
$$
$$
2.x_{n} = \frac{\ln(1+2^{n})}{\ln (1+3^{n})};\lim_{ n \to \infty } x_{n} = \lim_{ n \to \infty } \frac{\ln\left( 2^{n}\left( \frac{1}{2^{n}}+1 \right) \right)}{\ln\left( 3^{n}\left( \frac{1}{3^{n}}+1 \right) \right)} = \lim_{ n \to \infty } \frac{\ln(2^{n})}{\ln(3^{n})} = \frac{\ln2}{\ln3}
$$
$$
3.x_{n} = \frac{a^{n}}{n},a>0;\lim_{ n \to \infty } x_{n} = \begin{cases}
\infty, & a>1 \\
0, & a\leq1 
\end{cases}
$$
$$
4.x_{n} = n^{k}\cdot a^{n},a\in(-1,1),k \in \mathbb{N};\lim_{ n \to \infty } x_{n} =\frac{n^{k}}{\frac{1}{a^{n}}} = 0
$$
$$
5.x_{n} = n^{3}(\sqrt{ n^{2}+\sqrt{ n^{4}+1 } }-n\sqrt{ 2 })
$$
$$
\lim_{ n \to \infty } x_{n} = \lim_{ n \to \infty } \frac{n^{3}(n^{2}+\sqrt{ n^{4}+1 }-2n^{2})}{\sqrt{ n^{2}+\sqrt{ n^{4}+1 } }+n\sqrt{ 2 }} = \frac{n^{3}}{(\sqrt{ n^{2}+\sqrt{ n^{4}+1 } }+n\sqrt{ 2 })\cdot (\sqrt{ n^{4}+1 } +n^{2})} = \frac{1}{4\sqrt{ 2 }}
$$

$$
6.x_{n} = (n+1)^{2/3} - (n-1)^{2/3} = \sqrt[3]{ (n+1)^{2} } - \sqrt[3]{(n-1)^{2} } = \frac{(n+1)^{2} - (n-1)^{2}}{\sqrt[3]{ (n+1)^{4} }+\sqrt[3]{ (n^{2}-1)^{2} } + \sqrt[3]{ (n-1)^{4} }}  = 0
$$$$
a^{3}-b^{3} = (a-b)(a^{2}+ab+b^{2});a = \sqrt[3]{ (n+1)^{2} };b = \sqrt[3]{ (n-1)^{2} }
$$

$$
7.x_{n} = n\ln(n^{2}+1) - n\ln(n^{2}+2) 
$$
$$
\lim_{ n \to \infty } x_{n} = n\ln\left( \frac{n^{2}+1}{n^{2}+2} \right)  = \ln \left(\frac{n^{2}+1}{n^{2}+2}\right)^{n}
$$
$$
\lim_{ n \to \infty } \left(\frac{n^{2}+1}{n^{2}+2}\right)^{n} = \lim_{ n \to \infty } \left( 1+ \frac{-1}{n^{2}+2} \right)^{n} = \lim_{ n \to \infty } \left(\left(\left( 1 + \frac{-1}{n^{2}+2} \right)^{-n^{2}-2}\right)^{(-1)/n^{2}+2}\right)^{n} = e^{\lim_{ n \to \infty } -n/(n^{2}+2)} = e^{0} = 1
$$
$$
\lim_{ n \to \infty } (1+f(n))^{1/f(n)} = e
$$


