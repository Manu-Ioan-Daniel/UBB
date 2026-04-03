using MySql.Data.MySqlClient;
using NLog;

namespace CSharpLab3.utils;

public class DbUtils
{
    private MySqlConnection? _connection;
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();
    private readonly string _connectionString;

    public DbUtils(string propertiesFile)
    {
        var props = File.ReadAllLines(propertiesFile)
            .Where(line => !string.IsNullOrWhiteSpace(line) && line.Contains('='))
            .ToDictionary(
                line => line[..line.IndexOf('=')].Trim(),
                line => line[(line.IndexOf('=') + 1)..].Trim()
            );

        _connectionString = $"Server={props["sql.url"]};" +
                            $"Port={props["sql.port"]};" +
                            $"Database={props["sql.database"]};" +
                            $"Uid={props["sql.user"]};" +
                            $"Pwd={props["sql.password"]};";
    }
    private MySqlConnection GetNewConnection()
    {
        try
        {
            var con = new MySqlConnection(_connectionString);
            con.Open();
            Logger.Info("Database connection established");
            return con;
        }
        catch (MySqlException ex)
        {
            Logger.Error(ex, "Error creating database connection");
            Console.WriteLine($"Error getting connection: {ex.Message}");
            throw;
        }
    }
    
    public MySqlConnection GetConnection()
    {
        Logger.Info("Connecting to database...");
        if (_connection is not { State: System.Data.ConnectionState.Open })
        {
            _connection = GetNewConnection();
        }
        return _connection;
    }


}