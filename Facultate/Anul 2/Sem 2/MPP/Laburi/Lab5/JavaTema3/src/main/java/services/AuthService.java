package services;

import models.Employee;
import repos.EmployeeDBRepo;

import java.util.Optional;

public class AuthService {
    private final EmployeeDBRepo employeeDBRepo;

    public AuthService(EmployeeDBRepo employeeDBRepo) {
        this.employeeDBRepo = employeeDBRepo;
    }

    public boolean authenticate(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return false;
        }
        Optional<Employee> employee = employeeDBRepo.findByUsername(username);
        return employee.map(value -> value.getPassword().equals(password)).orElse(false);
    }
}
