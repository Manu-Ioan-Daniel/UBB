### Imports Used

```python
from random import sample 
from math import factorial 
from itertools import permutations, combinations, combinations_with_replacement
from math import perm, comb 
```

### Exercitiul 1
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
### Exercitiul 2

Scrieți funcții care rezolvă problema anterioară pentru aranjamente și combinări

#### Aranjamente

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

#### Combinari

```python
def combinari(string, count, numar_total = False, aleator = False):  
    if numar_total:  
        print(comb(len(string),count))  
        return  
    if aleator:  
        print(sample(list(combinations(string,count)),1))  
        return  
    print(list(combinations(string,count)))  
combinari("word",2,aleator=True)
```
### Exercitiul 3

```python
print(list (combinations_with_replacement("ABCDE",4)))
```

### Exercitiul 4

Scrieți un program care afișează în câte moduri se pot așeza 5 persoane pe 12 scaune, astfel încât între oricare două persoane să existe cel puțin un scaun liber.

```python
print(perm(5) * comb(12-4,5))
#12-4 pentru ca sunt 4 scaune ce trebuie puse intre persoane,deci nu le luam in calcul
```



