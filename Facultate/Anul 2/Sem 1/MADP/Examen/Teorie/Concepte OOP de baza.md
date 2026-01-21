## 1.Incapsularea

**Încapsulare** = ascunderea datelor unei clase și accesarea lor doar prin metode controlate.

### Exemplu Java 

``` java
class Persoana {
    private int varsta;

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        if (varsta >= 0) {
            this.varsta = varsta;
        }
    }
}
```

Câmpul este `private`, iar accesul se face doar prin `get` și `set`

## 2.Mostenire

**Moștenire** = mecanism OOP prin care o clasă **preia atribute și metode** dintr-o altă clasă, folosind `extends`.

### Exemple

#### 1.Exemplu clasic

```java
class Animal {
    void sunet() {
        System.out.println("Animalul scoate un sunet");
    }
}

class Caine extends Animal {
    void latra() {
        System.out.println("Ham ham");
    }
}
```
#### 2.Ordinea constructorilor

```java
class A {
    A() { System.out.println("A"); }
}

class B extends A {
    B() { System.out.println("B"); }
}

new B();
//prima oara se apeleaza constructorul din A,apoi din B
```

## 3.Polimorfism

**Polimorfism** = același apel de metodă, **comportament diferit**, în funcție de obiectul real.

### Exemple

#### 1.Exemplu clasic

```java
class Parinte {
    void salut() { System.out.println("Salut din Parinte"); }
}

class Copil extends Parinte {
    void salut() { System.out.println("Salut din Copil"); }
}

Parinte p = new Copil();
p.salut(); // Salut din Copil
```

#### 2.Variabilele nu sunt polimorfice

```java
class A {
    int x = 10;
}

class B extends A {
    int x = 20;
}

A obj = new B();
System.out.println(obj.x); // 10
```

#### 3.Metodele statice nu sunt polimorfice

```java
class A {
    static void test() { System.out.println("A"); }
}

class B extends A {
    static void test() { System.out.println("B"); }
}

A obj = new B();
obj.test(); // A
```

#### 4.Final opreste polimorfismul

```java
class A {
    final void run() {}
}

class B extends A {
    // NU poate suprascrie run()
}
```

#### 5.Downcasting periculos

```java
A a = new A();
B b = (B) a; // ClassCastException
```

## 4.Overloading vs Overriding

### Overloading

- **Aceeași metodă**, **parametri diferiți**  
 - Se decide la **compilare**
```java
class Calc {
    int suma(int a, int b) { return a + b; }
    int suma(int a, int b, int c) { return a + b + c; }
}
```

### Overriding

- Metodă din **clasa copil** care o înlocuiește pe cea din părinte  
- Se decide la **runtime** (polimorfism)

```java
class A {
    void show() { System.out.println("A"); }
}

class B extends A {
	@Override // nu este necesar dar foarte recomandat
    void show() { System.out.println("B"); }
}
```

## 5.Legare statică vs dinamică

### Legare statică (Static Binding)

- Apelul metodei este decis la **compilare**  
-  Nu depinde de obiectul real

**Se aplică la:**

- metode `static`
    
- metode `final`
    
- metode `private`
    
- overloading

[[Concepte OOP de baza#3.Metodele statice nu sunt polimorfice|exemplu aici]]
### Legare dinamică (Dynamic Binding)

- Apelul metodei este decis la **runtime**  
-  Depinde de **tipul obiectului**

**Se aplică la:**

- metode suprascrise (overriding)
    
- polimorfism

[[Concepte OOP de baza#3.Polimorfism#1.Exemplu clasic|exemplu aici]]


## 6.Clase abstracte

**Clasă abstractă** = o clasă care **nu poate fi instanțiată** și poate avea **metode abstracte** (fără corp) care **trebuie suprascrise** în clasele copil.

### Exemple

#### 1. Clasă abstractă simplă

```java
abstract class Animal {
    abstract void sunet(); // metodă abstractă

    void mananca() {       // metodă normală
        System.out.println("Animalul mănâncă");
    }
}

class Caine extends Animal {
    @Override
    void sunet() {
        System.out.println("Ham ham");
    }
}
Animal a = new Caine();
a.sunet();   // Ham ham
a.mananca(); // Animalul mănâncă
```

#### 2.Clasa abstracta + constructor
```java
abstract class A {
    A() { System.out.println("Constructor A"); }
}

class B extends A {
    B() { System.out.println("Constructor B"); }
}

B b = new B();
// Apoi output:
// Constructor A
// Constructor B
```
### Caracteristici

- Nu poți crea obiect direct din clasă abstractă:

```java
	Animal a = new Animal(); // ❌ eroare
```

- Poate avea atât metode abstracte, cât și concrete
    
- Se folosește pentru a defini **șabloane comune** pentru mai multe clase

## 7.Interfete

**Interfață** = un **tip abstract pur** care **defineste doar metode (implicit abstracte)** și constante; clasele care o implementează trebuie să suprascrie metodele.

### Exemple

#### 1.Exemplu simplu

```java
interface Vehicul {
    void porneste();   // metodă abstractă
    void opreste();    // metodă abstractă
}

class Masina implements Vehicul {
    @Override
    public void porneste() {
        System.out.println("Mașina pornește");
    }

    @Override
    public void opreste() {
        System.out.println("Mașina se oprește");
    }
}
Vehicul v = new Masina();
v.porneste(); // Mașina pornește
v.opreste();  // Mașina se oprește
```

#### 2.Trebuie sa specificam care metoda default o vrem

```java
interface A {
    default void test() { System.out.println("A"); }
}

interface B {
    default void test() { System.out.println("B"); }
}

class C implements A, B {
    // ❌ compilatorul cere suprascrierea metodei
    @Override
    public void test() {
        A.super.test(); // sau B.super.test()
    }
}
```

Cazul de mai jos nu are aceasta problema pentru ca nu avem default

```java
interface X { void m(); }
interface Y { void m(); }

class Z implements X, Y {
    @Override
    public void m() {
        System.out.println("Z");
    }
}
//in cazul asta nu este problema
```


#### 3.Interfata + campuri

```java
interface Test {
    int x = 10;
}

class Demo implements Test {
    void show() {
        x = 20;
        //❌ eroare: câmpurile interfeței sunt  public static final implicit
        System.out.println(x);
    }
}
```

### Caracteristici

- Nu se poate instanția direct:

```java
Vehicul v = new Vehicul(); // ❌ eroare
```
- Toate metodele sunt **implicit public abstract** (Java ≤7)
    
- Din Java 8+ pot avea și **metode default** cu implementare
    
- Poate fi implementată de **mai multe clase** (permite moștenire multiplă de comportament)

## 9.Low Coupling

### Exemplu de high coupling

```java
class Motor {
    void porneste() { System.out.println("Motor pornit"); }
}

class Masina {
    Motor motor = new Motor(); // Masina depinde direct de Motor
    void pornesteMasina() {
        motor.porneste();
    }
}
```

#### Fix

```java
class Motor {
    void porneste() { System.out.println("Motor pornit"); }
}

class Masina {
    Motor motor;
    public Masina(Motor motor){
	    this.motor = motor;
    }
    void pornesteMasina() {
        motor.porneste();
    }
}
```

## 10. High Cohesion

**High Cohesion** = o clasă (sau modul) ar trebui să aibă **o singură responsabilitate clară** și toate metodele ei să fie **strâns legate de acea responsabilitate**.

### Exemplu low cohesion

```java
class Masina {
    void pornesteMotor() { ... }
    void opresteMotor() { ... }
    void trimiteEmail(String mesaj) { ... }  // nu are legătură cu masina
    void calculeazaTaxe() { ... }           // alt domeniu
}

```

#### Fix
```java
class Masina {
    void pornesteMotor() { ... }
    void opresteMotor() { ... }
}

class EmailService {
    void trimiteEmail(String mesaj) { ... }
}

class TaxService {
    void calculeazaTaxe() { ... }
}
```
## 11.Contracte intre obiecte 

**Contract între obiecte** = un acord implicit sau explicit că un obiect **va respecta anumite metode, comportamente și reguli** atunci când interacționează cu alt obiect.

### Exemplu contract prin interfata

```java
interface Pagabil {
    void plateste(double suma);  // contract: orice obiect Pagabil trebuie să implementeze această metodă
}

class Factura implements Pagabil {
    @Override
    public void plateste(double suma) {
        System.out.println("Factura a fost plătită: " + suma);
    }
}

class CardBancar implements Pagabil {
    @Override
    public void plateste(double suma) {
        System.out.println("Plată cu card: " + suma);
    }
}
```




