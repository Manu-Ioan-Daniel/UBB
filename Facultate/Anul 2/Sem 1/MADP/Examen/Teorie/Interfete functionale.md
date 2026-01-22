
## 1.Metode default și static în interfețe

### Metode **default**

**Metodă default** = metodă cu implementare în interfață  
→ **nu rupe** clasele care deja implementează interfața

#### Exemplu

```java
interface A {
    default void run() {
        System.out.println("run din A");
    }
}

class B implements A {
}

B b = new B();
b.run(); // run din A
```

#### Conflict între metode default

```java
interface A {
    default void run() {}
}

interface B {
    default void run() {}
}

class C implements A, B {
    // OBLIGATORIU
    public void run() {}
}

//de exemplu

class C implements A, B {
    public void run() {
        A.super.run();
    }
}

```

**De ce?**  
Java nu știe pe care implementare să o folosească → te obligă să rezolvi conflictul

### Metode **static** în interfețe
#### Exemplu

```java
interface Utils {
    static void log(String msg) {
        System.out.println(msg);
    }
}

Utils.log("Salut");

class A implements Utils {}
A.log("x"); // eroare

```

Metodele statice **nu se moștenesc**

## Default vs Static – diferențe

|Default|Static|
|---|---|
|Se moștenește|NU se moștenește|
|Poate fi suprascrisă|NU poate fi suprascrisă|
|Se apelează pe obiect|Se apelează pe interfață|
|Folosită pt backward compatibility|Folosită pt utilitare|

### Default + clasă părinte

```java
class A {
    void run() {
        System.out.println("A");
    }
}

interface B {
    default void run() {
        System.out.println("B");
    }
}

class C extends A implements B {
}

C c = new C()
c.run() //afiseaza A

```
**Clasa bate interfața** întotdeauna

### Conflict default + abstract

```java
interface A {
    default void run() {}
}

interface B {
    void run(); // abstract
}

class C implements A, B {
}
//❌EROARE de compilare 
//dar asta ar merge:

class C implements A,B{
	@Override
	void run(){
		A.super().run()
	}
}
```

**De ce e ciudat?**

- Ai o implementare default
    
- DAR o interfață cere metoda abstractă
    
- Java te obligă să implementezi

📌 **Abstract > default**

### Default method + `Object`

```java
interface A {
    default String toString() {
        return "A";
    }
}

//sau

interface A {
    String toString() {
        return "A";
    }
}

```
❌ **NU compilează**

## ## 2.Interfețe funcționale (Java 8+)

**Interfață funcțională** = interfață care are **exact o metodă abstractă**  
→ poate fi implementată cu **lambda expression**, daddy 😈

Este **baza** pentru Java 8: lambda, streams, API-uri funcționale.


## Definiție clară

> O interfață funcțională are **o singură metodă abstractă**, indiferent câte metode `default` sau `static` are.

```java
@FunctionalInterface //- nu e OBLIGATORIU,doar recomandat,Compilatorul te protejează dacă mai adaugi o metodă abstractă
interface Operatie {
    int aplica(int a, int b);
}
```

### Lambda + interfață funcțională

```java
Operatie op = (a, b) -> a + b;
System.out.println(op.aplica(2, 3)); // 5
```

### Metodă abstractă **moștenită**

```java
interface A {
    void run();
}

@FunctionalInterface
interface B extends A {
    // tot UNA abstractă
}
```
### Default + abstract conflict

```java
interface A {
    default void run() {}
}

interface B {
    void run();
}

@FunctionalInterface
interface C extends A, B {}
//nu merge pentru ca structura este corupta de conficltul dintre cele doua metode run,chiar daca o pastreaza doar pe cea abstracta din B
```

## 3.Lambda expressions(Java 8+)
### Forma de bază

```java
(parametri) -> expresie
//sau
(parametri) -> { bloc de cod }
//exemple
x -> x * 2              // un parametru,return implicit
(x, y) -> x + y         // mai mulți,return implicit
() -> System.out.println("hi") // fără parametri
x -> { return x * 2; }  // return explicit

x -> { x * 2; } // lipsă return,gresit(avem acoladele)

```

### Variabilele capturate trebuie să fie „effectively final”

```java
int x = 10;
Runnable r = () -> {
    // x++; ❌
};

//Permis:
int x = 10;
Runnable r = () -> System.out.println(x);

```

### Overloading + lambda

```java
void test(Runnable r) {}
void test(Callable<Integer> c) {}

test(() -> 1); // ❌ ambiguu

//Compilatorul nu știe ce interfață vrei
```

## 3.Method References și Constructor References (Java 8+)

### Method references

```java
//forma generala
ClassName::methodName
object::methodName
```

|Tip|Sintaxă|Exemplu|
|---|---|---|
|Static method|`ClassName::staticMethod`|`Math::max`|
|Instance method|`instance::method`|`"hello"::length`|
|Instance method (any object of type)|`ClassName::instanceMethod`|`String::toUpperCase`|
|Constructor|`ClassName::new`|`ArrayList::new`|

#### Exemple method reference

```java
List<Integer> list = Arrays.asList(1,2,3);
list.forEach(System.out::println);
//echivalent lambda
list.forEach(x -> System.out.println(x));
//------------------------------------------

String prefix = "Hi ";
Consumer<String> printer = prefix::concat;
System.out.println(printer.apply("Daddy")); // Hi Daddy

//-------------------------------------------
List<String> list = Arrays.asList("a", "b", "c");
list.sort(String::compareToIgnoreCase);
//echivalent lambda
list.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
```

### Constructor references

```java
ClassName::new

Supplier<List<String>> s = ArrayList::new;
List<String> l = s.get();

Function<Integer, List<String>> f = ArrayList::new;
List<String> l = f.apply(10); // capacitate inițială 10
```

### Exemple ciudate

```java
List<String> list = Arrays.asList("1","2");
list.forEach(System.out::println); // ✅ OK

list.forEach(String::length); // ❌ Nu compilează, trebuie lambda:
list.forEach(s -> System.out.println(s.length()));
```
Reason: `forEach` așteaptă `Consumer<? super String>` → `String::length` returnează int, nu void

```java
Function<String, StringBuilder> f = StringBuilder::new;
StringBuilder sb = f.apply("test"); // ✅ constructor cu String

Function<String, Integer> f = Integer::parseInt; // static
Function<String, String> f2 = String::toUpperCase; // instanță arbitrary
```

## 4.Predicate,Consumer,Function,Supplier,Comparator,Optional

### Predicate

**Definiție:**

- O interfață funcțională care primește un argument și returnează **boolean**.
    
- Folosită pentru a testa condiții.

**Sintaxă + exemplu:**
```java
@FunctionalInterface
interface Predicate<T> {
    boolean test(T t);
}

Predicate<Integer> p = x -> x > 10;
System.out.println(p.test(5));  // false
System.out.println(p.test(15)); // true
```

### Consumer

**Definiție:**

- Interfață funcțională care primește un argument și **nu returnează nimic**.
    
- Folosită pentru operațiuni de tip side-effect.
    

Sintaxă: + exemplu

```java
@FunctionalInterface
interface Consumer<T> {
    void accept(T t);
}

Consumer<String> c = s -> System.out.println(s);
c.accept("Salut"); // Salut

```

### Function

**Definiție:**

- Interfață funcțională care primește un argument și returnează o valoare.
    
- Folosită pentru **transformări / mapping**.

Sintaxa + exemplu:

```java
@FunctionalInterface
interface Function<T, R> {
    R apply(T t);
}

Function<String, Integer> f = s -> s.length();
System.out.println(f.apply("Java")); // 4

```

### Supplier

**Definiție:**

- Interfață funcțională care **nu primește argument** și returnează o valoare.
    
- Folosită pentru generarea de valori la cerere.

Sintaxa + exemplu

```java
@FunctionalInterface
interface Supplier<T> {
    T get();
}

Supplier<Double> s = () -> Math.random();
System.out.println(s.get()); // 0.0 - 1.0

```

### Comparator

**Definiție:**

- Interfață funcțională folosită pentru **sortarea obiectelor**, comparând două obiecte.
    
- Returnează: <0 dacă primul < al doilea, 0 dacă egale, >0 dacă primul > al doilea.

Sintaxa + exemplu:

```java
@FunctionalInterface
interface Comparator<T> {
    int compare(T o1, T o2);
}

Comparator<String> cmp = (a, b) -> a.length() - b.length();
List<String> list = Arrays.asList("aaa", "b", "cc");
list.sort(cmp);
System.out.println(list); // [b, cc, aaa]

```

#### Functii des folosite

| **Funcție**                        | **Ce face**                                        | **Exemplu**                                        |
| ---------------------------------- | -------------------------------------------------- | -------------------------------------------------- |
| **`comparing()`**                  | Extrage o cheie pentru comparare.                  | `Comparator.comparing(User::getName)`              |
| **`reversed()`**                   | Inversează ordinea curentă.                        | `myComp.reversed()`                                |
| **`thenComparing()`**              | Adaugă un al doilea criteriu (dacă primul e egal). | `comp.thenComparing(User::getAge)`                 |
| **`naturalOrder()`**               | Sortare standard (1, 2, 3 sau A, B, C).            | `Comparator.naturalOrder()`                        |
| **`reverseOrder()`**               | Inversul ordinii naturale.                         | `Comparator.reverseOrder()`                        |
| **`nullsFirst()` / `nullsLast()`** | Decide unde merg valorile `null`.                  | `Comparator.nullsFirst(Comparator.naturalOrder())` |
### Optional

**Definiție:**

- Container pentru valori care pot fi `null`.
    
- Evită **NullPointerException** și forțează verificarea explicită.

Exemplu:

```java
Optional<String> opt = Optional.ofNullable(null);
System.out.println(opt.isPresent()); // false
opt.ifPresent(System.out::println);  // nu afișează nimic
```

### Exemple

#### Predicate compus

```java
Predicate<Integer> p = x -> x > 5;
Predicate<Integer> q = x -> x < 10;
System.out.println(p.and(q).test(7)); // true
System.out.println(p.and(q).test(11)); // false
```

#### Consumer cu lambda + side-effect

```java
List<String> list = new ArrayList<>();
Consumer<String> c = list::add;
c.accept("x");
c.accept("y");
System.out.println(list); // [x, y]
```

### Function cu tip generics dubios

```java
Function<Object, String> f = o -> o.toString();
System.out.println(f.apply(123)); // "123"
System.out.println(f.apply(null)); // NPE
```
### Supplier care generează valori random

```java
Supplier<Integer> s = () -> (int)(Math.random()*10);
System.out.println(s.get());
System.out.println(s.get());
```

### Comparator cu lambda cu overflow

```java
Comparator<Integer> cmp = (a, b) -> a - b;
System.out.println(cmp.compare(Integer.MAX_VALUE, -1)); // overflow!
```

### Optional cu map

```java
Optional<String> opt = Optional.ofNullable("java");
System.out.println(opt.map(String::toUpperCase).orElse("N/A")); // JAVA
System.out.println(Optional<String>ofNullable(null).map(String::toUpperCase).orElse("N/A")); // N/A
```

### Optional care conține null

```java
Optional<String> opt = Optional.of(null); // ❌ Throws NullPointerException
Optional<String> opt2 = Optional.ofNullable(null); // ✅ OK
```
### Predicate + lambda + method reference

```java
Predicate<String> p = String::isEmpty;
System.out.println(p.test("")); // true
System.out.println(p.test("abc")); // false
```
### Function care poate arunca NPE

```java
Function<String, Integer> f = s -> s.length();
System.out.println(f.apply(null)); // NPE
```
