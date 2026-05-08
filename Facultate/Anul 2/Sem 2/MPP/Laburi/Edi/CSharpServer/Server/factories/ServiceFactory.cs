namespace Server.factories;

using repos;
using services;
using utils;
using Shared.services;

public class ServiceFactory
{
    private static ServiceFactory? _instance;
    private readonly DbUtils _dbUtils;
    
    public static ServiceFactory Instance => _instance ??= new ServiceFactory();
    
    private ServiceFactory()
    {
        _dbUtils = new DbUtils("C:\\Users\\Deny\\Documents\\GitHub\\mpp-proiect-csharp-Manu-Ioan-Daniel\\CSharpLab_3\\Client\\db.properties");
    }

    public AuthService GetAuthService() =>
        new(new EmployeeDbRepo(_dbUtils));

    public IMainService GetMainService()
    {
        return new MainService(
            new EmployeeDbRepo(_dbUtils),
            new RidesDbRepo(_dbUtils),
            new ReservationsDbRepo(_dbUtils)
        );
    }

}