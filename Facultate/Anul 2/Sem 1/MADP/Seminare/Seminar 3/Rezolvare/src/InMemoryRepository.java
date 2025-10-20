import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InMemoryRepository implements Repository<Integer,Student>{
    private Map<Integer,Student> studenti;
    public InMemoryRepository(){
        studenti=new HashMap<Integer,Student>();
    }
    @Override
    public Student findOne(Integer id){
        if(id==null)
            throw new IllegalArgumentException("Id-ul nu poate fi null!");
        return studenti.get(id);
    }
    @Override
    public Iterable<Student> findAll(){
        return studenti.values();
    }
    @Override
    public Student save(Student student){
        if(student==null)
            throw new IllegalArgumentException("student null!");
        ValidatorStudenti.valideazaStudent(student);
        if(studenti.containsValue(student))
            return student;
        studenti.put(student.getId(),student);
        return null;
    }
    @Override
    public Student delete(Integer id){
        if(id==null)
            throw new IllegalArgumentException("Id null!");
        return studenti.remove(id);
    }
    @Override
    public Student update(Student student){
        if(student==null)
            throw new IllegalArgumentException("student null!");
        ValidatorStudenti.valideazaStudent(student);
        if(!studenti.containsKey(student.getId()))
            return student;
        studenti.replace(student.getId(),student);
        return null;

    }

}
