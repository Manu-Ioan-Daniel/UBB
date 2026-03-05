package repos;

import java.util.List;

public interface Repository<ID,Entity> {
    /***
     * Gaseste o entitate dupa id
     * @param id id ul entitatii cautate
     * @return entitatea cu id ul dat sau null daca nu exista
     */
    Entity findOne(ID id);

    /***
     * Gaseste toate entitatile
     * @return o lista cu toate entitatile
     */
    List<Entity> findAll();

    /***
     * Salveaza o entitate
     * @param entity entitatea de salvat
     */
    void save(Entity entity);

    /***
     * Sterge o entitate dupa id
     * @param id id ul entitatii de sters
     */
    void delete(ID id);

    /***
     * Actualizeaza o entitate
     * @param entity entitatea cu id ul entitatii de actualizat si datele noi
     */
    void update(Entity entity);

}
