
## Definiții

**Def. 1:** Un algoritm pe parcursul executării căruia se iau anumite decizii aleatoare este numit **algoritm aleator (randomizat)**.  
- Durata de execuție, spațiul de stocare și rezultatul obținut sunt **variabile aleatoare**, chiar dacă se folosesc aceleași valori de input.  
- La anumite tipuri de algoritmi, corectitudinea este garantată doar cu o anumită **probabilitate**.  

### Tipuri de algoritmi aleatori

- **Algoritm de tip Las Vegas** → returnează la fiecare execuție **rezultatul corect**, indiferent de alegerile aleatoare făcute; durata de execuție este o **variabilă aleatoare**.  
  **Exemplu:** Random QuickSort  

- **Algoritm de tip Monte Carlo** → rezultatele obținute sunt corecte doar cu o anumită **probabilitate**.  
  - Se examinează probabilitatea ca rezultatul să fie corect.  
  - Probabilitatea de eroare poate fi redusă semnificativ prin execuții independente repetate.  
  **Exemplu:** testul Miller-Rabin, care verifică dacă un număr natural este prim sau compus.  
  - Testul returnează fie:
    - „Numărul este sigur un număr compus”  
    - „Numărul este probabil un număr prim”
    
## **Exercitiu**

 1.Fie S un vector cu 60 de elemente,din multimea {0,1,2} (ordinea este necunoscuta,se presupune ca sirul contine cel putin un 0).De care tip este urmatorul algoritm?
```python
import numpy as np

N = 60
S = np.random.randint(1, 3, size=N) 
S[np.random.randint(0, N)] = 0        
k = 1
i = np.random.randint(low=0, high=N)

while S[i] != 0:
    print("iteratia:", k)
    print("S[", i, "]=", S[i])
    i = np.random.randint(low=0, high=N)
    k = k + 1

if S[i] == 0:
    print("iteratia:", k)
    print("S[", i, "]=", S[i])
    print("S-a gasit aleator un 0.")

```
#### **Raspuns**
- Algoritm de tip **Las Vegas**,mereu returneaza raspunsul corect.

---
### Versiunea Monte Carlo a problemei:

```python
import numpy as np

print("A doua versiune")

N = 50
S = np.random.randint(3, size=N)
print(S)
# Un vector cu N elemente, din mulțimea {0,1,2}

M = 3  # Numărul maxim de iteratii M > 1
a = True

for k in range(M):
    print("Iteratia:", k+1)
    i = np.random.randint(low=0, high=N)
    print("S[", i, "] =", S[i])
    
    if S[i] == 0:
        print("La iteratia", k+1, "s-a gasit aleator un 0.")
        a = False
        break

if a:
    print("In", k+1, "iteratii nu s-a gasit niciun 0.")

```

- Daca 0 este gasit,atunci algoritmul se incheie cu rezultatul corect,altfel algoritmul nu gaseste niciun 0

