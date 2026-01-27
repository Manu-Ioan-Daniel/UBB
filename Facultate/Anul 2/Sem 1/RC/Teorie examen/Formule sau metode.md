
## Exemplu pt determinarea subnet ului 

daca avem 248.0.0.0 => facem 256-248 = 8 = $2^3$  si dupa facem 8-3 = 5 =>avem 5 de 1 =>subnet /5

## Exemplu pt determinarea cu cat sa se divida

190.23.0.0/11
facem 32 - 11 = 21 % 8 = 5 =>divizibile cu 32 ($2^5$)
ca sa aflii pozitia vezi de cate ori poti sa scazi 8 din 21 pana ajungi la nr mai mic decat 8,deci 21-8 = 13 skip o pozitie 13-8 = 5 skip inca o pozitie deci dam skip la 2 pozitii in total de la dreapta la stanga deci rezulta a 3 a pozitie de la dreapta la stanga divizibla cu 32 ca sa fie adresa de retea

## Det nr de adrese

x.x.x.x/subnet => avem $2^{32-subnet}$ ip uri

## Adrese din acelasi netmask

doua adrese din acelasi submask au primii *submask* biti la fel
## Clase de adrese dupa litere

| **Clasa** | **Interval Primul Octet** | **Mască Implicită**   | **Utilizare Principală**                              |
| --------- | ------------------------- | --------------------- | ----------------------------------------------------- |
| **A**     | **1 – 126**               | `255.0.0.0` (/8)      | Rețele gigantice (ex: guverne, corporații ISP)        |
| **B**     | **128 – 191**             | `255.255.0.0` (/16)   | Companii și universități de mărime medie              |
| **C**     | **192 – 223**             | `255.255.255.0` (/24) | Rețele mici, utilizatori casnici                      |
| **D**     | **224 – 239**             | Fără mască            | **Multicast** (transmitere către un grup de host-uri) |
| **E**     | **240 – 255**             | Fără mască            | Scopuri **experimentale** și cercetare                |

### 127.x.x.x este rezervata pentru loopback