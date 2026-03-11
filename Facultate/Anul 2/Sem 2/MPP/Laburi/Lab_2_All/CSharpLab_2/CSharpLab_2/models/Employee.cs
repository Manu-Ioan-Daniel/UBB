namespace CSharpLab_2.models;

public class Employee : Entity<long>
{
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required string Name { get; set; }
    public required string Office { get; set; }
}