namespace Shared.models;

public abstract class Entity<TId>
{
    public TId? Id { get; set; }
}