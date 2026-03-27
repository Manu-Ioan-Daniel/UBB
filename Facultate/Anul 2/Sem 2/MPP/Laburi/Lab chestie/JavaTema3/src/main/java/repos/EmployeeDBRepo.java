package repos;

import models.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class EmployeeDBRepo implements EmployeeRepo{

    private final JdbcUtils dbUtils;
    private final Logger logger = LogManager.getLogger();

    public EmployeeDBRepo(Properties properties) {
        logger.info("Constructing EmployeeDBRepo with properties: {} ", properties);
        this.dbUtils = new JdbcUtils(properties);
    }

    /**
     * Finds an Employee in the database by their username
     * @param username the username of the Employee to find
     * @return an Optional containing the Employee if found, or an empty Optional if not found or if there was an error accessing the database
     */
    @Override
    public Optional<Employee> findByUsername(String username) {

        String sql ="SELECT * FROM employees WHERE username = ?";
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Employee employee = employeeFromResultSet(rs);
                logger.info("Employee found with username {}: {}", username, employee);
                return Optional.of(employee);

            }else{
                logger.info("No employee found with username {}", username);
                return Optional.empty();
            }

        }catch(SQLException e){
            logger.error(e);
            return Optional.empty();
        }

    }

    /**
     * Finds an Employee in the database by their ID
     * @param id the ID of the Employee to find
     * @return an Optional containing the Employee if found, or an empty Optional if not found or if there was an error accessing the database
     */
    @Override
    public Optional<Employee> findOne(Long id) {

        String sql = "SELECT * FROM employees WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Employee employee = employeeFromResultSet(rs);
                logger.info("Employee found with id {}: {}", id, employee);
                return Optional.of(employee);
            }else{
                logger.info("No employee found with id {}", id);
                return Optional.empty();
            }
        }catch(SQLException e){
            logger.error(e);
            return Optional.empty();
        }


    }

    /**
     * Finds all Employees in the database
     * @return a List of all Employees in the database, or an empty List if there was an error accessing the database
     */
    @Override
    public List<Employee> findAll() {

        String sql = "SELECT * FROM employees";
        List<Employee> employees = new ArrayList<>();
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql);){

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = employeeFromResultSet(rs);
                employees.add(employee);
            }
            logger.info("Found {} employees in the database", employees.size());

        }catch(SQLException e){
            logger.error(e);
        }
        return employees;
    }

    /**
     * Saves a new Employee to the database
     * @param employee the Employee to save
     */
    @Override
    public void save(Employee employee) {

        String sql = "INSERT INTO employees (username, password, name, office) VALUES (?, ?, ?, ?)";
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setString(1, employee.getUsername());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getName());
            ps.setString(4, employee.getOffice());
            ps.executeUpdate();
            logger.info("Employee saved successfully: {}", employee);

        }catch(SQLException e){
            logger.error(e);
        }
    }

    /**
     * Deletes an Employee from the database by their ID
     * @param id the ID of the Employee to delete
     */
    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM employees WHERE id = ?";
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("Employee with id {} deleted successfully", id);

        }catch(SQLException e){
            logger.error(e);
        }
    }

    /**
     * Updates an existing Employee in the database
     * @param employee the Employee with the updated data to save to the database and the id of the Employee to update
     */
    @Override
    public void update(Employee employee) {

        String sql = "UPDATE employees SET username = ?, password = ?, name = ?, office = ? WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setString(1, employee.getUsername());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getName());
            ps.setString(4, employee.getOffice());
            ps.setLong(5, employee.getId());
            ps.executeUpdate();
            logger.info("Employee updated successfully: {}", employee);

        }catch(SQLException e){
            logger.error(e);
        }
    }

    /**
     * Returns an Employee object created from the data in the given ResultSet
     * @param rs the ResultSet containing the employee data
     * @return an Employee object created from the data in the ResultSet
     * @throws SQLException if there is an error accessing the ResultSet
     */
    private Employee employeeFromResultSet(ResultSet rs) throws SQLException {

        String username=rs.getString("username");
        String password=rs.getString("password");
        String name=rs.getString("name");
        String office=rs.getString("office");
        Employee employee=new Employee(username,password,name,office);
        employee.setId(rs.getLong("id"));

        return employee;
    }
}
