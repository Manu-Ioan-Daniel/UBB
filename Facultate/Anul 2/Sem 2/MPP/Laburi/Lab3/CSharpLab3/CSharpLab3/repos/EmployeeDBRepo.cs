using CSharpLab3.models;
using CSharpLab3.utils;
using MySql.Data.MySqlClient;
using NLog;

namespace CSharpLab3.repos;

public class EmployeeDbRepo:  IEmployeeRepo
{
    private readonly DbUtils _dbUtils;
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();
    
    public EmployeeDbRepo(DbUtils dbUtils)
    {
       _dbUtils = dbUtils;
        Logger.Info("Employee Repo created succesfully");
    }
    
    /// <summary>
    /// Finds an employee by their ID.
    /// </summary>
    /// <param name="id">The employee's ID</param>
    /// <returns>The employee with the specified ID or null if he isn't found</returns>
    public Employee? FindOne(long id)
    {
        const string sql = "SELECT * FROM employees WHERE id = @id";
        
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@id", id);
        using var reader = cmd.ExecuteReader();
        if (reader.Read())
        {
            var employee = EmployeeFromReader(reader);
            Logger.Info("Employee found: " + employee);
            return employee;
        }
        
        Logger.Info("Employee not found: " + id);
        return null;
        
    }

    /// <summary>
    /// Finds an employee by their username.
    /// </summary>
    /// <param name="username">The username of the employee</param>
    /// <returns>The employee with the specified username or null if he isn't found</returns>
    public Employee? FindByUsername(string username)
    {
        const string sql = "SELECT * FROM employees WHERE username = @username";
        
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@username", username);
        using var reader = cmd.ExecuteReader();
        if (reader.Read())
        {
            var employee = EmployeeFromReader(reader);
            Logger.Info("Employee found: " + employee);
            return employee;
        }
        Logger.Info("Employee not found: " + username);
        return null;
    }
    
    /// <summary>
    ///  Finds all employees in the database.
    /// </summary>
    /// <returns>Anything that can be iterated on containing all employees</returns>
    public IEnumerable<Employee> FindAll()
    {
        const string sql = "SELECT * FROM employees";
        
        var employees = new List<Employee>();
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        using var reader = cmd.ExecuteReader();
        
        while (reader.Read())
        {
            employees.Add(EmployeeFromReader(reader));
        }
        
        Logger.Info("Employees found: " + employees.Count);
        return employees;
    }
    
    
    /// <summary>
    /// Saves an employee to the database
    /// </summary>
    /// <param name="employee">The employee we are supposed to save</param>
    public void Save(Employee employee)
    {
        const string sql = "INSERT INTO employees (username, password, name, office) VALUES (@username,@password,@name,@office)";
        
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        
        cmd.Parameters.AddWithValue("@username", employee.Username);
        cmd.Parameters.AddWithValue("@password", employee.Password);
        cmd.Parameters.AddWithValue("@name", employee.Name);
        cmd.Parameters.AddWithValue("@office", employee.Office);
        cmd.ExecuteNonQuery();
        Logger.Info("Employee saved: Username =  " + employee.Username);
    }
    
    /// <summary>
    /// Deletes the employee with the specified id
    /// </summary>
    /// <param name="id">The id of the employee we want to delete</param>
    public void Delete(long id)
    {
        const string sql = "DELETE FROM employees WHERE id = @id";
        
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        
        cmd.Parameters.AddWithValue("@id", id);
        cmd.ExecuteNonQuery();
        Logger.Info("Employee with ID = " + id + "deleted");

    }
    /// <summary>
    /// Updates an employee in the database. The employee is identified by its id and all other fields are updated to the values of the given employee
    /// </summary>
    /// <param name="employee">The employee with new data,and the id of the employee we want to update</param>
    public void Update(Employee employee)
    {
        const string sql = "UPDATE employees SET username = @username, password = @password, name = @name, office = @office WHERE id = @id";
        
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        
        cmd.Parameters.AddWithValue("@id", employee.Id);
        cmd.Parameters.AddWithValue("@username", employee.Username);
        cmd.Parameters.AddWithValue("@password", employee.Password);
        cmd.Parameters.AddWithValue("@name", employee.Name);
        cmd.Parameters.AddWithValue("@office", employee.Office);
        cmd.ExecuteNonQuery();
        Logger.Info("Employee updated: Username = " + employee.Username);
    }
    
    /// <summary>
    /// Helper method that creates an Employee object from a MySqlDataReader
    /// </summary>
    /// <param name="reader"> A MySqlDataReader containing all employee information</param>
    /// <returns>The employee that results from the MySqlDataReader</returns>
    private static Employee EmployeeFromReader(MySqlDataReader reader)
    {
        return new Employee
        {
            Id = reader.GetInt64("id"),
            Username = reader.GetString("username"),
            Password = reader.GetString("password"),
            Name = reader.GetString("name"),
            Office = reader.GetString("office")
        };
    }
    
    
    
}