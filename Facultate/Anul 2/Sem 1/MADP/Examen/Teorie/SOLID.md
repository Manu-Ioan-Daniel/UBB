## 1.Single Responsibility Principle

O clasă trebuie să aibă o **singură responsabilitate** (un singur motiv de a fi schimbată).

- **Problema:** Dacă o clasă se ocupă și de date, și de calcule, și de salvarea în baza de date, ea devine greu de întreținut.
    
- **Exemplu:** Am separat clasa `Video` (date/calcule) de clasa `VideoDAO` (salvare în baza de date).

### Exemplu unde nu se respecta acest principu

```java
@Data
public class Video {

    private String title;
    private int time;
    private int likes;
    private int views;

    public double getNumberOfHoursPlayed() {
        return (time / 3600.0) * views;
    }

    public void persist() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/youtube";
        Connection conn = DriverManager.getConnection(url, "root", "");
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO Video (Title, Time, Likes, Views) VALUES ("
                + title + "," + time + "," + likes + "," + views + ")";
        stmt.executeUpdate(sql);
    }
}
//persist method should not be here,it breaks SRP
```

#### Fix:

```java
@Data
public class Video {
    private String title;
    private int time;
    private int likes;
    private int views;

    public double getNumberOfHoursPlayed() {
        return (time / 3600.0) * views;
    }
}

public class ConnectionDAO {
    private Connection connection;
    // user, password, dbms, dbName, server, port

    public ConnectionDAO(...) { 
        // constructorul clasei
    }

    public Connection createConnection() {
        // creează conexiunea și o returnează
        // bazându-se pe proprietățile oferite
        return connection;
    }
}

public class VideoDAO {
    public void persist(Video video) throws SQLException {
        // Apelăm clasa ConnectionDAO și îi pasăm proprietățile necesare
        Connection conn = new ConnectionDAO(...).createConnection();
        Statement stmt = conn.createStatement();
        
        String sql = "INSERT INTO Video (Title, Time, Likes, Views) VALUES ("
                + video.getTitle() + "," + video.getTime() + "," 
                + video.getLikes() + "," + video.getViews() + ")";
                
        stmt.executeUpdate(sql);
    }
}
```

## 2.Open Closed Principle

Entitățile software trebuie să fie **deschise pentru extindere**, dar **închise pentru modificare**.

- **Problema:** Dacă adaugi o funcționalitate nouă (ex: o formă geometrică nouă) și trebuie să modifici codul existent, poți introduce bug-uri în părți care funcționau deja.
    
- **Soluția:** Folosești interfețe sau clase abstracte pentru a adăuga comportamente noi prin clase noi, fără a atinge codul vechi.

### Exemplu unde nu se respecta acest principiu

```java
public class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.width * r.height;
        } 
        // Dacă vrem să adăugăm un Cerc, TREBUIE să modificăm această clasă:
        else if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return Math.PI * c.radius * c.radius;
        }
        return 0;
    }
}
```
#### Fix

```java
public class Rectangle implements Shape {
    double width, height;
    public double calculateArea() { return width * height; }
}

public class Circle implements Shape {
    double radius;
    public double calculateArea() { return Math.PI * radius * radius; }
}

public interface Shape {
    double calculateArea(); // Fiecare formă va știi să-și calculeze propria arie
}

public class AreaCalculator {
    public double calculate(Shape shape) {
        return shape.calculateArea(); // Funcționează pentru orice formă viitoare
    }
}

```

## 3.Liskov Substitution Principle

Subclasele trebuie să poată înlocui clasa de bază fără a altera corectitudinea programului.

- **Problema:** Atunci când o subclasă moștenește o metodă, dar îi schimbă comportamentul într-un mod neașteptat (ex: un Pătrat care strică logica unui Dreptunghi când îi setezi lățimea).
    
- **Soluția:** Moștenirea trebuie folosită doar dacă subclasa respectă 100% "contractul" clasei părinte; altfel, se folosesc interfețe comune.
### Exemplu in care nu respectam acest principu(bad practice)

```java
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // Forțăm egalitatea
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        this.width = height; // Forțăm egalitatea
    }
}
```

#### Fix:

```java
public interface Shape {
    int getArea();
}

public class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}

public class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}
```


## 4. Interface Segregation Principle

Principiul **Interface Segregation (ISP)** spune că un client (o clasă) nu ar trebui să fie forțat să depindă de metode pe care nu le folosește. Mai simplu spus: este mai bine să ai mai multe interfețe mici și specifice decât una singură mare și "bună la toate".

### Exemplu problema

```java
public interface Worker {
    void work();
    void eat();
    void getPaid();
}
public class Robot implements Worker {
    public void work() { 
        // Robotul muncește 24/7
    }

    public void eat() { 
        // Robotul nu mănâncă! 
        // Suntem forțați să lăsăm metoda goală sau să aruncăm o excepție.
    }

    public void getPaid() {
        // Robotul nu are nevoie de salariu.
    }
}
```

#### Fix

```java
public interface Workable {
    void work();
}

public interface Feedable {
    void eat();
}

public interface Payable {
    void getPaid();
}
// Robotul implementează doar munca
public class Robot implements Workable {
    public void work() { 
        System.out.println("Robotul asamblează servere...");
    }
}

// Omul implementează tot
public class HumanDeveloper implements Workable, Feedable, Payable {
    public void work() { System.out.println("Scriu cod..."); }
    public void eat() { System.out.println("Pauză de prânz..."); }
    public void getPaid() { System.out.println("Primesc salariul..."); }
}
```

## 5.Dependency Inversion Principle

DIP spune că modulele de nivel înalt (logica de business) nu ar trebui să depindă de modulele de nivel scăzut (detalii de implementare, baze de date), ci ambele ar trebui să depindă de **abstracții** (interfețe).

### Exemplu problema

```java
// Modul de nivel scăzut
public class EmailService {
    public void sendEmail(String mesaj) {
        System.out.println("Email trimis: " + mesaj);
    }
}

// Modul de nivel înalt (Logica de business)
public class AlertaUtilizator {
    private EmailService emailService = new EmailService(); // DEPENDENȚĂ RIGIDĂ

    public void notifica(String mesaj) {
        emailService.sendEmail(mesaj);
    }
}
```

#### Fix

```java
public interface Mesager {
    void trimite(String mesaj);
}
public class EmailService implements Mesager {
    public void trimite(String mesaj) { System.out.println("Email: " + mesaj); }
}

public class SmsService implements Mesager {
    public void trimite(String mesaj) { System.out.println("SMS: " + mesaj); }
}
public class AlertaUtilizator {
    private Mesager mesager;

    // Aici are loc Dependency Injection (injectăm jucăria prin constructor)
    public AlertaUtilizator(Mesager mesager) {
        this.mesager = mesager;
    }

    public void notifica(String mesaj) {
        mesager.trimite(mesaj);
    }
}
```