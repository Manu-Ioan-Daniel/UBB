package repo;
import domain.User;
import utils.database.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DbUserRepo implements Repository<Long, User>{
    private Connection connection;
    public DbUserRepo() throws SQLException {
        connection = DbConnection.getInstance().getConnection();
    }
    @Override
    public Optional<User> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> save(User entity) {
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.empty();
    }
}
