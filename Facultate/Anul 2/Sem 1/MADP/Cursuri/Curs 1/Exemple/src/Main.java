
void main() {
    //single line comment
    /*
    Multiple lines comment
     */
    byte a;//declarare
    int valoarea =100;//declarare + initializare
    final double PI=3.14;//constanta
    String bautura="Cola";
    var str = "Java 10"; // infers String
    var list = new ArrayList<String>(); // infers ArrayList<String>
    var stream = list.stream(); // infers Stream<String>
    //for(tip variabila:colectie) ...
    for(String s:list){
        System.out.println(s);
    }
    //--------------------
    //Expresia de la switch poate fi byte,short,int,char(sau clase invelitoare),enum,string
    switch(str){
        case "mama":
            System.out.println(str);
            break;
        case "tata":
            System.out.println("ok");
            break;
        default:
            System.out.println("Not found");
    }
    //-------------------
    //Declararea unui array se poate face in 2 moduri:
    float[] vector; //sau
    float vector2[];//da warning
    //Crearea unui array
    vector=new float[10];//se aloca spatiul in memorie,indexarea de la 0 la 9
    vector2=vector;//vector2 si vector refera acelasi tablou
    System.out.println(vector.length);//putem accesa lungimea,afiseaza 10
    //Creare si initializare
    int[] dx={1,0,-1};
    int[][] matrice;
    matrice=new int[5][5];
    matrice[0][0]=2;
    System.out.println(matrice[2][2]);//afiseaza 0

    int[][] wtf=new int[3][0];
    for(int i = 0;i<3;i++){
        wtf[i]=new int[i+1];
    }
    System.out.println(wtf[2].length);//afiseaza 3
    //Creeam un motor
    Motor motorSlab=new Motor(2);
    Motor motorOarecare;
    motorOarecare=motorSlab;//motor oarecare si motor slab refera acelasi obiect in memorie
    //clase invelitoare
    Integer intobj1=new Integer(34);
    Integer intobj2=Integer.valueOf("35");
    Integer intobj3=Integer.valueOf("1001",2);
    System.out.println(intobj3);//afiseaza 9
    Integer intobj5=35;//autoboxing
    int intPrimitiv=intobj1;//unboxing
    Integer intobj6=intPrimitiv;//autoboxing

    //apelarea unei functii statice dintr o clasa
    Motor.EngineSound();
    AB.generateAB();

    //Legare dinamica

    Persoana stud=new Student("Andu",(byte)3,34);
    System.out.println(stud.toString());//afiseaza cu metoda de la student,nu cea de la persoana
    Student stud2=(Student)stud;//downcasting
    System.out.println(stud2.toString());//merge tati
    stud=(Persoana)stud2;//upcasting

}

