using MySql.Data.MySqlClient;
using NLog;

namespace CSharpLab_3.utils;

public class DbUtils(string connectionString)
{
    private MySqlConnection? _connection;
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();


    private MySqlConnection GetNewConnection()
    {
        try
        {
            var con = new MySqlConnection(connectionString);
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