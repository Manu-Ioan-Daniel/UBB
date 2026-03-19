namespace CSharpLab_2.models;

public abstract class Entity<TId>
{
    public TId? Id { get; set; }
}