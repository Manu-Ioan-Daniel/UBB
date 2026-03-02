package services;

import models.Materie;
import models.Professor;
import models.Student;
import repos.Repository;
import utils.Tuple;
import validator.Validator;

import java.util.List;

public class Service {
    private final Repository<Long, Materie> repoMaterii;
    private final Repository<Long, Student> repoStudenti;
    private final Repository<Long, Professor> repoProfessor;
    private final Repository<Tuple<Long,Long>,Long> repoNote;
    private final Validator<Professor> validatorProfessor;

    public Service(Repository<Long, Materie> repoMaterii, Repository<Long, Student> repoStudenti, Repository<Long, Professor> repoProfessor, Repository<Tuple<Long,Long>, Long> repoNote, Validator<Professor> validatorProfessor) {
        this.repoMaterii = repoMaterii;
        this.repoStudenti = repoStudenti;
        this.repoProfessor = repoProfessor;
        this.repoNote = repoNote;
        this.validatorProfessor = validatorProfessor;
    }

    public List<Materie> getAllMaterii(){
        return repoMaterii.findAll();
    }

    public List<Student> getAllStudentsByMaterie(Long idMaterie){
        List<Student> students = repoStudenti.findAll();
        return students.stream().filter(s->!(getNota(idMaterie, s.getId()) == null)).toList();
    }

    public List<Professor> getAllProfessorsByMaterie(Long idMaterie) {
        List<Professor> professors = repoProfessor.findAll();
        return professors.stream().filter(p->p.getMaterieId().equals(idMaterie)).toList();
    }
    public Long getNota(Long materieId, Long studentId) {
        return repoNote.findOne(new Tuple<>(studentId, materieId));
    }

    public void addProfessor(String name, String ageString, String email, String materieIdString) {
        Professor p = new Professor(name, Integer.parseInt(ageString), email, Long.parseLong(materieIdString));
        validatorProfessor.validate(p);
        repoProfessor.save(p);
    }
}
