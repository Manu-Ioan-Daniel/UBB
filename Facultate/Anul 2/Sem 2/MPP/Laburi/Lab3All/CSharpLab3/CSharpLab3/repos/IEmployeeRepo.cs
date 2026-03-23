using CSharpLab3.models;

namespace CSharpLab3.repos;

public interface IEmployeeRepo : IRepository<long, Employee>
{
    Employee? FindByUsername(string username);
}