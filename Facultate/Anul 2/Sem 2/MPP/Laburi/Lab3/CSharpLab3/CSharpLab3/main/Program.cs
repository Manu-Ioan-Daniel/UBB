using CSharpLab3.models;
using CSharpLab3.repos;
using CSharpLab3.utils;
using NLog;
using NLog.Config;
using NLog.Targets;

class Program
{
    static void Main(string[] args)
    {
        var config = new LoggingConfiguration();
        var consoleTarget = new ColoredConsoleTarget("console")
        {
            Layout = @"${logger} | ${message}"
        };
        config.AddTarget(consoleTarget);
        config.AddRule(LogLevel.Info, LogLevel.Fatal, consoleTarget);
        LogManager.Configuration = config;
        
        const string url = "Server=localhost;Port=3306;Database=mpp;Uid=root;Pwd=1234;";
        var utils = new DbUtils(url);
        var employeeRepo = new EmployeeDbRepo(utils);
        var ridesRepo = new RidesDbRepo(utils);
        var reservationsRepo = new ReservationsDbRepo(utils);

        RunEmployee(employeeRepo);
        RunRideAndReservationOps(ridesRepo, reservationsRepo);
    }

    private static void RunEmployee(EmployeeDbRepo employeeRepo)
    {
        var employee1 = new Employee
        {
            Username = "emp1",
            Password = "pass1",
            Name = "Demo Employee 1",
            Office = "Office 1"
        };
        var employee2 = new Employee
        {
            Username = "emp2",
            Password = "pass2",
            Name = "Demo Employee 2",
            Office = "Office 2"
        };

        employeeRepo.Save(employee1);
        employeeRepo.Save(employee2);

        var savedEmployee1 = employeeRepo.FindByUsername("emp1");
        var savedEmployee2 = employeeRepo.FindByUsername("emp2");

        if(savedEmployee1 == null || savedEmployee2 == null)
            throw new Exception("Could not find inserted employees for demo operations");

        var employeeToUpdate = new Employee
        {
            Username = "emp1",
            Password = "pass1",
            Name = "Demo Employee 1 Updated",
            Office = "Office 1 Updated",
            Id = savedEmployee1.Id
        };
        employeeRepo.Update(employeeToUpdate);

        employeeRepo.Delete(savedEmployee1.Id);
        employeeRepo.Delete(savedEmployee2.Id);
    }

    private static void RunRideAndReservationOps(RidesDbRepo ridesRepo, ReservationsDbRepo reservationsRepo)
    {
        var ride1 = new Ride
        {
            Destination = "dest1",
            Date = new DateOnly(2026, 3, 20),
            DepartureTime = new TimeOnly(10, 0)
        };
        var ride2 = new Ride
        {
            Destination = "dest2",
            Date = new DateOnly(2026, 3, 21),
            DepartureTime = new TimeOnly(12, 30)
        };

        ridesRepo.Save(ride1);
        ridesRepo.Save(ride2);

        var allRides = ridesRepo.FindAll().ToList();
        var savedRide1 = allRides.ElementAtOrDefault(0);
        var savedRide2 = allRides.ElementAtOrDefault(1);

        if(savedRide1 == null || savedRide2 == null)
            throw new Exception("Could not find inserted rides for demo operations");

        var rideToUpdate = new Ride
        {
            Destination = savedRide1.Destination,
            Date = savedRide1.Date,
            DepartureTime = savedRide1.DepartureTime,
            Id = savedRide1.Id,
            AvailableSeats = 10
        };
        ridesRepo.Update(rideToUpdate);

        var reservation1 = new Reservation
        {
            ClientName = "client1",
            ReservedSeatsCount = 2,
            RideId = savedRide1.Id
        };
        var reservation2 = new Reservation
        {
            ClientName = "client2",
            ReservedSeatsCount = 3,
            RideId = savedRide2.Id
        };

        reservationsRepo.Save(reservation1);
        reservationsRepo.Save(reservation2);

        var allReservations = reservationsRepo.FindAll().ToList();
        var savedReservation1 = allReservations.ElementAtOrDefault(0);
        var savedReservation2 = allReservations.ElementAtOrDefault(1);

        if(savedReservation1 != null)
        {
            var reservationToUpdate = new Reservation
            {
                ClientName = savedReservation1.ClientName,
                ReservedSeatsCount = 1,
                RideId = savedReservation1.RideId,
                Id = savedReservation1.Id
            };
            reservationsRepo.Update(reservationToUpdate);
        }

        if(savedReservation1 != null) reservationsRepo.Delete(savedReservation1.Id);
        if(savedReservation2 != null) reservationsRepo.Delete(savedReservation2.Id);

        ridesRepo.Delete(savedRide1.Id);
        ridesRepo.Delete(savedRide2.Id);
    }
}