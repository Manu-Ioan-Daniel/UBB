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

import java.math.BigDecimal;
import java.util.List;

public class Service extends Observable {

    private final Repository<Long, Materie> repoMaterii;
    private final Repository<Long, Student> repoStudenti;
    private final Repository<Tuple<Long,Long>,BigDecimal> repoNote;
    private final ProfessorRepository repoProfessor;
    private final Validator<Professor> validatorProfessor;

    private String currentUsername = "anonymous";
    private boolean currentUserAdmin = false;

    public Service(Repository<Long, Materie> repoMaterii, Repository<Long, Student> repoStudenti, ProfessorRepository repoProfessor, Repository<Tuple<Long,Long>, BigDecimal> repoNote, Validator<Professor> validatorProfessor) {
        this.repoMaterii = repoMaterii;
        this.repoStudenti = repoStudenti;
        this.repoProfessor = repoProfessor;
        this.repoNote = repoNote;
        this.validatorProfessor = validatorProfessor;
    }

    public void setCurrentUser(String username, boolean isAdmin) {
        this.currentUsername = (username == null || username.isBlank()) ? "anonymous" : username;
        this.currentUserAdmin = isAdmin;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public boolean isCurrentUserAdmin() {
        return currentUserAdmin;
    }

    public List<Materie> getAllMaterii(){
        return repoMaterii.findAll();
    }

    /**
     * Return all Materii, initializing 'notas' when using ORM repository (JOIN FETCH) to avoid LazyInitializationException in UI.
     */
    public List<Materie> getAllMateriiWithNotas(){
        if (repoMaterii instanceof repos.MaterieDBRepoORM orm) {
            return orm.findAllWithJoinFetchNotas();
        }
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

    public List<Professor> getAllDeletedProfessors() {
        if (!currentUserAdmin) {
            throw new ServiceException("Acces interzis: doar admin poate vedea inregistrarile sterse.");
        }
        return repoProfessor.findAllDeleted();
    }

    /***
     * Cauta nota unui student la o materie
     * @param materieId id ul materiei pentru care se cauta nota
     * @param studentId id ul studentului pentru care se cauta nota
     * @return nota studentului la materia cu id ul dat sau null daca studentul nu are nota la acea materie
     */

    public BigDecimal getNota(Long materieId, Long studentId) {
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
        repoProfessor.softDelete(id, currentUsername);
        notifyObservers();
    }

    public void restoreProfessor(Long id) {
        if (!currentUserAdmin) {
            throw new ServiceException("Acces interzis: doar admin poate restaura inregistrari sterse.");
        }
        repoProfessor.restore(id);
        notifyObservers();
    }

    public void hardDeleteProfessor(Long id) {
        if (!currentUserAdmin) {
            throw new ServiceException("Acces interzis: doar admin poate sterge permanent.");
        }
        repoProfessor.hardDelete(id);
        notifyObservers();
    }

    // Pagination helpers (by materie)
    public List<Professor> getProfessorsPageOffset(Long materieId, int pageNumber, int pageSize) {
        return repoProfessor.findPageByMaterieOffset(materieId, pageNumber, pageSize);
    }

    public List<Professor> getProfessorsPageKeyset(Long materieId, Long lastId, int pageSize) {
        return repoProfessor.findPageByMaterieAfter(materieId, lastId, pageSize);
    }

    // direct access to Materie by id (uses underlying repo which has a SimpleEntityCache)
    public models.Materie getMaterieById(Long id) {
        return repoMaterii.findOne(id);
    }

    // numeric cache stats accessor for Materie repo (returns [hits, misses])
    public long[] getMaterieCacheStatsArray() {
        if (repoMaterii instanceof repos.MaterieDBRepoORM mrepo) {
            return mrepo.getCacheStats();
        }
        return new long[]{0L, 0L};
    }

    // Explain analyze helper (writes file and returns path)
    public String explainQueryToFile(String sql, String outPath) {
        try {
            bench.ExplainCollector.explainToFile(sql, outPath);
            return outPath;
        } catch (Exception e) {
            throw new RuntimeException("Explain failed: " + e.getMessage(), e);
        }
    }

    // Cache stats helper for Materie repo (returns "hits/misses")
    public String getMaterieCacheStats() {
        if (repoMaterii instanceof repos.MaterieDBRepoORM mrepo) {
            long[] s = mrepo.getCacheStats();
            return s[0] + "/" + s[1];
        }
        return "N/A";
    }
}
