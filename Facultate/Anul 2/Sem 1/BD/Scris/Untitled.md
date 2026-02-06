

![[Pasted image 20260206162018.png]]

F = {AB->C,AB->D,AB->E,AC->B,AC->D,AC->E,B->C,C->B,C->D,B->E}

B+ = {B,C,D,E} cum C,D,E apartine B+ asta inseamna ca atributul A este redundant in AB->C dar si in AB->D si in AB->E => F echivalent cu {B->C,B->D,B->E,AC->B,AC->D,AC->E,C->B,C->D}

C+ = {D,B,E,C} deci cum B,D,E apartin C+ inseamna ca A este atribut redundant in AC->D,AC->E,AC->B => F echivalent cu  F_2 = {B->C,B->D,B->E,C->B,C->D,C->E}

B+ pe baza lui F_2\{B->D} este {B,C,D,E} deci cum D apartine rezulta ca B->D este redundant

F echivalent cu F_3 = {B->C,B->E,C->B,C->D,C->E}

B+  pe baza lui F_3\{B->E} este {B,C,D,E} si cum E apartine rezulta ca B->E este redundant deci F echivalent cu {B->C,C->B,C->D,C->E} care este o acoperire minimala pentru ca nu mai avem atribute redundante si nici dependente functionale redundante


cum detrerminam 3NF pe bazza la acoperire minimala

G = {B->C,C->B,C->D,C->E} = {B->C,C->BDE} deci R se descompune in (B,C),(B,C,D,E) si cum cheile candidat AB respectiv AC nu sunt in nici una din multimi continute mai adaugam si (A,B),(A,C)