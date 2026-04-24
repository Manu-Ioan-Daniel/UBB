using Shared.models;

namespace Server.repos;

public interface IRepository<in TId, TEntity> where TEntity : Entity<TId>
{
    
    TEntity? FindOne(TId id);
    IEnumerable<TEntity> FindAll();
    void Save(TEntity entity);
    void Delete(TId id);
    void Update(TEntity entity);
    
}