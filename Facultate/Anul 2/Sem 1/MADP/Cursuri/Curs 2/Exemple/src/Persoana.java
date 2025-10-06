//o persoana comparabila
public class Persoana implements Comparable<Persoana> {
    protected String nume;
    protected int varsta;
    @Override
    public int compareTo(Persoana o){
        return this.nume.compareTo(o.nume);
    }
    public Persoana(String nume,int varsta){
        this.nume=nume;
        this.varsta=varsta;
    }
}
//putem face si asa:
class Persoana2{
    protected String nume;
    protected int varsta;
}



