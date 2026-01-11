### Imports Used

``` python
from random import randrange, choice
from matplotlib.pyplot import bar, hist, grid, show, legend
from scipy.stats import binom
```




---

### Exercitiul 2

Se simulează aruncarea unui zar de 500 de ori. Afișați histograma frecvențelor relative ale numerelor obținute și barele corespunzătoare probabilităților teoretice, înlocuind semnele de întrebare din codul următor:

#### Rezolvare

```Python
from random import randrange
from matplotlib.pyplot import bar, hist, grid, show, legend

data = [randrange(1,7) for _ in range(500)]
hist(data, 6,range = (0.5,6.5), density = True, rwidth = 0.9, color = 'green', edgecolor = 'black',
     alpha = 0.5, label = 'frecvente relative')

distribution = dict([(i,1/6) for i in range(1,7)])

bar(distribution.keys(), distribution.values(), width = 0.85, color = 'red', edgecolor = 'black',
    alpha= 0.6, label = 'probabilitati')

legend(loc = 'lower left')
grid()
show()
```

#### Output

![[Pasted image 20260109224757.png]]

---

### Exercitiul 3

Într-o urnă sunt 6 bile cu cifra 1 inscripționată și 4 bile cu cifra 0. Se extrag aleator cu returnare 5 bile din urnă. Fie $X$ variabila aleatoare care indică suma numerelor de pe bilele extrase.

**a)** Generați o listă de 1000 de valori pentru $X$.

**b)** Afișați histograma frecvențelor relative și barele corespunzătoare valorilor teoretice.

**c)** Estimați probabilitatea $P(2 < X \leq 5)$ și afișați valoarea teoretică. Folosiți metodele `rvs`, `pmf`, `cdf` ale clasei `binom`:

```
from scipy.stats import binom
help('scipy.stats.binom')
```

```python
from random import randrange  
from scipy.stats import binom 
import numpy as np 
from matplotlib.pyplot import bar, hist, grid, show, legend  
  
values = binom.rvs(n=5,p=0.6,size=1000)  
bin_edges = [k-0.5 for k in range(0,7)]  
hist(values,bin_edges,color = 'blue',density = True,rwidth = 0.85)  
dist = {k:binom.pmf(k,n = 5,p = 0.6) for k in range(0,6)}  
bar(dist.keys(),dist.values(),color='red', alpha = 0.6)  
grid()  
show()
estimate = np.mean((values>2) & (values<=5))
teoretic = 1 - binom.cdf(2,5,0.6)
print(estimate)
print(teoretic)
```

#### Output

![[Pasted image 20260109231907.png]]
```
0.661
0.6825599999999998
```