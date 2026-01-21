
## 1. Tipuri generice  + metode generice

**Generics** = mecanism Java prin care o clasă, interfață sau metodă poate lucra cu **orice tip de date**, păstrând **siguranța tipurilor la compilare**.

### Exemple

#### Clasă generică

```java
class Cutie<T> { // T = tip generic
    private T continut;

    public void setContinut(T continut) {
        this.continut = continut;
    }

    public T getContinut() {
        return continut;
    }
}

public class Main {
    public static void main(String[] args) {
        Cutie<String> cutieText = new Cutie<>();
        cutieText.setContinut("Salut");
        System.out.println(cutieText.getContinut()); // Salut

        Cutie<Integer> cutieNumar = new Cutie<>();
        cutieNumar.setContinut(42);
        System.out.println(cutieNumar.getContinut()); // 42
    }
}

```

#### Metoda generica

```java
class Util {
    public static <T> void afiseaza(T valoare) {
        System.out.println(valoare);
    }
}

Util.<String>afiseaza("Salut"); // Salut
Util.<Integer>afiseaza(123);    // 123

```

#### Observatii ciudate

```java
if(obj instanceof Cutie<String>) {} // ❌ nu merge

T[] arr = new T[10]; // ❌ eroare


Cutie<? extends Number> cutie = new Cutie<Integer>();
cutie.setContinut(10); // ❌ eroare!
Number n = cutie.getContinut(); // ✅ ok
Integer m = cutie.getContinut(); // ❌ eroare la compilare


Cutie<int> cutie = new Cutie<>(); // ❌ nu merge
Cutie<Integer> cutie = new Cutie<>(); // ✅ corect
```

## 2.Constrangeri pe tipuri generice + wildcards

**Constrângeri pe tipuri generice** = limitezi tipurile pe care un parametru generic le poate folosi. Astfel, metodele sau clasele generice pot lucra doar cu **subtipuri specifice**.

### Upper Bound (`extends`)

- Tipul generic trebuie să fie **subtip** al unui anumit tip.

```java
class Util {
    public static <T extends Number> double dubleaza(T n) {
        return n.doubleValue() * 2;
    }
}

System.out.println(Util.dubleaza(10));   // 20.0
System.out.println(Util.dubleaza(3.5));  // 7.0
// Util.dubleaza("Salut"); ❌ String nu e Number

```

```java
List<? extends Number> lista = new ArrayList<Integer>();
// lista.add(10); ❌ nu merge
Number n = lista.get(0); // ✅ merge
//Concluzie - wildcard + upper bound = citire safe,scriere cooked
```

### Explicația

1. `? extends Number` = “un tip necunoscut care este subtip de Number”
    
    - Poate fi `Integer`, `Double`, `Float`, orice subtip de `Number`.
        
2. Compilatorul **nu știe exact tipul real al elementelor** în listă
    
    - Lista poate fi `List<Integer>` sau `List<Double>`
        
    - Dacă ai adăuga `5` (Integer), iar lista e de fapt `List<Double>`, atunci la runtime ar fi `ClassCastException`.
        
3. Din cauza **type safety**, compilatorul **blochează adăugarea** la `? extends` → nu poți risca să încalci tipul real.
### **Lower Bound (`super`)** (folosit cu wildcard-uri)

- Permite să **scrii valori într-un container** pentru tipul dat sau superclasele lui.

```java

List<? super Integer> lista = new ArrayList<Number>();
lista.add(10);    // ✅ merge
lista.add(3.14)  // ❌ Double nu este un parinte al lui Integer
Number n = lista.get(0); // ❌ trebuie cast la Integer
//Concluzie:wildcard + lower bound => scriere sigura,citire cooked

```
### Explicația

1. `? super Integer` = “un tip necunoscut care este superclasă a lui Integer”
    
    - Poate fi `Integer`, `Number` sau `Object`.
        
2. **Scrierea e sigură**
    
    - Poți adăuga `Integer`, pentru că orice superclasă acceptă subtipul (`Integer` este subtip pentru `Number` și `Object`).
        
3. **Citirea e limitată**
    
    - Compilatorul știe că lista poate conține orice supertip (`Number`, `Object`) → **nu știe sigur că e Integer**
        
    - Deci poți citi doar ca `Object`, nu ca `Integer` fără cast
### **Multiple Bounds**

- Poți să limitezi un tip generic la **mai multe interfețe sau clase**.

```java
class Util {
    public static <T extends Number & Comparable<T>> void afiseazaMax(T a, T b) {
        System.out.println(a.compareTo(b) > 0 ? a : b);
    }
}

afiseazaMax(5, 10);   // 10

```

## 3.Colectii generice

**Colecții generice** = clase din Java Collections Framework care folosesc **tipuri generice** pentru a stoca elemente, oferind **siguranță la compilare** și **evitând cast-uri inutile**.
## Type erasure și instanceof

```java
List<Integer> listaInt = new ArrayList<>();
List<Double> listaDouble = new ArrayList<>();

System.out.println(listaInt.getClass() == listaDouble.getClass());

```

**Ciudat:**

- La runtime, tipurile generice dispar → JVM vede doar `ArrayList`
    
- Nu poți verifica `instanceof List<Integer>`

