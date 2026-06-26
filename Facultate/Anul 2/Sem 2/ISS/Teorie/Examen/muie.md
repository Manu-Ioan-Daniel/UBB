
## ORM Impedance Mismatch - done


- paradigma orientata obiect permite dezvoltarea aplicatiilor prin intermediul unor obiecte ce contin date si logica aplicatiei
- baze de date relationale stocheaza datele in tabele si sunt manipulate prin intermediul procedurilor stocate sau interogari sql
- deiferentele dintre cele doua reprezinta ORM Impedance Mismatch

## Sablonul Proxy distribuit - done

- este un tip de proxy care ofera un inlocuitor pentru on obiect dintr un alt spatiu de adrese.
- proxy ul intercepteaza o cerere de la client, o codifica si o trimite catre obiect. Obiectul proceseaza cererea si trimite un response inapoi la proxy, care la randul sau trimite la client.
- Clientul nu stie ca cererea este trimisa unui obiect dintr un alt spatiu de adrese, pare ca un apel local.
## Inversion of Control

- este un principiu de design in care controlul asupra fluxului de executie si al gestionarii obiectelor este delegat unui framework sau unui container extern.
- de regula realizat prin dependency injection
- reduce cuplajul si creste modularitatea

## Dependency Injection


- este procesul prin care obiectele isi definesc dependentele fie prin adaugarea unor parametrii la constructor, fie prin argumentele unei metode de tip factory sau cu ajutorul metodelor de tip setter care trebuie apelate imediat dupa crearea obiectului.
- un container injecteaza aceste dependente cand se creeaza obiectul
- separa logica obiectului de modul in care isi obtine dependentele.


## Maparea mostenirii

- fiecare clasa din aplicatie are tabelul ei, atributele sunt mapate la coloanele coresp din tabela
- inregastrile se leaga prin
   - folosirea cheii primare in tabelul coresp clazei de baza si folosirea unui foreign key in tabela coresp clasei derivate
- operatii de join pt gasirea datelor


## Maparea membrilor statici

- se face o tabela cu o singura inregistrare pentru fiecare entitate, si fiecare coloana reprezinta un atribut static
- multe tabele, putine inregistrari


## REST stateless

- serverul nu pastreaza nici o informatie despre starea clientului intre cereri
- fiecare cerere va fi tratata ca si cum este o cerere noua
- fiecare cerere trebuia sa contina toate informatiile necesare pentru a intelege cererea si nu pot contine informatii legate de context pastrate pe server


## Point to point model

- fiecare mesaj are exact un destinatar si un expeditor
- cand brokerul primeste un mesaj, acesta este pus in coada iar cand destinatarul cere un mesaj, acesta este scos din coada si trimis.
- se garanteaza livrarea mesajului la un singura destinatar deoarece este scos din coada odata ce este trimis.
- putem avea mai multi destinatari, fiecare cu propriul mesaj

## Broker de mesaje

- un software care faciliteaza comunicarea intre diferite aplicatii, sisteme, sau servicii ca intermediar ce primeste, stocheaza  si redirectioneaza mesajele intre componentele unei aplicatii distribuite.

## Publish-Subscribe Model


- mesajele sunt trimise unui topic, mai multi destinatari pot astepta livrarea unor mesaje de la un topic.
- odata ce un mesaj este trimis la un topic, o copie a acestuia este trimisa catre toti destinatarii care asteapta sa primeasca mesajul.
- expeditorul nu stie cine sunt destinatarii si nici cum vor fi procesate mesajele.

## Structura jwt

- este format din 3 parti: Header, Payload, Signature
- header->contine tipul de token si algoritmul de criptare
- payload->contine informatii despre entiate ce pot fi verificate
   - 7 claims rezervate si recomandate:issue, audit, subject, not before time, expiration, jwt id, issue at time
- semnatura-> folosita pt a verifica expeditorul si pt a verifica daca mesajul a fost modificat pe parcurs



