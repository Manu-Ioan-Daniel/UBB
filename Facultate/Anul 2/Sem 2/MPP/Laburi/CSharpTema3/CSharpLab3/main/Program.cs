using CSharpLab3.repos;
using CSharpLab3.utils;


class Program
{
    static void Main(string[] args)
    {
        var utils = new DbUtils("db.properties");
        var employeeRepo = new EmployeeDbRepo(utils);
        var ridesRepo = new RidesDbRepo(utils);
        var reservationsRepo = new ReservationsDbRepo(utils);

    }
    
}