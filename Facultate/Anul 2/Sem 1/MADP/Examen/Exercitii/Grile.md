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