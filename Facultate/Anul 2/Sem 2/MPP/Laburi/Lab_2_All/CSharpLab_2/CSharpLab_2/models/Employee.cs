namespace CSharpLab_2.models;

public class Employee : Entity<long>
{
    public required string Username { get; init; }
    public required string Password { get; init; }
    public required string Name { get; init; }
    public required string Office { get; init; }
}