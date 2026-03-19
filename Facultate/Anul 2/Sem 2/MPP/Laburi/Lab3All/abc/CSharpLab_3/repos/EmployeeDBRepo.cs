using CSharpLab_3.models;
using CSharpLab_3.utils;
using MySql.Data.MySqlClient;

namespace CSharpLab_3.repos;

public class EmployeeDBRepo
{
    private readonly DbUtils _dbUtils;

    public EmployeeDBRepo(DbUtils dbUtils)
    {
        _dbUtils = dbUtils;
    }

    public Employee? FindByUsername(string username)
    {
        const string sql = "SELECT * FROM employees WHERE username = @username";
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@username", username);

        using var reader = cmd.ExecuteReader();
        
        return reader.Read() ? EmployeeFromReader(reader) : null;
    }

    public List<Employee> FindAll()
    {
        string sql = "SELECT * FROM employees";
        var employees = new List<Employee>();
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        using var reader = cmd.ExecuteReader();
        while (reader.Read())
        {
            employees.Add(EmployeeFromReader(reader));
        }
        return employees;
    }

    public void Save(Employee employee)
    {
        string sql = "INSERT INTO employees (username, password, name, office) VALUES (@username,@password,@name,@office)";
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@username", employee.Username);
        cmd.Parameters.AddWithValue("@password", employee.Password);
        cmd.Parameters.AddWithValue("@name", employee.Name);
        cmd.Parameters.AddWithValue("@office", employee.Office);
        cmd.ExecuteNonQuery();
    }

    private Employee EmployeeFromReader(MySqlDataReader reader)
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