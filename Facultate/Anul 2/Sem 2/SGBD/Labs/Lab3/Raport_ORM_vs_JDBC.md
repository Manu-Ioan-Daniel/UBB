# Raport comparatie JDBC vs ORM (Hibernate)


## Comparatie cod: inainte vs dupa

### 1.Acces la date pentru listare

### Inainte (JDBC)
Exemplu din `src/main/java/repos/MaterieDBRepo.java`:
- SQL explicit: `SELECT * FROM materii`
- `PreparedStatement`
- `ResultSet`
- mapare manuala rand -> obiect (`createMaterieFromRS`)
- tratare explicita `SQLException`

Acest model se repeta in mai multe repository-uri.

### Dupa (ORM)
Exemplu din `src/main/java/repos/MaterieDBRepoORM.java`:
- query JPQL: `SELECT m FROM Materie m`
- `EntityManager`
- rezultat direct tipat: `List<Materie>`
- fara mapare manuala `ResultSet`

### Cateva exemple:

Before:

```java
 public List<Materie> findAll() {
        String sql = "SELECT * FROM materii";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){

            ResultSet resultSet = ps.executeQuery();

            List<Materie> materii = new ArrayList<>();

            while(resultSet.next()){
                Materie materie = createMaterieFromRS(resultSet);
                materii.add(materie);
            }
            return materii;
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

```
After:

```java
   @Override
    public List<Materie> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Materie m", Materie.class).getResultList();
        }
    }
```
Before:

```java
@Override
    public void save(Professor professor) {

        String sql = """
                INSERT INTO profesori(name,age,email,materie_id)
                VALUES(?,?,?,?)
                """;

        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){

            ps.setString(1, professor.getName());
            ps.setInt(2, professor.getAge());
            ps.setString(3, professor.getEmail());
            ps.setLong(4, professor.getMaterieId());
            ps.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
```

After

```java
 @Override
    public void save(Professor professor) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(professor);
            em.getTransaction().commit();
        }
    }
```
O gramada de cod boilerplate este eliminat, iar intentia este mai clara.


## 2. Analiza reducerii liniilor de cod


| Set fisiere | Linii total |
|---|---:|
| JDBC (`MaterieDBRepo`, `StudRepo`, `ProfessorDBRepo`, `NoteDBRepo`) | 310 |
| ORM (`MaterieDBRepoORM`, `StudRepoORM`, `ProfessorDBRepoORM`, `NoteDBRepoORM`) | 205 |

Reducere estimata: `310 - 205 = 105` linii, adica aproximativ `33.9%`.

Interpretare:
- Principala reducere vine din eliminarea codului repetitiv: `PreparedStatement`, `ResultSet`, mapare manuala, SQL CRUD repetitiv.
- O parte din complexitate se muta in:
  - configurare JPA/Hibernate (`persistence.xml`, `HibernateConfig`)
  - modelarea entitatilor si relatiilor.

---

## 3. Rezultate masurari de performanta si interpretare

Masurarea se face in `src/main/java/teste/PerformanceTests.java` prin:
- 100 deschideri conexiune fara pool (`OldDBManager`)
- 100 deschideri conexiune cu pool (`DatabaseManager` + Hikari)

Metrici afisate:
- `Average connection time(no pool)`
- `Total connection time(no pool)`
- `Average connection time(pool)`
- `Total connection time(pool)`

### Rezultate (de completat dupa rulare)

| Metrica | Valoare |
|---|--------:|
| Average connection time(no pool) |  1.9 ms |
| Total connection time(no pool) |  207 ms |
| Average connection time(pool) | 0.95 ms |
| Total connection time(pool) |  102 ms |

### Interpretare asteptata
- In mod normal, varianta cu pool este mai rapida la deschidere conexiune, deoarece reutilizeaza conexiuni existente.
- Diferenta devine mai vizibila la multe operatii repetate (ex: 100+ request-uri).

---

## 4. Avantaje si dezavantaje ORM observate

## Avantaje
- Mai putin cod repetitiv in repository.
- CRUD mai concis (`persist`, `merge`, `remove`, `find`).
- Model orientat pe obiecte, mai apropiat de domeniu.
- Query-uri JPQL mai portabile decat SQL strict specific DB.

## Dezavantaje
- Configurare initiala mai sensibila (ex: `META-INF/persistence.xml`, provider, unit name).
- Erori de mapping uneori mai greu de diagnosticat (`UnknownEntityException`, tipuri schema diferite).
- Control SQL mai putin direct; pentru optimizari fine poate fi nevoie de query-uri native.
- Necesita intelegerea contextului de persistenta si a tranzactiilor.

---

## 5. Provocari intampinate in migratie

Probleme aparute in timpul migrarii:
1. `No persistence provider for EntityManager`
   - cauza: `persistence.xml` nu era in `META-INF` sau configurarea provider-ului nu era detectata.
2. `UnknownEntityException: Could not resolve root entity 'Materie'`
   - cauza: entitatile nu erau inregistrate clar in persistence unit.
3. `Schema-validation: wrong column type ... expecting BIGINT`
   - cauza: mismatch intre tipurile din entitati (`Long`) si schema SQL (`SERIAL/INT`).

Cum au fost adresate:
- mutare/creare `persistence.xml` in `src/main/resources/META-INF/`
- adaugare explicita clase entitate in `persistence.xml`
- relaxare validare schema pentru demo (`hbm2ddl.auto=none`)

---

## 6. Concluzie

Migratia JDBC -> ORM reduce codul din layer-ul de acces la date si creste claritatea pentru operatii CRUD uzuale. Costul este o configurare mai stricta si o etapa de debugging pentru mapping si schema.

Pentru laborator, ORM aduce un castig bun de productivitate si mentenanta, cu conditia sa fie stabilita corect conventia de mapping (entitati, ID-uri, tipuri SQL, persistence unit).

