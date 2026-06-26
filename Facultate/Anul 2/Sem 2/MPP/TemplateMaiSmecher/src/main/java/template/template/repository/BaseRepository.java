package template.template.repository;

import java.util.Optional;

public interface BaseRepository<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    void update(T entity);
    void delete(T entity);
    void deleteById(ID id);
}