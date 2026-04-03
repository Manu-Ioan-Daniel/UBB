using CSharpLab3.repos;
using CSharpLab3.services;
using CSharpLab3.utils;

namespace CSharpLab3.factories;

public class ServiceFactory
{
    private static ServiceFactory? _instance;
    private readonly DbUtils _dbUtils;
    private ServiceFactory()
    {
        _dbUtils = new DbUtils("db.properties");
    }

    public static ServiceFactory Instance => _instance ??= new ServiceFactory();

    public AuthService GetAuthService() =>
        new(new EmployeeDbRepo(_dbUtils));

    public MainService GetMainService() =>
        new(new EmployeeDbRepo(_dbUtils),
            new RidesDbRepo(_dbUtils),
            new ReservationsDbRepo(_dbUtils));
}