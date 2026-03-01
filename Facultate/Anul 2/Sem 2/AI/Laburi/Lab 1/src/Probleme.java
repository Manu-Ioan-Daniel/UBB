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
            return rezultat;
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
    //Complexitate Theta(nr de cuvinte) spatiu si memorie
    List<String> cuvCuOAparitieAI(String text) {
        if (text == null || text.isEmpty()) {
            return Collections.emptyList();
        }

        String[] cuvinte = text.toLowerCase().split("\\s+");
        Map<String, Integer> frecventa = new LinkedHashMap<>();

        for (String cuvant : cuvinte) {
            frecventa.put(cuvant, frecventa.getOrDefault(cuvant, 0) + 1);
        }

        List<String> rezultat = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : frecventa.entrySet()) {
            if (entry.getValue() == 1) {
                rezultat.add(entry.getKey());
            }
        }

        return rezultat;
    }

    /***
     * Complexitate timp : Theta(n), complexitate spatiu : Theta(1)
     * valoarea care se repeta din lista de numere
     * @param numere o lista de numere care fac parte din multimea {1,2,3,...,n-1} si ce contine un singur numar care se repeta
     * @return numarul care se repeta de 2 ori
     */
    int valoareRepetata(List<Integer> numere){
        if(numere.isEmpty()){
            throw new IllegalArgumentException();
        }
        int n = numere.size();
        int sumaTeoretica = n*(n-1)/2;
        int suma = 0;
        for(int i = 0;i<n;i++){
            suma += numere.get(i);
        }
        return suma - sumaTeoretica;
    }

    //Complexitate timp si spatiu: O(n)
    int valoareRepetataAI(int[] sir) {
        Set<Integer> vazute = new HashSet<>();
        for (int x : sir) {
            if (vazute.contains(x)) {
                return x; // am găsit duplicatul
            }
            vazute.add(x);
        }
        return -1; // dacă nu există duplicat
    }

    /***
     * Complexitate Theta(n) spatiu si timp
     * @param numere o lista de numere oarecare
     * @return elementul majoritar(cel care apare de mai mult de n/2 ori in sir)
     */
    int elementMajoritar(List<Integer> numere){
        if(numere.isEmpty()){
            throw new IllegalArgumentException();
        }
        Map<Integer,Integer> frecventa = new HashMap<>();
        for(int x : numere){
            frecventa.put(x, frecventa.getOrDefault(x,0)+1);
        }
        for(int x : frecventa.keySet()){
            if(frecventa.get(x)>numere.size()/2){
                return x;
            }
        }
        return -1;
    }

    //Complexitate Theta(n) timp si Theta(1) memorie
    Integer elementMajoritarAI(int[] nums) {
        int candidate = 0;
        int count = 0;

        // Prima trecere: găsim un candidat
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        // A doua trecere: verificăm dacă candidatul este cu adevărat majoritar
        count = 0;
        for (int num : nums) {
            if (num == candidate) {
                count++;
            }
        }

        if (count > nums.length / 2) {
            return candidate;
        } else {
            return null; // nu există element majoritar
        }
    }


    /***
     * Complexitate timp Theta(n * log k) si spatiu theta(k)
     * @param numere o lista de numere oarecare
     * @param k un numar natural nenul
     * @return al k-lea cel mai mare element din lista
     */
    int alKleaCelMaiMare(List<Integer> numere, int k) {
        if(numere.isEmpty() || k<=0){
            throw new IllegalArgumentException();
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : numere) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }

    //Complexitate O(n log n)
    int alKleaCelMaiMareAI(int[] arr, int k) {

        Arrays.sort(arr);
        return arr[arr.length - k];
    }


    /***
     * Complexitate: Theta(n log n) spatiu si memorie
     * @param n un numar natural nenul
     * @return toate numerele de la 1 la n in forma binara
     */
    List<String> numereBinar(int n){
        if(n <= 0){
            throw new IllegalArgumentException();
        }
        List<String> binare = new ArrayList<>();

        for(int i = 1; i <= n; i++){

            List<Integer> resturi = new ArrayList<>();
            int k = i;
            while(k>0){
                resturi.add(k%2);
                k/=2;
            }
            StringBuilder numarBinar = new StringBuilder();
            for(int j = resturi.size()-1; j>=0; j--){
                numarBinar.append(resturi.get(j));
            }
            binare.add(numarBinar.toString());
        }
        return binare;
    }

    //Complexitate Theta(n log n) timp si spatiu
    List<String> numereBinarAI(int n){

        List<String> binare = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            String binary = Integer.toBinaryString(i);
            binare.add(binary);
        }
        return binare;
    }

    /***
     * Complexitate : Theta(nr perechi * (r-p)(s-q)) timp si Theta(nr perechi) spatiu
     * @param matrix o matrice oarecare
     * @param perechi o lista de perechi {(p,q),(r,s)}
     * @return suma elementelor din submatricea formata de fiecare pereche
     */
    List<Integer> sumaElementeSubMatrice(int[][] matrix, List<Pereche> perechi){
        if(matrix.length == 0){
            throw new IllegalArgumentException();
        }
        List<Integer> res = new ArrayList<>();
        for(Pereche pereche : perechi){
            int suma = 0;
            for(int i = pereche.p; i <= pereche.r; i++){
                for(int j = pereche.q; j <= pereche.s; j++){
                    suma +=  matrix[i][j];
                }
            }
            res.add(suma);
        }
        return res;
    }
    //Complexitate Theta(nr perechi * (r-p)(s-q)) timp si Theta(nr perechi) spatiu
    List<Integer> sumaElementeSubMatriceAI(int[][] matrix, List<int[]> pairs) {

        List<Integer> result = new ArrayList<>();

        for (int[] pair : pairs) {
            int p = pair[0];
            int q = pair[1];
            int r = pair[2];
            int s = pair[3];

            int sum = 0;
            for (int i = Math.min(p, r); i <= Math.max(p, r); i++) {
                for (int j = Math.min(q, s); j <= Math.max(q, s); j++) {
                    sum += matrix[i][j];
                }
            }
            result.add(sum);
        }

        return result;
    }

    /***
     * Complexitate : Theta(n * log(m)) timp si Theta(1) spatiu
     * @param matrice o matrice binara cu fiecare linie sortata
     * @return indexul liniei cu cei mai multi de unu
     */
    int linieCuCeiMaiMultiUnu(int [][] matrice){
        int max1 = -1;
        int maxIndex = -1;
        for(int i = 0; i < matrice.length; i++){
            int st = 0;
            int dr = matrice[i].length-1;
            int index = matrice[i].length;
            while (st <= dr){
                int mid = st + (dr - st)/2;
                if (matrice[i][mid] == 1){
                    index = mid;
                    dr = mid-1;
                }else{
                    st = mid+1;
                }
            }
            int nr1 = matrice[i].length - index;
            if(nr1 > max1){
                max1 = nr1;
                maxIndex = index;
            }
        }
        return maxIndex;
    }
    //Complexitate O(n * m) timp si Theta(1) spatiu
    int liniaCuCeiMaiMultiUnuAI(int[][] matrice) {
        int maxIndex = -1;
        int maxUnu = -1;

        for (int i = 0; i < matrice.length; i++) {
            int[] linie = matrice[i];
            int countUnu = 0;

            // Ca liniile sunt sortate crescător, putem număra de la sfârșit
            for (int j = linie.length - 1; j >= 0; j--) {
                if (linie[j] == 1) {
                    countUnu++;
                } else {
                    break; // restul sunt 0
                }
            }

            if (countUnu > maxUnu) {
                maxUnu = countUnu;
                maxIndex = i;
            }
        }

        return maxIndex;
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
        }catch(IllegalArgumentException _){}
        double[] vector11 = {1, 0, 2, 0, 3};
        double[] vector22 = {1, 2, 0, 3, 1};
        double[] vectorEmpty = {};
        assert produsScalarAI(vector11,vector22) == 4;
        try{
            assert produsScalarAI(vector11,vectorEmpty) == 4;
            assert false;
        }catch(IllegalArgumentException _){}


        //problema 4
        String text2 = "ana are ana are mere rosii ana";
        assert cuvCuOAparitie(text2).containsAll(Arrays.asList("mere", "rosii"));
        assert cuvCuOAparitie("").isEmpty();
        assert cuvCuOAparitieAI(text2).containsAll(Arrays.asList("mere", "rosii"));
        assert cuvCuOAparitieAI("").isEmpty();


        //problema 5
        List<Integer> lista1 = Arrays.asList(1, 2, 3, 4, 2);
        assert valoareRepetata(lista1) == 2;
        assert valoareRepetataAI(new int[]{1, 2, 3, 4,2}) == 2;

        //problema 6
        int[] arr = {2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2};
        List<Integer> lista = Arrays.asList(2,8,7,2,2,5,2,3,1,2,2);
        assert elementMajoritarAI(arr) == 2;
        assert elementMajoritar(lista) == 2;

        //problema 7
        int[] array = {7, 4, 6, 3, 9, 1};
        assert alKleaCelMaiMareAI(array,2) == 7;
        assert alKleaCelMaiMare(Arrays.asList(7,4,6,3,9,1),2) == 7;

        //problema 8
        int n = 4;
        List<String> expected = Arrays.asList("1", "10", "11", "100");
        assert numereBinar(n).equals(expected);
        assert numereBinarAI(n).equals(expected);

        //problema 9
        int[][] matrix = {
                {0, 2, 5, 4, 1},
                {4, 8, 2, 3, 7},
                {6, 3, 4, 6, 2},
                {7, 3, 1, 8, 3},
                {1, 5, 7, 9, 4}
        };

        List<Pereche> perechi = Arrays.asList(
                new Pereche(1, 1, 3, 3),
                new Pereche(2, 2, 4, 4)
        );

        List<int[]> pairs = Arrays.asList(
                new int[]{1, 1, 3, 3},
                new int[]{2, 2, 4, 4}
        );

        List<Integer> result1 = sumaElementeSubMatrice(matrix, perechi);
        List<Integer> result2 = sumaElementeSubMatriceAI(matrix, pairs);

        assert result1.equals(Arrays.asList(38,44));
        assert result2.equals(Arrays.asList(38,44));

        //problema 10
        int[][] matrice = {
                {0, 0, 1, 1},
                {0, 1, 1, 1},
                {0, 0, 0, 1}
        };

        assert linieCuCeiMaiMultiUnu(matrice) == 1;
        assert liniaCuCeiMaiMultiUnuAI(matrice) == 1;
    }
    static class Pereche{
        int p;
        int q;
        int r;
        int s;
        public Pereche(int p, int q, int r, int s) {
            this.p = p;
            this.q = q;
            this.r = r;
            this.s = s;
        }
    }
}
