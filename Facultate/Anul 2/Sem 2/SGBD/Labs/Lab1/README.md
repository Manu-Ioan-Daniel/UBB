## Configurarea Bazei de Date

Cerinte: PostgreSQL instalat si activ

Pasi:

1. Deschideti psql: `psql -U postgres`
    
2. Creati baza de date: `CREATE DATABASE sgbd_lab;`
    
3. Conectati-va: `\c sgbd_lab`
    
4. Rulati scriptul SQL din `src/main/resources/sql/tables`
    

Scriptul SQL creeaza tabelele materii, studenti, profesori si note_studenti cu date initiale.

## Configurarea Conexiunii la Baza de Date

Conexiunea este configurata in `src/main/java/utils/DatabaseManager.java`

URL-ul JDBC curent:

jdbc:postgresql://localhost:5432/sgbd_lab?user=postgres&password=123

Parametrii:

- localhost - host-ul serverului PostgreSQL
    
- 5432 - portul implicit PostgreSQL
    
- sgbd_lab - baza de date
    
- user=postgres - utilizatorul PostgreSQL
    
- password=123 - parola
    

## Rularea Aplicatiei


Metoda 1 - Gradle pe Windows:

cd "C:\Users\Deny\Documents\GitHub\UBB\Facultate\Anul 2\Sem 2\SGBD\Labs\Lab_1"  
.\gradlew.bat build  
.\gradlew.bat run

Metoda 2 - IDE (IntelliJ IDEA / Eclipse):  
Deschideti proiectul, sincronizati Gradle, si run.