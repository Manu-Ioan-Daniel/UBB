
## Anomalii ale executiei concurente

### Dirty read

T1 citeste si modifica A->T2 citeste si modifica A ul schimbat de T1->T1 citeste B si Scrie B dar da Abort(a esuat), deci nu a fost updata baza de date si se da rollback, dar T2 se termina cu succes, cu un A citit "gresit".

![[Pasted image 20260524201419.png]]

### Unrepeatable reads

T1 il citeste pe A, T2 modifica pe A, T1 citeste A din nou, si nu este la fel cu cel citit initial->unrepeatable read nu este consistenta tranzactia

![[Pasted image 20260524201608.png]]

### Blind write

T1 il modifica pe A->T2 il modifica pe A si se suprascrie modificarea lui T1, T2 il modifica pe B si T1 il modifica pe B->se suprascrie write ul din T2->overwriting uncommited data

![[Pasted image 20260524201930.png]]

### Phantom Read

T1 face select pe un tabel, T2 face insert in acelasi tabel fix dupa->t1 face iara select, o sa avem o intregistrare in plus "fantoma"


## Conflict-serializabila

Daca conflictele unui plan de tranzactii sunt la fel cu conflictele altui plan de tranzactii->conflict echivalente
Daca in plus, planul de tranzactii este serializat, atunci este conflict serializabila

### Graf de precenta

![[Pasted image 20260524203903.png]]

- Daca graful de precenta are un circuit->nu este conflict-serializabila, daca nu are=>este

## View-serializabilitatea

![[Pasted image 20260524204236.png]]


![[Pasted image 20260524204540.png]]

## Deadlock

- deadlock inseamna de exemplu ca T1 asteapta dupa T2, iar T2 asteapta dupa T1, deci se astepata la infinit una pe alta

![[Pasted image 20260524205640.png]]

### Detectare

![[Pasted image 20260524210224.png]]


![[Pasted image 20260526194158.png]]![[Pasted image 20260526201253.png]]