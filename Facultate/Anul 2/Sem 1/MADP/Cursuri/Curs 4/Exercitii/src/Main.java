void main(){
    List<String> lista = new ArrayList<>(
            Arrays.asList("Ana", "Anam", "Mari", "Anamaria", "Mar", "Anaa")
    );
    String cuvant="Anamaria";
    //stergem toate stringurile care sunt prefix pt cuvant
    lista.removeIf(cuvant::startsWith); // sau lista.removeIf(s->cuvant.startsWith(s));
    System.out.println(lista);
    List<String> lista2 = new ArrayList<>(
            Arrays.asList("Ana", "Anam", "Mari", "Anamaria", "Mar", "Anaa")
    );
    lista2.removeIf(s->s.startsWith("a"));
    System.out.println(lista2);
    List<String> lista3 = new ArrayList<>(
            Arrays.asList("Ana", "Anam", "Mari", "Anamaria","", "Mar", "Anaa","")
    );
    lista3.removeIf(s -> Objects.equals(s, ""));//sau lista3.removeIf(""::equals)
    System.out.println(lista3);
    //studenti skip ca e prea useless

    Function<Double,Double> lg= Math::log10;
    Function<Double,Double> compose=lg.compose(x->x*x);
    System.out.println(compose.apply(10d));

    List<Student> studenti = new ArrayList<>();
    studenti.add(new Student(3, "Ana", 9));
    studenti.add(new Student(1, "Pop", 8));
    studenti.add(new Student(2, "Maria", 10));
    // Sortare după medie
    studenti.sort(StudentHelper::compareByAverage);
    // Sortare după nume
    studenti.sort(StudentHelper::compareByName);
    // Sortare după id
    studenti.sort(StudentHelper::compareById);
    // Sortare după medie
    studenti.sort((s1,s2)->StudentHelper.compareByAverage(s1,s2));
    //etc

    List<String> stringCollection = Arrays.asList("ddd2", "aaa2","bbb1","aaa1","bbb3", "ccc1", "bbb2","ddd1","aaa3","aaa4");
    List<String>result=stringCollection
            .stream()
            .filter(x->x.startsWith("a"))
            .map(String::toUpperCase)
            //.forEach(System.out::println) sau:
            .toList();
    System.out.println(result);



}
