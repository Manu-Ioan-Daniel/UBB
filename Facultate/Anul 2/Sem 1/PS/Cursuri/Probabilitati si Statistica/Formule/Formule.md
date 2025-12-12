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
20. 