import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main{
    public static <E> void printArie(List<E> l, Arie<E> f){
        l.forEach(figura->System.out.println(f.calculeaza(figura)));
    }
    public static void main(String[] args){
        //ex 1
        Arie<Cerc> arieCerc=c->c.getRaza()*c.getRaza()*Math.PI;
        Arie<Patrat> ariePatrat=p->p.getLungime()*p.getLungime();
        List<Cerc> listaCercuri= Arrays.asList(new Cerc(1),new Cerc(2),new Cerc(3));
        List<Patrat> listaPatrat=Arrays.asList(new Patrat(1),new Patrat(2),new Patrat(3));
        printArie(listaCercuri,arieCerc);
        printArie(listaPatrat,ariePatrat);
        Predicate<Cerc> p=c->c.getRaza()>=3;
        afiseazaEntitati(listaCercuri,p);
        //ex 3
        Function<String,Integer> converterLambda=x->Integer.valueOf(x);
        Function<String,Integer> converterReference= Integer::valueOf;
        //ex 4
        Supplier<Integer> supplierLambda=()->32;
        Supplier<String> supplierReference=String::new;
        System.out.println(supplierReference.get().isEmpty());
    }
    //ex 2
    public static <E> void afiseazaEntitati(List<E> l, Predicate<E> p){
        //l.forEach(entitate->{if(p.test(entitate)) System.out.println(entitate);}); sau:
        l.stream()
                .filter(p)
                .forEach(System.out::println);

    }
    //ex 6
    public static <E> List<E> filterGeneric(List<E> lista, Predicate<E> p){
        return lista.stream()
                .filter(p)
                .toList();
    }
    //ex 7
    public static <E> List<E> filterGeneric2(List<E> lista, Predicate<E> p, Comparator<E> c){
        return lista.stream()
                .filter(p)
                .sorted(c)
                .toList();
    }
}