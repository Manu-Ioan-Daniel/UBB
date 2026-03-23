package repos;

import java.util.List;
import java.util.Optional;

public interface Repository<Entity, ID> {

    Optional<Entity> findOne(ID id);
    List<Entity> findAll();
    void save(Entity entity);
    void delete(ID id);
    void update(Entity entity);

}

