using CSharpLab_2.models;

namespace CSharpLab_2.repos;

public interface IEmployeeRepo : IRepository<long, Employee>
{
    Employee? FindByUsername(string username);
}