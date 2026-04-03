namespace CSharpLab3.models;

public class Employee : Entity<long>
{
    public required string Username { get; init; }
    public required string Password { get; init; }
    public required string Name { get; init; }
    public required string Office { get; init; }

    public override string ToString()
    {
        return $"Employee: Username = {Username}, Name = {Name}, Office = {Office}";
    }
}