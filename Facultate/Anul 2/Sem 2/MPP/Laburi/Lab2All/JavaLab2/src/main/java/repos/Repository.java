package repos;

import java.util.List;

public interface Repository<Entity, ID> {
    Entity findOne(ID id);
    List<Entity> findAll();
    void save(Entity entity);
    void delete(ID id);
    void update(Entity entity);
}
