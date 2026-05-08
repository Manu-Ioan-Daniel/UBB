using Server.repos;
using Shared.services;

namespace Server.services;

public class AuthService : IAuthService
{
    private readonly IEmployeeRepo _employeeRepo;

    public AuthService(IEmployeeRepo employeeRepo)
    {
        _employeeRepo = employeeRepo;
    }

    public bool Authenticate(string username, string password)
    {
        if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            return false;

        var employee = _employeeRepo.FindByUsername(username);
        return employee?.Password == password;
    }
}