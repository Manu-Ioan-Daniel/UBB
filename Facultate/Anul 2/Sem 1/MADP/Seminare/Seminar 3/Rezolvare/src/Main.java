void main(){
    Student s1= new Student("Dan", 4.5f); Student s2= new Student("Ana", 8.5f); Student s3= new Student("Dan", 4.5f);
    HashSet<Student> studenti= new HashSet<>();
    studenti.add(s1); studenti.add(s2); studenti.add(s3);
    System.out.println("Numar studenti: "+studenti.size());
    for (Student s: studenti){
        System.out.println(s);
    }
    TreeSet<Student> studenti2=new TreeSet<Student>(new Comparator<Student>(){
        @Override
        public int compare(Student o1, Student o2) {
            int rezultat=Float.compare(o1.getMedia(),o2.getMedia());
            if(rezultat==0){
                return o1.getNume().compareTo(o2.getNume());
            }
            return rezultat;
        }
    });
    studenti2.add(s1);
    studenti2.add(s2);
    studenti2.add(s3);
    HashMap<Float,Student> studentiMap=new HashMap<>();
    studentiMap.put(s1.getMedia(),s1);
    studentiMap.put(s2.getMedia(),s2);
    studentiMap.put(s3.getMedia(),s3);
    for(Map.Entry<Float,Student> entry:studentiMap.entrySet())
        System.out.println("Cheia: "+entry.getKey()+" Valoarea: "+entry.getValue());
    TreeMap<Float,Student> studentiTreeMap=new TreeMap<>();
    studentiTreeMap.put(s1.getMedia(),s1);
    studentiTreeMap.put(s2.getMedia(),s2);
    studentiTreeMap.put(s3.getMedia(),s3);
    for(Map.Entry<Float,Student> entry:studentiTreeMap.entrySet())
        System.out.println("Cheia: "+entry.getKey()+" Valoarea: "+entry.getValue());
}