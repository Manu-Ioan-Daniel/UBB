using Shared.models;

namespace Server.repos;

public interface IEmployeeRepo : IRepository<long, Employee>
{
    Employee? FindByUsername(string username);
}