public class Persoana {
    protected byte varsta;
    protected String nume;
    public Persoana(String nume,byte varsta){
        this.nume=nume;
        this.varsta=varsta;
    }
    public Persoana(){
        this("",(byte)0);
    }
    @Override
    public String toString(){
        return nume+" "+varsta;
    }
}

