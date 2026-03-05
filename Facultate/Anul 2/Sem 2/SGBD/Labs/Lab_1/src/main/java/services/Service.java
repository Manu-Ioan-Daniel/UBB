package services;

import exceptions.ServiceException;
import models.Materie;
import models.Professor;
import models.Student;
import repos.ProfessorRepository;
import repos.Repository;
import utils.Observable;
import utils.Tuple;
import validator.Validator;

import java.util.List;

public class Service extends Observable {

    private final Repository<Long, Materie> repoMaterii;
    private final Repository<Long, Student> repoStudenti;
    private final Repository<Tuple<Long,Long>,Long> repoNote;
    private final ProfessorRepository repoProfessor;
    private final Validator<Professor> validatorProfessor;

    public Service(Repository<Long, Materie> repoMaterii, Repository<Long, Student> repoStudenti, ProfessorRepository repoProfessor, Repository<Tuple<Long,Long>, Long> repoNote, Validator<Professor> validatorProfessor) {
        this.repoMaterii = repoMaterii;
        this.repoStudenti = repoStudenti;
        this.repoProfessor = repoProfessor;
        this.repoNote = repoNote;
        this.validatorProfessor = validatorProfessor;
    }

    public List<Materie> getAllMaterii(){
        return repoMaterii.findAll();
    }

    /***
     * @param idMaterie id ul materiei pentru care se cauta studentii
     * @return o lista cu toti studentii care au nota la materia cu id ul dat
     */
    public List<Student> getAllStudentsByMaterie(Long idMaterie){
        List<Student> students = repoStudenti.findAll();
        return students.stream().filter(s->!(getNota(idMaterie, s.getId()) == null)).toList();
    }

    /***
     * @param idMaterie id ul materiei pentru care se cauta profesorii
     * @return o lista cu toti profesorii care predau materia cu id ul dat
     */

    public List<Professor> getAllProfessorsByMaterie(Long idMaterie) {
        List<Professor> professors = repoProfessor.findAll();
        return professors.stream().filter(p->p.getMaterieId().equals(idMaterie)).toList();
    }

    /***
     * Cauta nota unui student la o materie
     * @param materieId id ul materiei pentru care se cauta nota
     * @param studentId id ul studentului pentru care se cauta nota
     * @return nota studentului la materia cu id ul dat sau null daca studentul nu are nota la acea materie
     */

    public Long getNota(Long materieId, Long studentId) {
        return repoNote.findOne(new Tuple<>(studentId, materieId));
    }

    /***
     * Adauga un profesor nou cu datele date, daca email ul nu exista deja in baza de date
     * @param name numele profesorului de adaugat
     * @param ageString varsta profesorului de adaugat
     * @param email email ul profesorului de adaugat
     * @param materieIdString id ul materiei predate de profesorul de adaugat
     */
    public void addProfessor(String name, String ageString, String email, String materieIdString){
        if(repoProfessor.findByEmail(email) != null){
            throw new ServiceException("Email ul exista deja!");
        }
        Professor p = new Professor(name, null, email, null);
        if(!ageString.isEmpty()){
            p.setAge(Integer.parseInt(ageString));
        }
        if(!materieIdString.isEmpty()){
            p.setMaterieId(Long.parseLong(materieIdString));
        }
        validatorProfessor.validate(p);
        repoProfessor.save(p);
        notifyObservers();
    }

    /***
     * Updateaza datele profesorul ui cu id ul dat
     * @param id id ul profesorului de actualizat
     * @param name numele profesorului de actualizat
     * @param ageString varsta profesorului de actualizat
     * @param email email ul profesorului de actualizat
     * @param materieIdString id ul materiei predate de profesorul de actualizat
     */
    public void updateProfessor(Long id, String name, String ageString, String email, String materieIdString) {
        Professor p = new Professor(name, Integer.parseInt(ageString), email, Long.parseLong(materieIdString));
        p.setId(id);
        validatorProfessor.validate(p);
        repoProfessor.update(p);
        notifyObservers();
    }

    public void deleteProfessor(Long id) {
        repoProfessor.delete(id);
        notifyObservers();
    }
}
