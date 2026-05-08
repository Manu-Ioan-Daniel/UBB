package utils;

import repos.EmployeeDBRepo;
import repos.HibernateReservationsRepo;
import repos.HibernateRidesRepo;
import services.AuthService;
import services.MainService;


public class ServiceFactory {

     private static ServiceFactory instance;

     private ServiceFactory() {

     }

     public static ServiceFactory getInstance() {
          if (instance == null) {
               instance = new ServiceFactory();
          }
          return instance;
     }

     public AuthService getAuthService() {
         return new AuthService(new EmployeeDBRepo(Props.getProperties()));
     }

     public MainService getMainService() {
            return new MainService(
                    new EmployeeDBRepo(Props.getProperties()),
                    new HibernateRidesRepo(),
                    new HibernateReservationsRepo()
            );
     }

}
