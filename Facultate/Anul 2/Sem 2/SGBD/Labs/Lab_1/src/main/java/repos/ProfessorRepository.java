package repos;

import models.Professor;

public interface ProfessorRepository extends Repository<Long, Professor>{
    /***
     * Cauta un profesor dupa email
     * @param email email ul profesorului cautat
     * @return profesorul cu email ul dat sau null daca nu exista
     */
    public Professor findByEmail(String email);
}
