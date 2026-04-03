package repos;

import models.Employee;
import java.util.Optional;

public interface EmployeeRepo extends Repository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

}
