
# **1. itertools**

### **permutations**

**Sintaxă generală:**

```python
permutations(iterable, r=None)
```

- `iterable` = listă sau alt obiect iterabil
    
- `r` = lungimea permutărilor (dacă None, folosește lungimea completă)
    

**Exemplu:**

``` python 
from itertools import permutations 
list(permutations([1,2,3], 2)) # Output: [(1,2),(1,3),(2,1),(2,3),(3,1),(3,2)]
```

### **combinations**

**Sintaxă generală:**

`combinations(iterable, r)`

- `iterable` = listă sau alt obiect iterabil
    
- `r` = lungimea combinațiilor
    

**Exemplu:**

``` python
from itertools import combinations 
list(combinations([1,2,3], 2)) # Output: [(1,2),(1,3),(2,3)]
```

---

# **2. math**

### **factorial**

`factorial(n)`

- `n` = întreg ≥ 0
    

**Exemplu:**

```python
from math import factorial 
factorial(5)  # 120
```

### **perm**

`perm(n, k)`

- `n` = total elemente
    
- `k` = câte luăm
    

**Exemplu:**

```python
from math import perm 
perm(5, 2)  # 20
```

### **comb**

`comb(n, k)`

- combinări n luate câte k
    

**Exemplu:**

```python
from math import comb
comb(5, 2)  # 10
``` 
`

### **log**

```python
log(x, base=math.e)
```

- `x` = număr
    
- `base` = opțional, default e
    

**Exemplu:**

```python
from math import log 
log(100, 10)  # 2.0
```

### **exp**

```python
exp(x)
```

**Exemplu:**

```python
from math import exp 
exp(2)  # 7.389056...
```

### **dist**

```python
dist(p, q)
```

- `p`, `q` = puncte ca liste sau tupluri

**Exemplu:**

```python
from math import dist 
dist([0,0],[3,4])  # 5.0
```

---



---

# 3. random

### choice

```python
# Sintaxă generală:
choice(sequence) -> alege un element aleator din sequence

from random import choice
choice([1,2,3])  # ex: 2
```

### choices

```python
# Sintaxă generală:
choices(population, k=1) -> alege k elemente cu posibilitate de repetare

from random import choices
choices([1,2,3], k=2)  # ex: [1,3]
```

### sample

```python
# Sintaxă generală:
sample(population, k) -> alege k elemente fără repetare

from random import sample
sample([1,2,3], 2)  # ex: [2,3]
```

### randint

```python
# Sintaxă generală:
randint(a, b) -> întreg aleator între a și b inclusiv

from random import randint
randint(1,6)  # ex: 4
```

### randrange

```python
# Sintaxă generală:
randrange(start, stop, step=1) -> întreg aleator în interval cu pas

from random import randrange
randrange(0,10,2)  # ex: 4
```

### random

```python
# Sintaxă generală:
random() -> float aleator între 0 și 1

from random import random
random()  # ex: 0.3745...
```

---

# 4. matplotlib.pyplot

### hist

```python
# Sintaxă generală:
hist(x, bins=None) -> histogramă pentru datele x

from matplotlib.pyplot import hist, show
hist([1,2,2,3,3,3])
show()
```

### bar

```python
# Sintaxă generală:
bar(x, bins, height) -> grafic cu bare

from matplotlib.pyplot import bar, show
bar([1,2,3],[4,5,6])
show()
```

### plot

```python
# Sintaxă generală:
plot(x, y) -> grafic liniar

from matplotlib.pyplot import plot, show
plot([1,2,3],[4,5,6])
show()
```

### xticks / yticks

```python
# Sintaxă generală:
xticks(ticks), yticks(ticks) -> setează valorile axelor

from matplotlib.pyplot import xticks, yticks
xticks([0,1,2])
yticks([0,5,10])
```

### axis / legend / grid / show

```python
# Sintaxă generală:
axis([xmin,xmax,ymin,ymax]) -> limitele axelor
legend(labels) -> etichete pentru grafice
grid(True/False) -> afișează/restrânge grila
show() -> afișează grafica

from matplotlib.pyplot import axis, legend, grid, show
axis([0,3,0,6])
legend(["linie"])
grid()
show()
```

---

# 5. numpy

### mean / var / std

```python
# Sintaxă generală:
mean(array), var(array), std(array) -> medie, varianță, deviație standard
 
from numpy import mean, var, std
mean([1,2,3])  # 2.0
var([1,2,3])   # 0.6666
std([1,2,3])   # 0.8164
```

### randint

```python
# Sintaxă generală:
randint(low, high=None, size=None) -> valori întregi aleatoare

from numpy import randint
randint(0,10,5)  # ex: [2,5,7,1,3]
```

### linspace

```python
# Sintaxă generală:
linspace(start, stop, num=50) -> generează num valori uniform distribuite

from numpy import linspace
linspace(0,1,5)  # [0.0,0.25,0.5,0.75,1.0]
```

### log / exp / unique / floor

```python
# Sintaxă generală:
log(x), exp(x), unique(array), floor(x)

from numpy import log, exp, unique, floor
log(10)           # 2.3025...
exp(2)            # 7.389...
unique([1,1,2])   # [1,2]
floor(2.7)        # 2
```

---

# 6. scipy.stats

### rvs / pdf / pmf / cdf

```python
# Sintaxă generală:
dist.rvs(parametri, size=N)  -> generează valori aleatoare
dist.pdf(x, parametri)       -> densitate (continuu)
dist.pmf(x, parametri)       -> probabilitate (discret)
dist.cdf(x, parametri)       -> distribuție cumulată

from scipy.stats import randint, bernoulli, binom, norm, expon, uniform
```

### Exemple Discrete

```python
# randint
randint.rvs(1,7, size=5)        # 5 valori între 1 și 6

# bernoulli
bernoulli.pmf(1, 0.3)           # P(X=1)

# binom
binom.cdf(3, n=10, p=0.5)       # P(X≤3)
```

### Exemple Continue

```python
# norm
norm.pdf(0, loc=0, scale=1)      # densitate normală
norm.cdf(1.96, loc=0, scale=1)   # P(X≤1.96)

# expon
expon.rvs(scale=2.0, size=5)     # 5 valori exponențiale

# uniform
uniform.rvs(loc=0, scale=10, size=3)  # valori uniforme
```

> ⚠️ Pentru distribuțiile continue: loc = media/shift, scale = deviație/scale 😈

