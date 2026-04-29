package repos;

import models.Professor;

import java.util.List;

public interface ProfessorRepository extends Repository<Long, Professor>{
    /***
     * Cauta un profesor dupa email
     * @param email email ul profesorului cautat
     * @return profesorul cu email ul dat sau null daca nu exista
     */
    public Professor findByEmail(String email);

    List<Professor> findPageByMaterieOffset(Long materieId, int pageNumber, int pageSize);
    List<Professor> findPageByMaterieAfter(Long materieId, Long lastId, int pageSize);
}
