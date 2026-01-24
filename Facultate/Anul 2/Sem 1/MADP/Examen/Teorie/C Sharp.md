# Ghid Complet de Programare C# și .NET (Curs 10 & 11)

Acest ghid acoperă integral materia din cursurile 10 și 11, incluzând detalii fine, comportamente „ciudate" și capcanele de examen.

---

# 📘 PARTEA 1: Platforma .NET și Fundamente (Curs 10)

## 1. Arhitectura .NET

### Ce este .NET?
- **.NET Framework / .NET Core:** Platformă completă de dezvoltare și execuție.
- **Filozofia:** „Scrie în orice limbaj .NET, rulează oriunde (unde este .NET instalat)."
- **Limbaje suportate:** C# (principal), VB.NET, F# (funcțional), C++ (managed).

### Componente Cheie
| Acronim | Denumire | Rol |
|---------|----------|-----|
| **CLI** | Common Language Infrastructure | Standardul |
| **CLR** | Common Language Runtime | Implementarea (Mașina Virtuală) |
| **JIT** | Just-In-Time Compiler | Compilează CIL în cod nativ la prima apelare |
| **GC** | Garbage Collector | Eliberează automat memoria obiectelor nefolosite |
| **CTS** | Common Type System | Sistem de tipuri unificat între limbaje |
| **Assembly** | - | Unitatea de compilare (`.dll` sau `.exe`) |

### Procesul de Compilare
1. **Compilare Sursă (`.cs`) → CIL:** Codul e transformat în byte-code universal, stocat în Assemblies (`.exe`/`.dll`).
2. **Execuție Runtime (CLR + JIT):** La rulare, JIT compilează CIL în cod nativ optimizat pentru procesor.

---

## 2. Structura unui Program C#

```csharp
using System;

namespace PrimulMeuProgram 
{
    class Program 
    {
        
        static void Main(string[] args) 
        {
            Console.WriteLine("Salut, lume!");
        }
    }
}
```

>[!WARNING] 
**** `main` (cu m mic) NU funcționează! C# e case-sensitive.

## 3. Sistemul de Tipuri (Foarte Important!)

În C#, **totul** moștenește din `System.Object`. Tipurile se împart strict în două categorii:

### A. Tipuri Valoare (Value Types)
- Stocate pe **Stivă (Stack)**
- Conțin **datele efective**
- La atribuire (`a = b`) se copiază **valoarea**
- Nu pot fi `null` (decât dacă le declari `Nullable<int>` sau `int?`)

| Categorie | Exemple |
|-----------|---------|
| Numere | `int`, `long`, `float`, `double`, `decimal` |
| Logice | `bool` |
| Caractere | `char` |
| Custom | `struct`, `enum` |

**Exemplu - Copiere Value Type:**
```csharp
int a = 5;
int b = a;  // se copiază VALOAREA
b = 10;     // modificăm b
Console.WriteLine(a); // Afișează: 5 (a rămâne neschimbat!)
```

### B. Tipuri Referință (Reference Types)
- Stocate pe **Heap** (memoria dinamică)
- Variabila conține doar o **adresă de memorie**
- La atribuire (`a = b`) se copiază **adresa** (ambele arată spre același obiect!)
- Pot fi `null`

| Categorie | Exemple |
|-----------|---------|
| Clase | `class` |
| Interfețe | `interface` |
| Delegați | `delegate` |
| Șiruri | `string` |
| Tablouri | `array` |

**Exemplu - Copiere Reference Type (CAPCANĂ!):**
```csharp
class Persoana { public int Varsta; }

Persoana p1 = new Persoana();
p1.Varsta = 25;

Persoana p2 = p1;  // se copiază ADRESA (referința)
p2.Varsta = 30;    // modificăm prin p2

Console.WriteLine(p1.Varsta); // Afișează: 30 !!! (p1 și p2 arată spre ACELAȘI obiect)
```

###  Boxing și Unboxing

Când pui un value type într-o variabilă de tip `object`, se face **boxing** (se împachetează pe heap).

```csharp
int x = 42;
object obj = x;     // BOXING: x e copiat pe heap, obj ține referința
int y = (int)obj;   // UNBOXING: se extrage valoarea înapoi

// 
obj = 100;
Console.WriteLine(x); // Afișează: 42 (x e independent, boxing a făcut o copie!)
```

>[!WARNING] 
Boxing/Unboxing e LENT (overhead de memorie). Evită-l în bucle!
###  Operatorii `is` și `as` (Downcasting Sigur)

1. **`is`**: Verifică dacă un obiect este de un anumit tip. Returnează `true`/`false`.
   ```csharp
   object obj = "salut";
   if (obj is string) { 
       Console.WriteLine("E string!"); 
   }
   
   // Pattern matching (C# 7+):
   if (obj is string s) {
       Console.WriteLine(s.ToUpper()); // s e deja convertit!
   }
   ```

2. **`as`**: Încearcă conversia. Dacă eșuează, returnează `null` (nu aruncă excepție).
   ```csharp
   Animal animal = new Caine();
   Pisica p = animal as Pisica;
   if (p != null) { 
       p.Miauna(); 
   } else {
       Console.WriteLine("Nu e pisică!"); // Acesta se afișează
   }
   ```
   
>[!WARNING]
 `as` NU merge cu tipuri valoare (`int`, `double`) care nu pot fi null.
```csharp
object obj = 42;
// int x = obj as int; // EROARE DE COMPILARE! int nu poate fi null
int? x = obj as int?;  // OK, nullable int
```

### Diferența dintre cast explicit și `as`:
```csharp
object obj = "text";

// Cast explicit - aruncă InvalidCastException dacă eșuează:
int x = (int)obj; // CRASH! InvalidCastException

// as - returnează null dacă eșuează:
string s = obj as string; // OK, s = "text"
int? y = obj as int?;     // OK, y = null (fără crash)
```

---

## 4. Variabile și Sintaxă

### Declarare și Inițializare
```csharp
int numar = 10;
string text = "C# e fain"; 
double pi = 3.14;
bool esteValid = true;

// var - inferență de tip (compilatorul ghicește tipul)
var x = 100;        // x devine int
var y = "hello";    // y devine string
// var z;           // EROARE! Trebuie inițializat imediat
```

### Constante vs Readonly

| Tip | Setare | Moment | Poate fi de tip referință? |
|-----|--------|--------|---------------------------|
| `const` | La declarare | Compilare (hardcodat) | Doar `string` și `null` |
| `readonly` | La declarare sau în Constructor | Runtime | Da, orice tip |

```csharp
const int ZileInSaptamana = 7; // Nu se poate schimba niciodată
// const DateTime Acum = DateTime.Now; // EROARE! Nu se știe la compilare

class Cerc {
    public readonly double Raza;
    public readonly DateTime CreatLa = DateTime.Now; // OK! Se calculează la runtime
    
    public Cerc(double r) { Raza = r; } // OK
    void Metoda() { Raza = 5; } // EROARE! Nu poți modifica după constructor
}
```

> [!WARNING]
>  `const` pentru referințe funcționează doar cu `string` și `null`:

```csharp
const string Mesaj = "Hello"; // OK
const object Obj = null;      // OK
const int[] Arr = {1,2,3}; // EROARE! Array nu poate fi const
```

---

## 5. Tablouri (Arrays)

>[!WARNING] 
Parantezele `[]` fac parte din tip, nu din variabilă!
### Vectori Simpli (Unidimensionali)
```csharp
int[] numere = new int[5]; // Vector de 5 int-uri (inițializate cu 0)
int[] prime = { 2, 3, 5, 7 }; // Inițializare directă
numere[0] = 1; // Accesare
numere.Length; // Proprietate, nu metodă!
```

### Tablouri Multidimensionale (Matrici Reale)
Stocate continuu în memorie.
```csharp
int[,] matrice = new int[3, 2]; // 3 rânduri, 2 coloane
matrice[0, 1] = 5; // Accesare cu virgulă: [x, y]

// Dimensiuni:
matrice.GetLength(0); // 3 (rânduri)
matrice.GetLength(1); // 2 (coloane)
matrice.Length;       // 6 (total elemente)
```

### Tablouri „Jagged" (Vectori de Vectori)
Fiecare rând poate avea lungime diferită.
```csharp
int[][] jagged = new int[3][];
jagged[0] = new int[10];
jagged[1] = new int[2];
jagged[2] = new int[5];
jagged[0][1] = 99; // Accesare dublă: [x][y]
```

### Arrays sunt Reference Types!
```csharp
int[] a = { 1, 2, 3 };
int[] b = a;         // b arată spre ACELAȘI array!
b[0] = 999;
Console.WriteLine(a[0]); // Afișează: 999

// Dacă vrei o copie independentă:
int[] c = (int[])a.Clone();
// sau
int[] d = new int[a.Length];
Array.Copy(a, d, a.Length);
```

---

## 6. Programare Orientată pe Obiecte (OOP)

### Clase și Metode
```csharp
public class Persoana 
{
    // Câmp (Field) - de obicei private
    private int _varsta;

    // Proprietate (Property) - Modul C# de a face Get/Set
    public string Nume { get; set; } // Auto-implemented property

    // Constructor
    public Persoana(string nume) {
        Nume = nume;
    }

    // Metodă
    public void Saluta() {
        Console.WriteLine($"Salut, sunt {Nume}");
    }
}
```

### Proprietăți (Properties) - Detalii
Nu sunt doar variabile! Sunt metode mascate (`get_Nume`, `set_Nume`).
```csharp
// Full Property (cu backing field)
private int _varsta;
public int Varsta {
    get { return _varsta; }
    set {
        if (value < 0) throw new ArgumentException("Vârsta nu poate fi negativă!");
        _varsta = value; // 'value' este cuvânt cheie special
    }
}

// Auto-property cu valoare default (C# 6+)
public string Tara { get; set; } = "România";

// Read-only auto-property (setabilă doar în constructor)
public Guid Id { get; } = Guid.NewGuid();
```

### Proprietăți cu acces diferit pentru get/set
```csharp
public string Parola { 
    get; 
    private set;  // Poate fi citită din exterior, dar setată doar din clasă
}

// Sau chiar:
public int Scor { 
    get; 
    protected set;  // Setabilă în clasă sau clase derivate
}
```

### Indexatori (Indexers)
Permit unui obiect să fie tratat ca un vector (`obj[0]`).
```csharp
class Catalog {
    private string[] studenti = new string[100];
    
    // Indexator - folosește cuvântul cheie 'this'
    public string this[int index] {
        get { 
            if (index < 0 || index >= studenti.Length)
                throw new IndexOutOfRangeException();
            return studenti[index]; 
        }
        set { studenti[index] = value; }
    }
    
    // Poți avea mai mulți indexatori cu tipuri diferite!
    public int this[string nume] {
        get {
            for (int i = 0; i < studenti.Length; i++)
                if (studenti[i] == nume) return i;
            return -1;
        }
    }
}

// Utilizare:
var cat = new Catalog();
cat[0] = "Popescu";      // Arată ca un array, dar e obiect!
int poz = cat["Popescu"]; // Indexare după string!
```

### Modificatori de Parametri

| Modificator | Comportament | Inițializare necesară |
|-------------|--------------|----------------------|
| *(implicit)* | Prin valoare (copie) | Da |
| `ref` | Prin referință (modificările persistă) | Da, înainte de apel |
| `out` | Pentru returnare valori multiple | Nu, funcția o setează |
| `in` | Prin referință read-only (optimizare) | Da |
| `params` | Număr variabil de argumente | - |

```csharp
// ref - modificările persistă
void Dubleaza(ref int x) { x = x * 2; }
int a = 5; 
Dubleaza(ref a); 
Console.WriteLine(a); // 10

// out - funcția TREBUIE să seteze valoarea
bool TryParse(string s, out int rezultat) {
    if (int.TryParse(s, out rezultat)) return true;
    rezultat = 0;  // OBLIGATORIU să setezi înainte de return
    return false;
}

// params - număr variabil
int Suma(params int[] numere) {
    int total = 0;
    foreach (var n in numere) total += n;
    return total;
}
Console.WriteLine(Suma(1, 2, 3, 4, 5)); // 15
Console.WriteLine(Suma());              // 0 (array gol)
```

### ref vs out:
```csharp
// ref - variabila TREBUIE inițializată ÎNAINTE
int x;
// Dubleaza(ref x); // EROARE! x nu e inițializat

// out - variabila NU trebuie inițializată înainte
int y;
TryParse("123", out y); // OK! funcția o va seta

// out inline (C# 7+)
if (int.TryParse("456", out int z)) {
    Console.WriteLine(z); // z e declarat și inițializat aici
}
```

### Modificatori de Acces

| Modificator | Vizibilitate |
|-------------|--------------|
| `public` | Peste tot |
| `private` | Doar în clasa curentă (DEFAULT pentru membri) |
| `protected` | Clasa curentă + clase derivate |
| `internal` | Tot assembly-ul curent (proiectul) - DEFAULT pentru clase |
| `protected internal` | Assembly curent **SAU** clase derivate din alte assembly-uri |
| `private protected` | Clase derivate **DIN ACELAȘI** assembly (C# 7.2+) |

>[!WARNING] 
`protected internal` este **reuniune** (OR), NU intersecție!

```csharp
// Dacă nu pui nimic:
class MeaClasa { }           // e internal (vizibilă doar în proiect)
class Clasa {
    int camp;                // e private
    void Metoda() { }        // e private
}
```

---

## 7. Moștenire și Polimorfism

> [!WARNING]
>  În C#, metodele NU sunt virtuale implicit. Trebuie marcate explicit!

### Cuvinte Cheie

| Cuvânt | Rol |
|--------|-----|
| `virtual` | Permite suprascrierea într-o clasă derivată |
| `override` | Suprascrie o metodă virtuală |
| `sealed` | Interzice moștenirea clasei sau suprascrierea metodei |
| `base` | Referință la clasa părinte |
| `abstract` | Metodă fără implementare, TREBUIE suprascrisă |

```csharp
class Animal {
    public virtual void Suna() { Console.WriteLine("..."); }
}
class Caine : Animal {
    public override void Suna() { Console.WriteLine("Ham Ham"); }
}

// Polimorfism în acțiune:
Animal a = new Caine();
a.Suna(); // Afișează: "Ham Ham" (metoda din Caine, nu din Animal)
```

### `override` vs `new` (Hiding) - Foarte Important!

- **`override`**: Polimorfismul funcționează (se apelează metoda obiectului real)
- **`new`**: **ASCUNDE** metoda bazei. Polimorfismul NU funcționează!

```csharp
class Baza { 
    public virtual void Metoda() => Console.Write("Baza "); 
}
class DerivataOverride : Baza { 
    public override void Metoda() => Console.Write("Override "); 
}
class DerivataNew : Baza { 
    public new void Metoda() => Console.Write("New "); 
}

Baza b1 = new DerivataOverride();
Baza b2 = new DerivataNew();

b1.Metoda(); // Afișează: "Override " (Polimorfism OK)
b2.Metoda(); // Afișează: "Baza " (Polimorfismul e SPART de 'new')!!!

// Dar dacă folosești tipul derivat direct:
DerivataNew d = new DerivataNew();
d.Metoda();  // Afișează: "New " (acum se găsește metoda din DerivataNew)
```

### Exemplu complet cu lanț de moștenire:
```csharp
class A {
    public virtual void Test() => Console.WriteLine("A");
}
class B : A {
    public override void Test() => Console.WriteLine("B");
}
class C : B {
    public new void Test() => Console.WriteLine("C");
}
class D : C {
    public override void Test() => Console.WriteLine("D"); // EROARE!
    // Nu poți face override la o metodă ascunsă cu 'new'
}

// Ce afișează?
A obj = new C();
obj.Test(); // Afișează: "B" 
// De ce? new în C a "rupt" lanțul, dar A.Test() e virtual, 
// deci se caută cel mai recent override = B.Test()
```

### `sealed`
```csharp
// Pe clasă - nimeni nu o poate moșteni
sealed class ClasaFinala { }
// class Copil : ClasaFinala { } // EROARE!

// Pe metodă - oprește lanțul de override-uri
class Baza {
    public virtual void M() { }
}
class Derivata : Baza {
    public sealed override void M() { } // Ultima implementare permisă
}
class MaiDerivata : Derivata {
    // public override void M() { } // EROARE! Metodă sealed
}
```

### `base` - Apelarea metodei din clasa părinte
```csharp
class Animal {
    public virtual void Mananca() {
        Console.WriteLine("Mănâncă generic");
    }
}
class Caine : Animal {
    public override void Mananca() {
        base.Mananca(); // Apelează metoda din Animal
        Console.WriteLine("...dar preferă oasele!");
    }
}

new Caine().Mananca();
// Output:
// Mănâncă generic
// ...dar preferă oasele!
```

### Clase și Metode Abstracte
```csharp
abstract class Forma {
    public abstract double CalculeazaArie(); // FĂRĂ implementare
    
    public void Afiseaza() { // Metodă normală, are implementare
        Console.WriteLine($"Aria: {CalculeazaArie()}");
    }
}

class Cerc : Forma {
    public double Raza { get; set; }
    
    public override double CalculeazaArie() { // OBLIGATORIU
        return Math.PI * Raza * Raza;
    }
}

// Forma f = new Forma(); // EROARE! Nu poți instanția abstract
Forma f = new Cerc { Raza = 5 };
f.Afiseaza(); // Aria: 78.54...
```

### Destructori (Finalizers)
Sintaxă C++ `~Clasa`, dar comportament diferit!
- NU știi când se apelează (decide Garbage Collector-ul)
- Folosit doar pentru resurse „unmanaged" (fișiere, conexiuni OS)
```csharp
class Fisier {
    private IntPtr handle; // resursă unmanaged
    
    ~Fisier() {
        // Curățenie finală (foarte rar folosit în C# modern)
        // Preferă să folosești IDisposable și using!
        Console.WriteLine("Destructor apelat... când?");
    }
}
```

>[!WARNING]  
Nu știi când rulează destructorul. Poate trece mult timp!

```csharp
var f = new Fisier();
f = null; // Obiectul e eligibil pentru GC, DAR destructorul nu rulează acum
// ... poate rulează după câteva secunde, minute, sau deloc până la exit
```

### Interfețe
- O clasă poate moșteni o singură clasă, dar **oricâte** interfețe.

**Explicit Interface Implementation** (când două interfețe au aceeași metodă):
```csharp
interface IAvion {
    void Decoleaza();
}
interface IPasare {
    void Decoleaza();
}

class AvionPasare : IAvion, IPasare {
    // Implementare implicită (una singură pentru ambele)
    public void Decoleaza() {
        Console.WriteLine("Generic decolează");
    }
    
    // SAU implementare explicită (diferite pentru fiecare):
    void IAvion.Decoleaza() { Console.WriteLine("Avion: pornește motoarele"); }
    void IPasare.Decoleaza() { Console.WriteLine("Pasăre: bate din aripi"); }
}

var x = new AvionPasare();
// x.Decoleaza(); // EROARE dacă ai doar implementări explicite
((IAvion)x).Decoleaza();  // "Avion: pornește motoarele"
((IPasare)x).Decoleaza(); // "Pasăre: bate din aripi"
```

---

# 📙 PARTEA 2: Concepte Avansate

## 8. Genericitate (Generics)

Permite scrierea de cod care lucrează cu orice tip, păstrând Type Safety.

```csharp
public class Cutie<T> 
{
    private T continut;
    public void Pune(T obiect) { continut = obiect; }
    public T Scoate() { return continut; }
}

// Utilizare:
Cutie<int> cutieInt = new Cutie<int>();
cutieInt.Pune(100);
// cutieInt.Pune("text"); // EROARE DE COMPILARE! Type safety

Cutie<string> cutieString = new Cutie<string>();
cutieString.Pune("Salut");
```

### Metode Generice
```csharp
// Metoda generică - tipul se deduce automat
T Max<T>(T a, T b) where T : IComparable<T> {
    return a.CompareTo(b) > 0 ? a : b;
}

int maxInt = Max(5, 10);           // T = int (dedus automat)
string maxStr = Max("abc", "xyz"); // T = string (dedus automat)
```

### Constrângeri (Constraints)

| Constrângere | Semnificație |
|--------------|--------------|
| `where T : struct` | Trebuie să fie value type |
| `where T : class` | Trebuie să fie reference type |
| `where T : new()` | Trebuie să aibă constructor fără parametri |
| `where T : IComparable` | Trebuie să implementeze interfața |
| `where T : ClasaBaza` | Trebuie să moștenească din ClasaBaza |
| `where T : U` | T trebuie să fie sau să moștenească din U |

**Constrângeri Multiple:**
```csharp
// T trebuie să fie clasă, SĂ AIBĂ constructor gol, ȘI să fie IComparable
class Manager<T> where T : class, IComparable, new() 
{
    public T Creaza() {
        T obj = new T(); // Posibil doar datorită constrângerii new()
        return obj;
    }
}
```

### `default(T)` - Valoarea implicită pentru orice tip
Într-o metodă generică, nu poți scrie `T x = null` sau `T x = 0`.

| Tip | Valoare `default(T)` |
|-----|---------------------|
| Reference type | `null` |
| Numeric type | `0` |
| bool | `false` |
| char | `'\0'` |
| Struct | Toți membrii setați la default |

```csharp
T GetDefaultValue<T>() {
    return default(T);  // sau doar: default (C# 7.1+)
}
Console.WriteLine(GetDefaultValue<int>());      // 0
Console.WriteLine(GetDefaultValue<string>());   // (null)
Console.WriteLine(GetDefaultValue<bool>());     // False
```

###   Static cu Generics
```csharp
class Container<T> {
    public static int Count = 0;
}

Container<int>.Count = 10;
Container<string>.Count = 20;
Container<bool>.Count = 30;

Console.WriteLine(Container<int>.Count);    // 10 !!!
Console.WriteLine(Container<string>.Count); // 20 !!!
Console.WriteLine(Container<bool>.Count);   // 30 !!!

// Fiecare Container<T> e o CLASĂ DIFERITĂ în runtime!
```

---

## 9. Colecții

Nu folosi vectori simpli (`[]`) decât dacă e necesar!

### Colecții Generice (`System.Collections.Generic`)

```csharp
// List<T> - Vector dinamic (cel mai folosit)
List<string> nume = new List<string>();
nume.Add("Ana");
nume.Add("Ion");
nume.AddRange(new[] { "Maria", "Vasile" });
nume.Remove("Ion");
nume.RemoveAt(0);
bool areAna = nume.Contains("Ana");
int index = nume.IndexOf("Maria");

// Dictionary<K, V> - Perechi Cheie-Valoare (HashMap)
Dictionary<string, int> note = new Dictionary<string, int>();
note["Mate"] = 10;
note["Fizică"] = 8;
note.Add("Chimie", 9);

if (note.TryGetValue("Mate", out int nota)) {
    Console.WriteLine($"Nota la Mate: {nota}");
}

// HashSet<T> - Elemente unice, fără ordine
HashSet<int> set = new HashSet<int> { 1, 2, 3, 2, 1 };
Console.WriteLine(set.Count); // 3 (duplicatele ignorate)

// Queue<T> - Coadă (FIFO)
Queue<string> coada = new Queue<string>();
coada.Enqueue("primul");
coada.Enqueue("al doilea");
string prim = coada.Dequeue(); // "primul"

// Stack<T> - Stivă (LIFO)
Stack<int> stiva = new Stack<int>();
stiva.Push(1);
stiva.Push(2);
int ultim = stiva.Pop(); // 2
```

### `IEnumerable` vs `IEnumerator`

| Interfață | Rol | Metode |
|-----------|-----|--------|
| `IEnumerable<T>` | „Ceva ce poate fi iterat" (colecția) | `GetEnumerator()` |
| `IEnumerator<T>` | „Cursorul" care face iterarea | `MoveNext()`, `Current`, `Reset()` |

> `foreach` folosește `GetEnumerator()` în spate.

```csharp
// De fapt, foreach face asta:
List<int> numere = new List<int> { 1, 2, 3 };

// foreach (var n in numere) { ... }
// se traduce în:

IEnumerator<int> enumerator = numere.GetEnumerator();
while (enumerator.MoveNext()) {
    int n = enumerator.Current;
    // ... cod
}
```

###   Modificarea colecției în foreach
```csharp
List<int> numere = new List<int> { 1, 2, 3, 4, 5 };

// CRASH! InvalidOperationException
foreach (var n in numere) {
    if (n == 3) numere.Remove(n); // NU poți modifica în foreach!
}

// Soluții:
// 1. Iterează pe o copie
foreach (var n in numere.ToList()) {
    if (n == 3) numere.Remove(n);
}

// 2. Folosește for în ordine inversă
for (int i = numere.Count - 1; i >= 0; i--) {
    if (numere[i] == 3) numere.RemoveAt(i);
}

// 3. Folosește RemoveAll (cel mai curat)
numere.RemoveAll(n => n == 3);
```

---

## 10. Delegați și Evenimente

### Delegați (Delegates)
Gândește-te la un delegat ca la o „variabilă care ține o funcție".

```csharp
// Definire delegat (tipul funcției)
delegate int Operatie(int a, int b);

// Metode care se potrivesc semnăturii
int Aduna(int a, int b) { return a + b; }
int Inmulteste(int a, int b) { return a * b; }

// Atribuire și apelare
Operatie op = Aduna;
Console.WriteLine(op(3, 4)); // 7

op = Inmulteste;
Console.WriteLine(op(3, 4)); // 12
```

### Delegați Built-in: `Func` și `Action`

```csharp
// Action - delegat care returnează void
Action saluta = () => Console.WriteLine("Salut!");
Action<string> salutaCuNume = (nume) => Console.WriteLine($"Salut, {nume}!");
Action<int, int> afiseazaSuma = (a, b) => Console.WriteLine(a + b);

// Func - delegat care returnează valoare (ultimul tip e return-ul)
Func<int> random = () => new Random().Next();
Func<int, int> patrat = x => x * x;
Func<int, int, int> aduna = (a, b) => a + b;
Func<string, int, bool> verificaLungime = (s, len) => s.Length >= len;
```

### Multicast Delegates
```csharp
Action metode = Metoda1;
metode += Metoda2;
metode += Metoda3;

metode(); // Apelează Metoda1, apoi Metoda2, apoi Metoda3

metode -= Metoda2; // Îndepărtează Metoda2
metode(); // Apelează doar Metoda1 și Metoda3
```

> [!WARNING]
> **Capcană:** Dacă metodele returnează valoare (nu sunt `void`), delegatul returnează **doar rezultatul ultimei metode apelate**!
>

```csharp
Func<int> multi = () => { Console.WriteLine("Prima"); return 1; };
multi += () => { Console.WriteLine("A doua"); return 2; };
multi += () => { Console.WriteLine("A treia"); return 3; };

int rezultat = multi();
// Output:
// Prima
// A doua  
// A treia
// rezultat = 3 (doar ultima valoare!)
```

### Funcții Anonime și Lambda
```csharp
// Funcție anonimă (sintaxă veche)
Func<int, int> patrat1 = delegate(int x) { return x * x; };

// Lambda expression (sintaxă nouă, preferată)
Func<int, int> patrat2 = x => x * x;

// Lambda cu mai multe instrucțiuni
Func<int, int> patrat3 = x => {
    Console.WriteLine($"Calculez pătratul lui {x}");
    return x * x;
};

// Lambda cu mai mulți parametri
Func<int, int, int> aduna = (a, b) => a + b;
```

### Evenimente (Events)
- Bazate pe delegați, dar mai sigure (doar proprietarul clasei poate declanșa evenimentul)

```csharp
class Buton {
    // Declarație event (bazat pe Action)
    public event Action Click;
    
    // Metodă care declanșează evenimentul
    public void Apasa() {
        Console.WriteLine("Buton apăsat!");
        Click?.Invoke(); // ?. verifică dacă e null
    }
}

// Utilizare:
var buton = new Buton();

// Abonare la eveniment
buton.Click += () => Console.WriteLine("Handler 1 executat");
buton.Click += () => Console.WriteLine("Handler 2 executat");

buton.Apasa();
// Output:
// Buton apăsat!
// Handler 1 executat
// Handler 2 executat

// Dezabonare (ai nevoie de referința la metodă)
Action handler = () => Console.WriteLine("Handler");
buton.Click += handler;
buton.Click -= handler;
```

**Sintaxă Explicită (Event Accessors):**
```csharp
private Action _click;
public event Action Click {
    add { 
        _click += value; 
        Console.WriteLine("Cineva s-a abonat!"); 
    }
    remove { 
        _click -= value;
        Console.WriteLine("Cineva s-a dezabonat!"); 
    }
}
```

###  Event vs Delegate Public - De ce contează?
```csharp
class ClasaCuDelegate {
    public Action Callback; // NU e event
}

class ClasaCuEvent {
    public event Action Callback; // E event
}

var d = new ClasaCuDelegate();
var e = new ClasaCuEvent();

// Cu delegate public:
d.Callback = null;        // OK - poți șterge toți subscriberii!
d.Callback?.Invoke();     // OK - poți declanșa din exterior
d.Callback = MetodaNoua;  // OK - poți înlocui complet

// Cu event:
// e.Callback = null;     // EROARE!
// e.Callback?.Invoke();  // EROARE!
e.Callback += Metoda;     // OK - doar += și -=
e.Callback -= Metoda;     // OK
```

---

## 11. LINQ (Language Integrated Query)

Permite prelucrarea colecțiilor folosind sintaxă declarativă, direct în C#.

### Sintaxa Metode (Fluent)
```csharp
var studenti = new List<Student> {
    new Student { Nume = "Ana", Varsta = 20, Nota = 9 },
    new Student { Nume = "Ion", Varsta = 17, Nota = 7 },
    new Student { Nume = "Maria", Varsta = 22, Nota = 10 },
    new Student { Nume = "Vasile", Varsta = 19, Nota = 6 }
};

// Filtrare + Sortare + Proiecție
var rezultate = studenti
    .Where(s => s.Varsta >= 18)       // Filtrare: doar majori
    .OrderByDescending(s => s.Nota)   // Sortare: după notă descrescător
    .ThenBy(s => s.Nume)              // Apoi după nume crescător
    .Select(s => new {                // Proiecție: obiect anonim
        s.Nume, 
        Categorie = s.Nota >= 9 ? "Excelent" : "Bun" 
    });

foreach (var r in rezultate) 
    Console.WriteLine($"{r.Nume}: {r.Categorie}");
// Ana: Excelent
// Maria: Excelent
// Vasile: Bun
```

### Sintaxa Query (SQL-like)
```csharp
var rezultate = 
    from s in studenti
    where s.Varsta >= 18
    orderby s.Nota descending, s.Nume
    select new { s.Nume, s.Nota };
```

### Metode LINQ Comune

| Metodă | Scop |
|--------|------|
| `Where(predicate)` | Filtrare |
| `Select(selector)` | Proiecție/transformare |
| `OrderBy/OrderByDescending` | Sortare |
| `ThenBy/ThenByDescending` | Sortare secundară |
| `First/FirstOrDefault` | Primul element |
| `Single/SingleOrDefault` | Exact un element |
| `Any(predicate?)` | Există vreun element? |
| `All(predicate)` | Toate respectă condiția? |
| `Count(predicate?)` | Numără elementele |
| `Sum/Average/Min/Max` | Agregări |
| `GroupBy(keySelector)` | Grupare |
| `Join` | Join între colecții |
| `Distinct()` | Elimină duplicatele |
| `Take(n)/Skip(n)` | Paginare |
| `ToList()/ToArray()` | Materializare |

###  First vs FirstOrDefault, Single vs SingleOrDefault
```csharp
var numere = new List<int> { 1, 2, 3 };
var gol = new List<int>();

// First - aruncă excepție dacă nu există
numere.First();       // 1
// gol.First();       // CRASH! InvalidOperationException

// FirstOrDefault - returnează default dacă nu există
numere.FirstOrDefault(); // 1
gol.FirstOrDefault();    // 0 (default pentru int)

// Single - exact UN element, altfel excepție
numere.Single(n => n == 2);     // 2
// numere.Single();             // CRASH! Mai mult de 1 element
// numere.Single(n => n > 100); // CRASH! Niciun element

// SingleOrDefault - exact 0 sau 1 element
numere.SingleOrDefault(n => n == 2);     // 2
numere.SingleOrDefault(n => n > 100);    // 0 (nu există)
// numere.SingleOrDefault();             // CRASH! Mai mult de 1
```

### Metode de Extensie (Extension Methods)
LINQ funcționează datorită metodelor de extensie. Permit adăugarea de metode noi unor clase existente fără a le modifica.

```csharp
public static class StringExtensions {
    // 'this' înaintea primului parametru = extension method
    public static bool EsteEmail(this string s) {
        return s.Contains("@") && s.Contains(".");
    }
    
    public static string Repeta(this string s, int n) {
        return string.Concat(Enumerable.Repeat(s, n));
    }
}

// Utilizare (arată ca și cum ar fi metode ale string-ului):
string email = "test@email.com";
Console.WriteLine(email.EsteEmail());  // True
Console.WriteLine("Ha".Repeta(3));     // HaHaHa
```

###  Deferred Execution (Execuție Amânată) - CRITIC!

Interogările LINQ **NU** rulează când sunt definite, ci când sunt **consumate**.

```csharp
var numere = new List<int> { 1, 2, 3 };

// 1. Definim interogarea (NIMIC nu se întâmplă aici)
var query = numere.Where(n => {
    Console.WriteLine($"Verific {n}");
    return n > 0;
});

Console.WriteLine("Query definit, dar încă nu a rulat");

// 2. Modificăm sursa
numere.Add(4);

// 3. Iterăm (AICI se execută interogarea)
Console.WriteLine("Acum rulez query:");
foreach(var n in query) Console.Write(n + " "); 

// Output:
// Query definit, dar încă nu a rulat
// Acum rulez query:
// Verific 1
// 1 Verific 2
// 2 Verific 3
// 3 Verific 4
// 4 

// OBSERVAȚIE: L-a văzut și pe 4, deși l-am adăugat DUPĂ definirea query-ului!
```

###  Immediate Execution
```csharp
var numere = new List<int> { 1, 2, 3 };

// ToList() forțează execuția ACUM
var query = numere.Where(n => n > 0).ToList();

numere.Add(4);

foreach (var n in query) Console.Write(n + " ");
// Output: 1 2 3 (fără 4! rezultatele au fost "înghețate")
```

###   Query executat de mai multe ori
```csharp
int count = 0;
var query = numere.Where(n => {
    count++;
    return n > 0;
});

foreach (var n in query) { } // count devine 3
foreach (var n in query) { } // count devine 6 !!!

// Query-ul se re-execută LA FIECARE iterare!
// Dacă vrei să ruleze o singură dată, folosește .ToList()
```

---

# 💣 EXAM TRAPS & WEIRD BEHAVIOUR

## 1. Main cu M mare
```csharp
static void main() { }  // NU funcționează!
static void Main() { }  // Corect
```

## 2. Totul este Object
```csharp
int x = 42;
Console.WriteLine(x.GetType());    // System.Int32
Console.WriteLine(x.ToString());   // "42"

// Merge și cu literale:
Console.WriteLine(5.ToString());   // "5"
Console.WriteLine(3.14.GetType()); // System.Double
```

## 3. Arrays - poziția parantezelor
```csharp
int[] a;    // C# - corect
// int a[]; // Java style - EROARE în C#
```

## 4. Generics Reale vs Java Type Erasure
```csharp
// C# - tipurile sunt păstrate la runtime
List<int> intList = new List<int>();
List<string> stringList = new List<string>();
Console.WriteLine(intList.GetType() == stringList.GetType()); // FALSE

// În Java ar fi TRUE (type erasure șterge tipurile la compilare)
```

## 5. Structuri sunt copiate
```csharp
struct Punct {
    public int X, Y;
}

Punct p1 = new Punct { X = 1, Y = 2 };
Punct p2 = p1;          // COPIE completă
p2.X = 999;
Console.WriteLine(p1.X); // 1 (nemodificat)
```

## 6. `var` nu este dinamic
```csharp
var x = 10;        // x e de tip int (determinat la compilare)
// x = "text";     // EROARE! int nu poate primi string

dynamic d = 10;    // d poate fi orice (determinat la runtime)
d = "text";        // OK
d = new List<int>(); // OK
```

## 7. Excepții în blocuri `finally`
```csharp
try {
    throw new Exception("Prima excepție");
} finally {
    throw new Exception("Excepție din finally"); // Prima se pierde!
}
// Doar "Excepție din finally" ajunge în exterior
```

## 8. `struct` mutabil în colecții
```csharp
struct Punct { public int X; }
List<Punct> lista = new List<Punct> { new Punct { X = 5 } };

// lista[0].X = 10; // EROARE! Nu poți modifica
// De ce? lista[0] returnează o COPIE, modificarea ar fi inutilă

// Soluție:
var p = lista[0];
p.X = 10;
lista[0] = p;
```

## 9. Static Generics - câmpuri separate
```csharp
class Test<T> { public static int Count; }

Test<int>.Count = 1;
Test<string>.Count = 2;

Console.WriteLine(Test<int>.Count);    // 1 (nu 2!)
Console.WriteLine(Test<string>.Count); // 2
// Sunt CLASE DIFERITE în memorie!
```

## 10. Constructorii Statici
```csharp
class Problema {
    static Problema() {
        throw new Exception("Constructor static crapat");
    }
}

// Prima încercare:
try { var x = new Problema(); }
catch { Console.WriteLine("Eroare!"); }

// A doua încercare:
try { var y = new Problema(); }
catch { Console.WriteLine("Eroare din nou!"); }

// Ambele aruncă TypeInitializationException!
// Constructorul static rulează O SINGURĂ DATĂ și clasa rămâne "moartă"
```

## 11. String Comparison
```csharp
string a = "test";
string b = "test";
string c = new string(new char[]{'t','e','s','t'});
string d = "te" + "st";

Console.WriteLine(a == b);            // TRUE (intern pool)
Console.WriteLine(a == c);            // TRUE (operator == compară conținut)
Console.WriteLine((object)a == (object)c); // FALSE (referințe diferite)
Console.WriteLine(a == d);            // TRUE (compilatorul optimizează)
Console.WriteLine(ReferenceEquals(a, d)); // TRUE (compilatorul face intern)
```

## 12. `throw` vs `throw ex`
```csharp
void Metoda() {
    try {
        FunctieCareCreapa();
    }
    catch (Exception ex) {
        // throw ex;  // ❌ Rău - Stack Trace arată AICI
        throw;        // ✅ Bun - Stack Trace arată ORIGINEA
    }
}
```

## 13. `checked` vs `unchecked`
```csharp
int a = int.MaxValue; // 2147483647

unchecked {
    a++;  // Devine -2147483648 (overflow silențios)
}

checked {
    int b = int.MaxValue;
    b++; // Aruncă OverflowException!
}

// Unchecked e default pentru performanță
```

## 14. `catch` fără parametru
```csharp
try { }
catch (ArgumentException) { }      // Prinde doar ArgumentException
catch (Exception e) { }            // Prinde orice Exception, cu variabilă
catch (Exception) { }              // Prinde orice Exception, fără variabilă
catch { }                          // Prinde ORICE (inclusiv non-Exception)
```

## 15. Null Conditional și Null Coalescing
```csharp
string s = null;

// Null conditional (?.) - scurtcircuitează dacă e null
int? lungime = s?.Length;  // null (nu crash)

// Null coalescing (??) - valoare default
string text = s ?? "default";  // "default"

// Combinat:
int len = s?.Length ?? 0;  // 0

// Null coalescing assignment (??=) - C# 8+
s ??= "nou";  // s devine "nou" doar dacă era null
```

## 16. Pattern Matching
```csharp
object obj = 42;

// Type pattern
if (obj is int numar) {
    Console.WriteLine($"E int: {numar}");
}

// Switch expression (C# 8+)
string descriere = obj switch {
    int n when n > 0 => "Număr pozitiv",
    int n when n < 0 => "Număr negativ",
    int => "Zero",
    string s => $"Text: {s}",
    null => "Null",
    _ => "Altceva"
};
```

## 17. Nullable Value Types
```csharp
int? x = null;  // Nullable<int>
int? y = 5;

// Verificare
if (x.HasValue) { int val = x.Value; }
if (x == null) { }

// GetValueOrDefault
int z = x.GetValueOrDefault();     // 0
int w = x.GetValueOrDefault(10);   // 10

// Ciudățenie cu comparații
int? a = null;
int? b = null;
Console.WriteLine(a == b);   // TRUE (ambele null)
Console.WriteLine(a >= b);   // FALSE (!!! comparații cu null sunt false)
Console.WriteLine(a > 5);    // FALSE
Console.WriteLine(a < 5);    // FALSE
Console.WriteLine(a == 5);   // FALSE
```

## 18. String Interpolation vs Format
```csharp
string nume = "Ioan";
int varsta = 20;

// Interpolation (preferată)
string s1 = $"Nume: {nume}, Vârsta: {varsta}";

// Cu formatare
string s2 = $"Preț: {123.456:F2}";  // "Preț: 123.46"
string s3 = $"Data: {DateTime.Now:dd/MM/yyyy}";

// Verbatim + interpolation
string path = $@"C:\Users\{nume}\Documents";
```

## 19. Tuple-uri
```csharp
// Tuple simplu
(int, string) tuplu = (1, "unu");
Console.WriteLine(tuplu.Item1);  // 1

// Tuple cu nume
(int Numar, string Text) named = (1, "unu");
Console.WriteLine(named.Numar);  // 1

// Deconstrucție
var (x, y) = named;
(int a, string b) = named;

// Returnat din metodă
(int min, int max) GasesteMinMax(int[] arr) {
    return (arr.Min(), arr.Max());
}

var (minim, maxim) = GasesteMinMax(new[] { 3, 1, 4, 1, 5 });
```

## 20. Discard (_)
```csharp
// Ignoră valori nedorite
var (_, max) = GasesteMinMax(arr);  // nu mă interesează minimul

// În pattern matching
object obj = 42;
if (obj is int _) {
    Console.WriteLine("E int, dar nu am nevoie de valoare");
}

// La out parameters
if (int.TryParse("123", out _)) {
    Console.WriteLine("E valid, dar nu vreau valoarea");
}
```
