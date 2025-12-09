
```python
#Exemplu 1  
import numpy as np  
n=4  
r=np.random.rand(n) #returneaza o lista cu n numere random  
print(n,"valori aleatoare din intervalul (0,1) ",r)  
r2=np.random.randint(1,5,size=n+3) #returneaza o lista cu n+3 numere random intre 1 si 5  
print(n+3,"valori aleatoare ",r2)  
lista=["A","B","C","D","E"]  
print(n,"extrageri aleatoare cu returnare: ",np.random.choice(lista,size=n,replace=True)) #acelasi element poate aparea de mai multe ori,adica alegem un element si il bagam inapoi in pool  
print(n,"extrageri aleatoare cu returnare: ",np.random.choice(lista,size=n,replace=False)) #toate elementele sunt diferite,un elemnt o data ales nu mai poate fi ales din nou  
  
#Exemplu 2  
n=30  
r=np.random.randint(1,7,size=n)  
print(n,"valori:",r)  
print("r==2 este defapt un array de valori boolene:",r==2)  
x=sum(r==2) #aici facem suma de acel array de valori boolene,deci numara toate elementele care sunt egale cu 2 basically,pt ca la fiecare true adauga 1  
print("Rezultat=",x)
```