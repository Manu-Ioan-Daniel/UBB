namespace CSharpLab3.models;

public abstract class Entity<TId>
{
    public TId? Id { get; set; }
}