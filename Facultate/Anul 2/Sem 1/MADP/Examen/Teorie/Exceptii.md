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