public static void main(){
    TreeSet<Persoana2> persoane=new TreeSet<>(new Comparator<Persoana2>() {
        @Override
        public int compare(Persoana2 p1, Persoana2 p2) {
            return p1.nume.compareTo(p2.nume);
        }
    });
    //exemplu MAP
    Map<Integer,Persoana> map=new LinkedHashMap<>();
    map.put(1,new Persoana("Ana",2));
    map.put(2,new Persoana("Tactu",3));
    for(Integer key:map.keySet()){
        System.out.println(map.get(key));
    }
    for(Map.Entry<Integer,Persoana> entry :map.entrySet()){
        System.out.println(entry.getValue());
    }
}