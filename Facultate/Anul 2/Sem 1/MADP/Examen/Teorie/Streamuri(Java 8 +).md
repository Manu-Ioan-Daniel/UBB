

**Stream** = secvență de date procesată declarativ, folosește lambda expressions și interfețe funcționale.

## 1️⃣ Operații Intermediare vs Terminale

|Tip|Caracteristici|Exemple|
|---|---|---|
|Intermediare|Returnează tot un Stream → lazy evaluation|filter, map, sorted, distinct, limit, skip|
|Terminale|Încheie stream-ul → produc rezultat|forEach, collect, reduce, count, anyMatch, allMatch, noneMatch|

---

## 2️⃣ Operații Intermediare

### 2.1 filter

```java
List<Integer> nums = Arrays.asList(1,2,3,4,5);
nums.stream().filter(x -> x % 2 == 0).forEach(System.out::println); // 2 4
```

### 2.2 map

```java
List<String> words = Arrays.asList("a","bb","ccc");
words.stream().map(String::length).forEach(System.out::println); // 1 2 3
```

### 2.3 sorted

```java
List<Integer> nums = Arrays.asList(5,3,1,4,2);
nums.stream().sorted().forEach(System.out::println); // 1 2 3 4 5
```

### 2.4 distinct

```java
List<Integer> nums = Arrays.asList(1,2,2,3,3,3);
nums.stream().distinct().forEach(System.out::println); // 1 2 3
```

### 2.5 limit și skip

```java
List<Integer> nums = Arrays.asList(1,2,3,4,5);
nums.stream().skip(2).limit(2).forEach(System.out::println); // 3 4
```

### 2.6 peek (debugging)

```java
List<Integer> nums = Arrays.asList(1,2,3);
nums.stream().peek(x -> System.out.println("before map: " + x))
    .map(x -> x*2)
    .peek(x -> System.out.println("after map: " + x))
    .forEach(x -> {});
```

---

## 3️⃣ Operații Terminale

### 3.1 forEach

```java
List<String> list = Arrays.asList("a","b");
list.stream().forEach(System.out::println); // a b
```

### 3.2 collect

```java
List<String> upper = list.stream().map(String::toUpperCase).collect(Collectors.toList());
System.out.println(upper); // [A, B]
```

### 3.3 groupingBy

```java
List<String> words = Arrays.asList("a","aa","b","bb");
Map<Integer, List<String>> byLength = words.stream().collect(Collectors.groupingBy(String::length));
System.out.println(byLength); // {1=[a, b], 2=[aa, bb]}
```

### 3.4 reduce

```java
List<Integer> nums = Arrays.asList(1,2,3,4);
int sum = nums.stream().reduce(0, Integer::sum);
System.out.println(sum); // 10
```

### 3.5 anyMatch, allMatch, noneMatch

```java
List<Integer> nums = Arrays.asList(2,4,6);
System.out.println(nums.stream().allMatch(x -> x % 2 == 0)); // true
System.out.println(nums.stream().anyMatch(x -> x > 5));       // true
System.out.println(nums.stream().noneMatch(x -> x < 0));      // true
```

### 3.6 count

```java
long cnt = nums.stream().filter(x -> x % 2 == 0).count();
System.out.println(cnt); // 2
```

### 3.7 min, max

```java
Optional<Integer> min = nums.stream().min(Integer::compare);
Optional<Integer> max = nums.stream().max(Integer::compare);
```

### 3.8 flatMap

```java
List<List<String>> list = Arrays.asList(Arrays.asList("a","b"), Arrays.asList("c"));
list.stream().flatMap(Collection::stream).forEach(System.out::println); // a b c
```

---

## 4️⃣ Exemple ciudate / capcane

### 1. Stream consumat o singură dată

```java
Stream<Integer> s = Stream.of(1,2,3);
s.forEach(System.out::println);
s.forEach(System.out::println); // IllegalStateException
```

### 2. Lazy evaluation

```java
List<Integer> nums = Arrays.asList(1,2,3);
Stream<Integer> s = nums.stream().filter(x -> { System.out.println("filter " + x); return x > 1; });
System.out.println("Nothing printed yet");
s.forEach(System.out::println); // filtrele rulează acum
```

### 3. reduce fără identitate

```java
List<Integer> empty = Arrays.asList();
Optional<Integer> sum = empty.stream().reduce(Integer::sum);
System.out.println(sum.isPresent()); // false
```

### 4. groupingBy + downstream collector

```java
List<String> words = Arrays.asList("a","aa","b","bb");
Map<Integer, Long> countByLength = words.stream().collect(Collectors.groupingBy(String::length, Collectors.counting()));
System.out.println(countByLength); // {1=2, 2=2}
```

### 5. map cu null value

```java
List<String> list = Arrays.asList("a", null, "b");
list.stream().map(String::toUpperCase).forEach(System.out::println); // NullPointerException la null
```

### 6. sorted + comparator cu overflow

```java
List<Integer> nums = Arrays.asList(Integer.MAX_VALUE, -1);
nums.stream().sorted((a,b) -> a - b).forEach(System.out::println); // overflow
```

### 7. peek NU modifică elementele

```java
List<Integer> nums = Arrays.asList(1,2,3);
nums.stream().peek(x -> x*2).forEach(System.out::println); // afișează 1 2 3, nu dublate
```

---

## 5️⃣ Alte operații utile
    
- forEachOrdered()
    
- findFirst(), findAny()

### Collectors

|**Metodă**|**Rezultat**|**Exemplu de cod**|
|---|---|---|
|**`toList()`**|O listă (`ArrayList`)|`.collect(Collectors.toList())`|
|**`toSet()`**|Un set (elimină duplicatele)|`.collect(Collectors.toSet())`|
|**`toCollection()`**|O colecție specifică|`.collect(Collectors.toCollection(LinkedList::new))`|
|**`toMap()`**|Un `Map<K, V>`|`.collect(Collectors.toMap(User::getId, u -> u))`|

|**Metodă**|**Ce face**|**Rezultat**|
|---|---|---|
|**`groupingBy()`**|Grupează elementele după un criteriu.|`Map<Cheie, List<T>>`|
|**`partitioningBy()`**|Împarte lista în două (True/False) bazat pe un `Predicate`.|`Map<Boolean, List<T>>`|
|**`counting()`**|Numără elementele (se folosește des cu `groupingBy`).|`Long`|

|**Metodă**|**Ce face**|**Exemplu**|
|---|---|---|
|**`joining()`**|Unește String-uri (opțional cu separator).|`.collect(Collectors.joining(", "))`|
|**`summingInt()`**|Calculează suma valorilor `int`.|`.collect(Collectors.summingInt(User::getAge))`|
|**`averagingDouble()`**|Calculează media aritmetică.|`.collect(Collectors.averagingDouble(p -> p.price))`|
|**`summarizingInt()`**|Îți dă dintr-o dată: min, max, sumă, medie și count.|`.collect(Collectors.summarizingInt(x -> x))`|

