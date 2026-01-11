# Laboratorul 6 -- Simulări variabile aleatoare

## 1. Simularea distribuției normale

Se consideră distribuția normală cu parametrii: - media: $(m = 165)$ cm
- deviația standard: $(\sigma = 10)$ cm

Se vor simula de $n \in \{1000, 2000, 5000\}$ ori valorile înălțimii
unei persoane alese aleator.

### Cerințe

i)  

-   Afișați o histogramă cu 16 bare pe intervalul $[130, 210]$ pentru
    frecvențele relative ale datelor obținute
-   Suprapuneți pe același grafic funcția de densitate teoretică

ii) 

-   Afișați valoarea medie
-   Deviația standard
-   Proporția valorilor aflate în intervalul $[160, 170]$

Comparați rezultatele obținute cu valorile teoretice corespunzătoare.

### Rezolvare

```python
from scipy.stats import norm
from numpy import mean, std, linspace
from matplotlib.pyplot import show, hist, grid, legend, xticks, plot
def showHist(data,color,label):  
  
    print(label + " simulation result: " + str(mean(data)))  
    print(label + " theoretical result: " + str(norm.mean(loc = 165,scale = 10)))  
    print(label + " deviata standard simulata " + str(data.std()))  
    print(label + " deviatia standard teoretica: " + str(norm.std(loc = 165,scale=                                 10)))  
    print(label + " proprotia valorilor simulata: " + str(100 * mean((data<=170) &                             (data>=160))))  
    print(label + " proportia valorilor teoretica: " + str(100 * (norm.cdf(170,loc = 165,scale = 10) - norm.cdf(160,loc = 165,scale = 10))))  
    hist(data, 16, density=True, alpha=0.6, rwidth=0.9, range=(130, 210),                                               color=color, label=label)  
    x = linspace(130,210,500)  
    plot(x,norm.pdf(x,loc = 165,scale = 10),color = 'black')  
    grid()  
    legend()  
    show()  
a = norm.rvs(size = 1000,loc = 165,scale = 10)  
b = norm.rvs(size = 2000,loc = 165,scale = 10)  
c = norm.rvs(size = 5000,loc = 165,scale = 10)  
showHist(a,'red','n=1000')  
showHist(b,'blue','n=2000')  
showHist(c,'yellow','n=5000')

```

------------------------------------------------------------------------

## 2. Sistem cu două imprimante

Un computer este conectat la două imprimante: - 
- I1, aleasă cu probabilitatea $0.4$
- I2, aleasă cu probabilitatea $0.6$
Timpul de printare: - $T_1 \sim Exp(1/5)$
- $T_2 \sim Unif[4,6]$

Un inginer solicită printarea unui poster A2.

### Cerințe

a)  

-   Estimați valoarea medie și deviația standard pentru timpul de
    printare

b)  

-   Estimați probabilitatea ca timpul de printare să fie mai mic de $5$
    secunde

c)  

-   Determinați probabilitatea teoretică de la punctul b)

### Rezolvare

``` python
from scipy.stats import expon, uniform  
from numpy import mean, std, multiply  
  
imp1 = expon.rvs(size = 5000,loc = 0,scale = 5)  
imp2 = uniform.rvs(size = 5000,loc = 4,scale = 2)  
p = uniform.rvs(size = 5000)  
data = imp1 * (p<0.4) + imp2 * (p>=0.4)  
print(mean(data))  
print(std(data))  
print(mean(data<5))  
print(0.4 * expon.cdf(5,loc= 0,scale = 5) + 0.6 * uniform.cdf(5,loc = 4,scale =2))
```


------------------------------------------------------------------------

## 3. Metoda Monte Carlo pentru integrare

Se cere estimarea integralei:

$$
\int_{-1}^{3} e^{-x^2} \, dx
$$

folosind metoda Monte Carlo.

### Descrierea metodei

Fie $g : [a,b] \to [0, \infty)$ o funcție continuă.

-   Se generează $U_1, \dots, U_n \sim Unif[a,b]$ independente
-   Se definește $X_k = g(U_k)$
-   Atunci:

$$
\int_a^b g(x) \, dx \approx (b-a) \cdot \frac{1}{n} \sum_{k=1}^n g(U_k)
$$

### Rezolvare

``` python
from scipy.stats import uniform  
from numpy import exp, mean  
from scipy.integrate import quad  
import math  
  
def g(x):  
    return exp(-x*x)  
def aproximare(n,a,b):  
    interval = uniform.rvs(size=n, loc = a,scale = (b-a))  
    return (b-a) * mean([g(x) for x in interval])  
  
print(aproximare(5000,-1,3))  
print(quad(g,-1,3))

```

