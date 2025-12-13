1. $\displaystyle A \cup \overline{A} = \Omega$ - [[2.Notiuni introductive#Relații importante|de aici]]
    
2. $\displaystyle A \cap \overline{A} = \emptyset$ - [[2.Notiuni introductive#Relații importante|de aici]]
    
3. $\displaystyle \overline{\overline{A}} = A$ - [[2.Notiuni introductive#Relații importante|de aici]]
    
 4. $\displaystyle A \setminus B = A \cap \overline{B}$  - [[2.Notiuni introductive#Operații cu evenimente|de aici]]
    
5.  $\displaystyle P(A \in M) := \frac{\text{masura}(M)}{\text{masura}(D)}$ - [[3.Definitia axiomatica a probabilitatilor#Probabilitatea geometrică|de aici]]
    
6. $\displaystyle P\Big(\bigcup_{n=1}^{\infty} A_n\Big) = \sum_{n=1}^{\infty} P(A_n)$ - [[3.Definitia axiomatica a probabilitatilor#Definiție Probabilitate|de aici]]
    
7. $\displaystyle P(\overline{A}) = 1 - P(A)$, cu $\displaystyle 0 \le P(A) \le 1$ - [[3.Definitia axiomatica a probabilitatilor#Proprietăți ale probabilității|de aici]]
    
8. $\displaystyle P(\emptyset) = 0$ - [[3.Definitia axiomatica a probabilitatilor#Proprietăți ale probabilității|de aici]]
    
9. $\displaystyle P(A \setminus B) = P(A) - P(A \cap B)$ - [[3.Definitia axiomatica a probabilitatilor#Proprietăți ale probabilității|de aici]]
    
10. $\displaystyle A \subseteq B \Rightarrow P(A) \le P(B)$ - [[3.Definitia axiomatica a probabilitatilor#Proprietăți ale probabilității|de aici]]
    
11. $\displaystyle P(A \cup B) = P(A) + P(B) - P(A \cap B)$ - [[3.Definitia axiomatica a probabilitatilor#Proprietăți ale probabilității|de aici]]
    
12. $\displaystyle P(A \cap B)= P(A)\cdot P(B)$ (dacă A și B sunt independente) - [[4.Evenimente independente#**Definitie evenimente independente**|de aici]]
    
13. $\displaystyle P\Big(\bigcap_{i=1}^m B_i\Big)=\prod_{i=1}^{m} P(B_i)$, unde $m \in {2,\ldots,n}$ și $B_1,\ldots,B_n$ sunt  [[4.Evenimente independente#Definitie n evenimente independente|n-independente]] - [[4.Evenimente independente#**Definitie n evenimente independente**|de aici]]
    
14. $\displaystyle P(A \mid B) = \frac{P(A \cap B)}{P(B)}$ - [[5.Probabilitate conditionata#Definitie probabilitate conditionata|de aici]]
    
15. $\displaystyle P(A \cap B) = P(B)\cdot P(A\mid B)=P(A)\cdot P(B \mid A)$ - [[5.Probabilitate conditionata#Formule|de aici]]
    
16. $\displaystyle P(\overline{A}\mid B) = 1 - P(A \mid B)$ - [[5.Probabilitate conditionata#Formule|de aici]]
    
17. $\displaystyle P(A) = P(A \mid H_1)P(H_1)+\ldots+P(A \mid H_n)P(H_n)=P(A \cap H_1)+\ldots+P(A \cap H_n)$  - [[5.Probabilitate conditionata#Formula probabilității totale|de aici]]
    
18. $\displaystyle P(A_1 \cap \cdots \cap A_n) = P(A_1) \cdot P(A_2 \mid A_1) \cdot \ldots \cdot P(A_n \mid A_1 \cap \ldots \cap A_{n-1})$  sau $\displaystyle \prod_{k=1}^n P(A_k)$  daca $A_1 \ldots A_n$ sunt [[4.Evenimente independente#Definitie n evenimente independente|n-independente]] - [[5.Probabilitate conditionata#Formula înmulțirii probabilităților|de aici]]
19.  $\displaystyle P(H_j \mid E) = \frac{P(E \mid H_j) P(H_j)}{P(E)} = \frac{P(E \mid H_j) P(H_j)}{P(E \mid H_1)P(H_1) + \dots + P(E \mid H_n)P(H_n)}, \quad \forall j \in \{1, \dots, n\}$ - [[6.Formula lui Bayes#Formula lui Bayes|de aici]]
20. $X \sim \begin{pmatrix}1 & 2 & \dots & n \\\frac{1}{n} & \frac{1}{n} & \dots & \frac{1}{n}\end{pmatrix}$ - [[8.Distributia de probabilitate#Distributia discreta uniforma $X sim Unid(n)$, $n in mathbb{N} {*}$|de aici]]
21. $X \sim \begin{pmatrix} 0 & 1 \\ 1 - p & p \end{pmatrix}$ - [[8.Distributia de probabilitate#Distributia Bernoulli $X sim Bernoulli(p)$, $p in (0, 1)$|de aici]]
22. $P(X = k) = C_{n}^{k} p^{k} (1 - p)^{n-k}, \quad k \in \{0, \dots, n\}.$ - [[8.Distributia de probabilitate#Distributia binomiala $X sim Bino(n, p)$, $n in mathbb{N} {*}$, $p in (0, 1)$|de aici]]

23. $X \sim Bino(n, p) \iff X \sim \begin{pmatrix} k \\ C_{n}^{k} p^{k} (1 - p)^{n-k} \end{pmatrix}_{k \in \{0, \dots, n\}}$ - [[8.Distributia de probabilitate#Distributia binomiala $X sim Bino(n, p)$, $n in mathbb{N} {*}$, $p in (0, 1)$|de aici]]
24. $(p+(1-p))^n=1=\sum_{k=0}^n C_n^k \ p^k \ (1-p)^{n-k},\quad \forall n \in \mathbb {N}$ - [[8.Distributia de probabilitate#Formula|de aici]]
25. $P(X_1 = k_1, \dots, X_r = k_r) \displaystyle= \frac{n!}{k_1! \dots k_r!} \cdot p_1^{k_1} \cdot \dots \cdot p_r^{k_r}$ - [[8.Distributia de probabilitate#Distributia multinomiala|de aici]]
26. $\displaystyle P(X_1 = k_1, \dots, X_r = k_r) = \frac{C_{n_1}^{k_1} \cdot \dots \cdot C_{n_r}^{k_r}}{C_{n_1+\dots+n_r}^{n}}$ - [[8.Distributia de probabilitate#Distributia hipergeometrica multidimensionala|de aici]]
27. $P(X = k) = p(1 - p)^{k} \quad \text{pentru } k \in \{0, 1, 2, \dots \}$ - [[8.Distributia de probabilitate#Distributia geometrica $X sim Geo(p)$, $p in (0, 1)$|de aici]]
28. $\displaystyle P(\mathbb X = \mathbb x_k) := P \left( \{\omega \in \Omega : \mathbb X(\omega) = \mathbb x_k\} \right), \quad k \in K$ - [[9.Variabile aleatoare independente#**Definitie vector aleator discret**|de aici]]
29. $\displaystyle \mathbb X \sim \begin{pmatrix} \mathbb x_k \\ P(X = \mathbb x_k) \end{pmatrix}_{k \in K}$ - [[9.Variabile aleatoare independente#**Definitie vector aleator discret**|de aici]]
30. $P(X = x_i) = \sum_{j \in J} p_{ij} \quad \forall i \in I, \quad P(Y = y_j ) = \sum_{i \in I} p_{ij} \quad \forall j \in J$ - [[9.Variabile aleatoare independente#Observatie 2|de aici]]
31. 

