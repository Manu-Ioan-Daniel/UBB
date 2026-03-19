using CSharpLab_2.models;

namespace CSharpLab_2.repos;

public interface IRepository<TId, TEntity> where TEntity : Entity<TId>
{
    // Adăugăm ? la TEntity deoarece FindOne poate returna null dacă nu găsește TId-ul
    TEntity? FindOne(TId id);
    IEnumerable<TEntity> FindAll();
    void Save(TEntity entity);
    void Delete(TId id);
    void Update(TEntity entity);
}