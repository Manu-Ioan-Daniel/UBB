# Raport migrații și evoluție schemă (Sarcinile B–E, Locking optimist, Soft-delete)

Acest raport documentează implementarea solicitată: migrații SQL reversibile, actualizări entități JPA/Hibernate, locking optimist (@Version), soft-delete, teste/demo și pași pentru rulare.

## Rezumat
Am implementat și plasat migrațiile SQL în:
- `src/main/resources/db/migrations/up/` — scripturi "up" (aplicare)
- `src/main/resources/db/migrations/down/` — scripturi "down" (rollback)

Am actualizat entitățile Java (`models/Professor`) pentru a reflecta adăugări: `phone`, `version`, `is_deleted`, `deleted_at`, `deleted_by` și am adăugat un demo pentru demonstrat locking optimist: `main.OptimisticLockDemo`.

DB folosit: PostgreSQL (conform `src/main/resources/db/db.properties`). Conexiune implicită: `jdbc:postgresql://localhost:5432/sgbd_lab` (user: `postgres`, pass: `123`).

---

## Checklist (ce am făcut)
- [x] Sarcina B — Adăugare coloană `phone` la `profesori` (migrare up/down)
- [x] Sarcina C — Creare tabel `projects` cu FK către `materii` + seed
- [x] Sarcina D — Modificare tip coloană `materii.credits` (INT -> BIGINT) (+down)
- [x] Sarcina E — Adăugare indici (profesori.materie_id, profesori.email, note_studenti.materie_id)
- [x] Adăugat suport locking optimist: `version` (bigint) și `@Version` în entitate
- [x] Implementat soft-delete pentru entități (coloane + @SQLDelete/@Where pe `Professor`)
- [x] Demo de locking optimist (`main.OptimisticLockDemo`)
- [x] Documentație Markdown (acest fișier)

---

## Fișiere noi / modificate (important)
- Modificat: `src/main/java/models/Professor.java` — adăugat: `phone`, `version`, `isDeleted`, `deletedAt`, `deletedBy`, adnotări `@SQLDelete` și `@Where` și metode `softDelete()/restore()`.
- Nou: `src/main/java/main/OptimisticLockDemo.java` — demo pentru conflict optimist.
- Migrații "up":
  - `src/main/resources/db/migrations/up/001_add_professor_phone_up.sql`
  - `src/main/resources/db/migrations/up/002_create_projects_up.sql`
  - `src/main/resources/db/migrations/up/003_alter_materii_credits_type_up.sql`
  - `src/main/resources/db/migrations/up/004_add_indexes_up.sql`
  - `src/main/resources/db/migrations/up/005_add_version_and_soft_delete_up.sql`
- Migrații "down":
  - `src/main/resources/db/migrations/down/001_add_professor_phone_down.sql`
  - `src/main/resources/db/migrations/down/002_create_projects_down.sql`
  - `src/main/resources/db/migrations/down/003_alter_materii_credits_type_down.sql`
  - `src/main/resources/db/migrations/down/004_add_indexes_down.sql`
  - `src/main/resources/db/migrations/down/005_add_version_and_soft_delete_down.sql`

---

## Informații detaliate pe sarcini

### Sarcina B — Adăugare coloană nouă
- Scop: `ALTER TABLE profesori ADD COLUMN phone VARCHAR(20);`
- Up script: `001_add_professor_phone_up.sql`
- Down script: `001_add_professor_phone_down.sql` (DROP COLUMN IF EXISTS)
- Nota: coloana este NULL-abilă; pentru valoare implicită se poate modifica scriptul cu `DEFAULT '+40-000-000000'` dacă doriți.
- Entitate: `Professor` — adăugat `@Column(name = "phone") private String phone;` plus getter/setter.

### Sarcina C — Adăugare tabel nou cu FK
- Scop: creare tabel `projects` (id, name, description, start_date, department_id)
- Up script: `002_create_projects_up.sql` (include seed de 2 proiecte)
- Down script: `002_create_projects_down.sql` (DROP TABLE)
- Constrângeri: `name NOT NULL`, FK `department_id` referă `materii(id)` cu `ON DELETE SET NULL`.
- Entitate (recomandare): creați `models.Project` cu `@ManyToOne` către `Materie` (sau `Department` dacă adăugați entitate separată).
- Migrare date: am inclus seed în fișierul up; alternativ, creați fișier separat `data/002_projects_seed.sql`.

### Sarcina D — Modificare coloană existentă
- Scop: `ALTER TABLE materii ALTER COLUMN credits TYPE BIGINT;`
- Up script: `003_alter_materii_credits_type_up.sql`
- Down script: `003_alter_materii_credits_type_down.sql` (folosește `USING credits::int`) — ATENȚIE la pierdere de date: verificați că valorile se încadrează în INT înainte de down.
- Entitate: `Materie#credits` era `int` — actualizați tipul Java la `long` sau `Long` dacă doriți coerență cu BIGINT (opțional). În codul curent `credits` e `int`. Recomand schimbare la `Long credits;` în Java dacă realizați migrația.

### Sarcina E — Adăugare index-uri
- Up script: `004_add_indexes_up.sql` — creează indexuri `idx_profesori_materie`, `idx_profesori_email`, `idx_note_studenti_materie`.
- Down script: `004_add_indexes_down.sql` — dropează indexurile.
- Test performanță: folosiți `EXPLAIN ANALYZE` pe interogările țintă (vezi secțiunea Testare mai jos).

---

## Locking optimist (versiune)
- Migrare: `005_add_version_and_soft_delete_up.sql` adaugă coloana `version BIGINT DEFAULT 0` pe tabelele principale.
- Entitate: `@Version @Column(name = "version") private Long version;` — exemplu în `Professor`.
- Demo: `main.OptimisticLockDemo` simulează doi utilizatori care încarcă aceeași entitate, unul salvează, celălalt primește `OptimisticLockException` la commit.
- Management excepții: demo prinde `OptimisticLockException`, loghează stacktrace, oferă opțiune de reîncărcare.
- Recomandare UI: afișați mesaj prietenos:
  - "Înregistrarea a fost modificată de altcineva. Puteți reîncărca datele, încerca din nou sau forța actualizarea (doar admin)."

---

## Soft-delete
- Migrare: `005_add_version_and_soft_delete_up.sql` adaugă `is_deleted BOOLEAN DEFAULT FALSE`, `deleted_at TIMESTAMP WITH TIME ZONE`, `deleted_by VARCHAR(100)`.
- Entitate: `@SQLDelete` la `Professor` (ex: `@SQLDelete(sql = "UPDATE profesori SET is_deleted = true, deleted_at = now(), deleted_by = current_user WHERE id = ?")`) și `@Where(clause = "is_deleted = false")` pentru ascunderea implicită.
- Comportament:
  - Delete normal (repository.delete) va rula UPDATE și nu va șterge fizic rândul.
  - Pentru a vizualiza rândurile șterse soft, folosiți query explicit care ignoră `@Where` sau un EntityManager native SQL.
  - Restaurare: UPDATE set `is_deleted=false`, `deleted_at=NULL`, `deleted_by=NULL`.
  - Ștergere permanentă: `DELETE FROM ... WHERE id = ?` (operație rezervată admin).

---

## Pași practici: rulare migrații (PowerShell)
Presupunând PostgreSQL local (port 5432), user `postgres`, parola `123`, DB `sgbd_lab`.

1. Aplicare toate migrațiile Up în ordine numerică:

```powershell
$env:PGPASSWORD='123';
psql -h localhost -p 5432 -U postgres -d sgbd_lab -f "src/main/resources/db/migrations/up/001_add_professor_phone_up.sql";
psql -h localhost -p 5432 -U postgres -d sgbd_lab -f "src/main/resources/db/migrations/up/002_create_projects_up.sql";
psql -h localhost -p 5432 -U postgres -d sgbd_lab -f "src/main/resources/db/migrations/up/003_alter_materii_credits_type_up.sql";
psql -h localhost -p 5432 -U postgres -d sgbd_lab -f "src/main/resources/db/migrations/up/004_add_indexes_up.sql";
psql -h localhost -p 5432 -U postgres -d sgbd_lab -f "src/main/resources/db/migrations/up/005_add_version_and_soft_delete_up.sql";
```

2. Rollback (reverse order): rulați fișierele `down` în ordine inversă.

```powershell
$env:PGPASSWORD='123';
psql -h localhost -p 5432 -U postgres -d sgbd_lab -f "src/main/resources/db/migrations/down/005_add_version_and_soft_delete_down.sql";
# ... și așa mai departe pentru 004,003,002,001
```

Notă: Executați backup DB înainte de alterări critice (ex: schimb tip coloană).

---

## Testare & demonstrații

1. Compilare proiect:
- Asigurați JDK instalat și $env:JAVA_HOME setat.
- În PowerShell: `.\gradlew.bat build` (în root proiect).

2. Rulați demo optimistic lock:
- După aplicarea migrațiilor, populate tabele cu cel puțin un `profesori` (id=1).
- Rulați: `java -cp build\classes\java\main main.OptimisticLockDemo`
- Așteptați output; ar trebui să vedeți `Optimistic lock detected...` în cazul în care Hibernate activează verificarea.

3. Test soft-delete:
- Folosiți psql: `SELECT * FROM profesori WHERE id = 1;` înainte și după `DELETE FROM profesori WHERE id = 1;` (observați că rândul nu este eliminat fizic; `is_deleted` devine `true`).
- Restaurați cu `UPDATE profesori SET is_deleted=false, deleted_at=NULL, deleted_by=NULL WHERE id=1;`.

4. Măsurare performanță (indici):
- Rulează `EXPLAIN ANALYZE SELECT * FROM note_studenti WHERE materie_id = 2;` înainte și după aplicarea indexurilor. Comparați `Total runtime` și planul (selecție index vs seq scan).

---

## Logare conflicte & bune practici
- Prindeți `OptimisticLockException` și logați `e.getMessage()` + stacktrace.
- Afișați în UI mesaje prietenoase și oferiți opțiuni: reîncărcare, forțare (admin) sau anulare.
- Păstrați audit trail (`deleted_by`, `deleted_at`) pentru trasabilitate.
- Pentru migrații în producție folosiți tool dedicat (Flyway sau Liquibase) și testați pe staging înainte.

---

## Diagrama schemă — descriere (înainte / după)
- Tabele originale: `materii(id, name, credits)`, `studenti(id, name, age)`, `profesori(id, name, age, email, materie_id)`, `note_studenti(student_id, materie_id, nota)`.
- După modificări:
  - `profesori` -> +`phone`, +`version`, +`is_deleted`, +`deleted_at`, +`deleted_by`.
  - `materii` -> `credits` tip BIGINT, +`version`, +soft-delete coloane.
  - `studenti` -> +`version`, +soft-delete coloane.
  - `projects` nou tabel cu FK `department_id -> materii(id)`.

(Pentru o diagramă vizuală exportați schema din pgAdmin sau folosiți tool precum DBDiagram.io cu tabelele actualizate.)

---

## Pași următori recomandati
- Convertiți migrațiile la Flyway pentru management automat.
- Extindeți entități pentru a folosi auditable (who/when/why) cu `@CreatedBy/@LastModifiedBy` (Spring Data) sau un mecanism manual.
- Implementați UI admin pentru vizualizare/restore/delete-permanent.

---

## Notă finală
Am adăugat fișierele necesare în proiect. Am rulat verificările statice; compilarea Gradle nu a rulat local din cauza lipsei JAVA_HOME în mediu CI al editorului. După setarea JAVA_HOME local, rulați `.
gradlew.bat build` și apoi demo-urile indicate.

Dacă doriți, pot:
- genera fișiere exacte `models/Project.java` și actualiza `Materie#credits` la `Long`;
- converti migrațiile la Flyway `V1__...sql` și configura build-ul Gradle pentru a le rula.




