

### Subiectul I (2p)
Definiți mecanismele de **suprascriere (overriding)** și **supraîncărcare (overloading)** și ilustrați cele două concepte prin exemple de cod Java.

Overriding - un copil ce mosteneste o clasa /interfata poate suprascrie metodele publice si protected ale acestora.
Overloading - cand scriem o functie cu acelasi nume,dar cu signatura diferita

### Exemple

```java
public class A{
	public void afiseaza(){
		System.out.println("A");
	}
	
	public void afiseaza(String string){
		System.out.println("Sugi " + string);	
	}
}
public class B extends A{
	@Override
	public void afiseaza(){
		System.out.println("B");
	}
}
```
### Subiectul al II-lea (1p)
Explicați ce se afișează în urma execuției codului și de ce.

```java
public class ExceptionExercise {
    static int test() {
        try {
            System.out.print("A");
            return 1;
        } catch (RuntimeException e) {
            System.out.print("B");
            return 2;
        } finally {
            System.out.print("C");
        }
    }

    public static void main(String[] args) {
        int result = test();
        System.out.print(result);
    }
}
```

Se afiseaza AC1

---

### Subiectul al III-lea (2p)

Completați linia lipsă folosind operații pe **stream-uri** astfel încât programul să afișeze mesajul: **ANA**. _Notă: Dacă nu se folosesc stream-uri se acordă 0 puncte._


``` Java
import java.util.*;
import java.util.stream.*;

public class Test {
    public static void main(String[] args) {
        List<String> names = List.of("ana", "bob", "ana");
        
        String result = names.stream()
	        .distinct()
	        .filter(s->s.equals("ana"))
	        .map(String::toUpperCase)
	        .collect(Collectors.joining())
        System.out.println(result);
    }
}
```

---

### Subiectul al IV-lea (3p)

Se dă clasa `Vehicle` care are următoarele atribute: `VIN` (Vehicle Identification Number) de tip **Integer**, `price` de tip **Double** și `brand` de tip **String**. Clasa conține: un getter pentru fiecare atribut și o metodă `toString()` care afișează detaliile unui vehicul.

Implementați în limbajul Java următoarele cerințe:

1. Definiți clasa `Vehicle` cu atributele și metodele menționate. (1p)
    
2. Definiți o funcție `addVehicle(List<Vehicle> vehicles, Vehicle v, int position)` care adaugă vehiculul `v` într-o listă de vehicule `vehicles` pe poziția `position`. Funcția va returna lista actualizată. (1p)
    
3. Definiți o funcție de tip `void` care șterge dintr-o listă de vehicule acele elemente care fac parte din brandul „Porsche”. (1p)

```java
class Vehicle{

	private final Integer VIN;
	private final Double price;
	private final String brand;
	
	public Integer getVIN(){
		return this.VIN;
	}
	public Double getPrice(){
		return this.price;
	}
	public String getBrand(){
		return this.brand;
	}
	
	public Vehicle(Integer VIN, Double price, String brand) {
	    this.VIN = VIN;
	    this.price = price;
	    this.brand = brand;
	}
	
	@Override
	public String toString(){
		return "Vehicul cu: " + VIN + price + brand;
	}
	
	public List<Vehicle> addVehicle(List<Vehicle> vehicles, Vehicle v, int position){
		vehicles.add(position,v);
		return vehicles;	
	}
	public void sterge(List<Vehicle> vehicles){
		List<Vehicle> copyList = new ArrayList<>();
		for(Vehicle v:vehicles){
			copyList.add(v);
		}
		for(Vehicle v:copyList){
			if(v.getBrand().equals("Porsche")){
				vehicles.remove(v);
			}
		}
	}
}
```
    

---

### Subiectul al V-lea (1p) – C#

Analizați codul de mai jos:

``` C#
using System;

class A
{
    protected int x = 1;
    public A()
    {
        Print();
        Console.Write(x);
    }
    public virtual void Print()
    {
        Console.Write("A");
    }
}

class B : A
{
    protected new int x = 2;
    public B()
    {
        Print();
        Console.Write(x);
    }
    public override void Print()
    {
        Console.Write("B");
    }
}

class Program
{
    static void Main()
    {
        new B();
    }
}
```

**Ce se afișează și de ce?** 
A) A1B2

B) B1B2

C) B2B2

D) A1A2

B1B2