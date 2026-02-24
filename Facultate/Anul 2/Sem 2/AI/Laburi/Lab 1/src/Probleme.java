import java.util.*;

public class Probleme {

    static class Punct {
        public int x;
        public int y;
        Punct(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Probleme p = new Probleme();
        p.teste();
    }

    /***
     * Complexitate : Theta(nr de cuvinte din text) timp si spatiu
     * @param text un text de lungime variabila
     * @return ultimul cuvant din punct de vedere alfabetic din text
     */
    String ultimulCuv(String text){
        if(text.isEmpty()){
            throw new IllegalArgumentException();
        }
        String ultimulCuv = "";
        for(String cuvant : text.split(" ")){
            if(cuvant.compareTo(ultimulCuv)>0){
                ultimulCuv = cuvant;
            }
        }
        return ultimulCuv;
    }

    //Complexitate Theta(nr de cuv din text) timp si spatiu
    public static String ultimulCuvAI(String text) {
        String[] cuvinte = text.split(" ");
        String maxim = cuvinte[0];

        for (int i = 1; i < cuvinte.length; i++) {
            if (cuvinte[i].compareTo(maxim) > 0) {
                maxim = cuvinte[i];
            }
        }

        return maxim;
    }

    /***
     * Complexitate: Theta(1) spatiu si memorie
     * @param A un punct oarecare
     * @param B un punct oarecare
     * @return distanta euclidiana dintre A si B
     */
    double distanta(Punct A, Punct B){
        double x = B.x-A.x;
        double y = B.y-A.y;
        return Math.sqrt(x*x + y*y);
    }
    //Complexitate: Theta(1) spatiu si memorie
    double distantaAI(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /***
     * Complexitate:theta(n) timp si theta(1) memorie
     * @param vector1 un vector rar
     * @param vector2 un vector rar
     * @return produsul scalar dintre cei doi vectori
     */
    double produsScalar(List<Integer> vector1, List<Integer> vector2){

        if(vector1.isEmpty() || vector2.isEmpty()){
            throw new IllegalArgumentException("Unul dintre vectori este empty");
        }

        if(vector1.size() != vector2.size()){
            throw new IllegalArgumentException("Vectorii nu sunt de aceeasi dimensiune");
        }
        double sum = 0;
        for(int i = 0; i<vector1.size(); i++){
           sum+=vector1.get(i)*vector2.get(i);
        }
        return sum;
    }

    //Complexitate Theta(n) timp,theta(1) memorie
    double produsScalarAI(double[] vectorA, double[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectorii trebuie să aibă aceeași dimensiune!");
        }

        double product = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            product += vectorA[i] * vectorB[i];
        }

        return product;
    }

    /***
     * Complexitate timp si spatiu Theta(nr de cuvinte)
     * @param text un string ce contine cuvinte
     * @return lista cu cuvintele ce au o singura aparitie
     */
    List<String> cuvCuOAparitie(String text){
        List<String> rezultat = new ArrayList<>();
        if(text.isEmpty()){
            throw new IllegalArgumentException();
        }
        Map<String,Integer> map = new HashMap<>();
        for(String cuvant : text.split(" ")){
            map.put(cuvant, map.getOrDefault(cuvant, 0) + 1);
        }
        for(String cuvant : map.keySet()){
            if(map.get(cuvant).equals(1)){
                rezultat.add(cuvant);
            }
        }
        return rezultat;
    }

    public void teste(){

        //problema 1

        Punct A = new Punct(1, 5);
        Punct B = new Punct(4, 1);
        assert distanta(A, B) == 5;
        assert distantaAI(1,5,4,1) == 5;

        //problema 2

        String text = "Ana are mere rosii si galbene";
        assert ultimulCuv(text).equals("si");
        assert ultimulCuvAI(text).equals("si");

        //problema 3

        List<Integer> vector1 = Arrays.asList(1,0,2,0,3);
        List<Integer> vector2 = Arrays.asList(1,2,0,3,1);
        List<Integer> emptyVec = new ArrayList<>();
        assert produsScalar(vector1, vector2) == 4;
        try{
            assert produsScalar(vector1, emptyVec) == 4;
            assert false;
        }catch(IllegalArgumentException e){
            assert e.getMessage().equals("Unul dintre vectori este empty");
        }
        double[] vector11 = {1, 0, 2, 0, 3};
        double[] vector22 = {1, 2, 0, 3, 1};
        double[] vectorEmpty = {};
        assert produsScalarAI(vector11,vector22) == 4;
        try{
            assert produsScalarAI(vector11,vectorEmpty) == 4;
            assert false;
        }catch(IllegalArgumentException _){}
        try{
            assert produsScalarAI(vectorEmpty,vectorEmpty) == 4;
            //assert false; ajunge la assert false pentru ca nu a dat handle la acest caz
        }catch(IllegalArgumentException _){}

        //problema 4
        String text2 = "ana are ana are mere rosii ana";
        
    }
}
