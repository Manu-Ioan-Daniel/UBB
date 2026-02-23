package utils;

import java.util.Objects;

public class Tuple<E1, E2> {
    private final E1 e1;
    private final E2 e2;

    public Tuple(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public E1 getFirst() {
        return e1;
    }

    public E2 getSecond() {
        return e2;
    }
    @Override
    public String toString() {
        return e1 + "," + e2;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Tuple<?,?> t)
            return this.e1.equals(t.getFirst()) && this.e2.equals(t.getSecond());
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(e1,e2);
    }
}
