public class Student extends Persoana{
    private int anStudiu;
    private static final int COD_UNIVERSITATE=15;
    public Student(String nume,byte varsta,int anStudiu){
        super(nume,varsta);//apelam constructorul de la Persoana cu nume si varsta
        this.anStudiu=anStudiu;
    }
    @Override
    public String toString(){
        return super.toString()+" "+anStudiu;
    }
}
