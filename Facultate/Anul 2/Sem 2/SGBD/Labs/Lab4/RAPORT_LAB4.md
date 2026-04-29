# Raport Lab 4 - Optimizarea performantelor si bune practici

## 1. Scop

Scopul lucrarii a fost sa aplic tehnici de optimizare a performantelor pentru accesul la baza de date si sa demonstrez diferenta dintre o implementare simpla si una mai apropiata de productie.

Am urmarit urmatoarele puncte:
- problema N+1 query si rezolvarea ei cu `JOIN FETCH`
- efectul indexarii asupra interogarilor
- paginarea cu `OFFSET` si cu cursor (keyset)
- caching pentru date read-only
- optimizarea operatiilor in masa
- reutilizarea prepared statements
- configurarea conexiunii la baza de date cu HikariCP si variabile de mediu

## 2. Ce este implementat in proiect

### 2.1 N+1 query

Am creat un demo in `teste/NPlusOneDemo.java`.

- varianta slaba: incarca `Materie` si acceseaza `notas` lazy
- varianta buna: `SELECT DISTINCT m FROM Materie m LEFT JOIN FETCH m.notas`

In loguri se vede diferenta dintre accesul lazy si `JOIN FETCH`.

### 2.2 Indexare

Am creat migrari SQL in:
- `src/main/resources/sql/migrations/001_add_indexes.sql`

Indexuri adaugate:
- `idx_profesori_email`
- `idx_profesori_materie_id`
- `idx_profesori_age`
- `idx_profesori_materie_age`

Nota: indexurile trebuie aplicate efectiv in PostgreSQL inainte de rerun-ul benchmark-urilor.

### 2.3 Paginare

Am implementat in `ProfessorDBRepoORM`:
- paginare pe offset
- paginare pe cursor / keyset

In UI exista:
- butoane `Prev` / `Next`
- selector pentru page size: `10, 25, 50, 100`
- afisare pentru pagina curenta
- panou in dreapta pentru statistici si `EXPLAIN ANALYZE`

### 2.4 Caching

Am implementat cache simplu in `MaterieDBRepoORM`:
- cache pentru entitatile `Materie`
- invalidare la update/delete
- statistica de tip hit/miss afisabila in UI

### 2.5 Operatii in masa

Am adaugat benchmark pentru:
- actualizari individuale
- update bulk intr-o singura interogare
- update batch

### 2.6 Prepared statements

Am adaugat benchmark pentru:
- creare de statement nou la fiecare executie
- reutilizare statement

### 2.7 Conexiune la baza de date

`DatabaseManager` foloseste:
- HikariCP
- variabile de mediu pentru URL, user si parola
- setari pentru pool si validation query

## 3. Rezultate obtinute

### 3.1 Benchmark query-uri profesori

Fisier: `reports/professor_query_bench.csv`

Medii obtinute:
- cautare dupa email: aproximativ `1.43 ms`
- cautare dupa materie: aproximativ `3.09 ms`
- interval dupa age: aproximativ `5.26 ms`
- cautare multi-coloana: aproximativ `2.53 ms`

### 3.2 Prepared statements

Fisier: `reports/prepared_stmt.csv`

Rezultat:
- statement nou de fiecare data: `119 ms`
- statement reutilizat: `81 ms`

Concluzie: reutilizarea statement-ului este mai buna.

### 3.3 Operatii in masa

Fisier: `reports/bulk_ops.csv`

Rezultate medii din benchmark:
- actualizari individuale: `64 ms`
- bulk update: `30 ms`
- batch updates: `8 ms`

Concluzie: pentru volum mare, batch-ul si bulk update sunt mai bune decat actualizarile individuale.

### 3.4 Paginare

Fisiere:
- `reports/pagination-offset.csv`
- `reports/pagination-keyset.csv`

Rezultate medii:
- offset: `12 ms`
- keyset: `333.67 ms` media este influentata de ultimul test, care a iterat pana la capat

Observatie: keyset este buna pentru navigare incrementala, dar benchmark-ul pentru ultima pagina trebuie interpretat cu grija deoarece a fost facut prin parcurgere.

### 3.5 N+1

Fisier marker:
- `reports/nplus1_summary.txt`

In consola si in loguri se vede diferenta dintre accesul lazy si `JOIN FETCH`.

## 4. EXPLAIN ANALYZE

Fisiere:
- `reports/explain/prof_email.txt`
- `reports/explain/prof_materie.txt`

In varianta curenta se vede scanare secventiala:
- `Seq Scan on profesori`

Asta arata ca indexurile trebuie aplicate efectiv in baza de date si benchmark-ul rulat din nou pentru comparatie corecta.

## 5. Concluzie

Proiectul demonstreaza tehnicile de baza cerute la laborator:
- rezolvarea problemei N+1
- paginare
- caching simplu
- prepared statements
- operatii in masa
- configurare corecta a conexiunii

Pentru partea de indexare, exista scriptul de migrare, dar el trebuie aplicat in baza de date si benchmark-ul trebuie rulat din nou ca sa se vada clar diferenta.
