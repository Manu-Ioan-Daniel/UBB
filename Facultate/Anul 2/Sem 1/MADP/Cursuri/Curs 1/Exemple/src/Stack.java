public class Stack<E> {
    private List<E> items = new ArrayList<>();
    //sau putem face
    //private E[] items2=(E[]) new Object[100];
    //sau putem face
    //private Object[] items3=new Object[100] si la peek trebuie sa facem return (E)items3[vf-1]
    private int vf=0;
    public void push(E elem){
        items.add(elem);
        vf++;
    }
    public E peek(){
        return items.getLast();
    }
    //nu are legatura cu stiva,doar dau showcase la metoda generica
    public static <T> int countNullValues(T[] array){
        int count=0;
        for(T el:array)
            if(el==null)
                ++count;
        return count;
    }
}
//instantiere

void main() {
    Stack<String> stiva=new Stack<String>();
    stiva.push("Ana");
    System.out.println(stiva.peek());
    //Stack<int> stiva2=new Stack<int>(); - linia da eroare,trebuie folosite clasele invelitoare
    Stack<Integer> stiva2=new Stack<Integer>();
    stiva2.push(5);
    System.out.println(stiva2.peek());
    System.out.println(Stack.countNullValues(new String[]{"a",null,"b"}));

}
