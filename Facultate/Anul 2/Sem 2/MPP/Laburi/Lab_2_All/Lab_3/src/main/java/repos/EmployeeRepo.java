package repos;

import models.Employee;

public interface EmployeeRepo extends Repository<Employee, Long> {

    Employee findByUsername(String username);

}
