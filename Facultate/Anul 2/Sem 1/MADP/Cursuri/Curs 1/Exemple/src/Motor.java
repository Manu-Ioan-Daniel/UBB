//putem folosi [abstract][final][public] class NumeClasa
/*daca folosim public atunci:
 -clasa trebuie declarata intr un fisier care are numele clasei (public class Motor in fisier Motor.java)
 -clasa poate fi accesata de oriunde
 */
public class Motor {
    public String marca;//public,deci poate fi accesata de oricine
    protected int engineCapacity;//protected,deci accesibil doar clasei si subclaselor
    private String serieSasiu;//private,poate fi accesat doar dinauntrul clasei
    int horsePower;//implicit este accesibil doar la nivelul pachetului
    //bloc de initializare,sau putem initializa direct(gen int horsePower=0;)
    {
        horsePower=0;
        engineCapacity=0;
    }
    //constructor
    public Motor(int horsePower,int engineCapacity,String marca,String serieSasiu){
        this.horsePower=horsePower;
        this.engineCapacity=engineCapacity;
        this.marca=marca;
        this.serieSasiu=serieSasiu;
    }
    //supraincarcarea constructorului
    public Motor(int horsePower){
        this.horsePower=horsePower;
        this.marca="CFMoto";
        this.serieSasiu="LCPED19203";
        this.engineCapacity=300;
    }
    //apelarea constructorului din clasa
    public Motor(int horsePower,int engineCapacity){
        this(horsePower,engineCapacity,"CFMoto","L");
    }
    //metode cu nr variabil de argumente
    public void setBolts(int ... bolts){
        for(int bolt:bolts){
            //chestii
        }
    }
    //functie statica
    public static void EngineSound(){
        System.out.println("Vroom vroom!");
    }

    public void tuneaza(int horsePower){
        this.horsePower+=horsePower;
    }
    //suprascrierea metodei tuneaza
    public void tuneaza(Motor m){
        this.horsePower+=m.horsePower;
    }
}
/*daca folosim [abstract]:
 -clasa nu poate fi instantiata,dar poate fi folosita ca o baza pentru alte clase
 */
abstract class Animal{
    abstract void vorbeste();
}
//mostenire

class Caine extends Animal{
    @Override
    void vorbeste(){
        System.out.println("Ham ham!");
    }
}
/*daca folosim [final]:
    -clasa nu mai poate fi mostenita
 */
final class Pisica extends Animal{
    @Override
    void vorbeste(){
        System.out.println("Miau miau!");
    }
}
