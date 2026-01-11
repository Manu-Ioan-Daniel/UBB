Iată enunțurile problemelor din documentul PDF, structurate într-un format Markdown clar.

Puteți copia textul de mai jos și să îl salvați într-un fișier cu extensia `.md`.

---

# Probleme Test Practic

## Problema 1

Timpul de întârziere în minute al unui student la un examen este o variabilă aleatoare $T$ cu distribuţia exponenţială cu media 2 minute, adică $Exp(1/2)$.Independentă de $T$, nota obținută de student la examen are distribuţia uniformă discretă, adică $Unid(10)$. Profesorul scade un punct din nota studentului pentru fiecare minut întreg din timpul de întârziere al studentului (dacă se obține o notă mai mică decât 1, atunci profesorul consideră nota finală 1. Fie $N$ nota finală a studentului. 


**Cerințe:**

- **a)** Simulaţi 10000 de valori pentru $T$, apoi afişați o histogramă a frecvențelor relative cu 10 bare pe intervalul $[0, 10]$ și graficul funcției de densitate pe acest interval. 
    
- **b)** Estimaţi $P(N \ge 5)$, apoi afişați probabilitatea teoretică. 
    

### Rezolvare

```python
  
from numpy import exp, mean, linspace,array,floor  
from matplotlib.pyplot import hist, plot, grid, show  
from scipy.stats import expon, randint  
  
valori = expon.rvs(size=10000, scale=2)  
hist(valori, 10, range=(0, 10), rwidth=0.9, density=True)  
x = linspace(0, 10, 500)  
plot(x, expon.pdf(x, scale=2), color='red')  
grid()  
show()  
note_sim = array([randint.rvs(1, 11) - floor(t) for t in valori])  
print(mean(note_sim >= 5))  
print(sum([randint.pmf(note,1,11) * expon.cdf(note-4,scale=2) for note in range(5,11)]))

```
---

## Problema 2

Dintr-o populaţie se alege aleator, cu returnare, câte o persoană până când se găseşte o persoană cu înălțimea mai mare decât 1,90 m. Fie $X$ numărul de persoane alese. 
Ştiind că înălțimea unei persoane alese aleator urmează distribuţia normală cu media 1,65 m şi deviaţia standard 0,20 m: 

**Cerințe:**

- **a)** Generaţi 10000 de valori pentru $X$, apoi afişați o histogramă a frecvenţelor relative pentru valorile: $1, 2, ..., 10$. 
    
- **b)** Estimaţi $P(X > 10)$, apoi afişați probabilitatea teoretică.
### Rezolvare

$$
X \sim Geom(p)
$$

```python
from scipy.stats import norm,geom  
from matplotlib.pyplot import hist,show,grid,xticks  
from numpy import mean  
  
p = 1- norm.cdf(x = 190,loc = 165,scale = 20)  
X = geom.rvs(p,size = 10000)  
bin_edges = [k + 0.5 for k in range(0,11)]  
hist(X,bin_edges,density = True,rwidth = 0.9)  
xticks(range(1,11))  
grid()  
show()  
print(mean(X>10))  
print(1-geom.cdf(10,p))

```