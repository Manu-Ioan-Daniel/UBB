import java.util.*;

public class MyMap {
    private Map<Integer, List<Student>> studenti;
    public MyMap(){
        studenti = new TreeMap<>(Comparator.reverseOrder());
    }
    public void addStudent(Student student){
        if(student.getMedia()<0 || student.getMedia()>10){
            throw new IllegalArgumentException("Media trebuie sa fie intre 0 si 10");
        }
        int mediaRotunjita = Math.round(student.getMedia());
        studenti.putIfAbsent(mediaRotunjita, new java.util.ArrayList<>());
        studenti.get(mediaRotunjita).add(student);
    }
    public Set<Map.Entry<Integer,List<Student>>> getEntries(){
        return studenti.entrySet();
    }
    public static List<Student> getList(){
        List<Student> l=new ArrayList<Student>();
        l.add(new Student("1",9.7f));
        l.add(new Student("2",7.3f));
        l.add(new Student("3",6f));
        l.add(new Student("4",6.9f));
        l.add(new Student("5",9.5f));
        l.add(new Student("6",9.9f));
        return l;
    }

    public void main(String[] args){
        MyMap myMap=new MyMap();
        for(Student s:getList()){
            myMap.addStudent(s);
        }
        for(Map.Entry<Integer,List<Student>> entry: myMap.getEntries()){
            entry.getValue().sort(new Comparator<Student>(){
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getNume().compareTo(o2.getNume());
                }
            });
        }
    }

}
