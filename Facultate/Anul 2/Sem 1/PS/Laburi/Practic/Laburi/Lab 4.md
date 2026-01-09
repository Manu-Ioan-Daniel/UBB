
### Imports Used

```python
from random import randrange, choice 
from scipy.stats import bernoulli, binom, hypergeom, geom 
from matplotlib.pyplot import bar, hist, grid, show, legend, xticks
```

---

## Exercițiul 1 – Deplasare aleatoare pe axa reală

Un punct material se deplasează pe axa reală dintr-un nod spre un nod vecin la fiecare pas.  
Cu probabilitatea p∈(0,1) se deplasează la dreapta și cu probabilitatea 1−p la stânga.  
Nodurile sunt centrate în numerele întregi, iar nodul inițial este 0.

a)Simulați o astfel de deplasare pentru un număr dat de pași și o probabilitate p∈(0,1)
Returnați pozițiile curente ale punctului material la fiecare pas.

b)Simulați de 1000 de ori deplasarea de la punctul a), pentru un număr fix de pași și o probabilitate p∈(0,1).
Afișați histograma pozițiilor finale obținute.

c) Rezolvați cerința b) pentru cazul unei deplasări pe un cerc cu $n \in \mathbb {N}^{*}$

#### Rezolvare

```python
from random import randrange, choice  
from scipy.stats import bernoulli, binom, hypergeom, geom  
from matplotlib.pyplot import bar, hist, grid, show, legend, xticks  
import numpy as np  
def simulare(p,size):  
    values = bernoulli.rvs(p,size = size)  
    pozitie = 0  
    for value in values:  
        if value==0:  
            pozitie-=1  
            # print(pozitie)  
        else:  
            pozitie+=1  
            # print(pozitie)  
    return pozitie  
  
def simulare_cerc(n,p,size):  
    values = bernoulli.rvs(p, size=size)  
    pozitie = 0  
    for value in values:  
        if value == 0:  
            pozitie-=1;  
        else:  
            pozitie+=1  
        if pozitie<0:  
            pozitie=n-1  
        elif pozitie == n:  
            pozitie = 0  
    return pozitie  
n=1000  
values = []  
while n:  
    values.append(simulare(0.5,10))  
    n-=1  
hist(values,color = 'red', alpha = 0.5)  
  
n=1000  
values.clear()  
while n:  
    values.append(simulare_cerc(11,0.5,10))  
    n-=1  
hist(values,color = 'blue',alpha = 0.6)  
grid()  
show()
```

---

## Exercițiul 2 – Loto 6/49

Un jucător de **Loto 6/49** își cumpără câte un bilet pentru fiecare extragere efectuată de loteria română până când reușește să nimerească un bilet cu **cel puțin 3 numere câștigătoare**.

Se vor folosi metodele claselor `hypergeom` și `geom`.

a)Generați o listă care conține, pentru fiecare simulare, **numărul de bilete necâștigătoare**  
(bilete cu cel mult 2 numere câștigătoare) până la apariția **primului bilet câștigător**  
(bilet cu cel puțin 3 numere câștigătoare).

 b)Estimați probabilitatea evenimentului: Cel puțin 10 bilete succesive sunt necâștigătoare până când jucătorul nimerește un bilet câștigător.
 
Afișați:

- valoarea estimată prin simulare
    
- valoarea teoretică corespunzătoare
#### Rezolvare

```python

from random import randrange, choice  
from scipy.stats import bernoulli, binom, hypergeom, geom  
from matplotlib.pyplot import bar, hist, grid, show, legend, xticks  
import numpy as np  
  
  
def simulare_loto_1(n_simulari=1000):  
    N = 49  
    K = 6  
    n = 6  
    necastigatoare = []  
  
    for _ in range(n_simulari):  
        bilete = 0  
        while True:  
            x = hypergeom.rvs(N, K, n)  
            if x >= 3:  
                necastigatoare.append(bilete)  
                break  
            else:  
                bilete += 1  
  
    return necastigatoare  
  
rezultate = simulare_loto_1(100)  
print(" rezultate:", rezultate)  
  
N, K, n = 49, 6, 6  
p = 1 - hypergeom.cdf(2,N,K,n)  
print("Probabilitate teoretică:", geom.pmf(11,p)/p)

```

