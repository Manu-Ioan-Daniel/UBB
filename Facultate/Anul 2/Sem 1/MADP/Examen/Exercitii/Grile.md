# Probleme practice – Wildcards & Bounded Generics

## **Problema 1 – Upper Bound**

```java
List<? extends Number> lista = Arrays.asList(1, 2, 3);

Number n = lista.get(0);   // linia A
lista.add(4);           // linia B

```

**Întrebare:** Ce se întâmplă la linia A și linia B?

### Rezolvare

Linia A merge si n = 1,Linia B da eroare pentru ca compilatorul nu stie ce adaugam,Integer,Double,sau orice ce mosteneste Number.

---

## **Problema 2 – Lower Bound**

```java
List<? super Integer> lista = new ArrayList<Number>();
lista.add(10);              // linia A
Object o = lista.get(0);    // linia B
Integer n = (Integer) lista.get(0); // linia C
```

**Întrebare:** Ce linii merg și ce linii pot arunca eroare?

### Rezolvare

adauga in lista numarul intreg 10,pentru ca stie ca este deja minim un integer,linia B merge,pentru ca Object este tatal tuturor claselor si linia C merge dar e risky

---

## **Problema 3 – Tip generic bounded**

```java
class Util {
    public static <T extends Number> double dubleaza(T n) {
        return n.doubleValue() * 2;
    }
}

System.out.println(Util.dubleaza(5));      // linia A
System.out.println(Util.dubleaza(3.5));    // linia B
System.out.println(Util.dubleaza("abc")); // linia C

```

**Întrebare:** Ce linii merg și ce linii dau eroare?

### Rezolvare

linia A si linia B merge,linia C nu merge pentru ca "abc" este un String care nu mosteneste Number

---

## **Problema 4 – Wildcard în metodă generică**

```java
public static void afiseazaLista(List<? extends Number> lista) {
    for (Number n : lista) {
        System.out.print(n + " ");
    }
}

List<Integer> intList = Arrays.asList(1,2,3);
afiseazaLista(intList); // linia A

List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);
afiseazaLista(doubleList); // linia B

```

**Întrebare:** Ce se afișează la linia A și linia B?

### Rezolvare

linia A afiseaza `(1,2,3)` si linia B afiseaza `1.1,2.2,3.3`

---

## **Problema 5 – Multiple bounds**

```java
class Util {
    public static <T extends Number & Comparable<T>> T maxim(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }
}

System.out.println(Util.maxim(5, 10));     // linia A
System.out.println(Util.maxim(3.5, 2.7));  // linia B
System.out.println(Util.maxim("a", "b")); // linia C

```

**Întrebare:** Care linii merg și care dau eroare?

### Rezolvare

linia A merge,linia B merge,pentru ca Number este si comparable,dar linia C nu merge pentru ca Stringurile nu mostenesc Number

---

## **Problema 6 – Wildcard + Upper Bound**

```java
List<? extends Number> lista = new ArrayList<Integer>();
lista.add(5); // linia A
Number n = lista.get(0); // linia B

```
**Întrebare:** Ce linii merg și ce linii dau eroare?

### Rezolvare

linia A nu merge ca nu stie ce drq e,linia B  nu merge ca lista nu are nimic inauntru(la runtime,ca sintactic e corect)

---

## **Problema 7 – Wildcard + Lower Bound**

```java
List<? super Integer> lista = new ArrayList<Number>();
lista.add(10); // linia A
Object o = lista.get(0); // linia B
Integer n = lista.get(0); // linia C

```

**Întrebare:** Care linii sunt sigure, care pot da eroare?

### Rezolvare

linia A merge,pentru ca pur si simplu ii da upcast,linia B merge,pentru ca Object este daddy ul nostru,linia C pentru ca nu stiu ce este lista.get(0)

---

## **Problema 8 – Generic method + wildcard**

```java
public static <T> void afiseaza(T element) {
    System.out.println(element);
}

afiseaza(123);       // linia A
afiseaza("Salut");   // linia B
afiseaza(3.14);      // linia C

```

**Întrebare:** Ce se afișează la fiecare linie?
`123,salut,3.14

---

## **Problema 9 – Covariant + contravariant**

```java
List<? extends Number> lista1 = Arrays.asList(1,2,3);
lista1.add(4); // linia A
Number n = lista1.get(0); // linia B

List<? super Integer> lista2 = new ArrayList<Number>();
lista2.add(10); // linia C
Object o = lista2.get(0); // linia D

```

**Întrebare:** Pentru fiecare linie, ce se întâmplă la compilare și runtime?

### Rezolvare

La compilare:linia A nu va merge,linia B nu are probleme,linia C merge,linia D merge
La runtime:nici nu ajunem


## Problema 10

```java
static int test() {
    try {
        return 10;
    } finally {
        System.out.println("finally");
    }
}
```
**Întrebare:**  
– Ce se afișează?  
– Ce valoare se întoarce?

### Rezolvare

Se afiseaza "finally" + se returneaza valoarea 10


## Problema 11

```java
static int test() {
    try {
        int x = 10 / 0;
    } catch (ArithmeticException e) {
        return 1;
    } finally {
        return 2;
    }
}

```

**Întrebare:**  
– Compilează?  
– Ce se întoarce?

### Rezolvare

Compileaza si intoarce valoarea 2

## Problema 12

```java
static void test() {
    try {
        int x = 10 / 0;
    } finally {
        System.out.println("finally");
    }
}

public static void main(String[] args) {
    test();
    System.out.println("end");
}

```
**Întrebare:**  
– Ce se afișează?  
– Se ajunge la `"end"`?

### Rezolvare

Se afiseaza finally -> exceptia de la 10/0 nu este handled si nu mai ajunge la acel end

## Problema 13

```java
try {
    throw new NullPointerException();
} catch (RuntimeException e) {
    System.out.println("runtime");
} catch (NullPointerException e) {
    System.out.println("null");
}
```
**Întrebare:**  
– Compilează?  
– Dacă nu, de ce?

### Rezolvare

Programul nu compileaza,pt ca in Java, blocurile `catch` trebuie ordonate de la **cea mai specifică** excepție la **cea mai generală**. Deoarece `NullPointerException` moștenește `RuntimeException`, orice obiect de tip NPE este automat "prins" de primul bloc (`catch (RuntimeException e)`).

## Problema 14

```java
static void test() {
    try {
        throw new java.io.IOException();
    } catch (Exception e) {
        throw new RuntimeException();
    }
}
```

**Întrebare:**  
– Compilează fără `throws`?  
– Ce tip de excepție ajunge la apelant?

### Rezolvare
compileaza fara throws pentru ca dam catch la exceptia de tip checked (IOException) si dam throw la un unchecked exception,adica RuntimeException,deci nu este nevoie de throws.Tipul de exceptie care ajunge la apelant este RuntimeException

## Problema 15

```java
try {
    String s = null;
    s.length();
} catch (NullPointerException | ArithmeticException e) {
    System.out.println("prins");
}
```

**Întrebare:**  
– Compilează?  
– Ce se afișează?
### Rezolvare

codul compileaza,pentru ca s.length() ne da un NullPointerException,intra in catch,si afiseaza "prins"

## Problema 16

```java
static int test() {
    int x = 1;
    try {
        return x;
    } finally {
        x = 5;
    }
}
```

**Întrebare:**  
– Ce valoare se întoarce?  
– Contează modificarea din `finally`?

### Rezolvarea

se returneaza valoarea 1,deci modificarea din finally nu conteaza


## Problema 17

```java
static void test() throws Exception {
    throw new Exception();
}

public static void main(String[] args) {
    test();
}

```

**Întrebare:**  
– Compilează?  
– Ce trebuie adăugat ca să fie corect?

### Rezolvare

nu compileaza ce drq ba baiatule,trebuie adauguat un try catch in main

## Problema 18

```java
static void test() {
    try {
        throw new RuntimeException("A");
    } finally {
        throw new RuntimeException("B");
    }
}
```
**Întrebare:**  
– Ce excepție ajunge la runtime?  
– Ce se întâmplă cu prima?

### Rezolvare
prima exceptie nu ajunge la runtime si este pierduta,pentru ca o metoda nu poate arunca doua exceptii simultan si este pastrata doar cea mai recenta exceptie aruncata,doar a doua


## Problema 19

```java
class R implements AutoCloseable {
    public void close() {
        System.out.println("close");
    }
}

try (R r = new R()) {
    System.out.println("try");
} finally {
    System.out.println("finally");
}

```

**Întrebare:**  
– Ordinea exactă a afișării?

### Rezolvare

Afiseaza try,dupa close,dupa finally


## Problema 20

```java
try {
    throw new StackOverflowError();
} catch (Exception e) {
    System.out.println("exception");
} finally {
    System.out.println("finally");
}

```

**Întrebare:**  
– Ce se afișează?  
– Este prins `Error`?

### Rezolvare

Se afiseaza finally,iar error nu este prins pentru ca error nu este considerata o exceptie

## Problema 21

```java
class A {
    void run() throws java.io.IOException {}
}

class B extends A {
    void run() {}
}

```
**Întrebare:**  
– Compilează?  
– De ce este permis / interzis?

### Rezolvare

Da,compileaza,este permis pentru ca clasa B da override la metoda din A si nu mai arunca exceptie
#### Cele 3 reguli de bază la Overriding și Excepții:

Metoda din clasa copil (`B`):

1. **Poate să nu declare nicio excepție**
    
2. **Poate declara exact aceleași excepții** ca în părinte.
    
3. **Poate declara excepții mai specifice** (subclase ale celei din părinte). De exemplu, dacă `A` aruncă `IOException`, `B` poate arunca `FileNotFoundException`
#### Ce este interzis:

- `B` **nu** poate arunca o excepție mai generală (ex: dacă `A` aruncă `FileNotFoundException`, `B` nu poate arunca `IOException`).
    
- `B` **nu** poate arunca o excepție checked nouă, care nu are nicio legătură cu cea din părinte (ex: `SQLException`).

