# Cerinta 1 - Gasirea colegilor pentru echipe de proiect

## Utilizatorul

Aplicatia ar fi folosita de studentii care trebuie sa formeze o echipa pentru un proiect la o materie si nu au inca suficienti colegi. De exemplu, un student are nevoie de o echipa de trei persoane, dar colegii pe care ii cunoaste s-au organizat deja. Ar putea fi folosita si de o echipa careia ii mai lipseste un membru.

## Problema

Intr-o situatie plauzibila, studentul intreaba pe grupul anului cine mai cauta echipa. Mesajul se pierde printre alte discutii, iar raspunsurile nu arata mereu pentru ce materie cauta colegii sau daca mai au locuri disponibile. Studentul ajunge sa contacteze separat mai multe persoane, inclusiv echipe care intre timp s-au completat. Chiar daca gaseste colegi, programul lor poate sa nu permita intalniri comune.

Problema pe care vrem sa o verificam este dificultatea de a gasi colegi disponibili pentru acelasi proiect, cu un program compatibil, inainte de termenul de formare a echipelor.

## Solutia propusa

Propunem o aplicatie in care studentii publica si consulta anunturi pentru formarea echipelor de proiect.

1. Studentul sau reprezentantul unei echipe publica un anunt cu materia, numarul de colegi cautati, disponibilitatea si, optional, ideea de proiect.
2. Un student care cauta echipa consulta anunturile pentru materia respectiva si vede unde mai sunt locuri.
3. Contacteaza autorul prin metoda indicata in anunt. Cei doi discuta cerintele proiectului si programul, apoi decid daca vor sa lucreze impreuna.
4. Autorul actualizeaza numarul de locuri sau inchide anuntul cand echipa este completa.

## Trei functii candidate

1. **Publicarea si gestionarea anunturilor:** crearea unui anunt, afisarea lui intr-o lista comuna, actualizarea locurilor disponibile si inchiderea lui cand echipa este completa.
2. **Cautare si filtrare:** selectarea anunturilor dupa materie si intervalele de disponibilitate.
3. **Notificari pentru anunturi noi:** studentul urmareste o materie si primeste o notificare cand apare un anunt relevant.

## Prioritizare

Pentru prima versiune, functia esentiala este publicarea si gestionarea anunturilor intr-o lista comuna. Aceasta permite unui student sa afle cine mai cauta colegi si cum poate lua legatura cu persoana respectiva. Actualizarea locurilor si inchiderea anunturilor sunt necesare pentru ca lista sa ramana utila. Daca sunt putine anunturi, filtrarea si notificarile pot fi adaugate ulterior.

## Presupuneri de verificat

- Nu stim cati studenti intampina aceasta dificultate.
- Presupunem ca grupurile de mesagerie nu sunt suficiente pentru unele cazuri. Trebuie verificat daca exista deja o lista sau o platforma folosita cu succes.
- Trebuie sa aflam la ce materii studentii isi aleg singuri echipele si daca pot forma echipe cu studenti din alte grupe.
- Nu stim daca disponibilitatea este un criteriu important sau daca studentii aleg mai ales dupa cunostinte, interese ori relatii existente.
- Presupunem ca autorii vor actualiza anunturile dupa completarea echipelor. Am verifica acest lucru intr-o proba cu un grup mic de studenti.
