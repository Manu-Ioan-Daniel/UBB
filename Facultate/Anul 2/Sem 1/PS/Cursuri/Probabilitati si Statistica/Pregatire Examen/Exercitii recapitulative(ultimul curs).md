
### Exerciții recapitulative

**1.** Fie vectorul $U = [1, 2, 3, 1, 2, 4, 1, 2, 2, 4]$ și vectorul de date aleatoare alese:

1. cu returnare din $U$: $X = [U_{i_1}, \dots, U_{i_5}]$;
    
2. fără returnare din $U$: $Y = [U_{j_1}, U_{j_2}, U_{j_3}]$.
    

Fie $Z$ variabila aleatoare care indică de câte ori apare $1$ în vectorul $X$.

- **a)** Determinați: $P(Z = 3)$, $P(\{Z < 3\} \cup \{Z > 4\})$, $P(Z < 3 \mid Z \geq 1)$, $P(Y = [1, 2, 3])$, $P(Y(2) \text{ este un număr par})$.
    
- **b)** Să se scrie distribuția de probabilitate a variabilei aleatoare $Z$.

$$
P(Z = 3) = C_{10}^{3} \cdot  \Big(\frac{1}{10}\Big)^{3} \cdot  (\frac{9}{10})^{7} = 120 \cdot \frac{9^{7}}{10^{10}} \approx 0.05739
$$
$$
P(Z<3 \cup Z>4 ) = \sum_{i=1}^{2} C_{10}^{i}\cdot \left( \frac{1}{10} \right)^{i} \cdot  \left( \frac{9}{10} \right)^{10-i} + \sum_{i=5}^{10} C_{10}^{i}\cdot \left( \frac{1}{10} \right)^{i} \cdot  \left( \frac{9}{10} \right)^{10-i}
$$
$$
P(Z>3 | Z\geq1) = \frac{P(Z>3 \cap Z\geq1)}{P(Z\geq1)} = \frac{P(Z>3)}{P(Z\geq1)} = 
$$
$$
\frac{\sum_{i=4}^{10} C_{10}^{i} \cdot  \left( \frac{1}{10} \right)^{i} \cdot \left( \frac{9}{10} \right)^{10-i}}{\sum_{i=1}^{10} C_{10}^{i} \cdot  \left( \frac{1}{10} \right)^{i} \cdot \left( \frac{9}{10} \right)^{10-i} }
$$

