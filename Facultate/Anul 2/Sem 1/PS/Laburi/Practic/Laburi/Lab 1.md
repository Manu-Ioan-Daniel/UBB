### Imports Used

```python
from random import sample 
from math import factorial 
from itertools import permutations, combinations, combinations_with_replacement
from math import perm, comb 
```

#### Exercitiul 1
**a)** Afișați o listă cu toate permutările cuvântului **word**.
```python
print(list (permutations("word")))
```
**b)** Afișați numărul total al permutărilor cuvântului **word**.
```python
print(factorial(len("word")))
```

**c)** Afișați o permutare aleatoare a cuvântului **word**.
```python
print(sample("word",4))
```
#### Exercitiul 2

Scrieți funcții care rezolvă problema anterioară pentru aranjamente și combinări

##### Aranjamente

```python
def aranjamente(string, count, numar_total = False, aleator = False):  
    if numar_total:  
        print(perm(len(string),count))  
        return  
    if aleator:  
        print(sample(string,count))  
        return  
    print(list(permutations(string,count)))

```

##### Combinari

```python
print(list (combinations_with_replacement("ABCDE",4)))
```
