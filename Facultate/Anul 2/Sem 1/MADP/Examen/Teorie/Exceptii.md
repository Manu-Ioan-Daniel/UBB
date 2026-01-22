## 1.Checked vs Unchecked Exceptions

### **Checked exceptions**

**Checked** = excepții **verificate la compilare**  
→ **trebuie** tratate cu `try-catch` **sau** declarate cu `throws`.

#### Exemple

```java
import java.io.*;

void citeste() throws IOException {
    FileReader f = new FileReader("file.txt");
}

try {
    citeste();
} catch (IOException e) {
    e.printStackTrace();
}

```

### **Unchecked exceptions**

**Unchecked** = excepții **neverificate la compilare**  
→ apar la **runtime**, nu e obligatoriu să le tratezi.

#### Exemple

```java
int x = 10 / 0; // ArithmeticException

String s = null;
s.length(); // NullPointerException
```

###  Diferențe-cheie

|Checked|Unchecked|
|---|---|
|Verificate la compilare|Apar la runtime|
|Obligatoriu `try-catch` sau `throws`|Opțional|
|Forțează tratarea erorilor|Semnalează bug-uri|
|Moștenesc `Exception`|Moștenesc `RuntimeException`|

### Exemple ciudate

#### Overriding + checked

```java
class A {
    void run() throws IOException {}
}

class B extends A {
    // void run() throws Exception {} // ❌ NU merge (mai general)
    void run() throws FileNotFoundException {} // ✅ mai specific ,exceptia mosteneste IOException
}
```

```java
class A {
    void run() throws IOException {}
}

class B extends A {
    void run() {} // ✅ voie să NU mai arunci nimic
}
```

#### Checked exception „mascată” ca unchecked

```java
static void test() {
    try {
        throw new IOException();
    } catch (IOException e) {
        throw new RuntimeException(e); // unchecked
    }
}

```

#### `throws Exception` ≠ aruncare reală

```java
void test() throws Exception {
    System.out.println("Salut");
}
```
- Metoda **nu aruncă nimic**
    
- Dar apelantul **e obligat să trateze**
    
- Creează **coupling inutil**
#### Unchecked în constructor

```java
class A {
    A() {
        throw new RuntimeException();
    }
}

```
- Constructorii **pot arunca unchecked**
- Obiectul nu se creează → stare inconsistentă

#### `Error` NU e Exception

```java
try {
    throw new OutOfMemoryError();
} catch (Exception e) {
    System.out.println("Prins");
}
```
**Ciudat:**

- `Error` NU e prins de `Exception`
    
- `Error` ≠ checked / unchecked → e altă categorie

#### Checked exception în lambda

```java
list.forEach(x -> {
    // throw new IOException(); // ❌ nu merge
});
```

### Try-catch-finnaly

**`try–catch–finally`** = mecanismul prin care Java **gestionează excepțiile** și **garantează execuția unui cod**, indiferent dacă apare sau nu o eroare.

#### Structura de bază

```java
try {
    // cod care poate arunca excepții
} catch (Exception e) {
    // tratează excepția
} finally {
    // se execută ORICUM
}

```

#### Fără excepție
```java
try {
    System.out.println("OK");
} catch (Exception e) {
    System.out.println("Eroare");
} finally {
    System.out.println("Finally");
}
/*
Output:
OK
Finally
*/

```

#### Cu excepție prinsă

```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Eroare");
} finally {
    System.out.println("Finally");
}
/*
Output:
Eroare
Finally
*/
```

#### Fără catch (doar try–finally)

```java
try {
    int x = 10 / 0;
} finally {
    System.out.println("Finally");
}

```

#### finally + return

```java
static int test() {
    try {
        return 1;
    } finally {
        return 2;
    }
}

System.out.println(test());
//Afiseaza 2
```

#### finally NU se execută

```java
try{
	System.exit(0);
}
finally{
	System.out.println("mama");
}
```

#### try-with-resources (legat de finally)

```java
try (FileReader f = new FileReader("a.txt")) {
    // folosire
} catch (IOException e) {

}
```

Echivalent cu:

- `finally` care închide automat resursa
    
- mai sigur decât `finally` manual

#### nume

```java
try {  
    throw new NullPointerException();  
} catch (RuntimeException e) {  
    System.out.println("runtime");  
} catch (NullPointerException e) {  
    System.out.println("null");  
}
//acest cod da eroare pentru ca NullPointerException deja a fost prins mai sus,deci linia de mai jos este redundanta asa ca compilatorul spune "NullPointerException has already been caught"
```


