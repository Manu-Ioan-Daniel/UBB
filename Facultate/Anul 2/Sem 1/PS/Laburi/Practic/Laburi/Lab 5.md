
# Simulare variabile aleatoare – Metoda inversei

## Exercițiul 1 – Variabilă aleatoare discretă

Realizați un program care generează $N$ numere pseudo-aleatoare pentru variabila aleatoare discretă $X$, care are distribuția de probabilitate:

$$
X \sim
\begin{pmatrix}
x_1 & x_2 & \dots & x_n \\
p_1 & p_2 & \dots & p_n
\end{pmatrix}
$$

Se vor folosi instrucțiunile:

```python
from scipy.stats import uniform
```

### Aplicație – Grupe sanguine

Conform statisticilor medicale:

- 46% dintre oameni au grupa sanguină **0**
- 40% au grupa sanguină **A**
- 10% au grupa sanguină **B**
- 4% au grupa sanguină **AB**

Simulați de $N$ ori observarea grupei sanguine a unei persoane alese aleator.
Cerințe:
- Afișați frecvența relativă de apariție a fiecărei grupe sanguine
- Afișați histograma datelor obținute
- Suprapuneți barele corespunzătoare probabilităților teoretice

Instrucțiuni utile:

```python
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks, yticks
```
#### Rezolvare

```python

grupari = ['0','A','B','AB']  
prob = [0.46,0.40,0.10,0.04]  
def simulare(n):  
  
    grupe = {'0': 0, 'A': 0, 'B': 0, 'AB': 0}  
    valori = uniform.rvs(size = n)  
    gay = []  
    for valoare in valori:  
        if valoare<=0.46:  
            grupe['0']+=1  
        elif valoare<=0.86:  
            grupe['A']+=1  
        elif valoare <=0.96:  
            grupe['B']+=1  
        else:  
            grupe['AB']+=1  
    frecv = [grupe[grupa]/n for grupa in grupe]  
    return frecv  
  
valori = simulare(100)  
bar(grupari,valori,color = 'red')  
bar(grupari,prob,color = 'black',alpha = 0.5)  
grid()  
show()

```
---

## Exercițiul 2 – Variabilă aleatoare continuă (Distribuția exponențială)

Realizați un program care generează $N$ numere pseudo-aleatoare pentru variabila aleatoare:

$$
X \sim Exp(\alpha), \quad \alpha > 0
$$

Se vor folosi instrucțiunile:

```python
from scipy.stats import uniform
from math import log
```

### Aplicație – Timp de printare

Timpul $T$ (în secunde) necesar ca o imprimantă să printeze un afiș are distribuție exponențială cu parametrul:

$$
\alpha = \frac{1}{12}
$$

Simulați de $N$ ori timpul de printare al unui afiș.

### Rezolvare

```python
def genereaza(n,alpha):
	U = uniform.rvs(size = n)
	return [-1/alpha * log(1-u) for u in U]

```

### i) Histogramă și densitate teoretică

Cerințe:
- Afișați o histogramă cu 12 bare pe intervalul $[0,60]$
- Reprezentați pe același grafic funcția de densitate teoretică

Cod schelet:

```python
from matplotlib.pyplot import show, hist, grid, legend, xticks, plot  
from scipy.stats import expon
def simulare(n,alpha):  
    return expon.rvs(size=n,scale=1/alpha)  
  
alpha = 1/12  
data = simulare(1000,alpha)  
  
hist(data, bins=12, density=True, range=(0, 61))  
  
x = range(60)  
plot(x, expon.pdf(x, loc=0, scale=1/alpha), 'r-')  
  
xticks(range(0, 60, 5))  
grid()  
show()
```

### ii) Estimarea unei probabilități

Estimați probabilitatea evenimentului:

$$
E = \{ T \ge 5 \}
$$

Cerințe:
- Estimați probabilitatea prin simulare
- Afișați valoarea teoretică a probabilității $P(E)$

### Rezolvare

```python
def simulare(n,alpha):  
    values = expon.rvs(size=n,scale=1/alpha)  
    return np.mean(sum(values >= 5))  
p_teoretica = 1-expon.cdf(5,scale = 12)  
p_simulare = simulare(1000000,1/12)  
print(p_teoretica)  
print(p_simulare)

```

---

### II. Distribuție continuă

Fie $X$ o variabilă aleatoare continuă cu funcția de repartiție $F$ inversabilă pe $(0,1)$.

**Input:** funcția $F^{-1}$ și numărul $N$.

Pași:
- Se generează $U_i \sim U(0,1)$
- Se definește:

$$
X_i = F^{-1}(U_i)
$$

### Cazul distribuției exponențiale

Funcția de densitate:

$$
f(t) =
\begin{cases}
\alpha e^{-\alpha t}, & t > 0 \\
0, & t \le 0
\end{cases}
$$

Funcția de repartiție:

$$
F(x) =
\begin{cases}
1 - e^{-\alpha x}, & x > 0 \\
0, & x \le 0
\end{cases}
$$

Funcția inversă:

$$
F^{-1}(u) = -\frac{1}{\alpha} \log(1 - u), \quad u \in (0,1)
$$
