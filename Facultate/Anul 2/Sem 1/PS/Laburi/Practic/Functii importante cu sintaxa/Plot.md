
```python
from matplotlib.pyplot import plot, grid, title, show, xlabel, ylabel

# Titlul graficului
title('Plot test') 

# Definirea datelor pentru axe
xs = range(10) 
ys = [x*x/100 for x in xs] 

# Generarea graficului cu marker de tip stea roșie ('r*')
plot(xs, ys, 'r*') 

# Activarea grilei pentru o mai bună lizibilitate
grid() 

# Afișarea ferestrei cu graficul
show()
```