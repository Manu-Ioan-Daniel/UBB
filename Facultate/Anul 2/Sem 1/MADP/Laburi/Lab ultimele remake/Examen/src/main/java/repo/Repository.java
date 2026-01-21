package repo;

import models.Entity;

import java.util.List;
import java.util.Optional;



public interface Repository<ID, E extends Entity<ID>> {

    Optional<E> findOne(ID id);

    List<E> findAll();

    void save(E entity);

    void delete(ID id);

    void update(E entity);

}


