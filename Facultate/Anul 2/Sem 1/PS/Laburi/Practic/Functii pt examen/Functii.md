# Python Libraries Cheat Sheet

Acest fișier conține toate funcțiile importante din **itertools, math, random, matplotlib.pyplot, numpy și scipy.stats** împreună cu toți parametrii posibili. 😈

---

## 1. itertools

### permutations

```python
permutations(iterable, r=None)
# iterable = listă sau alt obiect iterabil
# r = lungimea permutărilor (dacă None, folosește lungimea completă)
```

### combinations

```python
combinations(iterable, r)
# iterable = listă sau alt obiect iterabil
# r = lungimea combinațiilor
```

### product

```python
product(*iterables, repeat=1)
# repeat = câte ori să se repete produsul cartezian
```

### combinations_with_replacement

```python
combinations_with_replacement(iterable, r)
```

---

## 2. math

### factorial

```python
factorial(n)  # n >= 0
```

### perm

```python
perm(n, k)
```

### comb

```python
comb(n, k)
```

### log

```python
log(x, base=math.e)
```

### exp

```python
exp(x)
```

### dist

```python
dist(p, q)
# p, q = puncte ca liste sau tupluri
```

### ceil / floor / sqrt / sin / cos / tan / radians / degrees

```python
ceil(x), floor(x), sqrt(x)
sin(x), cos(x), tan(x)
radians(x), degrees(x)
```

---

## 3. random

### choice

```python
choice(sequence)
```

### choices

```python
choices(population, k=1, weights=None, cum_weights=None)
```

### sample

```python
sample(population, k)
```

### randint

```python
randint(a, b)
```

### randrange

```python
randrange(start, stop=None, step=1)
```

### random

```python
random()
```

### uniform

```python
uniform(a, b)
```


---

## 4. matplotlib.pyplot

### hist

```python
hist(x, bins=None, range=None, density=False, weights=None, cumulative=False)
```

### bar

```python
bar(x, height, width=0.8, bottom=None, *, align='center', orientation='vertical')
```

### plot

```python
plot(x, y, fmt='', *, data=None, **kwargs)
```

### xticks / yticks

```python
xticks(ticks, labels=None)
yticks(ticks, labels=None)
```

### axis / legend / grid / show

```python
axis([xmin,xmax,ymin,ymax])
legend(labels=None)
grid(b=True)
show()
```

---

## 5. numpy

### mean / var / std

```python
mean(array, axis=None, dtype=None, out=None)
var(array, axis=None, dtype=None, out=None)
std(array, axis=None, dtype=None, out=None)
```

### randint

```python
randint(low, high=None, size=None, dtype=int)
```

### linspace

```python
linspace(start, stop, num=50, endpoint=True, retstep=False, dtype=None)
```

### log / exp / unique / floor

```python
log(x, base=None)
exp(x)
unique(array, return_index=False, return_inverse=False, return_counts=False)
floor(x)
```

### array / arange / reshape / zeros / ones

```python
array(object, dtype=None)
arange([start,] stop[, step,], dtype=None)
reshape(a, newshape)
zeros(shape, dtype=float)
ones(shape, dtype=float)
```

---

## 6. scipy.stats

### Parametri generali

- Distribuții continue (`rv_continuous`): `*shape parameters, loc=0, scale=1`
    
- Distribuții discrete (`rv_discrete`): `*shape parameters, loc=0`
    

### Funcții generale

- `rvs(*args, loc=0, scale=1, size=1, random_state=None)`
    
- `pdf(x, *args, loc=0, scale=1)` / `pmf(x, *args, loc=0)`
    
- `cdf(x, *args, loc=0, scale=1)`
	

### Exemple Distribuții Continue

```python
from scipy.stats import norm, expon, gamma, beta

# Normal
norm.rvs(loc=0, scale=1, size=10)
norm.pdf(0, loc=0, scale=1)

# Exponential
expon.rvs(loc=0, scale=2, size=5)

# Gamma
gamma.rvs(a=2, loc=0, scale=1, size=5)

# Beta
beta.rvs(a=2, b=5, loc=0, scale=1, size=5)
```

### Exemple Distribuții Discrete

```python
from scipy.stats import binom, bernoulli, poisson, geom, nbinom

# Binomial
binom.rvs(n=10, p=0.5, loc=0, size=5)

# Bernoulli
bernoulli.rvs(p=0.3, loc=0, size=5)

# Poisson
poisson.rvs(mu=3, loc=0, size=5)

# Geometric
geom.rvs(p=0.3, loc=0, size=5)

# Negative Binomial
nbinom.rvs(n=5, p=0.5, loc=0, size=5)
```

### Alte Distribuții

- **chi2**: `df, loc=0, scale=1`
    
- **f**: `dfn, dfd, loc=0, scale=1`
    
- **t**: `df, loc=0, scale=1`
    
- **lognorm**: `s, loc=0, scale=1`
    
- **exponweib**: `a, c, loc=0, scale=1`
    
- **dweibull**: `c, loc=0, scale=1`
    
- **hypergeom**: `M, n, N, loc=0`
    
- **randint**: `low, high, loc=0`
    
- **skellam**: `mu1, mu2, loc=0`
    
- **zipf**: `a, loc=0`