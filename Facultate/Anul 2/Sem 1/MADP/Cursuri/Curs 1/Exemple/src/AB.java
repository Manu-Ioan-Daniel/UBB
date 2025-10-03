class A{
    int i;
    public A(int i){
        System.out.print("A("+i+")");
        this.i=i;
    }

}
class B extends A{
    float i=3;
    public B(int i){
        super(i);
        System.out.print("B()");
    }
}

public class AB extends B {
    public AB(int i){
        super(i);
        System.out.println("AB()");
    }
    public static void generateAB(){
        AB[] x=new AB[]{new AB(3),new AB(4)};
        for(AB obiect:x){
            obiect.test();
        }
    }
    public void test(){
        System.out.println("AB");
    }
}
