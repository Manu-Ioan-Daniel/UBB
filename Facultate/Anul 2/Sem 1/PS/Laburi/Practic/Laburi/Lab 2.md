
### Imports Used

```Python
from random import randint, random, sample
from math import dist, pi
import matplotlib.pyplot as plt
```

---

### Exercitiul 1

**a)** Scrieți o funcție care estimează prin simulări repetate probabilitatea ca într-un grup de $n$ persoane cel puțin două să aibă aceeași zi de naștere, pentru $n \in \mathbb{N}^*$ dat ca parametru de intrare.
#### Rezolvare
```python
from random import randint, random, sample  
import numpy as np  
  
def simulare(n):  
    S = 10000  
    values = []  
    favorable = 0  
    while S > 0:  
  
        zile_nastere = np.random.randint(1, 366, size=n)  
        if len(zile_nastere) != len(set(zile_nastere)):  
            favorable+=1  
        values.append(favorable/n)  
        S -= 1  
    return favorable/10000;  
  
print(simulare(70))
		

```

**b)** Scrieți o funcție care calculează probabilitatea (teoretică) ca într-un grup de $n$ persoane cel puțin două să aibă aceeași zi de naștere.

#### Rezolvare

```python

def teoretica(n):  
    p = 1  
    for i in range(n):  
        p *= (365-i)/365  
    return round((1-p) * 100,2)  
print(teoretica(50))

```

**c)** Reprezentați grafic funcțiile (estimarea și valoarea teoretică) pentru $n \in \{2, 3, \dots, 50\}$.

#### Rezolvare

```python
from matplotlib.pyplot import plot, grid, title, show
title('Plot test')  
xs = range(2,51)  
ys = [simulare(x) for x in xs]  
ys2 = [teoretica(x) for x in xs]  
  
plot(xs,ys)  
plot(xs,ys2)  
grid()  
show()


```

---

### Exercitiul 2

**a)** Generați $N \in \{500, 1000, 2000\}$ puncte uniform aleatoare într-un pătrat. Afișați frecvența relativă a punctelor care:

- **i)** sunt în interiorul cercului tangent laturilor pătratului.
		
- **ii)** sunt mai apropiate de centrul pătratului decât de vârfurile pătratului.
    
- **iii)** formează cu vârfurile pătratului două triunghiuri ascuțitunghice și două triunghiuri obtuzunghice.

**b)** Reprezentați grafic pătratul și punctele pentru fiecare caz menționat anterior.

**c)** Comparați frecvențele relative obținute prin simulare cu probabilitățile geometrice corespunzătoare calculate teoretic.

#### Rezolvare

```python

from math import dist,pi  
from matplotlib.pyplot import axis  
  
def square_points(n,cerinta):  
    axis('square')  
    axis((0,1,0,1))  
    count=0  
    A,B,C,D,E=[0,0],[1,0],[1,1],[0,1],[0.5,0.5]  
    match cerinta:  
        case 1:  
            for i in range(n):  
                P=[random(), random()]  
                if dist(E,P)<=0.5:  
                    count+=1  
                    plot(P[0],P[1],'rx')  
                else:  
                    plot(P[0],P[1],'bo')  
            return count/n  
        case 2:  
            for i in range(n):  
                P=[random(), random()]  
                if dist(P,E)<min(dist(P,A),dist(P,B),dist(P,C),dist(P,D)):  
                    count+=1  
                    plot(P[0],P[1],'rx')  
                else:  
                    plot(P[0],P[1],'bo')  
            return count/n  
        case 3:  
            for i in range(n):  
                P=[random(), random()]  
                tri=((dist(P,A)**2+dist(P,D)**2<1) + (dist(P,D)**2+dist(P,C)**2<1) + (dist(P,C)**2+dist(P,B)**2<1) + (dist(P,B)**2+dist(P,A)**2<1))  
                if tri>=2:  
                    count+=1  
                    plot(P[0],P[1],'rx')  
                else:  
                    plot(P[0],P[1],'bo')  
            return count/n  
        case _:  
            return "Cerinta invalida"  
  
print(square_points(1000,1))  
print(pi/4)  
  
print(square_points(1000,2))  
print(1/2)  
  
print(square_points(100000,3))  
print(pi/2-1)

```
    

#### Output

![[Pasted image 20260109213243.png]]
![[Pasted image 20260109213334.png]]
![[Pasted image 20260109213408.png]]